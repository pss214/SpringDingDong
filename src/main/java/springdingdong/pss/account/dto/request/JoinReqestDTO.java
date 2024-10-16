package springdingdong.pss.account.dto.request;

public record JoinReqestDTO(
        String username,
        String password,
        String name,
        String phone
) {
}
