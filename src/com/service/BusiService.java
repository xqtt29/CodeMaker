package com.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.base.DateBase;

public class BusiService extends DateBase{
	public BusiService(String url,String driver,String user,String pass,String path){
		init(url,driver,user,pass,path);
	}
	private List<String> func(String sql){
		List<String> relist=new ArrayList<String>();
		List<Map<String,String>> list=executeQuery(sql);
		for (Map<String,String> str : list) {
			if("".equals(str.get("COL"))||str.get("COL")==null)continue;
			relist.add(str.get("COL").replace("''", "NULL"));
		}
		return relist;
	}
	private String getLen(int i){
		String len="";
		for(int j=1;j<=i;j++){
			len+="0";
		}
		return len;
	}
	public String getBusiCode(String busi_code){
		String code="";
		List<String> list=func("SELECT MAX(BUSI_CODE) AS COL FROM TB_BUS_BUSIINFO WHERE BUSI_CODE LIKE '"+busi_code+"%' AND LENGTH(BUSI_CODE)=7");
		if(list.size()==0){
			code=busi_code+getLen(6-busi_code.length())+"1";
		}else{
			String temp=list.get(0);
			temp="1"+temp.substring(temp.length()-2,temp.length());
			temp=String.valueOf(Integer.parseInt(temp)+1);
			code=list.get(0).substring(0,list.get(0).length()-2)+temp.substring(1,3);
		}
		return code;
	}
	/**
	 * @desciption 获取TB_BUS_BUSIINFO表信息
	 * @param String busi_code
	 * @return List<String>
	 */
	public List<String> getTb_Bus_Busiinfo(String busi_code){
		String sql="SELECT 'INSERT INTO TB_BUS_BUSIINFO('||(SELECT WMSYS.WM_CONCAT(B.COLUMN_NAME) FROM USER_COL_COMMENTS B WHERE B.TABLE_NAME='TB_BUS_BUSIINFO')||')"+
			 " VALUES ('''||A.BUSI_CODE||''','''||A.BUSI_NAME||''','''||A.BUSI_DESC||''','''||A.CREATER||''','''||A.CREATE_TIME||''','''||A.UPDATE_MAN||''','''||A.UPDATE_TIME||''');' as COL"+
			" FROM TB_BUS_BUSIINFO A WHERE A.BUSI_CODE in('"+busi_code+"') ORDER BY A.BUSI_CODE";
		return func(sql);
	}
	
	/**
	 * @desciption 获取TB_BUS_COREINFRULE表信息
	 * @param String busi_code
	 * @return List<String>
	 */
	public List<String> getTb_Bus_Coreinfrule(String busi_code){
		String sql="SELECT 'INSERT INTO TB_BUS_COREINFRULE('||(SELECT WMSYS.WM_CONCAT(B.COLUMN_NAME) FROM USER_COL_COMMENTS B WHERE B.TABLE_NAME='TB_BUS_COREINFRULE')||')"+
			 " VALUES ('''||A.BUSI_CODE||''','''||A.CHANNEL||''','''||A.ACTIVITY_CODE||''','''||A.CITY_CODE||''','''||A.RULE_ID||''','''||A.CREATE_TIME||''','''||A.CREATE_MAN||''','''"+
			 "||A.INF_TYPE_CODE||''','''||A.STATE||''','''||A.BUSI_GROUP||''','''||A.BUSI_STARTDATE||''','''||A.BUSI_ENDDATE||''','''||A.RULE_VER||''');' as COL"+
			" FROM TB_BUS_COREINFRULE A WHERE A.BUSI_CODE in('"+busi_code+"') ORDER BY A.BUSI_CODE";
		return func(sql);
	}

	/**
	 * @desciption 获取TB_BUS_CORECHECKREF表信息
	 * @param String busi_code
	 * @return List<String>
	 */
	public List<String> getTb_Bus_Corecheckref(String busi_code){
		String sql="SELECT 'INSERT INTO TB_BUS_CORECHECKREF('||(SELECT WMSYS.WM_CONCAT(B.COLUMN_NAME) FROM USER_COL_COMMENTS B WHERE B.TABLE_NAME='TB_BUS_CORECHECKREF')||')"+
			" VALUES ('''||A.RULE_ID||''','''||A.CHECK_ID||''');' as COL"+
			" FROM TB_BUS_CORECHECKREF A WHERE A.RULE_ID in(SELECT distinct rule_id FROM TB_BUS_COREINFRULE WHERE busi_code in('"+busi_code+"')) ORDER BY A.RULE_ID,A.CHECK_ID";
		return func(sql);
	}
	/**
	 * @desciption 获取TB_BUS_INFFIELD表信息
	 * @param String busi_code
	 * @return List<String>
	 */
	public List<String> getTb_Bus_Inffield(String busi_code){
		String sql="SELECT 'INSERT INTO TB_BUS_INFFIELD('||(SELECT WMSYS.WM_CONCAT(B.COLUMN_NAME) FROM USER_COL_COMMENTS B WHERE B.TABLE_NAME='TB_BUS_INFFIELD')||')"+
			" VALUES ('''||A.CHECK_ID||''','''||A.FIELD_CODE||''','''||A.FIELD_NAME||''','''||A.IS_NULL||''','''||A.IS_DEFAULT||''','''||A.DEFAULT_VALUE||'''"+
			",'''||A.IS_MULTI||''','''||A.STATE||''','''||A.IS_MOBILENUM||''','''||A.IS_PULL||''','''||A.BUSI_TYPE||''','''||A.IS_INOUT||''','''||A.CHECK_NAME||''');' as COL"+
			" FROM TB_BUS_INFFIELD A WHERE A.CHECK_ID in(SELECT DISTINCT CHECK_ID FROM TB_BUS_CORECHECKREF WHERE RULE_ID IN"+
			"(SELECT distinct RULE_ID FROM TB_BUS_COREINFRULE WHERE BUSI_CODE in('"+busi_code+"'))) ORDER BY A.CHECK_ID";
		return func(sql);
	}
	/**
	 * @desciption 获取TB_BUS_BUSIPARAM表信息
	 * @param String busi_code
	 * @return List<String>
	 */
	public List<String> getTb_Bus_Busiparam(String busi_code){
		String sql="SELECT 'INSERT INTO TB_BUS_BUSIPARAM('||(SELECT WMSYS.WM_CONCAT(B.COLUMN_NAME) FROM USER_COL_COMMENTS B WHERE B.TABLE_NAME='TB_BUS_BUSIPARAM')||')"+
			 " VALUES ('''||A.BUSI_CODE||''','''||A.BUSI_NAME||''','''||A.PARAM_CODE||''','''||A.PARAM_NAME||''','''||A.PARAM_VALUE||''','''||A.STATE||''');' as COL"+
				" FROM TB_BUS_BUSIPARAM A WHERE A.BUSI_CODE in('"+busi_code+"') ORDER BY A.BUSI_CODE";
		return func(sql);
	}
	
	/**
	 * @desciption 获取TB_BUS_CORERULEREF表信息
	 * @param String busi_code
	 * @return List<String>
	 */
	public List<String> getTb_Bus_Coreruleref(String busi_code){
		String sql="SELECT 'INSERT INTO TB_BUS_CORERULEREF('||(SELECT WMSYS.WM_CONCAT(B.COLUMN_NAME) FROM USER_COL_COMMENTS B WHERE B.TABLE_NAME='TB_BUS_CORERULEREF')||')"+
			 " VALUES ('''||A.RULE_ID||''','''||A.TRANS_BUSI_CODE||''','''||A.TRANS_BUSI_NAME||''','''||A.BUSI_SEQ||''','''||A.STATE||''','''||A.EXECUTE_CLASS||''','''||A.IS_OKCODE||'''"+
			 ",'''||A.OPER_CODE||''','''||A.IS_RETORCODE||''','''||RETOR_SEQ||''');' as COL"+
			" FROM TB_BUS_CORERULEREF A WHERE A.RULE_ID in(SELECT distinct rule_id FROM TB_BUS_COREINFRULE WHERE busi_code in('"+busi_code+"')) ORDER BY A.RULE_ID,A.OPER_CODE,A.BUSI_SEQ";
		return func(sql);
	}
	
	/**
	 * @desciption 获取TB_BUS_INSIDECONFIG表信息
	 * @param String busi_code
	 * @return List<String>
	 */
	public List<String> getTb_Bus_Insideconfig(String busi_code){
		String sql="SELECT 'INSERT INTO TB_BUS_INSIDECONFIG('||(SELECT WMSYS.WM_CONCAT(B.COLUMN_NAME) FROM USER_COL_COMMENTS B WHERE B.TABLE_NAME='TB_BUS_INSIDECONFIG')||')"+
			 " VALUES ('''||A.INF_BUSI_CODE||''','''||A.INF_BUSI_NAME||''','''||A.INF_BUSI_CLASS||''','''||A.INF_BUSI_METHOD||''','''||A.STATE||''','''||A.CREATE_TIME||''','''||A.CREATE_MAN||'''"+
			 ",'''||A.REMARK||''');' as COL"+
			" FROM TB_BUS_INSIDECONFIG A WHERE A.INF_BUSI_CODE NOT IN('QCITY','PULLVALUE') AND A.INF_BUSI_CODE in(SELECT distinct TRANS_BUSI_CODE FROM TB_BUS_CORERULEREF WHERE RULE_ID IN(SELECT distinct rule_id FROM TB_BUS_COREINFRULE WHERE busi_code in('"+busi_code+"')))";
		return func(sql);
	}
	
	/**
	 * @desciption 获取TB_SRV_TRANSINT表信息
	 * @param String busi_code
	 * @param String trans_code
	 * @return List<String>
	 */
	public List<String> getTb_Bus_Transint(String busi_code,String trans_code){
		String sql="SELECT 'INSERT INTO TB_SRV_TRANSINT('||(SELECT WMSYS.WM_CONCAT(B.COLUMN_NAME) FROM USER_COL_COMMENTS B WHERE B.TABLE_NAME='TB_SRV_TRANSINT')||')"+
			 " VALUES ('''||A.TRANSCODE||''','''||A.TRANSCNAME||''','''||A.INTCODE||''','''||A.CALLNAME||''','''||A.CALLTYPE||''','''||A.ISQUERY||''','''||A.TIMEOUT||'''"+
			 ",'''||A.ISLOG||''','''||A.ISLOGDETAIL||''','''||A.ADAPTER_CODE||''');' as COL"+
			" FROM TB_SRV_TRANSINT A WHERE A.TRANSCODE NOT IN('QCITY','PULLVALUE') AND A.TRANSCODE in(SELECT '"+trans_code+"' FROM DUAL UNION SELECT distinct TRANS_BUSI_CODE FROM TB_BUS_CORERULEREF WHERE RULE_ID IN(SELECT distinct rule_id FROM TB_BUS_COREINFRULE WHERE busi_code in('"+busi_code+"')))";
		return func(sql);
	}
	
	/**
	 * @desciption 获取TB_SRV_INTFIELD表信息
	 * @param String busi_code
	 * @param String trans_code
	 * @return List<String>
	 */
	public List<String> getTb_Srv_Intfield(String busi_code,String trans_code){
		String sql="SELECT 'INSERT INTO TB_SRV_INTFIELD('||(SELECT WMSYS.WM_CONCAT(B.COLUMN_NAME) FROM USER_COL_COMMENTS B WHERE B.TABLE_NAME='TB_SRV_INTFIELD')||')"+
			 " VALUES ('''||A.INTCODE||''','''||A.INTCNAME||''','''||A.FIELDENAME||''','''||A.FIELDCNAME||''','''||A.INOUT||''','''||A.ISMULTIROW||''','''||A.FIELDTYPE||'''"+
			 ",'''||A.SEQ||''','''||A.CONSTRAINTS||''');' as COL"+
			" FROM TB_SRV_INTFIELD A WHERE A.INTCODE IN(SELECT INTCODE FROM TB_SRV_TRANSINT WHERE TRANSCODE in(SELECT '"+trans_code+"' FROM DUAL UNION SELECT distinct TRANS_BUSI_CODE FROM TB_BUS_CORERULEREF WHERE RULE_ID IN(SELECT distinct rule_id FROM TB_BUS_COREINFRULE WHERE busi_code in('"+busi_code+"')))) ORDER BY INTCODE,INTCNAME,SEQ";
		return func(sql);
	}
	
	/**
	 * @desciption 获取TB_SRV_TRANSFIELD表信息
	 * @param String busi_code
	 * @param String trans_code
	 * @return List<String>
	 */
	public List<String> getTb_Srv_Transfield(String busi_code,String trans_code){
		String sql="SELECT 'INSERT INTO TB_SRV_TRANSFIELD('||(SELECT WMSYS.WM_CONCAT(B.COLUMN_NAME) FROM USER_COL_COMMENTS B WHERE B.TABLE_NAME='TB_SRV_TRANSFIELD')||')"+
			 " VALUES ('''||A.TRANSCODE||''','''||A.TRANSCNAME||''','''||A.FIELDENAME||''','''||A.FIELDCNAME||''','''||A.INOUT||''','''||A.SEQ||''','''||A.USEDEFAULT||'''"+
			 ",'''||A.DEFAULTVAL||''','''||A.ISMOBILE||''','''||A.ISMULTIROW||''');' as COL"+
			" FROM TB_SRV_TRANSFIELD A WHERE A.TRANSCODE in(SELECT '"+trans_code+"' FROM DUAL UNION SELECT distinct TRANS_BUSI_CODE FROM TB_BUS_CORERULEREF WHERE RULE_ID IN(SELECT distinct rule_id FROM TB_BUS_COREINFRULE WHERE busi_code in('"+busi_code+"'))) order by TRANSCODE,TRANSCNAME";
		return func(sql);
	}

	/**
	 * @desciption 获取TB_SRV_TRANSINT_FIELD表信息
	 * @param String busi_code
	 * @param String trans_code
	 * @return List<String>
	 */
	public List<String> getTb_Srv_Transint_Field(String busi_code,String trans_code){
		String sql="SELECT 'INSERT INTO TB_SRV_TRANSINT_FIELD('||(SELECT WMSYS.WM_CONCAT(B.COLUMN_NAME) FROM USER_COL_COMMENTS B WHERE B.TABLE_NAME='TB_SRV_TRANSINT_FIELD')||')"+
			 " VALUES ('''||A.TRANSCODE||''','''||A.INTCODE||''','''||A.FIELDCNAME||''','''||A.TRANS_FIELDENAME||''','''||A.INT_FIELDENAME||''','''||A.INOUT||''','''||A.TRANSTYPE||'''"+
			 ",'''||A.TRANSRULE||''','''||A.DESCRIPTION||''');' as COL"+
			" FROM TB_SRV_TRANSINT_FIELD A WHERE A.TRANSCODE in(SELECT '"+trans_code+"' FROM DUAL UNION SELECT distinct TRANS_BUSI_CODE FROM TB_BUS_CORERULEREF WHERE RULE_ID IN(SELECT distinct rule_id FROM TB_BUS_COREINFRULE WHERE busi_code in('"+busi_code+"'))) ORDER BY TRANSCODE,INTCODE";
		return func(sql);
	}

	/**
	 * @desciption 向TB_SRV_TRANSINT_FIELD表插入信息
	 * @param String busi_code
	 * @param String trans_code
	 * @return List<String>
	 */
	public List<String> setTb_Srv_Transint_Field(String int_code){
		List<String> list=new ArrayList<String>();
		String sql="INSERT INTO TB_SRV_TRANSINT_FIELD"+
			" SELECT REPLACE(A.INTCODE,'_',''),A.INTCODE,A.FIELDCNAME,A.FIELDENAME,A.FIELDENAME,"+
			"A.INOUT,'A',NULL,NULL FROM TB_SRV_INTFIELD A WHERE A.INTCODE='"+int_code+"';";
		list.add(sql);
		return list;
	}

	/**
	 * @desciption 向TB_SRV_TRANSFIELD表插入信息
	 * @param String busi_code
	 * @param String trans_code
	 * @return List<String>
	 */
	public List<String> setTb_Srv_Transfield(String int_code){
		List<String> list=new ArrayList<String>();
		String sql="INSERT INTO TB_SRV_TRANSFIELD SELECT A.TRANSCODE,(SELECT B.INTCNAME FROM TB_SRV_INTFIELD B WHERE B.INTCODE=A.INTCODE AND ROWNUM=1)||DECODE(A.INOUT,'in','输入','out','输出'),"+
			"A.TRANS_FIELDENAME,A.FIELDCNAME,A.INOUT,(SELECT C.SEQ FROM TB_SRV_INTFIELD C WHERE C.INOUT=A.INOUT AND C.INTCODE=A.INTCODE AND C.FIELDENAME=A.INT_FIELDENAME),"+
			"'1',NULL,DECODE(A.INT_FIELDENAME,'SERIAL_NUMBER','1','0'),(SELECT B.ISMULTIROW FROM TB_SRV_INTFIELD B WHERE B.INOUT=A.INOUT AND B.INTCODE=A.INTCODE AND B.FIELDENAME=A.INT_FIELDENAME)"+
			" FROM TB_SRV_TRANSINT_FIELD A WHERE A.TRANSCODE=REPLACE('"+int_code+"','_','');";
		list.add(sql);
		return list;
	}

	/**
	 * @desciption 向TB_SRV_TRANSINT表插入信息
	 * @param String busi_code
	 * @param String trans_code
	 * @return List<String>
	 */
	public List<String> setTb_Srv_Transint(String int_code){
		List<String> list=new ArrayList<String>();
		String sql="INSERT INTO TB_SRV_TRANSINT"+
			" SELECT REPLACE(a.INTCODE,'_',''),A.INTCNAME,A.INTCODE,'ITF_WEB','NGBOSS','1',60,'0','0',NULL"+
			" FROM TB_SRV_INTFIELD A WHERE A.INTCODE='"+int_code+"' and ROWNUM=1;";
		list.add(sql);
		return list;
	}
	/**
	 * @desciption 向TB_SALES_BUSIDATA表插入信息
	 * @param String busi_code
	 * @param String busi_name
	 * @return List<String>
	 */
	public String getTb_Sales_Busidata(String busi_code,String busi_name){
		String sql="INSERT INTO TB_SALES_BUSIDATA (BUSI_CODE,BUSI_NAME,BUSI_TYPE_CODE,BUSI_TYPE_NAME,BOSS_INTERFACE,BUSI_ID,BUSI_OPEN_CODE,BUSI_OPEN_TYPE,BUSI_FEE,BUSI_FEE_REMARK,BUSI_QUERY_CODE,BUSI_STATE,IS_ONEOFF,PROVIDER_CODE) VALUES ('"+busi_code+"','"+busi_name+"',NULL,NULL,NULL,NULL,NULL,'65',NULL,NULL,NULL,NULL,NULL,NULL);";
		return sql;
	}
	/**
	 * @desciption 向TB_SALES_BUSIINF表插入信息
	 * @param String busi_code
	 * @param String busi_name
	 * @return List<String>
	 */
	public List<String> getTb_Sales_Busiinf(String busi_code,String busi_name){
		List<String> sql=new ArrayList<String>();
		String sql1="INSERT INTO TB_SALES_BUSIINF (BUSI_CODE,BUSI_ID,EPF_BUSI_CODE,OPERATION,INF_CODE,INF_EXE_CLASS,INF_EXE_METHOD,INF_EXPLAIN,STATE) VALUES ('"+busi_code+"','"+busi_code+"','"+busi_code+"','query',NULL,'com.chinacreator.ecb.EcbInterfaceBase','ecbYearQuery','"+busi_name+"','0');";
		String sql2="INSERT INTO TB_SALES_BUSIINF (BUSI_CODE,BUSI_ID,EPF_BUSI_CODE,OPERATION,INF_CODE,INF_EXE_CLASS,INF_EXE_METHOD,INF_EXPLAIN,STATE) VALUES ('"+busi_code+"','"+busi_code+"','"+busi_code+"','operation',NULL,'com.chinacreator.ecb.EcbInterfaceBase','ecbYearOperation','"+busi_name+"','0');";
		sql.add(sql1);
		sql.add(sql2);
		return sql;
	}
	/**
	 * @desciption 向TB_ECB_BUSITR表插入信息（推荐）
	 * @param String busi_code
	 * @param String busi_name
	 * @return List<String>
	 */
	public List<String> getTb_Ecb_Busiitr1(String busi_code,String busi_name,String ecb_oper,String ecb_query){
		List<String> sql=new ArrayList<String>();
		String sql1="INSERT INTO TB_ECB_BUSITR (INF_CODE,BUSICODE,BUSI_NAME,BUSI_CODE,ACTIVE_CODE,QUERY_CODE) VALUES ('EB_B_MARKETINGBUSITRANSACT','"+busi_code+"','"+busi_name+"','"+ecb_oper+"','0','"+ecb_query+"');";
		String sql2="INSERT INTO TB_ECB_BUSITR (INF_CODE,BUSICODE,BUSI_NAME,BUSI_CODE,ACTIVE_CODE,QUERY_CODE) VALUES ('EB_B_MARKETINGBUSITRANSACT','"+ecb_query+"','"+busi_name+"查询','"+ecb_query+"','0',NULL);";
		sql.add(sql1);
		sql.add(sql2);
		return sql;
	}
	/**
	 * @desciption 向TB_WS_BUSIINF表插入信息
	 * @param String busi_code
	 * @param String busi_name
	 * @return List<String>
	 */
	public String getTb_Ws_Busiinf(String busi_code,String busi_name){
		String sql="INSERT INTO TB_WS_BUSIINF (INF_CODE,INF_EXE_CLASS,INF_EXE_METHOD,INF_EXPLAIN,STATE,INF_TYPE_CODE) VALUES ('"+busi_code+"','com.chinacreator.qzyx.busi.ItfNetNumQuery','publicCallEcb','"+busi_name+"','0','BOSS');";
		return sql;
	}
	/**
	 * @desciption 向TB_WS_BUSIINF_FIELD表插入信息
	 * @param String busi_code
	 * @param String ecb_oper
	 * @return List<String>
	 */
	public List<String> getTb_Ws_Busiinf_Field(String busi_code,String ecb_oper){
		String sql="SELECT 'INSERT INTO TB_WS_BUSIINF_FIELD (INF_CODE,FIELD_NAME,IS_INOUT,FIELD_TYPE,STATE,FIELD_EXPLAIN,IS_NULL,VALIDATE_EXPRESSION) VALUES (''"+busi_code+"'','''||B.FIELD_CODE||''',''in'',''1'',''0'','''||B.FIELD_NAME||''','''||B.IS_NULL||''',NULL);' AS COL"+
			" FROM TB_BUS_CORECHECKREF A,TB_BUS_INFFIELD B WHERE A.CHECK_ID=B.CHECK_ID AND IS_PULL='1' AND A.RULE_ID=(SELECT RULE_ID FROM TB_BUS_COREINFRULE WHERE BUSI_CODE='"+ecb_oper+"' AND CHANNEL='QZYX')";
		return func(sql);
	}
	/**
	 * @desciption 向TB_ECB_BUSITR表插入信息（办理）
	 * @param String busi_code
	 * @param String busi_name
	 * @param String ecb_oper
	 * @return List<String>
	 */
	public String getTb_Ecb_Busiitr2(String busi_code,String busi_name,String ecb_oper){
		String sql="INSERT INTO TB_ECB_BUSITR (INF_CODE,BUSICODE,BUSI_NAME,BUSI_CODE,ACTIVE_CODE,QUERY_CODE) VALUES ('"+busi_code+"','"+busi_code+"','"+busi_name+"','"+ecb_oper+"','0','"+ecb_oper+"');";
		return sql;
	}
	/**
	 * @desciption 向TB_ECB_BUSIINFO表插入信息
	 * @param String busi_code
	 * @param String busi_name
	 * @return List<String>
	 */
	public String getTb_Ecb_Busiinfo(String busi_code,String busi_name){
		String sql="INSERT INTO TB_BUS_BUSIINFO (BUSI_CODE,BUSI_NAME,BUSI_DESC,CREATER,CREATE_TIME,UPDATE_MAN,UPDATE_TIME) VALUES ('"+busi_code+"','"+busi_name+"',NULL,NULL,NULL,NULL,NULL);";
		return sql;
	}
	/**
	 * @desciption 向TB_BUS_BUSIPARAM表插入信息
	 * @param String busi_code
	 * @param String busi_name
	 * @return List<String>
	 */
	public String getTb_Ecb_Busiparam(String busi_code,String busi_name){
		String sql="INSERT INTO TB_BUS_BUSIPARAM (BUSI_CODE,BUSI_NAME,PARAM_CODE,PARAM_NAME,PARAM_VALUE,STATE) VALUES ('"+busi_code+"','"+busi_name+"',NULL,NULL,NULL,'0');";
		return sql;
	}
	/**
	 * @desciption 向TB_BUS_COREINFRULE表插入信息
	 * @param String busi_code
	 * @param String author
	 * @return List<String>
	 */
	public String getTb_Ecb_Coreinfrule(String busi_code,String author){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
		String time=sdf.format(new Date());
		String sql="INSERT INTO TB_BUS_COREINFRULE (BUSI_CODE,CHANNEL,ACTIVITY_CODE,CITY_CODE,RULE_ID,CREATE_TIME,CREATE_MAN,INF_TYPE_CODE,STATE,BUSI_GROUP,BUSI_STARTDATE,BUSI_ENDDATE,RULE_VER) VALUES ('"+busi_code+"','WEB','0','*','"+busi_code+"#*#0#*','"+time+"','"+author+"','BOSS','0','GROUP002','20101001','20201010',NULL);";
		return sql;
	}

	/**
	 * @desciption 向TB_BUS_CORECHECKREF表插入信息
	 * @param String busi_code
	 * @return List<String>
	 */
	public String getTb_Ecb_Corecheckref(String busi_code){
		String sql="INSERT INTO TB_BUS_CORECHECKREF (RULE_ID,CHECK_ID) VALUES ('"+busi_code+"#*#0#*','100005');";
		return sql;
	}

	/**
	 * @desciption 向TB_BUS_CORERULEREF表插入信息（推荐）
	 * @param String busi_code
	 * @param String int_code
	 * @param String int_name
	 * @return List<String>
	 */
	public List<String> getTb_Bus_Coreruleref(String busi_code,String int_code,String int_name){
		List<String> sql=new ArrayList<String>();
		String sql1="INSERT INTO TB_BUS_CORERULEREF (RULE_ID,TRANS_BUSI_CODE,TRANS_BUSI_NAME,BUSI_SEQ,OPER_CODE,STATE,EXECUTE_CLASS,IS_OKCODE,IS_RETORCODE,RETOR_SEQ) VALUES ('"+busi_code+"#*#0#*','QCITY','查询地市',1,'*','0','com.chinacreator.ecb.bus.service.CBusTransFactory','0','*',NULL);";
		String sql2="INSERT INTO TB_BUS_CORERULEREF (RULE_ID,TRANS_BUSI_CODE,TRANS_BUSI_NAME,BUSI_SEQ,OPER_CODE,STATE,EXECUTE_CLASS,IS_OKCODE,IS_RETORCODE,RETOR_SEQ) VALUES ('"+busi_code+"#*#0#*','PULLVALUE','填充参数',2,'*','0','com.chinacreator.ecb.bus.service.CBusTransFactory','0','*',NULL);";
		String sql3="INSERT INTO TB_BUS_CORERULEREF (RULE_ID,TRANS_BUSI_CODE,TRANS_BUSI_NAME,BUSI_SEQ,OPER_CODE,STATE,EXECUTE_CLASS,IS_OKCODE,IS_RETORCODE,RETOR_SEQ) VALUES ('"+busi_code+"#*#0#*','"+int_code+"','"+int_name+"',3,'*','0','com.chinacreator.ecb.bus.service.CBusSrvExecFactory','0','*',NULL);";
		sql.add(sql1);
		sql.add(sql2);
		sql.add(sql3);
		return sql;
	}
}
