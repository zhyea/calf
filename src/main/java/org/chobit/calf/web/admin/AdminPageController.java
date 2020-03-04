package org.chobit.calf.web.admin;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.chobit.calf.service.UserService;
import org.chobit.calf.service.entity.User;
import org.chobit.calf.tools.SessionHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;

/**
 * @author robin
 */
@Controller
public class AdminPageController extends AbstractAdminPageController {


    private static final Logger logger = LoggerFactory.getLogger(AdminPageController.class);

    @Autowired
    private UserService userService;


    @GetMapping("/admin")
    public String index(ModelMap model) {
        return view("home", model, "首页");
    }


    @GetMapping("/login")
    public String login(ModelMap map) {
        return view("login", map, "登录页");
    }


    @PostMapping("/login/check")
    public String check(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        HttpServletRequest request) {
        User user = userService.check(username, password, request);
        if (null == user) {
            return redirect("/login");
        }
        return redirect("/admin");
    }


    @GetMapping("/logout")
    public String logout() {
        User user = SessionHolder.removeUser();
        logger.info("user {} has logout.", null != user ? user.getNickname() : "zz");
        return redirect("/");
    }


    @RequestMapping(value = {"/robots", "/robot.txt", "/robots.txt"})
    public void robot(HttpServletResponse response) {
        ClassLoader classLoader = getClass().getClassLoader();
        try (InputStream input = classLoader.getResourceAsStream("static/robots.txt")) {
            response.addHeader("Content-disposition", "filename=robots.txt");
            response.setContentType("text/plain");
            if (null != input) {
                IOUtils.copy(input, response.getOutputStream());
            }
            response.flushBuffer();
        } catch (Exception e) {
            logger.error("Problem with displaying robot.txt", e);
        }
    }
}
