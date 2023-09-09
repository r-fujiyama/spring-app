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
  public GetUserResponse getUser(long userID, UserType userType, String firstName, String lastName, Integer age) {
    getUserDetailProcess();
    return new GetUserResponse(
        User.builder()
            .id(null)
            .type(UserType.UNKNOWN)
            .status(UserStatus.UNKNOWN)
            .firstName(null)
            .lastName(null)
            .age(null)
            .build()
    );
  }

  abstract void getUserDetailProcess();

  @Override
  public InsertUserResponse insertUser(long userID, InsertUserRequest request) {
    insertUserDetailProcess();
    return new InsertUserResponse(
        User.builder()
            .id(null)
            .type(UserType.UNKNOWN)
            .status(UserStatus.UNKNOWN)
            .firstName(null)
            .lastName(null)
            .age(null)
            .build()
    );
  }

  abstract void insertUserDetailProcess();

  @Override
  public UpdateUserResponse updateUser(long userID, UpdateUserRequest request) {
    updateUserDetailProcess();
    return new UpdateUserResponse(
        User.builder()
            .id(null)
            .type(UserType.UNKNOWN)
            .status(UserStatus.UNKNOWN)
            .firstName(null)
            .lastName(null)
            .age(null)
            .build()
    );
  }

  abstract void updateUserDetailProcess();

  @Override
  public DeleteUserResponse deleteUser(long userID) {
    deleteUserDetailProcess();
    return new DeleteUserResponse(userID);
  }

  abstract void deleteUserDetailProcess();

}
