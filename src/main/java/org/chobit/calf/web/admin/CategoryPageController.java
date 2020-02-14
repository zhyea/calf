package org.chobit.calf.web.admin;

import org.chobit.calf.service.MetaService;
import org.chobit.calf.service.entity.Meta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

/**
 * @author robin
 */
@Controller
@RequestMapping("/admin/category")
public class CategoryPageController extends AbstractAdminPageController {

    @Autowired
    private MetaService metaService;


    @GetMapping({"/list/{parent}", "/list", ""})
    public String list(@PathVariable(value = "parent", required = false) Integer parent, ModelMap map) {
        parent = null == parent ? 0 : parent;
        String title = "分类列表";
        Meta meta = metaService.get(parent);
        if (null != meta) {
            title = title + " - " + meta.getName();
        }
        map.put("parent", null == meta ? 0 : meta.getParent());
        map.put("id", null == meta ? 0 : meta.getId());
        map.put("headerTitle", title);
        return view("cat-list", map, title);
    }


    @GetMapping({"/settings/{parent}/{id}", "/settings/{parent}", "/settings"})
    public String settings(@PathVariable(value = "parent", required = false) Integer parent,
                           @PathVariable(value = "id", required = false) Integer catId, ModelMap map) {
        Meta meta = metaService.get(null == catId ? 0 : catId);
        meta = null == meta ? new Meta() : meta;
        meta.setParent(null == parent ? 0 : parent);
        map.put("cat", meta);
        return view("cat-settings", map, null == meta ? "新增分类" : "编辑分类");
    }


    @PostMapping("/settings")
    public String maintain(@RequestParam("id") int id,
                           @RequestParam("parent") int parent,
                           @RequestParam("name") String name,
                           @RequestParam("slug") String slug,
                           @RequestParam("remark") String remark) {
        metaService.maintain(id, parent, name, slug, remark);
        interactMsg("分类信息[" + name + "]维护成功");
        return redirect("/admin/category/list/" + parent);
    }
}