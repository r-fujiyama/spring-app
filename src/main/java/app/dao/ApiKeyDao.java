package app.dao;

import app.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ApiKeyDao {

  @Select({"<script>", """
      SELECT
        *
      FROM
        api_key
      WHERE
        api_key = #{apiKey}
      """, "</script>"})
  Role findByAPIKey(String apiKey);

}
