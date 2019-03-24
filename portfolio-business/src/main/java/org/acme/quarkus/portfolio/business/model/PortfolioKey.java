package org.acme.quarkus.portfolio.business.model;

import java.util.Objects;

public class PortfolioKey {
    private final String code;

    public PortfolioKey(String codePortfolio) {
        this.code = codePortfolio;
    }

    public String getCode() {
        return code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PortfolioKey that = (PortfolioKey) o;
        return Objects.equals(code, that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}
