package org.chobit.calf.service;

import org.chobit.calf.model.Pair;
import org.chobit.calf.model.WorkModel;
import org.chobit.calf.service.entity.Feature;
import org.chobit.calf.service.entity.FeatureRecord;
import org.chobit.calf.service.mapper.FeatureMapper;
import org.chobit.calf.service.mapper.FeatureRecordMapper;
import org.chobit.calf.tools.UploadKit;
import org.chobit.calf.utils.Args;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.chobit.calf.utils.Collections2.isEmpty;
import static org.chobit.calf.utils.Collections2.pairToMap;

/**
 * @author robin
 */
@Component
@CacheConfig(cacheNames = "feature")
public class FeatureService {

    @Autowired
    private FeatureMapper featureMapper;
    @Autowired
    private FeatureRecordMapper recordMapper;


    public List<Feature> findFeatures() {
        List<Feature> list = featureMapper.findAll();
        if (isEmpty(list)) {
            return new LinkedList<>();
        }
        List<Pair<Integer, Long>> recordCount = recordMapper.countWithFeature();
        Map<Integer, Long> map = pairToMap(recordCount);
        for (Feature f : list) {
            long count = map.getOrDefault(f.getId(), 0L);
            f.setRecordsCount(count);
        }
        return list;
    }


    public Feature get(int id) {
        if (id <= 0) {
            return null;
        }
        return featureMapper.get(id);
    }


    public List<WorkModel> findFeatureRecords(int featureId) {
        return recordMapper.findFeatureRecords(featureId);
    }


    @CacheEvict(allEntries = true)
    public Boolean deleteFeatureRecords(Collection<Integer> ids) {
        return recordMapper.deleteByIds(ids);
    }


    @CacheEvict(allEntries = true)
    public Integer addFeatureRecord(int featureId, int workId) {
        FeatureRecord record = new FeatureRecord();
        record.setFeatureId(featureId);
        record.setWorkId(workId);
        return recordMapper.insert(record);
    }


    @CacheEvict(allEntries = true)
    public void maintain(int id, String name, String alias, String keywords, String brief, MultipartFile cover) {

        Args.checkNotBlank(name, "专题名称不能为空");
        Args.checkNotBlank(name, "专题别名不能为空");

        Long count = featureMapper.countByNameOrAlias(name, alias);
        Args.check(count <= 0, "名称或别名已存在");

        String pathCover = UploadKit.upload(cover);

        Feature feature = new Feature();
        feature.setName(name);
        feature.setAlias(alias);
        feature.setKeywords(keywords);
        feature.setBrief(brief);
        feature.setCover(pathCover);
        if (id > 0) {
            feature.setId(id);
            featureMapper.update(feature);
        } else {
            featureMapper.insert(feature);
        }
    }


    @Transactional(rollbackFor = Exception.class)
    public boolean delete(int featureId) {
        if (featureId <= 1) {
            return false;
        }
        featureMapper.delete(featureId);
        recordMapper.deleteByFeatureId(featureId);
        return true;
    }

}
