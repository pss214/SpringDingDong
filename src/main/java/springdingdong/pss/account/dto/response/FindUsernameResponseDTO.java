package springdingdong.pss.account.dto.response;

import springdingdong.pss.account.domain.Account;

public record FindUsernameResponseDTO(
        String username,
        String name,
        String phone
) {
    public FindUsernameResponseDTO(Account account) {
        this(account.getUsername(), account.getName(), account.getPhone());
    }
}
