package org.chobit.calf.web.admin;

import org.chobit.calf.service.UserService;
import org.chobit.calf.service.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @author robin
 */
@Controller
@RequestMapping("/login")
public class LoginPageController extends AbstractAdminPageController {

    @Autowired
    private UserService userService;

    @GetMapping("")
    public String login(ModelMap map) {
        return view("login", map, "登录页");
    }


    @PostMapping("/check")
    public String check(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        HttpServletRequest request) {
        User user = userService.check(username, password, request.getRemoteHost());
        if (null == user) {
            return redirect("/login");
        }
        return redirect("/admin");
    }


    public String logout() {
        return "index";
    }


    @Override
    protected String titleParent() {
        return "Calf";
    }
}
