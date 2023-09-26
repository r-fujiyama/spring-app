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

}