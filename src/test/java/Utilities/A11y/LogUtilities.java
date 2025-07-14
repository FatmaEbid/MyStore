package Utilities.A11y;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class LogUtilities {

	public static Logger logger = LogManager.getLogger();

	// This method is used to get the logger instance for the current class
	// It uses the class name from the stack trace to create a logger
	// This allows for dynamic logging based on the class that is currently executing
	public static Logger getLogger() {
		 logger = LogManager.getLogger(Thread.currentThread().getStackTrace()[3].getClassName());
		return logger;
	}


	public static void info(String message) {
		getLogger().info(message);
	}

	public static void warn(String message) {
		getLogger().warn(message);
	}

	public static void error(String message) {
		getLogger().error(message);
	}

	public static void fatal(String message) {
		getLogger().fatal(message);
	}

	public static void debug(String message) {
		getLogger().debug(message);
	}

}
