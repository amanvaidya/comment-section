package service

import org.example.entity.Comment
import org.example.model.PostCommentDto
import org.example.repository.CommentRepository
import org.example.service.CommentService
import org.example.type.Type
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*


@ExtendWith(MockitoExtension::class)
class CommentServiceTest {

    @Mock
    private lateinit var commentRepository: CommentRepository

    @InjectMocks
    private lateinit var commentService: CommentService

    private lateinit var postCommentDto: PostCommentDto
    private lateinit var comment: Comment

    @BeforeEach
    fun setup() {
        postCommentDto = PostCommentDto(postId = 1L, userId = 1L, content = "Test Comment")
        comment = Comment(postId = postCommentDto.postId, userId = postCommentDto.userId, content = postCommentDto.content)
        comment.id = 1L  // Set the comment ID to a non-null value
    }

    @Test
    fun `should add a comment`() {
        `when`(commentRepository.save(any(Comment::class.java))).thenReturn(comment)

        val result = commentService.addComment(postCommentDto)

        assertNotNull(result)
        assertEquals(postCommentDto.postId, result.postId)
        assertEquals(postCommentDto.userId, result.userId)
        assertEquals(postCommentDto.content, result.content)
        verify(commentRepository, times(1)).save(any(Comment::class.java))
    }

    @Test
    fun `should get first level comments`() {
        val comments = listOf(comment)
        `when`(commentRepository.findByPostId(postCommentDto.postId)).thenReturn(comments)

        val result = commentService.getFirstLevelComments(postCommentDto.postId)

        assertNotNull(result)
        assertEquals(1, result.size)
        assertEquals(comment, result[0])
        verify(commentRepository, times(1)).findByPostId(postCommentDto.postId)
    }

    @Test
    fun `should like a comment`() {
        comment.likesCount = 0
        `when`(commentRepository.findById(comment.id?:0)).thenReturn(Optional.of(comment))

        val result = commentService.likeDislikeComment(comment.id?:0, Type.LIKE)

        assertNotNull(result)
        assertEquals(1, result.likesCount)
        verify(commentRepository, times(1)).findById(comment.id?:0)
    }

    @Test
    fun `should dislike a comment`() {
        comment.dislikesCount = 0
        `when`(commentRepository.findById(comment.id?:0)).thenReturn(Optional.of(comment))

        val result = commentService.likeDislikeComment(comment.id?:0, Type.DISLIKE)

        assertNotNull(result)
        assertEquals(1, result.dislikesCount)
        verify(commentRepository, times(1)).findById(comment.id?:0)
    }

    @Test
    fun `should throw exception when liking a non-existent comment`() {
        `when`(commentRepository.findById(comment.id?:0)).thenReturn(Optional.empty())

        val exception = assertThrows(Exception::class.java) {
            commentService.likeDislikeComment(comment.id?:0, Type.LIKE)
        }

        assertEquals("Comment no longer exist", exception.message)
        verify(commentRepository, times(1)).findById(comment.id?:0)
    }
}