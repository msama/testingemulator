package com.ibm.oti.pim;

/*
 * Licensed Materials - Property of IBM,
 * (c) Copyright IBM Corp. 2002, 2003  All Rights Reserved
 */

import java.io.IOException;
import java.io.OutputStream;


class UTF8OutputStreamWriter {
	
	private OutputStream out;
	
	public UTF8OutputStreamWriter(OutputStream out) {
		this.out = out;
	}
	
	/**
	 * Encode the given String in UTF8 and writes it to the stream.
	 * @param str. The String to write.
	 * @param offset. Offset in the String.
	 * @param count. Number of chars in the String to write.
	 * @throws IOException
	 */
	void write(String str, int offset, int count) throws IOException {
		if (0 <= offset && offset <= str.length() && 0 <= count && count <= str.length() - offset) {
			char convert[] = new char[count];
			str.getChars(offset, offset + count, convert, 0);
			write(convert, 0, convert.length);
		} 
		else 
			throw new IOException();
	}

	/**
	 * Encode the given chars in UTF8 and writes them to the stream.
	 * @param buf. The buffer to written
	 * @param offset. Offset in buffer to get bytes
	 * @param count. The number of chars in buffer to write
	 * @throws IOException
	 */
	void write(char[] buf, int offset, int count) throws IOException {
		if (0 <= offset && offset <= buf.length && 0 <= count && count <= buf.length - offset) {
			byte[] converted = EncodingHelper.encodeUTF8(buf, offset, count);
			out.write(converted);
		} 
		else 
			throw new IOException();
	}
}