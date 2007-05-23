package com.ibm.oti.pim;

/*
 * Licensed Materials - Property of IBM,
 * (c) Copyright IBM Corp. 2002, 2003  All Rights Reserved
 */

import java.io.IOException;

public class EncodingHelper {
	
	private static char digits[] = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P',
					 				  'Q','R','S','T','U','V','W','X','Y','Z','a','b','c','d','e','f',
					 				  'g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v',
					 				  'w','x','y','z','0','1','2','3','4','5','6','7','8','9','+','/'};
	
	private static final byte equalSign = (byte) '=';
	
	
	/**
	 * Checks if the given String needs to be encoded using the QUOTED-PRINTED encoding.
	 * @param string
	 * @return boolean
	 */
	public static boolean hasToBeQuotedPrintableEncoded(String string) {
		for (int i=0; i < string.length(); i++) {
			char a = string.charAt(i);
			if (!((33 <= a && a <= 60) || (62 <= a && a <= 126)) && a != ' ')
				return true;
		}		
		return false;
	}
		
	/**
	 * 
	 * Encodes the given String in QuotedPrintable.
	 * @param str
	 * @return String
	 */
	public static String encodeQuotedPrintable(String str) {
		StringBuffer result= new StringBuffer();
		int strlen = str.length();
		int chars=0;
		
		for (int j = 0; j < strlen; j++) {
			if (chars == 76)
				result.append("=\r\n");
			
			char a = str.charAt(j);
			int v = (int)a;
			// no encoding
			if ((33 <= a && a <= 60) || (62 <= a && a <= 126)) {
				result.append(a);
				chars++;
			}
			// encode
			else {
				if (a == ' ') {
					if (j != (strlen - 1)) {
						result.append(' ');
						chars++;
						continue;
					}
				}
				if (a == '\n') {
					result.append("=0D=0A");
					chars=0;
				}
				else {
					if (0 > a || a > 255)
						continue;
					result.append('=');
					String hs = Integer.toHexString(v);
					hs = hs.toUpperCase();
					if (hs.length() == 1)
						result.append('0');
					result.append(hs);
					chars += 3;
				}
			}
		}
		return result.toString();
	}
	
	/**
	 * Decodes the given QuotedPrintable String.
	 * @param str
	 * @return String
	 */
	public static String decodeQuotedPrintable(String str) {
		String temp=null;
		StringBuffer result= new StringBuffer();
		int strlen = str.length();
		
		for (int j = 0; j < strlen; j++)  {
			char a = str.charAt(j);
			// no decoding
			if ((33 <= a && a <= 60) || (62 <= a && a <= 126)) {
				result.append(a);
			}
			// decode
			else {
				if (a == '=') {
					j++;
					a = str.charAt(j);
					if (a == '\r')
						j++;
					else {
						temp = "";
						temp += a;
						j++;
						a = str.charAt(j);
						temp += a;
						int i = Integer.parseInt(temp, 16);
						result.append((char)i);
					}
				}
				else if (a == ' ')
					result.append(' ');
			}
		}
		return result.toString();
	}

	/**
	 * This method encodes the byte array into a char array in base 64 according to
	 * the specification given by the RFC 1521 (5.2).
	 * @return byte[]	the byte array that needs to be encoded
	 * @param  char[]	the encoded char array
	 */
	public static byte[] encodeBASE64(byte[] data) {
		int sourceChunks = data.length / 3;
		int len = ((data.length+2)/3)*4;
		byte[] result = new byte[len];
		int extraBytes = data.length - (sourceChunks * 3);
		// Each 4 bytes of input (encoded) we end up with 3 bytes of output
		int dataIndex = 0;
		int resultIndex = 0;
		int allBits = 0;
		for (int i=0; i < sourceChunks; i++) {
			allBits = 0;
			// Loop 3 times gathering input bits (3 * 8 = 24)
			for (int j = 0; j < 3; j++) {
				allBits = (allBits << 8) | (data[dataIndex++] & 0xff);
			}
	
			// Loop 4 times generating output bits (4 * 6 = 24)
			for (int j = resultIndex+3; j >= resultIndex ; j--) {
				result [j] = (byte) digits[(allBits & 0x3f)];	// Bottom 6 bits
				allBits = allBits >>> 6;
			}
			resultIndex += 4;	// processed 4 result bytes
		}
		// Now we do the extra bytes in case the original (non-encoded) data
		// is not multiple of 4 bytes
		switch (extraBytes) {
			case 1:
					allBits = data[dataIndex++];	// actual byte
					allBits = allBits << 8; // 8 bits of zeroes
					allBits = allBits << 8; // 8 bits of zeroes
					// Loop 4 times generating output bits (4 * 6 = 24)
					for (int j = resultIndex+3; j >= resultIndex ; j--) {
						result [j] = (byte) digits[(allBits & 0x3f)];	// Bottom 6 bits
						allBits = allBits >>> 6;
					}
					// 2 pad tags
					result[result.length-1] = (byte)'=';
					result[result.length-2] = (byte)'=';
					break;
			case 2:
					allBits = data[dataIndex++]; // actual byte
					allBits = (allBits << 8) | (data[dataIndex++] & 0xff);	// actual byte
					allBits = allBits << 8; // 8 bits of zeroes
					// Loop 4 times generating output bits (4 * 6 = 24)
					for (int j = resultIndex+3; j >= resultIndex ; j--) {
						result [j] = (byte) digits[(allBits & 0x3f)];	// Bottom 6 bits
						allBits = allBits >>> 6;
					}
					// 1 pad tag
					result[result.length-1] = (byte)'=';
					break;
		}
		return result;
	}
	
	/**
	 * This method encodes the string in base 64 according to
	 * the specification given by the RFC 1521 (5.2).
	 * @return java.lang.String		the string needs to be encoded
	 * @param s java.lang.String	the encoded string
	 */
	public static String encodeBASE64(String s) {
		return new String(encodeBASE64(s.getBytes()));
	}
	
	/**
	 * This method decodes the byte array in base 64 encoding into a char array
	 * Base 64 encoding has to be according to the specification given by the RFC 1521 (5.2).
	 * @param  byte[]	data	the encoded byte array
	 * @return char[]			the decoded byte array
	 */
	public static byte[] decodeBASE64(byte[] data) {
		int lastRealDataIndex;
		for (lastRealDataIndex = data.length - 1; data[lastRealDataIndex] == equalSign; lastRealDataIndex--);
		// original data digit is 8 bits long, but base64 digit is 6 bits long
		int padBytes = data.length - 1 - lastRealDataIndex;
		int byteLength = data.length * 6 / 8 - padBytes;
		byte[] result = new byte[byteLength];
		// Each 4 bytes of input (encoded) we end up with 3 bytes of output
		int dataIndex = 0;
		int resultIndex = 0;
		int allBits = 0;
		// how many result chunks we can process before getting to pad bytes
		int resultChunks =  (lastRealDataIndex + 1) / 4;
		for (int i=0; i < resultChunks; i++) {
			allBits = 0;
			// Loop 4 times gathering input bits (4 * 6 = 24)
			for (int j = 0; j < 4; j++) {
				allBits = (allBits << 6) | decodeDigitBASE64 (data[dataIndex++]);
			}
	
			// Loop 3 times generating output bits (3 * 8 = 24)
			for (int j = resultIndex+2; j >= resultIndex ; j--) {
				result [j] = (byte) (allBits & 0xff);	// Bottom 8 bits
				allBits = allBits >>> 8;
			}
			resultIndex += 3;	// processed 3 result bytes
		}
		// Now we do the extra bytes in case the original (non-encoded) data
		// was not multiple of 3 bytes
	
		switch (padBytes) {
			case 1	: 	// 1 pad byte means 3 (4-1) extra Base64 bytes of input, 18 bits, of which only 16 are meaningful
						// Or: 2 bytes of result data
						allBits = 0;
						// Loop 3 times gathering input bits
						for (int j = 0; j < 3; j++) {
							allBits = (allBits << 6) | decodeDigitBASE64 (data[dataIndex++]);
						}
						// NOTE - The code below ends up being equivalent to allBits = allBits>>>2
						// But we code it in a non-optimized way for clarity
	
						// The 4th, missing 6 bits are all 0
						allBits = allBits << 6;
	
						// The 3rd, missing 8 bits are all 0
						allBits = allBits >>> 8;
						// Loop 2 times generating output bits
						for (int j = resultIndex+1; j >= resultIndex ; j--) {
							result [j] = (byte) (allBits & 0xff);	// Bottom 8 bits
							allBits = allBits >>> 8;
						}
						break;
	
			case 2	: 	// 2 pad bytes mean 2 (4-2) extra Base64 bytes of input, 12 bits of data, of which only 8 are meaningful
						// Or: 1 byte of result data
						allBits = 0;
						// Loop 2 times gathering input bits
						for (int j = 0; j < 2; j++) {
							allBits = (allBits << 6) | decodeDigitBASE64 (data[dataIndex++]);
						}
						// NOTE - The code below ends up being equivalent to allBits = allBits>>>4
						// But we code it in a non-optimized way for clarity
	
						// The 3rd and 4th, missing 6 bits are all 0
						allBits = allBits << 6;
						allBits = allBits << 6;
	
						// The 3rd and 4th, missing 8 bits are all 0
						allBits = allBits >>> 8;
						allBits = allBits >>> 8;
						result [resultIndex] = (byte) (allBits & 0xff);	// Bottom 8 bits
						break;
		}
		return result;
	}
	
	/**
	 * This method converts a Base 64 digit to its numeric value.
	 * @param  byte		data	digit (character) to convert
	 * @return int		value for the digit
	 */
	private static int decodeDigitBASE64 (byte data) {
		char charData = (char) data;
		if (charData <= 'Z' && charData >= 'A')
			return (int) (charData - 'A');
	
		if (charData <= 'z' && charData >= 'a')
			return (int) (charData - 'a' + 26);
		if (charData <= '9' && charData >= '0')
			return (int) (charData - '0' + 52);
	
		switch (charData) {
			case '+'	: return 62;
			case '/'	: return 63;
			default: throw new IllegalArgumentException();
		}
	}
	
	/**
	 * This method decodes the string in base 64 according to
	 * the specification given by the RFC 1521.
	 * @param s java.lang.String	the encoded string
	 * @return java.lang.String		the decoded string
	 */
	public static byte[] decodeBASE64(String s) {
		return decodeBASE64(s.getBytes());
	}
	
	/**
	 * Encodes the given char array in UTF8.
	 * @param value. The char array to encode.
	 * @param offset. The position to start in the array
	 * @param count. The number of cahr to encode.
	 * @return byte[]
	 */
	public static byte[] encodeUTF8(char[] value, int offset, int count) {
		int total = 0;
		for (int i=offset+count; --i >= offset;) {
			char ch = value[i];
			if (ch < 0x80) total++;
			else if (ch < 0x800) total += 2;
			else if (ch >= 0xdc00 && ch < 0xe000 && i > 0 &&
				value[i-1] >= 0xd800 && value[i-1] < 0xdc00)
			{
				i--;
				total += 4;
			} else total += 3;
		}
		byte[] result = new byte[total];
		int pos = result.length;
		for (int i=offset+count; --i >= offset;) {
			char ch = value[i];
			if (ch < 0x80) result[--pos] = (byte)ch;
			else if (ch < 0x800) {
				result[--pos] = (byte)(0x80 | (ch & 0x3f));
				result[--pos] = (byte)(0xc0 | (ch >> 6));
			} else if (ch >= 0xdc00 && ch < 0xe000 && i > 0 &&
				value[i-1] >= 0xd800 && value[i-1] < 0xdc00)
			{
				int temp = (value[i-1] & 0x3c0) + 0x40;
				result[--pos] = (byte)(0x80 | (ch & 0x3f));
				result[--pos] = (byte)(0x80 | ((ch >> 6) & 0xf) |
					((value[i-1] & 3) << 4));
				result[--pos] = (byte)(0x80 | ((value[i-1] >> 2) & 0xf) |
					((temp >> 2) & 0x30));
				result[--pos] = (byte)(0xf0 | (temp >> 8));
				i--;
			} else {
				result[--pos] = (byte)(0x80 | (ch & 0x3f));
				result[--pos] = (byte)(0x80 | ((ch >> 6) & 0x3f));
				result[--pos] = (byte)(0xe0 | (ch >> 12));
			}
		}
		return result;
	}
	
	/**
	 * Decodes the given UTF8 byte array.
	 * @param buf. The buffer to decode.
	 * @param out. The reult
	 * @param utfSize. The number of bytes to decode.
	 * @return String
	 * @throws IOException
	 */
	public static String decodeUTF8(byte[] buf, char[] out, int utfSize) throws IOException {
		int count = 0, s = 0, a;
		while (count < utfSize) {
			if ((out[s] = (char)buf[count++]) < '\u0080') s++;
			else if (((a = out[s]) & 0xe0) == 0xc0) {
				if (count >= utfSize)
					throw new IOException();
				int b = buf[count++];
				if ((b & 0xC0) != 0x80)
					throw new IOException();
				out[s++] = (char) (((a & 0x1F) << 6) | (b & 0x3F));
			} else if ((a & 0xf0) == 0xe0) {
				if (count+1 >= utfSize)
					throw new IOException();
				int b = buf[count++];
				int c = buf[count++];
				if (((b & 0xC0) != 0x80) || ((c & 0xC0) != 0x80))
					throw new IOException();
				out[s++] = (char) (((a & 0x0F) << 12) | ((b & 0x3F) << 6) | (c & 0x3F));
			} 
			else
				throw new IOException();
		}
		return new String(out, 0, s);
	}
}