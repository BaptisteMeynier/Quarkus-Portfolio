package org.acme.quarkus.portfolio.persistence.repository;


import org.acme.quarkus.portfolio.business.repository.RepositoryPort;
import org.acme.quarkus.portfolio.business.repository.spi.RepositoryProvider;

public class SqlRepositoryProvider implements RepositoryProvider {
    @Override
    public RepositoryPort create() {
        return new SqlRepositoryAdapter();
    }
}
