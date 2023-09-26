package app.dao;

import app.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface RoleDao {

  @Select({"<script>", """
      SELECT
        *
      FROM
        role
      WHERE
        user_id = #{userID}
      """, "</script>"})
  Role findByUserID(String userID);

}
