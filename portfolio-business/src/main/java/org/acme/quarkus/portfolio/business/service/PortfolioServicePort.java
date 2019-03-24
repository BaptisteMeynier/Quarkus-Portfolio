package org.acme.quarkus.portfolio.business.service;


import org.acme.quarkus.portfolio.business.model.Portfolio;
import org.acme.quarkus.portfolio.business.model.PortfolioKey;

import java.util.List;
import java.util.Optional;

public interface PortfolioServicePort {

    List<Portfolio> getPortfolios(int offset, int totalReturnedValue);

    Optional<Portfolio> getPortfolio(final PortfolioKey key);

    boolean insertPortfolio(final Portfolio portfolio);

    boolean updatePortfolio(final Portfolio portfolio);

    boolean deletePortfolio(final PortfolioKey key);

    int countPortfolio();
}
