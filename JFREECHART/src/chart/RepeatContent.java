package chart;

import java.io.File;

import org.jfree.data.category.CategoryDataset;

import excel.ExcelDataReader;

public class RepeatContent implements ChartContent {
	

	@Override
	public CategoryDataset getDataset() {
		
		ExcelDataReader e = new ExcelDataReader();
		File oriFile = new File("d:/TestAwareness/(��l�U�����ɮ�)����B�I��M��-�_�Ϲq�H�����q.xls");
		return e.composeRepeatDataSet(oriFile);

	}

}
