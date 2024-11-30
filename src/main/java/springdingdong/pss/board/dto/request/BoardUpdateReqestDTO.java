package springdingdong.pss.board.dto.request;

public record BoardUpdateReqestDTO(
        Long boardId,
        String subject,
        String content
) {
}
