package org.chobit.calf.web.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static org.chobit.calf.constants.Constants.REDIRECT_PREFIX;

/**
 * @author robin
 */

@Controller
public class LoginPageController {


    @RequestMapping("/login")
    public String login() {
        return "login";
    }


    @PostMapping("/check-login")
    public String doLogin(@RequestParam("username") String username,
                          @RequestParam("password") String password) {

        return REDIRECT_PREFIX + "/";
    }


    public String logout() {
        return "index";
    }
}
