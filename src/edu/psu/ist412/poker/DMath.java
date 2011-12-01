package edu.psu.ist412.poker;

/**
 * A kind of extension of the Math object.
 * DMath is short for "Discrete" Mathematics operations
 * used for calculating permutation and combinations of 
 * collections of integer sizes.
 * @author KennedyBD
 *
 */
public abstract class DMath {

	/**
	 * Calculate the permutation of n items, same 
	 * as the nPn annotation.
	 * 
	 * @param n
	 * @return
	 */
	public static final long permutation (int n){
		return permutation(n,n);
	}
	
	/**
	 * Calculate the purmutation of n items choose r,
	 * same as the nPr notation.
	 * @param n
	 * @param r
	 * @return
	 */
	public static final long permutation (int n, int r){
		long result;
		long numerator = 1;
		for (int i=n;i>=(n-r+1);i--){
			numerator = ((long) numerator) * ((long) i);
		}
		result = numerator;
		return result;
	}
	
	/**
	 * Calculate the combination of n items choose r,
	 * same as the nCr notation.
	 * @param n
	 * @param r
	 * @return
	 */
	public static final long combination(int n, int r){
		long result;
		long numerator = 1;
		for (int i=n;i>=(n-r+1);i--){
			numerator = ((long) numerator) * ((long) i);
		}
		long denominator = 1;
		for (int i=r;i>=(1);i--){
			denominator = ((long) denominator) * ((long) i);
		}
		result = numerator/denominator;
		return result;
	}
	
	/**
	 * Calculates the factorial of then number n,
	 * n! in mathematics notation.
	 * @param n
	 * @return
	 */
	public static final long factorial(int n){
		long r = 1;
		for (int i=1;i<=n;i++){
			r = r*((long) i);
		}
		return r;
	}
}
