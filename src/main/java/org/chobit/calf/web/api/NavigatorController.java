package org.chobit.calf.web.api;

import org.chobit.calf.model.TreeViewNode;
import org.chobit.calf.service.NavService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author robin
 */
@RestController
@RequestMapping("/api/nav")
public class NavigatorController {


    @Autowired
    private NavService navService;


    @GetMapping("/data/{parent}")
    public List<Map<String, Object>> allCategory(@PathVariable("parent") int parent) {
        return navService.findByParent(parent);
    }


    @PostMapping("/change-order/{id}")
    public boolean changeOrder(@PathVariable("id") int id, @RequestBody int step) {
        return navService.changerOrder(id, step);
    }


    @PostMapping("/delete")
    public boolean delete(@RequestBody List<Integer> ids) {
        return navService.deleteByIds(ids);
    }


    @PostMapping("/candidates")
    public List<TreeViewNode> candidates() {
        return navService.buildCandidateTree();
    }


}
