package org.chobit.calf.service;

import org.chobit.calf.model.Pair;
import org.chobit.calf.service.entity.Volume;
import org.chobit.calf.service.mapper.VolumeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.chobit.calf.utils.Strings.isNotBlank;

/**
 * @author robin
 */
@Service
@CacheConfig(cacheNames = "vol")
public class VolumeService {


    @Autowired
    private VolumeMapper volumeMapper;


    public Pair<String, Object> suggest(int workId, String keyword) {
        keyword = null == keyword ? "" : keyword;
        List<Map> r = volumeMapper.query(workId, "%" + keyword + "%");
        return new Pair<>(keyword, r);
    }


    @CacheEvict(allEntries = true)
    public int addOrUpdate(int id, int workId, String name) {
        if (isNotBlank(name)) {
            if (id <= 0) {
                Volume vol = volumeMapper.getByWorkIdAndName(workId, name);
                if (null != vol) {
                    return vol.getId();
                }
                vol = new Volume(workId, name);
                volumeMapper.insert(vol);
                return vol.getId();
            } else {
                volumeMapper.update(new Volume(id, workId, name));
                return id;
            }
        }
        return 0;
    }


    @Cacheable(key = "'findByWorkId'+#workId")
    public List<Volume> findByWorkId(int workId) {
        return volumeMapper.findByWorkId(workId);
    }


    @Cacheable(key = "'get' + #id")
    public Volume get(int id) {
        if (id <= 0) {
            return null;
        }
        return volumeMapper.get(id);
    }


    @CacheEvict(allEntries = true)
    public Volume add(int workId, String name) {
        Volume volume = new Volume(workId, name);
        volumeMapper.insert(volume);
        return volume;
    }


    @CacheEvict(allEntries = true)
    public boolean delete(int id) {
        if (id <= 0) {
            return true;
        }
        return volumeMapper.delete(id);
    }


    @Cacheable(key = "'getByWorkIdAndName' + #workId + '-' + #name")
    public Volume getByWorkIdAndName(int workId, String name) {
        return volumeMapper.getByWorkIdAndName(workId, name);
    }


    @Cacheable(key = "'getLatestByWorkId' + #workId")
    public Volume getLatestByWorkId(int workId) {
        return volumeMapper.getLatestByWorkId(workId);
    }


    @CacheEvict(allEntries = true)
    public int deleteByWorkIds(Collection<Integer> workIds) {
        return volumeMapper.deleteByWorkIds(workIds);
    }


    @CacheEvict(allEntries = true)
    public int deleteByWorkId(int workId) {
        return volumeMapper.deleteByWorkId(workId);
    }
}
