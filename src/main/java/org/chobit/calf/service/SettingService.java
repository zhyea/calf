package org.chobit.calf.service;

import org.chobit.calf.constants.AlertType;
import org.chobit.calf.except.CalfArgsException;
import org.chobit.calf.model.AlertMessage;
import org.chobit.calf.model.Setting;
import org.chobit.calf.service.entity.PairRecord;
import org.chobit.calf.service.mapper.SettingMapper;
import org.chobit.calf.tools.UploadKit;
import org.chobit.calf.utils.Args;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.chobit.calf.utils.Strings.isNotBlank;

/**
 * @author robin
 */
@Service
@CacheConfig(cacheNames = "setting")
public class SettingService {


    private final Logger logger = LoggerFactory.getLogger(SettingService.class);


    @Autowired
    private SettingMapper mapper;


    @Cacheable(key = "'all'")
    public Setting all() {
        List<PairRecord> list = mapper.findAll();
        Map<String, String> map = new HashMap<>(6);
        for (PairRecord p : list) {
            map.put(p.getName(), p.getValue());
        }

        Setting setting = new Setting();
        setting.setName(map.get("name"));
        setting.setDescription(map.get("description"));
        setting.setKeywords(map.get("keywords"));
        setting.setNotice(map.get("notice"));
        setting.setLogo(map.get("logo"));
        setting.setBackgroundImg(map.get("backgroundImg"));

        return setting;
    }


    @CacheEvict(key = "'all'")
    public AlertMessage maintain(String name,
                                 String desc,
                                 String keywords,
                                 String notice,
                                 MultipartFile logo,
                                 MultipartFile bgImg) {
        try {
            Args.checkNotBlank(name, "网站名称不能为空");

            mapper.replace("name", name);
            mapper.replace("description", desc);
            mapper.replace("keywords", keywords);
            mapper.replace("notice", notice);

            String logoUrl = UploadKit.upload(logo);
            if (null != logo && isNotBlank(logoUrl)) {
                mapper.replace("logo", logoUrl);
            }

            String bgImgUrl = UploadKit.upload(bgImg);
            if (null != bgImg && isNotBlank(bgImgUrl)) {
                mapper.replace("backgroundImg", bgImgUrl);
            }
        } catch (CalfArgsException cae) {
            return new AlertMessage(AlertType.WARNING, cae.getMessage());
        } catch (Exception e) {
            logger.error("Maintaining settings error.", e);
            return new AlertMessage(AlertType.WARNING, "修改网站设置出现问题.");
        }

        return new AlertMessage(AlertType.SUCCESS, "修改网站设置成功.");
    }


}
