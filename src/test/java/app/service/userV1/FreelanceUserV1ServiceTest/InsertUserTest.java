package app.service.userV1.FreelanceUserV1ServiceTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import app.TransactionInfo;
import app.enums.ErrorCode;
import app.enums.UserStatus;
import app.enums.UserType;
import app.exception.ConflictException;
import app.service.ServiceTest;
import app.service.userV1.FreelanceUserV1Service;
import app.service.userV1.parameter.InsertUserParam;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;

@Import(FreelanceUserV1Service.class)
public class InsertUserTest extends ServiceTest {

  private final static String TEST_DATA_BASE_PATH = "test-data/service/userV1/FreelanceUserV1ServiceTest/InsertUserTest/";

  @Autowired
  private FreelanceUserV1Service service;

  @BeforeAll
  public void beforeAll() {
    TransactionInfo.init("test_user", UserType.FREELANCE);
  }

  @Test
  @DataSet(CLEAN_TABLE_DATA_BASE_PATH + "clean-user.yml")
  @ExpectedDataSet(TEST_DATA_BASE_PATH + "expected-user.yml")
  public void insertUserTest() {
    var actual = service.insertUser(InsertUserParam.builder()
        .userName("test_user_1")
        .password("password")
        .userType(UserType.FREELANCE)
        .firstName("ichiro")
        .lastName("tokyo")
        .age(20)
        .build());
    assertThat(actual.getId()).isNotZero();
    assertThat(actual.getName()).isEqualTo("test_user_1");
    assertThat(actual.getType()).isEqualTo(UserType.FREELANCE);
    assertThat(actual.getStatus()).isEqualTo(UserStatus.REGISTERED);
    assertThat(actual.getFirstName()).isEqualTo("ichiro");
    assertThat(actual.getLastName()).isEqualTo("tokyo");
    assertThat(actual.getAge()).isEqualTo(20);
  }

  @Test
  @DataSet(TEST_DATA_BASE_PATH + "setup-user.yml")
  @ExpectedDataSet(TEST_DATA_BASE_PATH + "setup-user.yml")
  public void existsUserInsertTest() {
    assertThatThrownBy(() -> service.insertUser(InsertUserParam.builder()
        .userName("test_user_1")
        .password("password")
        .userType(UserType.FREELANCE)
        .firstName("ichiro")
        .lastName("tokyo")
        .age(20)
        .build())
    ).isInstanceOfSatisfying(ConflictException.class, e -> {
      assertThat(e.getMessage()).isEqualTo(
          "ErrorCode:Conflict, Message:ユーザー:'test_user_1'は既に存在します。追加することは出来ません。");
      assertThat(e.getHttpStatus()).isEqualTo(HttpStatus.CONFLICT);
      assertThat(e.getErrors()).hasSize(1);
      assertThat(e.getErrors().getFirst().getCode()).isEqualTo(ErrorCode.CONFLICT);
      assertThat(e.getErrors().getFirst().getMessage()).isEqualTo(
          "ユーザー:'test_user_1'は既に存在します。追加することは出来ません。");
    });
  }

}
