package org.chobit.calf.web.admin;

import org.chobit.calf.model.WorkModel;
import org.chobit.calf.service.WorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author robin
 */
@Controller
@RequestMapping("/admin/work")
public class WorkPageController extends AbstractAdminPageController {

    @Autowired
    private WorkService workService;


    @GetMapping({"/list", ""})
    public String list(ModelMap map) {
        return view("work-list", map, "作品列表");
    }


    @GetMapping({"/settings/{id}", "/settings"})
    public String settings(@PathVariable(value = "id", required = false) Integer id, ModelMap map) {
        WorkModel work = workService.getWorkModel(null == id ? 0 : id);
        map.put("work", null == work ? new WorkModel() : work);
        return view("work-settings", map, null == work ? "新增作品" : "作品编辑");
    }


    @GetMapping("/delete/cover/{id}")
    public String deleteCover(@PathVariable("id") int id) {
        workService.deleteCover(id);
        interactMsg("封面删除成功");
        return redirect("/admin/work/settings/" + id);
    }


    @PostMapping("/settings")
    public String maintain(@RequestParam int id,
                           @RequestParam String name,
                           @RequestParam int authorId,
                           @RequestParam String author,
                           @RequestParam String country,
                           @RequestParam int catId,
                           @RequestParam String cat,
                           @RequestParam String brief,
                           @RequestParam MultipartFile cover) {
        workService.maintain(id, name, authorId, author, country, catId, cat, brief, cover);
        interactMsg("作品信息维护成功");
        if (id > 0) {
            return redirect("/admin/work/settings/" + id);
        }
        return redirect("/admin/work/list");
    }

}
