package org.acme.quarkus.portfolio.persistence.repository;



import org.acme.quarkus.portfolio.business.model.Portfolio;
import org.acme.quarkus.portfolio.business.model.PortfolioKey;
import org.acme.quarkus.portfolio.business.model.enums.Devise;
import org.acme.quarkus.portfolio.business.repository.RepositoryPort;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class SqlRepositoryAdapter implements RepositoryPort {

    private static final String COUNT_QUERY = "SELECT count(ID) FROM PORTFOLIO";
    private static final String SELECT_PORTFOLIO_QUERY = "SELECT CODE,AMOUNT,DEVISE,MANAGER FROM PORTFOLIO WHERE CODE ='%s'";
    private static final String SELECT_PORTFOLIOS_QUERY = "SELECT CODE,AMOUNT,DEVISE,MANAGER FROM PORTFOLIO ORDER BY id ASC LIMIT %d,%d";
    private static final String INSERT_QUERY = "INSERT INTO PORTFOLIO (CODE,AMOUNT,DEVISE,MANAGER) VALUES ('%s','%d', '%s', '%s')";
    private static final String UPDATE_QUERY = "UPDATE PORTFOLIO SET %s WHERE CODE='%s'";
    private static final String DELETE_QUERY = "DELETE FROM PORTFOLIO WHERE CODE='%s'";

    @Resource(lookup = "java:jboss/datasources/PortfolioDS")
    private DataSource ds;

    public Optional<Portfolio> getPortfolio(final PortfolioKey key) {

        Optional<Portfolio> portfolio =Optional.empty();
        final String selectQuery = String.format(SELECT_PORTFOLIO_QUERY, key.getCode());
        final List<Portfolio> portfolios = getPortfolios(selectQuery);
        if(!portfolios.isEmpty()){
            portfolio = Optional.ofNullable(portfolios.get(0));
        }
        return portfolio;
    }


    public List<Portfolio> getPortfolios(int offset, int totalReturnedValue) {
        final String selectQuery = String.format(SELECT_PORTFOLIOS_QUERY, offset, totalReturnedValue);
        return getPortfolios(selectQuery);
    }

    private List<Portfolio> getPortfolios(final String query){
        final List<Portfolio> portfolios = new ArrayList<>();
        System.out.println(query);
        try (final Connection connection = ds.getConnection();
             final Statement statement = connection.createStatement();
             final ResultSet resultSet = statement.executeQuery(query);
        ) {
            while (resultSet.next()) {
                portfolios.add(
                        Portfolio.builder()
                                .setKey(new PortfolioKey(resultSet.getString("CODE")))
                                .setAmount(resultSet.getInt("AMOUNT"))
                                .setDevise(Devise.valueOf(resultSet.getString("DEVISE")))
                                .setManager(resultSet.getString("MANAGER")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return portfolios;
    }

    public boolean insertPortfolio(final Portfolio portfolio) {
        Objects.requireNonNull(portfolio);
        final String insertQuery = String.format(INSERT_QUERY, portfolio.getKey().getCode(), portfolio.getAmount(), portfolio.getDevise(), portfolio.getManager());
        return processSimpleQuery(insertQuery);
    }

    public boolean updatePortfolio(final Portfolio portfolio) {
        Objects.requireNonNull(portfolio);
        Objects.requireNonNull(portfolio.getKey());
        boolean manage = false;
        final String setStatement = computeSetStatement(portfolio);
        if (!"".equals(setStatement)) {
            final String update = String.format(UPDATE_QUERY, setStatement, portfolio.getKey().getCode());
            manage = processSimpleQuery(update);
        }
        return manage;
    }


    public boolean deletePortfolio(final PortfolioKey key) {
        Objects.requireNonNull(key);
        final String deleteQuery = String.format(DELETE_QUERY, key.getCode());
        return processSimpleQuery(deleteQuery);
    }


    private String computeSetStatement(final Portfolio portfolio) {
        final StringBuilder setFields = new StringBuilder();
        if (Objects.nonNull(portfolio.getAmount())) {
            setFields.append("AMOUNT=").append(portfolio.getAmount()).equals(" ");
        }
        if (Objects.nonNull(portfolio.getDevise())) {
            if (setFields.length() > 0) {
                setFields.append(", ");
            }
            setFields.append("DEVISE='").append(portfolio.getDevise()).equals("' ");
        }
        if (Objects.nonNull(portfolio.getManager()) && !"".equals(portfolio.getManager())) {
            if (setFields.indexOf(",") < setFields.indexOf("=")) {
                setFields.append(", ");
            }
            setFields.append("MANAGER='").append(portfolio.getManager()).append("' ");
        }
        return setFields.toString();
    }

    private boolean processSimpleQuery(final String query) {
        System.out.println(query);
        boolean success = false;
        try (final Connection connection = ds.getConnection();
             final Statement statement = connection.createStatement()
        ) {
            statement.execute(query);
            success = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }


    public int countPorfolio() {
        int total = 0;
        try (final Connection connection = ds.getConnection();
             final Statement statement = connection.createStatement();
             final ResultSet resultSet = statement.executeQuery(COUNT_QUERY);
        ) {
            if (resultSet.next()) {
                total = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }
}
