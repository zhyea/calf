package org.chobit.calf.web.front;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author robin
 */
@Controller
@RequestMapping("/")
public class FrontPageController {


    @RequestMapping("/")
    public String index() {
        return "index";
    }







}
