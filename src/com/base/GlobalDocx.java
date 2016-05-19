package com.base;

import org.docx4j.wml.JcEnumeration;
import org.docx4j.wml.STVerticalJc;

import com.entity.Style;

public class GlobalDocx {
	//中文字体型号
	public final static String cnFontFamily="宋体";
	//英文字体型号
	public final static String enFontFamily="Times New Roman";
	//表头字体大小
	public final static String colFontSize="21";
	//表内容字体大小
	public final static String valFontSize="18";
	//颜色
	public final static String colorRed="FF0000";
	//垂直方向居中
	public final static STVerticalJc vertical_center=STVerticalJc.CENTER;
	//水平方向居中
	public final static JcEnumeration horizontal_center=JcEnumeration.CENTER;
	//水平方向居左
	public final static JcEnumeration horizontal_left=JcEnumeration.LEFT;
	//水平方向居右
	public final static JcEnumeration horizontal_right=JcEnumeration.RIGHT;
	//列宽
	public final static int width=2000;
	//行高
	public final static int high=20;
	//行间距
	public final static String spacing="80";
	//默认表风格
	public final static Style defaultStyle=new Style();
}
