package com.tapatron.data

import java.time.LocalDateTime

import scalikejdbc._
import scalikejdbc.jsr310._

case class Post(id: Option[Long],
                articleId: String,
                title: String,
                source: String,
                url: String,
                imgUrl: String,
                added: LocalDateTime,
                categories: Option[Seq[String]])

object Post extends SQLSyntaxSupport[Post] {
  override val tableName = "posts"

  def apply(p: SyntaxProvider[Post])(rs: WrappedResultSet): Post = apply(p.resultName)(rs)

  def apply(p: ResultName[Post])(rs: WrappedResultSet): Post = new Post(
    id = rs.longOpt(p.id),
    articleId = rs.string(p.articleId),
    title = rs.string(p.title),
    source = rs.string(p.source),
    url = rs.string(p.url),
    added = rs.get[LocalDateTime](p.added),
    imgUrl = rs.string(p.imgUrl),
    categories = None
  )

  def apply(rs: WrappedResultSet): Post = new Post(
    id = rs.longOpt("id"),
    articleId = rs.string("article_id"),
    title = rs.string("title"),
    source = rs.string("source"),
    url = rs.string("url"),
    added = rs.get[LocalDateTime]("added"),
    imgUrl = rs.string("img_url"),
    categories = None
  )
}
