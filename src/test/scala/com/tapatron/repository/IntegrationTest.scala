package com.tapatron.repository

import java.time.LocalDateTime

import com.tapatron.DyServer
import com.tapatron.data.Post
import com.tapatron.testsupport.JsonPathMatchers
import com.twitter.finatra.http.EmbeddedHttpServer
import com.twitter.inject.server.FeatureTest
import scalikejdbc._

class IntegrationTest extends FeatureTest with JsonPathMatchers {
  override val server = new EmbeddedHttpServer(new DyServer)

  def givenSomePosts(posts: Post*) = DB autoCommit { implicit session =>
    posts.foreach(post => {
      sql"INSERT INTO posts VALUES (${post.id}, ${post.articleId}, ${post.title}, ${post.source}, ${LocalDateTime.now()}, ${post.url}, ${post.imgUrl})"
        .update.apply()
    })
  }
}
