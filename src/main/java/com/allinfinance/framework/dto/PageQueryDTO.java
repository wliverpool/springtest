package com.allinfinance.framework.dto;

import java.io.Serializable;

/**
 * 分页用的DTO
 *
 * @author jianji.dai
 *
 */
public class PageQueryDTO extends BaseDTO implements Serializable {

    private static final long serialVersionUID = 324244354465809279L;

    public static final String COUNT_ALL_NAME = "TOTAL_RESULT";

    // 每页行数
    private int pageSize = 10;

    // 当前页数
    protected int currentPage=1;

    // 当前一页显示的行数
    protected int rowsDisplayed=50;

    // 排序字段得名称
    protected String sortFieldName;

    // 排序字段的别名
    protected String sortFieldAliasName;

    // 排序的方式
    protected String sort;

    /**
     * 是否查询总记录
     */
    private boolean doCount = true;
    /**
     * 是否查询所有记录
     */
    private boolean queryAll = false;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public boolean isDoCount() {
        return doCount;
    }

    public void setDoCount(boolean doCount) {
        this.doCount = doCount;
    }

    public boolean isQueryAll() {
        return queryAll;
    }

    public void setQueryAll(boolean queryAll) {
        this.queryAll = queryAll;
    }

    public int getRowsDisplayed() {
        return rowsDisplayed;
    }

    public void setRowsDisplayed(int rowsDisplayed) {
        this.rowsDisplayed = rowsDisplayed;
    }

    public String getSortFieldName() {
        return sortFieldName;
    }

    public void setSortFieldName(String sortFieldName) {
        this.sortFieldName = sortFieldName;
    }

    public String getSortFieldAliasName() {
        return sortFieldAliasName;
    }

    public void setSortFieldAliasName(String sortFieldAliasName) {
        this.sortFieldAliasName = sortFieldAliasName;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public int getFirstCursorPosition() {
        return (currentPage - 1) * rowsDisplayed + 1;
    }

    public int getLastCursorPosition() {
        return (currentPage) * rowsDisplayed;
    }
}
