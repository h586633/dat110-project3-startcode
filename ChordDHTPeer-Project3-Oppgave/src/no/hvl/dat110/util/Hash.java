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
				
				MessageDigest md = null;
				try {
					md = MessageDigest.getInstance("MD5");
				} catch (NoSuchAlgorithmException e) {
					System.out.println("No such algorithm found! " + e);
				}
				byte[] bytes = entity.getBytes("UTF-8");
				byte[] digest = md.digest(bytes);
				hashint = new BigInteger (toHex(digest), 16);
				
				return hashint;
	}
	
	public static BigInteger addressSize() throws NoSuchAlgorithmException {
		
				// Task: compute the address size of MD5
		
				// get the digest length
				
				// compute the number of bits = digest length * 8
				
				// compute the address size = 2 ^ number of bits
				
				// return the address size
				
				MessageDigest md = null;
				try {
					md = MessageDigest.getInstance("MD5");
				} catch (NoSuchAlgorithmException e) {
					System.out.println("No such algorithm found! " + e);
				}
				int dLength = md.getDigestLength() * 8;
				BigInteger addressSize = BigInteger.valueOf((long)Math.pow(2, dLength));
				return addressSize;
	}
	
	public static int bitSize() throws NoSuchAlgorithmException {
		
		int digestlen = 0;
		
		// find the digest length
		
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			System.out.println("No such algorithm found! " + e);
		}
		digestlen = md.getDigestLength();
		
		return digestlen*8;
	}
	
	public static String toHex(byte[] digest) {
		StringBuilder strbuilder = new StringBuilder();
		for(byte b : digest) {
			strbuilder.append(String.format("%02x", b&0xff));
		}
		return strbuilder.toString();
	}

}
