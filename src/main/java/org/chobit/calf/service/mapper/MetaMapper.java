package org.chobit.calf.service.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.chobit.calf.service.entity.Meta;

/**
 * @author robin
 */
@Mapper
public interface MetaMapper {

    @Insert({"insert into meta (parent, type, name, slug, remark, sn)",
            "values",
            "(#{parent}, #{type}, #{name}, #{slug}, #{remark}, #{sn})"})
    int insert(Meta meta);

}
