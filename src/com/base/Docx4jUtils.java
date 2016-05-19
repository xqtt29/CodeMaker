package com.base;

import java.io.File;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.docx4j.model.properties.table.tr.TrHeight;
import org.docx4j.openpackaging.contenttype.ContentType;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.wml.BooleanDefaultTrue;
import org.docx4j.wml.CTHeight;
import org.docx4j.wml.CTVerticalJc;
import org.docx4j.wml.Color;
import org.docx4j.wml.HpsMeasure;
import org.docx4j.wml.Jc;
import org.docx4j.wml.JcEnumeration;
import org.docx4j.wml.P;
import org.docx4j.wml.PPr;
import org.docx4j.wml.PPrBase.PStyle;
import org.docx4j.wml.PPrBase.Spacing;
import org.docx4j.wml.R;
import org.docx4j.wml.RFonts;
import org.docx4j.wml.RPr;
import org.docx4j.wml.STLineSpacingRule;
import org.docx4j.wml.STVerticalJc;
import org.docx4j.wml.Tbl;
import org.docx4j.wml.TblPr;
import org.docx4j.wml.TblWidth;
import org.docx4j.wml.Tc;
import org.docx4j.wml.TcPr;
import org.docx4j.wml.TcPrInner.GridSpan;
import org.docx4j.wml.Text;
import org.docx4j.wml.CTTblPrBase.TblStyle;
import org.docx4j.wml.Tr;
import org.docx4j.wml.TrPr;

import com.entity.BOSS;
import com.entity.Style;
import com.service.DocxService;

public class Docx4jUtils {
	/**
	 * @Description
	 * 创建表格
	 * 
	 * @Author qiang.zhu
	 * @Datetime 2015年10月10日 下午1:34:05
	 * @Version
	 */
	public static Tbl createTbl(){
		Tbl tbl=new Tbl();
		TblPr tblPr = new TblPr();
	    TblStyle tblStyle = new TblStyle();
	    tblStyle.setVal("TableGrid");
	    tblPr.setTblStyle(tblStyle);
	    tbl.setTblPr(tblPr);
	    return tbl;
	}
	/**
	 * @Description
	 * 创建表列
	 * 
	 * @Author qiang.zhu
	 * @Datetime 2015年10月10日 下午1:34:05
	 * @Version
	 */
	public static Tr createTr(){
		Tr tr=new Tr();
		setTrHeight(tr, GlobalDocx.high);
	    return tr;
	}
	/**
	 * @Description
	 * 创建表行
	 * 
	 * @Author qiang.zhu
	 * @Datetime 2015年10月10日 下午1:34:05
	 * @Version
	 */
	public static Tc createTc(){
		Tc tc=new Tc();
		return tc;
	}
	/**
	 * @Description
	 * 设置表行内容
	 * 
	 * @Author qiang.zhu
	 * @Datetime 2015年10月10日 下午1:34:45
	 * @Version
	 */
	public static void setTcTest(Tc tc,String text){
		setTcTest(tc,text,GlobalDocx.defaultStyle);
	}
	/**
	 * @Description
	 * 设置表行内容(列，内容，颜色，中文字体，英文字体，是否加粗)
	 * 
	 * @Author qiang.zhu
	 * @Datetime 2015年10月10日 下午1:34:45
	 * @Version
	 */
	public static void setTcTest(Tc tc,String text,Style style){
		TcPr tcpr=tc.getTcPr();
		if(tcpr==null){
			tcpr=new TcPr();
			tc.setTcPr(tcpr);
		}
		P p = new P();
		Text t = new Text();
		t.setValue(text);
		R r = new R();
		RPr rpr=new RPr();
		setFontColor(rpr, style.fontColor);
		setFontFamily(rpr, style.cnFontFamily, style.enFontFamily);
		addBoldStyle(rpr,style.isBold);
		setHorizontalAlignment(p, style.horizontal);
		setVerticalAlignment(tc, style.vertical);
		setTcWidth(tc, style.width);
		setCellVMerge(tc, style.vcol);
		setCellHMerge(tc, style.hcol);
		setFontSize(rpr, style.fontSize);
		setParagraphSpacing(p, true, "0", GlobalDocx.spacing, "60", "0", true, GlobalDocx.spacing, STLineSpacingRule.AUTO);
		r.setRPr(rpr);
		r.getContent().add(t);
		p.getContent().add(r);
		tc.getContent().add(p);
	}
	/**
	 * @Description
	 * 设置列宽
	 * 
	 * @Author qiang.zhu
	 * @Datetime 2015年10月10日 下午1:33:38
	 * @Version
	 */
	public static void setTcWidth(Tc tc,int width){
		TcPr tcpr=tc.getTcPr();
		if(tcpr==null){
			tcpr=new TcPr();
			tc.setTcPr(tcpr);
		}
		TblWidth tw=new TblWidth();
		tw.setType("dxa");
		tw.setW(BigInteger.valueOf(width));
		tcpr.setTcW(tw);
	}
	/**
	 * @Description
	 * 设置行高
	 * 
	 * @Author qiang.zhu
	 * @Datetime 2015年10月10日 下午1:33:38
	 * @Version
	 */
	public static void setTrHeight(Tr tr, int heigth) {
	    TrPr trPr = tr.getTrPr();
	    if(trPr==null){
	    	trPr=new TrPr();
	    	tr.setTrPr(trPr);
	    }
	    CTHeight ctHeight = new CTHeight();
	    ctHeight.setVal(BigInteger.valueOf(heigth));
	    TrHeight trHeight = new TrHeight(ctHeight);
	    trHeight.set(trPr);
	  }
	/**
	 * @Description
	 * 合并单元格水平方向
	 * 
	 * @Author qiang.zhu
	 * @Datetime 2015年10月10日 下午1:33:19
	 * @Version
	 */
	public static void setCellVMerge(Tc tc,int vcol){
		TcPr tcpr=tc.getTcPr();
		if(tcpr==null){
			tcpr=new TcPr();
			tc.setTcPr(tcpr);
		}
		GridSpan gs=new GridSpan();
		gs.setVal(BigInteger.valueOf(vcol));
		tcpr.setGridSpan(gs);
	}
	/**
	 * @Description
	 * 合并单元格垂直方向
	 * 
	 * @Author qiang.zhu
	 * @Datetime 2015年10月10日 下午1:33:19
	 * @Version
	 */
	public static void setCellHMerge(Tc tc, int hcol) {
	    if (hcol > 1) {
	      TcPr tcp = tc.getTcPr();
	      if (tcp == null) {
	    	  tcp = new TcPr();
	        tc.setTcPr(tcp);
	      }
	      GridSpan gridSpan = new GridSpan();
	      gridSpan.setVal(BigInteger
	          .valueOf(hcol));
	      tcp.setGridSpan(gridSpan);
	    }
	  }
	/**
	 * @Description
	 * 设置垂直对齐方式
	 * 
	 * @Author qiang.zhu
	 * @Datetime 2015年10月10日 下午1:33:07
	 * @Version
	 */
	public static void setVerticalAlignment(Tc tc, STVerticalJc align) {
	    if (align != null) {
	      TcPr tcp = tc.getTcPr();
	      if (tcp == null) {
	    	  tcp = new TcPr();
	      	  tc.setTcPr(tcp);
	      }
	      CTVerticalJc valign = new CTVerticalJc();
	      valign.setVal(align);
	      tcp.setVAlign(valign);
	    }
	}
	/**
	 * @Description
	 * 设置水平对齐方式
	 * 
	 * @Author qiang.zhu
	 * @Datetime 2015年10月10日 下午1:32:39
	 * @Version
	 */
	public static void setHorizontalAlignment(P paragraph, JcEnumeration hAlign) {
	    if (hAlign != null) {
	      PPr ppr = new PPr();
	      Jc align = new Jc();
	      align.setVal(hAlign);
	      ppr.setJc(align);
	      paragraph.setPPr(ppr);
	    }
	}
	/**
	 * @Description
	 * 字体加粗
	 * 
	 * @Author qiang.zhu
	 * @Datetime 2015年10月10日 下午1:32:24
	 * @Version
	 */
	private static void addBoldStyle(RPr rpr,boolean isBold) {
	    BooleanDefaultTrue b = new BooleanDefaultTrue();
	    b.setVal(isBold);
	    rpr.setB(b);
	}
	/**
	 * @Description
	 * 设置字体颜色
	 * 
	 * @Author qiang.zhu
	 * @Datetime 2015年10月10日 下午1:31:58
	 * @Version
	 */
	private static void setFontColor(RPr runProperties, String color) {
	    if (color != null) {
	      Color c = new Color();
	      c.setVal(color);
	      runProperties.setColor(c);
	    }
	}
	/**
	 * @Description
	 * 设置字体类型(微软雅黑  Times New Roman)
	 * 
	 * @Author qiang.zhu
	 * @Datetime 2015年10月10日 下午1:30:53
	 * @Version
	 */
	private static void setFontFamily(RPr runProperties, String cnFontFamily,String enFontFamily) {
	    if (cnFontFamily != null||enFontFamily!=null) {
	      RFonts rf = runProperties.getRFonts();
	      if (rf == null) {
	        rf = new RFonts();
	        runProperties.setRFonts(rf);
	      }
	      if(cnFontFamily!=null){
	        rf.setEastAsia(cnFontFamily);
	      }
	      if(enFontFamily!=null){
	        rf.setAscii(enFontFamily);
	      }
	    }
	}
	/**
	 * @Description
	 * 段落格式
	 * 
	 * @Author qiang.zhu
	 * @Datetime 2015年10月10日 下午3:21:12
	 * @Version
	 */
	private static void setParagraphSpacing(P p, boolean isSpace, String before,
		      String after, String beforeLines, String afterLines,
		      boolean isLine, String lineValue,
		      STLineSpacingRule sTLineSpacingRule) {
	    PPr pPr = p.getPPr();
	    if (pPr == null) {
	    	pPr = new PPr();
	    	p.setPPr(pPr);
	    }
	    Spacing spacing = pPr.getSpacing();
	    if (spacing == null) {
	    	spacing = new Spacing();
	    	pPr.setSpacing(spacing);
	    }
	    if (isSpace) {
			if (before!=null&&before.length()!=0) {
				// 段前磅数
				spacing.setBefore(new BigInteger(before));
			}
			if (after!=null&&after.length()!=0) {
			    // 段后磅数
			    spacing.setAfter(new BigInteger(after));
			}
			if (beforeLines!=null&&beforeLines.length()!=0) {
			    // 段前行数
			    spacing.setBeforeLines(new BigInteger(beforeLines));
			}
			if (afterLines!=null&&afterLines.length()!=0) {
			    // 段后行数
			    spacing.setAfterLines(new BigInteger(afterLines));
			}
	    }
	    if (isLine) {
	    	if (lineValue!=null&&lineValue.length()!=0) {
	    		spacing.setLine(new BigInteger(lineValue));
	    	}
	    	if (sTLineSpacingRule != null) {
	    		spacing.setLineRule(sTLineSpacingRule);
	    	}
	    }
	}
	private static void setFontSize(RPr runProperties, String fontSize) {
	    if (fontSize != null && !fontSize.isEmpty()) {
	      HpsMeasure size = new HpsMeasure();
	      size.setVal(new BigInteger(fontSize));
	      runProperties.setSz(size);
	      runProperties.setSzCs(size);
	    }
	}
	public static void writeDocx(Map map,List<BOSS> inSingle,List<BOSS> inMutil,List<BOSS> outSingle,List<BOSS> outMutil){
		try {
			WordprocessingMLPackage wmp = WordprocessingMLPackage
					.createPackage();
			DocxService docxService = new DocxService();
			Tbl tbl = docxService.dom2Grid(map,inSingle, inMutil, outSingle, outMutil);
			P p=new P();
			PPr ppr=new PPr();
			PStyle ps=new PStyle();
			ps.setVal("2");
			ppr.setPStyle(ps);
			R r=new R();
			Text t = new Text();
			t.setValue(map.get(Global.INT_ECB_NAME).toString());
			r.getContent().add(t);
			p.setPPr(ppr);
			p.getContent().add(r);
			wmp.getMainDocumentPart().addObject(p);
			wmp.getMainDocumentPart().addObject(tbl);
			wmp.save(new File(map.get(Global.FILE_PATH).toString()+File.separator+map.get(Global.INT_ECB_NAME).toString()+".docx"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
