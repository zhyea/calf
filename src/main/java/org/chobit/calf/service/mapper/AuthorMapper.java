package org.chobit.calf.service.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.chobit.calf.service.entity.Author;

/**
 * @author robin
 */
@Mapper
public interface AuthorMapper {


    @Insert("insert into author (name, country, bio) values (#{name}, #{country}, #{bio})")
    int insert(Author author);


}
