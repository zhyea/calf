package org.chobit.calf.service.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.chobit.calf.service.entity.Setting;

/**
 * @author robin
 */
@Mapper
public interface SettingMapper {

    @Insert("insert into setting (name, value) values (#{name}, #{value})")
    int insert(Setting settings);

}
