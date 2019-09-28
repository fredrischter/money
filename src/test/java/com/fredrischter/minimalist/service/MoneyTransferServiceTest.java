package com.fredrischter.minimalist.service;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import com.fredrischter.minimalist.repository.AccountRepository;
import com.fredrischter.minimalist.service.exceptions.AccountNotFoundException;
import com.fredrischter.minimalist.service.exceptions.NotEnoughBalanceException;
import org.junit.Before;
import org.junit.Test;

public class MoneyTransferServiceTest {

	private static final String INEXISTENT_ID = "12312312";
	private static final String ACCOUNT_A = "11111";
	private static final String ACCOUNT_B = "22222";

	MoneyTransferService moneyTransferService;

	@Before
	public void setup() {
		AccountRepository.drop();
		moneyTransferService = new MoneyTransferService();
	}

	@Test(expected = AccountNotFoundException.class)
	public void accountNotFoundTest() throws AccountNotFoundException {
		// When
		moneyTransferService.getBalance(INEXISTENT_ID);
	}

	@Test
	public void creatingAccount() {
		// When
		moneyTransferService.deposit(INEXISTENT_ID, new BigDecimal(10));
	}

	@Test
	public void transfering() throws AccountNotFoundException, NotEnoughBalanceException {
		// Given
		createA100B100();
		
		// When
		moneyTransferService.transfer(ACCOUNT_A, ACCOUNT_B, new BigDecimal(30));
	}

	private void createA100B100() {
		moneyTransferService.deposit(ACCOUNT_A, new BigDecimal(100));
		moneyTransferService.deposit(ACCOUNT_B, new BigDecimal(100));
	}

	@Test(expected = NotEnoughBalanceException.class)
	public void notEnoughBalance() throws AccountNotFoundException, NotEnoughBalanceException {
		// Given
		createA100B100();
		
		// When
		moneyTransferService.transfer(ACCOUNT_A, ACCOUNT_B, new BigDecimal(130));
	}

	@Test
	public void balanceCheck() throws AccountNotFoundException, NotEnoughBalanceException {
		// Given
		createA100B100();
		
		// When
		moneyTransferService.transfer(ACCOUNT_A, ACCOUNT_B, new BigDecimal(30));
		
		// Then
		assertEquals(new BigDecimal(130), moneyTransferService.getBalance(ACCOUNT_B));
		assertEquals(new BigDecimal(70), moneyTransferService.getBalance(ACCOUNT_A));
	}
	
}
