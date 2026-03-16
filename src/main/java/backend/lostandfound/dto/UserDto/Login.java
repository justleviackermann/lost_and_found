package backend.lostandfound.dto.UserDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class Login {
    @Email(message = "Please provide a valid email adderss")
    @NotBlank(message = "email shouldn't be blank")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;
}
