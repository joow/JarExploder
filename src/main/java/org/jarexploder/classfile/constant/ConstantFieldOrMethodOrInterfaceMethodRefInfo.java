package org.jarexploder.classfile.constant;

import java.io.DataInputStream;
import java.io.IOException;


class ConstantFieldOrMethodOrInterfaceMethodRefInfo implements ConstantInfo {
	@SuppressWarnings("unused")
	private final short classIndex;

	@SuppressWarnings("unused")
	private final short nameAndTypeIndex;

	ConstantFieldOrMethodOrInterfaceMethodRefInfo(DataInputStream in) throws IOException {
		classIndex = in.readShort();
		nameAndTypeIndex = in.readShort();
	}

	@Override
	public short getNameIndex() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getStringValue() {
		throw new UnsupportedOperationException();
	}
}
