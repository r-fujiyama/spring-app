package app.service.userV1;

import app.constants.ErrorMessage;
import app.dao.UserDao;
import app.enums.UserStatus;
import app.enums.UserType;
import app.exception.ConflictException;
import app.service.userV1.parameter.InsertUserParam;
import app.service.userV1.parameter.UpdateUserParam;
import app.service.userV1.parameter.SearchUsersParam;
import app.service.userV1.result.User;
import app.util.MessageUtils;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
public abstract class AbstractUserV1Service implements UserV1Service {

  private final UserDao userDao;
  private final MessageUtils messageUtils;

  @Override
  public List<User> searchUsers(SearchUsersParam param) {
    getUserDetailProcess();
    var users = userDao.findBySearchUsersParam(param);
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
  @Transactional
  public User insertUser(InsertUserParam param) {
    insertUserDetailProcess();
    userExistsCheck(param.getUserName());

    userDao.insert(app.entity.User.builder()
        .name(param.getUserName())
        .password(param.getPassword())
        .type(param.getUserType())
        .status(UserStatus.REGISTERED)
        .firstName(param.getFirstName())
        .lastName(param.getLastName())
        .age(param.getAge())
        .build());

    var user = userDao.findByName(param.getUserName());
    return User.builder()
        .id(user.getId())
        .name(user.getName())
        .type(user.getType())
        .status(user.getStatus())
        .firstName(user.getFirstName())
        .lastName(user.getLastName())
        .age(user.getAge())
        .build();
  }

  private void userExistsCheck(String userName) {
    var user = userDao.findByName(userName);
    if (user != null) {
      throw new ConflictException(messageUtils.getMessage(ErrorMessage.USER_ALREADY_EXISTS, user.getName()));
    }
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
