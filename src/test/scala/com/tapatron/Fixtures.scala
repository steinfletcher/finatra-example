package com.tapatron

import java.time.LocalDateTime
import java.util.UUID

import com.tapatron.data.Post

object Fixtures {

  def aPost(id: Option[Long]): Post = Post(
    id = id,
    articleId = UUID.randomUUID().toString,
    title = "Some post",
    source = "reddit",
    url = "http://url",
    added = LocalDateTime.now(),
    imgUrl = "http://imgUrl",
    categories = None
  )
}
