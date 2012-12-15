package org.jarexploder.classfile.constant;

import java.io.DataInputStream;
import java.io.IOException;


class ConstantStringInfo implements ConstantInfo {
	@SuppressWarnings("unused")
	private final short stringIndex;

	ConstantStringInfo(DataInputStream in) throws IOException {
		stringIndex = in.readShort();
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
