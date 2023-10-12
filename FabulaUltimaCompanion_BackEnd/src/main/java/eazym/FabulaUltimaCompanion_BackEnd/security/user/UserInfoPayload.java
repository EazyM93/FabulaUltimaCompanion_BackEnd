package eazym.FabulaUltimaCompanion_BackEnd.security.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class UserInfoPayload {

    private String firstname;
    private String lastname;
    private String email;
    private String password;

}
