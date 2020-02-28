package org.chobit.calf.web.admin;

import org.chobit.calf.service.RemoteCodeService;
import org.chobit.calf.service.entity.RemoteCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.concurrent.TimeUnit;

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
        RemoteCode code = remoteCodeService.add();
        map.put("code", code.getCode());
        map.put("time", new Date(code.getOpTime().getTime() + TimeUnit.MINUTES.toMillis(30)));
        return view("remote-code", map, "远程交互");
    }


}
