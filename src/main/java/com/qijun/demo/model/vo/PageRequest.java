package com.qijun.demo.model.vo;

import javax.validation.constraints.NotNull;

/**
 * 后台分页传入的参数
 *
 * @author Qijun
 * @version 1.0
 * @date 5/3/2019 09:59
 */
public class PageRequest {

    @NotNull(message = "Page draw cannot be null")
    private Integer draw;

    @NotNull(message = "Page index cannot be null")
    private Integer pageIndex;

    @NotNull(message = "Page size cannot be null")
    private Integer pageSize;

    public Integer getDraw() {
        return draw;
    }

    public void setDraw(Integer draw) {
        this.draw = draw;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
