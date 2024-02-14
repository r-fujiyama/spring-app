package app.entity;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class APIKey {

  private long userID;
  private String apiKey;
  private String updatedBy;
  private LocalDateTime updatedAt;
  private String createdBy;
  private LocalDateTime createdAt;

}
