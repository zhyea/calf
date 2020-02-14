package org.chobit.calf.service.mapper;

import org.apache.ibatis.annotations.*;
import org.chobit.calf.constants.MetaType;
import org.chobit.calf.model.Pair;
import org.chobit.calf.service.entity.Meta;

import java.util.Collection;
import java.util.List;

/**
 * @author robin
 */
@Mapper
public interface MetaMapper {


    @Insert({"insert into meta (parent, type, name, slug, remark, sn)",
            "values",
            "(#{parent}, #{type}, #{name}, #{slug}, #{remark}, #{sn})"})
    int insert(Meta meta);


    @Update({"update meta set type=#{type}, name=#{name}, slug=#{slug}, remark=#{remark}",
            "where id=#{id}"})
    boolean update(Meta meta);


    @Select({"select * from meta where parent=#{parent} and type=#{type} order by sn desc"})
    List<Meta> findByParent(@Param("parent") int parent,
                            @Param("type") MetaType type);


    @Select({"<script>",
            "select parent as `key`, count(id) as `value` from meta where type=#{type} and parent in",
            "<foreach collection='ids' item='item' separator=',' open='(' close=')'>",
            "#{item}",
            "</foreach>",
            "group by parent",
            "</script>"})
    List<Pair<Integer, Long>> countChildrenCat(@Param("ids") Collection<Integer> ids,
                                               @Param("type") MetaType type);



    @Select("select * from meta where id=#{id}")
    Meta get(@Param("id") int id);


    @Select("select 1 from meta where id<>#{id} and (name=#{name} or slug=#{slug})")
    Integer checkNameAndSlug(@Param("id") int id,
                             @Param("name") String name,
                             @Param("slug") String slug);


    @Delete({"<script>",
            "delete from meta where id in",
            "<foreach collection='ids' item='item' separator=',' open='(' close=')'>",
            "#{item}",
            "</foreach>",
            "</script>"})
    boolean deleteByIds(@Param("ids") Iterable<Integer> ids);


    @Update({"update meta set sn=(sn+#{step}) where id=#{id}"})
    boolean changeOrder(@Param("id") int id, @Param("step") int step);

}
