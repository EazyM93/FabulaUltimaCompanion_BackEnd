package eazym.FabulaUltimaCompanion_BackEnd.security.config;


import eazym.FabulaUltimaCompanion_BackEnd.exceptions.UnauthorizedException;
import eazym.FabulaUltimaCompanion_BackEnd.security.payloads.LoginAuthenticationResponse;
import eazym.FabulaUltimaCompanion_BackEnd.security.payloads.LoginRequestPayload;
import eazym.FabulaUltimaCompanion_BackEnd.security.payloads.RegisterRequestPayload;
import eazym.FabulaUltimaCompanion_BackEnd.security.user.User;
import eazym.FabulaUltimaCompanion_BackEnd.security.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    UserService userService;

    @Autowired
    JwtTools jwtTools;

    @Autowired
    PasswordEncoder bcrypt;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public User register(
            @RequestBody RegisterRequestPayload registerBody
    ){
        registerBody.setPassword(bcrypt.encode(registerBody.getPassword()));
        return userService.createUser(registerBody);
    }

    @PostMapping("/login")
    public LoginAuthenticationResponse login(
            @RequestBody LoginRequestPayload loginBody
    ){

        User user = userService.findByEmail(loginBody.getEmail());

        if(bcrypt.matches(loginBody.getPassword(), user.getPassword())){
            String token = jwtTools.createToken(user);
            return new LoginAuthenticationResponse(token);
        }else{
            throw new UnauthorizedException("Not valid credential!");
        }

    }

}
