package org.acme.quarkus.portfolio.webservice.rest.param;


import org.acme.quarkus.portfolio.business.model.enums.Devise;
import org.acme.quarkus.portfolio.webservice.rest.param.validation.UpdatePortfolio;

import javax.validation.constraints.*;


public class PortfolioParam {

    @NotEmpty(groups = UpdatePortfolio.class)
    @Size(min=10,max=10,groups = UpdatePortfolio.class)
    private String code;
    @Positive()
    private int amount;
    @NotNull
    private Devise devise;
    @NotEmpty
    private String manager;

    public PortfolioParam() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Devise getDevise() {
        return devise;
    }

    public void setDevise(Devise devise) {
        this.devise = devise;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }
}
