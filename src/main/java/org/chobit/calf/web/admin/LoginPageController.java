package org.chobit.calf.web.admin;

import org.chobit.calf.web.AbstractPageController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author robin
 */

@Controller
public class LoginPageController extends AbstractPageController {


    @RequestMapping("/login")
    public String login() {
        return "login";
    }


    @PostMapping("/check-login")
    public String doLogin(@RequestParam("username") String username,
                          @RequestParam("password") String password) {

        return redirect("");
    }


    public String logout() {
        return "index";
    }


    @Override
    protected String themeName() {
        return "Calf";
    }
}
