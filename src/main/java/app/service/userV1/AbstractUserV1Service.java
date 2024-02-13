package app.service.userV1;

import app.controller.user.response.User;
import app.controller.user.v1.request.InsertUserRequest;
import app.controller.user.v1.request.UpdateUserRequest;
import app.controller.user.v1.response.DeleteUserResponse;
import app.controller.user.v1.response.GetUserResponse;
import app.controller.user.v1.response.InsertUserResponse;
import app.controller.user.v1.response.UpdateUserResponse;
import app.enums.UserStatus;
import app.enums.UserType;

public abstract class AbstractUserV1Service implements UserV1Service {

  @Override
  public GetUserResponse getUser(String userID, UserType userType, String firstName, String lastName, Integer age) {
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
  public InsertUserResponse insertUser(InsertUserRequest request) {
    insertUserDetailProcess();
    return new InsertUserResponse(
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

  protected abstract void insertUserDetailProcess();

  @Override
  public UpdateUserResponse updateUser(long id, UpdateUserRequest request) {
    updateUserDetailProcess();
    return new UpdateUserResponse(
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

  protected abstract void updateUserDetailProcess();

  @Override
  public DeleteUserResponse deleteUser(long id) {
    deleteUserDetailProcess();
    return new DeleteUserResponse(id);
  }

  protected abstract void deleteUserDetailProcess();

}
