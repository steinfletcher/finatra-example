package com.tapatron.repository

import java.time.LocalDateTime
import java.util.UUID
import java.util.UUID.randomUUID

import com.tapatron.Fixtures.aPost
import com.tapatron.data.Post
import com.twitter.finagle.http.Status

class PostTest extends IntegrationTest {

  "PostResource#fetchPost" should {
    "return a specific post" in {

      givenSomePosts(
        Post(
          id = UUID.fromString("1cbf7292-863d-43d4-8949-89b3d1590a6e"),
          articleId = "8rwDR5235qwg",
          title = "Some post",
          source = "reddit",
          url = "http://url",
          added = LocalDateTime.now(),
          imgUrl = "http://imgUrl",
          categories = None
        )
      )

      val response = server.httpGet(path = s"/post/1cbf7292-863d-43d4-8949-89b3d1590a6e",
        andExpect = Status.Ok).getContentString()

      jsonPath("$.source", response) should containItems(text("reddit"))
      jsonPath("$.title", response) should containItems(text("Some post"))
      jsonPath("$.article_id", response) should containItems(text("8rwDR5235qwg"))
      jsonPath("$.id", response) should containItems(text("1cbf7292-863d-43d4-8949-89b3d1590a6e"))
      jsonPath("$.url", response) should containItems(text("http://url"))
      jsonPath("$.img_url", response) should containItems(text("http://imgUrl"))
    }

    "return NotFound if post id does not exist" in {
      server.httpGet(path = s"/post/${randomUUID()}", andExpect = Status.NotFound)
    }

    "return BadRequest if post id invalid" in {
      val response = server.httpGet(path = s"/post/1234", andExpect = Status.BadRequest)
        .getContentString()

      jsonPath("$.errors[*]", response) should containItems(text("id: '1234' is not a valid UUID"))
    }
  }

  "PostResource#fetchPosts" should {
    "return the most recent posts" in {
      val post1Id = randomUUID()
      val post2Id = randomUUID()
      val post3Id = randomUUID()

      givenSomePosts(
        aPost().copy(id = post1Id),
        aPost().copy(id = post2Id),
        aPost().copy(id = post3Id)
      )

      val response = server.httpGet(path = "/post?limit=2", andExpect = Status.Ok).getContentString()

      jsonPath("$[*].id", response) should containItemsInOrder(text(post3Id.toString), text(post2Id.toString))
    }
  }

}
