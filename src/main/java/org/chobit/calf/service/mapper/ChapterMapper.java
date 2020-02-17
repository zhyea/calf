package org.chobit.calf.service.mapper;

import org.apache.ibatis.annotations.*;
import org.chobit.calf.service.entity.Chapter;

import java.util.List;

/**
 * @author robin
 */
@Mapper
public interface ChapterMapper {


    @Insert({"insert into chapter (volume_id, work_id, name, keywords, content)",
            "values",
            "(#{volumeId}, #{workId}, #{name}, #{keywords}, #{content})"})
    int insert(Chapter chapter);


    @Update({"update chapter set ",
            "volume_id=#{volumeId}, work_id=#{workId}, name=#{name}, keywords=#{keywords}, content=#{content}",
            "where id=#{id}"})
    boolean update(Chapter chapter);


    @Select("select * from chapter where id=#{id}")
    Chapter get(@Param("id") int id);


    @Select("select * from chapter where work_id=#{workId}")
    List<Chapter> findByWorkId(@Param("workId") int workId);


    @Delete("delete from chapter where id=#{id}")
    boolean delete(@Param("id") int id);


    @Select("select count(id) from chapter where volume_id=#{volId}")
    Long countByVolumeId(@Param("volId") int volId);


    @Delete({"<script>",
            "delete from chapter where work_id in",
            "<foreach collection='ids' item='item' separator=',' open='(' close=')'>",
            "#{item}",
            "</foreach>",
            "</script>"})
    int deleteByWorkIds(@Param("ids") Iterable<Integer> ids);
}
