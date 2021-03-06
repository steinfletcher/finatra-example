package com.tapatron.web

import com.google.inject.Inject
import com.tapatron.service.PostService
import com.twitter.finatra.http.Controller
import com.twitter.finatra.request.{QueryParam, RouteParam}
import com.twitter.finatra.validation.{Max, Min}

class PostController @Inject()(postService: PostService) extends Controller {
  get("/post") { request: FindAllPostsRequest =>
    postService.findAll(request.limit, request.page, request.q.map(_.split(",")))
  }

  get("/post/:id") { request: FindPostRequest =>
    postService.findById(request.id)
  }
}

case class FindAllPostsRequest(@Max(50) @QueryParam limit: Int = 10,
                               @Min(0) @QueryParam page: Int = 0,
                               @QueryParam q: Option[String] = None)

case class FindPostRequest(@RouteParam id: Long)
