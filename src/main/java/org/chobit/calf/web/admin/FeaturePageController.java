package org.chobit.calf.web.admin;

import org.chobit.calf.service.FeatureService;
import org.chobit.calf.service.entity.Feature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author robin
 */
@Controller
@RequestMapping("/admin/feature")
public class FeaturePageController extends AbstractAdminPageController {

    @Autowired
    private FeatureService featureService;


    @GetMapping({"/list", ""})
    public String list(ModelMap map) {
        return view("feature-list", map, "专题列表");
    }


    @GetMapping({"/settings/{id}", "/settings"})
    public String settings(@PathVariable(value = "id", required = false) Integer id,
                           ModelMap map) {
        Feature feature = featureService.get(null == id ? 0 : id);
        map.put("f", null == feature ? new Feature() : feature);
        return view("feature-settings", map, null == feature ? "新增专题" : "编辑专题" + feature.getName());
    }


    @PostMapping("/settings")
    public String maintain(@RequestParam int id,
                           @RequestParam String name,
                           @RequestParam String alias,
                           @RequestParam String keywords,
                           @RequestParam String brief,
                           @RequestParam MultipartFile cover,
                           @RequestParam MultipartFile background,
                           @RequestParam String bgRepeat) {
        featureService.maintain(id, name, alias, keywords, brief, cover, background, bgRepeat);
        interactMsg("专题信息保存成功");
        if (id > 0) {
            return redirect("/admin/feature/settings/" + id);
        }
        return redirect("/admin/feature/list");
    }


    @GetMapping("/delete/cover/{id}")
    public String deleteCover(@PathVariable("id") int id) {
        featureService.deleteCover(id);
        interactMsg("专题封面删除成功");
        return redirect("/admin/feature/settings/" + id);
    }


    @GetMapping("/delete/bg/{id}")
    public String deleteBackground(@PathVariable("id") int id) {
        featureService.deleteBg(id);
        interactMsg("专题背景删除成功");
        return redirect("/admin/feature/settings/" + id);
    }


    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id) {
        featureService.delete(id);
        interactMsg("删除专题成功");
        return redirect("/admin/feature/list");
    }


    @GetMapping("/records/{id}")
    public String records(@PathVariable("id") int id, ModelMap map) {
        Feature feature = featureService.get(id);
        map.put("f", feature);
        return view("feature-records", map, "专题" + feature.getName() + "作品列表");
    }

}
