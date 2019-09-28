package com.fredrischter.minimalist;

import java.nio.file.Files;
import java.nio.file.Paths;

import static spark.Spark.get;

public class App {

	public App() throws Exception {
		System.out.println(new String(Files.readAllBytes(Paths.get(ClassLoader.getSystemResource("banner.txt").toURI()))));

		get("/hello", (req, res) -> "Testing");

		System.out.println("Try http://localhost:4567/hello");
	}
	
	public static void main(String []args) throws Exception {
		new App();
	}
}
