package org.chobit.calf.service.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.chobit.calf.service.entity.Work;

/**
 * @author robin
 */
@Mapper
public interface WorkMapper {

    @Insert({"insert into work (author_id, category_id, cover, file, name, brief)",
            "values",
            "(#{authorId}, #{categoryId}, #{cover}, #{file}, #{name}, #{brief})"})
    int insert(Work work);

}
