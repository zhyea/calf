package org.chobit.calf.web.admin;

import org.chobit.calf.model.AlertMessage;
import org.chobit.calf.tools.SessionHolder;
import org.chobit.calf.web.AbstractPageController;

/**
 * @author robin
 */
public class AbstractAdminPageController extends AbstractPageController {


    @Override
    protected String titleParent() {
        return "Calf";
    }


    /**
     * 从session中读取信息
     */
    public AlertMessage takeAlertFromSession() {
        return SessionHolder.takeAttribute("alert");
    }


    /**
     * 将报警信息添加到session
     */
    public void addAlertToSession(AlertMessage msg) {
        SessionHolder.addAttribute("alert", msg);
    }


}
