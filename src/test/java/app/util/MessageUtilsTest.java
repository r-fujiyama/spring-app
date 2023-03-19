package app.util;

import static org.assertj.core.api.Assertions.assertThat;

import app.constants.ErrorMessage;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.support.ResourceBundleMessageSource;

public class MessageUtilsTest {

  private static final String[] PROPERTIES_FILE_NAMES = new String[]{"error-messages", "parameter-name"};
  private static MessageUtils messageUtils;

  @BeforeAll
  static void beforeAll() {
    var messageSource = new ResourceBundleMessageSource();
    messageSource.setDefaultEncoding(StandardCharsets.UTF_8.name());
    messageSource.setBasenames(PROPERTIES_FILE_NAMES);
    messageUtils = new MessageUtils(messageSource);
  }

  @Nested
  public class CodeArgDefaultMessage {

    @Test
    public void getMessage() {
      var actual = messageUtils.getMessage(ErrorMessage.MISSING_REQUEST_PARAMETER, "test", "default message");
      assertThat(actual).isEqualTo("testに値が入力されていません。");
    }

  }

  @Nested
  public class CodeArgsDefaultMessage {

    @Test
    public void getMessage() {
      var actual = messageUtils.getMessage(ErrorMessage.MISSING_REQUEST_PARAMETER, null, "default message");
      assertThat(actual).isEqualTo("{0}に値が入力されていません。");
    }

    @Test
    public void getReplacedMessage() {
      var actual = messageUtils.getMessage(ErrorMessage.MISSING_REQUEST_PARAMETER, new String[]{"test"},
          "default message");
      assertThat(actual).isEqualTo("testに値が入力されていません。");
    }

    @Test
    public void returnDefaultMessage() {
      var actual = messageUtils.getMessage("", null, "default message");
      assertThat(actual).isEqualTo("default message");
    }
  }

  @Nested
  public class CodeArgs {

    @Test
    public void getMessage() {
      var actual = messageUtils.getMessage(ErrorMessage.MISSING_REQUEST_PARAMETER);
      assertThat(actual).isEqualTo("{0}に値が入力されていません。");
    }

    @Test
    public void getReplacedMessage() {
      var actual = messageUtils.getMessage(ErrorMessage.MISSING_REQUEST_PARAMETER, "test");
      assertThat(actual).isEqualTo("testに値が入力されていません。");
    }

    @Test
    public void returnDefaultMessage() {
      var actual = messageUtils.getMessage("");
      assertThat(actual).isEqualTo(null);
    }
  }

  @Nested
  public class Resolvable {

    @Test
    public void getMessage() {
//      MessageSourceResolvable resolvable = () -> new String[]{ErrorMessage.MISSING_REQUEST_PARAMETER};
      var resolvable = new MessageSourceResolvable() {
        @Override
        public String[] getCodes() {
          return new String[]{ErrorMessage.MISSING_REQUEST_PARAMETER};
        }

        @Override
        public Object[] getArguments() {
          return new String[]{};
        }

        @Override
        public String getDefaultMessage() {
          return null;
        }
      };
      var actual = messageUtils.getMessage(resolvable);
      assertThat(actual).isEqualTo("{0}に値が入力されていません。");
    }

    @Test
    public void getReplacedMessage() {
      var resolvable = new MessageSourceResolvable() {
        @Override
        public String[] getCodes() {
          return new String[]{ErrorMessage.MISSING_REQUEST_PARAMETER};
        }

        @Override
        public Object[] getArguments() {
          return new String[]{"test"};
        }

        @Override
        public String getDefaultMessage() {
          return null;
        }
      };
      var actual = messageUtils.getMessage(resolvable);
      assertThat(actual).isEqualTo("testに値が入力されていません。");
    }

    @Test
    public void returnDefaultMessage() {
      var resolvable = new MessageSourceResolvable() {
        @Override
        public String[] getCodes() {
          return new String[]{""};
        }

        @Override
        public Object[] getArguments() {
          return new String[]{"test"};
        }

        @Override
        public String getDefaultMessage() {
          return "default message";
        }
      };
      var actual = messageUtils.getMessage(resolvable);
      assertThat(actual).isEqualTo("default message");
    }
  }

}
