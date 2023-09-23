package app.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@AllArgsConstructor
public enum Role implements CodeValueEnum, GrantedAuthority {

  UNKNOWN(Integer.MIN_VALUE, "Unknown"),
  CREATE(8, "Create"),
  READ(4, "Read"),
  UPDATE(2, "Update"),
  DELETE(1, "Delete");

  private final int code;
  private final String value;

  @Override
  public int getCode() {
    return this.code;
  }

  @JsonValue
  @Override
  public String getValue() {
    return this.value;
  }

  public static Role fromCode(Integer code) {
    return CodeValueEnum.fromCode(Role.class, code);
  }

  @JsonCreator
  public static Role fromValue(String value) {
    return CodeValueEnum.fromValue(Role.class, value);
  }

  @Override
  public String getAuthority() {
    return this.getValue();
  }

}
