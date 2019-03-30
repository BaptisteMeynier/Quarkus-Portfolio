package org.acme.quarkus.portfolio.business.repository.spi;

import org.acme.quarkus.portfolio.business.repository.RepositoryPort;

public interface RepositoryProvider {
    RepositoryPort create();
}
