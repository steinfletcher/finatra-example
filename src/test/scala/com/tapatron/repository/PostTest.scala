package com.tapatron.repository

import java.time.LocalDateTime.now
import java.util.UUID.randomUUID

import com.tapatron.Fixtures.aPost
import com.tapatron.data.Post
import com.twitter.finagle.http.Status

class PostTest extends IntegrationTest {

  override def beforeEach(): Unit = {
    clearData()
  }

  "PostResource#fetchPost" should {
    "return a specific post" in {
      val postId = randomUUID()

      givenSomePosts(
        Post(
          id = postId,
          articleId = "8rwDR5235qwg",
          title = "Some post",
          source = "reddit",
          url = "http://url",
          added = now(),
          imgUrl = "http://imgUrl",
          categories = None
        )
      )

      val response = server.httpGet(path = s"/post/${postId}",
        andExpect = Status.Ok).getContentString()

      jsonPath("$.source", response) should containItems(text("reddit"))
      jsonPath("$.title", response) should containItems(text("Some post"))
      jsonPath("$.article_id", response) should containItems(text("8rwDR5235qwg"))
      jsonPath("$.id", response) should containItems(text(postId.toString))
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
        aPost().copy(id = post1Id, added = now().minusHours(10)),
        aPost().copy(id = post2Id, added = now().minusHours(5)),
        aPost().copy(id = post3Id, added = now().minusHours(1))
      )

      val response = server.httpGet(path = "/post?limit=2", andExpect = Status.Ok).getContentString()

      jsonPath("$[*].id", response) should containItems(text(post3Id.toString), text(post2Id.toString))
    }
  }

  "PostResource#fetchPosts" should {
    "support paging" in {
      val post1Id = randomUUID()
      val post2Id = randomUUID()
      val post3Id = randomUUID()
      val post4Id = randomUUID()

      givenSomePosts(
        aPost().copy(id = post1Id, title = "post1", added = now().minusMinutes(10)),
        aPost().copy(id = post2Id, title = "post2", added = now().minusMinutes(8)),
        aPost().copy(id = post3Id, title = "post3", added = now().minusMinutes(6)),
        aPost().copy(id = post4Id, title = "post4", added = now().minusMinutes(4))
      )

      val page1 = server.httpGet(path = "/post?limit=2&page=0", andExpect = Status.Ok).getContentString()
      jsonPath("$[*].id", page1) should containItems(text(post4Id.toString), text(post3Id.toString))

      val page2 = server.httpGet(path = "/post?limit=2&page=1", andExpect = Status.Ok).getContentString()
      jsonPath("$[*].id", page2) should containItems(text(post2Id.toString), text(post1Id.toString))
    }
  }

  "PostResource#fetchPosts" should {
    "support category search" in {

      givenSomePosts(
        aPost().copy(title = "food and tech", categories = Some(Seq("FOOD", "TECH"))),
        aPost().copy(title = "food", categories = Some(Seq("FOOD"))),
        aPost().copy(title = "sport", categories = Some(Seq("SPORT"))),
        aPost().copy(title = "news", categories = Some(Seq("NEWS")))
      )

      val response = server.httpGet(path = "/post?q=FOOD", andExpect = Status.Ok).getContentString()
      jsonPath("$[*].title", response) should containItems(text("food and tech"), text("food"))
    }
  }

}
