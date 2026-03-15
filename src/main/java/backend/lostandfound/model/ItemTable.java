package backend.lostandfound.model;

import jakarta.persistence.*;
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
@NoArgsConstructor
@AllArgsConstructor
public class ItemTable {
    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    private String itemName;

    private String itemDesc;

    @NotNull
    @Enumerated(EnumType.STRING)

    private Status status;
    //lost or found


    @ManyToOne
    @JoinColumn(name="reporter_id",referencedColumnName = "regNo")
    private UserProfile userProfile;

    @CreationTimestamp //automaticcaly create timestamp shen save is called
    @Column(columnDefinition = "TimeStamp with Time Zone")
    private OffsetDateTime reportedAt; //store exact time and time zone

    @NotNull
    private Boolean isResolved;
    //1 resolved 0 not resolved


    @Column(columnDefinition = "TimeStamp with Time Zone")
    private OffsetDateTime resolvedAt;


}
