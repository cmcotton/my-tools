package chart;

import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

public class TestContent implements ChartContent {
	

	@Override
	public CategoryDataset getDataset() {
		System.out.println("testing");
		DefaultCategoryDataset mDataset = new DefaultCategoryDataset();
		mDataset.addValue(2000, "�I���v", "�̭p�B");
		mDataset.addValue(873, "�I���v", "��T�B");

		return mDataset;
	}

}
