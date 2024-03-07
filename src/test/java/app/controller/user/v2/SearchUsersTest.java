package app.controller.user.v2;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import app.controller.ControllerTest;
import app.controller.response.Error;
import app.controller.response.Response;
import app.controller.user.response.User;
import app.controller.user.v1.response.SearchUsersResponse;
import app.enums.ErrorCode;
import app.enums.UserStatus;
import app.enums.UserType;
import app.service.userV2.UserV2Service;
import app.util.JSONUtils;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@WebMvcTest(UserV2Controller.class)
public class SearchUsersTest extends ControllerTest {

  @MockBean
  UserV2Service userService;

  @BeforeEach
  public void beforeEach() {
    var users = new ArrayList<app.service.userV2.result.User>();
    users.add(app.service.userV2.result.User.builder()
        .id(1L)
        .name("user-name")
        .type(UserType.PRIVATE)
        .status(UserStatus.REGISTERED)
        .firstName("taro")
        .lastName("tokyo")
        .age(20)
        .build());
    when(userService.searchUsers(any())).thenReturn(users);
  }

  @Test
  public void OK200() throws Exception {
    var actual = mockMvc.perform(get("/v2/user")
            .param("userName", "user-name")
            .param("userType", UserType.PRIVATE.getValue())
            .param("firstName", "taro")
            .param("lastName", "tokyo")
            .param("age", "20"))
        .andExpect(status().isOk())
        .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

    var users = new ArrayList<User>();
    users.add(User.builder()
        .id(1L)
        .name("user-name")
        .type(UserType.PRIVATE)
        .status(UserStatus.REGISTERED)
        .firstName("taro")
        .lastName("tokyo")
        .age(20)
        .build());
    var expected = JSONUtils.toJSON(new SearchUsersResponse(users));
    assertThat(actual).isEqualTo(expected);
  }

  @ParameterizedTest
  @MethodSource("validationErrorProvider")
  public void validationErrorTest(String userName, String userType, String firstName, String lastName,
      String age, Error[] errors) throws Exception {
    var res = mockMvc.perform(get("/v2/user")
            .param("userName", userName)
            .param("userType", userType)
            .param("firstName", firstName)
            .param("lastName", lastName)
            .param("age", age))
        .andExpect(status().isBadRequest())
        .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

    var actual = JSONUtils.toObject(res, Response.class);
    var expected = new Response(errors);
    assertThat(actual.getStatus()).isEqualTo(expected.getStatus());
    assertThat(actual.getErrors()).containsExactlyInAnyOrder(expected.getErrors().toArray(new Error[0]));
  }

  static Stream<Arguments> validationErrorProvider() {
    return Stream.of(
        // ユーザー名
        arguments("", UserType.PRIVATE.getValue(), "taro", "tokyo", "20",
            new Error[]{new Error(ErrorCode.BAD_REQUEST, "ユーザー名は^.*[1-9a-zA-Z-_]$の形式で入力してください。"),
                new Error(ErrorCode.BAD_REQUEST, "ユーザー名は1~256文字以内で入力してください。")}),
        arguments("a".repeat(257), UserType.PRIVATE.getValue(), "taro", "tokyo", "20",
            new Error[]{new Error(ErrorCode.BAD_REQUEST, "ユーザー名は1~256文字以内で入力してください。")}),
        arguments(" ", UserType.PRIVATE.getValue(), "taro", "tokyo", "20",
            new Error[]{new Error(ErrorCode.BAD_REQUEST, "ユーザー名は^.*[1-9a-zA-Z-_]$の形式で入力してください。")}),
        arguments("!", UserType.PRIVATE.getValue(), "taro", "tokyo", "20",
            new Error[]{new Error(ErrorCode.BAD_REQUEST, "ユーザー名は^.*[1-9a-zA-Z-_]$の形式で入力してください。")}),
        // ユーザタイプ
        arguments("1", "", "taro", "tokyo", "20",
            new Error[]{new Error(ErrorCode.BAD_REQUEST, "ユーザータイプに指定された値は許可されていません。")}),
        arguments("1", "　", "taro", "tokyo", "20",
            new Error[]{new Error(ErrorCode.BAD_REQUEST, "ユーザータイプに指定された値は許可されていません。")}),
        arguments("1", UserType.UNKNOWN.getValue(), "taro", "tokyo", "20",
            new Error[]{new Error(ErrorCode.BAD_REQUEST, "ユーザータイプに指定された値は許可されていません。")}),
        arguments("1", "aaa", "taro", "tokyo", "20",
            new Error[]{new Error(ErrorCode.BAD_REQUEST, "ユーザータイプに指定された値は許可されていません。")}),
        // 名前
        arguments("1", UserType.PRIVATE.getValue(), "", "tokyo", "20",
            new Error[]{new Error(ErrorCode.BAD_REQUEST, "名前は^[a-zA-Z]+$の形式で入力してください。")}),
        arguments("1", UserType.PRIVATE.getValue(), " ", "tokyo", "20",
            new Error[]{new Error(ErrorCode.BAD_REQUEST, "名前は^[a-zA-Z]+$の形式で入力してください。")}),
        arguments("1", UserType.PRIVATE.getValue(), "aaa!aaa", "tokyo", "20",
            new Error[]{new Error(ErrorCode.BAD_REQUEST, "名前は^[a-zA-Z]+$の形式で入力してください。")}),
        // 苗字
        arguments("1", UserType.PRIVATE.getValue(), "taro", "", "20",
            new Error[]{new Error(ErrorCode.BAD_REQUEST, "苗字は^[a-zA-Z]+$の形式で入力してください。")}),
        arguments("1", UserType.PRIVATE.getValue(), "taro", " ", "20",
            new Error[]{new Error(ErrorCode.BAD_REQUEST, "苗字は^[a-zA-Z]+$の形式で入力してください。")}),
        arguments("1", UserType.PRIVATE.getValue(), "taro", "aaa!aaa", "20",
            new Error[]{new Error(ErrorCode.BAD_REQUEST, "苗字は^[a-zA-Z]+$の形式で入力してください。")}),
        // 年齢
        arguments("1", UserType.PRIVATE.getValue(), "taro", "tokyo", "aaa",
            new Error[]{new Error(ErrorCode.BAD_REQUEST, "年齢に指定された値の型に誤りがあります。")}),
        arguments("1", UserType.PRIVATE.getValue(), "taro", "tokyo", "-1",
            new Error[]{new Error(ErrorCode.BAD_REQUEST, "年齢は0~999以内の値を入力してください。")}),
        arguments("1", UserType.PRIVATE.getValue(), "taro", "tokyo", "1000",
            new Error[]{new Error(ErrorCode.BAD_REQUEST, "年齢は0~999以内の値を入力してください。")})
    );
  }

}
