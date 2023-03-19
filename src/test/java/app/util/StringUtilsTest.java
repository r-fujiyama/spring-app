package app.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.util.stream.Stream;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class StringUtilsTest {

  @Nested
  public class IsEmpty {

    @ParameterizedTest
    @MethodSource("testValueProvider")
    public void isEmptyTest(String value, boolean expected) {
      assertThat(StringUtils.isEmpty(value)).isEqualTo(expected);
    }

    static Stream<Arguments> testValueProvider() {
      return Stream.of(
          arguments(null, true),
          arguments("", true),
          // 半角スペース
          arguments(" ", false),
          // 全角スペース
          arguments("　", false),
          arguments("\t", false),
          arguments("\r", false),
          arguments("\n", false),
          arguments("\r\n", false)
      );
    }
  }

  @Nested
  public class IsBlank {

    @ParameterizedTest
    @MethodSource("testValueProvider")
    public void isBlankTest(String value, boolean expected) {
      assertThat(StringUtils.isBlank(value)).isEqualTo(expected);
    }

    static Stream<Arguments> testValueProvider() {
      return Stream.of(
          arguments(null, true),
          arguments("", true),
          arguments("     ", true),
          arguments("\n\n\n\n\n", true),
          // General category "Zs" in the Unicode specification
          // SPACE SEPARATOR
          // '\u0020', U+0020 Space (半角スペース)
          arguments(new String(Character.toChars(0x0020)), true),
          // '\u00A0', U+00A0 No-Break Space (NBSP)
          arguments(new String(Character.toChars(0x00A0)), false),
          // '\u1680', U+1680 Ogham Space Mark
          arguments(new String(Character.toChars(0x1680)), true),
          // '\u2000', U+2000 En Quad
          arguments(new String(Character.toChars(0x2000)), true),
          // '\u2001', U+2001 Em Quad
          arguments(new String(Character.toChars(0x2001)), true),
          // '\u2002', U+2002 Em Quad
          arguments(new String(Character.toChars(0x2002)), true),
          // '\u2003', U+2003 Em Space
          arguments(new String(Character.toChars(0x2003)), true),
          // '\u2004', U+2004 Three-Per-Em Space
          arguments(new String(Character.toChars(0x2004)), true),
          // '\u2005', U+2005 Four-Per-Em Space
          arguments(new String(Character.toChars(0x2005)), true),
          // '\u2006', U+2006 Six-Per-Em Space
          arguments(new String(Character.toChars(0x2006)), true),
          // '\u2007', U+2007 Figure Space
          arguments(new String(Character.toChars(0x2007)), false),
          // '\u2008', U+2008 Punctuation Space
          arguments(new String(Character.toChars(0x2008)), true),
          // '\u2009', U+2009 Thin Space
          arguments(new String(Character.toChars(0x2009)), true),
          // '\u200A', U+200A Hair Space
          arguments(new String(Character.toChars(0x200A)), true),
          // '\u202F', U+202F Narrow No-Break Space (NNBSP)
          arguments(new String(Character.toChars(0x202F)), false),
          // '\u205F', U+205F Medium Mathematical Space (MMSP)
          arguments(new String(Character.toChars(0x205F)), true),
          // '\u3000', U+3000 Ideographic Space (全角スペース)
          arguments(new String(Character.toChars(0x3000)), true),

          // '\u2028', U+2028 Line Separator
          arguments(new String(Character.toChars(0x2028)), true),
          // '\u2029', U+2029 Paragraph Separator
          arguments(new String(Character.toChars(0x2029)), true),

          // '\t', U+0009 Character Tabulation (HT, TAB)
          arguments(new String(Character.toChars(0x0009)), true),
          // '\n', U+000A End of Line (EOL, LF, NL)
          arguments(new String(Character.toChars(0x000A)), true),
          // '\u000B', U+000B Line Tabulation  (VT)
          arguments(new String(Character.toChars(0x000B)), true),
          // '\f', U+000C Form Feed (FF)
          arguments(new String(Character.toChars(0x000C)), true),
          // '\r', U+000D Carriage Return (CR)
          arguments(new String(Character.toChars(0x000D)), true),
          // '\u001C', U+001C File Separator (FS)
          arguments(new String(Character.toChars(0x001C)), true),
          // '\u001D', U+001D Group Separator (GS)
          arguments(new String(Character.toChars(0x001D)), true),
          // '\u001E', U+001E Information Separator Two (RS)
          arguments(new String(Character.toChars(0x001E)), true),
          // '\u001F', U+001F Information Separator One (US)
          arguments(new String(Character.toChars(0x001F)), true)
      );
    }
  }

}
