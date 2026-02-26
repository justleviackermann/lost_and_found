package backend.lostandfound.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;

@Entity
public class UserProfile {
@GeneratedValue
    @Id
    private Long id;


@Column(unique = true)
@NotNull
    private Long regNo;

@Column(columnDefinition = "Varchar(10) Default 'Student' ")
    private String role;

@NotBlank
    private String name;

@Email
@NotNull
    private String email;

@NotNull
    private int studyYear; //year of study

    @NotBlank
    private String password;





}
