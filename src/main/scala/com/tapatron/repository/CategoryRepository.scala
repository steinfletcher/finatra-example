package com.tapatron.repository

import com.google.inject.Singleton
import scalikejdbc._

@Singleton
class CategoryRepository {

  def selectAllByPost(postsId: Long): Seq[String] = DB readOnly { implicit session =>
    sql"""
          SELECT text FROM post_categories WHERE posts_id = ${postsId}
      """.map{rs => rs.get[String]("text")}.list.apply()
  }
}
