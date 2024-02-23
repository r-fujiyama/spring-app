package app.service.userV2;

import app.enums.UserStatus;
import app.enums.UserType;
import app.service.userV2.parameter.InsertUserParam;
import app.service.userV2.parameter.UpdateUserParam;
import app.service.userV2.parameter.UserSearchParam;
import app.service.userV2.result.User;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractUserV2Service implements UserV2Service {

  @Override
  public List<User> userSearch(UserSearchParam param) {
    getUserDetailProcess();
    var user = User.builder()
        .id(1L)
        .name(null)
        .type(UserType.UNKNOWN)
        .status(UserStatus.UNKNOWN)
        .firstName(null)
        .lastName(null)
        .age(0)
        .build();
    var users = new ArrayList<User>();
    users.add(user);
    return users;
  }

  protected abstract void getUserDetailProcess();

  @Override
  public void insertUser(InsertUserParam param) {
    insertUserDetailProcess();
  }

  protected abstract void insertUserDetailProcess();

  @Override
  public void updateUser(UpdateUserParam param) {
    updateUserDetailProcess();
  }

  protected abstract void updateUserDetailProcess();

  @Override
  public void deleteUser(long id) {
    deleteUserDetailProcess();
  }

  protected abstract void deleteUserDetailProcess();

}
