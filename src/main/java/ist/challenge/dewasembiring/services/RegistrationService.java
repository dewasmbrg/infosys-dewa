package ist.challenge.dewasembiring.services;

        import ist.challenge.dewasembiring.dto.request.RegistrationRequest;
        import ist.challenge.dewasembiring.enums.UserRole;
        import ist.challenge.dewasembiring.utils.UsernameValidator;
        import lombok.AllArgsConstructor;
        import org.springframework.security.core.userdetails.User;
        import org.springframework.stereotype.Service;
        import org.springframework.transaction.annotation.Transactional;

        import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final UserService userService;
    private final UsernameValidator usernameValidator;

    public String register(RegistrationRequest request) {
        boolean isValidEmail = usernameValidator.
                checkUsername(request.getUsername());

        if (!isValidEmail) {
            throw new IllegalStateException("username not valid");
        }

//        String token = userService.signUpUser(
//                new User(
//                        request.getUsername(),
//                        request.getPassword(),
//                        UserRole.USER
//
//                )
//        );

        return "Registration Success";
    }
}