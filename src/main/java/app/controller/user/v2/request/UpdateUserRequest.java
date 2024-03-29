package app.controller.user.v2.request;


import app.annotation.constraint.Age;
import app.annotation.constraint.NotUnknown;
import app.constants.RegExp;
import app.enums.UserType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Value;

@Value
public class UpdateUserRequest {

  @NotNull
  @NotUnknown
  UserType userType;

  @NotNull
  @Pattern(regexp = RegExp.HALF_WIDTH_ALPHABET)
  String firstName;

  @NotNull
  @Pattern(regexp = RegExp.HALF_WIDTH_ALPHABET)
  String lastName;

  @NotNull
  @Age
  Integer age;

}
