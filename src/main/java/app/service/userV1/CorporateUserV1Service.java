package app.service.userV1;

import app.annotation.Corporate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Corporate
@Slf4j
@Service
public class CorporateUserV1Service extends AbstractUserV1Service {

  @Override
  void getUserDetailProcess() {
    log.info("v1 getUserDetailProcess for Corporate");
  }

  @Override
  void insertUserDetailProcess() {
    log.info("v1 insertUserDetailProcess for Corporate");
  }

  @Override
  void updateUserDetailProcess() {
    log.info("v1 updateUserDetailProcess for Corporate");
  }

  @Override
  void deleteUserDetailProcess() {
    log.info("v1 deleteUserDetailProcess for Corporate");
  }

}
