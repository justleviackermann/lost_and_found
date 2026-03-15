package backend.lostandfound.dto.ItemDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemResponseDto {
    private Long id;
    private String itemName;
    private String itemDesc;
    private String status;
    private Long reporterRegNo; // Only the ID/RegNo for the UI
    private String reporterName;
    private OffsetDateTime reportedAt;
    private Boolean isResolved;
    private OffsetDateTime resolvedAt;
}
