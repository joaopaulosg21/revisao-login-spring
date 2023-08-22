package revisao.api.crudrevisao.user;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import revisao.api.crudrevisao.common.CommonResponse;
import revisao.api.crudrevisao.configuration.auth.TokenService;
import revisao.api.crudrevisao.exceptions.user.EmailAlreadyUsedException;
import revisao.api.crudrevisao.model.LoginDTO;
import revisao.api.crudrevisao.model.User;
import revisao.api.crudrevisao.repository.UserRepository;
import revisao.api.crudrevisao.service.UserService;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserUnitTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private TokenService tokenService;

    private UserService userService;

    @BeforeEach
    private void setup() {
        userService = new UserService(userRepository,passwordEncoder,authenticationManager,tokenService);
    }

    @Test
    public void createUserTest() {
        User user = new User("test name","test@email.com","123");

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("123");
        when(userRepository.save(any(User.class))).thenReturn(user);

        CommonResponse<User> response = userService.create(user);

        assertEquals("User created successfully",response.message());
        assertEquals(user.getEmail(),response.object().getEmail());

    }

    @Test
    public void createUserExceptionTest() {
        User user = new User("test name","test@email.com","123");

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        EmailAlreadyUsedException exception = assertThrows(EmailAlreadyUsedException.class,() -> userService.create(user));

        assertEquals("Email is already in use",exception.getMessage());
    }

    @Test
    public void loginTest() {
        LoginDTO loginDTO = new LoginDTO("test@email.com","123");

        User user = new User("test name","test@email.com","123");
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user,loginDTO);
        when(authenticationManager.authenticate(any(Authentication.class))).thenReturn(auth);
        when(tokenService.generate(any(Authentication.class))).thenReturn("token");


        CommonResponse<String> response = userService.login(loginDTO);

        assertEquals("Login successfully",response.message());
    }
}
