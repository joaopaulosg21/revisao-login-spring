package revisao.api.crudrevisao.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "name cannot be null")
    private String name;

    @NotBlank(message = "email cannot be null")
    private String email;

    @NotBlank(message = "password cannot be null")
    private String password;
}
