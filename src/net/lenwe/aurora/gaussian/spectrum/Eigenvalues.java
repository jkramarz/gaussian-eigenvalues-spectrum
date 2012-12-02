package net.lenwe.aurora.gaussian.spectrum;

import java.util.concurrent.CountDownLatch;
import Jampack.Eig;
import Jampack.JampackException;
import Jampack.Rand;
import Jampack.Times;
import Jampack.Z;
import Jampack.Zmat;

class Eigenvalues implements Runnable{
	static int n;
	static CountDownLatch doneSignal;
	int repeats = 0;
	private int no;

	public Eigenvalues(int dimmension, int number) throws InterruptedException{
		this(
			dimmension,
			number, 
			Runtime.getRuntime().availableProcessors() * 2
		);
	}
	
	private Eigenvalues(int dimmension, int number, int threads) throws InterruptedException{
		doneSignal = new CountDownLatch(threads);
		System.err.println("CountDownLatch: " + doneSignal.getCount());
		Eigenvalues.n=dimmension;
		for(int i = 0; i < threads; i++){
			System.err.println("Spawning thread " + i);
			Eigenvalues e = new Eigenvalues(i, (int) number/threads, true);
			new Thread(e).start();
		}
		doneSignal.await();
	}
	private Eigenvalues(int no, int repeats, boolean t){
		this.no = no;
		this.repeats = repeats;
	}
	
	@SuppressWarnings("unused")
	private Eigenvalues(){}

	public void run(){
		System.err.println("Thread no. " + no + " spawned.");
		while(repeats-- > 0){
			System.err.println("Thread no. " + no + ", " + repeats + " remaining.");
			try{
				Zmat zmat = new Zmat(Rand.nzmat(n, n));
				Eig eig = new Eig(zmat);
				for(int i = 1; i <= n; i++){
					Spectrum.listOfEigenvalues.add(eig.D.get(i));
				}
				Eig kapp = new Eig(Times.aah(zmat));
				double maxDouble = Double.MIN_VALUE, minDouble = Double.MAX_VALUE;
				Z maxZ = null, minZ = null;
				for(int i = 1; i < kapp.D.dx; i++){
					Z tZ = kapp.D.get(i);
					double tDouble = Z.abs(tZ);
					if(tDouble > maxDouble){
						maxZ = tZ;
						maxDouble = tDouble;
					}
					if(tDouble < minDouble){
						minZ = tZ;
						minDouble = tDouble;
					}
				}
				Z K = new Z().Div(maxZ,  minZ);
				
				//System.err.println("K: (" + K.re + "," +K.im + ") " + "maxZ: (" + maxZ.re + "," +maxZ.im + ") " + "minZ: (" + minZ.re + "," +minZ.im + ")");
				Spectrum.listOfKappas.add(Math.sqrt(Z.abs(K)));
			}catch(JampackException e){
				repeats++;
			}
		}
		System.err.println("Thread no. " + no + " exiting.");
		doneSignal.countDown();
	}

}
