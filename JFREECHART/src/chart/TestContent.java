package chart;

import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

public class TestContent implements ChartContent {
	

	@Override
	public CategoryDataset getDataset() {
		System.out.println("testing");
		DefaultCategoryDataset mDataset = new DefaultCategoryDataset();
		mDataset.addValue(2000, "點擊率", "檜計處");
		mDataset.addValue(873, "點擊率", "資訊處");

		return mDataset;
	}

}
