package org.acme.quarkus.portfolio.webservice.rest.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;
import org.acme.quarkus.portfolio.business.model.enums.Devise;

@RegisterForReflection
public class PortfolioDTO {
    String code;
    int amount;
    Devise devise;
    String manager;

    public PortfolioDTO() {
    }

    public PortfolioDTO(String code, int amount, Devise devise, String manager) {
        this.code = code;
        this.amount = amount;
        this.devise = devise;
        this.manager = manager;
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
