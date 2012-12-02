package net.lenwe.aurora.gaussian.spectrum;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.jfree.ui.RefineryUtilities;
import Jampack.Z;

public class Spectrum {
	public static List<Z> listOfEigenvalues = Collections.synchronizedList(new ArrayList<Z>());
	
	
	public static void main(String[] args) throws Exception {
		switch(args.length){
			case 2:
				compute(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
				break;
			default:
				usage();
				break;
		}
	}

	private static void compute(int dim, int n) throws InterruptedException {
			new Eigenvalues(
				dim,
				n
			);

			EigenChart chart = new EigenChart("Eigen values for " + n + " Gaussian " + dim + "x" + dim + " matrices, kappa=" + Kappas.getAvg(), listOfEigenvalues);	
			chart.pack();
	        RefineryUtilities.centerFrameOnScreen(chart);
	        System.err.println("Chart created.");
	        chart.setVisible(true);
	        
	        EigenHistogram histogram = new EigenHistogram("Histogram", listOfEigenvalues);
	        histogram.pack();
	        RefineryUtilities.centerFrameOnScreen(histogram);
	        histogram.setVisible(true);
	        
	        
	        
	        return;
	}

	private static void usage() {
		System.out.println("usage: java filename matrices_dimmension number_of_matrices");
		return;
	}
}
