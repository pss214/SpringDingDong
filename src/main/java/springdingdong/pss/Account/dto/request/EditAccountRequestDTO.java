package springdingdong.pss.Account.dto.request;

public record EditAccountRequestDTO(
        String username,
        String password,
        String name,
        String phone
) {
}
