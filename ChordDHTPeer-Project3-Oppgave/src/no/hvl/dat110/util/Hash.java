package no.hvl.dat110.util;

/**
 * project 3
 * @author tdoy
 *
 */

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class Hash { 
	
	private static BigInteger hashint; 
	
	public static BigInteger hashOf(String entity) throws NoSuchAlgorithmException, UnsupportedEncodingException {		
		
		// Task: Hash a given string using MD5 and return the result as a BigInteger.
		
		// we use MD5 with 128 bits digest
		
		// compute the hash of the input 'entity'
		
		// convert the hash into hex format
		
		// convert the hex into BigInteger
		
		// return the BigInteger
		
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] bytesOfMessage = entity.getBytes("UTF-8");
			byte[] theDigest = md.digest(bytesOfMessage);
			
			hashint = new BigInteger(toHex(theDigest), 16);
		
			return hashint;
	}
	
	public static BigInteger addressSize() throws NoSuchAlgorithmException {
		
		// Task: compute the address size of MD5
		
		// get the digest length
		
		// compute the number of bits = digest length * 8
		
		// compute the address size = 2 ^ number of bits
		
		// return the address size
		
		MessageDigest md = MessageDigest.getInstance("MD5");
		int digestLength = md.getDigestLength();
		int numberOfBits = digestLength * 8;
		int addressSize = 2 ^ numberOfBits;
		BigInteger addressSizeBigInt = BigInteger.valueOf(addressSize);
		
		
		return addressSizeBigInt;
	}
	
	public static int bitSize() throws NoSuchAlgorithmException {
		
		MessageDigest md = MessageDigest.getInstance("MD5");
		int digestLength = md.getDigestLength();
		
		// find the digest length
		
		return digestLength*8;
	}
	
	public static String toHex(byte[] digest) {
		StringBuilder strbuilder = new StringBuilder();
		for(byte b : digest) {
			strbuilder.append(String.format("%02x", b&0xff));
		}
		return strbuilder.toString();
	}

}
