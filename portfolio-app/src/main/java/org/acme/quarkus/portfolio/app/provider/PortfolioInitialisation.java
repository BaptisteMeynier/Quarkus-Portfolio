package org.acme.quarkus.portfolio.app.provider;


import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import org.acme.quarkus.portfolio.business.repository.RepositoryPort;
import org.acme.quarkus.portfolio.business.repository.spi.RepositoryProvider;
import org.acme.quarkus.portfolio.business.service.PortfolioService;
import org.acme.quarkus.portfolio.business.service.PortfolioServicePort;
//import org.acme.quarkus.portfolio.persistence.repository.SqlRepositoryAdapter;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.acme.quarkus.portfolio.persistence.repository.SqlRepositoryAdapter;
import org.jboss.logging.Logger;

import java.nio.file.ProviderNotFoundException;
import java.util.Iterator;
import java.util.ServiceLoader;

@ApplicationScoped
public class PortfolioInitialisation {

    private static final Logger LOGGER = Logger.getLogger(PortfolioInitialisation.class);

    @Inject
    RepositoryPort repositoryPort;

    @Produces
    public PortfolioServicePort getPortfolioService() {
        return  new PortfolioService(repositoryPort);
    }

}
