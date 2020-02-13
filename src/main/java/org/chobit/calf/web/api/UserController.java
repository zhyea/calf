package org.chobit.calf.web.api;

import org.chobit.calf.service.UserService;
import org.chobit.calf.service.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.util.CollectionUtils.isEmpty;

/**
 * @author robin
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/data")
    public List<User> all() {
        return userService.all();
    }


    @PostMapping("/delete")
    public boolean delete(@RequestBody List<Integer> ids) {
        if (!isEmpty(ids)) {
            ids.remove(Integer.valueOf(1));
        }
        return userService.deleteByIds(ids);
    }

}
