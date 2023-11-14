package ist.challenge.dewasembiring.services;

import ist.challenge.dewasembiring.dto.request.LoginRequest;
import ist.challenge.dewasembiring.dto.response.BaseResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ist.challenge.dewasembiring.utils.JwtUtil;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class LoginService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public ResponseEntity<BaseResponse> login(LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String username = ((UserDetails) authentication.getPrincipal()).getUsername();
            String jwt = jwtUtil.generateToken(username);

            return ResponseEntity.ok(new BaseResponse(
                    LocalDateTime.now(),
                    HttpStatus.OK.value(),
                    false,
                    "Sukses Login " + jwt,
                    "/user/login"
            ));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    new BaseResponse(
                            LocalDateTime.now(),
                            HttpStatus.UNAUTHORIZED.value(),
                            true,
                            "Autentikasi gagal",
                            "/user/login"
                    )
            );
        }
    }
}
