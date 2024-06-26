package app.service.userV1;

import app.constants.ErrorMessage;
import app.dao.UserDao;
import app.entity.User;
import app.enums.UserStatus;
import app.exception.ConflictException;
import app.exception.NotFoundException;
import app.service.userV1.parameter.InsertUserParam;
import app.service.userV1.parameter.SearchUsersParam;
import app.service.userV1.parameter.UpdateUserParam;
import app.service.userV1.result.UserInfo;
import app.util.MessageUtils;
import app.util.StringUtils;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
public abstract class AbstractUserV1Service implements UserV1Service {

  private final UserDao userDao;
  private final MessageUtils messageUtils;

  @Override
  @Transactional(readOnly = true)
  public List<UserInfo> searchUsers(SearchUsersParam param) {
    getUserDetailProcess();
    var users = userDao.findBySearchUsersParam(param);
    return users.stream().map(
        user -> UserInfo.builder()
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
  public UserInfo insertUser(InsertUserParam param) {
    insertUserDetailProcess();
    userNameConflictCheck(param.getUserName());

    userDao.insert(User.builder()
        .name(param.getUserName())
        .password(param.getPassword())
        .type(param.getUserType())
        .status(UserStatus.REGISTERED)
        .firstName(param.getFirstName())
        .lastName(param.getLastName())
        .age(param.getAge())
        .build());

    var user = userDao.findByName(param.getUserName());
    return UserInfo.builder()
        .id(user.getId())
        .name(user.getName())
        .type(user.getType())
        .status(user.getStatus())
        .firstName(user.getFirstName())
        .lastName(user.getLastName())
        .age(user.getAge())
        .build();
  }

  private void userNameConflictCheck(String userName) {
    var user = userDao.findByName(userName);
    if (user != null) {
      throw new ConflictException(messageUtils.getMessage(ErrorMessage.USER_ALREADY_EXISTS, user.getName()));
    }
  }

  protected abstract void insertUserDetailProcess();

  @Override
  @Transactional
  public UserInfo updateUser(UpdateUserParam param) {
    updateUserDetailProcess();
    if (!StringUtils.isEmpty(param.getName())) {
      userNameConflictCheck(param.getName());
    }

    userDao.update(User.builder()
        .id(param.getId())
        .name(param.getName())
        .password(param.getPassword())
        .type(param.getUserType())
        .firstName(param.getFirstName())
        .lastName(param.getLastName())
        .age(param.getAge())
        .build());

    var user = userDao.findByID(param.getId());
    return UserInfo.builder()
        .id(user.getId())
        .name(user.getName())
        .type(user.getType())
        .status(user.getStatus())
        .firstName(user.getFirstName())
        .lastName(user.getLastName())
        .age(user.getAge())
        .build();
  }

  protected abstract void updateUserDetailProcess();

  @Override
  @Transactional
  public void deleteUser(long id) {
    deleteUserDetailProcess();
    existsUserIDCheck(id);
    userDao.delete(id);
  }

  private void existsUserIDCheck(long id) {
    var user = userDao.findByID(id);
    if (user == null) {
      throw new NotFoundException(messageUtils.getMessage(ErrorMessage.USER_NOT_FOUND));
    }
  }

  protected abstract void deleteUserDetailProcess();

}
