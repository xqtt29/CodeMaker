package com.service;

import java.util.List;
import java.util.Map;

import org.docx4j.wml.Tbl;
import org.docx4j.wml.Tc;
import org.docx4j.wml.Tr;

import com.base.Docx4jUtils;
import com.base.Global;
import com.base.GlobalDocx;
import com.entity.BOSS;
import com.entity.Style;

public class DocxService {

	public Tbl dom2Grid(Map map,List<BOSS> inSingle,List<BOSS> inMutil,List<BOSS> outSingle,List<BOSS> outMutil){
		Tbl tbl=Docx4jUtils.createTbl();
		init1Row(tbl,map);
		init2Row(tbl);
		init3Row(tbl);
		initHeadRow(tbl);
		init4Row(tbl);
		if(inSingle.size()==0){
			initNullRow(tbl);
		}else{
			for(BOSS boss : inSingle){
				initRow(tbl,boss);
			}
		}
		init5Row(tbl);
		if(inMutil.size()==0){
			initNullRow(tbl);
		}else{
			for(BOSS boss : inMutil){
				initRow(tbl,boss);
			}
		}
		init6Row(tbl);
		initHeadRow(tbl);
		init7Row(tbl);
		initCommonRow(tbl);
		init8Row(tbl);
		if(outSingle.size()==0){
			initNullRow(tbl);
		}else{
			for(BOSS boss : outSingle){
				initRow(tbl,boss);
			}
		}
		init9Row(tbl);
		if(outMutil.size()==0){
			initNullRow(tbl);
		}else{
			for(BOSS boss : outMutil){
				initRow(tbl,boss);
			}
		}
		initEndRow(tbl);
		return tbl;
	}
	private void init1Row(Tbl tbl,Map map){
		Tr tr=Docx4jUtils.createTr();
		
		Tc tc1=new Tc();
		Docx4jUtils.setTcTest(tc1, "业务编码");
		tr.getContent().add(tc1);
		
		Tc tc2=new Tc();
		Style s=new Style();
		s.setVcol(4);
		Docx4jUtils.setTcTest(tc2, map.get(Global.INT_ECB_CODE+"_true").toString()+"  "+map.get(Global.INT_ECB_NAME).toString(),s);
		tr.getContent().add(tc2);
		
		tbl.getContent().add(tr);
	}
	private void init2Row(Tbl tbl){
		Tr tr=Docx4jUtils.createTr();
		
		Tc tc1=new Tc();
		Docx4jUtils.setTcTest(tc1, "功能说明");
		tr.getContent().add(tc1);
		
		Tc tc2=new Tc();
		Style s=new Style();
		s.setVcol(4);
		Docx4jUtils.setTcTest(tc2, "",s);
		tr.getContent().add(tc2);
		
		tbl.getContent().add(tr);
	}

	private void init3Row(Tbl tbl){
		Tr tr=Docx4jUtils.createTr();
		
		Tc tc=new Tc();
		Style s=new Style();
		s.setVcol(5);
		s.setHorizontal(GlobalDocx.horizontal_center);
		Docx4jUtils.setTcTest(tc, "输入列表参数",s);
		tr.getContent().add(tc);
		
		tbl.getContent().add(tr);
	}
	private void initHeadRow(Tbl tbl){
		Tr tr=Docx4jUtils.createTr();
		
		Tc tc1=new Tc();
		Style s1=new Style();
		s1.setWidth(2000);
		Docx4jUtils.setTcTest(tc1, "参数英文名",s1);
		tr.getContent().add(tc1);
		
		Tc tc2=new Tc();
		Style s2=new Style();
		s2.setWidth(2000);
		Docx4jUtils.setTcTest(tc2, "参数中文名",s2);
		tr.getContent().add(tc2);
		
		Tc tc3=new Tc();
		Style s3=new Style();
		s3.setWidth(1150);
		Docx4jUtils.setTcTest(tc3, "参数类型",s3);
		tr.getContent().add(tc3);
		
		Tc tc4=new Tc();
		Style s4=new Style();
		s4.setWidth(800);
		Docx4jUtils.setTcTest(tc4, "长度",s4);
		tr.getContent().add(tc4);
		
		Tc tc5=new Tc();
		Style s5=new Style();
		s5.setWidth(3000);
		Docx4jUtils.setTcTest(tc5, "备注",s5);
		tr.getContent().add(tc5);
		
		tbl.getContent().add(tr);
	}
	private void init4Row(Tbl tbl){
		Tr tr=Docx4jUtils.createTr();
		
		Tc tc=new Tc();
		Style s=new Style();
		s.setVcol(5);
		s.setHorizontal(GlobalDocx.horizontal_left);
		Docx4jUtils.setTcTest(tc, "单行输入参数",s);
		tr.getContent().add(tc);
		
		tbl.getContent().add(tr);
	}
	private void init5Row(Tbl tbl){
		Tr tr=Docx4jUtils.createTr();
		
		Tc tc=new Tc();
		Style s=new Style();
		s.setVcol(5);
		s.setHorizontal(GlobalDocx.horizontal_left);
		Docx4jUtils.setTcTest(tc, "多行输入参数",s);
		tr.getContent().add(tc);
		
		tbl.getContent().add(tr);
	}
	private void init6Row(Tbl tbl){
		Tr tr=Docx4jUtils.createTr();
		
		Tc tc=new Tc();
		Style s=new Style();
		s.setVcol(5);
		s.setHorizontal(GlobalDocx.horizontal_center);
		Docx4jUtils.setTcTest(tc, "输出列表参数",s);
		tr.getContent().add(tc);
		
		tbl.getContent().add(tr);
	}
	private void init7Row(Tbl tbl){
		Tr tr=Docx4jUtils.createTr();
		
		Tc tc=new Tc();
		Style s=new Style();
		s.setVcol(5);
		s.setHorizontal(GlobalDocx.horizontal_left);
		Docx4jUtils.setTcTest(tc, "公共输出参数",s);
		tr.getContent().add(tc);
		
		tbl.getContent().add(tr);
	}
	private void init8Row(Tbl tbl){
		Tr tr=Docx4jUtils.createTr();
		
		Tc tc=new Tc();
		Style s=new Style();
		s.setVcol(5);
		s.setHorizontal(GlobalDocx.horizontal_left);
		Docx4jUtils.setTcTest(tc, "单行输出参数",s);
		tr.getContent().add(tc);
		
		tbl.getContent().add(tr);
	}
	private void init9Row(Tbl tbl){
		Tr tr=Docx4jUtils.createTr();
		
		Tc tc=new Tc();
		Style s=new Style();
		s.setVcol(5);
		s.setHorizontal(GlobalDocx.horizontal_left);
		Docx4jUtils.setTcTest(tc, "多行输出参数",s);
		tr.getContent().add(tc);
		
		tbl.getContent().add(tr);
	}
	private void initRow(Tbl tbl,BOSS boss){
		Tr tr=Docx4jUtils.createTr();
		
		Tc tc1=new Tc();
		Style s =new Style();
		s.setFontSize(GlobalDocx.valFontSize);
		Docx4jUtils.setTcTest(tc1, boss.getCode(),s);
		tr.getContent().add(tc1);
		
		Tc tc2=new Tc();
		Docx4jUtils.setTcTest(tc2, boss.getName(),s);
		tr.getContent().add(tc2);
		
		Tc tc3=new Tc();
		Docx4jUtils.setTcTest(tc3, "String",s);
		tr.getContent().add(tc3);
		
		Tc tc4=new Tc();
		Docx4jUtils.setTcTest(tc4, "20",s);
		tr.getContent().add(tc4);
		
		Tc tc5=new Tc();
		Docx4jUtils.setTcTest(tc5, "",s);
		tr.getContent().add(tc5);
		
		tbl.getContent().add(tr);
	}
	private void initNullRow(Tbl tbl){
		Tr tr=Docx4jUtils.createTr();

		Style s=new Style();
		s.setFontSize(GlobalDocx.valFontSize);
		
		Tc tc1=new Tc();
		Docx4jUtils.setTcTest(tc1, "",s);
		tr.getContent().add(tc1);
		
		Tc tc2=new Tc();
		Docx4jUtils.setTcTest(tc2, "",s);
		tr.getContent().add(tc2);
		
		Tc tc3=new Tc();
		Docx4jUtils.setTcTest(tc3, "",s);
		tr.getContent().add(tc3);
		
		Tc tc4=new Tc();
		Docx4jUtils.setTcTest(tc4, "",s);
		tr.getContent().add(tc4);
		
		Tc tc5=new Tc();
		Docx4jUtils.setTcTest(tc5, "",s);
		tr.getContent().add(tc5);
		
		tbl.getContent().add(tr);
	}
	private void initCommonRow(Tbl tbl){
		Tr tr=Docx4jUtils.createTr();

		Style s=new Style();
		s.setFontSize(GlobalDocx.valFontSize);
		
		Tc tc1=new Tc();
		Docx4jUtils.setTcTest(tc1, "BUSI_RETURN_CODE",s);
		tr.getContent().add(tc1);
		
		Tc tc2=new Tc();
		Docx4jUtils.setTcTest(tc2, "业务处理编码",s);
		tr.getContent().add(tc2);
		
		Tc tc3=new Tc();
		Docx4jUtils.setTcTest(tc3, "String",s);
		tr.getContent().add(tc3);
		
		Tc tc4=new Tc();
		Docx4jUtils.setTcTest(tc4, "10",s);
		tr.getContent().add(tc4);
		
		Tc tc5=new Tc();
		Docx4jUtils.setTcTest(tc5, "-1：系统错误（线程池满）",s);
		tr.getContent().add(tc5);
		
		tbl.getContent().add(tr);
		
		Tr tr1=Docx4jUtils.createTr();

		Tc tc11=new Tc();
		Docx4jUtils.setTcTest(tc11, "BUSI_RETURN_INFO",s);
		tr1.getContent().add(tc11);
		
		Tc tc12=new Tc();
		Docx4jUtils.setTcTest(tc12, "业务处理内容",s);
		tr1.getContent().add(tc12);
		
		Tc tc13=new Tc();
		Docx4jUtils.setTcTest(tc13, "String",s);
		tr1.getContent().add(tc13);
		
		Tc tc14=new Tc();
		Docx4jUtils.setTcTest(tc14, "500",s);
		tr1.getContent().add(tc14);
		
		Tc tc15=new Tc();
		Docx4jUtils.setTcTest(tc15, "根据不同编码返回不同结果",s);
		tr1.getContent().add(tc15);
		
		tbl.getContent().add(tr1);
	}
	private void initEndRow(Tbl tbl){
		Tr tr=Docx4jUtils.createTr();
		
		Tc tc=new Tc();
		Style s=new Style();
		s.setVcol(5);
		Docx4jUtils.setTcTest(tc, "",s);
		tr.getContent().add(tc);
		
		tbl.getContent().add(tr);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
