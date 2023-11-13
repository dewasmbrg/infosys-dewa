package ist.challenge.dewasembiring.controllers;

        import ist.challenge.dewasembiring.dto.request.RegistrationRequest;
        import ist.challenge.dewasembiring.services.RegistrationService;
        import lombok.AllArgsConstructor;
        import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/registration")
@AllArgsConstructor
public class UserController {

    private final RegistrationService registrationService;

    @PostMapping
    public String register(@RequestBody RegistrationRequest request) {
        return registrationService.register(request);
    }

}
