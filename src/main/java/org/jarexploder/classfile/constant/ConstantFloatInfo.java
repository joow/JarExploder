package org.jarexploder.classfile.constant;

import java.io.DataInputStream;
import java.io.IOException;


class ConstantFloatInfo implements ConstantInfo {
	private static final int POSITIVE_INFINITY = 0x7f800000;

	private static final int NEGATIVE_INFINITY = 0xff800000;

	private static final int NAN_POSITIVE_UPPER_LIMIT = 0x7fffffff;

	private static final int NAN_NEGATIVE_UPPER_LIMIT = 0xffffffff;

	@SuppressWarnings("unused")
	private final float value;

	ConstantFloatInfo(DataInputStream in) throws IOException {
		final int bits = in.readInt();

		/*
		 * Following code is completely UNTESTED (see chapter 4.5.4 for details
		 * to determine float value).
		 */
		if ((bits & POSITIVE_INFINITY) == POSITIVE_INFINITY) {
			value = Float.POSITIVE_INFINITY;
		} else if ((bits & NEGATIVE_INFINITY) == NEGATIVE_INFINITY) {
			value = Float.NEGATIVE_INFINITY;
		} else if ((bits > POSITIVE_INFINITY && bits <= NAN_POSITIVE_UPPER_LIMIT) || (bits > NEGATIVE_INFINITY && bits <= NAN_NEGATIVE_UPPER_LIMIT)) {
			value = Float.NaN;
		} else {
			int s = ((bits >> 31) == 0) ? 1 : -1;
			int e = ((bits >> 23) & 0xff);
			int m = (e == 0) ? (bits & NAN_POSITIVE_UPPER_LIMIT) << 1 : (bits & NAN_POSITIVE_UPPER_LIMIT) | 0x800000;
			value = (float) (s * m * Math.pow(2, e - 150));
		}
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
