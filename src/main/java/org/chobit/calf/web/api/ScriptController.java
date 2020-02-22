package org.chobit.calf.web.api;

import org.chobit.calf.service.ScriptService;
import org.chobit.calf.service.entity.Script;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author robin
 */
@RestController
@RequestMapping("/api/spt")
public class ScriptController {

    @Autowired
    private ScriptService scriptService;

    @RequestMapping({"/data", ""})
    public List<Script> data() {
        return scriptService.all();
    }

}
