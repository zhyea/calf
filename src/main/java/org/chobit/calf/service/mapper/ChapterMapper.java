package org.chobit.calf.service.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.chobit.calf.service.entity.Chapter;

/**
 * @author robin
 */
@Mapper
public interface ChapterMapper {


    @Insert({"insert into chapter (volume_id, work_id, name, key_words, content)",
            "values",
            "(#{volumeId}, #{workId}, #{name}, #{keyWords}, #{content})"})
    int insert(Chapter chapter);

}
