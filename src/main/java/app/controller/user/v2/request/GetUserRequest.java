package app.controller.user.v2.request;


import app.constants.RegExp;
import app.constraint.Age;
import app.constraint.NotUnknown;
import app.constraint.Pattern;
import app.constraint.UserID;
import app.enums.UserType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
public class GetUserRequest {

  @UserID
  String userID;

  @NotNull
  @NotUnknown
  UserType userType;

  @NotBlank
  @Pattern(regexp = RegExp.ALL_HALF_WIDTH_ALPHABET)
  String firstName;

  @NotBlank
  @Pattern(regexp = RegExp.ALL_HALF_WIDTH_ALPHABET)
  String lastName;

  @NotNull
  @Age
  Integer age;

}
