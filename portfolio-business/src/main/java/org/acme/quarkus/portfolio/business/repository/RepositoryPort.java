package org.acme.quarkus.portfolio.business.repository;


import org.acme.quarkus.portfolio.business.model.Portfolio;
import org.acme.quarkus.portfolio.business.model.PortfolioKey;

import java.util.*;

public interface RepositoryPort {

    Optional<Portfolio> getPortfolio(final PortfolioKey key) ;

    List<Portfolio> getPortfolios(int offset, int totalReturnedValue);

    boolean insertPortfolio(final Portfolio portfolio) ;

    boolean updatePortfolio(final Portfolio portfolio);

    boolean deletePortfolio(final PortfolioKey key);

    int countPorfolio();
}
