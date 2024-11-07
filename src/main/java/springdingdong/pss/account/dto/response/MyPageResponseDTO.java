package springdingdong.pss.account.dto.response;

import springdingdong.pss.account.domain.Account;

public record MyPageResponseDTO(
        String username,
        String name,
        String phone
) {
    public MyPageResponseDTO(Account account){
        this(account.getUsername(), account.getName(), account.getPhone());
    }
}
