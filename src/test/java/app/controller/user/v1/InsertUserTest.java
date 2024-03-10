package app.controller.user.v1;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import app.controller.ControllerTest;
import app.controller.response.Error;
import app.controller.response.Response;
import app.controller.user.response.User;
import app.controller.user.v1.request.InsertUserRequest;
import app.controller.user.v1.response.InsertUserResponse;
import app.enums.ErrorCode;
import app.enums.UserStatus;
import app.enums.UserType;
import app.service.userV1.UserV1Service;
import app.service.userV1.result.UserInfo;
import app.util.JSONUtils;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

@WebMvcTest(UserV1Controller.class)
public class InsertUserTest extends ControllerTest {

  @MockBean
  UserV1Service userService;

  @BeforeEach
  public void beforeEach() {
    var user = UserInfo.builder()
        .id(1L)
        .name("user-name")
        .type(UserType.PRIVATE)
        .status(UserStatus.REGISTERED)
        .firstName("taro")
        .lastName("tokyo")
        .age(20)
        .build();
    when(userService.insertUser(any())).thenReturn(user);
  }

  @Test
  public void OK200() throws Exception {
    var req = new InsertUserRequest("user-name", "password123!#$%&@", UserType.PRIVATE, "taro", "tokyo", 20);
    var actual = mockMvc.perform(post("/v1/user")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JSONUtils.toJSON(req)))
        .andExpect(status().isOk())
        .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

    var expected = JSONUtils.toJSON(new InsertUserResponse(
        User.builder()
            .id(1L)
            .name("user-name")
            .type(UserType.PRIVATE)
            .status(UserStatus.REGISTERED)
            .firstName("taro")
            .lastName("tokyo")
            .age(20)
            .build()
    ));
    assertThat(actual).isEqualTo(expected);
  }

  @ParameterizedTest
  @MethodSource("validationErrorProvider")
  public void validationErrorTest(String userName, String password, UserType userType, String firstName,
      String lastName, Integer age, Error[] errors) throws Exception {
    var req = new InsertUserRequest(userName, password, userType, firstName, lastName, age);
    var res = mockMvc.perform(post("/v1/user")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JSONUtils.toJSON(req)))
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
        arguments(null, "password", UserType.PRIVATE, "taro", "tokyo", "20",
            new Error[]{new Error(ErrorCode.BAD_REQUEST, "ユーザー名にNULLは許可されていません。")}),
        arguments("", "password", UserType.PRIVATE, "taro", "tokyo", "20",
            new Error[]{new Error(ErrorCode.BAD_REQUEST, "ユーザー名は1~256文字以内で入力してください。"),
                new Error(ErrorCode.BAD_REQUEST, "ユーザー名は^.*[1-9a-zA-Z-_]$の形式で入力してください。")}),
        arguments("a".repeat(257), "password", UserType.PRIVATE, "taro", "tokyo", "20",
            new Error[]{new Error(ErrorCode.BAD_REQUEST, "ユーザー名は1~256文字以内で入力してください。")}),
        arguments(">".repeat(8), "password", UserType.PRIVATE, "taro", "tokyo", "20",
            new Error[]{new Error(ErrorCode.BAD_REQUEST, "ユーザー名は^.*[1-9a-zA-Z-_]$の形式で入力してください。")}),
        // パスワード
        arguments("user-name", null, UserType.PRIVATE, "taro", "tokyo", "20",
            new Error[]{new Error(ErrorCode.BAD_REQUEST, "パスワードにNULLは許可されていません。")}),
        arguments("user-name", "", UserType.PRIVATE, "taro", "tokyo", "20",
            new Error[]{new Error(ErrorCode.BAD_REQUEST, "パスワードは8~64文字以内で入力してください。"),
                new Error(ErrorCode.BAD_REQUEST, "パスワードは^.*[1-9a-zA-Z!#$%&@]$の形式で入力してください。")}),
        arguments("user-name", "a".repeat(7), UserType.PRIVATE, "taro", "tokyo", "20",
            new Error[]{new Error(ErrorCode.BAD_REQUEST, "パスワードは8~64文字以内で入力してください。")}),
        arguments("user-name", "a".repeat(65), UserType.PRIVATE, "taro", "tokyo", "20",
            new Error[]{new Error(ErrorCode.BAD_REQUEST, "パスワードは8~64文字以内で入力してください。")}),
        arguments("user-name", "<", UserType.PRIVATE, "taro", "tokyo", "20",
            new Error[]{new Error(ErrorCode.BAD_REQUEST, "パスワードは8~64文字以内で入力してください。"),
                new Error(ErrorCode.BAD_REQUEST, "パスワードは^.*[1-9a-zA-Z!#$%&@]$の形式で入力してください。")}),
        arguments("user-name", "<".repeat(8), UserType.PRIVATE, "taro", "tokyo", "20",
            new Error[]{
                new Error(ErrorCode.BAD_REQUEST, "パスワードは^.*[1-9a-zA-Z!#$%&@]$の形式で入力してください。")}),
        // ユーザタイプ
        arguments("user-name", "password", null, "taro", "tokyo", "20",
            new Error[]{new Error(ErrorCode.BAD_REQUEST, "ユーザータイプにNULLは許可されていません。")}),
        arguments("user-name", "password", UserType.UNKNOWN, "taro", "tokyo", "20",
            new Error[]{new Error(ErrorCode.BAD_REQUEST, "ユーザータイプに指定された値は許可されていません。")}),
        // 名前
        arguments("user-name", "password", UserType.PRIVATE, null, "tokyo", "20",
            new Error[]{new Error(ErrorCode.BAD_REQUEST, "名前にNULLは許可されていません。")}),
        arguments("user-name", "password", UserType.PRIVATE, "", "tokyo", "20",
            new Error[]{new Error(ErrorCode.BAD_REQUEST, "名前は^[a-zA-Z]+$の形式で入力してください。")}),
        arguments("user-name", "password", UserType.PRIVATE, " ", "tokyo", "20",
            new Error[]{new Error(ErrorCode.BAD_REQUEST, "名前は^[a-zA-Z]+$の形式で入力してください。")}),
        arguments("user-name", "password", UserType.PRIVATE, "aaa!aaa", "tokyo", "20",
            new Error[]{new Error(ErrorCode.BAD_REQUEST, "名前は^[a-zA-Z]+$の形式で入力してください。")}),
        // 苗字
        arguments("user-name", "password", UserType.PRIVATE, "taro", null, "20",
            new Error[]{new Error(ErrorCode.BAD_REQUEST, "苗字にNULLは許可されていません。")}),
        arguments("user-name", "password", UserType.PRIVATE, "taro", "", "20",
            new Error[]{new Error(ErrorCode.BAD_REQUEST, "苗字は^[a-zA-Z]+$の形式で入力してください。")}),
        arguments("user-name", "password", UserType.PRIVATE, "taro", " ", "20",
            new Error[]{new Error(ErrorCode.BAD_REQUEST, "苗字は^[a-zA-Z]+$の形式で入力してください。")}),
        arguments("user-name", "password", UserType.PRIVATE, "taro", "aaa!aaa", "20",
            new Error[]{new Error(ErrorCode.BAD_REQUEST, "苗字は^[a-zA-Z]+$の形式で入力してください。")}),
        // 年齢
        arguments("user-name", "password", UserType.PRIVATE, "taro", "tokyo", null,
            new Error[]{new Error(ErrorCode.BAD_REQUEST, "年齢にNULLは許可されていません。")}),
        arguments("user-name", "password", UserType.PRIVATE, "taro", "tokyo", "-1",
            new Error[]{new Error(ErrorCode.BAD_REQUEST, "年齢は0~999以内の値を入力してください。")}),
        arguments("user-name", "password", UserType.PRIVATE, "taro", "tokyo", "1000",
            new Error[]{new Error(ErrorCode.BAD_REQUEST, "年齢は0~999以内の値を入力してください。")})
    );
  }

  @Test
  public void unsupportedMediaTypesTest() throws Exception {
    var req = new InsertUserRequest("user-name", "password", UserType.PRIVATE, "taro", "tokyo", 20);
    var actual = mockMvc.perform(post("/v1/user")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .content(JSONUtils.toJSON(req)))
        .andExpect(status().isBadRequest())
        .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

    var expected = JSONUtils.toJSON(
        new Response(new Error(ErrorCode.BAD_REQUEST, "サポートしていないContent-Typeが指定されています。")));
    assertThat(actual).isEqualTo(expected);
  }

}
