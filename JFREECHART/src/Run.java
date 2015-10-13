import chart.ChartContent;
import chart.DrawingChart;


public class Run {

	public static void main(String[] args) {
		DrawingChart d = new DrawingChart();
		d.draw("演練結果統計表", ChartContent.CHART.click);
		
		d.draw("重覆點選表", ChartContent.CHART.repeat);
	}
	
}
