package org.chobit.calf.service;

import org.chobit.calf.constants.MetaType;
import org.chobit.calf.model.Category;
import org.chobit.calf.model.MetaNode;
import org.chobit.calf.model.Pair;
import org.chobit.calf.service.entity.AbstractEntity;
import org.chobit.calf.service.entity.Meta;
import org.chobit.calf.service.mapper.MetaMapper;
import org.chobit.calf.utils.Args;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.chobit.calf.constants.MetaType.CATEGORY;
import static org.chobit.calf.utils.Collections2.isEmpty;
import static org.chobit.calf.utils.Collections2.pairToMap;
import static org.chobit.calf.utils.Strings.isBlank;

/**
 * @author robin
 */
@Service
@CacheConfig(cacheNames = "meta")
public class MetaService {


    @Autowired
    private MetaMapper metaMapper;
    @Autowired
    private WorkService workService;

    public static final int DEFAULT_CATEGORY_ID = 1;


    public List<Category> findCatByParent(int parent) {
        List<Meta> cats = metaMapper.findByParent(parent, CATEGORY);
        if (isEmpty(cats)) {
            return new LinkedList<>();
        }
        List<Integer> ids = cats.stream().map(AbstractEntity::getId).collect(Collectors.toList());
        List<Pair<Integer, Long>> pairs = metaMapper.countChildrenCat(ids, CATEGORY);
        Map<Integer, Long> map = pairToMap(pairs);
        List<Category> list = new LinkedList<>();
        for (Meta m : cats) {
            list.add(new Category(m, map.getOrDefault(m.getId(), 0L).intValue()));
        }
        return list;
    }


    public Meta get(int id) {
        if (id <= 0) {
            return null;
        }
        return metaMapper.get(id);
    }


    @CacheEvict(allEntries = true)
    public void maintain(int id, int parent, String name, String slug, String remark) {
        Args.check(id >= 0, "请求数据异常");
        Args.checkNotBlank(name, "分类名称不能为空");
        Args.checkNotBlank(slug, "缩略名不能为空");
        Integer flag = metaMapper.checkNameAndSlug(id, name, slug);
        Args.check(null == flag, "名称或缩略名已存在");

        Meta meta = new Meta();
        meta.setId(id);
        meta.setParent(parent);
        meta.setType(CATEGORY.name());
        meta.setName(name);
        meta.setSlug(slug.trim());
        meta.setRemark(remark);

        if (id > 0) {
            metaMapper.update(meta);
        } else {
            metaMapper.insert(meta);
        }
    }


    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteByIds(Collection<Integer> ids) {
        if (null != ids) {
            ids.remove(DEFAULT_CATEGORY_ID);
        }
        if (isEmpty(ids)) {
            return true;
        }
        ids.remove(1);
        List<Pair<Integer, Long>> pairs = metaMapper.countChildrenCat(ids, CATEGORY);
        List<Integer> idList = pairs.stream().map(Pair::getKey).collect(Collectors.toList());
        ids.removeAll(idList);
        if (isEmpty(ids)) {
            return true;
        }
        ids.forEach(e -> workService.changeCat(e, DEFAULT_CATEGORY_ID));
        return metaMapper.deleteByIds(ids);
    }


    @CacheEvict(allEntries = true)
    public boolean changerOrder(int id, int step) {
        return metaMapper.changeOrder(id, step);
    }


    public Pair<String, Object> findCatsSuggest(String key) {
        key = isBlank(key) ? "" : key;
        Object value = metaMapper.findCatsByKeyword("%" + key + "%");
        return new Pair<>(key, value);
    }


    @Cacheable(key = "'buildMetaTree' + #type")
    public MetaNode buildMetaTree(MetaType type) {
        List<Meta> list = findByType(type);
        MetaNode root = new MetaNode();
        addChildren(root, list);
        return root;
    }


    @Cacheable(key = "'findByType' + #type")
    public List<Meta> findByType(MetaType type) {
        return metaMapper.findByType(type);
    }


    private void addChildren(MetaNode root, List<Meta> list) {
        list.stream()
                .filter(e -> e.getParent() == root.getId())
                .forEach(e -> {
                    MetaNode node = new MetaNode(e.getId(), e.getName());
                    node.setSlug(e.getSlug());
                    root.addChild(node);
                    addChildren(node, list);
                });
    }


    @Cacheable(key = "'getBySlug' + #slug")
    public Meta getBySlug(String slug) {
        return metaMapper.getBySlug(slug);
    }


    @Cacheable(key = "'getByName' + #name")
    public Meta getByName(String name) {
        if (isBlank(name)) {
            return null;
        }
        return metaMapper.getByName(name);
    }
}
