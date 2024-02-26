package app.service.userV1;

import app.annotation.service.Corporate;
import app.dao.UserDao;
import app.util.MessageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Corporate
@Slf4j
@Service
public class CorporateUserV1Service extends AbstractUserV1Service {

  public CorporateUserV1Service(UserDao userDao, MessageUtils messageUtils) {
    super(userDao, messageUtils);
  }

  @Override
  protected void getUserDetailProcess() {
    log.info("v1 getUserDetailProcess for Corporate");
  }

  @Override
  protected void insertUserDetailProcess() {
    log.info("v1 insertUserDetailProcess for Corporate");
  }

  @Override
  protected void updateUserDetailProcess() {
    log.info("v1 updateUserDetailProcess for Corporate");
  }

  @Override
  protected void deleteUserDetailProcess() {
    log.info("v1 deleteUserDetailProcess for Corporate");
  }

}
