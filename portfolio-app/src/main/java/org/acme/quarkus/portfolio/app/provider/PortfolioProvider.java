package org.acme.quarkus.portfolio.app.provider;


import org.acme.quarkus.portfolio.business.repository.RepositoryPort;
import org.acme.quarkus.portfolio.business.service.PortfolioService;
import org.acme.quarkus.portfolio.business.service.PortfolioServicePort;
import org.acme.quarkus.portfolio.persistence.repository.SqlRepositoryAdapter;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

@ApplicationScoped
public class PortfolioProvider {


    private RepositoryPort repositoryPort = new SqlRepositoryAdapter();

    @Produces
    public PortfolioServicePort getPortfolioService(){
        return new PortfolioService(repositoryPort);
    }


}
