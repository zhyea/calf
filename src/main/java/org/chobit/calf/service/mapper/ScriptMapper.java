package org.chobit.calf.service.mapper;

import org.apache.ibatis.annotations.*;
import org.chobit.calf.service.entity.Script;

import java.util.List;

/**
 * @author robin
 */
@Mapper
public interface ScriptMapper {

    @Insert({"insert into script (name, code, script, remark)",
            "values", "(#{name}, #{code}, #{script}, #{remark})"})
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Script script);


    @Update({"update script set ",
            "name=#{name}, code=#{code}, script=#{script}, remark=#{remark}",
            "where id=#{id}"})
    boolean update(Script script);


    @Select("select id, name, code from script")
    List<Script> findAll();


    @Delete("delete from script where id=#{id}")
    boolean delete(@Param("id") int id);


    @Select("select * from script where id=#{id}")
    Script get(@Param("id") int id);


    @Select("select * from script where id<>#{id} and (name=#{name} or code=#{code}) order by id desc limit 1")
    Script getByNameOrCode(@Param("id") int id, @Param("name") String name, @Param("code") String code);

}
