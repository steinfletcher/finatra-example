package com.tapatron.repository

import java.util.UUID

import com.google.inject.Singleton
import com.tapatron.data.Post
import scalikejdbc._

@Singleton
class PostRepository {

  def selectAll(limit: Int, offset: Int): Seq[Post] = DB readOnly { implicit session =>
    sql"SELECT * FROM posts ORDER BY added DESC LIMIT ${limit} OFFSET ${offset}".map { rs => Post(rs) }.list.apply()
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

  def selectById(id: UUID): Option[Post] = DB readOnly { implicit session =>
    sql"SELECT * FROM posts WHERE id = ${id}".map { rs => Post(rs) }.single.apply()
  }
}
