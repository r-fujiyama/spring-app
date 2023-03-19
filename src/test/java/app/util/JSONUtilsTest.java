package app.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import app.controller.response.Error;
import app.controller.response.Response;
import app.enums.ErrorCode;
import app.exception.InternalServerErrorException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.Charset;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class JSONUtilsTest {

  @Nested
  public class ConvertToJSON {

    @Test
    public void convertToJSONTest() {
      var obj = new Response(new Error(ErrorCode.BAD_REQUEST, "test"));
      var actual = JSONUtils.convertToJSON(obj);
      var expected = "{\"status\":\"Failure\",\"errors\":[{\"code\":\"BadRequest\",\"message\":\"test\"}]}";
      assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void throwException() {
      assertThatThrownBy(() -> JSONUtils.convertToJSON(new EmptyClass()))
          .isInstanceOf(InternalServerErrorException.class)
          .hasMessage(
              "InternalServerError:No serializer found for class app.util.JSONUtilsTest$EmptyClass and no properties discovered to create BeanSerializer (to avoid exception, disable SerializationFeature.FAIL_ON_EMPTY_BEANS)");
    }

  }

  @Nested
  public class ConvertToObject {

    @Test
    public void convertToObject() {
      var json = "{\"status\":\"Failure\",\"errors\":[{\"code\":\"BadRequest\",\"message\":\"test\"}]}";
      var actual = JSONUtils.convertToObject(json, Response.class);
      var expected = new Response(new Error(ErrorCode.BAD_REQUEST, "test"));
      assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void throwException() {
      assertThatThrownBy(() -> JSONUtils.convertToObject("{error}", Response.class))
          .isInstanceOf(InternalServerErrorException.class)
          .hasMessage(
              "InternalServerError:Unexpected character ('e' (code 101)): was expecting double-quote to start field name\n at [Source: (String)\"{error}\"; line: 1, column: 3]");
    }
  }

  @Nested
  public class WriteValue {

    @Test
    public void writeValue() {
      var writer = new StringWriter();
      var writeObject = new Response(new Error(ErrorCode.BAD_REQUEST, "test"));
      JSONUtils.writeValue(writer, writeObject);
      var actual = writer.toString();
      var expected = JSONUtils.convertToJSON(writeObject);
      assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void throwException() throws IOException {
      var writer = new StringWriter();
      var writeObject = new Response(new Error(ErrorCode.BAD_REQUEST, "test"));
      assertThatThrownBy(() -> JSONUtils.writeValue(writer, new EmptyClass()))
          .isInstanceOf(InternalServerErrorException.class)
          .hasMessage(
              "InternalServerError:No serializer found for class app.util.JSONUtilsTest$EmptyClass and no properties discovered to create BeanSerializer (to avoid exception, disable SerializationFeature.FAIL_ON_EMPTY_BEANS)");
    }
  }

  public class EmptyClass {

  }

}
