package backend.lostandfound.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserProfile {
@GeneratedValue
    @Id
    private Long id;


@Column(unique = true)
@NotNull(message = "Regno already exists")

    private Long regNo;

@Enumerated(EnumType.STRING)
@Column(columnDefinition = "varchar(10) ", nullable = false)
    private Role role=Role.student;

@NotBlank
    private String name;

@Email
@NotNull
    private String email;

@NotNull
    private Integer studyYear; //year of study

    @NotBlank
    private String password;





}
