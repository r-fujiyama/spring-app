package app.service.userV1.PrivateUserV1ServiceTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import app.enums.UserStatus;
import app.enums.UserType;
import app.service.ServiceTest;
import app.service.userV1.PrivateUserV1Service;
import app.service.userV1.parameter.SearchUsersParam;
import app.service.userV1.result.UserInfo;
import com.github.database.rider.core.api.dataset.DataSet;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

@Import(PrivateUserV1Service.class)
public class SearchUsersTest extends ServiceTest {

  @Autowired
  private PrivateUserV1Service service;

  @DataSet("test-data/service/userV1/PrivateUserV1ServiceTest/SearchUsersTest/users.yml")
  @ParameterizedTest
  @MethodSource("searchUsersTestDataProvider")
  public void searchUsersTest(SearchUsersParam param, UserInfo[] expected) {
    var actual = service.searchUsers(param);
    assertThat(actual).containsExactly(expected);
  }

  private static Stream<Arguments> searchUsersTestDataProvider() {
    return Stream.of(
        // id
        arguments(SearchUsersParam.builder().id(0L).build(), new UserInfo[]{}),
        arguments(SearchUsersParam.builder().id(1L).build(), new UserInfo[]{UserData.ID1}),
        arguments(SearchUsersParam.builder().id(2L).build(), new UserInfo[]{UserData.ID2}),
        arguments(SearchUsersParam.builder().id(3L).build(), new UserInfo[]{UserData.ID3}),
        // name
        arguments(SearchUsersParam.builder().userName("unknown").build(), new UserInfo[]{}),
        arguments(SearchUsersParam.builder().userName("test_user_1").build(), new UserInfo[]{UserData.ID1}),
        arguments(SearchUsersParam.builder().userName("test_user_2").build(), new UserInfo[]{UserData.ID2}),
        arguments(SearchUsersParam.builder().userName("test_user_3").build(), new UserInfo[]{UserData.ID3}),
        // type
        arguments(SearchUsersParam.builder().userType(UserType.UNKNOWN).build(), new UserInfo[]{}),
        arguments(SearchUsersParam.builder().userType(UserType.PRIVATE).build(),
            new UserInfo[]{UserData.ID1, UserData.ID4}),
        arguments(SearchUsersParam.builder().userType(UserType.FREELANCE).build(),
            new UserInfo[]{UserData.ID2, UserData.ID5}),
        arguments(SearchUsersParam.builder().userType(UserType.CORPORATE).build(),
            new UserInfo[]{UserData.ID3, UserData.ID6}),
        // status
        arguments(SearchUsersParam.builder().userStatus(UserStatus.UNKNOWN).build(), new UserInfo[]{}),
        arguments(SearchUsersParam.builder().userStatus(UserStatus.REGISTERED).build(),
            new UserInfo[]{UserData.ID1, UserData.ID4}),
        arguments(SearchUsersParam.builder().userStatus(UserStatus.BLOCKED).build(),
            new UserInfo[]{UserData.ID2, UserData.ID5}),
        arguments(SearchUsersParam.builder().userStatus(UserStatus.DELETED).build(),
            new UserInfo[]{UserData.ID3, UserData.ID6}),
        // first_name
        arguments(SearchUsersParam.builder().firstName("unknown").build(), new UserInfo[]{}),
        arguments(SearchUsersParam.builder().firstName("ichiro").build(), new UserInfo[]{UserData.ID1, UserData.ID4}),
        arguments(SearchUsersParam.builder().firstName("giro").build(), new UserInfo[]{UserData.ID2, UserData.ID5}),
        arguments(SearchUsersParam.builder().firstName("saburo").build(), new UserInfo[]{UserData.ID3, UserData.ID6}),
        // last_name
        arguments(SearchUsersParam.builder().lastName("unknown").build(), new UserInfo[]{}),
        arguments(SearchUsersParam.builder().lastName("tokyo").build(), new UserInfo[]{UserData.ID1, UserData.ID4}),
        arguments(SearchUsersParam.builder().lastName("chiba").build(), new UserInfo[]{UserData.ID2, UserData.ID5}),
        arguments(SearchUsersParam.builder().lastName("kanagawa").build(), new UserInfo[]{UserData.ID3, UserData.ID6}),
        // age
        arguments(SearchUsersParam.builder().age(0).build(), new UserInfo[]{}),
        arguments(SearchUsersParam.builder().age(20).build(), new UserInfo[]{UserData.ID1}),
        arguments(SearchUsersParam.builder().age(21).build(), new UserInfo[]{UserData.ID2}),
        arguments(SearchUsersParam.builder().age(22).build(), new UserInfo[]{UserData.ID3}),
        // all
        arguments(SearchUsersParam.builder()
            .id(1L)
            .userName("test_user_1")
            .userType(UserType.PRIVATE)
            .userStatus(UserStatus.REGISTERED)
            .firstName("ichiro")
            .lastName("tokyo")
            .age(20)
            .build(), new UserInfo[]{UserData.ID1}),
        arguments(SearchUsersParam.builder()
            .id(2L)
            .userName("test_user_2")
            .userType(UserType.FREELANCE)
            .userStatus(UserStatus.BLOCKED)
            .firstName("giro")
            .lastName("chiba")
            .age(21)
            .build(), new UserInfo[]{UserData.ID2}),
        arguments(SearchUsersParam.builder()
            .id(3L)
            .userName("test_user_3")
            .userType(UserType.CORPORATE)
            .userStatus(UserStatus.DELETED)
            .firstName("saburo")
            .lastName("kanagawa")
            .age(22)
            .build(), new UserInfo[]{UserData.ID3})
    );
  }

  private static class UserData {

    private static final UserInfo ID1 = UserInfo.builder()
        .id(1L)
        .name("test_user_1")
        .type(UserType.PRIVATE)
        .status(UserStatus.REGISTERED)
        .firstName("ichiro")
        .lastName("tokyo")
        .age(20)
        .build();

    private static final UserInfo ID2 = UserInfo.builder()
        .id(2L)
        .name("test_user_2")
        .type(UserType.FREELANCE)
        .status(UserStatus.BLOCKED)
        .firstName("giro")
        .lastName("chiba")
        .age(21)
        .build();

    private static final UserInfo ID3 = UserInfo.builder()
        .id(3L)
        .name("test_user_3")
        .type(UserType.CORPORATE)
        .status(UserStatus.DELETED)
        .firstName("saburo")
        .lastName("kanagawa")
        .age(22)
        .build();

    private static final UserInfo ID4 = UserInfo.builder()
        .id(4L)
        .name("test_user_4")
        .type(UserType.PRIVATE)
        .status(UserStatus.REGISTERED)
        .firstName("ichiro")
        .lastName("tokyo")
        .age(23)
        .build();

    private static final UserInfo ID5 = UserInfo.builder()
        .id(5L)
        .name("test_user_5")
        .type(UserType.FREELANCE)
        .status(UserStatus.BLOCKED)
        .firstName("giro")
        .lastName("chiba")
        .age(24)
        .build();

    private static final UserInfo ID6 = UserInfo.builder()
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
