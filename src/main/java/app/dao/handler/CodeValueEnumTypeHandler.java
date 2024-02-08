package app.dao.handler;

import app.enums.CodeValueEnum;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

public class CodeValueEnumTypeHandler<T1, T2, T3 extends Enum<T3> & CodeValueEnum<T1, T2>> extends BaseTypeHandler<T3> {

  private final Class<T3> type;

  public CodeValueEnumTypeHandler(Class<T3> type) {
    this.type = type;
  }

  @Override
  public void setNonNullParameter(PreparedStatement ps, int i, T3 parameter, JdbcType jdbcType) throws SQLException {
    if (parameter == null) {
      ps.setObject(i, null);
    } else {
      ps.setObject(i, parameter.getCode());
    }
  }

  @Override
  public T3 getNullableResult(ResultSet rs, String columnName) throws SQLException {
    return CodeValueEnum.fromCode(type, rs.getInt(columnName));
  }

  @Override
  public T3 getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
    return CodeValueEnum.fromCode(type, rs.getInt(columnIndex));
  }

  @Override
  public T3 getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
    return CodeValueEnum.fromCode(type, cs.getInt(columnIndex));
  }

}
