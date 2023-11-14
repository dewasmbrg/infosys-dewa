package ist.challenge.dewasembiring.services;

import ist.challenge.dewasembiring.dto.request.LoginRequest;
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
import java.util.Optional;

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
            BaseResponse response = new BaseResponse(
                    LocalDateTime.now(),
                    HttpStatus.CONFLICT.value(),
                    true,
                    "Username sudah terpakai.",
                    "/api/v1/registration"
            );

            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }

        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        userRepository.save(user);

        BaseResponse response = new BaseResponse(
                LocalDateTime.now(),
                HttpStatus.CREATED.value(),
                false,
                "Akun berhasil dibuat.",
                "/api/v1/registration"
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    public ResponseEntity<BaseResponse> editUser(LoginRequest request, User updatedUser) {
        Optional<User> optionalUser = userRepository.findByUsername(request.getUsername());

        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();

            existingUser.setUsername(updatedUser.getUsername());

            userRepository.save(existingUser);

            BaseResponse response = new BaseResponse(
                    LocalDateTime.now(),
                    HttpStatus.OK.value(),
                    false,
                    "Username berhasil diubah.",
                    "/user/edit/" + request.getUsername()
            );

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            BaseResponse response = new BaseResponse(
                    LocalDateTime.now(),
                    HttpStatus.NOT_FOUND.value(),
                    true,
                    "Username tidak ditemukan.",
                    "/user/edit/" + request.getUsername()
            );

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }


    public int enableUser(String username) {
        return userRepository.enableUser(username);
    }
}