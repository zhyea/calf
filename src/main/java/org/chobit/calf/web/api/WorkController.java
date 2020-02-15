package org.chobit.calf.web.api;

import org.chobit.calf.model.Page;
import org.chobit.calf.model.PageResult;
import org.chobit.calf.model.WorkModel;
import org.chobit.calf.service.WorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author robin
 */
@RestController
@RequestMapping("/api/work")
public class WorkController {


    @Autowired
    private WorkService workService;


    @PostMapping("/data")
    public PageResult<WorkModel> data(@RequestBody Page page) {
        return workService.findInPage(page);
    }

}
