package org.example.entity

import javax.persistence.*

@Entity
@Table(name = "comment_replies")
data class CommentReplies(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "comment_id", nullable = false)
    val commentId: Long,

    @Column(nullable = false)
    val userId: Long,

    @Column(nullable = false, length = 1000)
    val content: String,

    @Column(name = "likes_count")
    var likesCount: Int = 0,

    @Column(name = "dislikes_count")
    var dislikesCount: Int = 0
)

