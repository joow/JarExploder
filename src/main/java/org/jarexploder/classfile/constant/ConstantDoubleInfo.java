package org.jarexploder.classfile.constant;

import java.io.DataInputStream;
import java.io.IOException;


class ConstantDoubleInfo implements ConstantInfo {
	@SuppressWarnings("unused")
	private final int highBytes;

	@SuppressWarnings("unused")
	private final int lowBytes;

	ConstantDoubleInfo(DataInputStream in) throws IOException {
		highBytes = in.readInt();
		lowBytes = in.readInt();
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
