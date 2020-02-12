package org.chobit.calf.web.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author robin
 */
@Controller
@RequestMapping("/admin")
public class AdminPageController extends AbstractAdminPageController {


    @GetMapping()
    public String index(ModelMap model) {
        return view("home", model, "首页");
    }
}
