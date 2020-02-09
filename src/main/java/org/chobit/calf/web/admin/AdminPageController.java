package org.chobit.calf.web.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author robin
 */
@Controller
@RequestMapping("/admin")
public class AdminPageController {


    @RequestMapping("/login")
    public String login() {
        return "login";
    }

}
