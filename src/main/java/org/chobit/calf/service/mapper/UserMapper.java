package org.chobit.calf.service.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.chobit.calf.service.entity.User;

/**
 * @author robin
 */
@Mapper
public interface UserMapper {

    @Insert({"insert into user (username, password, email, nickname)",
            "values",
            "(#{username}, #{password}, #{email}, #{nickname})"})
    int insert(User user);

}
