package app.controller.user.v1;

import app.annotation.role.RoleCreate;
import app.annotation.role.RoleDelete;
import app.annotation.role.RoleUpdate;
import app.constants.RegExp;
import app.constraint.Age;
import app.constraint.ID;
import app.constraint.NotUnknown;
import app.constraint.UserID;
import app.controller.user.v1.request.InsertUserRequest;
import app.controller.user.v1.request.UpdateUserRequest;
import app.controller.user.v1.response.DeleteUserResponse;
import app.controller.user.v1.response.GetUserResponse;
import app.controller.user.v1.response.InsertUserResponse;
import app.controller.user.v1.response.UpdateUserResponse;
import app.enums.UserType;
import app.service.userV1.UserV1Service;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping(path = "v1/user")
@Validated
public class UserV1Controller {

  private final UserV1Service userService;

  @RoleCreate
  @GetMapping
  public GetUserResponse getUser(
      @Valid @NotNull @UserID String userID,
      @Valid @NotUnknown @RequestParam UserType userType,
      @Valid @NotNull @Pattern(regexp = RegExp.ALL_HALF_WIDTH_ALPHABET) @RequestParam String firstName,
      @Valid @NotNull @Pattern(regexp = RegExp.ALL_HALF_WIDTH_ALPHABET) @RequestParam String lastName,
      @Valid @Age @RequestParam Integer age) {
    return userService.getUser(userID, userType, firstName, lastName, age);
  }

  @RoleCreate
  @PostMapping(path = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
  public InsertUserResponse insertUser(@Valid @ID @PathVariable("id") long id,
      @Valid @RequestBody InsertUserRequest request) {
    return userService.insertUser(id, request);
  }

  @RoleUpdate
  @PutMapping(path = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
  public UpdateUserResponse updateUser(@Valid @ID @PathVariable("id") long id,
      @Valid @RequestBody UpdateUserRequest request) {
    return userService.updateUser(id, request);
  }

  @RoleDelete
  @DeleteMapping(path = "{id}")
  public DeleteUserResponse deleteUser(@Valid @ID @PathVariable("id") long id) {
    return userService.deleteUser(id);
  }

}
