package backend.lostandfound.dto.UserDto;

import backend.lostandfound.model.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponseDto{
private Long id;
private Long regNo;
private String name;
private String email;
private Integer studyYear;
private Role role;
}
