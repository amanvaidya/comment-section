package org.example.controller

import org.example.entity.CommentReplies
import org.example.model.PostReplyDto
import org.example.service.CommentRepliesService
import org.example.type.Type
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/replies")
class CommentRepliesController {

    companion object {
        private val log = LoggerFactory.getLogger(CommentController::class.java)
    }

    @Autowired
    private lateinit var commentRepliesService: CommentRepliesService

    @PostMapping("/{commentId}/reply")
    fun addReply(@PathVariable commentId: Long, @RequestBody postReplyDto: PostReplyDto): ResponseEntity<CommentReplies> {
        val reply = commentRepliesService.addReply(commentId, postReplyDto.userId, postReplyDto.content)
        return ResponseEntity.ok(reply)
    }

    @GetMapping("/{commentId}/reply")
    fun getRepliesForComment(@PathVariable commentId: Long): ResponseEntity<List<CommentReplies>> {
        val replies = commentRepliesService.getRepliesForComment(commentId)
        return ResponseEntity.ok(replies)
    }

    @PostMapping("/{replyId}")
    fun likeDislikeComment(@PathVariable replyId: Long, @RequestParam type: Type): CommentReplies {
        return commentRepliesService.likeDislikeCommentReplies(replyId, type)
    }
}