package com.tapatron.repository

import java.util.UUID

import com.google.inject.Singleton
import com.tapatron.data.Post
import scalikejdbc._

@Singleton
class PostRepository {
  val p = Post.syntax("p")

  def selectAll(limit: Int, offset: Int): Seq[Post] = DB readOnly { implicit session =>
    withSQL {
      select.from(Post as p)
        .orderBy(p.added).desc
        .limit(limit)
        .offset(offset)
    }.map(Post(p)).list.apply()
  }

    def selectAll(limit: Int, offset: Int, q: Seq[String]): Seq[Post] = DB readOnly { implicit session =>
      sql"""
           SELECT DISTINCT(posts_id), p.* FROM post_categories pc
           LEFT OUTER JOIN posts p ON pc.posts_id = p.id
           WHERE pc.text IN (${q})
           ORDER BY p.added DESC
           LIMIT ${limit}
           OFFSET ${offset}
        """
        .map { rs => Post(rs) }.list.apply()
    }


    def selectById(id: Long): Option[Post] = DB readOnly { implicit session =>
      withSQL {
        select.from(Post as p).where.eq(p.id, id)
      }.map(Post(p)).single.apply()
    }
}
