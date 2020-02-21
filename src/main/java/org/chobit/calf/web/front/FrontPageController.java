package org.chobit.calf.web.front;

import org.chobit.calf.model.*;
import org.chobit.calf.service.*;
import org.chobit.calf.service.entity.Author;
import org.chobit.calf.service.entity.Chapter;
import org.chobit.calf.service.entity.Meta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
    @Autowired
    private AuthorService authorService;
    @Autowired
    private ChapterService chapterService;


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
                           @PathVariable(value = "page", required = false) Integer pageNo,
                           ModelMap map) {
        pageNo = null == pageNo ? 0 : pageNo;
        Meta cat = metaService.getBySlug(catSlug);
        if (null == cat) {
            return redirect("/");
        }
        List<WorkModel> recommend = featureService.findRecordsByAlias("recommend");
        PageResult<WorkModel> r = workService.findWithCat(cat.getId(), new Page(pageNo, DEFAULT_PAGE_LENGTH));
        List<WorkModel> works = r.getRows();
        long total = workService.countWithCat(cat.getId());
        map.put("cat", cat);
        map.put("works", works);
        map.put("page", pageNo);
        map.put("total", (total / DEFAULT_PAGE_LENGTH));
        map.put("recommend", recommend);
        return view("category", map, cat.getName());
    }


    @GetMapping({"/author/{id}", "/author/{id}/{page}"})
    public String author(@PathVariable("id") int id,
                         @PathVariable(value = "page", required = false) Integer pageNo,
                         ModelMap map) {
        Author author = authorService.get(id);
        if (null == author) {
            return redirect("/");
        }
        pageNo = null == pageNo ? 0 : pageNo;
        List<WorkModel> recommend = featureService.findRecordsByAlias("recommend");
        PageResult<WorkModel> r = workService.findWithAuthor(author.getId(), new Page(pageNo, DEFAULT_PAGE_LENGTH));
        List<WorkModel> works = r.getRows();
        long total = workService.countWithAuthor(author.getId());
        map.put("author", author);
        map.put("works", works);
        map.put("page", pageNo);
        map.put("total", (total / DEFAULT_PAGE_LENGTH));
        map.put("recommend", recommend);
        return view("author", map, author.getName());
    }


    @GetMapping({"/work/{id}"})
    public String work(@PathVariable("id") int workId,
                       ModelMap map) {
        WorkModel work = workService.getDetail(workId);
        if (null == work) {
            return redirect("/");
        }
        PageResult<WorkModel> r = workService.findWithAuthor(work.getAuthorId(), new Page(0, DEFAULT_PAGE_LENGTH));
        List<VolumeModel> vols = chapterService.chapters(workId);
        map.put("w", work);
        map.put("relate", r.getRows());
        map.put("vols", vols);
        return view("work", map, work.getName());
    }


    @GetMapping({"/chapter/{id}"})
    public String chapter(@PathVariable("id") int id,
                          ModelMap map) {
        Chapter chapter = chapterService.get(id);
        if (null == chapter) {
            return redirect("/");
        }
        WorkModel work = workService.getDetail(chapter.getWorkId());
        Chapter last = chapterService.getLast(chapter.getWorkId(), chapter.getId());
        Chapter next = chapterService.getNext(chapter.getWorkId(), chapter.getId());

        map.put("w", work);
        map.put("c", chapter);
        map.put("last", null == last ? 0 : last.getId());
        map.put("next", null == next ? 0 : next.getId());

        return view("chapter", map, chapter.getName());
    }


}
