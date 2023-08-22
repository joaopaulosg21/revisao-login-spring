package revisao.api.crudrevisao.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import revisao.api.crudrevisao.common.CommonResponse;
import revisao.api.crudrevisao.model.LoginDTO;
import revisao.api.crudrevisao.model.User;
import revisao.api.crudrevisao.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<CommonResponse<User>> create(@RequestBody User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(user));
    }

    @PostMapping("/login")
    public ResponseEntity<CommonResponse<String>> login(@RequestBody LoginDTO loginDTO) {
        return ResponseEntity.ok(userService.login(loginDTO));
    }

    @GetMapping()
    public ResponseEntity<List<User>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }
}
