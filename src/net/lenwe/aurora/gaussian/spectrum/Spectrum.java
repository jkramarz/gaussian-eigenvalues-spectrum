package net.lenwe.aurora.gaussian.spectrum;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.jfree.ui.RefineryUtilities;
import Jampack.Z;

public class Spectrum {
	public static List<Z> list = Collections.synchronizedList(new ArrayList<Z>());

	public static void main(String[] args) throws Exception {
		int n = 1000;
		int dim = 111;
		new Eigenvalues(
			dim,
			n
		);
		System.err.println("Creating chart.");
		EigenChart chart = new EigenChart("Eigen values for " + n + " Gaussian " + dim + "x" + dim + " matrices.", list);
		chart.pack();
        RefineryUtilities.centerFrameOnScreen(chart);
        System.err.println("Chart created.");
        chart.setVisible(true);
	}
}
