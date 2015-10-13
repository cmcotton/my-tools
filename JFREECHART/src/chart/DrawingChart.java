package chart;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileOutputStream;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;

import util.Property;

public class DrawingChart {
	
	static 

	public void draw(String title, ChartContent.CHART type) {
		
		String releaseMode = Property.getProperty("release_mode");
		ChartContent content = null;
		if ("false".equals(releaseMode)) {
			content = new TestContent();
		} else {
			if (type.equals(ChartContent.CHART.click)) {
				content = new ClickContent();
			} else if (type.equals(ChartContent.CHART.repeat)) {
				content = new RepeatContent();
			}
		}
		
		CategoryDataset mDataset = content.getDataset();
 
		
		JFreeChart chart = ChartFactory.createBarChart3D(title, "���", "��v",
				mDataset, PlotOrientation.HORIZONTAL, // 
				true, //
				false, //
				false); //
		
		configFont(chart);

		
		exportJPG(chart, title);
	}

	private static void exportJPG(JFreeChart chart, String fileName) {
		FileOutputStream fos_jpg = null;

		try {
			// ��Ϥ��s��w�Ъ��۹��m
			File destDir = new File("d:\\");
			if (!destDir.exists()) {
				destDir.mkdir();
			}
			fos_jpg = new FileOutputStream(destDir.getAbsolutePath() + "/" + fileName + ".jpg");
			ChartUtilities.writeChartAsJPEG(fos_jpg, 0.99f, chart, 800, 800,
					null);

			fos_jpg.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	private static void configFont(JFreeChart chart) {

		
		// �t�m�r��
		Font xfont = new Font("�ө���", Font.PLAIN, 16); // X�b
		Font yfont = new Font("�ө���", Font.PLAIN, 16); // Y�b
		Font kfont = new Font("�ө���", Font.PLAIN, 16); // ����
		Font titleFont = new Font("�ө���", Font.BOLD, 25); // �Ϥ����D
		CategoryPlot plot = chart.getCategoryPlot(); // �ϧΪ�ø�s���c��H

		// �Ϥ����D
		chart.setTitle(new TextTitle(chart.getTitle().getText(), titleFont));

		// ����
		chart.getLegend().setItemFont(kfont);

		// X �b
		CategoryAxis domainAxis = plot.getDomainAxis();
		domainAxis.setLabelFont(xfont); // �b���D
		domainAxis.setTickLabelFont(xfont); // �b�ƭ�
		domainAxis.setTickLabelPaint(Color.BLUE); // �r���C��
//		domainAxis.setCategoryLabelPositions(CategoryLabelPositions.); // ��b�W��label�����


		// Y �b
		ValueAxis rangeAxis = plot.getRangeAxis();
		rangeAxis.setLabelFont(yfont);
		rangeAxis.setLabelPaint(Color.BLUE); // �r���C��
		rangeAxis.setTickLabelFont(yfont);

	}
	


}
