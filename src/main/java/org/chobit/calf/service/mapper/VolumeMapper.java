package org.chobit.calf.service.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author robin
 */
@Mapper
public interface VolumeMapper {


    @Insert({"insert into volume (work_id, name)",
            "values",
            "(#{workId}, #{name})"})
    int insert();

}
