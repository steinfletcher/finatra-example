package com.tapatron.repository

import java.util.UUID.randomUUID

import com.tapatron.DyServer
import com.tapatron.data.Post
import com.tapatron.testsupport.JsonPathMatchers
import com.twitter.finatra.http.EmbeddedHttpServer
import com.twitter.inject.server.FeatureTest
import scalikejdbc._

import scala.math.Numeric.IntIsIntegral.abs
import scala.util.Random

class IntegrationTest extends FeatureTest with JsonPathMatchers {
  override val server = new EmbeddedHttpServer(new DyServer)

  val random = new Random()

  def generateId() = Some(abs(random.nextInt()).toLong)

  def clearData() = DB localTx  { implicit session =>
    sql"DELETE FROM post_categories".update.apply()
    sql"DELETE FROM posts".update.apply()
    sql"DELETE FROM users".update.apply()
  }

  def givenSomePosts(posts: Post*) = DB localTx { implicit session =>

    posts.foreach(post => {
      sql"INSERT INTO posts VALUES (${post.id}, ${post.articleId}, ${post.title}, ${post.source}, ${post.added}, ${post.url}, ${post.imgUrl})"
        .update.apply()

      post.categories match {
        case Some(categories) => categories.foreach(category => {
          sql"INSERT INTO post_categories(id, posts_id, text) VALUES (${generateId()}, ${post.id}, ${category})".update.apply()
        })
        case _ => ;
      }

    })
  }
}
