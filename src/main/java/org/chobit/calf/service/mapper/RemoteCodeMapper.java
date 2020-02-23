package org.chobit.calf.service.mapper;

import org.apache.ibatis.annotations.*;
import org.chobit.calf.service.entity.RemoteCode;

/**
 * @author robin
 */
@Mapper
public interface RemoteCodeMapper {


    @Insert("insert into remote_code (user_id, code) values (#{userId}, #{code})")
    int insert(RemoteCode code);


    @Update("update remote_code set user_id=#{userId}, code=#{code} where id=#{id}")
    boolean update(RemoteCode code);


    @Select("select * from remote_code where code=#{code}")
    RemoteCode getByCode(@Param("code")String code);


    @Select("select * from remote_code where user_id=#{userId} order by id desc limit 1")
    RemoteCode getByUserId(@Param("userId")int userId);

}
