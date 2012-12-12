package net.lenwe.aurora.gaussian.spectrum;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jfree.ui.RefineryUtilities;

import Jampack.Z;

public class Spectrum {
	public static List<Z> listOfEigenvalues = Collections
			.synchronizedList(new ArrayList<Z>());

	public static void main(String[] args) throws Exception {
		switch (args.length) {
		case 2:
			compute(Integer.parseInt(args[0]), Integer.parseInt(args[1]), false);
			break;
		case 3:
			compute(Integer.parseInt(args[0]), Integer.parseInt(args[1]), true);
			break;
		default:
			usage();
			break;
		}
	}

	private static void compute(int dim, int n, boolean save)
			throws InterruptedException {
		new Eigenvalues(dim, n);

		EigenChart chart = new EigenChart("Eigen values for " + n
				+ " Gaussian " + dim + "x" + dim + " matrices, kappa="
				+ Kappas.getAvg(), listOfEigenvalues);
		chart.pack();
		RefineryUtilities.centerFrameOnScreen(chart);
		System.err.println("Chart created.");
		chart.setVisible(true);

		EigenHistogram histogram = new EigenHistogram("Histogram",
				listOfEigenvalues);
		histogram.pack();
		RefineryUtilities.centerFrameOnScreen(histogram);
		histogram.setVisible(true);

		if (save) {
			try {
				writeFile();
			} catch (IOException e) {
			}
			return;
		}
	}

	private static void writeFile() throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(new File(
				"eigenvalues.dat")));
		for (Z ev : listOfEigenvalues) {
			writer.write(ev.re + "\t" + ev.im);
			writer.newLine();
		}
		writer.flush();
		writer.close();
	}

	private static void usage() {
		System.out
				.println("usage: java filename matrices_dimmension number_of_matrices [saveToFile]");
		return;
	}
}
