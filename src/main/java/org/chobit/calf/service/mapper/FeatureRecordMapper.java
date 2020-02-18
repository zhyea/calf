package org.chobit.calf.service.mapper;

import org.apache.ibatis.annotations.*;
import org.chobit.calf.model.Pair;
import org.chobit.calf.model.WorkModel;
import org.chobit.calf.service.entity.FeatureRecord;

import java.util.Collection;
import java.util.List;

/**
 * @author robin
 */
@Mapper
public interface FeatureRecordMapper {

    @Insert({"insert into feature_record (type, feature_id, work_id)",
            "values",
            "(#{type}, #{featureId}, #{workId})"})
    Integer insert(FeatureRecord featureRecord);


    @Select({"select feature_id as `key`, count(id) as `value` from feature_record ",
            "group by feature_id"})
    List<Pair<Integer, Long>> countWithFeature();


    @Select({"select r.id, w.name, a.name as author, a.country as country from feature_record r left join work w on r.work_id=w.id left join author a on w.author_id=a.id",
            "where r.feature_id=#{featureId}"})
    List<WorkModel> findFeatureRecords(@Param("featureId") int featureId);


    @Delete({"<script>",
            "delete from feature_record where id in",
            "<foreach collection='ids' item='item' separator=',' open='(' close=')'>",
            "#{item}",
            "</foreach>",
            "</script>"})
    boolean deleteByIds(@Param("ids") Collection<Integer> ids);


    @Delete("delete from feature_record where feature_id=#{featureId}")
    boolean deleteByFeatureId(@Param("featureId") int featureId);



    @Delete({"<script>",
            "delete from feature_record where work_id in",
            "<foreach collection='ids' item='item' separator=',' open='(' close=')'>",
            "#{item}",
            "</foreach>",
            "</script>"})
    int deleteByWorkIds(@Param("ids") Iterable<Integer> ids);
}
