package org.example.entity

import javax.persistence.*
import kotlin.jvm.Transient

@Entity
@Table(name = "comments")
data class Comment(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false)
    val postId: Long,

    @Column(nullable = false)
    val userId: Long,

    @Column(nullable = false)
    val content: String,

    @Column(name = "likes_count")
    var likesCount: Int = 0,

    @Column(name = "dislikes_count")
    var dislikesCount: Int = 0,

    @Transient
    var replies: List<CommentReplies> = mutableListOf()
)
