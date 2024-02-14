package app.service.userV1;

import app.annotation.service.Private;
import app.dao.UserDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Private
@Slf4j
@Service
public class PrivateUserV1Service extends AbstractUserV1Service {

  public PrivateUserV1Service(UserDao userDao) {
    super(userDao);
  }

  @Override
  protected void getUserDetailProcess() {
    log.info("v1 getUserDetailProcess for private");
  }

  @Override
  protected void insertUserDetailProcess() {
    log.info("v1 insertUserDetailProcess for private");
  }

  @Override
  protected void updateUserDetailProcess() {
    log.info("v1 updateUserDetailProcess for private");
  }

  @Override
  protected void deleteUserDetailProcess() {
    log.info("v1 deleteUserDetailProcess for private");
  }

}
