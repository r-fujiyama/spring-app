package app.dao;

import app.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserDao {

  @Select({"<script>", """
      SELECT
        *
      FROM
        user
      WHERE
        user_id = #{userID}
      """, "</script>"})
  User findByUserID(String userID);

  @Select({"<script>", """
      SELECT
        u.*
      FROM
        user u JOIN api_key a
          ON u.user_id = a.user_id
      WHERE
        a.api_key = #{apiKey}
      """, "</script>"})
  User findByAPIKey(String apiKey);

}
