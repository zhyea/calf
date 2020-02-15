package org.chobit.calf.service;

import org.chobit.calf.model.Page;
import org.chobit.calf.model.PageResult;
import org.chobit.calf.model.Pair;
import org.chobit.calf.service.entity.Author;
import org.chobit.calf.service.mapper.AuthorMapper;
import org.chobit.calf.service.mapper.WorkMapper;
import org.chobit.calf.tools.LowerCaseKeyMap;
import org.chobit.calf.utils.Args;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.chobit.calf.utils.Collections2.pairToMap;
import static org.chobit.calf.utils.Strings.isBlank;

/**
 * @author robin
 */
@Service
public class AuthorService {


    private static final Logger logger = LoggerFactory.getLogger(AuthorService.class);

    private static final int DEFAULT_AUTHOR_ID = 1;

    @Autowired
    private AuthorMapper authorMapper;
    @Autowired
    private WorkMapper workMapper;
    @Autowired
    private AssembleQueryService assembleQueryService;


    public void maintain(int id, String name, String country, String bio) {
        Args.checkNotBlank(name, "作者姓名不能为空");
        Args.checkNotBlank(country, "作者国籍不能为空");

        Author author = authorMapper.getByNameAndCountry(id, name, country);
        Args.check(null == author, "用户信息已存在");

        author = new Author(name, country);
        author.setId(id);
        author.setBio(bio);

        if (id > 0) {
            authorMapper.update(author);
        } else {
            authorMapper.insert(author);
        }
    }


    public PageResult<LowerCaseKeyMap> queryInPage(Page page) {
        Args.checkPage(page);
        PageResult<LowerCaseKeyMap> result = assembleQueryService.findInPage("author", page,
                Arrays.asList("name", "country", "bio"), "id", "name", "country", "bio");

        List<Integer> ids = result.getRows().stream().map(e -> e.getInt("id")).distinct().collect(Collectors.toList());
        List<Pair<Integer, Long>> authorWork = workMapper.countWithAuthor(ids);
        Map<Integer, Long> map = pairToMap(authorWork);

        result.getRows().forEach(e -> {
            Long count = map.getOrDefault(e.getInt("id"), 0L);
            e.put("workCount", count);
        });
        return result;
    }


    public Author get(int id) {
        if (id <= 0) {
            return null;
        }
        return authorMapper.get(id);
    }


    public boolean delete(int id) {
        if (id <= DEFAULT_AUTHOR_ID) {
            return true;
        }
        workMapper.changeAuthor(id, DEFAULT_AUTHOR_ID);
        return authorMapper.delete(id);
    }


    public Pair<String, Object> findSuggest(String key) {
        key = isBlank(key) ? "" : key;
        Object value = authorMapper.findByKeyword("%" + key + "%");
        return new Pair<>(key, value);
    }
}
