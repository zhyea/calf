package org.chobit.calf.web.admin;

import org.chobit.calf.web.AbstractPageController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author robin
 */
@Controller
@RequestMapping("/admin")
public class AdminPageController extends AbstractPageController {


    @GetMapping("/")
    public String index(ModelMap model) {
        return forward("home", model, "首页");
    }


    @Override
    protected String themeName() {
        return "Calf";
    }
}
