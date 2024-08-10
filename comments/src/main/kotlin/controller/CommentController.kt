package org.example.controller

import org.example.entity.Comment
import org.example.model.PostCommentDto
import org.example.service.CommentService
import org.example.type.Type
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/comments")
class CommentController {

    companion object {
        private val log = LoggerFactory.getLogger(CommentController::class.java)
    }

    @Autowired
    private lateinit var commentService: CommentService

    @PostMapping
    fun addComment(@RequestBody postCommentDto: PostCommentDto): Comment {
        return commentService.addComment(postCommentDto)
    }

    @GetMapping("/first-level")
    fun getFirstLevelComments(@RequestParam postId: Long): List<Comment> {
        return commentService.getFirstLevelComments(postId)
    }

    @PostMapping("/{commentId}")
    fun likeDislikeComment(@PathVariable commentId: Long, @RequestParam type: Type): Comment {
        return commentService.likeDislikeComment(commentId, type)
    }
}