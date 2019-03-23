package com.qijun.demo.response;

import java.util.List;

/**
 * 通用的分页返回结果
 *
 * @author Qijun
 * @version 1.0
 * @date 12/21/18 4:11 PM
 */
public class PageResult {

    /**
     * 本页开始索引
     */
    private int index;

    /**
     * 当前页码
     */
    private int pageIndex;

    /**
     * 页面大小
     */
    private int pageSize;

    /**
     * 总数据数
     */
    private long totalCount;

    /**
     * 页面总数
     */
    private long pageCount;

    /**
     * 是否有下一页
     */
    private boolean more;

    /**
     * 对象列表
     */
    private List items;


    public PageResult(int pageIndex, int pageSize, long totalCount, List items){
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        this.pageCount = (totalCount + pageSize -1)/this.pageSize;
        this.index = (pageIndex -1)*this.pageSize +1;
        this.more = this.pageIndex >= pageCount;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public long getPageCount() {
        return pageCount;
    }

    public void setPageCount(long pageCount) {
        this.pageCount = pageCount;
    }

    public boolean isMore() {
        return more;
    }

    public void setMore(boolean more) {
        this.more = more;
    }

    public List getItems() {
        return items;
    }

    public void setItems(List items) {
        this.items = items;
    }
}
