package org.jarexploder.classfile.constant;

import java.io.DataInputStream;
import java.io.IOException;


class ConstantClassInfo implements ConstantInfo {
	/**
	 * Index to the constant pool holding a ConstantUtf8Info (see chapter
	 * 4.5.1).
	 */
	private final short nameIndex;

	ConstantClassInfo(DataInputStream in) throws IOException {
		nameIndex = in.readShort();
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
