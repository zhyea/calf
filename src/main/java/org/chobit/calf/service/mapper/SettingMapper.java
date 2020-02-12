package org.chobit.calf.service.mapper;

import org.apache.ibatis.annotations.*;
import org.chobit.calf.service.entity.PairRecord;

import java.util.List;

/**
 * @author robin
 */
@Mapper
public interface SettingMapper {


    /**
     * 获取全部数据
     */
    @Select("select * from setting")
    List<PairRecord> findAll();


    /**
     * 更新配置信息
     *
     * @param name  配置名称
     * @param value 配置值
     * @return 是否更新成功
     */
    @Insert("replace into setting (name, value) values (#{name}, #{value})")
    boolean replace(@Param("name") String name,
                    @Param("value") String value);


    /**
     * 查询配置信息
     *
     * @param name 配置名称
     * @return 配置值
     */
    @Select("select value from setting where name=#{name}")
    String getByName(@Param("name") String name);

}
