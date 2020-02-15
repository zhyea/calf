package org.chobit.calf.service.mapper;

import org.apache.ibatis.annotations.*;
import org.chobit.calf.service.entity.Author;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author robin
 */
@Mapper
public interface AuthorMapper {


    @Insert("insert into author (name, country, bio) values (#{name}, #{country}, #{bio})")
    @Options(useGeneratedKeys = true)
    int insert(Author author);


    @Update("update author set name=#{name}, country=#{country}, bio=#{bio} where id=#{id}")
    boolean update(Author author);


    @Select("select * from author where id<>#{id} and name=#{name} and country=#{country}")
    Author getByNameAndCountry(@Param("id") int id,
                               @Param("name") String name,
                               @Param("country") String country);


    @Select("select * from author where id=#{id}")
    Author get(@Param("id") int id);


    @Delete("delete from author where id=#{id}")
    boolean delete(@Param("id") int id);


    @Select({"select id, name, country from author ",
            "where name like #{key} or country like #{key} or bio like #{key} order by id desc limit 12"})
    List<LinkedHashMap> findByKeyword(@Param("key") String keyword);

}
