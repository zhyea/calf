package org.chobit.calf.web.admin;

import org.chobit.calf.service.AuthorService;
import org.chobit.calf.service.entity.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

/**
 * @author robin
 */
@Controller
@RequestMapping("/admin/author")
public class AuthorPageController extends AbstractAdminPageController {


    @Autowired
    private AuthorService authorService;


    @GetMapping({"/list", ""})
    public String list(ModelMap map) {
        return view("author-list", map, "作者列表");
    }


    @GetMapping({"/settings/{id}", "/settings"})
    public String settings(@PathVariable(value = "id", required = false) Integer id, ModelMap map) {
        Author author = authorService.get(null == id ? 0 : id);
        map.put("author", null == author ? new Author() : author);
        return view("author-settings", map, null == author ? "新增作者" : "编辑作者-" + author.getName());
    }


    @PostMapping("/settings")
    public String maintain(@RequestParam("id") int id,
                           @RequestParam("name") String name,
                           @RequestParam("country") String country,
                           @RequestParam("bio") String bio, ModelMap map) {
        authorService.maintain(id, name, country, bio);
        interactMsg("作者信息保存成功");
        return redirect(id > 0 ? "/admin/author/settings/" + id : "/admin/author/list");
    }


    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id) {
        authorService.delete(id);
        interactMsg("删除作者信息成功");
        return redirect("/admin/author/list");
    }


    @GetMapping("/works/{id}")
    public String works(@PathVariable("id") int id, ModelMap map) {
        Author author = authorService.get(id);
        if (null == author) {
            return redirect("/admin/author/list");
        }
        map.put("author", author);
        return view("author-works", map, author.getName() + "作品列表");
    }

}
