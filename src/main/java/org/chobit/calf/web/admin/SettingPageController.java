package org.chobit.calf.web.admin;

import org.chobit.calf.model.AlertMessage;
import org.chobit.calf.model.Setting;
import org.chobit.calf.service.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

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
        Setting setting = settingService.all();
        map.put("setting", setting);
        AlertMessage alert = takeAlertFromSession();
        if (null != alert) {
            map.put("alert", alert);
        }
        return view("settings", map, "网站配置");
    }


    @PostMapping
    public String maintain(@RequestParam("name") String name,
                           @RequestParam("description") String desc,
                           @RequestParam("keywords") String keywords,
                           @RequestParam("notice") String notice,
                           @RequestParam("logo") MultipartFile logo,
                           @RequestParam("backgroundImg") MultipartFile backgroundImg, HttpSession session) {

        AlertMessage msg = settingService.maintain(name, desc, keywords, notice, logo, backgroundImg);
        addAlertToSession(msg);
        return redirect("settings");
    }


}
