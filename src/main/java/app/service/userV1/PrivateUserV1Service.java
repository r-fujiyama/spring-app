package app.service.userV1;

import app.annotation.Private;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Private
@Slf4j
@Service
public class PrivateUserV1Service extends AbstractUserV1Service {

  @Override
  void getUserDetailProcess() {
    log.info("v1 getUserDetailProcess for private");
  }

  @Override
  void insertUserDetailProcess() {
    log.info("v1 insertUserDetailProcess for private");
  }

  @Override
  void updateUserDetailProcess() {
    log.info("v1 updateUserDetailProcess for private");
  }

  @Override
  void deleteUserDetailProcess() {
    log.info("v1 deleteUserDetailProcess for private");
  }

}
