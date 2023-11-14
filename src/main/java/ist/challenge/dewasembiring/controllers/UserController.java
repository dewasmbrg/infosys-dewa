package ist.challenge.dewasembiring.controllers;

        import io.swagger.annotations.ApiOperation;
        import ist.challenge.dewasembiring.dto.request.LoginRequest;
        import ist.challenge.dewasembiring.dto.request.RegistrationRequest;
        import ist.challenge.dewasembiring.dto.request.UserUpdateRequest;
        import ist.challenge.dewasembiring.dto.response.BaseResponse;
        import ist.challenge.dewasembiring.dto.response.BaseResponseData;
        import ist.challenge.dewasembiring.models.User;
        import ist.challenge.dewasembiring.services.LoginService;
        import ist.challenge.dewasembiring.services.RegistrationService;
        import ist.challenge.dewasembiring.services.UserService;
        import lombok.AllArgsConstructor;
        import org.springframework.http.ResponseEntity;
        import org.springframework.web.bind.annotation.*;

        import java.util.List;

@RestController
@RequestMapping(path = "/user")
@AllArgsConstructor
public class UserController {

    private final RegistrationService registrationService;
    private final LoginService loginService;
    private final UserService userService;

    @ApiOperation("User Registration")
    @PostMapping("/registration")
    public ResponseEntity<BaseResponse> registerUser(@RequestBody RegistrationRequest request) {
        return registrationService.register(request);
    }

    @ApiOperation("User Login")
    @PostMapping("/login")
    public ResponseEntity<BaseResponse> loginUser(@RequestBody LoginRequest request) {
        return loginService.login(request);
    }

    @ApiOperation("User Lihat All")
    @GetMapping("/lihat/all")
    public ResponseEntity<BaseResponseData<List<User>>> lihatUserAll() {
        return userService.lihatUserAll();
    }

    @ApiOperation("User Lihat Single")
    @PostMapping("/lihat/single")
    public ResponseEntity<BaseResponseData<User>> lihatUserSingle(@RequestBody LoginRequest request) {
        return userService.lihatUserSingle(request);
    }

    @ApiOperation("User Edit")
    @PutMapping("/edit/{username}")
    public ResponseEntity<BaseResponse> editUser(@PathVariable String username, @RequestBody UserUpdateRequest request) {
        return userService.editUser(username, request);
    }
}
