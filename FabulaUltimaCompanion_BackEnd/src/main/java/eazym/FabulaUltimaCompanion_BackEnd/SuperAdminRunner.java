package eazym.FabulaUltimaCompanion_BackEnd;

import eazym.FabulaUltimaCompanion_BackEnd.security.user.Role;
import eazym.FabulaUltimaCompanion_BackEnd.security.user.User;
import eazym.FabulaUltimaCompanion_BackEnd.security.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.util.Properties;

@Component
public class SuperAdminRunner implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder bcrypt;

    @Override
    public void run(String... args) throws Exception {

        // FETCHING env.properties FILE
        Properties properties = new Properties();
        FileInputStream fileInputStream = new FileInputStream("env.properties");
        properties.load(fileInputStream);
        fileInputStream.close();

        // SEARCH FOR ADMIN IN DATABASE
        boolean SuperAdminMatch = false;

        for(User user : userRepository.findAll()) {
            //IF ADMIN IS FOUND NOTHING WILL HAPPEN
            if (user.getRole().equals(Role.SUPER_ADMIN)) {
                SuperAdminMatch = true;
                break;
            }
        }

        //IF NO ADMIN FOUND, ONE IS CREATED
        if(!SuperAdminMatch) {
            User defaultSuperAdmin = User.builder()
                    .firstname("Super_Admin")
                    .lastname("Super_Admin")
                    .email("superadmin@admin.com")
                    .password(bcrypt.encode(properties.getProperty("ps_defaultAdmin")))
                    .role(Role.SUPER_ADMIN)
                    .build();

            userRepository.save(defaultSuperAdmin);
        }

    }

}
