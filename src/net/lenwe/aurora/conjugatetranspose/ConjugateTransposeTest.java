/**
 * 
 */
package net.lenwe.aurora.conjugatetranspose;

import static org.junit.Assert.*;

import org.junit.Test;

import Jampack.Eig;
import Jampack.JampackException;
import Jampack.Rand;
import Jampack.Times;
import Jampack.Z;
import Jampack.Zmat;

/**
 * @author lenwe
 *
 */
public class ConjugateTransposeTest {

	
	@Test
	public void testComplexTimes(){
		Z a = new Z(1, 2);
		Z b = new Z(3, 4);
		Z result = new Z().Times(a, b);
		
		Z expected = new Z(-5, 10);
		
		assertTrue(result.re == expected.re);
		assertTrue(result.im == expected.im);
	}

	@Test
	public void testTranspose() throws JampackException {
		Zmat matrix = new Zmat(3, 3);
		for(int i = 1; i <= 3; i++){
			for(int j = 1; j <= 3; j++){
				matrix.put(i, j, new Z( (i-1)*3 + j, 0));
			}
		}
		Zmat expected_result = new Zmat(3, 3);
		expected_result.put(1, 1, new Z(14, 0));
		expected_result.put(1, 2, new Z(32, 0));
		expected_result.put(1, 3, new Z(50, 0));
		expected_result.put(2, 1, new Z(32, 0));
		expected_result.put(2, 2, new Z(77, 0));
		expected_result.put(2, 3, new Z(122, 0));
		expected_result.put(3, 1, new Z(50, 0));
		expected_result.put(3, 2, new Z(122, 0));
		expected_result.put(3, 3, new Z(194, 0));
		
		Zmat transposition = ConjugateTransposeTimes.Transpose(matrix);
		Zmat result = ConjugateTransposeTimes.o(matrix, transposition);
		for(int i = 1; i <= 3; i++){
			for(int j = 1; j <= 3; j++){
				assertTrue(expected_result.get(i, j).im == result.get(i, j).im);
				assertTrue(expected_result.get(i, j).re == result.get(i, j).re);
			}
		}
	}
		
		@Test
		public void testConjugateTranspose() throws JampackException {
			Zmat matrix = new Zmat(2, 2);
			matrix.put(1, 1, new Z(1, 1));
			matrix.put(1, 2, new Z(0, 0));
			matrix.put(2, 1, new Z(0, 0));
			matrix.put(2, 2, new Z(1, -1));
			
			Zmat expected_result = new Zmat(2, 2);
			expected_result.put(1, 1, new Z(2, 0));
			expected_result.put(1, 2, new Z(0, 0));
			expected_result.put(2, 1, new Z(0, 0));
			expected_result.put(2, 2, new Z(2, 0));
			
			Zmat transposition = ConjugateTransposeTimes.ConjugateTranspose(matrix);
			Zmat result = ConjugateTransposeTimes.o(matrix, transposition);
			for(int i = 1; i <= 2; i++){
				for(int j = 1; j <= 2; j++){
					assertTrue(expected_result.get(i, j).im == result.get(i, j).im);
					assertTrue(expected_result.get(i, j).re == result.get(i, j).re);
				}
			}
	
		
	}
		
		@Test
		public void testAah() throws JampackException {
			Zmat matrix = new Zmat(2, 2);
			matrix.put(1, 1, new Z(1, 1));
			matrix.put(1, 2, new Z(0, 0));
			matrix.put(2, 1, new Z(0, 0));
			matrix.put(2, 2, new Z(1, -1));
			
			Zmat expected_result = new Zmat(2, 2);
			expected_result.put(1, 1, new Z(2, 0));
			expected_result.put(1, 2, new Z(0, 0));
			expected_result.put(2, 1, new Z(0, 0));
			expected_result.put(2, 2, new Z(2, 0));
			
			Zmat result = Times.aah(matrix);
			for(int i = 1; i <= 2; i++){
				for(int j = 1; j <= 2; j++){
					assertTrue(expected_result.get(i, j).im == result.get(i, j).im);
					assertTrue(expected_result.get(i, j).re == result.get(i, j).re);
				}
			}
	
		
	}
		
	@Test
	public void testEigOfAah() throws JampackException{
		Zmat zmat = new Zmat(Rand.nzmat(3, 3));
		Zmat aah = Times.aah(zmat);
		Eig eig = new Eig(aah);
		for(int i = 1; i <= eig.D.n; i++ ){
			System.out.println(eig.D.get(i).im);
			assertTrue(eig.D.get(i).im < 0.00001);
		}
		
		
	}

}
