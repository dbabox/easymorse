package com.easymorse;

/**
 * TableLayout 将子元素的位置分配到行或列中。一个TableLayout 由许多的TableRow 组成，每个TableRow 都会定义一个 row （事实上，你可以定义其它的子对象，这在下面会解释到）。TableLayout 容器不会显示row 、cloumns 或cell 的边框线。每个 row 拥有0个或多个的cell ；每个cell 拥有一个View 对象。表格由列和行组成许多的单元格。表格允许单元格为空。单元格不能跨列，这与HTML 中的不一样。
 */
import android.app.Activity;
import android.os.Bundle;

public class TableLayout extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tablelayout);
	}
}
