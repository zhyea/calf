package org.chobit.calf.service.mapper;

import org.apache.ibatis.annotations.*;
import org.chobit.calf.service.entity.User;

import java.util.List;

/**
 * @author robin
 */
@Mapper
public interface UserMapper {

    @Insert({"insert into user (username, password, email, nickname)",
            "values",
            "(#{username}, #{password}, #{email}, #{nickname})"})
    @Options(useGeneratedKeys = true)
    int insert(User user);


    @Update("update user set username=#{username}, password=#{password}, email=#{email}, nickname=#{nickname} where id=#{id}")
    boolean update(User user);


    @Select("select * from user")
    List<User> findAll();


    @Select("select * from user where id=#{id}")
    User get(@Param("id") int id);


    @Select("select * from user where id<>#{id} and (username=#{username} or email=#{email})")
    Integer checkByUsernameAndEmail(@Param("id") int id,
                                    @Param("username") String username,
                                    @Param("email") String email);


    @Select("select * from user where username=#{username} and password=#{password}")
    User getByUsernameAndPassword(@Param("username") String username,
                                  @Param("password") String password);


    @Delete({"<script>",
            "delete from user where id in",
            "<foreach collection='ids' item='item' separator=',' open='(' close=')'>",
            "#{item}",
            "</foreach>",
            "</script>"})
    boolean deleteByIds(@Param("ids") Iterable<Integer> ids);
}
