package org.chobit.calf.web.admin;

import org.chobit.calf.constants.AlertType;
import org.chobit.calf.model.AlertMessage;
import org.chobit.calf.tools.SessionHolder;
import org.chobit.calf.web.AbstractPageController;
import org.springframework.ui.ModelMap;

/**
 * @author robin
 */
public class AbstractAdminPageController extends AbstractPageController {


    @Override
    protected String titleParent() {
        return "Calf";
    }


    /**
     * 添加交互信息，显示给前端用户
     */
    public void interactMsg(String msg) {
        SessionHolder.addAlert(new AlertMessage(AlertType.SUCCESS, msg));
    }


    @Override
    public String view(String viewName, ModelMap map, String title) {
        AlertMessage alert = SessionHolder.takeAlert();
        if (null != alert) {
            map.put("alert", alert);
        }
        return super.view(viewName, map, title);
    }
}
