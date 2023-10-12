package eazym.FabulaUltimaCompanion_BackEnd.security.payloads;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestPayload {

    private String email;
    private String password;

}
