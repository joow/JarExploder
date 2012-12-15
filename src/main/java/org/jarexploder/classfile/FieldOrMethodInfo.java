package org.jarexploder.classfile;

import java.io.DataInputStream;
import java.io.IOException;

import org.jarexploder.classfile.attribute.AttributeInfo;
import org.jarexploder.classfile.constant.ConstantPool;

class FieldOrMethodInfo {
	private final AccessFlags accessFlags;

	private final short nameIndex;

	private final short descriptorIndex;

	@SuppressWarnings("unused")
	private final AttributeInfo[] attributes;

	FieldOrMethodInfo(DataInputStream in, ConstantPool constantPool) throws IOException {
		accessFlags = new AccessFlags(in.readShort());
		nameIndex = in.readShort();
		descriptorIndex = in.readShort();
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

	short getNameIndex() {
		return nameIndex;
	}

	short getDescriptorIndex() {
		return descriptorIndex;
	}

	boolean isSynthetic() {
		return accessFlags.isSynthetic();
	}
}
