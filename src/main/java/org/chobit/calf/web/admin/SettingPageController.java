package org.chobit.calf.web.admin;

import org.chobit.calf.model.SettingModel;
import org.chobit.calf.service.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 网站配置信息维护
 *
 * @author robin
 */
@Controller
@RequestMapping("/admin/settings")
public class SettingPageController extends AbstractAdminPageController {


    @Autowired
    private SettingService settingService;

    @GetMapping
    public String get(ModelMap map) {
        SettingModel setting = settingService.all();
        map.put("setting", setting);
        return view("settings", map, "网站配置");
    }


    @PostMapping
    public String maintain(@RequestParam("name") String name,
                           @RequestParam("description") String desc,
                           @RequestParam("keywords") String keywords,
                           @RequestParam("notice") String notice,
                           @RequestParam("logo") MultipartFile logo,
                           @RequestParam("backgroundImg") MultipartFile backgroundImg) {
        settingService.maintain(name, desc, keywords, notice, logo, backgroundImg);
        interactMsg("维护网站设置成功。");
        return redirect("/admin/settings");
    }


    @GetMapping("/delete/{item}")
    public String delete(@PathVariable("item") String item) {
        settingService.delete(item);
        interactMsg("删除成功");
        return redirect("/admin/settings");
    }


}
