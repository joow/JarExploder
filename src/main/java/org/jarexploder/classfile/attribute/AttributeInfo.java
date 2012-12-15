package org.jarexploder.classfile.attribute;

import java.io.DataInputStream;
import java.io.IOException;

import org.jarexploder.classfile.constant.ConstantPool;

public class AttributeInfo {
	private static final String CONSTANT_VALUE_ATTRIBUTE_TYPE = "ConstantValue";

	private static final String SIGNATURE_ATTRIBUTE_TYPE = "Signature";

	private static final String CODE_ATTRIBUTE_TYPE = "Code";

	private static final String EXCEPTIONS_ATTRIBUTE_TYPE = "Exceptions";

	@SuppressWarnings("unused")
	private final Attribute attribute;

	public AttributeInfo(DataInputStream in, ConstantPool constantPool) throws IOException {
		final short attributeNameIndex = in.readShort();
		final String attributeType = constantPool.getStringValue(attributeNameIndex);
		attribute = parseAttribute(attributeType, in);
	}

	private Attribute parseAttribute(String attributeType, DataInputStream in) throws IOException {
		final int attributeLength = in.readInt();

		switch (attributeType) {
			case CONSTANT_VALUE_ATTRIBUTE_TYPE:
				return new ConstantValueAttribute(in);
			case SIGNATURE_ATTRIBUTE_TYPE:
				return new SignatureAttribute(in);
				// TODO case EXCEPTIONS_ATTRIBUTE_TYPE:
				// return new ExceptionsAttribute(in);
			case CODE_ATTRIBUTE_TYPE:
			case EXCEPTIONS_ATTRIBUTE_TYPE:
				return new OptionalAttribute(in, attributeLength);
			default:
				throw new ClassFormatError(String.format("Unknown attribute type: %s", attributeType));
		}
	}
}
