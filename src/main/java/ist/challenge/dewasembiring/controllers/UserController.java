package ist.challenge.dewasembiring.controllers;

        import ist.challenge.dewasembiring.dto.request.RegistrationRequest;
        import ist.challenge.dewasembiring.dto.response.BaseResponse;
        import ist.challenge.dewasembiring.models.User;
        import ist.challenge.dewasembiring.services.RegistrationService;
        import lombok.AllArgsConstructor;
        import org.springframework.http.ResponseEntity;
        import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/user")
@AllArgsConstructor
public class UserController {

    private final RegistrationService registrationService;

    @PostMapping("/registration")
    public ResponseEntity<BaseResponse> registerUser(@RequestBody RegistrationRequest request) {
        return registrationService.register(request);
    }

    @PostMapping("/login")
    public ResponseEntity<BaseResponse> loginUser(@RequestBody RegistrationRequest request) {
        return registrationService.register(request);
    }
}
