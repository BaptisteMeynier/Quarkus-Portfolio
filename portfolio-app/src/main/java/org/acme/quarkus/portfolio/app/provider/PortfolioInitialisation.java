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

import org.jboss.logging.Logger;

import java.nio.file.ProviderNotFoundException;
import java.util.Iterator;
import java.util.ServiceLoader;

@ApplicationScoped
public class PortfolioInitialisation {

    private static final Logger LOGGER = Logger.getLogger(PortfolioInitialisation.class);
    private static final String DEFAULT_PERSISTENCE_PROVIDER = "org.acme.quarkus.portfolio.persistence.repository.SqlRepositoryAdapter";



    void onStart(@Observes StartupEvent ev) {
        LOGGER.info("The application is starting...{}");
    }

    void onStop(@Observes ShutdownEvent ev) {
        LOGGER.info("The application is stopping... {}");
    }


  //  private RepositoryPort repositoryPort = defaultRepositoryProvider();

  /*  @Produces
    public PortfolioServicePort getPortfolioService() {
        return  new PortfolioService(null);
       // return new PortfolioService(repositoryPort);
    }
*/

  /*  public RepositoryPort defaultRepositoryProvider() {
        return provider(DEFAULT_PERSISTENCE_PROVIDER).create();
    }

    public RepositoryProvider provider(String providerName) {
        LOGGER.info("1)" + providerName);
        ServiceLoader<RepositoryProvider> loader = ServiceLoader.load(RepositoryProvider.class);
        LOGGER.info("2)" + loader);
        Iterator<RepositoryProvider> it = loader.iterator();
        LOGGER.info("3)" + loader);
        while (it.hasNext()) {
            LOGGER.info("4)");
            RepositoryProvider provider = it.next();
            LOGGER.info("5)");
            if (providerName.equals(provider.getClass().getName())) {
                LOGGER.info("6)");
                return provider;
            }
            LOGGER.info("7)");
        }
        throw new ProviderNotFoundException(String.format("Exchange Rate provider %s not found",providerName));
    }*/

}
