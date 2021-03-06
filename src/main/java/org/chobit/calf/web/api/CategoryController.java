package org.chobit.calf.web.api;

import org.chobit.calf.model.Category;
import org.chobit.calf.model.Pair;
import org.chobit.calf.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author robin
 */
@RestController
@RequestMapping("/api/category")
public class CategoryController {


    @Autowired
    private CategoryService categoryService;


    @GetMapping("/data/{parent}")
    public List<Category> allCategory(@PathVariable("parent") int parent) {
        return categoryService.findCatByParent(parent);
    }


    @PostMapping("/change-order/{id}")
    public boolean changeOrder(@PathVariable("id") int id, @RequestBody int step) {
        return categoryService.changerOrder(id, step);
    }


    @PostMapping("/delete")
    public boolean delete(@RequestBody List<Integer> ids) {
        return categoryService.deleteByIds(ids);
    }

    @GetMapping("/suggest")
    public Pair<String, Object> suggest(@RequestParam("key") String key) {
        return categoryService.findCatsSuggest(key);
    }

}
