package org.chobit.calf.service.mapper;

import org.apache.ibatis.annotations.*;
import org.chobit.calf.service.entity.Feature;

import java.util.List;

/**
 * @author robin
 */
@Mapper
public interface FeatureMapper {


    @Insert({"insert into feature (cover, name, alias, keywords, brief)",
            "values",
            "(#{cover}, #{name}, #{alias}, #{keywords}, #{brief})"})
    int insert(Feature feature);


    @Update({"update feature set name=#{name}, cover=#{cover}, alias=#{alias}, keywords=#{keywords}, brief=#{brief}",
            "where id=#{id}"})
    boolean update(Feature feature);


    @Select("select * from feature")
    List<Feature> findAll();


    @Select("select * from feature where id=#{id}")
    Feature get(@Param("id") int id);


    @Select("select count(id) from feature where id<>#{id} and(name=#{name} or alias=#{alias})")
    Long countByNameOrAlias(@Param("id") int id, @Param("name") String name, @Param("alias") String alias);


    @Delete("delete from feature where id=#{id}")
    boolean delete(@Param("id") int id);
}
