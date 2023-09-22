package app.dao.handler;

import app.enums.CodeValueEnum;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

public class CodeValueEnumTypeHandler<T extends Enum<T> & CodeValueEnum> extends BaseTypeHandler<T> {

  private final Class<T> type;

  public CodeValueEnumTypeHandler(Class<T> type) {
    this.type = type;
  }

  @Override
  public void setNonNullParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException {
    if (parameter == null) {
      ps.setObject(i, null);
    } else {
      ps.setObject(i, parameter.getCode());
    }
  }

  @Override
  public T getNullableResult(ResultSet rs, String columnName) throws SQLException {
    return CodeValueEnum.fromCode(type, rs.getInt(columnName));
  }

  @Override
  public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
    return CodeValueEnum.fromCode(type, rs.getInt(columnIndex));
  }

  @Override
  public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
    return CodeValueEnum.fromCode(type, cs.getInt(columnIndex));
  }

}
