package backend.lostandfound.dto.UserDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateUserDto {


    @NotNull(message = "Regno already exists")

    private Long regNo;


    @NotBlank(message = "Name shouldn't be blank")
    private String name;

    @Email(message = "Please provide a valid email adderss")
    @NotBlank(message = "email shouldn't be blank")
    private String email;

    @NotNull(message = "Study year is required")
    private Integer studyYear; //year of study

    @NotBlank(message = "Password is required")
    private String password;



}
