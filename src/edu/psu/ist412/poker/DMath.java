package edu.psu.ist412.poker;

public abstract class DMath {

	public static final long permutation (int n){
		return permutation(n,n);
	}
	
	public static final long permutation (int n, int r){
		long result;
		long numerator = 1;
		for (int i=n;i>=(n-r+1);i--){
			numerator = ((long) numerator) * ((long) i);
		}
		result = numerator;
		return result;
	}
	
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
	
	public static final long factorial(int n){
		long r = 1;
		for (int i=1;i<=n;i++){
			r = r*((long) i);
		}
		return r;
	}
}
