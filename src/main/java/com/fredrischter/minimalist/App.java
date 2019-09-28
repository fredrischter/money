package com.fredrischter.minimalist;

import org.apache.commons.io.IOUtils;

import static spark.Spark.get;

public class App {

	public App() throws Exception {
		System.out.println(new String(IOUtils.toByteArray(getClass().getResourceAsStream("/banner.txt"))));

		get("/hello", (req, res) -> "Testing");

		System.out.println("Try http://localhost:4567/hello");
	}
	
	public static void main(String []args) throws Exception {
		new App();
	}
}
