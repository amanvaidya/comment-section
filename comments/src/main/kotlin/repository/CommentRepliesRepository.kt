package org.example.repository

import org.example.entity.CommentReplies
import org.springframework.data.jpa.repository.JpaRepository

interface CommentRepliesRepository : JpaRepository<CommentReplies, Long> {
}