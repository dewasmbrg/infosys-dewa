package ist.challenge.dewasembiring.utils;

import ist.challenge.dewasembiring.exceptions.CustomAuthenticationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class CustomAuthenticationManager implements AuthenticationManager {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (authentication != null) {
            return authentication;
        } else {
            throw new CustomAuthenticationException("Authentication failed");
        }
    }
}

