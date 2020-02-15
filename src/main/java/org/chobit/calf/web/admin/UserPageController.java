package org.chobit.calf.web.admin;

import org.chobit.calf.service.UserService;
import org.chobit.calf.service.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

/**
 * @author robin
 */
@Controller
@RequestMapping("/admin/user")
public class UserPageController extends AbstractAdminPageController {

    @Autowired
    private UserService userService;

    private static final String VIEW_USER_SETTING = "";


    @GetMapping({"/list", ""})
    public String list(ModelMap map) {
        return view("user-list", map, "用户列表");
    }


    @GetMapping({"/settings/{id}", "/settings"})
    public String settings(@PathVariable(value = "id", required = false) Integer userId, ModelMap map) {
        User user = userService.get(null == userId ? 0 : userId);
        map.put("user", null == user ? new User() : user);
        return view("user-settings", map, null == user ? "新增用户" : "编辑用户");
    }


    @PostMapping("/settings")
    public String maintain(
            @RequestParam("id") int id,
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("email") String email,
            @RequestParam("nickname") String nickname) {
        userService.maintain(id, username, password, email, nickname);
        interactMsg("用户信息维护成功.");
        return redirect(id > 0 ? "/admin/user/list" : "/admin/user/settings/" + id);
    }


}
