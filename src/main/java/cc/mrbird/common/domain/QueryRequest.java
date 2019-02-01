package cc.mrbird.common.domain;

import com.google.common.base.MoreObjects;

import javax.persistence.Transient;
import java.io.Serializable;

public class QueryRequest implements Serializable {

    private static final long serialVersionUID = -4869594085374385813L;
    @Transient
    private int pageSize;
    @Transient
    private int pageNum;
    @Transient
    private String sort;
    @Transient
    private String sortOrder;
    @Transient
    private String addBeginTime;
    @Transient
    private String addEndTime;
    @Transient
    private String updateBeginTime;
    @Transient
    private String updateEndTime;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getAddBeginTime() {
        return addBeginTime;
    }

    public void setAddBeginTime(String addBeginTime) {
        this.addBeginTime = addBeginTime;
    }

    public String getAddEndTime() {
        return addEndTime;
    }

    public void setAddEndTime(String addEndTime) {
        this.addEndTime = addEndTime;
    }

    public String getUpdateBeginTime() {
        return updateBeginTime;
    }

    public void setUpdateBeginTime(String updateBeginTime) {
        this.updateBeginTime = updateBeginTime;
    }

    public String getUpdateEndTime() {
        return updateEndTime;
    }

    public void setUpdateEndTime(String updateEndTime) {
        this.updateEndTime = updateEndTime;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("pageSize", pageSize)
                .add("pageNum", pageNum)
                .add("sort", sort)
                .add("sortOrder", sortOrder)
                .add("addBeginTime", addBeginTime)
                .add("addEndTime", addEndTime)
                .add("updateBeginTime", updateBeginTime)
                .add("updateEndTime", updateEndTime)
                .toString();
    }


}
