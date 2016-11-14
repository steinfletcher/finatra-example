package com.tapatron.data

import java.time.LocalDateTime
import java.util.UUID

import scalikejdbc._
import scalikejdbc.jsr310._

case class Post(id: UUID,
                articleId: String,
                title: String,
                source: String,
                url: String,
                imgUrl: String,
                added: LocalDateTime,
                categories: Option[Seq[String]])

object Post {

  def apply(rs: WrappedResultSet): Post = Post(
    id = UUID.fromString(rs.string("id")),
    articleId = rs.string("article_id"),
    title = rs.string("title"),
    source = rs.string("source"),
    url = rs.string("url"),
    added = rs.get[LocalDateTime]("added"),
    imgUrl = rs.string("imgUrl"),
    categories = None
  )

  def apply(categories: Seq[String], rs: WrappedResultSet): Post = {
    Post(
      id = UUID.fromString(rs.string("id")),
      articleId = rs.string("article_id"),
      title = rs.string("title"),
      source = rs.string("source"),
      url = rs.string("url"),
      added = rs.get[LocalDateTime]("added"),
      imgUrl = rs.string("imgUrl"),
      categories = Some(categories)
    )
  }
}
