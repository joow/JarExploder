package org.jarexploder.classfile.attribute;

import java.io.DataInputStream;
import java.io.IOException;

class OptionalAttribute implements Attribute {
	private final byte[] info;

	OptionalAttribute(DataInputStream in, int attributeLength) throws IOException {
		info = new byte[attributeLength];
		in.read(info, 0, attributeLength);
	}
}
