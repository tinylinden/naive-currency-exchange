package eu.tinylinden.nce;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperHolder {

  private static final ObjectMapper objectMapper = new ObjectMapper();

  public static String jsonString(Object input) {
    try {
      return objectMapper.writeValueAsString(input);
    } catch (JsonProcessingException ex) {
      throw new RuntimeException(ex);
    }
  }
}
