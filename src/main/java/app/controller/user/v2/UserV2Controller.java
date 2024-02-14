package app.controller.user.v2;

import app.annotation.role.RoleCreate;
import app.annotation.role.RoleDelete;
import app.annotation.role.RoleRead;
import app.annotation.role.RoleUpdate;
import app.annotation.constraint.ID;
import app.controller.response.Response;
import app.controller.user.v2.request.GetUserRequest;
import app.controller.user.v2.request.InsertUserRequest;
import app.controller.user.v2.request.UpdateUserRequest;
import app.controller.user.v2.response.GetUserResponse;
import app.service.userV2.UserV2Service;
import jakarta.validation.Valid;
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
  public GetUserResponse getUserV2(@Valid GetUserRequest request) {
    return userService.getUser(request);
  }

  @RoleCreate
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public Response insertUser(@Valid @RequestBody InsertUserRequest request) {
    userService.insertUser(request);
    return new Response();
  }

  @RoleUpdate
  @PutMapping(path = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
  public Response updateUser(@Valid @ID @PathVariable("id") long id, @Valid @RequestBody UpdateUserRequest request) {
    userService.updateUser(id, request);
    return new Response();
  }

  @RoleDelete
  @DeleteMapping(path = "{id}")
  public Response deleteUser(@Valid @ID @PathVariable("id") long id) {
    userService.deleteUser(id);
    return new Response();
  }

}
