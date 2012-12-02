package net.lenwe.aurora.gaussian.spectrum;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Kappas {
	public static List<Double> listOfKappas = Collections.synchronizedList(new ArrayList<Double>());
	
	public static void add(Double a){
		listOfKappas.add(a);
	}
	
	public static Double getAvg(){
		double averageK = (double) 0;
		for(double i: listOfKappas){
			averageK += i;
		}
		averageK /= listOfKappas.size();
		return averageK;
	}
}
