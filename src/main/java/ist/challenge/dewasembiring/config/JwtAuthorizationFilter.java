package ist.challenge.dewasembiring.config;

import ist.challenge.dewasembiring.dto.response.BaseResponse;
import ist.challenge.dewasembiring.exceptions.ExpiredJwtException;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

import ist.challenge.dewasembiring.services.UserService;
import ist.challenge.dewasembiring.utils.JwtUtil;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserService userService;

    public JwtAuthorizationFilter(JwtUtil jwtUtil, UserService userService) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String header = request.getHeader("Authorization");

        if (StringUtils.hasText(header) && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            String username = jwtUtil.extractUsername(token);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                try {
                    jwtUtil.validateToken(token); // This will throw an ExpiredJwtException if the token is expired
                    UserDetails userDetails = userService.loadUserByUsername(username);

                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                } catch (ExpiredJwtException e) {

                    BaseResponse errorResponse = new BaseResponse(
                            LocalDateTime.now(),
                            HttpStatus.UNAUTHORIZED.value(),
                            true,
                            "Token sudah kadaluarsa.",
                            "/user/login"
                    );

                    ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).body(errorResponse);
                }

            }
        }

        filterChain.doFilter(request, response);
    }

}
