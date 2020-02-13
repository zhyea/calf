package org.chobit.calf.service.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author robin
 */
@Mapper
public interface GeneralOpMapper {



    @Delete({"<script>",
            "delete from ${table} where id in",
            "<foreach collection='ids' item='item' separator=',' open='(' close=')'>",
            "#{item}",
            "</foreach>",
            "</script>"})
    int deleteByIds(@Param("table") String tableName,
                    @Param("ids") Iterable<Integer> ids);
}
