package app.controller.user.v1;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;
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
import app.util.JSONUtils;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;

@WebMvcTest(controllers = UserController.class)
public class InsertUserTest extends ControllerTest {

  @Test
  public void OK200() throws Exception {
    var req = new InsertUserRequest(UserType.PRIVATE, "taro", "nihon", 20);
    var json = mockMvc.perform(post("/v1/user/{userID}", 1)
            .contentType(MediaType.APPLICATION_JSON)
            .content(JSONUtils.convertToJSON(req)))
        .andExpect(status().isOk())
        .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

    var actual = JSONUtils.convertToObject(json, InsertUserResponse.class);
    var expected = new InsertUserResponse(
        User.builder()
            .id(1L)
            .type(UserType.PRIVATE)
            .status(UserStatus.REGISTERED)
            .firstName("taro")
            .lastName("nihon")
            .age(20)
            .build()
    );
    assertThat(actual).isEqualTo(expected);
  }

  @ParameterizedTest
  @MethodSource("validationErrorProvider")
  public void validationErrorTest(String userID, UserType userType, String firstName, String lastName,
      Integer age, Error error) throws Exception {
    var req = new InsertUserRequest(userType, firstName, lastName, age);
    var json = mockMvc.perform(post("/v1/user/{userID}", userID)
            .contentType(MediaType.APPLICATION_JSON)
            .content(JSONUtils.convertToJSON(req)))
        .andExpect(status().isBadRequest())
        .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

    var actual = JSONUtils.convertToObject(json, Response.class);
    var expected = new Response(error);
    assertThat(actual).isEqualTo(expected);
  }

  static Stream<Arguments> validationErrorProvider() {
    return Stream.of(
        // ユーザーID
        arguments("a", UserType.PRIVATE, "taro", "nihon", "20",
            new Error(ErrorCode.BAD_REQUEST, "ユーザーIDに指定された値の型に誤りがあります。")),
        arguments("0", UserType.PRIVATE, "taro", "nihon", "20",
            new Error(ErrorCode.BAD_REQUEST, "ユーザーIDは1~9223372036854775807以内の値を入力してください。")),
        arguments("9223372036854775808", UserType.PRIVATE, "taro", "nihon", "20",
            new Error(ErrorCode.BAD_REQUEST, "ユーザーIDに指定された値の型に誤りがあります。")),
        // ユーザタイプ
        arguments("1", null, "taro", "nihon", "20",
            new Error(ErrorCode.BAD_REQUEST, "ユーザータイプに値が入力されていません。")),
        arguments("1", UserType.UNKNOWN, "taro", "nihon", "20",
            new Error(ErrorCode.BAD_REQUEST, "ユーザータイプに指定された値は許可されていません。")),
        // 名前
        arguments("1", UserType.PRIVATE, null, "nihon", "20",
            new Error(ErrorCode.BAD_REQUEST, "名前にNULL、空文字、空白は許可されていません。")),
        arguments("1", UserType.PRIVATE, "", "nihon", "20",
            new Error(ErrorCode.BAD_REQUEST, "名前にNULL、空文字、空白は許可されていません。")),
        arguments("1", UserType.PRIVATE, " ", "nihon", "20",
            new Error(ErrorCode.BAD_REQUEST, "名前にNULL、空文字、空白は許可されていません。")),
        arguments("1", UserType.PRIVATE, "aaa!aaa", "nihon", "20",
            new Error(ErrorCode.BAD_REQUEST, "名前は^[a-zA-Z]+$の形式で入力してください。")),
        // 苗字
        arguments("1", UserType.PRIVATE, "taro", null, "20",
            new Error(ErrorCode.BAD_REQUEST, "苗字にNULL、空文字、空白は許可されていません。")),
        arguments("1", UserType.PRIVATE, "taro", "", "20",
            new Error(ErrorCode.BAD_REQUEST, "苗字にNULL、空文字、空白は許可されていません。")),
        arguments("1", UserType.PRIVATE, "taro", " ", "20",
            new Error(ErrorCode.BAD_REQUEST, "苗字にNULL、空文字、空白は許可されていません。")),
        arguments("1", UserType.PRIVATE, "taro", "aaa!aaa", "20",
            new Error(ErrorCode.BAD_REQUEST, "苗字は^[a-zA-Z]+$の形式で入力してください。")),
        // 年齢
        arguments("1", UserType.PRIVATE, "taro", "nihon", null,
            new Error(ErrorCode.BAD_REQUEST, "年齢に値が入力されていません。")),
        arguments("1", UserType.PRIVATE, "taro", "nihon", "-1",
            new Error(ErrorCode.BAD_REQUEST, "年齢は0~999以内の値を入力してください。")),
        arguments("1", UserType.PRIVATE, "taro", "nihon", "1000",
            new Error(ErrorCode.BAD_REQUEST, "年齢は0~999以内の値を入力してください。"))
    );
  }

  @Test
  public void unsupportedMediaTypesTest() throws Exception {
    var req = new InsertUserRequest(UserType.PRIVATE, "taro", "nihon", 20);
    var json = mockMvc.perform(post("/v1/user/{userID}", 1)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .content(JSONUtils.convertToJSON(req)))
        .andExpect(status().isBadRequest())
        .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

    var actual = JSONUtils.convertToObject(json, Response.class);
    var expected = new Response(new Error(ErrorCode.BAD_REQUEST, "サポートしていないContent-Typeが指定されています。"));
    assertThat(actual).isEqualTo(expected);
  }

}
