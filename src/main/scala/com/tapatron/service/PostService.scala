package com.tapatron.service

import java.util.UUID

import com.google.inject.{Inject, Singleton}
import com.tapatron.data.Post
import com.tapatron.repository.{CategoryRepository, PostRepository}

@Singleton
class PostService @Inject() (postRepository: PostRepository, categoryRepository: CategoryRepository) {

  def findAll(limit: Int, page: Int, categories: Option[Seq[String]]): Seq[Post] = {
    val offset = limit * page
    categories match {
      case Some(cat) => postRepository.selectAll(limit, offset, cat)
      case None => postRepository.selectAll(limit, offset)
    }
  }

  def findById(id: UUID): Option[Post] = {
    postRepository.selectById(id).map(p => {
      val categories = categoryRepository.selectAllByPost(p.id)
      p.copy(categories = Some(categories))
    })
  }

}
