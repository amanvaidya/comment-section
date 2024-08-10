package org.example.service

import org.example.entity.CommentReplies
import org.example.repository.CommentRepliesRepository
import org.example.type.Type
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CommentRepliesService {

    companion object {
        private val log = LoggerFactory.getLogger(CommentRepliesService::class.java)
    }

    @Autowired
    private lateinit var commentRepliesRepository: CommentRepliesRepository

    @Transactional
    fun addReply(commentId: Long, userId: Long, content: String): CommentReplies {
        log.info("inside addReply")
        val reply = CommentReplies(commentId = commentId, userId = userId, content = content)
        return commentRepliesRepository.save(reply)
    }

    @Transactional
    fun getRepliesForComment(commentId: Long): List<CommentReplies> {
        log.info("inside getRepliesForComment")
        return commentRepliesRepository.findAll().filter { it.commentId == commentId }
    }

    @Transactional
    fun likeDislikeCommentReplies(replyId: Long, type: Type): CommentReplies {
        log.info("inside likeDislikeComment")
        val comment = commentRepliesRepository.findByIdOrNull(replyId)?:throw Exception("Comment no longer exist")
        when(type){
            Type.LIKE -> comment.likesCount+=1
            Type.DISLIKE -> comment.dislikesCount+=1
        }
        val response = commentRepliesRepository.save(comment)
        return response
    }
}