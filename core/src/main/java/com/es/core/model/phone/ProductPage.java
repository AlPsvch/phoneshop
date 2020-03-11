package com.es.core.model.phone;

import java.util.List;

public class ProductPage {

    private List<Phone> phones;
    private String sortBy;
    private SortingDirection sortDirection;
    private String query;
    private Integer totalNumOfPages;
    private Integer currentPage;


    public ProductPage() {
    }

    public ProductPage(String sortBy, SortingDirection sortDirection, String query, Integer currentPage) {
        this.sortBy = sortBy;
        this.sortDirection = sortDirection;
        this.query = query;
        this.currentPage = currentPage;
    }

    public ProductPage(List<Phone> phones, String sortBy, SortingDirection sortDirection, String query, Integer totalNumOfPages, Integer currentPage) {
        this.phones = phones;
        this.sortBy = sortBy;
        this.sortDirection = sortDirection;
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

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public SortingDirection getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(SortingDirection sortDirection) {
        this.sortDirection = sortDirection;
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
