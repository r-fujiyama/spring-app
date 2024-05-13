package app.controller.user.v1;

import app.annotation.constraint.Age;
import app.annotation.constraint.ID;
import app.annotation.constraint.NotUnknown;
import app.annotation.constraint.UserName;
import app.annotation.role.RoleCreate;
import app.annotation.role.RoleDelete;
import app.annotation.role.RoleRead;
import app.annotation.role.RoleUpdate;
import app.constants.RegExp;
import app.controller.response.Response;
import app.controller.user.response.User;
import app.controller.user.v1.request.InsertUserRequest;
import app.controller.user.v1.request.UpdateUserRequest;
import app.controller.user.v1.response.InsertUserResponse;
import app.controller.user.v1.response.SearchUsersResponse;
import app.controller.user.v1.response.UpdateUserResponse;
import app.enums.UserStatus;
import app.enums.UserType;
import app.service.userV1.UserV1Service;
import app.service.userV1.parameter.InsertUserParam;
import app.service.userV1.parameter.SearchUsersParam;
import app.service.userV1.parameter.UpdateUserParam;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import java.util.stream.Collectors;
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

  @RoleRead
  @GetMapping("search")
  public SearchUsersResponse searchUsers(
      @RequestParam(required = false) @Valid @ID Long id,
      @RequestParam(required = false) @Valid @UserName String userName,
      @RequestParam(required = false) @Valid @NotUnknown UserType userType,
      @RequestParam(required = false) @Valid @NotUnknown UserStatus userStatus,
      @RequestParam(required = false) @Valid @Pattern(regexp = RegExp.HALF_WIDTH_ALPHABET) String firstName,
      @RequestParam(required = false) @Valid @Pattern(regexp = RegExp.HALF_WIDTH_ALPHABET) String lastName,
      @RequestParam(required = false) @Valid @Age Integer age) {
    var users = userService.searchUsers(SearchUsersParam.builder()
        .id(id)
        .userName(userName)
        .userType(userType)
        .userStatus(userStatus)
        .firstName(firstName)
        .lastName(lastName)
        .age(age)
        .build());
    return new SearchUsersResponse(users.stream().map(
        user -> User.builder()
            .id(user.getId())
            .name(user.getName())
            .type(user.getType())
            .status(user.getStatus())
            .firstName(user.getFirstName())
            .lastName(user.getLastName())
            .age(user.getAge())
            .build()
    ).collect(Collectors.toList()));
  }

  @RoleCreate
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public InsertUserResponse insertUser(@Valid @RequestBody InsertUserRequest request) {
    var user = userService.insertUser(InsertUserParam.builder()
        .userName(request.getUserName())
        .password(request.getPassword())
        .userType(request.getUserType())
        .firstName(request.getFirstName())
        .lastName(request.getLastName())
        .age(request.getAge())
        .build());
    return new InsertUserResponse(User.builder()
        .id(user.getId())
        .name(user.getName())
        .type(user.getType())
        .status(user.getStatus())
        .firstName(user.getFirstName())
        .lastName(user.getLastName())
        .age(user.getAge())
        .build());
  }

  @RoleUpdate
  @PutMapping(path = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
  public UpdateUserResponse updateUser(@Valid @ID @PathVariable("id") long id,
      @Valid @RequestBody UpdateUserRequest request) {
    var user = userService.updateUser(UpdateUserParam.builder()
        .id(id)
        .name(request.getUserName())
        .password(request.getPassword())
        .userType(request.getUserType())
        .firstName(request.getFirstName())
        .lastName(request.getLastName())
        .age(request.getAge())
        .build());
    return new UpdateUserResponse(User.builder()
        .id(user.getId())
        .name(user.getName())
        .type(user.getType())
        .status(user.getStatus())
        .firstName(user.getFirstName())
        .lastName(user.getLastName())
        .age(user.getAge())
        .build());
  }

  @RoleDelete
  @DeleteMapping(path = "{id}")
  public Response deleteUser(@Valid @ID @PathVariable("id") long id) {
    userService.deleteUser(id);
    return new Response();
  }

}
