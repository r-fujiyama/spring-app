package app.controller.user.v2;

import app.annotation.constraint.ID;
import app.annotation.role.RoleCreate;
import app.annotation.role.RoleDelete;
import app.annotation.role.RoleRead;
import app.annotation.role.RoleUpdate;
import app.controller.response.Response;
import app.controller.user.response.User;
import app.controller.user.v2.request.InsertUserRequest;
import app.controller.user.v2.request.UserSearchRequest;
import app.controller.user.v2.request.UpdateUserRequest;
import app.controller.user.v2.response.UserSearchResponse;
import app.service.userV2.UserV2Service;
import app.service.userV2.parameter.InsertUserParam;
import app.service.userV2.parameter.UserSearchParam;
import app.service.userV2.parameter.UpdateUserParam;
import jakarta.validation.Valid;
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
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping(path = "v2/user")
@Validated
public class UserV2Controller {

  private final UserV2Service userService;

  @RoleRead
  @GetMapping
  public UserSearchResponse userSearch(@Valid UserSearchRequest request) {
    var users = userService.userSearch(UserSearchParam.builder()
        .id(null)
        .userName(request.getUserName())
        .userType(request.getUserType())
        .userStatus(null)
        .firstName(request.getFirstName())
        .lastName(request.getLastName())
        .age(request.getAge())
        .build());
    return new UserSearchResponse(users.stream().map(
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
  public Response insertUser(@Valid @RequestBody InsertUserRequest request) {
    userService.insertUser(InsertUserParam.builder()
        .userName(request.getUserName())
        .password(request.getPassword())
        .userType(request.getUserType())
        .firstName(request.getFirstName())
        .lastName(request.getLastName())
        .age(request.getAge())
        .build());
    return new Response();
  }

  @RoleUpdate
  @PutMapping(path = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
  public Response updateUser(@Valid @ID @PathVariable("id") long id, @Valid @RequestBody UpdateUserRequest request) {
    userService.updateUser(UpdateUserParam.builder()
        .id(id)
        .userType(request.getUserType())
        .firstName(request.getFirstName())
        .lastName(request.getLastName())
        .age(request.getAge())
        .build());
    return new Response();
  }

  @RoleDelete
  @DeleteMapping(path = "{id}")
  public Response deleteUser(@Valid @ID @PathVariable("id") long id) {
    userService.deleteUser(id);
    return new Response();
  }

}
