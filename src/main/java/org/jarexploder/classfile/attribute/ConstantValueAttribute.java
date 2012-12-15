package org.jarexploder.classfile.attribute;

import java.io.DataInputStream;
import java.io.IOException;


class ConstantValueAttribute implements Attribute {
	@SuppressWarnings("unused")
	private final short constantValueIndex;

	ConstantValueAttribute(DataInputStream in) throws IOException {
		constantValueIndex = in.readShort();
	}
}
