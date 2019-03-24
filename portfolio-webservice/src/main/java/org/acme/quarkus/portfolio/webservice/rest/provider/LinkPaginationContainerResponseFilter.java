package org.acme.quarkus.portfolio.webservice.rest.provider;

import com.softeam.presentation.kubernetest.portfolio.webservice.rest.pagination.LinkBuilder;
import com.softeam.presentation.kubernetest.portfolio.webservice.rest.pagination.Page;

import javax.ws.rs.container.ContainerResponseFilter;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.UriInfo;
import java.util.stream.Collectors;
import java.util.stream.Stream;


import static com.softeam.presentation.kubernetest.portfolio.webservice.rest.pagination.PaginationConstants.X_PAGE_COUNT;
import static com.softeam.presentation.kubernetest.portfolio.webservice.rest.pagination.PaginationConstants.X_TOTAL_COUNT;
import static javax.ws.rs.core.HttpHeaders.LINK;

public class LinkPaginationContainerResponseFilter implements ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext requestContext,
                       ContainerResponseContext responseContext) {
        if (!(responseContext.getEntity() instanceof Page) ) {
            return;
        }

        UriInfo uriInfo = requestContext.getUriInfo();
        Page page = (Page) responseContext.getEntity();
        responseContext.setEntity(page.getEntities());

        Stream<Link> build = LinkBuilder
                .uriInfo(uriInfo)
                .pageSize(page.getPerPage())
                .pageNumber(page.getCurrentPage())
                .totalEntityCount(page.getTotalCount())
                .totalPageCount(page.getPageCount())
                .build();
        responseContext.getHeaders().addAll(LINK,build.collect(Collectors.toList()));
        responseContext.getHeaders().add(X_TOTAL_COUNT, page.getTotalCount());
        responseContext.getHeaders().add(X_PAGE_COUNT, page.getPageCount());
    }
}
