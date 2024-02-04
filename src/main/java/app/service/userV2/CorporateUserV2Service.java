package app.service.userV2;

import app.annotation.service.Corporate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Corporate
@Slf4j
@Service
public class CorporateUserV2Service extends AbstractUserV2Service {

  @Override
  protected void getUserDetailProcess() {
    log.info("v2 getUserDetailProcess for private");
  }

  @Override
  protected void insertUserDetailProcess() {
    log.info("v2 insertUserDetailProcess for private");
  }

  @Override
  protected void updateUserDetailProcess() {
    log.info("v2 updateUserDetailProcess for private");
  }

  @Override
  protected void deleteUserDetailProcess() {
    log.info("v2 deleteUserDetailProcess for private");
  }

}
