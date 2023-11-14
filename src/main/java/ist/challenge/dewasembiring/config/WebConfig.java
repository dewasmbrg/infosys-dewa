package ist.challenge.dewasembiring.config;

import ist.challenge.dewasembiring.exceptions.CustomAuthenticationEntryPoint;
import ist.challenge.dewasembiring.services.UserService;
import lombok.Data;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ist.challenge.dewasembiring.utils.JwtUtil;

@Configuration
@EnableWebSecurity
@Data
public class WebConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final CustomAuthenticationEntryPoint authenticationEntryPoint;
    private final JwtUtil jwtUtil;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtAuthorizationFilter jwtAuthorizationFilter;

    public WebConfig(
            UserService userService,
            BCryptPasswordEncoder bCryptPasswordEncoder,
            CustomAuthenticationEntryPoint authenticationEntryPoint,
            JwtUtil jwtUtil,
            JwtAuthenticationFilter jwtAuthenticationFilter,
            JwtAuthorizationFilter jwtAuthorizationFilter) {
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.jwtUtil = jwtUtil;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.jwtAuthorizationFilter = jwtAuthorizationFilter;
    }

//    @Bean
//    public FilterRegistrationBean<JwtAuthenticationFilter> jwtAuthenticationFilterRegistration() {
//        FilterRegistrationBean<JwtAuthenticationFilter> registrationBean = new FilterRegistrationBean<>(jwtAuthenticationFilter);
//        registrationBean.setOrder(1);
//        return registrationBean;
//    }
//
//    @Bean
//    public FilterRegistrationBean<JwtAuthorizationFilter> jwtAuthorizationFilterRegistration() {
//        FilterRegistrationBean<JwtAuthorizationFilter> registrationBean = new FilterRegistrationBean<>(jwtAuthorizationFilter);
//        registrationBean.setOrder(2);
//        return registrationBean;
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/user/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .httpBasic()
                .authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .addFilter(new JwtAuthenticationFilter(authenticationManager(), jwtUtil))
                .addFilter(new JwtAuthorizationFilter(jwtUtil, userService));
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(bCryptPasswordEncoder);
        provider.setUserDetailsService(userService);
        return provider;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
