package eazym.FabulaUltimaCompanion_BackEnd.security.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class LoginAuthenticationResponse {

    private String accessToken;

}
