package com.fredrischter.financial.money

import spark.Spark.*;
import java.io.*;

fun main(args: Array<String>) {
	println("Hello World!")

	get("/hello") { req, res ->
	    "Hello Spark Kotlin"
	}
	println("Try http://localhost:4567/hello")
}
