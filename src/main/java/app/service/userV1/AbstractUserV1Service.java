package app.service.userV1;

import app.controller.user.response.User;
import app.controller.user.v1.request.InsertUserRequest;
import app.controller.user.v1.request.UpdateUserRequest;
import app.controller.user.v1.response.DeleteUserResponse;
import app.controller.user.v1.response.InsertUserResponse;
import app.controller.user.v1.response.SearchUserResponse;
import app.controller.user.v1.response.UpdateUserResponse;
import app.dao.UserDao;
import app.enums.UserStatus;
import app.enums.UserType;
import app.service.userV1.dto.SearchUserParam;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class AbstractUserV1Service implements UserV1Service {

  private final UserDao userDao;

  @Override
  public SearchUserResponse searchUser(SearchUserParam param) {
    getUserDetailProcess();
    var users = userDao.findBySearchParam(param);
    return new SearchUserResponse(users.stream().map(
        user -> User.builder()
            .id(user.getId())
            .userID(user.getUserID())
            .type(user.getUserType())
            .status(user.getUserStatus())
            .firstName(user.getFirstName())
            .lastName(user.getLastName())
            .age(user.getAge())
            .build()
    ).collect(Collectors.toList()));
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
    return new DeleteUserResponse(
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

  protected abstract void deleteUserDetailProcess();

}
