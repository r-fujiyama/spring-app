package app.entity;

import app.enums.UserStatus;
import app.enums.UserType;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class User {

  private long id;
  private String userID;
  private String password;
  private UserType userType;
  private UserStatus userStatus;
  private String lastName;
  private String firstName;
  private int age;
  private String updatedBy;
  private LocalDateTime updatedAt;
  private String createdBy;
  private LocalDateTime createdAt;

}
