package revisao.api.crudrevisao.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.AntPathMatcher;
import revisao.api.crudrevisao.configuration.auth.CustomAuthFilter;
import revisao.api.crudrevisao.configuration.auth.CustomAuthenticationManager;
import revisao.api.crudrevisao.configuration.auth.TokenService;
import revisao.api.crudrevisao.repository.UserRepository;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((request) -> {
            request.requestMatchers(new AntPathRequestMatcher("/users","POST")).permitAll()
                    .requestMatchers(new AntPathRequestMatcher("/users/login","POST")).permitAll()
                    .anyRequest().authenticated();
        }).addFilterBefore(new CustomAuthFilter(tokenService,userRepository), UsernamePasswordAuthenticationFilter.class);

        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(AbstractHttpConfigurer::disable);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new CustomAuthenticationManager();
    }
}
