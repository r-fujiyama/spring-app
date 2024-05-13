package app.service.userV1.FreelanceUserV1ServiceTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import app.TransactionInfo;
import app.enums.ErrorCode;
import app.enums.UserType;
import app.exception.NotFoundException;
import app.service.ServiceTest;
import app.service.userV1.FreelanceUserV1Service;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;

@Import(FreelanceUserV1Service.class)
public class DeleteUserTest extends ServiceTest {

  private final static String TEST_DATA_BASE_PATH = "test-data/service/userV1/FreelanceUserV1ServiceTest/DeleteUserTest/";

  @Autowired
  private FreelanceUserV1Service service;

  @BeforeAll
  public void beforeAll() {
    TransactionInfo.init("test_user", UserType.FREELANCE);
  }

  @Test
  @DataSet(TEST_DATA_BASE_PATH + "setup-user.yml")
  @ExpectedDataSet(TEST_DATA_BASE_PATH + "expected-user.yml")
  public void deleteUserTest() {
    service.deleteUser(1L);
  }

  @Test
  @DataSet(TEST_DATA_BASE_PATH + "setup-user.yml")
  public void notExistsUserDeleteTest() {
    assertThatThrownBy(() -> service.deleteUser(2L)
    ).isInstanceOfSatisfying(NotFoundException.class, e -> {
      assertThat(e.getMessage()).isEqualTo(
          "ErrorCode:NotFound, Message:指定されたユーザーは存在しません。");
      assertThat(e.getHttpStatus()).isEqualTo(HttpStatus.NOT_FOUND);
      assertThat(e.getErrors()).hasSize(1);
      assertThat(e.getErrors().getFirst().getCode()).isEqualTo(ErrorCode.NOT_FOUND);
      assertThat(e.getErrors().getFirst().getMessage()).isEqualTo(
          "指定されたユーザーは存在しません。");
    });
  }

}
