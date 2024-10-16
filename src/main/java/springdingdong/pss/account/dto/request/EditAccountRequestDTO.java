package springdingdong.pss.account.dto.request;

public record EditAccountRequestDTO(
        String username,
        String password,
        String name,
        String phone
) {
}
