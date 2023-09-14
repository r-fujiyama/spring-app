package app.service.userV1;

import app.annotation.service.Freelance;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Freelance
@Slf4j
@Service
public class FreelanceUserV1Service extends AbstractUserV1Service {

  @Override
  void getUserDetailProcess() {
    log.info("v1 getUserDetailProcess for freelance");
  }

  @Override
  void insertUserDetailProcess() {
    log.info("v1 insertUserDetailProcess for freelance");
  }

  @Override
  void updateUserDetailProcess() {
    log.info("v1 updateUserDetailProcess for freelance");
  }

  @Override
  void deleteUserDetailProcess() {
    log.info("v1 deleteUserDetailProcess for freelance");
  }

}
