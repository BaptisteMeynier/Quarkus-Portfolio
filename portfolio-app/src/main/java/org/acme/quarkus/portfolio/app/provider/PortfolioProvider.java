package org.acme.quarkus.portfolio.app.provider;


import io.quarkus.runtime.annotations.RegisterForReflection;
import org.acme.quarkus.portfolio.business.repository.RepositoryPort;
import org.acme.quarkus.portfolio.business.repository.spi.RepositoryProvider;
import org.acme.quarkus.portfolio.business.service.PortfolioService;
import org.acme.quarkus.portfolio.business.service.PortfolioServicePort;
import org.acme.quarkus.portfolio.persistence.repository.SqlRepositoryAdapter;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import java.nio.file.ProviderNotFoundException;
import java.util.Iterator;
import java.util.ServiceLoader;
import org.jboss.logging.Logger;

@ApplicationScoped
@RegisterForReflection
@Named
public class PortfolioProvider {

    private static final Logger LOG = Logger.getLogger(PortfolioProvider.class);
    private static final String DEFAULT_PERSISTENCE_PROVIDER = "org.acme.quarkus.portfolio.persistence.repository.SqlRepositoryAdapter";

    private RepositoryPort repositoryPort = defaultRepositoryProvider();

    @Produces
    public PortfolioServicePort getPortfolioService() {
        return new PortfolioService(repositoryPort);
    }


    public static RepositoryPort defaultRepositoryProvider() {
        return provider(DEFAULT_PERSISTENCE_PROVIDER).create();
    }

    public static RepositoryProvider provider(String providerName) {
        LOG.info("1)" + providerName);
        ServiceLoader<RepositoryProvider> loader = ServiceLoader.load(RepositoryProvider.class);
        LOG.info("2)" + loader);
        Iterator<RepositoryProvider> it = loader.iterator();
        LOG.info("3)" + loader);
        while (it.hasNext()) {
            LOG.info("4)");
            RepositoryProvider provider = it.next();
            LOG.info("5)");
            if (providerName.equals(provider.getClass().getName())) {
                LOG.info("6)");
                return provider;
            }
            LOG.info("7)");
        }
        throw new ProviderNotFoundException(String.format("Exchange Rate provider %s not found",providerName));
    }

}
