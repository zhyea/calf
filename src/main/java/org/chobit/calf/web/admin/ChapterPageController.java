package org.chobit.calf.web.admin;

import org.chobit.calf.model.VolumeModel;
import org.chobit.calf.service.ChapterService;
import org.chobit.calf.service.VolumeService;
import org.chobit.calf.service.WorkService;
import org.chobit.calf.service.entity.Chapter;
import org.chobit.calf.service.entity.Volume;
import org.chobit.calf.service.entity.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author robin
 */
@Controller
@RequestMapping("/admin/chapter")
public class ChapterPageController extends AbstractAdminPageController {


    @Autowired
    private ChapterService chapterService;
    @Autowired
    private WorkService workService;
    @Autowired
    private VolumeService volumeService;

    @GetMapping("/all/{workId}")
    public String chapters(@PathVariable("workId") int workId, ModelMap map) {
        Work work = workService.get(workId);

        if (null == work) {
            return redirect("/admin/work/list");
        }
        List<VolumeModel> vols = chapterService.chapters(workId);

        map.put("work", work);
        map.put("vols", vols);

        return view("chapters", map, "章节信息" + "-" + work.getName());
    }


    @GetMapping({"/{workId}/{chapterId}", "/{workId}"})
    public String edit(@PathVariable("workId") int workId,
                       @PathVariable(value = "chapterId", required = false) Integer chapterId,
                       ModelMap map) {
        Work work = workService.get(workId);
        Chapter chapter = chapterService.get(null == chapterId ? 0 : chapterId);
        if (null == work || null == chapter) {
            return redirect("/admin/work/list");
        }

        Volume volume = volumeService.get(chapter.getVolumeId());

        map.put("work", work);
        map.put("volume", volume);
        map.put("chapter", chapter);

        return view("chapter-edit", map, null == chapter ? "新增章节" : "编辑章节" + "-" + chapter.getName() + "-" + work.getName());
    }


    @PostMapping("/edit")
    public String edit(@RequestParam int id,
                       @RequestParam int workId,
                       @RequestParam String name,
                       @RequestParam int volumeId,
                       @RequestParam String volume,
                       @RequestParam String content) {
        chapterService.maintain(id, workId, name, volumeId, volume, content);
        interactMsg("章节信息保存成功");
        if (id <= 0) {
            return redirect("/admin/chapter/" + workId);
        }
        return redirect("/admin/chapter/" + workId + "/" + id);
    }


    @PostMapping("/upload")
    public String upload(@RequestParam int workId,
                         @RequestParam MultipartFile myTxt) throws IOException {
        chapterService.upload(workId, myTxt);
        return redirect("/admin/chapter/all/" + workId);
    }


    @GetMapping("/delete/{workId}/{volId}/{id}")
    public String delete(@PathVariable("workId") int workId,
                         @PathVariable("volId") int volId,
                         @PathVariable("id") int id) {
        chapterService.delete(volId, id);
        interactMsg("删除成功");
        return redirect("/admin/chapter/all/" + workId);
    }


    @GetMapping("/delete-all/{workId}")
    public String deleteAll(@PathVariable("workId") int workId) {
        workService.deleteChapters(workId);
        interactMsg("删除章节信息成功");
        return redirect("/admin/chapter/all/" + workId);
    }

}
