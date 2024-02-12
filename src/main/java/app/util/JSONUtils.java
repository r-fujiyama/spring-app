package app.util;

import app.exception.InternalServerErrorException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.Writer;

public final class JSONUtils {

  private JSONUtils() {
  }

  private static final ObjectMapper mapper = new ObjectMapper();

  public static String toJSON(Object o) {
    try {
      return mapper.writeValueAsString(o);
    } catch (JsonProcessingException e) {
      throw new InternalServerErrorException(e.getMessage(), e);
    }
  }

  public static <T> T toObject(String s, Class<T> clazz) {
    try {
      return mapper.readValue(s, clazz);
    } catch (JsonProcessingException e) {
      throw new InternalServerErrorException(e.getMessage(), e);
    }
  }

  public static void writeValue(Writer w, Object value) {
    try {
      mapper.writeValue(w, value);
    } catch (IOException e) {
      throw new InternalServerErrorException(e.getMessage(), e);
    }
  }

}
