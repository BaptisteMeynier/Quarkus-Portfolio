package org.acme.quarkus.portfolio.webservice.rest;

import org.acme.quarkus.portfolio.business.model.Portfolio;
import org.acme.quarkus.portfolio.business.model.PortfolioKey;
import org.acme.quarkus.portfolio.webservice.adapter.PortfolioServiceAdapter;
import org.acme.quarkus.portfolio.webservice.rest.pagination.Page;
import org.acme.quarkus.portfolio.webservice.rest.param.PaginationParam;
import org.acme.quarkus.portfolio.webservice.rest.param.PortfolioParam;
import org.acme.quarkus.portfolio.webservice.rest.param.validation.UpdatePortfolio;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Metered;
import org.eclipse.microprofile.metrics.annotation.Timed;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.groups.ConvertGroup;
import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Path("/portfolio")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
/*@Metered
@Counted(unit = MetricUnits.NONE,
        name = "itemsCheckedOut",
        absolute = true,
        monotonic = true,
        displayName = "Checkout items",
        description = "Metrics to show how many times checkoutItems method was called.",
        tags = {"checkout=items"})
@Timed(name = "itemsProcessed",
        description = "Metrics to monitor the times of get portfolio.",
        unit = MetricUnits.MILLISECONDS,
        absolute = true)*/
public class PortfolioEndpoint {

  //  @Inject
    private PortfolioServiceAdapter portfolioServiceAdapter = new PortfolioServiceAdapter();

    @GET
    public void doGet(
            @BeanParam PaginationParam queryParams,
            @Suspended AsyncResponse asyncResponse
    ) {
        int offset = queryParams.per_page * (queryParams.page - 1);

        final List<Portfolio> portfolios = portfolioServiceAdapter.getPortfolios(offset, queryParams.per_page);
        int total = portfolioServiceAdapter.countPortfolio();

        final Page<Portfolio> paginated =
                new Page<>(
                        portfolios,
                        queryParams.page,
                        queryParams.per_page,
                        total / queryParams.per_page,
                        total);

        asyncResponse.resume(paginated);
    }

    @GET
    @Path("{key}")
    public Portfolio doGet(@PathParam("key") String key) {
        Portfolio res = null;
        final Optional<Portfolio> portfolio = portfolioServiceAdapter.getPortfolio(new PortfolioKey(key));
        if(portfolio.isPresent()){
            res = portfolio.get();
        }
        return res;
    }

    @POST
    public void doPost(
            @Valid PortfolioParam portfolioParam,
            @Suspended AsyncResponse asyncResponse
    ) {
        final Portfolio portfolio =
                Portfolio.builder()
                        .setKey(new PortfolioKey(portfolioParam.getCode()))
                        .setAmount(portfolioParam.getAmount())
                        .setDevise(portfolioParam.getDevise())
                        .setManager(portfolioParam.getManager());
        asyncResponse.resume(portfolioServiceAdapter.insertPortfolio(portfolio));
    }

    @PUT
    public void doPut(
            @Valid @ConvertGroup(to = UpdatePortfolio.class) PortfolioParam portfolioParam,
            @Suspended AsyncResponse asyncResponse
    ) {
        final Portfolio portfolio =
                Portfolio.builder()
                        .setKey(new PortfolioKey(portfolioParam.getCode()))
                        .setAmount(portfolioParam.getAmount())
                        .setDevise(portfolioParam.getDevise())
                        .setManager(portfolioParam.getManager());
        asyncResponse.resume(portfolioServiceAdapter.updatePortfolio(portfolio));
    }

    @DELETE
    @Path("{key}")
    public void doDelete(
            @PathParam("key") String key,
            @Suspended AsyncResponse asyncResponse
    ) {
        asyncResponse.resume(portfolioServiceAdapter.deletePortfolio(new PortfolioKey(key)));
    }
}
