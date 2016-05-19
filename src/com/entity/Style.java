package com.entity;

import org.docx4j.wml.JcEnumeration;
import org.docx4j.wml.STVerticalJc;

import com.base.GlobalDocx;

public class Style {
	//中文字体
	public String cnFontFamily;
	//英文字体
	public String enFontFamily;
	//字体大小
	public String fontSize;
	//字体颜色
	public String fontColor;
	//垂直对齐方式
	public STVerticalJc vertical;
	//水平对齐方式
	public JcEnumeration horizontal;
	//列宽
	public int width;
	//列高
	public int high;
	//占行(合并单元格)
	public int vcol;
	//占列(合并单元格)
	public int hcol;
	//是否加粗
	public boolean isBold;
	
	public Style(){
		this.setFontColor(null);
		this.setFontSize(GlobalDocx.colFontSize);
		this.setBold(false);
		this.setCnFontFamily(GlobalDocx.cnFontFamily);
		this.setEnFontFamily(GlobalDocx.enFontFamily);
		this.setHorizontal(GlobalDocx.horizontal_left);
		this.setVertical(GlobalDocx.vertical_center);
		//this.setWidth(Global.width);
		this.setVcol(1);
		this.setHcol(1);
	}
	
	public String getCnFontFamily() {
		return cnFontFamily;
	}
	public void setCnFontFamily(String cnFontFamily) {
		this.cnFontFamily = cnFontFamily;
	}
	public String getEnFontFamily() {
		return enFontFamily;
	}
	public void setEnFontFamily(String enFontFamily) {
		this.enFontFamily = enFontFamily;
	}
	public String getFontSize() {
		return fontSize;
	}
	public void setFontSize(String fontSize) {
		this.fontSize = fontSize;
	}
	public String getFontColor() {
		return fontColor;
	}
	public void setFontColor(String fontColor) {
		this.fontColor = fontColor;
	}
	
	public STVerticalJc getVertical() {
		return vertical;
	}
	public void setVertical(STVerticalJc vertical) {
		this.vertical = vertical;
	}
	public JcEnumeration getHorizontal() {
		return horizontal;
	}
	public void setHorizontal(JcEnumeration horizontal) {
		this.horizontal = horizontal;
	}
	public boolean isBold() {
		return isBold;
	}
	public void setBold(boolean isBold) {
		this.isBold = isBold;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHigh() {
		return high;
	}

	public void setHigh(int high) {
		this.high = high;
	}

	public int getVcol() {
		return vcol;
	}

	public void setVcol(int vcol) {
		this.vcol = vcol;
	}

	public int getHcol() {
		return hcol;
	}

	public void setHcol(int hcol) {
		this.hcol = hcol;
	}

	public String toString(){
		StringBuffer sb=new StringBuffer();
		sb.append("字体颜色："+this.fontColor+"\r");
		sb.append("字体大小："+this.fontSize+"\r");
		sb.append("英文字体："+this.enFontFamily+"\r");
		sb.append("中文字体："+this.cnFontFamily+"\r");
		sb.append("宽度："+this.width+"\r");
		sb.append("合并单元格数(行)："+this.vcol+"\r");
		sb.append("合并单元格数(列)："+this.hcol+"\r");
		sb.append("水平对齐方式："+this.horizontal.value()+"\r");
		sb.append("垂直对齐方式："+this.vertical.value()+"\r");
		sb.append("是否加粗："+this.isBold+"\r");
		return sb.toString();
	}
}
