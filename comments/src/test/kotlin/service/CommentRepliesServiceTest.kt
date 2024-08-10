package service

import org.example.entity.CommentReplies
import org.example.repository.CommentRepliesRepository
import org.example.service.CommentRepliesService
import org.example.type.Type
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import java.util.Optional

@ExtendWith(MockitoExtension::class)
class CommentRepliesServiceTest {

    @Mock
    private lateinit var commentRepliesRepository: CommentRepliesRepository

    @InjectMocks
    private lateinit var commentRepliesService: CommentRepliesService

    private lateinit var commentReplies: CommentReplies

    @BeforeEach
    fun setup() {
        commentReplies = CommentReplies(commentId = 1L, userId = 1L, content = "Test Reply")
        commentReplies.id = 1L  // Set the reply ID to a non-null value
    }

    @Test
    fun `should add a reply`() {
        `when`(commentRepliesRepository.save(any(CommentReplies::class.java))).thenReturn(commentReplies)

        val result = commentRepliesService.addReply(commentReplies.commentId, commentReplies.userId, commentReplies.content)

        assertNotNull(result)
        assertEquals(commentReplies.commentId, result.commentId)
        assertEquals(commentReplies.userId, result.userId)
        assertEquals(commentReplies.content, result.content)
        verify(commentRepliesRepository, times(1)).save(any(CommentReplies::class.java))
    }

    @Test
    fun `should get replies for a comment`() {
        val replies = listOf(commentReplies)
        `when`(commentRepliesRepository.findAll()).thenReturn(replies)

        val result = commentRepliesService.getRepliesForComment(commentReplies.commentId)

        assertNotNull(result)
        assertEquals(1, result.size)
        assertEquals(commentReplies, result[0])
        verify(commentRepliesRepository, times(1)).findAll()
    }

    @Test
    fun `should like a comment reply`() {
        commentReplies.likesCount = 0
        `when`(commentRepliesRepository.findById(commentReplies.id?:0)).thenReturn(Optional.of(commentReplies))
        `when`(commentRepliesRepository.save(any(CommentReplies::class.java))).thenReturn(commentReplies)

        val result = commentRepliesService.likeDislikeCommentReplies(commentReplies.id?:0, Type.LIKE)

        assertNotNull(result)
        assertEquals(1, result.likesCount)
        verify(commentRepliesRepository, times(1)).findById(commentReplies.id?:0)
        verify(commentRepliesRepository, times(1)).save(commentReplies)
    }

    @Test
    fun `should dislike a comment reply`() {
        commentReplies.dislikesCount = 0
        `when`(commentRepliesRepository.findById(commentReplies.id?:0)).thenReturn(Optional.of(commentReplies))
        `when`(commentRepliesRepository.save(any(CommentReplies::class.java))).thenReturn(commentReplies)

        val result = commentRepliesService.likeDislikeCommentReplies(commentReplies.id?:0, Type.DISLIKE)

        assertNotNull(result)
        assertEquals(1, result.dislikesCount)
        verify(commentRepliesRepository, times(1)).findById(commentReplies.id?:0)
        verify(commentRepliesRepository, times(1)).save(commentReplies)
    }

    @Test
    fun `should throw exception when liking a non-existent comment reply`() {
        `when`(commentRepliesRepository.findById(commentReplies.id?:0)).thenReturn(Optional.empty())

        val exception = assertThrows(Exception::class.java) {
            commentRepliesService.likeDislikeCommentReplies(commentReplies.id?:0, Type.LIKE)
        }

        assertEquals("Comment no longer exist", exception.message)
        verify(commentRepliesRepository, times(1)).findById(commentReplies.id?:0)
    }
}