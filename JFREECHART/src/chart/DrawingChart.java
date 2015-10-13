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
 
		
		JFreeChart chart = ChartFactory.createBarChart3D(title, "單位", "比率",
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
			// 把圖片存到硬碟的相對位置
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

		
		// 配置字體
		Font xfont = new Font("細明體", Font.PLAIN, 16); // X軸
		Font yfont = new Font("細明體", Font.PLAIN, 16); // Y軸
		Font kfont = new Font("細明體", Font.PLAIN, 16); // 底部
		Font titleFont = new Font("細明體", Font.BOLD, 25); // 圖片標題
		CategoryPlot plot = chart.getCategoryPlot(); // 圖形的繪製結構對象

		// 圖片標題
		chart.setTitle(new TextTitle(chart.getTitle().getText(), titleFont));

		// 底部
		chart.getLegend().setItemFont(kfont);

		// X 軸
		CategoryAxis domainAxis = plot.getDomainAxis();
		domainAxis.setLabelFont(xfont); // 軸標題
		domainAxis.setTickLabelFont(xfont); // 軸數值
		domainAxis.setTickLabelPaint(Color.BLUE); // 字體顏色
//		domainAxis.setCategoryLabelPositions(CategoryLabelPositions.); // 橫軸上的label斜顯示


		// Y 軸
		ValueAxis rangeAxis = plot.getRangeAxis();
		rangeAxis.setLabelFont(yfont);
		rangeAxis.setLabelPaint(Color.BLUE); // 字體顏色
		rangeAxis.setTickLabelFont(yfont);

	}
	


}
