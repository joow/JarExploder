package org.jarexploder.classfile.constant;

import java.io.DataInputStream;
import java.io.IOException;


class ConstantIntegerInfo implements ConstantInfo {
	@SuppressWarnings("unused")
	private final int value;

	ConstantIntegerInfo(DataInputStream in) throws IOException {
		value = in.readInt();
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
