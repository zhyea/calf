package org.chobit.calf.service;


import org.chobit.calf.model.Page;
import org.chobit.calf.model.PageResult;
import org.chobit.calf.service.mapper.common.AssembleQueryMapper;
import org.chobit.calf.tools.LowerCaseKeyMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 管理各种组装查询能力，提供一些可重用的方法
 */
@Service
@CacheConfig(cacheNames = "assembleQuery")
public class AssembleQueryService {

    private static Logger logger = LoggerFactory.getLogger(AssembleQueryService.class);

    @Autowired
    private AssembleQueryMapper queryMapper;

    /**
     * 查询列表数据
     *
     * @param tableName     表名
     * @param page          分页对象
     * @param searchColumns 要搜索的字段名
     * @param resultColumns 查询结果字段
     * @return 查询结果
     */
    public PageResult<LowerCaseKeyMap> findInPage(String tableName,
                                                  Page page,
                                                  List<String> searchColumns,
                                                  String... resultColumns) {
        List<LowerCaseKeyMap> rows = queryMapper.findInPage(tableName, page, searchColumns, resultColumns);
        long total = queryMapper.countInPage(tableName, page, searchColumns);
        return new PageResult<>(total, rows);
    }

}
