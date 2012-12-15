package org.jarexploder.classfile.constant;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConstantPool {
	private final List<ConstantPoolInfo> constantPoolInfos;

	public ConstantPool(DataInputStream in) throws IOException {
		final short constantPoolCount = in.readShort();

		if (constantPoolCount == 0) {
			throw new ClassFormatError("Constant pool count shouldn't be equals to zero");
		}

		// Constant pool is indexed from 1 and not 0 so the first element (at
		// index 0) is null.
		constantPoolInfos = new ArrayList<>(constantPoolCount);
		constantPoolInfos.add(null);
		for (int i = 1; i < constantPoolCount; i++) {
			constantPoolInfos.add(new ConstantPoolInfo(in));
		}
	}

	public String getClassNameFromConstantClass(short classIndex) {
		final ConstantPoolInfo constantPoolInfo = constantPoolInfos.get(classIndex);
		final short nameIndex = constantPoolInfo.getNameIndex();
		return getClassName(nameIndex);
	}

	public String getClassName(short nameIndex) {
		final String internalClassName = getStringValue(nameIndex);
		return decodeInternalClassName(internalClassName);
	}

	private String decodeInternalClassName(String internalClassName) {
		if (internalClassName.matches("^\\[*B")) {
			return byte.class.getName();
		} else if (internalClassName.matches("^\\[*C")) {
			return char.class.getName();
		} else if (internalClassName.matches("^\\[*D")) {
			return double.class.getName();
		} else if (internalClassName.matches("^\\[*F")) {
			return float.class.getName();
		} else if (internalClassName.matches("^\\[*I")) {
			return int.class.getName();
		} else if (internalClassName.matches("^\\[*J")) {
			return long.class.getName();
		} else if (internalClassName.matches("^\\[*S")) {
			return short.class.getName();
		} else if (internalClassName.matches("^\\[*B")) {
			return boolean.class.getName();
		} else if (internalClassName.matches("V")) {
			// void descriptor.
			return null;
		} else {
			final Pattern pattern = Pattern.compile("(^\\[*L?+)(.+)");
			final Matcher matcher = pattern.matcher(internalClassName.replaceAll(";", "").replaceAll("/", "."));

			if (matcher.matches()) {
				return matcher.group(2);
			}

			throw new ClassFormatError(String.format("unknown class name: %s", internalClassName));
		}
	}

	/**
	 * Return a {@link String} value.
	 * 
	 * @param index
	 *            index of the string value in the pool (should point to a
	 *            {@link ConstantUtf8Info}).
	 * @return {@link String} value at index.
	 */
	public String getStringValue(short index) {
		return constantPoolInfos.get(index).getStringValue();
	}
}
