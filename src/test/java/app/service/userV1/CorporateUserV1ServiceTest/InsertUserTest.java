package app.service.userV1.CorporateUserV1ServiceTest;

import static org.assertj.core.api.Assertions.assertThat;

import app.TransactionInfo;
import app.enums.UserStatus;
import app.enums.UserType;
import app.service.ServiceTest;
import app.service.userV1.CorporateUserV1Service;
import app.service.userV1.parameter.InsertUserParam;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

@Import(CorporateUserV1Service.class)
public class InsertUserTest extends ServiceTest {

  private final static String TEST_DATA_BASE_PATH = "test-data/service/userV1/CorporateUserV1ServiceTest/InsertUserTest/";

  @Autowired
  private CorporateUserV1Service service;

  @BeforeAll
  public void beforeAll() {
    TransactionInfo.init("test_user", UserType.CORPORATE);
  }

  @Test
  @DataSet(TEST_DATA_BASE_PATH + "clean-user.yml")
  @ExpectedDataSet(TEST_DATA_BASE_PATH + "expected-user.yml")
  public void insertUserTest() {
    var actual = service.insertUser(InsertUserParam.builder()
        .userName("test_user_1")
        .password("password")
        .userType(UserType.CORPORATE)
        .firstName("ichiro")
        .lastName("tokyo")
        .age(20)
        .build());
    assertThat(actual.getId()).isNotZero();
    assertThat(actual.getName()).isEqualTo("test_user_1");
    assertThat(actual.getType()).isEqualTo(UserType.CORPORATE);
    assertThat(actual.getStatus()).isEqualTo(UserStatus.REGISTERED);
    assertThat(actual.getFirstName()).isEqualTo("ichiro");
    assertThat(actual.getLastName()).isEqualTo("tokyo");
    assertThat(actual.getAge()).isEqualTo(20);
  }

}
