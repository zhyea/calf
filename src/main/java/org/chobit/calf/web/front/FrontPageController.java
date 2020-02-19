package org.chobit.calf.web.front;

import org.chobit.calf.model.CategoryWork;
import org.chobit.calf.model.Page;
import org.chobit.calf.model.PageResult;
import org.chobit.calf.model.WorkModel;
import org.chobit.calf.service.FeatureService;
import org.chobit.calf.service.MetaService;
import org.chobit.calf.service.WorkService;
import org.chobit.calf.service.entity.Meta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.LinkedList;
import java.util.List;

import static org.chobit.calf.constants.Config.DEFAULT_PAGE_LENGTH;

/**
 * @author robin
 */
@Controller
@RequestMapping("/")
public class FrontPageController extends AbstractFrontPageController {


    @Autowired
    private MetaService metaService;
    @Autowired
    private WorkService workService;
    @Autowired
    private FeatureService featureService;


    @GetMapping({"", "/"})
    public String index(ModelMap map) {
        List<CategoryWork> all = workService.homeWorks();
        List<WorkModel> recommend = featureService.findRecordsByAlias("recommend");
        map.put("all", all);
        map.put("recommend", recommend);
        return view("index", map, "首页");
    }


    @GetMapping({"/{catSlug}", "/{catSlug}/{page}"})
    public String category(@PathVariable("catSlug") String catSlug,
                           @PathVariable(value = "page", required = false) Integer page,
                           ModelMap map) {
        page = null == page ? 1 : page;
        Meta cat = metaService.getBySlug(catSlug);
        List<WorkModel> works = new LinkedList<>();
        List<WorkModel> recommend = featureService.findRecordsByAlias("recommend");
        long total = 0;
        if (null != cat) {
            PageResult<WorkModel> r = workService.findWithCat(cat.getId(), new Page(page - 1, DEFAULT_PAGE_LENGTH));
            works = r.getRows();
            total = workService.countWithCat(cat.getId());
        } else {
            return redirect("/");
        }
        map.put("cat", cat);
        map.put("works", works);
        map.put("page", page);
        map.put("total", (total / DEFAULT_PAGE_LENGTH + (total % DEFAULT_PAGE_LENGTH == 0 ? 0 : 1)));
        map.put("recommend", recommend);
        return view("category", map, cat.getName());
    }


}
