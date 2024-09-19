package springdingdong.pss.Account.dto.request;

public record JoinReqestDTO(
        String username,
        String password,
        String name,
        String phone
) {
}
