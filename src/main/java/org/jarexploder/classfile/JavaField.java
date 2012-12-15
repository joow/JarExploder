package org.jarexploder.classfile;

import java.io.DataInputStream;
import java.io.IOException;

import org.jarexploder.classfile.attribute.AttributeInfo;
import org.jarexploder.classfile.constant.ConstantPool;

public class JavaField {
	@SuppressWarnings("unused")
	private final AccessFlags accessFlags;

	private final String name;

	private final String className;

	@SuppressWarnings("unused")
	private final AttributeInfo[] attributes;

	JavaField(DataInputStream in, ConstantPool constantPool) throws IOException {
		accessFlags = new AccessFlags(in.readShort());

		final short nameIndex = in.readShort();
		name = constantPool.getStringValue(nameIndex);

		final short descriptorIndex = in.readShort();
		className = constantPool.getClassName(descriptorIndex);

		attributes = parseAttributes(in, constantPool);
	}

	private AttributeInfo[] parseAttributes(DataInputStream in, ConstantPool constantPool) throws IOException {
		final short attributesCount = in.readShort();
		final AttributeInfo[] attributes = new AttributeInfo[attributesCount];

		for (int i = 0; i < attributesCount; i++) {
			attributes[i] = new AttributeInfo(in, constantPool);
		}

		return attributes;
	}

	public Object getName() {
		return name;
	}

	public String getClassName() {
		return className;
	}
}
