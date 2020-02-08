package org.chobit.calf.service.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.chobit.calf.service.entity.RemoteCode;

/**
 * @author robin
 */
@Mapper
public interface RemoteCodeMapper {

    @Insert("insert into remote_code (user_id, code) values #{userId}, #{code}")
    int insert(RemoteCode code);

}
