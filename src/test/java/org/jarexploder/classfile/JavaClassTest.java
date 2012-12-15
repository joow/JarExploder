package org.jarexploder.classfile;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

public class JavaClassTest {
	private static final String SRC_TEST_PATH = "src/test/java";

	private static JavaClass simpleJavaClass;

	private static JavaClass advancedJavaClass;

	private static JavaClass simpleInterfaceJavaClass;

	@BeforeClass
	public static void parseJavaClasses() throws IOException {
		simpleJavaClass = new JavaClass(Files.newInputStream(getPath("validclass/SimpleClass.class"), StandardOpenOption.READ));
		advancedJavaClass = new JavaClass(Files.newInputStream(getPath("validclass/AdvancedClass.class"), StandardOpenOption.READ));
		simpleInterfaceJavaClass = new JavaClass(Files.newInputStream(getPath("validclass/SimpleInterface.class"), StandardOpenOption.READ));
	}

	private static Path getPath(String relativePath) {
		return Paths.get(getCurrentPath(relativePath));
	}

	private static String getCurrentPath(String relativePath) {
		final String packagePath = JavaClassTest.class.getPackage().getName().replace(".", File.separator);
		return System.getProperty("user.dir") + File.separator + SRC_TEST_PATH + File.separator + packagePath + File.separator + relativePath;
	}

	@Test
	public void readJavaClassWithInvalidMagicCode() throws IOException {
		final Path path = Paths.get(getCurrentPath("invalidclass/InvalidMagicCode.bin"));

		try {
			new JavaClass(Files.newInputStream(path, StandardOpenOption.READ));
			fail("Parsing java class with invalid magic code should fail.");
		} catch (ClassFormatError e) {
			assertTrue(e.getMessage().matches("Invalid magic code, expected 0x[A-F0-9]{8}, got 0x[A-F0-9]{8}"));
		}
	}

	@Test
	public void readJavaClassWithConstantPoolCountEqualsToZero() throws IOException {
		final Path path = Paths.get(getCurrentPath("invalidclass/ConstantPoolCountEqualsToZero.bin"));

		try {
			new JavaClass(Files.newInputStream(path, StandardOpenOption.READ));
			fail("Parsing java class with constant pool count equals to zero should fail.");
		} catch (ClassFormatError e) {
			assertEquals("Constant pool count shouldn't be equals to zero", e.getMessage());
		}
	}

	@Test
	public void readJavaClassWithInvalidConstantPoolInfoTag() throws IOException {
		final Path path = Paths.get(getCurrentPath("invalidclass/InvalidConstantPoolInfoTag.bin"));

		try {
			new JavaClass(Files.newInputStream(path, StandardOpenOption.READ));
			fail("Parsing java class with invalid constant pool info tag should fail.");
		} catch (ClassFormatError e) {
			assertTrue(e.getMessage().matches("Unknown constant tag: \\d*"));
		}
	}

	@Test
	public void readClassNameFromJavaClass() {
		assertEquals(SimpleClass.class.getName(), simpleJavaClass.getClassName());
	}

	@Test
	public void readSuperClassNameFromJavaClassExtendingObject() {
		assertEquals(Object.class.getName(), simpleJavaClass.getSuperClassName());
	}

	@Test
	public void readSuperClassNameFromJavaInterface() {
		assertEquals(Object.class.getName(), simpleInterfaceJavaClass.getSuperClassName());
	}

	@Test
	public void readSuperClassNameFromJavaClassExtendingClass() {
		assertEquals(ArrayList.class.getName(), advancedJavaClass.getSuperClassName());
	}

	@Test
	public void readInterfacesFromClassNotImplementingAnyInterface() {
		assertTrue(simpleJavaClass.getInterfaces().isEmpty());
	}

	@Test
	public void readInterfacesFromClassImplementingInterfaces() {
		final List<String> interfaces = advancedJavaClass.getInterfaces();
		assertEquals(2, interfaces.size());
		assertTrue(interfaces.contains(SimpleInterface.class.getName()));
		assertTrue(interfaces.contains(Comparable.class.getName()));
	}

	@Test
	public void readFieldsFromClassWithoutFields() {
		assertTrue(simpleJavaClass.getFields().isEmpty());
	}

	@Test
	public void readFieldsFromClassWithFields() {
		final List<JavaField> fields = advancedJavaClass.getFields();

		assertEquals(6, fields.size());
		assertTrue(fieldsContainsField(fields, "STRING_CONSTANT", String.class.getName()));
		assertTrue(fieldsContainsField(fields, "INT_CONSTANT", int.class.getName()));
		assertTrue(fieldsContainsField(fields, "X", Point.class.getName()));
		assertTrue(fieldsContainsField(fields, "bytes", byte.class.getName()));
		assertTrue(fieldsContainsField(fields, "dates", List.class.getName()));
		assertTrue(fieldsContainsField(fields, "dimensions", Object.class.getName()));
	}

	private boolean fieldsContainsField(List<JavaField> fields, String name, String className) {
		for (final JavaField field : fields) {
			if (field.getName().equals(name) && field.getClassName().equals(className)) {
				return true;
			}
		}

		return false;
	}

	@Test
	public void readMethodsParametersFromClassWithoutMethods() {
		assertTrue(simpleInterfaceJavaClass.getMethodsParametersClassNames().isEmpty());
	}

	@Test
	public void readMethodsParametersFromClassWithMethodsWithoutParameters() {
		assertTrue(simpleJavaClass.getMethodsParametersClassNames().isEmpty());
	}

	@Test
	public void readMethodsParametersFromClassWithParameterizedMethods() {
		final List<String> methodsParametersClassNames = advancedJavaClass.getMethodsParametersClassNames();

		assertEquals(9, methodsParametersClassNames.size());
		assertTrue(methodsParametersClassNames.contains(Date.class.getName()));
		assertTrue(methodsParametersClassNames.contains(int.class.getName()));
		assertTrue(methodsParametersClassNames.contains(Calendar.class.getName()));
		assertTrue(methodsParametersClassNames.contains(String.class.getName()));
		assertTrue(methodsParametersClassNames.contains(byte.class.getName()));
		assertTrue(methodsParametersClassNames.contains(long.class.getName()));
	}

	@Test
	public void readThrowedExceptionsClassNamesFromClassWithoutThrowedException() {
		assertTrue(simpleJavaClass.getThrowedExceptionsClassNames().isEmpty());
	}

	@Test
	public void readThrowedExceptionsClassNamesFromClassWithThrowedExceptions() {
		final List<String> throwedExceptionsClassNames = advancedJavaClass.getThrowedExceptionsClassNames();

		// TODO assertEquals(8, throwedExceptionsClassNames.size());
	}

	// TODO : GET THROWED EXCEPTIONS, LOCAL VARIABLES, CATCHED EXCEPTIONS,
	// ANNOTATIONS ...
}
