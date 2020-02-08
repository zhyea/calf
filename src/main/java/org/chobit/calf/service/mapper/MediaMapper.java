package org.chobit.calf.service.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.chobit.calf.service.entity.Media;

/**
 * @author robin
 */
@Mapper
public interface MediaMapper {

    @Insert({"insert into media (type, name, path)",
            "values",
            "(#{type}, #{name}, #{path})"})
    int insert(Media media);

}
