package net.lenwe.aurora.gaussian.spectrum;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import Jampack.Eig;
import Jampack.JampackException;
import Jampack.Rand;
import Jampack.Times;
import Jampack.Zdiagmat;
import Jampack.Zmat;
import java.lang.Math;

class Eigenvalues implements Runnable {
	static int n;
	static CountDownLatch doneSignal;
	int repeats = 0;
	private int no;

	public Eigenvalues(int dimmension, int number) throws InterruptedException {
		this(dimmension, number, Runtime.getRuntime().availableProcessors());
	}

	private Eigenvalues(int dimmension, int number, int threads)
			throws InterruptedException {
		doneSignal = new CountDownLatch(threads);
		System.err.println("CountDownLatch: " + doneSignal.getCount());
		Eigenvalues.n = dimmension;
		for (int i = 0; i < threads; i++) {
			System.err.println("Spawning thread " + i);
			Eigenvalues e = new Eigenvalues(i, (int) number / threads, true);
			new Thread(e).start();
		}
		doneSignal.await();
	}

	private Eigenvalues(int no, int repeats, boolean t) {
		this.no = no;
		this.repeats = repeats;
	}

	@SuppressWarnings("unused")
	private Eigenvalues() {
	}

	public void run() {
		System.err.println("Thread no. " + no + " spawned.");
		while (repeats-- > 0) {
			System.err.println("Thread no. " + no + ", " + repeats
					+ " remaining.");
			try {
				Zmat zmat = new Zmat(Rand.nzmat(n, n));
				Eig eig = new Eig(zmat);
				for (int i = 1; i <= n; i++) {
					Spectrum.listOfEigenvalues.add(eig.D.get(i));
				}
				Zmat num = Times.aah(zmat);
				Zdiagmat gammasDiag = (new Eig(num)).D;
				List<Double> gammas = new ArrayList<>();
				for (int i = 1; i < gammasDiag.n; i++) {
					gammas.add(gammasDiag.get(i).re);
				}
				Kappas.add(Math.sqrt(Collections.max(gammas)
						/ Collections.min(gammas)));
			} catch (JampackException e) {
				repeats++;
			}
		}
		System.err.println("Thread no. " + no + " exiting.");
		doneSignal.countDown();
	}

}
