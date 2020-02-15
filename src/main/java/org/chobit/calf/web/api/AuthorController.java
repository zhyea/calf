package org.chobit.calf.web.api;

import org.chobit.calf.model.Page;
import org.chobit.calf.model.PageResult;
import org.chobit.calf.model.Pair;
import org.chobit.calf.service.AuthorService;
import org.chobit.calf.tools.LowerCaseKeyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author robin
 */
@RestController
@RequestMapping("/api/author")
public class AuthorController {


    @Autowired
    private AuthorService authorService;


    @PostMapping("/data")
    public PageResult<LowerCaseKeyMap> all(@RequestBody Page page) {
        return authorService.queryInPage(page);
    }


    @GetMapping("/suggest")
    public Pair<String, Object> suggest(@RequestParam("key") String key) {
        return authorService.findSuggest(key);
    }

}
