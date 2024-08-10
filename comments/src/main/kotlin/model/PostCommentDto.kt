package org.example.model

data class PostCommentDto(
    var postId: Long,
    var userId: Long,
    var content: String
)
