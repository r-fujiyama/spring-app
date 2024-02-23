package app.service.userV1.CorporateUserV1ServiceTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import app.enums.UserStatus;
import app.enums.UserType;
import app.service.ServiceTest;
import app.service.userV1.CorporateUserV1Service;
import app.service.userV1.parameter.UserSearchParam;
import app.service.userV1.result.User;
import com.github.database.rider.core.api.dataset.DataSet;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

@Import(CorporateUserV1Service.class)
public class UserSearchTest extends ServiceTest {

  @Autowired
  private CorporateUserV1Service service;

  @DataSet("test-data/service/userV1/CorporateUserV1ServiceTest/UserSearchTest/users.yml")
  @ParameterizedTest
  @MethodSource("userSearchTestDataProvider")
  public void userSearchTest(UserSearchParam param, User[] expected) {
    var actual = service.userSearch(param);
    assertThat(actual).containsExactly(expected);
  }

  private static Stream<Arguments> userSearchTestDataProvider() {
    return Stream.of(
        // id
        arguments(UserSearchParam.builder().id(0L).build(), new User[]{}),
        arguments(UserSearchParam.builder().id(1L).build(), new User[]{UserData.ID1}),
        arguments(UserSearchParam.builder().id(2L).build(), new User[]{UserData.ID2}),
        arguments(UserSearchParam.builder().id(3L).build(), new User[]{UserData.ID3}),
        // name
        arguments(UserSearchParam.builder().userName("unknown").build(), new User[]{}),
        arguments(UserSearchParam.builder().userName("test_user_1").build(), new User[]{UserData.ID1}),
        arguments(UserSearchParam.builder().userName("test_user_2").build(), new User[]{UserData.ID2}),
        arguments(UserSearchParam.builder().userName("test_user_3").build(), new User[]{UserData.ID3}),
        // type
        arguments(UserSearchParam.builder().userType(UserType.UNKNOWN).build(), new User[]{}),
        arguments(UserSearchParam.builder().userType(UserType.PRIVATE).build(), new User[]{UserData.ID1, UserData.ID4}),
        arguments(UserSearchParam.builder().userType(UserType.FREELANCE).build(),
            new User[]{UserData.ID2, UserData.ID5}),
        arguments(UserSearchParam.builder().userType(UserType.CORPORATE).build(),
            new User[]{UserData.ID3, UserData.ID6}),
        // status
        arguments(UserSearchParam.builder().userStatus(UserStatus.UNKNOWN).build(), new User[]{}),
        arguments(UserSearchParam.builder().userStatus(UserStatus.REGISTERED).build(),
            new User[]{UserData.ID1, UserData.ID4}),
        arguments(UserSearchParam.builder().userStatus(UserStatus.BLOCKED).build(),
            new User[]{UserData.ID2, UserData.ID5}),
        arguments(UserSearchParam.builder().userStatus(UserStatus.DELETED).build(),
            new User[]{UserData.ID3, UserData.ID6}),
        // first_name
        arguments(UserSearchParam.builder().firstName("unknown").build(), new User[]{}),
        arguments(UserSearchParam.builder().firstName("ichiro").build(), new User[]{UserData.ID1, UserData.ID4}),
        arguments(UserSearchParam.builder().firstName("giro").build(), new User[]{UserData.ID2, UserData.ID5}),
        arguments(UserSearchParam.builder().firstName("saburo").build(), new User[]{UserData.ID3, UserData.ID6}),
        // last_name
        arguments(UserSearchParam.builder().lastName("unknown").build(), new User[]{}),
        arguments(UserSearchParam.builder().lastName("tokyo").build(), new User[]{UserData.ID1, UserData.ID4}),
        arguments(UserSearchParam.builder().lastName("chiba").build(), new User[]{UserData.ID2, UserData.ID5}),
        arguments(UserSearchParam.builder().lastName("kanagawa").build(), new User[]{UserData.ID3, UserData.ID6}),
        // age
        arguments(UserSearchParam.builder().age(0).build(), new User[]{}),
        arguments(UserSearchParam.builder().age(20).build(), new User[]{UserData.ID1}),
        arguments(UserSearchParam.builder().age(21).build(), new User[]{UserData.ID2}),
        arguments(UserSearchParam.builder().age(22).build(), new User[]{UserData.ID3}),
        // all
        arguments(UserSearchParam.builder()
            .id(1L)
            .userName("test_user_1")
            .userType(UserType.PRIVATE)
            .userStatus(UserStatus.REGISTERED)
            .firstName("ichiro")
            .lastName("tokyo")
            .age(20)
            .build(), new User[]{UserData.ID1}),
        arguments(UserSearchParam.builder()
            .id(2L)
            .userName("test_user_2")
            .userType(UserType.FREELANCE)
            .userStatus(UserStatus.BLOCKED)
            .firstName("giro")
            .lastName("chiba")
            .age(21)
            .build(), new User[]{UserData.ID2}),
        arguments(UserSearchParam.builder()
            .id(3L)
            .userName("test_user_3")
            .userType(UserType.CORPORATE)
            .userStatus(UserStatus.DELETED)
            .firstName("saburo")
            .lastName("kanagawa")
            .age(22)
            .build(), new User[]{UserData.ID3})
    );
  }

  private static class UserData {

    private static final User ID1 = User.builder()
        .id(1L)
        .name("test_user_1")
        .type(UserType.PRIVATE)
        .status(UserStatus.REGISTERED)
        .firstName("ichiro")
        .lastName("tokyo")
        .age(20)
        .build();

    private static final User ID2 = User.builder()
        .id(2L)
        .name("test_user_2")
        .type(UserType.FREELANCE)
        .status(UserStatus.BLOCKED)
        .firstName("giro")
        .lastName("chiba")
        .age(21)
        .build();

    private static final User ID3 = User.builder()
        .id(3L)
        .name("test_user_3")
        .type(UserType.CORPORATE)
        .status(UserStatus.DELETED)
        .firstName("saburo")
        .lastName("kanagawa")
        .age(22)
        .build();

    private static final User ID4 = User.builder()
        .id(4L)
        .name("test_user_4")
        .type(UserType.PRIVATE)
        .status(UserStatus.REGISTERED)
        .firstName("ichiro")
        .lastName("tokyo")
        .age(23)
        .build();

    private static final User ID5 = User.builder()
        .id(5L)
        .name("test_user_5")
        .type(UserType.FREELANCE)
        .status(UserStatus.BLOCKED)
        .firstName("giro")
        .lastName("chiba")
        .age(24)
        .build();

    private static final User ID6 = User.builder()
        .id(6L)
        .name("test_user_6")
        .type(UserType.CORPORATE)
        .status(UserStatus.DELETED)
        .firstName("saburo")
        .lastName("kanagawa")
        .age(25)
        .build();
  }

}
