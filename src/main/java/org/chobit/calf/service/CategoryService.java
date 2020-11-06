package org.chobit.calf.service;

import org.chobit.calf.constants.MetaType;
import org.chobit.calf.model.MetaNode;
import org.chobit.calf.model.Pair;
import org.chobit.calf.service.entity.AbstractEntity;
import org.chobit.calf.service.entity.Category;
import org.chobit.calf.service.mapper.CategoryMapper;
import org.chobit.calf.utils.Args;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
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
public class CategoryService {


    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private WorkService workService;

    public static final int DEFAULT_CATEGORY_ID = 1;


    public List<org.chobit.calf.model.Category> findCatByParent(int parent) {
        List<Category> cats = categoryMapper.findByParent(parent, CATEGORY);
        if (isEmpty(cats)) {
            return new LinkedList<>();
        }
        List<Integer> ids = cats.stream().map(AbstractEntity::getId).collect(Collectors.toList());
        List<Pair<Integer, Long>> pairs = categoryMapper.countChildrenCat(ids, CATEGORY);
        Map<Integer, Long> map = pairToMap(pairs);
        List<org.chobit.calf.model.Category> list = new LinkedList<>();
        for (Category m : cats) {
            list.add(new org.chobit.calf.model.Category(m, map.getOrDefault(m.getId(), 0L).intValue()));
        }
        return list;
    }


    public Category get(int id) {
        if (id <= 0) {
            return null;
        }
        return categoryMapper.get(id);
    }


    @CacheEvict(allEntries = true)
    public void maintain(int id, int parent, String name, String slug, String remark) {
        Args.check(id >= 0, "请求数据异常");
        Args.checkNotBlank(name, "分类名称不能为空");
        Args.checkNotBlank(slug, "缩略名不能为空");
        Integer flag = categoryMapper.checkNameAndSlug(id, name, slug);
        Args.check(null == flag, "名称或缩略名已存在");

        Category category = new Category();
        category.setId(id);
        category.setParent(parent);
        category.setType(CATEGORY);
        category.setName(name);
        category.setSlug(slug.trim());
        category.setRemark(remark);

        if (id > 0) {
            categoryMapper.update(category);
        } else {
            categoryMapper.insert(category);
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
        List<Pair<Integer, Long>> pairs = categoryMapper.countChildrenCat(ids, CATEGORY);
        List<Integer> idList = pairs.stream().map(Pair::getKey).collect(Collectors.toList());
        ids.removeAll(idList);
        if (isEmpty(ids)) {
            return true;
        }
        ids.forEach(e -> workService.changeCat(e, DEFAULT_CATEGORY_ID));
        return categoryMapper.deleteByIds(ids);
    }


    @CacheEvict(allEntries = true)
    public boolean changerOrder(int id, int step) {
        return categoryMapper.changeOrder(id, step);
    }


    public Pair<String, Object> findCatsSuggest(String key) {
        key = isBlank(key) ? "" : key;
        Object value = categoryMapper.findCatsByKeyword("%" + key + "%");
        return new Pair<>(key, value);
    }


    @Cacheable(key = "'buildMetaTree' + #type")
    public MetaNode buildMetaTree(MetaType type) {
        List<Category> list = findByType(type);
        MetaNode root = new MetaNode();
        addChildren(root, list);
        return root;
    }


    @Cacheable(key = "'findByType' + #type")
    public List<Category> findByType(MetaType type) {
        return categoryMapper.findByType(type);
    }


    public List<Category> findCandidateParentCats(int catId) {
        List<Category> list = findByType(CATEGORY);
        Set<Integer> tree = new TreeSet<>();
        tree.add(catId);
        boolean flag = true;
        while (flag) {
            flag = false;
            for (Category m : list) {
                if (!tree.contains(m.getId()) && tree.contains(m.getParent())) {
                    tree.add(m.getId());
                    flag = true;
                }
            }
        }
        return list.stream()
                .filter(e -> !tree.contains(e.getId()))
                .collect(Collectors.toList());
    }


    private void addChildren(MetaNode root, List<Category> list) {
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
    public Category getBySlug(String slug) {
        return categoryMapper.getBySlug(slug);
    }


    @Cacheable(key = "'getByName' + #name")
    public Category getByName(String name) {
        if (isBlank(name)) {
            return null;
        }
        return categoryMapper.getByName(name);
    }
}
