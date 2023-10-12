package eazym.FabulaUltimaCompanion_BackEnd.security.user;

import eazym.FabulaUltimaCompanion_BackEnd.exceptions.BadRequestException;
import eazym.FabulaUltimaCompanion_BackEnd.exceptions.NotFoundException;
import eazym.FabulaUltimaCompanion_BackEnd.security.payloads.RegisterRequestPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    //CREATE A NEW USER
    public User createUser(RegisterRequestPayload registerBody){

        userRepository.findByEmail(registerBody.getEmail())
                .ifPresent(user -> {
                    throw new BadRequestException("Email already used.");
                });

        User newUser = User.builder()
                .firstname(registerBody.getFirstname())
                .lastname(registerBody.getLastname())
                .email(registerBody.getEmail())
                .password(registerBody.getPassword())
                .role(Role.USER)
                .build();

        return userRepository.save(newUser);

    }

    //GET ALL USERS AS SIMPLE LIST
    public List<User> findAllUsers(){

        List<User> userList = userRepository.findAll();

        for(User user : userList){
            if (user.getRole().equals(Role.SUPER_ADMIN)) {
                userList.remove(user);
                break;
            }
        }

        return userList;

    }

    //GET ALL USERS AS PAGEABLE and SORT
    public Page<User> findAllUsers(int pages, int size, String sortBy){

        Pageable pageable = PageRequest.of(pages, size, Sort.by(sortBy));

        return userRepository.findAll(pageable);

    }

    //GET USER BY ID
    public User findById(UUID id) throws NotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    //GET USER BY EMAIL
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found: " + email));
    }

    //DELETE A USER BY ID
    public void findByIdAndDelete(UUID id) throws NotFoundException {

        User foundUser = this.findById(id);

        userRepository.delete(foundUser);

    }

    //GET CURRENT USER
    public User getCurrentUser(){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String currentUserName = auth.getName();

        User foundUser = userRepository.findByEmail(currentUserName)
                .orElseThrow(() -> new NotFoundException("Mail not found: " + currentUserName));

        System.out.println("User found: " + foundUser.getLastname() + " " + foundUser.getFirstname());

        return foundUser;

    }

    //MODIFY CURRENT USER
    public User updateCurrentUserInfo(UserInfoPayload infoBody) throws NotFoundException{

        User foundUser = this.getCurrentUser();

        foundUser.setFirstname(infoBody.getFirstname());
        foundUser.setLastname(infoBody.getLastname());
        foundUser.setEmail(infoBody.getEmail());
        foundUser.setPassword(infoBody.getPassword());

        return userRepository.save(foundUser);

    }

    //DELETE CURRENT USER
    public void deleteCurrentUser() throws NotFoundException {

        User currentUser = this.getCurrentUser();

        userRepository.delete(currentUser);
    }

}
