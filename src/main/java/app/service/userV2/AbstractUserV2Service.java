package app.service.userV2;

import app.controller.response.Response;
import app.controller.user.response.User;
import app.controller.user.v2.request.GetUserRequest;
import app.controller.user.v2.request.InsertUserRequest;
import app.controller.user.v2.request.UpdateUserRequest;
import app.controller.user.v2.response.GetUserResponse;
import app.enums.UserStatus;
import app.enums.UserType;

public abstract class AbstractUserV2Service implements UserV2Service {

  @Override
  public GetUserResponse getUser(GetUserRequest request) {
    getUserDetailProcess();
    return new GetUserResponse(
        User.builder()
            .id(1L)
            .userID(null)
            .type(UserType.UNKNOWN)
            .status(UserStatus.UNKNOWN)
            .firstName(null)
            .lastName(null)
            .age(0)
            .build()
    );
  }

  protected abstract void getUserDetailProcess();

  @Override
  public Response insertUser(long id, InsertUserRequest request) {
    insertUserDetailProcess();
    return new Response();
  }

  protected abstract void insertUserDetailProcess();

  @Override
  public Response updateUser(long id, UpdateUserRequest request) {
    updateUserDetailProcess();
    return new Response();
  }

  protected abstract void updateUserDetailProcess();

  @Override
  public Response deleteUser(long id) {
    deleteUserDetailProcess();
    return new Response();
  }

  protected abstract void deleteUserDetailProcess();

}
