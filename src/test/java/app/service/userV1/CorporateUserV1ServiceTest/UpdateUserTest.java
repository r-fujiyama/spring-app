package app.service.userV1.CorporateUserV1ServiceTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import app.TransactionInfo;
import app.enums.ErrorCode;
import app.enums.UserStatus;
import app.enums.UserType;
import app.exception.ConflictException;
import app.service.ServiceTest;
import app.service.userV1.CorporateUserV1Service;
import app.service.userV1.parameter.UpdateUserParam;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;

@Import(CorporateUserV1Service.class)
public class UpdateUserTest extends ServiceTest {

  private final static String TEST_DATA_BASE_PATH = "test-data/service/userV1/CorporateUserV1ServiceTest/UpdateUserTest/";

  @Autowired
  private CorporateUserV1Service service;

  @BeforeAll
  public void beforeAll() {
    TransactionInfo.init("test_user", UserType.CORPORATE);
  }

  @Test
  @DataSet(TEST_DATA_BASE_PATH + "setup-user.yml")
  @ExpectedDataSet(TEST_DATA_BASE_PATH + "expected-user.yml")
  public void updateUserTest() {
    var actual = service.updateUser(UpdateUserParam.builder()
        .id(1)
        .name("test_user_2")
        .password("password2")
        .userType(UserType.FREELANCE)
        .firstName("jiro")
        .lastName("chiba")
        .age(21)
        .build());
    assertThat(actual.getId()).isNotZero();
    assertThat(actual.getName()).isEqualTo("test_user_2");
    assertThat(actual.getType()).isEqualTo(UserType.FREELANCE);
    assertThat(actual.getStatus()).isEqualTo(UserStatus.REGISTERED);
    assertThat(actual.getFirstName()).isEqualTo("jiro");
    assertThat(actual.getLastName()).isEqualTo("chiba");
    assertThat(actual.getAge()).isEqualTo(21);
  }

  @Test
  @DataSet(TEST_DATA_BASE_PATH + "setup-user.yml")
  @ExpectedDataSet(TEST_DATA_BASE_PATH + "setup-user.yml")
  public void existsUserNameUpdateTest() {
    assertThatThrownBy(() -> service.updateUser(UpdateUserParam.builder()
        .name("test_user_1")
        .build())
    ).isInstanceOfSatisfying(ConflictException.class, e -> {
      assertThat(e.getMessage()).isEqualTo(
          "ErrorCode:Conflict, Message:ユーザー:'test_user_1'は既に存在します。追加、更新することは出来ません。");
      assertThat(e.getHttpStatus()).isEqualTo(HttpStatus.CONFLICT);
      assertThat(e.getErrors()).hasSize(1);
      assertThat(e.getErrors().getFirst().getCode()).isEqualTo(ErrorCode.CONFLICT);
      assertThat(e.getErrors().getFirst().getMessage()).isEqualTo(
          "ユーザー:'test_user_1'は既に存在します。追加、更新することは出来ません。");
    });
  }

}
