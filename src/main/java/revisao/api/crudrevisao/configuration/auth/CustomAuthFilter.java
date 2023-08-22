package revisao.api.crudrevisao.configuration.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import revisao.api.crudrevisao.exceptions.user.LoginException;
import revisao.api.crudrevisao.model.User;
import revisao.api.crudrevisao.repository.UserRepository;

import java.io.IOException;
import java.util.Objects;


@RequiredArgsConstructor
public class CustomAuthFilter extends OncePerRequestFilter {

    private final TokenService tokenService;

    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = this.getTokenFromRequest(request);

        if(tokenService.isValid(token)) {
            this.authenticate(token);
        }

        doFilter(request,response,filterChain);
    }

    private String getTokenFromRequest(HttpServletRequest req) {
        String token = req.getHeader("Authorization");

        if(Objects.isNull(token) || token.isEmpty()) {
            return null;
        }

        return token.split(" ")[1];
    }

    private void authenticate(String token) {
        long id = tokenService.getIdFromSubject(token);
        User user = userRepository.findById(id).orElseThrow(LoginException::new);

        Authentication authentication = new UsernamePasswordAuthenticationToken(user,null,null);

        SecurityContextHolder.getContext().setAuthentication(authentication);

    }
}
