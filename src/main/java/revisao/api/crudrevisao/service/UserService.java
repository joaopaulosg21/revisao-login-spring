package revisao.api.crudrevisao.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import revisao.api.crudrevisao.common.CommonResponse;
import revisao.api.crudrevisao.configuration.auth.TokenService;
import revisao.api.crudrevisao.exceptions.user.EmailAlreadyUsedException;
import revisao.api.crudrevisao.model.LoginDTO;
import revisao.api.crudrevisao.model.User;
import revisao.api.crudrevisao.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final TokenService tokenService;

    public CommonResponse<User> create(User user) {
        Optional<User> optionalUser = userRepository.findByEmail(user.getEmail());

        if(optionalUser.isPresent()) {
            throw new EmailAlreadyUsedException();
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User saved = userRepository.save(user);

        return new CommonResponse<>(saved,"User created successfully");
    }

    public CommonResponse<String> login(LoginDTO loginDTO) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(null,loginDTO);
        Authentication auth = authenticationManager.authenticate(authentication);
        String token = tokenService.generate(auth);
        return new CommonResponse<>(token,"Login successfully");
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }
}
