package springdingdong.pss.comment.dto.request;

public record CreateCommentRequestDTO(
        Long board_id,
        Long comment_id,
        String body
) {
}
