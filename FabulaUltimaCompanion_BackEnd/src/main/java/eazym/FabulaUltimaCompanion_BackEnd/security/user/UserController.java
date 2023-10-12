package eazym.FabulaUltimaCompanion_BackEnd.security.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    //GET ALL USERS AS SIMPLE LIST
    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPER_ADMIN')")
    public List<User> findAllUsers(){
        return userService.findAllUsers();
    }

    //GET ALL USERS AS PAGEABLE and SORT
    @GetMapping("/pageableUsers")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPER_ADMIN')")
    public Page<User> findAllUsersPageable(
            @RequestParam(defaultValue = "0") int pages,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "lastname") String sortBy
    ){
        return userService.findAllUsers(pages, size, sortBy);
    }

}
