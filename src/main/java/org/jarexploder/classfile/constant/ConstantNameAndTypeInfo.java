package org.jarexploder.classfile.constant;

import java.io.DataInputStream;
import java.io.IOException;


class ConstantNameAndTypeInfo implements ConstantInfo {
	private final short nameIndex;

	@SuppressWarnings("unused")
	private final short descriptorIndex;

	ConstantNameAndTypeInfo(DataInputStream in) throws IOException {
		nameIndex = in.readShort();
		descriptorIndex = in.readShort();
	}

	@Override
	public short getNameIndex() {
		return nameIndex;
	}

	@Override
	public String getStringValue() {
		throw new UnsupportedOperationException();
	}
}
