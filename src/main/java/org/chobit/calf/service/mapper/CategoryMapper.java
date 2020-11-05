package org.chobit.calf.service.mapper;

import org.apache.ibatis.annotations.*;
import org.chobit.calf.constants.MetaType;
import org.chobit.calf.model.Pair;
import org.chobit.calf.service.entity.Category;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author robin
 */
@Mapper
public interface CategoryMapper {


    @Insert({"insert into category (parent, type, name, slug, remark, sn)",
            "values",
            "(#{parent}, #{type}, #{name}, #{slug}, #{remark}, #{sn})"})
    int insert(Category category);


    @Update({"update category set type=#{type}, name=#{name}, parent=#{parent}, slug=#{slug}, remark=#{remark}",
            "where id=#{id}"})
    boolean update(Category category);


    @Select({"select * from category where parent=#{parent} and type=#{type} order by sn desc"})
    List<Category> findByParent(@Param("parent") int parent,
                                @Param("type") MetaType type);


    @Select({"<script>",
            "select parent as `key`, count(id) as `value` from category where type=#{type} and parent in",
            "<foreach collection='ids' item='item' separator=',' open='(' close=')'>",
            "#{item}",
            "</foreach>",
            "group by parent",
            "</script>"})
    List<Pair<Integer, Long>> countChildrenCat(@Param("ids") Collection<Integer> ids,
                                               @Param("type") MetaType type);


    @Select("select * from category where id=#{id}")
    Category get(@Param("id") int id);


    @Select("select 1 from category where id<>#{id} and (name=#{name} or slug=#{slug})")
    Integer checkNameAndSlug(@Param("id") int id,
                             @Param("name") String name,
                             @Param("slug") String slug);


    @Delete({"<script>",
            "delete from category where id in",
            "<foreach collection='ids' item='item' separator=',' open='(' close=')'>",
            "#{item}",
            "</foreach>",
            "</script>"})
    boolean deleteByIds(@Param("ids") Iterable<Integer> ids);


    @Update({"update category set sn=(sn+#{step}) where id=#{id}"})
    boolean changeOrder(@Param("id") int id, @Param("step") int step);


    @Select({"select id, name, slug from meta ",
            "where type='CATEGORY' and (name like #{key} or slug like #{key}) order by id desc limit 12"})
    List<Map> findCatsByKeyword(@Param("key") String keyword);


    @Select("select * from category where type=#{type}")
    List<Category> findByType(@Param("type") MetaType type);


    @Select("select * from category where slug=#{slug} order by id desc limit 1")
    Category getBySlug(@Param("slug") String slug);


    @Select("select * from category where name=#{name} order by id desc limit 1")
    Category getByName(@Param("name") String name);

}
