package revisao.api.crudrevisao.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import revisao.api.crudrevisao.common.CommonResponse;
import revisao.api.crudrevisao.exceptions.EmailAlreadyUsedException;
import revisao.api.crudrevisao.model.User;
import revisao.api.crudrevisao.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public CommonResponse<User> create(User user) {
        Optional<User> optionalUser = userRepository.findByEmail(user.getEmail());

        if(optionalUser.isPresent()) {
            throw new EmailAlreadyUsedException();
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User saved = userRepository.save(user);

        return new CommonResponse<>(saved,"User created successfully");
    }
}
