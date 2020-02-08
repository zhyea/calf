package org.chobit.calf.service.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.chobit.calf.service.entity.Feature;

/**
 * @author robin
 */
@Mapper
public interface FeatureMapper {


    @Insert({"insert into feature (cover, name, alias, key_words, brief)",
            "values",
            "(#{cover}, #{name}, #{alias}, #{keyWords}, #{brief})"})
    int insert(Feature feature);



}
