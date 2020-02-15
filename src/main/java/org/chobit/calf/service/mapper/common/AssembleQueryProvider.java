package org.chobit.calf.service.mapper.common;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;
import org.chobit.calf.model.Page;

import static org.chobit.calf.utils.Collections2.isNotAllBlank;
import static org.chobit.calf.utils.Strings.isBlank;
import static org.chobit.calf.utils.Strings.isNotBlank;

public class AssembleQueryProvider {


    /**
     * 组装分页查询语句
     */
    public String queryInPage(@Param("table") String tableName,
                              @Param("p") Page page,
                              @Param("searchColumn") Iterable<String> searchColumns,
                              @Param("columns") String... resultColumns) {

        String sql = queryInPageCondition(false, tableName, page, searchColumns, resultColumns);

        if (null != page.getOrder()) {
            sql += " " + page.getOrder();
        }

        if (0 < page.getLimit()) {
            sql += " limit ";
            if (0 <= page.getOffset()) {
                sql += page.getOffset() + ", ";
            }
            sql += page.getLimit();
        }


        return sql;
    }


    /**
     * 组装分页查询统计语句
     */
    public String countInPage(@Param("table") String tableName,
                              @Param("p") Page page,
                              @Param("searchColumn") Iterable<String> searchColumns) {
        return queryInPageCondition(true, tableName, page, searchColumns);
    }


    /**
     * 分页查询基础条件
     */
    private String queryInPageCondition(boolean isCount,
                                        @Param("table") String tableName,
                                        @Param("p") Page page,
                                        @Param("searchColumn") Iterable<String> searchColumns,
                                        @Param("columns") String... resultColumns) {
        return new SQL() {
            {
                if (isCount) {
                    SELECT("count(id)");
                } else if (null == resultColumns || resultColumns.length == 0) {
                    SELECT("*");
                } else {
                    SELECT(resultColumns);
                }
                FROM(tableName);

                if (isNotBlank(page.getSearch()) && isNotAllBlank(searchColumns)) {
                    StringBuilder builder = new StringBuilder();

                    page.setSearch("%" + page.getSearch() + "%");

                    int i = 0;
                    for (String s : searchColumns) {
                        if (isBlank(s)) {
                            continue;
                        }
                        if (i++ > 0) {
                            builder.append(" or ");
                        }
                        builder.append(s + " like #{p.search}");
                    }

                    AND();
                    WHERE(builder.toString());
                }
                
                if (!isCount && isNotBlank(page.getSort())) {
                    ORDER_BY(page.getSort());
                }
            }
        }.toString();
    }
}
