package org.chobit.calf.service.mapper;

import org.apache.ibatis.annotations.*;
import org.chobit.calf.service.entity.Volume;

import java.util.List;
import java.util.Map;

/**
 * @author robin
 */
@Mapper
public interface VolumeMapper {


    @Insert({"insert into volume (work_id, name)",
            "values",
            "(#{workId}, #{name})"})
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Volume volume);


    @Update("update volume set work_id=#{workId}, name=#{name} where id=#{id}")
    boolean update(Volume volume);


    @Select("select * from volume where work_id=#{workId} order by id asc")
    List<Volume> findByWorkId(@Param("workId") int workId);


    @Select("select * from volume where work_id=#{workId} and name like #{key} limit 12")
    List<Map> query(@Param("workId") int workId,
                    @Param("key") String keyword);


    @Select("select * from volume where id=#{id}")
    Volume get(@Param("id") int id);


    @Select("select * from volume where work_id=#{workId} and name=#{name} order by id desc limit 1")
    Volume getByWorkIdAndName(@Param("workId") int workId, @Param("name") String name);


    @Delete("delete from volume where id=#{id}")
    boolean delete(@Param("id") int id);


    @Delete({"<script>",
            "delete from volume where work_id in",
            "<foreach collection='ids' item='item' separator=',' open='(' close=')'>",
            "#{item}",
            "</foreach>",
            "</script>"})
    int deleteByWorkIds(@Param("ids") Iterable<Integer> ids);


    @Delete("delete from volume where work_id=#{workId}")
    int deleteByWorkId(@Param("workId") int workId);
}
