package org.chobit.calf.web.admin;

import org.chobit.calf.service.ScriptService;
import org.chobit.calf.service.entity.Script;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

/**
 * @author robin
 */
@Controller
@RequestMapping("/admin/spt")
public class ScriptPageController extends AbstractAdminPageController {


    @Autowired
    private ScriptService scriptService;


    @GetMapping({"/list", ""})
    public String list(ModelMap map) {
        return view("script-list", map, "脚本列表");
    }


    @GetMapping({"/edit/{id}", "/edit"})
    public String script(@PathVariable(value = "id", required = false) Integer id,
                         ModelMap map) {
        id = null == id ? 0 : id;
        Script script = scriptService.get(id);
        map.put("spt", null == script ? new Script() : script);
        return view("script-settings", map, null == script ? "新增脚本" : "脚本编辑-" + script.getName());
    }


    @PostMapping("/edit")
    public String maintain(@RequestParam int id,
                           @RequestParam String name,
                           @RequestParam String code,
                           @RequestParam String script,
                           @RequestParam String remark) {
        scriptService.maintain(id, name, code, script, remark);
        interactMsg("脚本信息维护成功");
        if (id > 0) {
            return redirect("/admin/spt/edit/" + id);
        }
        return redirect("/admin/spt/list");
    }


    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id) {
        scriptService.delete(id);
        interactMsg("删除成功");
        return redirect("/admin/spt/list");
    }

}
