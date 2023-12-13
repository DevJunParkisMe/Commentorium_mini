package project.pnu.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TokenResponse {
	private String token;

    public TokenResponse(String token) {
        this.token = token;
    }
}
