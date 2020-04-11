package org.chobit.calf.service.mapper;

import org.apache.ibatis.annotations.*;
import org.chobit.calf.model.WorkModel;
import org.chobit.calf.service.entity.Feature;

import java.util.List;

/**
 * @author robin
 */
@Mapper
public interface FeatureMapper {


    @Insert({"insert into feature (cover, background, name, alias, keywords, brief, bg_repeat)",
            "values",
            "(#{cover}, #{background}, #{name}, #{alias}, #{keywords}, #{brief}, #{bgRepeat})"})
    int insert(Feature feature);


    @Update({"update feature set ",
            "name=#{name}, cover=#{cover}, background=#{background}, alias=#{alias}, keywords=#{keywords}, brief=#{brief}, bg_repeat=#{bgRepeat}",
            "where id=#{id}"})
    boolean update(Feature feature);


    @Select("select * from feature")
    List<Feature> findAll();


    @Select("select * from feature where id=#{id}")
    Feature get(@Param("id") int id);


    @Select("select * from feature where alias=#{alias} order by id desc limit 1")
    Feature getByAlias(@Param("alias") String alias);


    @Select("select count(id) from feature where id<>#{id} and(name=#{name} or alias=#{alias})")
    Long countByNameOrAlias(@Param("id") int id, @Param("name") String name, @Param("alias") String alias);


    @Select({"select w.id, w.name, w.cover, a.name as author, a.country as country",
            "from feature_record r",
            "left join work w on r.work_id=w.id",
            "left join author a on w.author_id=a.id",
            "left join feature f on r.feature_id=f.id",
            "where f.alias=#{alias}",
            "order by r.id desc"})
    List<WorkModel> findRecordsByAlias(@Param("alias") String alias);


    @Delete("delete from feature where id=#{id}")
    boolean delete(@Param("id") int id);
}
