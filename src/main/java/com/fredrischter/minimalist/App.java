package com.fredrischter.minimalist;

import com.fredrischter.minimalist.resource.MoneyTransferResource;
import org.apache.commons.io.IOUtils;

public class App {

	public App() throws Exception {
		System.out.println(new String(IOUtils.toByteArray(getClass().getResourceAsStream("/banner.txt"))));

		new MoneyTransferResource();

		System.out.println("Try http://localhost:4567/hello");
	}
	
	public static void main(String []args) throws Exception {
		new App();
	}
}
