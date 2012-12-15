package org.jarexploder.classfile.attribute;

import java.io.DataInputStream;
import java.io.IOException;


class SignatureAttribute implements Attribute {
	@SuppressWarnings("unused")
	private final short signatureIndex;

	SignatureAttribute(DataInputStream in) throws IOException {
		signatureIndex = in.readShort();
	}
}
