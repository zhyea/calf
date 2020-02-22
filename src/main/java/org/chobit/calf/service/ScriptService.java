package org.chobit.calf.service;

import org.chobit.calf.service.entity.Script;
import org.chobit.calf.service.mapper.ScriptMapper;
import org.chobit.calf.utils.Args;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author robin
 */
@Service
@CacheConfig(cacheNames = "script")
public class ScriptService {


    @Autowired
    private ScriptMapper scriptMapper;


    @CacheEvict(allEntries = true)
    public void maintain(int id,
                         String name,
                         String code,
                         String script, String remark) {
        Args.checkNotBlank(name, "名称不能为空");
        Args.checkNotBlank(code, "代码不能为空");

        Script s = scriptMapper.getByNameOrCode(id, name, code);
        Args.check(null == s, "名称或代码已存在");

        Script spt = new Script(id, name, code);
        spt.setScript(script);
        spt.setRemark(remark);

        if (id > 0) {
            scriptMapper.update(spt);
        } else {
            scriptMapper.insert(spt);
        }
    }


    @Cacheable(key = "'all'")
    public List<Script> all() {
        return scriptMapper.findAll();
    }


    @Cacheable(key = "'get' + #id")
    public Script get(int id) {
        if (id <= 0) {
            return null;
        }
        return scriptMapper.get(id);
    }


    @CacheEvict(allEntries = true)
    public Boolean delete(int id) {
        return scriptMapper.delete(id);
    }
}
