package org.jarexploder.classfile;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jarexploder.classfile.constant.ConstantPool;

public class JavaClass {
	private static final int MAGIC_CLASS_FILE_FORMAT = 0xCAFEBABE;

	@SuppressWarnings("unused")
	private final short minorVersion;

	@SuppressWarnings("unused")
	private final short majorVersion;

	@SuppressWarnings("unused")
	private final AccessFlags accessFlags;

	private final String className;

	private final String superClassName;

	// TODO : BETTER :)
	private final List<String> interfaces;

	private final List<JavaField> fields;

	private final List<String> methodsParametersClassNames;

	public JavaClass(InputStream inputStream) throws IOException {
		try (final DataInputStream in = new DataInputStream(new BufferedInputStream(inputStream))) {
			parseMagic(in);
			minorVersion = parseMinorVersion(in);
			majorVersion = parseMajorVersion(in);
			final ConstantPool constantPool = parseConstantPool(in);
			accessFlags = parseAccessFlags(in);
			className = parseThisClass(in, constantPool);
			superClassName = parseSuperClass(in, constantPool);
			interfaces = parseInterfaces(in, constantPool);
			fields = parseFields(in, constantPool);
			methodsParametersClassNames = parseMethods(in, constantPool);
		}
	}

	private void parseMagic(DataInputStream in) throws IOException {
		final int magic = in.readInt();

		if (magic != MAGIC_CLASS_FILE_FORMAT) {
			throw new ClassFormatError(String.format("Invalid magic code, expected 0x%X, got 0x%X", MAGIC_CLASS_FILE_FORMAT, magic));
		}
	}

	private short parseMinorVersion(DataInputStream in) throws IOException {
		return in.readShort();
	}

	private short parseMajorVersion(DataInputStream in) throws IOException {
		return in.readShort();
	}

	private ConstantPool parseConstantPool(DataInputStream in) throws IOException {
		return new ConstantPool(in);
	}

	private AccessFlags parseAccessFlags(DataInputStream in) throws IOException {
		return new AccessFlags(in.readShort());
	}

	private String parseThisClass(DataInputStream in, ConstantPool constantPool) throws IOException {
		final short thisClass = in.readShort();
		return constantPool.getClassNameFromConstantClass(thisClass);
	}

	private String parseSuperClass(DataInputStream in, ConstantPool constantPool) throws IOException {
		final short superClass = in.readShort();
		return constantPool.getClassNameFromConstantClass(superClass);
	}

	private List<String> parseInterfaces(DataInputStream in, ConstantPool constantPool) throws IOException {
		final short interfacesCount = in.readShort();

		if (interfacesCount == 0) {
			return Collections.emptyList();
		}

		final List<String> interfaces = new ArrayList<>();

		for (int i = 0; i < interfacesCount; i++) {
			interfaces.add(constantPool.getClassNameFromConstantClass(in.readShort()));
		}

		return interfaces;
	}

	private List<JavaField> parseFields(DataInputStream in, ConstantPool constantPool) throws IOException {
		final short fieldsCount = in.readShort();

		if (fieldsCount == 0) {
			return Collections.emptyList();
		}

		final List<JavaField> fields = new ArrayList<>();

		for (int i = 0; i < fieldsCount; i++) {
			fields.add(new JavaField(in, constantPool));
		}

		return fields;
	}

	private List<String> parseMethods(DataInputStream in, ConstantPool constantPool) throws IOException {
		final short methodsCount = in.readShort();

		if (methodsCount == 0) {
			return Collections.emptyList();
		}

		final List<String> methodsParametersClassNames = new ArrayList<>();

		for (int i = 0; i < methodsCount; i++) {
			final FieldOrMethodInfo methodInfo = new FieldOrMethodInfo(in, constantPool);

			if (!methodInfo.isSynthetic()) {
				final short descriptorIndex = methodInfo.getDescriptorIndex();
				final String methodDescriptor = constantPool.getStringValue(descriptorIndex);
				final List<String> parametersClassNames = parseMethodDescriptor(methodDescriptor);
				methodsParametersClassNames.addAll(parametersClassNames);
			}
		}

		return methodsParametersClassNames;
	}

	// TODO : MUTUALISER
	private String parseFieldDescriptor(String fieldDescriptor) {
		final Pattern pattern = Pattern.compile("(^\\[*L)(.+)");
		final Matcher matcher = pattern.matcher(fieldDescriptor.replaceAll(";", ""));

		if (matcher.matches()) {
			return decodeInternalName(matcher.group(2));
		} else if (fieldDescriptor.matches("^\\[*B")) {
			return byte.class.getName();
		} else if (fieldDescriptor.matches("^\\[*C")) {
			return char.class.getName();
		} else if (fieldDescriptor.matches("^\\[*D")) {
			return double.class.getName();
		} else if (fieldDescriptor.matches("^\\[*F")) {
			return float.class.getName();
		} else if (fieldDescriptor.matches("^\\[*I")) {
			return int.class.getName();
		} else if (fieldDescriptor.matches("^\\[*J")) {
			return long.class.getName();
		} else if (fieldDescriptor.matches("^\\[*S")) {
			return short.class.getName();
		} else if (fieldDescriptor.matches("^\\[*B")) {
			return boolean.class.getName();
		} else if (fieldDescriptor.matches("V")) {
			// void descriptor.
			return null;
		}

		throw new ClassFormatError(String.format("unknown field descriptor type: %s", fieldDescriptor));
	}

	private List<String> parseMethodDescriptor(String methodDescriptor) {
		final List<String> methodParameterClassNames = new ArrayList<>();
		final String[] methodDescriptorSplitted = methodDescriptor.split("\\)");

		// Removes starting '(' from parameters.
		final String parametersDescriptor = methodDescriptorSplitted[0].substring(1);
		final String returnDescriptor = methodDescriptorSplitted[1];

		final StringTokenizer stringTokenizer = new StringTokenizer(parametersDescriptor, ";");
		while (stringTokenizer.hasMoreTokens()) {
			final String parameterDescriptor = stringTokenizer.nextToken();
			final String parameterClassName = parseFieldDescriptor(parameterDescriptor);
			if (parameterClassName != null) {
				methodParameterClassNames.add(parameterClassName);
			}
		}

		final String returnClassName = parseFieldDescriptor(returnDescriptor);
		if (returnClassName != null) {
			methodParameterClassNames.add(returnClassName);
		}

		return methodParameterClassNames;
	}

	public String getClassName() {
		return className;
	}

	private String decodeInternalName(String internalName) {
		return internalName.replace('/', '.');
	}

	public String getSuperClassName() {
		return superClassName;
	}

	public List<String> getInterfaces() {
		return interfaces;
	}

	public List<JavaField> getFields() {
		return fields;
	}

	public List<String> getMethodsParametersClassNames() {
		return methodsParametersClassNames;
	}

	public List<String> getThrowedExceptionsClassNames() {
		return Collections.emptyList();
	}
}
