package com.kochetkov.investor;

import com.kochetkov.investor.command.stock.AddStockCommand;
import com.kochetkov.investor.command.user.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.FixedHostPortGenericContainer;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SuppressWarnings({"deprecation", "unused"})
@Testcontainers
public class StockExchangeTest {
    @Container
    private final GenericContainer<?> __ = new FixedHostPortGenericContainer<>("docker.io/library/stonks:0.1.0-SNAPSHOT")
            .withFixedExposedPort(8080, 8080)
            .withExposedPorts(8080);

    @Test
    public void haveToRegister() {
        var login = "login";
        var user = new RegisterUserCommand(login).execute();
        Assertions.assertEquals(login, user.getLogin());
    }

    @Test
    public void registerTwiceDenied() {
        var login = "login";
        var command = new RegisterUserCommand(login);
        command.execute();
        try {
            command.execute();
        } catch (Exception ex) {
            return;
        }
        Assertions.fail();
    }

    @Test
    public void haveToAuthorize() {
        var login = "login";
        var user = new RegisterUserCommand(login).execute();
        var authorizedUser = new AuthorizeCommand(login).execute();
        Assertions.assertEquals(user.getLogin(), authorizedUser.getLogin());
    }

    @Test
    public void haveNotAuthorizeIfNotExists() {
        var login = "login";
        var command = new AuthorizeCommand(login);
        try {
            command.execute();
        } catch (Exception ex) {
            return;
        }
        Assertions.fail();
    }

    @Test
    public void haveToDeposit() {
        var login = "login";
        var user = new RegisterUserCommand(login).execute();
        Assertions.assertEquals(0L, user.getMoney());
        var success = new DepositCommand(login, 200L).execute();
        user = new AuthorizeCommand(login).execute();
        Assertions.assertEquals(true, success);
        Assertions.assertEquals(200L, user.getMoney());
    }

    @Test
    public void haveNotDepositNotRegistered() {
        var login = "login";
        var command = new DepositCommand(login, 200L);
        try {
            command.execute();
        } catch (Exception ex) {
            return;
        }
        Assertions.fail();
    }

    @Test
    public void haveNotDepositInvalidValue() {
        var login = "login";
        var user = new RegisterUserCommand(login).execute();
        Assertions.assertEquals(0L, user.getMoney());
        var command = new DepositCommand(login, -200L);
        try {
            command.execute();
        } catch (Exception ex) {
            return;
        }
        Assertions.fail();
    }

    @Test
    public void haveNotDepositMoneyOverflow() {
        var login = "login";
        var user = new RegisterUserCommand(login).execute();
        Assertions.assertEquals(0L, user.getMoney());
        var command = new DepositCommand(login, 50001L);
        try {
            command.execute();
        } catch (Exception ex) {
            return;
        }
        Assertions.fail();
    }

    @Test
    public void haveToAddStock() {
        var company = "company";
        var stock = new AddStockCommand(company, 1L, 1L).execute();
        Assertions.assertEquals(company, stock.getCompany());
    }

    @Test
    public void haveNotAddStockTwice() {
        var company = "company";
        var command = new AddStockCommand(company, 1L, 1L);
        command.execute();
        try {
            command.execute();
        } catch (Exception ex) {
            return;
        }
        Assertions.fail();
    }

    @Test
    public void haveNotAddStockInvalidPrice() {
        var company = "company";
        var command = new AddStockCommand(company, -1L, 1L);
        try {
            command.execute();
        } catch (Exception ex) {
            return;
        }
        Assertions.fail();
    }

    @Test
    public void haveNotAddStockInvalidCount() {
        var company = "company";
        var command = new AddStockCommand(company, 1L, -1L);
        try {
            command.execute();
        } catch (Exception ex) {
            return;
        }
        Assertions.fail();
    }

    @Test
    public void haveNotBuyStockNotRegistered() {
        var company = "company";
        var login = "login";
        var stock = new AddStockCommand(company, 1L, 1L).execute();
        var command = new BuyStocksCommand(login, company, 1L);
        try {
            command.execute();
        } catch (Exception ex) {
            return;
        }
        Assertions.fail();
    }

    @Test
    public void haveNotBuyStockIfNotExists() {
        var company = "company";
        var login = "login";
        var command = new BuyStocksCommand(login, company, 1L);
        try {
            command.execute();
        } catch (Exception ex) {
            return;
        }
        Assertions.fail();
    }

    @Test
    public void haveNotBuyStockInsufficientFunds() {
        var company = "company";
        var login = "login";
        var stock = new AddStockCommand(company, 1L, 1L).execute();
        var user = new RegisterUserCommand(login).execute();
        var command = new BuyStocksCommand(login, company, 1L);
        try {
            command.execute();
        } catch (Exception ex) {
            return;
        }
        Assertions.fail();
    }

    @Test
    public void haveNotBuyStockNotEnoughStock() {
        var company = "company";
        var login = "login";
        var stock = new AddStockCommand(company, 1L, 1L).execute();
        var user = new RegisterUserCommand(login).execute();
        var success = new DepositCommand(login, 1L).execute();
        Assertions.assertEquals(true, success);
        var command = new BuyStocksCommand(login, company, 2L);
        try {
            command.execute();
        } catch (Exception ex) {
            return;
        }
        Assertions.fail();
    }

    @Test
    public void haveNotSellStockNotRegistered() {
        var company = "company";
        var login = "login";
        var stock = new AddStockCommand(company, 1L, 1L).execute();
        var command = new SellStocksCommand(login, company, 1L);
        try {
            command.execute();
        } catch (Exception ex) {
            return;
        }
        Assertions.fail();
    }

    @Test
    public void haveNotSellStockIfNotExists() {
        var company = "company";
        var login = "login";
        var command = new SellStocksCommand(login, company, 1L);
        try {
            command.execute();
        } catch (Exception ex) {
            return;
        }
        Assertions.fail();
    }

    @Test
    public void haveNotSellStockNotEnoughStock() {
        var company = "company";
        var login = "login";
        var stock = new AddStockCommand(company, 1L, 1L).execute();
        var user = new RegisterUserCommand(login).execute();
        var command = new SellStocksCommand(login, company, 1L);
        try {
            command.execute();
        } catch (Exception ex) {
            return;
        }
        Assertions.fail();
    }
}
