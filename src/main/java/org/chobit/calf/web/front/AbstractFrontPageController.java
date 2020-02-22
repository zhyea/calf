package org.chobit.calf.web.front;

import org.chobit.calf.constants.MetaType;
import org.chobit.calf.model.MetaNode;
import org.chobit.calf.model.SettingModel;
import org.chobit.calf.service.MetaService;
import org.chobit.calf.service.ScriptService;
import org.chobit.calf.service.SettingService;
import org.chobit.calf.service.entity.Script;
import org.chobit.calf.web.AbstractPageController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;

import java.util.List;

/**
 * @author robin
 */
public abstract class AbstractFrontPageController extends AbstractPageController {

    @Autowired
    private SettingService settingService;
    @Autowired
    private MetaService metaService;
    @Autowired
    private ScriptService scriptService;

    @Override
    public String view(String viewName, ModelMap map, String title) {
        MetaNode cats = metaService.buildMetaTree(MetaType.CATEGORY);
        SettingModel settings = settingService.all();

        map.put("cats", cats);
        map.put("siteName", settings.getName());
        map.put("notice", settings.getNotice());
        if (!map.containsKey("logo")) {
            map.put("logo", settings.getLogo());
        }
        if (!map.containsKey("background")) {
            map.put("background", settings.getBackgroundImg());
        }
        if (!map.containsKey("bgRepeat")) {
            map.put("bgRepeat", settings.getBgRepeat());
        }

        List<Script> all = scriptService.all();
        for (Script c : all) {
            map.put(c.getCode(), c.getScript());
        }

        return super.view(viewName, map, title);
    }

    @Override
    protected String titleParent() {
        return settingService.getSiteName();
    }
}
