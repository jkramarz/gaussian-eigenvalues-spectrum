package net.lenwe.aurora.gaussian.spectrum;

import java.util.List;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.FastScatterPlot;
import org.jfree.ui.ApplicationFrame;

public class EigenChart extends ApplicationFrame {
	private static final long serialVersionUID = 1L;

	public EigenChart(final String title, List<Jampack.Z> list) {
		super(title);
		populateData(list);
		JFreeChart chart = new JFreeChart(title, new FastScatterPlot(
				populateData(list), new NumberAxis("Real"), new NumberAxis(
						"Imaginary")));
		ChartPanel panel = new ChartPanel(chart, true);
		panel.setPreferredSize(new java.awt.Dimension(700, 700));
		setContentPane(panel);

	}

	private float[][] populateData(List<Jampack.Z> list) {
		float data[][] = new float[2][list.size()];
		for (int i = 0; i < list.size(); i++) {
			data[0][i] = (float) list.get(i).re;
			data[1][i] = (float) list.get(i).im;
		}
		return data;
	}

}
