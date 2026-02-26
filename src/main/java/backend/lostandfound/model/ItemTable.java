package backend.lostandfound.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;

@Entity
public class ItemTable {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String itemName;

    private String itemDesc;

    @NotNull
    private Boolean status;
    //lost or found


    @ManyToOne
    @JoinColumn(name="reporter_id",referencedColumnName = "regNo")
    private UserProfile userProfile;

    @CreationTimestamp //automaticcaly create timestamp shen save is called
    @Column(columnDefinition = "TimeStamp with Time Zone")
    private OffsetDateTime reportedAt; //store exact time and time zone

    @NotNull
    private Boolean isResolved;


    @Column(columnDefinition = "TimeStamp with Time Zone")
    private OffsetDateTime resolvedAt;


}
