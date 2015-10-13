package chart;

import org.jfree.data.category.CategoryDataset;

public interface ChartContent {
	
	enum CHART {
		click, repeat;
	}
	
	CategoryDataset getDataset();

}
