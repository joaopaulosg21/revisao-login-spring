package revisao.api.crudrevisao.configuration.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import revisao.api.crudrevisao.exceptions.user.LoginException;
import revisao.api.crudrevisao.model.LoginDTO;
import revisao.api.crudrevisao.model.User;
import revisao.api.crudrevisao.repository.UserRepository;


public class CustomAuthenticationManager implements AuthenticationManager {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        LoginDTO loginDTO = (LoginDTO) authentication.getCredentials();
        User user = userRepository.findByEmail(loginDTO.email()).orElseThrow(LoginException::new);

        if(!passwordEncoder.matches(loginDTO.password(),user.getPassword())) {
            throw new LoginException();
        }

        return new UsernamePasswordAuthenticationToken(user,loginDTO,null);
    }
}
