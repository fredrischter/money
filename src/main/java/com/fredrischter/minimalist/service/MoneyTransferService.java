package com.fredrischter.minimalist.service;

import com.fredrischter.minimalist.model.Account;
import com.fredrischter.minimalist.repository.AccountRepository;
import com.fredrischter.minimalist.service.exceptions.AccountNotFoundException;
import com.fredrischter.minimalist.service.exceptions.NotEnoughBalanceException;

import java.math.BigDecimal;
import java.util.Optional;

public class MoneyTransferService {

    AccountRepository transferRepository = new AccountRepository();

    public BigDecimal getBalance(String account) throws AccountNotFoundException {
        Optional<Account> accountEntityOp = transferRepository.findById(account);
        if (!accountEntityOp.isPresent()) {
            throw new AccountNotFoundException();
        }
        return accountEntityOp.get().getBalance();
    }

    public void transfer(String originAccountNumber, String destinationAccountNumber, BigDecimal amount) throws AccountNotFoundException, NotEnoughBalanceException {
        Optional<Account> originAccountOp = transferRepository.findById(originAccountNumber);
        Optional<Account> destinationAccountOp = transferRepository.findById(destinationAccountNumber);

        if (!originAccountOp.isPresent() || !destinationAccountOp.isPresent()) {
            throw new AccountNotFoundException();
        }
        Account originAccount = originAccountOp.get();
        Account destinationAccount = destinationAccountOp.get();

        if (originAccount.getBalance().compareTo(amount) < 0) {
            throw new NotEnoughBalanceException();
        }

        originAccount.setBalance(originAccount.getBalance().subtract(amount));
        destinationAccount.setBalance(destinationAccount.getBalance().add(amount));

        transferRepository.save(originAccount);
        transferRepository.save(destinationAccount);
    }

    public void deposit(String account, BigDecimal amount) {
        Optional<Account> accountEntityOp = transferRepository.findById(account);

        if (accountEntityOp.isPresent()) {
            Account accountEntity = accountEntityOp.get();
            accountEntity.setBalance(accountEntity.getBalance().add(amount));
            transferRepository.save(accountEntity);
        } else {
            Account accountEntity = new Account(account,amount);
            transferRepository.save(accountEntity);
        }
    }

}
