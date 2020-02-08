package org.chobit.calf.service.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.chobit.calf.service.entity.FeatureRecord;

/**
 * @author robin
 */
@Mapper
public interface FeatureRecordMapper {

    @Insert({"insert into feature_record (type, feature_id, work_id)",
            "values",
            "(#{type}, #{featureId}, #{workId})"})
    int insert(FeatureRecord featureRecord);

}
