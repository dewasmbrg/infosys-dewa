package ist.challenge.dewasembiring.services;

import ist.challenge.dewasembiring.dto.request.LoginRequest;
import ist.challenge.dewasembiring.dto.request.UserUpdateRequest;
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
import java.util.List;
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
                    "Username sudah terpakai",
                    "/user/registration"
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
                "Akun berhasil dibuat",
                "/user/registration"
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    public ResponseEntity<BaseResponse> editUser(String username, UserUpdateRequest request) {
        Optional<User> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();

            existingUser.setUsername(request.getUsername());
            existingUser.setPassword(request.getPassword());

            String encodedPassword = bCryptPasswordEncoder.encode(request.getPassword());
            existingUser.setPassword(encodedPassword);

            userRepository.save(existingUser);

            BaseResponse response = new BaseResponse(
                    LocalDateTime.now(),
                    HttpStatus.OK.value(),
                    false,
                    "User information successfully updated",
                    "/user/edit/" + existingUser.getUsername()
            );

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            BaseResponse response = new BaseResponse(
                    LocalDateTime.now(),
                    HttpStatus.NOT_FOUND.value(),
                    true,
                    "Username not found",
                    "/user/edit/" + username
            );

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    public ResponseEntity<BaseResponse> lihatUserAll() {
        try {
            List<User> userList = userRepository.findAll();

            if (!userList.isEmpty()) {
                BaseResponse response = new BaseResponse(
                        LocalDateTime.now(),
                        HttpStatus.OK.value(),
                        false,
                        "Success",
                        "/lihat/all"
                );

                return ResponseEntity.ok(response);
            } else {

                BaseResponse response = new BaseResponse(
                        LocalDateTime.now(),
                        HttpStatus.NOT_FOUND.value(),
                        true,
                        "User tidak ditemukan",
                        "/lihat/all"
                );
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {

            BaseResponse response = new BaseResponse(
                    LocalDateTime.now(),
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    true,
                    "Error",
                    "/lihat/all"
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    public ResponseEntity<BaseResponse> lihatUserSingle(LoginRequest request) {
        try {
            Optional<User> userOptional = userRepository.findByUsername(request.getUsername());

            if (userOptional.isPresent()) {
                User user = userOptional.get();

                BaseResponse response = new BaseResponse(
                        LocalDateTime.now(),
                        HttpStatus.OK.value(),
                        false,
                        "Success",
                        "/lihat/single/" + request.getUsername()
                );

                return ResponseEntity.ok(response);
            } else {
                // User not found
                BaseResponse response = new BaseResponse(
                        LocalDateTime.now(),
                        HttpStatus.NOT_FOUND.value(),
                        true,
                        "User tidak ditemukan",
                        "/lihat/single/" + request.getUsername()
                );
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            // Handle exceptions
            BaseResponse response = new BaseResponse(
                    LocalDateTime.now(),
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    true,
                    "Error",
                    "/lihat/single/" + request.getUsername()
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}