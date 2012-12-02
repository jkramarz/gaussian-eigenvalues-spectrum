package net.lenwe.aurora.conjugatetranspose;

import Jampack.JampackException;
import Jampack.Times;
import Jampack.Zmat;
import Jampack.Z;

public class ConjugateTransposeTimes extends Times {

	private ConjugateTransposeTimes(){}
	
	public static Zmat Transpose(Zmat a) throws JampackException{
		Zmat result = new Zmat(a.nr, a.nc);
		for(int i = 1; i <= a.nc; i++){
			for(int j = 1; j <= a.nr; j++){
				result.put(j, i, a.get(i,  j));
			}
		}
		return result;
	}
	
	public static Zmat Conjugate(Zmat a) throws JampackException{
		Zmat result = new Zmat(a.nc, a.nr);
		for(int i = 1; i <= a.nc; i++){
			for(int j = 1; j <= a.nr; j++){
				result.put(i, j, new Z().Conj(a.get(i,  j)));
			}
		}
		return result;
	}
	
	public static Zmat ConjugateTranspose(Zmat a) throws JampackException{
		return Conjugate(Transpose(a));
	}
	
	public static Zmat o(Zmat a, Zmat b) throws JampackException{
		if (a.nc != b.nr)
	         throw new JampackException("Unconformity in product");
		
		Zmat c = new Zmat(a.nr, b.nc);
		for(int i = 1; i <= c.nr; i++){
			for(int j = 1; j <= c.nc; j++){
				Z field = new Z();
				for(int r = 1; r <= a.nc; r++){
					Z product = new Z();
					product.Times(a.get(i, r), b.get(r, j));
					Z field2 = new Z(field.re + product.re, field.im + product.im);
					field = field2;
				}
				c.put(i, j, field);
			}
		}
		
		return c;
		
	}
}
