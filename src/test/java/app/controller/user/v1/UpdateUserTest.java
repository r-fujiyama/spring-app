package app.controller.user.v1;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import app.controller.ControllerTest;
import app.controller.response.Error;
import app.controller.response.Response;
import app.controller.user.response.User;
import app.controller.user.v1.request.UpdateUserRequest;
import app.controller.user.v1.response.UpdateUserResponse;
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
public class UpdateUserTest extends ControllerTest {

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
    when(userService.updateUser(any())).thenReturn(user);
  }

  @Test
  public void OK200() throws Exception {
    var req = new UpdateUserRequest("test_user_1", "password", UserType.PRIVATE, "taro", "tokyo", 20);
    var actual = mockMvc.perform(put("/v1/user/{id}", 1)
            .contentType(MediaType.APPLICATION_JSON)
            .content(JSONUtils.toJSON(req)))
        .andExpect(status().isOk())
        .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

    var expected = JSONUtils.toJSON(new UpdateUserResponse(
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
  public void validationErrorTest(String id, UserType userType, String firstName, String lastName,
      Integer age, Error error) throws Exception {
    var req = new UpdateUserRequest(null, null, userType, firstName, lastName, age);
    var res = mockMvc.perform(put("/v1/user/{id}", id)
            .contentType(MediaType.APPLICATION_JSON)
            .content(JSONUtils.toJSON(req)))
        .andExpect(status().isBadRequest())
        .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

    var actual = JSONUtils.toObject(res, Response.class);
    var expected = new Response(error);
    assertThat(actual.getStatus()).isEqualTo(expected.getStatus());
    assertThat(actual.getErrors()).containsExactlyInAnyOrder(expected.getErrors().toArray(new Error[0]));
  }

  static Stream<Arguments> validationErrorProvider() {
    return Stream.of(
        // ユーザー名
        arguments("a", UserType.PRIVATE, "taro", "tokyo", "20",
            new Error(ErrorCode.BAD_REQUEST, "IDに指定された値の型に誤りがあります。")),
        arguments("0", UserType.PRIVATE, "taro", "tokyo", "20",
            new Error(ErrorCode.BAD_REQUEST, "IDは1~9223372036854775807以内の値を入力してください。")),
        arguments("9223372036854775808", UserType.PRIVATE, "taro", "tokyo", "20",
            new Error(ErrorCode.BAD_REQUEST, "IDに指定された値の型に誤りがあります。")),
        // ユーザタイプ
        arguments("1", null, "taro", "tokyo", "20",
            new Error(ErrorCode.BAD_REQUEST, "ユーザータイプにNULLは許可されていません。")),
        arguments("1", UserType.UNKNOWN, "taro", "tokyo", "20",
            new Error(ErrorCode.BAD_REQUEST, "ユーザータイプに指定された値は許可されていません。")),
        // 名前
        arguments("1", UserType.PRIVATE, null, "tokyo", "20",
            new Error(ErrorCode.BAD_REQUEST, "名前にNULLは許可されていません。")),
        arguments("1", UserType.PRIVATE, "", "tokyo", "20",
            new Error(ErrorCode.BAD_REQUEST, "名前は^[a-zA-Z]+$の形式で入力してください。")),
        arguments("1", UserType.PRIVATE, " ", "tokyo", "20",
            new Error(ErrorCode.BAD_REQUEST, "名前は^[a-zA-Z]+$の形式で入力してください。")),
        arguments("1", UserType.PRIVATE, "aaa!aaa", "tokyo", "20",
            new Error(ErrorCode.BAD_REQUEST, "名前は^[a-zA-Z]+$の形式で入力してください。")),
        // 苗字
        arguments("1", UserType.PRIVATE, "taro", null, "20",
            new Error(ErrorCode.BAD_REQUEST, "苗字にNULLは許可されていません。")),
        arguments("1", UserType.PRIVATE, "taro", "", "20",
            new Error(ErrorCode.BAD_REQUEST, "苗字は^[a-zA-Z]+$の形式で入力してください。")),
        arguments("1", UserType.PRIVATE, "taro", " ", "20",
            new Error(ErrorCode.BAD_REQUEST, "苗字は^[a-zA-Z]+$の形式で入力してください。")),
        arguments("1", UserType.PRIVATE, "taro", "aaa!aaa", "20",
            new Error(ErrorCode.BAD_REQUEST, "苗字は^[a-zA-Z]+$の形式で入力してください。")),
        // 年齢
        arguments("1", UserType.PRIVATE, "taro", "tokyo", null,
            new Error(ErrorCode.BAD_REQUEST, "年齢にNULLは許可されていません。")),
        arguments("1", UserType.PRIVATE, "taro", "tokyo", "-1",
            new Error(ErrorCode.BAD_REQUEST, "年齢は0~999以内の値を入力してください。")),
        arguments("1", UserType.PRIVATE, "taro", "tokyo", "1000",
            new Error(ErrorCode.BAD_REQUEST, "年齢は0~999以内の値を入力してください。"))
    );
  }

  @Test
  public void unsupportedMediaTypesTest() throws Exception {
    var req = new UpdateUserRequest("test_user_1", "password", UserType.PRIVATE, "taro", "tokyo", 20);
    var actual = mockMvc.perform(put("/v1/user/{id}", 1)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .content(JSONUtils.toJSON(req)))
        .andExpect(status().isBadRequest())
        .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

    var expected = JSONUtils.toJSON(
        new Response(new Error(ErrorCode.BAD_REQUEST, "サポートしていないContent-Typeが指定されています。")));
    assertThat(actual).isEqualTo(expected);
  }

}
