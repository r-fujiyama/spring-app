package app.service.userV1;

import app.dao.UserDao;
import app.enums.UserStatus;
import app.enums.UserType;
import app.service.userV1.parameter.InsertUserParam;
import app.service.userV1.parameter.UpdateUserParam;
import app.service.userV1.parameter.UserSearchParam;
import app.service.userV1.result.User;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class AbstractUserV1Service implements UserV1Service {

  private final UserDao userDao;

  @Override
  public List<User> userSearch(UserSearchParam param) {
    getUserDetailProcess();
    var users = userDao.findByUserSearchParam(param);
    return users.stream().map(
        user -> User.builder()
            .id(user.getId())
            .name(user.getName())
            .type(user.getType())
            .status(user.getStatus())
            .firstName(user.getFirstName())
            .lastName(user.getLastName())
            .age(user.getAge())
            .build()
    ).collect(Collectors.toList());
  }

  protected abstract void getUserDetailProcess();

  @Override
  public User insertUser(InsertUserParam param) {
    insertUserDetailProcess();
    return User.builder()
        .id(1L)
        .name(null)
        .type(UserType.UNKNOWN)
        .status(UserStatus.UNKNOWN)
        .firstName(null)
        .lastName(null)
        .age(0)
        .build();
  }

  protected abstract void insertUserDetailProcess();

  @Override
  public User updateUser(UpdateUserParam param) {
    updateUserDetailProcess();
    return User.builder()
        .id(1L)
        .name(null)
        .type(UserType.UNKNOWN)
        .status(UserStatus.UNKNOWN)
        .firstName(null)
        .lastName(null)
        .age(0)
        .build();
  }

  protected abstract void updateUserDetailProcess();

  @Override
  public User deleteUser(long id) {
    deleteUserDetailProcess();
    return User.builder()
        .id(1L)
        .name(null)
        .type(UserType.UNKNOWN)
        .status(UserStatus.UNKNOWN)
        .firstName(null)
        .lastName(null)
        .age(0)
        .build();
  }

  protected abstract void deleteUserDetailProcess();

}
