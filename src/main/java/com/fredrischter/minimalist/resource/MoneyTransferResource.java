package com.fredrischter.minimalist.resource;

import com.fredrischter.minimalist.service.MoneyTransferService;
import com.fredrischter.minimalist.service.exceptions.AccountNotFoundException;
import com.fredrischter.minimalist.service.exceptions.NotEnoughBalanceException;

import java.math.BigDecimal;

import static spark.Spark.*;

public class MoneyTransferResource {

    MoneyTransferService moneyTransferService = new MoneyTransferService();

    public MoneyTransferResource() {

        get("/transfer", (req, res) -> {
            transfer(req.queryParams("originAccount"), req.queryParams("destinationAccount"), new BigDecimal(req.queryParams("amount")));
            return "";
        });

        get("/balance", (req, res) -> {
            balance(req.queryParams("account"));
            return "";
        });

        get("/deposit", (req, res) -> {
            deposit(req.queryParams("account"), new BigDecimal(req.queryParams("amount")));
            return "";
        });

        exception(NotEnoughBalanceException.class, (exception, request, response) -> {
            response.status(403);
        });

        exception(AccountNotFoundException.class, (exception, request, response) -> {
            response.status(404);
        });
    }

    public void transfer(String originAccount, String destinationAccount, BigDecimal amount) throws AccountNotFoundException, NotEnoughBalanceException {
        moneyTransferService.transfer(originAccount, destinationAccount, amount);
    }

    public BigDecimal balance(String account) throws AccountNotFoundException {
        return moneyTransferService.getBalance(account);
    }

    public void deposit(String accountNumber, BigDecimal amount) {
        moneyTransferService.deposit(accountNumber, amount);
    }
}
