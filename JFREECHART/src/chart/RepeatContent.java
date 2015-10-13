package chart;

import java.io.File;

import org.jfree.data.category.CategoryDataset;

import excel.ExcelDataReader;

public class RepeatContent implements ChartContent {
	

	@Override
	public CategoryDataset getDataset() {
		
		ExcelDataReader e = new ExcelDataReader();
		File oriFile = new File("d:/TestAwareness/(原始下載的檔案)附件、點選清單-北區電信分公司.xls");
		return e.composeRepeatDataSet(oriFile);

	}

}
