package ist.challenge.dewasembiring.services;

import ist.challenge.dewasembiring.dto.request.RegistrationRequest;
import ist.challenge.dewasembiring.dto.response.BaseResponse;
import ist.challenge.dewasembiring.enums.UserRole;
import ist.challenge.dewasembiring.utils.UsernameValidator;
import ist.challenge.dewasembiring.models.User;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final UserService userService;
    private final UsernameValidator usernameValidator;

    public ResponseEntity<BaseResponse> register(RegistrationRequest request) {
        boolean isValidUsername = usernameValidator.test(request.getUsername());

        if (!isValidUsername) {
            BaseResponse response = new BaseResponse(
                    LocalDateTime.now(),
                    HttpStatus.NOT_ACCEPTABLE.value(),
                    true,
                    "Username tidak valid.",
                    "/api/v1/registration"
            );

            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(response);
        }

        return userService.signUpUser(new User(
                request.getUsername(),
                request.getPassword(),
                UserRole.USER
        ));
    }
}
