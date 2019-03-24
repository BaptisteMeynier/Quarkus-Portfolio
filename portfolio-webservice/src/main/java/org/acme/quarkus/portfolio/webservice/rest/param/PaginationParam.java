package org.acme.quarkus.portfolio.webservice.rest.param;


import org.acme.quarkus.portfolio.webservice.rest.pagination.PaginationConstants;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;

public class PaginationParam {

    @Min(value = 1, message = "page start at " + PaginationConstants.FIRST_PAGE)
    @QueryParam(PaginationConstants.PAGE_QUERY_PARAM)
    @DefaultValue(PaginationConstants.FIRST_PAGE)
    public int page;

    @Max(value = 1, message = "per_page max is " + PaginationConstants.PER_PAGE_QUERY_PARAM)
    @QueryParam(PaginationConstants.PER_PAGE_QUERY_PARAM)
    @DefaultValue(PaginationConstants.DEFAULT_PER_PAGE)
    public int per_page;
}
