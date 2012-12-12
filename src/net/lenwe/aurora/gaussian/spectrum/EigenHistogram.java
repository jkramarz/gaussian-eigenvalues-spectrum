package net.lenwe.aurora.gaussian.spectrum;

import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.statistics.HistogramType;
import org.jfree.ui.ApplicationFrame;

import Jampack.Z;

public class EigenHistogram extends ApplicationFrame {
	private static final long serialVersionUID = 1L;

	public EigenHistogram(final String title, List<Jampack.Z> list) {
		super(title);
		String plotTitle = "Histogram";
		String xaxis = "absolute value";
		String yaxis = "number of";
		PlotOrientation orientation = PlotOrientation.VERTICAL;
		boolean show = false;
		boolean toolTips = false;
		boolean urls = false;
		JFreeChart chart = ChartFactory.createHistogram(plotTitle, xaxis,
				yaxis, populateData(list), orientation, show, toolTips, urls);
		ChartPanel panel = new ChartPanel(chart, true);
		panel.setPreferredSize(new java.awt.Dimension(700, 700));
		setContentPane(panel);

	}

	private HistogramDataset populateData(List<Jampack.Z> list) {
		HistogramDataset dataset = new HistogramDataset();
		double[] value = new double[list.size()];
		dataset.setType(HistogramType.FREQUENCY);

		for (int i = 0; i < list.size(); i++) {
			value[i] = Z.abs(list.get(i));
		}

		int number = (int) Math.sqrt(list.size());
		dataset.addSeries("Eigenvalues", value, number);
		return dataset;

	}

}