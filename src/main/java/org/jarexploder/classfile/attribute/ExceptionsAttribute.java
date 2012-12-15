package org.jarexploder.classfile.attribute;

import java.io.DataInputStream;
import java.io.IOException;

class ExceptionsAttribute implements Attribute {
	private final byte[] exceptionIndexTable;

	ExceptionsAttribute(DataInputStream in) throws IOException {
		final short numberOfExceptions = in.readShort();
		exceptionIndexTable = new byte[numberOfExceptions];
		in.read(exceptionIndexTable, 0, numberOfExceptions);
	}
}
