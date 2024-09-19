package springdingdong.pss.Account.dto.response;

import springdingdong.pss.Account.domain.Account;

public record FindUsernameResponseDTO(
        String username,
        String name,
        String phone
) {
    public FindUsernameResponseDTO(Account account) {
        this(account.getUsername(), account.getName(), account.getPhone());
    }
}
