package eazym.FabulaUltimaCompanion_BackEnd.security.payloads;

import lombok.*;

@Getter @Setter
@AllArgsConstructor
public class RegisterRequestPayload {

    private String firstname;
    private String lastname;
    private String email;
    private String password;

}
