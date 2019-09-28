package com.fredrischter.minimalist.service;

import com.fredrischter.minimalist.model.Account;
import com.fredrischter.minimalist.repository.AccountRepository;
import com.fredrischter.minimalist.service.exceptions.AccountNotFoundException;
import com.fredrischter.minimalist.service.exceptions.NotEnoughBalanceException;

import java.math.BigDecimal;
import java.util.Optional;

public class MoneyTransferService {

    AccountRepository transferRepository = new AccountRepository();

    public BigDecimal getBalance(String accountNumber) throws AccountNotFoundException {
        Optional<Account> accountEntityOp = transferRepository.findByNumber(accountNumber);
        if (!accountEntityOp.isPresent()) {
            throw new AccountNotFoundException();
        }
        return accountEntityOp.get().getBalance();
    }

    public void transfer(String originAccountNumber, String destinationAccountNumber, BigDecimal amount) throws AccountNotFoundException, NotEnoughBalanceException {
        Optional<Account> originAccountOp = transferRepository.findByNumber(originAccountNumber);
        Optional<Account> destinationAccountOp = transferRepository.findByNumber(destinationAccountNumber);

        if (!originAccountOp.isPresent() || !destinationAccountOp.isPresent()) {
            throw new AccountNotFoundException();
        }
        Account originAccount = originAccountOp.get();
        Account destinationAccount = destinationAccountOp.get();

        if (originAccount.getBalance().compareTo(amount) < 0) {
            throw new NotEnoughBalanceException();
        }

        BigDecimal originAccountBalance = originAccount.getBalance().subtract(amount);
        BigDecimal destinationAccountBalance = destinationAccount.getBalance().add(amount);

        transferRepository.updateBalance(originAccount.getNumber(), originAccountBalance);
        transferRepository.updateBalance(destinationAccount.getNumber(), destinationAccountBalance);
    }

    public void deposit(String accountNumber, BigDecimal amount) {
        Optional<Account> accountEntityOp = transferRepository.findByNumber(accountNumber);

        if (accountEntityOp.isPresent()) {
            Account accountEntity = accountEntityOp.get();
            BigDecimal accountEntityBalance = accountEntity.getBalance().add(amount);
            transferRepository.updateBalance(accountEntity.getNumber(), accountEntityBalance);
        } else {
            Account accountEntity = new Account(accountNumber, amount);
            transferRepository.create(accountEntity);
        }
    }

}
