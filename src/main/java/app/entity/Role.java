package app.entity;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class Role {

  private String userID;
  private boolean allowCreate;
  private boolean allowRead;
  private boolean allowUpdate;
  private boolean allowDelete;
  private String updatedBy;
  private LocalDateTime updatedAt;
  private String createdBy;
  private LocalDateTime createdAt;

}
