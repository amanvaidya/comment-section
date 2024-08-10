package org.example.service

import org.example.entity.Comment
import org.example.model.PostCommentDto
import org.example.repository.CommentRepository
import org.example.type.Type
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam


@Service
class CommentService {

    companion object {
        private val log = LoggerFactory.getLogger(CommentService::class.java)
    }

    @Autowired
    private lateinit var commentRepository: CommentRepository


    @Transactional
    fun addComment(postCommentDto: PostCommentDto): Comment {
        log.info("inside addComment")
        val comment = Comment(postId = postCommentDto.postId, userId = postCommentDto.userId, content = postCommentDto.content)
        return commentRepository.save(comment)
    }

    fun getFirstLevelComments(postId: Long): List<Comment> {
        log.info("inside getFirstLevelComments")
        return commentRepository.findByPostId(postId)
    }

    @Transactional
    fun likeDislikeComment(commentId: Long, type: Type): Comment{
        log.info("inside likeDislikeComment")
        var comment = commentRepository.findByIdOrNull(commentId)?:throw Exception("Comment no longer exist")
        when(type){
            Type.LIKE -> comment.likesCount+=1
            Type.DISLIKE -> comment.dislikesCount+=1
        }
        return comment
    }
}