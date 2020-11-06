package org.chobit.calf.service;

import org.chobit.calf.constants.NavType;
import org.chobit.calf.model.TreeNode;
import org.chobit.calf.model.NavNode;
import org.chobit.calf.model.Pair;
import org.chobit.calf.model.TreeViewNode;
import org.chobit.calf.service.entity.Feature;
import org.chobit.calf.service.entity.Navigator;
import org.chobit.calf.service.mapper.NavMapper;
import org.chobit.calf.utils.Args;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static org.chobit.calf.utils.Collections2.isEmpty;
import static org.chobit.calf.utils.Collections2.pairToMap;
import static org.chobit.calf.utils.Strings.isBlank;
import static org.chobit.calf.utils.Strings.isNotBlank;

/**
 * @author robin
 */
@Service
@CacheConfig(cacheNames = "nav")
public class NavService {


    @Autowired
    private NavMapper navMapper;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private FeatureService featureService;


    public List<Map<String, Object>> findByParent(int parent) {
        List<Map<String, Object>> result = new LinkedList<>();
        List<Navigator> list = navMapper.findByParent(parent);
        if (isEmpty(list)) {
            return result;
        }
        List<Integer> ids = list.stream().map(Navigator::getId).collect(Collectors.toList());
        List<Pair<Integer, Long>> pairs = navMapper.countChildren(ids);
        Map<Integer, Long> map = pairToMap(pairs);
        for (Navigator n : list) {
            Map<String, Object> m = new LinkedHashMap<>(6);
            m.put("id", n.getId());
            m.put("name", n.getName());
            m.put("parent", n.getParent());
            m.put("sn", n.getSn());
            m.put("type", n.getType().desc);
            m.put("subCount", map.getOrDefault(n.getId(), 0L));
            result.add(m);
        }

        return result;
    }


    public Navigator get(int id) {
        if (id <= 0) {
            return null;
        }
        return navMapper.get(id);
    }


    @CacheEvict(allEntries = true)
    public void maintain(int id, int parent, String name, NavType type, String url) {
        Args.check(id >= 0, "请求数据异常");
        Args.checkNotBlank(name, "名称不能为空");
        Args.check(null != type, "链接类型不能为空");
        Args.check(isNotBlank(url), "链接地址不能为空");

        Navigator nav = new Navigator();
        nav.setId(id);
        nav.setParent(parent);
        nav.setType(type);
        nav.setName(name);
        nav.setUrl(url);

        if (id > 0) {
            navMapper.update(nav);
        } else {
            navMapper.insert(nav);
        }
    }


    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteByIds(Collection<Integer> ids) {
        if (isEmpty(ids)) {
            return true;
        }
        List<Pair<Integer, Long>> pairs = navMapper.countChildren(ids);
        List<Integer> idList = pairs.stream().map(Pair::getKey).collect(Collectors.toList());
        ids.removeAll(idList);
        if (isEmpty(ids)) {
            return true;
        }
        return navMapper.deleteByIds(ids);
    }


    @CacheEvict(allEntries = true)
    public boolean changerOrder(int id, int step) {
        return navMapper.changeOrder(id, step);
    }


    public List<Navigator> findCandidateParent(int id) {
        List<Navigator> list = navMapper.findAll();
        Set<Integer> tree = new TreeSet<>();
        tree.add(id);
        boolean flag = true;
        while (flag) {
            flag = false;
            for (Navigator n : list) {
                if (!tree.contains(n.getId()) && tree.contains(n.getParent())) {
                    tree.add(n.getId());
                    flag = true;
                }
            }
        }
        return list.stream()
                .filter(e -> !tree.contains(e.getId()))
                .collect(Collectors.toList());
    }


    public List<TreeViewNode> buildCandidateTree() {
        List<TreeViewNode> list = new LinkedList<>();
        TreeViewNode cats = new TreeViewNode("分类");
        TreeViewNode features = new TreeViewNode("专题");
        TreeViewNode customs = new TreeViewNode("自定义");
        customs.setExt2(NavType.url.name());

        TreeNode root = categoryService.buildCatTree();
        copyCatsTree(root, cats);

        List<Feature> featureList = featureService.findFeatures();
        featureList.stream().forEach(e -> {
            TreeViewNode n = new TreeViewNode(e.getId(), e.getName(), "/f/" + e.getAlias() + ".html");
            n.setExt2(NavType.feature.name());
            features.addNode(n);
        });
        TreeViewNode cust = new TreeViewNode(0, "自定义", "custom");
        cust.setExt2(NavType.url.name());
        customs.addNode(cust);

        list.add(cats);
        list.add(features);
        list.add(customs);
        return list;
    }

    private void copyCatsTree(TreeNode root, TreeViewNode viewRoot) {
        TreeViewNode viewNode = new TreeViewNode(root.getId(), root.getName(), root.getSlug());
        if (isBlank(viewNode.getText()) || isBlank(viewNode.getExt())) {
            viewNode = viewRoot;
        } else {
            viewNode.setExt("/c/" + viewNode.getExt() + ".html");
            viewNode.setExt2(NavType.category.name());
            viewRoot.addNode(viewNode);
        }
        for (TreeNode n : root.getChildren()) {
            copyCatsTree(n, viewNode);
        }
    }


    @Cacheable(key = "'navTree'")
    public NavNode buildNavTree() {
        List<Navigator> list = navMapper.findAll();
        NavNode root = new NavNode();
        buildNavTree(list, root);
        return root;
    }

    private void buildNavTree(List<Navigator> navList, NavNode node) {
        for (Navigator n : navList) {
            if (n.getParent() != node.getId()) {
                continue;
            }
            NavNode child = new NavNode(n.getId(), n.getName(), n.getUrl(), node.getId());
            node.addChild(child);

            buildNavTree(navList, child);
        }
    }
}
