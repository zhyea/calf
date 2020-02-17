package org.chobit.calf.service.mapper;

import org.apache.ibatis.annotations.*;
import org.chobit.calf.model.Page;
import org.chobit.calf.model.Pair;
import org.chobit.calf.model.WorkModel;
import org.chobit.calf.service.entity.Work;

import java.util.Collection;
import java.util.List;

/**
 * @author robin
 */
@Mapper
public interface WorkMapper {

    @Insert({"insert into work (author_id, category_id, cover, file, name, brief)",
            "values",
            "(#{authorId}, #{categoryId}, #{cover}, #{file}, #{name}, #{brief})"})
    int insert(Work work);


    @Update({"update work set author_id=#{authorId}, category_id=#{categoryId}, cover=#{cover}, file=#{file}, name=#{name}, brief=#{brief}",
            "where id=#{id}"})
    boolean update(Work work);


    @Select("select * from work where id=#{id}")
    Work get(@Param("id") int id);


    @Update("update work set author_id=#{newAuthor} where author_id=#{oldAuthor}")
    boolean changeAuthor(@Param("oldAuthor") int oldAuthor,
                         @Param("newAuthor") int newAuthor);


    @Select({"select w.id, w.name, a.name as author, m.name as cat ",
            "from work w left join author a on w.author_id=a.id left join meta m on w.category_id=m.id",
            "where w.name like #{p.search} or brief like #{p.search} or a.name like #{p.search} or m.name like #{p.search}",
            "order by ${p.sort} ${p.order} limit ${p.offset}, ${p.limit}"})
    List<WorkModel> findInPage(@Param("p") Page p);


    @Select({"select count(w.id)",
            "from work w left join author a on w.author_id=a.id left join meta m on w.category_id=m.id",
            "where w.name like #{search} or brief like #{search} or a.name like #{search} or m.name like #{search}"
    })
    long countForSearch(@Param("search") String search);


    @Select({"select w.id, w.name, a.name as author, m.name as cat ",
            "from work w left join author a on w.author_id=a.id left join meta m on w.category_id=m.id",
            "where w.name like #{key} or brief like #{key} or a.name like #{key} or m.name like #{key}"})
    List<WorkModel> findWithKeywords(@Param("key") String key);


    @Select({"select w.id, w.name, w.brief, a.name as author, m.name as cat ",
            "from work w left join author a on w.author_id=a.id left join meta m on w.category_id=m.id",
            "where w.author_id=#{author}",
            "order by ${p.sort} ${p.order} limit ${p.offset}, ${p.limit}"})
    List<WorkModel> findWithAuthor(@Param("p") Page p, @Param("author") int authorId);


    @Select({"select count(w.id)",
            "from work w",
            "where w.author_id=#{author}"
    })
    long countWithAuthor(@Param("author") int authorId);


    @Select({"select w.id, w.name, w.brief, a.name as author, m.name as cat ",
            "from work w left join author a on w.author_id=a.id left join meta m on w.category_id=m.id",
            "where w.category_id=#{cat}",
            "order by ${p.sort} ${p.order} limit ${p.offset}, ${p.limit}"})
    List<WorkModel> findWithCategory(@Param("p") Page p, @Param("cat") int catId);


    @Select({"select count(w.id)",
            "from work w",
            "where w.category_id=#{cat}"
    })
    long countWithCategory(@Param("cat") int catId);


    @Select({"<script>",
            "select author_id as `key`, count(id) as `value` from work where author_id in",
            "<foreach collection='ids' item='item' separator=',' open='(' close=')'>",
            "#{item}",
            "</foreach>",
            "group by author_id",
            "</script>"})
    List<Pair<Integer, Long>> countWithAuthorIds(@Param("ids") Collection<Integer> ids);


    @Delete({"<script>",
            "delete from work where id in",
            "<foreach collection='ids' item='item' separator=',' open='(' close=')'>",
            "#{item}",
            "</foreach>",
            "</script>"})
    int deleteByIds(@Param("ids") Iterable<Integer> ids);

}
