package com.ibm.oti.pim;

/*
 * Licensed Materials - Property of IBM,
 * (c) Copyright IBM Corp. 2002, 2003  All Rights Reserved
 */

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;


class UTF8InputStreamReader {
	
	InputStream is;
	
	/**
	 * @see java.io.InputStreamReader#InputStreamReader(InputStream)
	 */
	UTF8InputStreamReader(InputStream is) {
		this.is = is;
	}	
	
	/**
	 * reads one characther from the stream.
	 * @return char
	 * @throws IOException
	 */
	char readChar() throws IOException {
		int firstByte = is.read();
		if (firstByte == -1)
			throw new EOFException("End of file reached.");
		int mask = 0x80;
		int count = 0;
		while ((firstByte & mask) == mask) {
			count++;
			mask >>= 1;
		}
		
		// at least firstByte
		count = Math.max(1, count);
		
		byte[] out = new byte[count]; 
		out[0] = (byte) firstByte;
		
		for (int i = 1; i < out.length; i++) {
			out[i] = readByte();
		}
		return convertChar(out, count);
	}
	
	/**
	 * Converts the given UTF8 bytes to chars.
	 * @param utfChar
	 * @param utfSize
	 * @return char
	 * @throws IOException
	 */
	private char convertChar(byte[] utfChar, int utfSize) throws IOException {
		char[] out = new char[1];
		EncodingHelper.decodeUTF8(utfChar, out, utfSize);
		return out[0];
	}
	
	/**
	 * Read one byte.
	 * @return byte
	 */
	private byte readByte() throws IOException {
		int temp = is.read();
		if (temp >= 0) return (byte) temp;
		throw new EOFException();
	}
}