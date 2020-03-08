package com.es.core.model.phone;

import java.util.List;

public class ProductPage {

    private List<Phone> phones;
    private String orderBy;
    private String orderDir;
    private String query;
    private Integer totalNumOfPages;
    private Integer currentPage;


    public ProductPage() {
    }

    public ProductPage(String orderBy, String orderDir, String query, Integer currentPage) {
        this.orderBy = orderBy;
        this.orderDir = orderDir;
        this.query = query;
        this.currentPage = currentPage;
    }

    public ProductPage(List<Phone> phones, String orderBy, String orderDir, String query, Integer totalNumOfPages, Integer currentPage) {
        this.phones = phones;
        this.orderBy = orderBy;
        this.orderDir = orderDir;
        this.query = query;
        this.totalNumOfPages = totalNumOfPages;
        this.currentPage = currentPage;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getOrderDir() {
        return orderDir;
    }

    public void setOrderDir(String orderDir) {
        this.orderDir = orderDir;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public Integer getTotalNumOfPages() {
        return totalNumOfPages;
    }

    public void setTotalNumOfPages(Integer totalNumOfPages) {
        this.totalNumOfPages = totalNumOfPages;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }
}
