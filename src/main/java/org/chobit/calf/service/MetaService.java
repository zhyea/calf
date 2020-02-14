package org.chobit.calf.service;

import org.chobit.calf.model.Category;
import org.chobit.calf.model.Pair;
import org.chobit.calf.service.entity.AbstractEntity;
import org.chobit.calf.service.entity.Meta;
import org.chobit.calf.service.mapper.MetaMapper;
import org.chobit.calf.utils.Args;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.chobit.calf.constants.MetaType.CATEGORY;
import static org.chobit.calf.utils.Collections2.isEmpty;
import static org.chobit.calf.utils.Collections2.pairToMap;

/**
 * @author robin
 */
@Service
public class MetaService {


    @Autowired
    private MetaMapper metaMapper;


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
        meta.setSlug(slug);
        meta.setRemark(remark);

        if (id > 0) {
            metaMapper.update(meta);
        } else {
            metaMapper.insert(meta);
        }
    }


    public Boolean delete(Collection<Integer> ids) {
        if (null != ids) {
            ids.remove(1);
        }
        if (isEmpty(ids)) {
            return true;
        }
        List<Pair<Integer, Long>> pairs = metaMapper.countChildrenCat(ids, CATEGORY);
        List<Integer> idList = pairs.stream().map(Pair::getKey).collect(Collectors.toList());
        ids.removeAll(idList);
        if (isEmpty(ids)) {
            return true;
        }
        return metaMapper.deleteByIds(ids);
    }


    public boolean changerOrder(int id, int step) {
        return metaMapper.changeOrder(id, step);
    }

}
