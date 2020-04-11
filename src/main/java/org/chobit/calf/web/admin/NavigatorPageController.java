package org.chobit.calf.web.admin;

import org.chobit.calf.constants.NavType;
import org.chobit.calf.service.NavService;
import org.chobit.calf.service.entity.Navigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author robin
 */
@Controller
@RequestMapping("/admin/nav")
public class NavigatorPageController extends AbstractAdminPageController {

    @Autowired
    private NavService navService;


    @GetMapping({"/list/{parent}", "/list", ""})
    public String list(@PathVariable(value = "parent", required = false) Integer parent, ModelMap map) {
        parent = null == parent ? 0 : parent;
        String title = "导航菜单";
        Navigator nav = navService.get(parent);
        if (null != nav) {
            title = title + " - " + nav.getName();
        }
        map.put("parent", null == nav ? 0 : nav.getParent());
        map.put("id", null == nav ? 0 : nav.getId());
        map.put("headerTitle", title);
        return view("nav-list", map, title);
    }


    @GetMapping({"/settings/{parent}/{id}", "/settings/{parent}", "/settings"})
    public String settings(@PathVariable(value = "parent", required = false) Integer parent,
                           @PathVariable(value = "id", required = false) Integer id,
                           ModelMap map) {
        Navigator nav = navService.get(null == id ? 0 : id);
        List<Navigator> candidateParents = navService.findCandidateParent(null == id ? -1 : id);
        map.put("parent", null == parent ? 0 : parent);
        map.put("nav", null == nav ? new Navigator() : nav);
        map.put("parentCandidates", candidateParents);
        return view("nav-settings", map, null == nav ? "新增" : "编辑");
    }


    @PostMapping("/settings")
    public String maintain(@RequestParam("id") int id,
                           @RequestParam("parent") int parent,
                           @RequestParam("name") String name,
                           @RequestParam("type") NavType type,
                           @RequestParam("url") String url) {
        navService.maintain(id, parent, name, type,  url);
        interactMsg("导航项 [" + name + "] 保存成功");
        return redirect(id <= 0 ? "/admin/nav/list/" + parent : "/admin/nav/settings/" + parent + "/" + id);
    }
}
