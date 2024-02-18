package app.service.userV2;

import app.controller.user.response.User;
import app.controller.user.v2.request.SearchUserRequest;
import app.controller.user.v2.request.InsertUserRequest;
import app.controller.user.v2.request.UpdateUserRequest;
import app.controller.user.v2.response.SearchUserResponse;
import app.enums.UserStatus;
import app.enums.UserType;

public abstract class AbstractUserV2Service implements UserV2Service {

  @Override
  public SearchUserResponse getUser(SearchUserRequest request) {
    getUserDetailProcess();
    return new SearchUserResponse(
        User.builder()
            .id(1L)
            .name(null)
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
  public void insertUser(InsertUserRequest request) {
    insertUserDetailProcess();
  }

  protected abstract void insertUserDetailProcess();

  @Override
  public void updateUser(long id, UpdateUserRequest request) {
    updateUserDetailProcess();
  }

  protected abstract void updateUserDetailProcess();

  @Override
  public void deleteUser(long id) {
    deleteUserDetailProcess();
  }

  protected abstract void deleteUserDetailProcess();

}
