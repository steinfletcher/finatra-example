package com.tapatron.repository

import java.time.LocalDateTime.now

import com.tapatron.Fixtures.aPost
import com.tapatron.data.Post
import com.twitter.finagle.http.Status

class PostTest extends IntegrationTest {

  override def beforeEach(): Unit = {
    clearData()
  }

  "PostResource#fetchPost" should {
    "return a specific post" in {
      val postId = generateId()

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

      val response = server.httpGet(path = s"/post/${postId.get}",
        andExpect = Status.Ok).getContentString()

      jsonPath("$.source", response) should containItems(text("reddit"))
      jsonPath("$.title", response) should containItems(text("Some post"))
      jsonPath("$.article_id", response) should containItems(text("8rwDR5235qwg"))
      jsonPath("$.id", response) should containItems(id(postId))
      jsonPath("$.url", response) should containItems(text("http://url"))
      jsonPath("$.img_url", response) should containItems(text("http://imgUrl"))
    }

    "return NotFound if post id does not exist" in {
      server.httpGet(path = s"/post/61616161", andExpect = Status.NotFound)
    }

    "return BadRequest if post id invalid" in {
      val response = server.httpGet(path = s"/post/wrongtype", andExpect = Status.BadRequest)
        .getContentString()

      jsonPath("$.errors[*]", response) should containItems(text("id: 'wrongtype' is not a valid Long"))
    }
  }

  "PostResource#fetchPosts" should {
    "return the most recent posts" in {
      val post1Id = generateId()
      val post2Id = generateId()
      val post3Id = generateId()

      givenSomePosts(
        aPost(post1Id).copy(added = now().minusHours(10)),
        aPost(post2Id).copy(added = now().minusHours(5)),
        aPost(post3Id).copy(added = now().minusHours(1))
      )

      val response = server.httpGet(path = "/post?limit=2", andExpect = Status.Ok).getContentString()

      jsonPath("$[*].id", response) should containItems(id(post3Id), id(post2Id))
    }
  }

  "PostResource#fetchPosts" should {
    "support paging" in {
      val post1Id = generateId()
      val post2Id = generateId()
      val post3Id = generateId()
      val post4Id = generateId()

      givenSomePosts(
        aPost(post1Id).copy(title = "post1", added = now().minusMinutes(10)),
        aPost(post2Id).copy(title = "post2", added = now().minusMinutes(8)),
        aPost(post3Id).copy(title = "post3", added = now().minusMinutes(6)),
        aPost(post4Id).copy(title = "post4", added = now().minusMinutes(4))
      )

      val page1 = server.httpGet(path = "/post?limit=2&page=0", andExpect = Status.Ok).getContentString()
      jsonPath("$[*].id", page1) should containItems(id(post4Id), id(post3Id))

      val page2 = server.httpGet(path = "/post?limit=2&page=1", andExpect = Status.Ok).getContentString()
      jsonPath("$[*].id", page2) should containItems(id(post2Id), id(post1Id))
    }
  }

  "PostResource#fetchPosts" should {
    "support category search" in {

      givenSomePosts(
        aPost(generateId()).copy(title = "food and tech", categories = Some(Seq("FOOD", "TECH"))),
        aPost(generateId()).copy(title = "food", categories = Some(Seq("FOOD"))),
        aPost(generateId()).copy(title = "sport", categories = Some(Seq("SPORT"))),
        aPost(generateId()).copy(title = "news", categories = Some(Seq("NEWS")))
      )

      val response = server.httpGet(path = "/post?q=FOOD", andExpect = Status.Ok).getContentString()
      jsonPath("$[*].title", response) should containItems(text("food and tech"), text("food"))
    }
  }

}
