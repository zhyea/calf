package org.chobit.calf.web.front;

import org.chobit.calf.constants.MetaType;
import org.chobit.calf.model.MetaNode;
import org.chobit.calf.service.MetaService;
import org.chobit.calf.service.SettingService;
import org.chobit.calf.web.AbstractPageController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;

/**
 * @author robin
 */
public abstract class AbstractFrontPageController extends AbstractPageController {

    @Autowired
    private SettingService settingService;

    @Autowired
    private MetaService metaService;


    @Override
    public String view(String viewName, ModelMap map, String title) {
        String siteName = settingService.getSiteName();
        String notice = settingService.getNotice();
        MetaNode cats = metaService.buildMetaTree(MetaType.CATEGORY);

        map.put("siteName", siteName);
        map.put("notice", notice);
        map.put("cats", cats);

        return super.view(viewName, map, title);
    }

    @Override
    protected String titleParent() {
        return settingService.getSiteName();
    }
}
