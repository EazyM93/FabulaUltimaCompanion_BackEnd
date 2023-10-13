package eazym.FabulaUltimaCompanion_BackEnd.security.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    //GET ALL USERS AS SIMPLE LIST
    @GetMapping
    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN')")
    public List<User> findAllUsers(){
        return userService.findAllUsers();
    }

    //GET ALL USERS AS PAGEABLE and SORT
    @GetMapping("/pageableUsers")
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    public Page<User> findAllUsersPageable(
            @RequestParam(defaultValue = "0") int pages,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "lastname") String sortBy
    ){
        return userService.findAllUsers(pages, size, sortBy);
    }

    // GET USER BY ID
    @GetMapping("/{idUser}")
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    public User findById(
            @PathVariable UUID idUser
    ) throws Exception {
        return userService.findById(idUser);
    }

    // DELETE USER BY ID
    @DeleteMapping("/{idUser}")
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(
            @PathVariable UUID idUser
    ) {
        userService.findByIdAndDelete(idUser);
    }

}
