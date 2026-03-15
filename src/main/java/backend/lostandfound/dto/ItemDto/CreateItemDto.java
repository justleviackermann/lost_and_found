package backend.lostandfound.dto.ItemDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateItemDto {
    @NotBlank(message = "Item name is required")
    private String itemName;

    private String itemDesc;

    @NotNull(message = "Status (lost/found) is required")
    private String status;

    @NotNull(message = "Reporter registration number is required")
    private Long reporterRegNo;

    @NotNull
    private Boolean isResolved;

}
