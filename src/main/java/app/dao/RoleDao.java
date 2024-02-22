package app.dao;

import app.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
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

  @Select("SELECT 1")
  @Results(id = "roleResultMap", value = {
      @Result(id = true, column = "user_id", property = "userID"),
      @Result(column = "allow_create", property = "allowCreate"),
      @Result(column = "allow_read", property = "allowRead"),
      @Result(column = "allow_update", property = "allowUpdate"),
      @Result(column = "allow_delete", property = "allowDelete"),
      @Result(column = "updated_by", property = "updatedBy"),
      @Result(column = "updated_at", property = "updatedAt"),
      @Result(column = "created_by", property = "createdBy"),
      @Result(column = "created_at", property = "createdAt")
  })
  Role __roleResultMap();

}
