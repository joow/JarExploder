package org.jarexploder.classfile.constant;

import java.io.DataInputStream;
import java.io.IOException;

class ConstantPoolInfo {
	private static final byte CONSTANT_CLASS_TAG = 7;
	private static final byte CONSTANT_FIELDREF_TAG = 9;
	private static final byte CONSTANT_METHODREF_TAG = 10;
	private static final byte CONSTANT_INTERFACEMETHODREF_TAG = 11;
	private static final byte CONSTANT_STRING_TAG = 8;
	private static final byte CONSTANT_INTEGER_TAG = 3;
	private static final byte CONSTANT_FLOAT_TAG = 4;
	private static final byte CONSTANT_LONG_TAG = 5;
	private static final byte CONSTANT_DOUBLE_TAG = 6;
	private static final byte CONSTANT_NAMEANDTYPE_TAG = 12;
	private static final byte CONSTANT_UTF8_TAG = 1;

	private final ConstantInfo constantInfo;

	ConstantPoolInfo(DataInputStream in) throws IOException {
		final byte tag = in.readByte();

		switch (tag) {
			case CONSTANT_CLASS_TAG:
				constantInfo = new ConstantClassInfo(in);
				break;
			case CONSTANT_FIELDREF_TAG:
			case CONSTANT_METHODREF_TAG:
			case CONSTANT_INTERFACEMETHODREF_TAG:
				constantInfo = new ConstantFieldOrMethodOrInterfaceMethodRefInfo(in);
				break;
			case CONSTANT_STRING_TAG:
				constantInfo = new ConstantStringInfo(in);
				break;
			case CONSTANT_INTEGER_TAG:
				constantInfo = new ConstantIntegerInfo(in);
				break;
			case CONSTANT_FLOAT_TAG:
				constantInfo = new ConstantFloatInfo(in);
				break;
			case CONSTANT_LONG_TAG:
				constantInfo = new ConstantLongInfo(in);
				break;
			case CONSTANT_DOUBLE_TAG:
				constantInfo = new ConstantDoubleInfo(in);
				break;
			case CONSTANT_NAMEANDTYPE_TAG:
				constantInfo = new ConstantNameAndTypeInfo(in);
				break;
			case CONSTANT_UTF8_TAG:
				constantInfo = new ConstantUtf8Info(in);
				break;

			default:
				throw new ClassFormatError(String.format("Unknown constant tag: %d", tag));

		}
	}

	public short getNameIndex() {
		return constantInfo.getNameIndex();
	}

	public String getStringValue() {
		return constantInfo.getStringValue();
	}
}
