package app.entity;

import app.enums.UserStatus;
import app.enums.UserType;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class User {

  private long id;
  private String name;
  private String password;
  private UserType type;
  private UserStatus status;
  private String firstName;
  private String lastName;
  private Integer age;
  private String updatedBy;
  private LocalDateTime updatedAt;
  private String createdBy;
  private LocalDateTime createdAt;

}
