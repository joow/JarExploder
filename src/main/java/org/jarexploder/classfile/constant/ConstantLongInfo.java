package org.jarexploder.classfile.constant;

import java.io.DataInputStream;
import java.io.IOException;


class ConstantLongInfo implements ConstantInfo {
	@SuppressWarnings("unused")
	private final long value;

	ConstantLongInfo(DataInputStream in) throws IOException {
		final int highBytes = in.readInt();
		final int lowBytes = in.readInt();
		value = ((long) highBytes << 32) + lowBytes;
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