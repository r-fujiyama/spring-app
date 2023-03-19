package app.controller.user.request;


import app.constants.RegExp;
import app.constraints.Age;
import app.constraints.NotUnknown;
import app.constraints.Pattern;
import app.enums.UserType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
public class InsertUserRequest {

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
