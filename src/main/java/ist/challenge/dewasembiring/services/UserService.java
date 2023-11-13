package ist.challenge.dewasembiring.services;

import ist.challenge.dewasembiring.dto.response.BaseResponse;
import ist.challenge.dewasembiring.repositories.UserRepository;
import lombok.AllArgsConstructor;
import ist.challenge.dewasembiring.models.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final static String USER_NOT_FOUND_MSG =
            "user with username %s not found";

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                String.format(USER_NOT_FOUND_MSG, username)));
    }

    public ResponseEntity<BaseResponse> signUpUser(User user) {
        boolean userExists = userRepository
                .findByUsername(user.getUsername())
                .isPresent();

        if (userExists) {
            throw new IllegalStateException("username already taken");
        }

        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        userRepository.save(user);

        BaseResponse response = new BaseResponse(
                LocalDateTime.now(),
                HttpStatus.CREATED.value(),
                false,
                "User created successfully",
                "/api/v1/registration"
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    public int enableUser(String username) {
        return userRepository.enableUser(username);
    }
}