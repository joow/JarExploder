package org.jarexploder.classfile.constant;

import java.io.DataInputStream;
import java.io.IOException;


class ConstantUtf8Info implements ConstantInfo {
	private final String value;

	ConstantUtf8Info(DataInputStream in) throws IOException {
		final short length = in.readShort();
		final byte[] bytes = new byte[length];
		in.read(bytes, 0, length);
		value = new String(bytes);
	}

	@Override
	public short getNameIndex() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getStringValue() {
		return value;
	}
}
