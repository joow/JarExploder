package org.jarexploder.classfile;

import java.awt.Dimension;
import java.awt.Point;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.jarexploder.classfile.exception.CheckedExceptionFour;
import org.jarexploder.classfile.exception.CheckedExceptionOne;
import org.jarexploder.classfile.exception.CheckedExceptionThree;
import org.jarexploder.classfile.exception.CheckedExceptionTwo;
import org.jarexploder.classfile.exception.UncheckedExceptionFour;
import org.jarexploder.classfile.exception.UncheckedExceptionOne;
import org.jarexploder.classfile.exception.UncheckedExceptionThree;
import org.jarexploder.classfile.exception.UncheckedExceptionTwo;

/**
 * "Advanced" class for test purposes only so a lot of code is unused.
 */
@SuppressWarnings("unused")
public class AdvancedClass extends ArrayList<String> implements SimpleInterface, Comparable<Date> {
	private static final String STRING_CONSTANT = "String";

	private static final int INT_CONSTANT = 1;

	private static Point X = new Point(0, 0);

	private final byte[] bytes = new byte[16];

	private List<Date> dates;

	private Object[][][] dimensions = new Dimension[1][1][1];

	@Override
	public int compareTo(Date o) {
		return 0;
	}

	public void methodWithOneParameter(Calendar calendar) {
	}

	public void methodWithMultipleParameters(String s1, String s2, String s3) {
	}

	public void methodWithArrayParameter(byte[] bytes) {
	}

	public void methodWithEllipseParameter(String s, long... values) {
	}

	public void throwOneCheckedException() throws CheckedExceptionOne {
	}

	public void throwOneUncheckedException() throws UncheckedExceptionOne {
	}

	public void throwTwoCheckedExceptions() throws CheckedExceptionTwo, CheckedExceptionThree {
	}

	public void throwTwoUncheckedExceptions() throws UncheckedExceptionTwo, UncheckedExceptionThree {
	}

	public void throwOneCheckedAndOneUncheckedExceptions() throws CheckedExceptionFour, UncheckedExceptionFour {
	}

	public void catchOneUncheckedException() {
		try {
			Integer.parseInt("Hello");
		} catch (NumberFormatException e) {
		}
	}

	public void catchOneCheckedException() {
		try {
			Files.newBufferedReader(Paths.get("inexistant file"), Charset.defaultCharset());
		} catch (IOException e) {
		}
	}

	public void catchThreeCheckedExceptions() {
		try {
			Class.forName("inexistant class").newInstance();
		} catch (InstantiationException e) {
		} catch (IllegalAccessException e) {
		} catch (ClassNotFoundException e) {
		}
	}
}
