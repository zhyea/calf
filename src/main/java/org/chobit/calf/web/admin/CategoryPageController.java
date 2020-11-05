package org.chobit.calf.web.admin;

import org.chobit.calf.service.CategoryService;
import org.chobit.calf.service.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author robin
 */
@Controller
@RequestMapping("/admin/category")
public class CategoryPageController extends AbstractAdminPageController {

    @Autowired
    private CategoryService categoryService;


    @GetMapping({"/list/{parent}", "/list", ""})
    public String list(@PathVariable(value = "parent", required = false) Integer parent, ModelMap map) {
        parent = null == parent ? 0 : parent;
        String title = "分类列表";
        Category category = categoryService.get(parent);
        if (null != category) {
            title = title + " - " + category.getName();
        }
        map.put("parent", null == category ? 0 : category.getParent());
        map.put("id", null == category ? 0 : category.getId());
        map.put("headerTitle", title);
        return view("cat-list", map, title);
    }


    @GetMapping({"/settings/{parent}/{id}", "/settings/{parent}", "/settings"})
    public String settings(@PathVariable(value = "parent", required = false) Integer parent,
                           @PathVariable(value = "id", required = false) Integer catId,
                           ModelMap map) {
        Category category = categoryService.get(null == catId ? 0 : catId);
        List<Category> candidateParents = categoryService.findCandidateParentCats(null == catId ? -1 : catId);
        map.put("parent", null == parent ? 0 : parent);
        map.put("cat", null == category ? new Category() : category);
        map.put("parentCandidates", candidateParents);
        return view("cat-settings", map, null == category ? "新增分类" : "编辑分类");
    }


    @PostMapping("/settings")
    public String maintain(@RequestParam("id") int id,
                           @RequestParam("parent") int parent,
                           @RequestParam("name") String name,
                           @RequestParam("slug") String slug,
                           @RequestParam("remark") String remark) {
        categoryService.maintain(id, parent, name, slug, remark);
        interactMsg("分类信息[" + name + "]保存成功");
        return redirect(id <= 0 ? "/admin/category/list/" + parent : "/admin/category/settings/" + parent + "/" + id);
    }
}
