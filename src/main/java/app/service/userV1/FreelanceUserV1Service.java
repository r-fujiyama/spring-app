package app.service.userV1;

import app.annotation.service.Freelance;
import app.dao.UserDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Freelance
@Slf4j
@Service
public class FreelanceUserV1Service extends AbstractUserV1Service {

  public FreelanceUserV1Service(UserDao userDao) {
    super(userDao);
  }

  @Override
  protected void getUserDetailProcess() {
    log.info("v1 getUserDetailProcess for freelance");
  }

  @Override
  protected void insertUserDetailProcess() {
    log.info("v1 insertUserDetailProcess for freelance");
  }

  @Override
  protected void updateUserDetailProcess() {
    log.info("v1 updateUserDetailProcess for freelance");
  }

  @Override
  protected void deleteUserDetailProcess() {
    log.info("v1 deleteUserDetailProcess for freelance");
  }

}
