package com.qijun.demo.response;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * DataTahle分页返回信息
 *
 * @author Qijun
 * @version 1.0
 * @date 5/3/2019 08:59
 */
public class DataTablePage<T> {

    //data 与datatales 加载的“dataSrc"对应
    private List<T> data;

    //记录总数
    private long recordsTotal;

    //筛选数目
    private long recordsFiltered;

    //页码
    private int draw;

    public DataTablePage(Page<T> info, int draw){
        this.draw = draw;
        this.recordsTotal = info.getTotal();
        this.recordsFiltered = info.getTotal();
        this.data = info.getResult();
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public long getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(long recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public long getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(long recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }
}
