package app.service.userV2;

import app.annotation.service.Freelance;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Freelance
@Slf4j
@Service
public class FreelanceUserV2Service extends AbstractUserV2Service {

  @Override
  void getUserDetailProcess() {
    log.info("v2 getUserDetailProcess for private");
  }

  @Override
  void insertUserDetailProcess() {
    log.info("v2 insertUserDetailProcess for private");
  }

  @Override
  void updateUserDetailProcess() {
    log.info("v2 updateUserDetailProcess for private");
  }

  @Override
  void deleteUserDetailProcess() {
    log.info("v2 deleteUserDetailProcess for private");
  }

}
