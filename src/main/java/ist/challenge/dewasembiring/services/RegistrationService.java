package ist.challenge.dewasembiring.services;

import ist.challenge.dewasembiring.dto.request.RegistrationRequest;
import ist.challenge.dewasembiring.enums.UserRole;
import ist.challenge.dewasembiring.utils.UsernameValidator;
import lombok.AllArgsConstructor;
import ist.challenge.dewasembiring.models.User;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final UserService userService;
    private final UsernameValidator usernameValidator;

    public String register(RegistrationRequest request) {
        boolean isValidEmail = usernameValidator.
                test(request.getUsername());

        if (!isValidEmail) {
            throw new IllegalStateException("username not valid");
        }

        return userService.signUpUser((new User(
                request.getUsername(),
                request.getPassword(),
                UserRole.USER
        )));
    }
}