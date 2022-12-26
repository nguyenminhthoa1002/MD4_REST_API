package ra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ra.jwt.JwtTokenProvider;
import ra.model.entity.ERole;
import ra.model.entity.Roles;
import ra.model.entity.Users;
import ra.model.service.IRoleService;
import ra.model.service.IUserService;
import ra.payload.request.LoginRequest;
import ra.payload.request.RegisterRequest;
import ra.payload.respone.JwtResponse;
//import ra.payload.response.JwtResponse;
import ra.payload.respone.MessageResponse;
import ra.security.CustomUserDetails;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("api/v1/user")
public class UserController {
    @Autowired
    private IUserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider tokenProvider;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private PasswordEncoder encoder;

    @GetMapping
    public List<Users> getAllUser(){
        return userService.findAll();
    }

    @GetMapping("/{userId}")
    public Users getUserById(@PathVariable("userId") int userId){
        return (Users) userService.findById(userId);
    }

    @PostMapping
    public Users createUser(@RequestBody Users users){
        return (Users) userService.saveOrUpdate(users);
    }

    @PutMapping("/{userId}")
    public Users updateUser(@PathVariable("userId") int userId, @RequestBody Users users){
        Users userUpdate = (Users) userService.findById(userId);
        userUpdate.setUserName(users.getUserName());
        userUpdate.setPassword(users.getPassword());
        userUpdate.setLastName(users.getLastName());
        userUpdate.setFirstName(users.getFirstName());
        userUpdate.setEmail(users.getEmail());
        userUpdate.setPhone(users.getPhone());
        userUpdate.setAddress((users.getAddress()));
        userUpdate.setUserCreateDate(users.getUserCreateDate());
        userUpdate.setUserStatus(users.isUserStatus());
        return (Users) userService.saveOrUpdate(userUpdate);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable("userId") int userId){
        userService.delete(userId);
    }

    @GetMapping("search")
    public List<Users> searchUserByName(@RequestParam("searchName") String searchName){
        return userService.searchUser(searchName);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest signupRequest) throws Throwable {
        if (userService.existsByUserName(signupRequest.getUserName())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Usermame is already"));
        }
        if (userService.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already"));
        }
        Users user = new Users();
        user.setUserName(signupRequest.getUserName());
        user.setPassword(encoder.encode(signupRequest.getPassword()));
        user.setLastName(signupRequest.getLastName());
        user.setFirstName(signupRequest.getFirstName());
        user.setEmail(signupRequest.getEmail());
        user.setPhone(signupRequest.getPhone());
        user.setAddress(signupRequest.getAddress());
        user.setUserStatus(true);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date dateNow = new Date();
        String strNow = sdf.format(dateNow);
        try {
            user.setUserCreateDate(sdf.parse(strNow));
        }catch (Exception ex){
            ex.printStackTrace();
        }
        Set<String> strRoles = signupRequest.getListRoles();
        Set<Roles> listRoles = new HashSet<>();
        if (strRoles==null){
            //User quyen mac dinh
            Roles userRole = (Roles) roleService.findByRoleName(ERole.ROLE_USER).orElseThrow(()->new RuntimeException("Error: Role is not found"));
            listRoles.add(userRole);
        }
//        else {
//            strRoles.forEach(role->{
//                switch (role){
//                    case "admin":
//                        Roles adminRole = roleService.findByRoleName(ERole.ROLE_ADMIN)
//                                .orElseThrow(()->new RuntimeException("Error: Role is not found"));
//                        listRoles.add(adminRole);
//                    case "moderator":
//                        Roles modRole = roleService.findByRoleName(ERole.ROLE_MODERATOR)
//                                .orElseThrow(()->new RuntimeException("Error: Role is not found"));
//                        listRoles.add(modRole);
//                    case "user":
//                        Roles userRole = roleService.findByRoleName(ERole.ROLE_USER)
//                                .orElseThrow(()->new RuntimeException("Error: Role is not found"));
//                        listRoles.add(userRole);
//                }
//            });
//        }
        user.setListRoles(listRoles);
        userService.saveOrUpdate(user);
        return ResponseEntity.ok(new MessageResponse("User registered successfully"));
    }

    @PostMapping("/signin")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUserName(),loginRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        CustomUserDetails customUserDetail = (CustomUserDetails) authentication.getPrincipal();
        //Sinh JWT tra ve client
        String jwt = tokenProvider.generateToken(customUserDetail);
        //Lay cac quyen cua user
        List<String> listRoles = customUserDetail.getAuthorities().stream()
                .map(item->item.getAuthority()).collect(Collectors.toList());
        return ResponseEntity.ok(new JwtResponse(jwt,customUserDetail.getUsername(), customUserDetail.getLastName(), customUserDetail.getFirstName(),
                customUserDetail.getEmail(), customUserDetail.getPhone(), customUserDetail.getAddress(), listRoles));
    }
}
