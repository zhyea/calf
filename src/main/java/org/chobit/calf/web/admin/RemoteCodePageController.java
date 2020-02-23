package org.chobit.calf.web.admin;

import org.chobit.calf.service.RemoteCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author robin
 */
@Controller
@RequestMapping("/admin/remote")
public class RemoteCodePageController extends AbstractAdminPageController {

    @Autowired
    private RemoteCodeService remoteCodeService;


    @GetMapping("/gen")
    public String gen(ModelMap map) {
        String code = remoteCodeService.add();
        map.put("code", code);
        return view("remote-code", map, "远程交互");
    }


}
