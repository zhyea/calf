package org.chobit.calf.model;

import static org.chobit.calf.model.Page.Direct.desc;


/**
 * 分页查询项
 */
public class Page {

    private String search;

    private int offset = 0;

    private int limit = 0;

    private String sort = "id";

    private Direct order = desc;


    public Page() {
    }

    public Page(int limit) {
        this.limit = limit;
    }

    public Page(int offset, int limit) {
        this.offset = offset;
        this.limit = limit;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public Direct getOrder() {
        return order;
    }

    public void setOrder(Direct order) {
        this.order = order;
    }

    public enum Direct {

        asc,

        desc,
        ;
    }
}
