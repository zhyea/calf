package org.chobit.calf.service.mapper;

import org.apache.ibatis.annotations.*;
import org.chobit.calf.model.Pair;
import org.chobit.calf.service.entity.Navigator;

import java.util.Collection;
import java.util.List;

/**
 * @author robin
 */
@Mapper
public interface NavMapper {


    @Insert({"insert into navigator (parent, type, name, url, remark, sn)",
            "values",
            "(#{parent}, #{type}, #{name}, #{url}, #{remark}, #{sn})"})
    int insert(Navigator nav);


    @Update({"update navigator set type=#{type}, name=#{name}, parent=#{parent}, url=#{url}, remark=#{remark}",
            "where id=#{id}"})
    boolean update(Navigator nav);


    @Select({"select * from navigator where parent=#{parent} order by sn desc"})
    List<Navigator> findByParent(@Param("parent") int parent);


    @Select({"select * from navigator order by sn desc"})
    List<Navigator> findAll();


    @Select({"<script>",
            "select parent as `key`, count(id) as `value` from navigator where parent in",
            "<foreach collection='ids' item='item' separator=',' open='(' close=')'>",
            "#{item}",
            "</foreach>",
            "group by parent",
            "</script>"})
    List<Pair<Integer, Long>> countChildren(@Param("ids") Collection<Integer> ids);


    @Select("select * from navigator where id=#{id}")
    Navigator get(@Param("id") int id);


    @Delete({"<script>",
            "delete from navigator where id in",
            "<foreach collection='ids' item='item' separator=',' open='(' close=')'>",
            "#{item}",
            "</foreach>",
            "</script>"})
    boolean deleteByIds(@Param("ids") Iterable<Integer> ids);


    @Update({"update navigator set sn=(sn+#{step}) where id=#{id}"})
    boolean changeOrder(@Param("id") int id, @Param("step") int step);


    @Select("select 1 from navigator where id<>#{id} and name=#{name}")
    Integer checkName(@Param("id") int id,
                      @Param("name") String name);
}
