package Utilities.A11y;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.ThreadContext;

import java.lang.StackWalker;
import java.lang.StackWalker.StackFrame;
import java.util.Optional;

public class LogUtilities {

	public static Logger logger = LogManager.getLogger();
	// 🧠 Short method to extract test method name
	private static String testName() {
		return StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE)
				.walk(frames -> frames.skip(2).findFirst())
				.map(frame -> frame.getClassName() + "." + frame.getLineNumber())
				.orElse("UnknownTest");
	}
	public static void setTestName(String name) {
		ThreadContext.put("testCaseName", name);
	}



	public static void info(String message) {
		logger.info("[{}]: [{}]", testName(), message);
	}

	public static void warn(String message) {
		logger.warn("[{}]: [{}]", testName(), message);
	}

	public static void error(String message) {
		logger.error("[{}]: [{}]", testName(), message);
	}

	public static void fatal(String message) {
		logger.fatal("[{}]: [{}]", testName(), message);
	}

	public static void debug(String message) {
		logger.debug("[{}]: [{}]", testName(), message);
	}

}
