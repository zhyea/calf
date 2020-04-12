package org.chobit.calf.web.front;

import org.chobit.calf.model.*;
import org.chobit.calf.service.*;
import org.chobit.calf.service.entity.Author;
import org.chobit.calf.service.entity.Chapter;
import org.chobit.calf.service.entity.Feature;
import org.chobit.calf.service.entity.Meta;
import org.chobit.calf.tools.VisitCounter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.TreeMap;

import static org.chobit.calf.constants.Config.DEFAULT_PAGE_LENGTH;
import static org.chobit.calf.utils.Strings.isNotBlank;

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


    /**
     * 进入首页
     */
    @GetMapping({"", "/"})
    public String index(ModelMap map) {
        List<CategoryWork> all = workService.homeWorks();
        List<WorkModel> recommend = featureService.findRecordsByAlias("recommend");
        map.put("all", all);
        map.put("recommend", recommend);
        return view("index", map, "");
    }

    /**
     * 进入分类页
     */
    @GetMapping({"/c/{catSlug}", "/c/{catSlug}/{page}"})
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
        map.put("cat", cat);
        map.put("works", works);
        map.put("page", pageNo);
        map.put("total", r.getTotal());
        map.put("totalPage", ((r.getTotal() > 0 ? r.getTotal() - 1 : 0) / DEFAULT_PAGE_LENGTH));
        map.put("recommend", recommend);
        return view("category", map, cat.getName());
    }

    /**
     * 进入作者页
     */
    @GetMapping({"/author/{id}", "/author/{id}/{page}"})
    public String author(@PathVariable("id") int id,
                         @PathVariable(value = "page", required = false) Integer pageNo,
                         ModelMap map) {
        Author author = authorService.get(id);
        if (null == author) {
            return redirect("/");
        }
        pageNo = null == pageNo ? 0 : pageNo;
        PageResult<WorkModel> r = workService.findWithAuthor(author.getId(), new Page(pageNo, DEFAULT_PAGE_LENGTH));
        List<WorkModel> works = r.getRows();
        map.put("author", author);
        map.put("works", works);
        map.put("page", pageNo);
        map.put("total", r.getTotal());
        map.put("totalPage", ((r.getTotal() > 0 ? r.getTotal() - 1 : 0) / DEFAULT_PAGE_LENGTH));
        return view("author", map, author.getName());
    }

    /**
     * 进入专题页
     */
    @GetMapping({"/f/{alias}", "/f/{alias}/{page}"})
    public String feature(@PathVariable("alias") String alias,
                          @PathVariable(value = "page", required = false) Integer pageNo,
                          ModelMap map) {
        Feature feature = featureService.getByAlias(alias);
        if (null == feature) {
            return redirect("/");
        }
        pageNo = null == pageNo ? 0 : pageNo;
        PageResult<WorkModel> r = workService.findWithFeature(feature.getAlias(), new Page(pageNo, DEFAULT_PAGE_LENGTH));
        List<WorkModel> works = r.getRows();
        map.put("feature", feature);
        map.put("works", works);
        map.put("page", pageNo);
        map.put("total", r.getTotal());
        map.put("totalPage", ((r.getTotal() > 0 ? r.getTotal() - 1 : 0) / DEFAULT_PAGE_LENGTH));
        if (isNotBlank(feature.getBackground())) {
            map.put("background", feature.getBackground());
            map.put("bgRepeat", feature.getBgRepeat());
        }
        if (isNotBlank(feature.getCover())) {
            map.put("logo", feature.getCover());
        }
        map.put("keywords", feature.getKeywords());
        map.put("description", feature.getBrief());

        return view("feature", map, feature.getName());
    }


    /**
     * 进入作品页
     */
    @GetMapping({"/work/{id}"})
    public String work(@PathVariable("id") int workId,
                       ModelMap map) {
        WorkModel work = workService.getDetail(workId);
        if (null == work) {
            return redirect("/");
        }

        VisitCounter.add(workId, 1);

        PageResult<WorkModel> r = workService.findWithAuthor(work.getAuthorId(), new Page(0, 7));
        List<VolumeModel> vols = chapterService.chapters(workId);
        map.put("w", work);
        map.put("relate", r.getRows());
        map.put("vols", vols);
        return view("work", map, work.getName());
    }


    /**
     * 进入章节
     */
    @GetMapping({"/chapter/{id}"})
    public String chapter(@PathVariable("id") int id,
                          ModelMap map) {
        ChapterAndVol chapter = chapterService.getChapterAndVol(id);
        if (null == chapter) {
            return redirect("/");
        }

        VisitCounter.add(chapter.getWorkId(), 0.2);

        WorkModel work = workService.getDetail(chapter.getWorkId());
        Chapter last = chapterService.getLast(chapter.getWorkId(), chapter.getId());
        Chapter next = chapterService.getNext(chapter.getWorkId(), chapter.getId());

        map.put("w", work);
        map.put("c", chapter);
        map.put("last", null == last ? 0 : last.getId());
        map.put("next", null == next ? 0 : next.getId());

        return view("chapter", map, chapter.getName() + "-" + chapter.getVolName() + "-" + work.getName());
    }


    /**
     * 全部作者
     */
    @GetMapping({"/authors"})
    public String authors(ModelMap map) {
        TreeMap<String, List<Author>> all = authorService.allAuthors();
        map.put("all", all);
        return view("authors", map, "全部作者");
    }
}
