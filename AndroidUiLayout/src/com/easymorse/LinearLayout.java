package com.easymorse;

import android.app.Activity;
import android.os.Bundle;

/**
 * linearLayout是能够设置垂直和水平的属性，来排列所有的自元素所有的子元素都被堆放在其它元素之后，因此一个垂直列表的每一行只会有
 * 一个元素，而不管他们有多宽，而一个水平列表将会只有一个行高（高度为最高子元素的高度加上边框高度）。LinearLayout保持子元素之间的间隔以
 * 及互相对齐（相对一个元素的右对齐、中间对齐或者左对齐）。 LinearLayout还支持为单独的子元素指定weight
 * 。好处就是允许子元素可以填充屏幕上的剩余空间。这也避免了在一个大屏幕中，一串小对象挤 成一堆的情况，而是允许他们放大填充空白。子元素指定一个weight
 * 值，剩余的空间就会按这些子元素指定的weight 比例分配给这些子元素。默认的 weight 值为0。例如，如果有三个文本框，其中两个指定了weight
 * 值为1，那么，这两个文本框将等比例地放大，并填满剩余的空间，而第三个文本框 不会放大。
 * 
 * @author ubuntu
 * 
 */
public class LinearLayout extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.linearlayout);
	}

}
