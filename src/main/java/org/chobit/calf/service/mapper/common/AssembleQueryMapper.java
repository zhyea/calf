package org.chobit.calf.service.mapper.common;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.chobit.calf.model.Page;
import org.chobit.calf.tools.LowerCaseKeyMap;

import java.util.List;

/**
 * 组装查询
 */
@Mapper
public interface AssembleQueryMapper {


    @SelectProvider(type = AssembleQueryProvider.class, method = "queryInPage")
    List<LowerCaseKeyMap> findInPage(@Param("table") String tableName,
                                     @Param("p") Page page,
                                     @Param("searchColumn") Iterable<String> searchColumns,
                                     @Param("columns") String... resultColumns);


    @SelectProvider(type = AssembleQueryProvider.class, method = "countInPage")
    long countInPage(@Param("table") String tableName,
                     @Param("p") Page page,
                     @Param("searchColumn") Iterable<String> searchColumns
    );


}
