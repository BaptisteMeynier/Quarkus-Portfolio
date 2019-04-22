package org.acme.quarkus.portfolio.webservice.rest.pagination;


import io.quarkus.runtime.annotations.RegisterForReflection;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name="page")
@XmlAccessorType(XmlAccessType.FIELD)
@RegisterForReflection
public class Page<T> {

    private List<T> entities;
    @XmlElement(required=true)
    private int currentPage;
    @XmlElement(required=true)
    private int perPage;
    @XmlElement(required=true)
    private int pageCount;
    @XmlElement(required=true)
    private int totalCount;

    public Page() {
    }

    public Page(List<T> entities, int currentPage, int perPage, int pageCount, int totalCount) {
        this.entities = entities;
        this.currentPage = currentPage;
        this.perPage = perPage;
        this.pageCount = pageCount;
        this.totalCount = totalCount;
    }

    public List<T> getEntities() {
        return entities;
    }

    public int getPerPage() {
        return perPage;
    }

    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }

    public void setEntities(List<T> entities) {
        this.entities = entities;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
