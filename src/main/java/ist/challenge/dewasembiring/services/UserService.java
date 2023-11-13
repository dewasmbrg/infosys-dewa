package ist.challenge.dewasembiring.services;

import ist.challenge.dewasembiring.repositories.UserRepository;
import lombok.AllArgsConstructor;
import ist.challenge.dewasembiring.models.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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

    public String signUpUser(User user) {
        boolean userExists = userRepository
                .findByUsername(user.getUsername())
                .isPresent();

        if (userExists) {
            throw new IllegalStateException("username already taken");
        }

        String encodedPassword = bCryptPasswordEncoder
                .encode(user.getPassword());

        user.setPassword(encodedPassword);

        userRepository.save(user);

        return "works";
    }

    public int enableUser(String username) {
        return userRepository.enableUser(username);
    }
}