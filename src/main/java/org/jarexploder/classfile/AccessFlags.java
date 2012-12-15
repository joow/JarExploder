package org.jarexploder.classfile;

class AccessFlags {
	private static final short ACC_SYNTHETIC = 0x1000;

	private final short value;

	AccessFlags(short value) {
		this.value = value;
	}

	boolean isSynthetic() {
		return (value & ACC_SYNTHETIC) == ACC_SYNTHETIC;
	}
}
