package com.temp;

import com.base.DateBase;
import com.base.Global;
import com.base.Logger;
import com.service.BOSSService;
import com.service.BusiService;
import com.service.FileService;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Temp extends DateBase
{
  private Map<String, String> map;
  private String filePath;
  private Logger log;

  public void createTemp()
  {
    try
    {
    	this.filePath = System.getProperty("user.dir")+File.separator;
    	//this.filePath="E:\\tempfunc\\";
	  this.log = new Logger(this.filePath);
      this.map = getProp();
      this.log.info("读取配置文件成功...");

      init((String)this.map.get(Global.URL), (String)this.map.get(Global.DRIVER), (String)this.map.get(Global.DBNAME), (String)this.map.get(Global.DBPASS), this.filePath);
      this.log.info("初始化数据库成功...");
      
      //版本打包模块
      if("0".equals(this.map.get(Global.VERSION_FLAG))){
    	  String versionName=this.map.get((Global.VERSION_NAME));
    	  String versionPath=this.map.get((Global.VERSION_PATH));
    	  String srcPath=this.map.get((Global.VERSION_SRCPATH));
    	  if(this.createVersionInfo(versionName,srcPath,versionPath))
    		  this.log.info("版本打包成功,路径："+versionPath);
    	  else
    		  this.log.info("版本打包失败。");
      }
      //版本打包模块结束
      
      func(this.filePath + "template", (String)this.map.get(Global.FILE_PATH) + "\\",getMapDatas());
      this.log.info("生成模版文件成功...");
      if("0".equals(this.map.get(Global.INT_FLAG))){
	      //生成接口规范文档
	      new BOSSService(this.filePath).createDocx(this.map);
	  }
      Runtime.getRuntime().exec("cmd.exe /c start " + (String)this.map.get(Global.FILE_PATH));
    } catch (Exception e) {
      e.printStackTrace();
      this.log.error(e.getMessage());
    }
  }

  private void deleteFile(File file)
  {
    if (file.exists())
      for (File temp : file.listFiles()) {
        if (!temp.isFile()) {
          deleteFile(temp);
        }
        temp.delete();
      }
  }

  private void func(String tempPath, String path,Map m)
  {
    try
    {
      Configuration cfg = new Configuration();
      cfg.setDirectoryForTemplateLoading(new File(tempPath));
      cfg.setObjectWrapper(new DefaultObjectWrapper());
      File f = new File(path);
      deleteFile(f);
      f.mkdir();
      this.log.info("从数据库中获取信息成功...");
      for (File temp : new File(tempPath).listFiles()) {
        if (((!"0".equals(this.map.get(Global.BEAN_FLAG))) && ("${ClassName}.java".equals(temp.getName()))) || 
                ((!"0".equals(this.map.get(Global.OTHER_FLAG))) && ("other.sql".equals(temp.getName())))|| 
                ((!"0".equals(this.map.get(Global.AUTOCLASSFLAG))) && ("${AutoClsName}.java".equals(temp.getName())))|| 
                ((!"TRUE".equals(m.get("OPEN_MUTILFLAG"))) && ("${OPEN_NAME}DetailOut.java".equals(temp.getName())))|| 
                ((!"TRUE".equals(m.get("OPEN_MUTILFLAGIN"))) && ("${OPEN_NAME}DetailIn.java".equals(temp.getName())))|| 
                ((!"0".equals(this.map.get(Global.WW_FLAG))) && ("client".equals(temp.getName())||"server".equals(temp.getName())||"ecb_webwin.xml".equals(temp.getName())))|| 
                ((!"0".equals(this.map.get(Global.OPEN_FLAG))) && ("bean".equals(temp.getName())||"request".equals(temp.getName())||"${OPEN_ECBCODE}Adapter.java".equals(temp.getName())||"response".equals(temp.getName())||"${OPEN_NAME}Service.java".equals(temp.getName())))) continue;
        if (!temp.isFile())
        {
          String dir=temp.getName();
          if("${package}".equals(temp.getName()))
        	  dir=m.get("wwPackageName")==null?"default":m.get("wwPackageName").toString();
          func(temp.toString(), path + dir + "\\",m);
        }
        else {
          Template tem = cfg.getTemplate(temp.getName(), "UTF-8");
          String fileName=temp.getName();
          Pattern pat = Pattern.compile("(?<=\\{)[^\\}]+");
          Matcher mat = pat.matcher(fileName);
  		  while(mat.find()){
  			  String key=mat.group(0);
  			  if(m.keySet().contains(key)){
  				  fileName=fileName.replace("${"+key+"}", m.get(key) == null ? "" : m.get(key).toString());
  			  }
  		  }
          File file = new File(path + fileName);
          file.createNewFile();
          Writer out = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
          tem.process(m, out);
          out.flush();
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      this.log.error(e.getMessage());
    }
  }

  private Map<Object, Object> getMapDatas()
    throws Exception
  {
    Map root = new HashMap();

    String exists = "";
    Field[] f = Global.class.getDeclaredFields();
    for (Field ft : f) {
      exists = exists + ft.get("") + "&&";
    }
    for (String str : this.map.keySet()) {
      if ((!str.contains("TSQL_")) && (!Arrays.asList(exists.split("&&")).contains(str))) {
        String tem = (String)this.map.get(str);
        if (tem.contains("&_&"))
          root.put(str, Arrays.asList(tem.split("&_&")));
        else {
          root.put(str, this.map.get(str));
        }
      }
    }

    for (String str : this.map.keySet()) {
      if (str.contains("TSQL_")) {
        root.put(str, executeQuery((String)this.map.get(str)));
      }
    }
    if ("0".equals(this.map.get(Global.BEAN_FLAG))) {
      root.put("table", getTable((String)this.map.get(Global.TABLE_NAME)));
      root.put("ClassName", getClassName((String)this.map.get(Global.TABLE_NAME)));
      root.put("classNote", this.map.get(Global.CLASS_NOTE));
      root.put("classPath", this.map.get(Global.CLASS_PATH));
    }
    if ("0".equals(this.map.get(Global.WW_FLAG))) {
        root.put("wwTable", getTable((String)this.map.get(Global.WW_TABLENAME)));
        root.put("wwPackageName", this.map.get(Global.WW_PACKAGENAME));
        root.put("wwPackagePath", this.map.get(Global.WW_PACKAGEPATH));
        root.put("wwClassName", this.map.get(Global.WW_CLASSNAME));
        root.put("wwClassInfo", this.map.get(Global.WW_CLASSINFO));
        root.put("wwClassCode", this.map.get(Global.WW_CLASSCODE));
      }
    if ("0".equals(this.map.get(Global.OPEN_FLAG))) {
        root.put("OPEN_NAME", this.map.get(Global.OPEN_NAME));
        root.put("OPEN_PATH", this.map.get(Global.OPEN_PATH));
        root.put("OPEN_METHOD", this.map.get(Global.OPEN_METHOD));
        root.put("OPEN_METHODNAME", this.map.get(Global.OPEN_METHODNAME));
        root.put("OPEN_ECBCODE", this.map.get(Global.OPEN_ECBCODE));
        StringBuffer sout=new StringBuffer();//this.map.get(Global.OPEN_SINGLEOUT);
        StringBuffer usout=new StringBuffer();
        StringBuffer osout=new StringBuffer();
        FileService fs=new FileService(filePath);
        List<Map> souts=fs.readFileFirstLast("out");
    	String str="";
        for(Map map : souts){
        	sout.append(map.get(1)+";");
        	usout.append(map.get(2)+";");
        	String[] temp=map.get(1).toString().toLowerCase().split("_");
        	if(temp.length!=0){
        		osout.append(temp[0]);
	        	for(int i=1;i<temp.length;i++){
	        		str=temp[i].substring(0, 1);
	        		osout.append(temp[i].replaceFirst(str, str.toUpperCase()));
	        	}
	        	osout.append(";");
        	}
        }
        root.put("OPEN_SINGLEOUT", new ArrayList());
        root.put("OPEN_FUSINGLEOUT", new ArrayList());
        root.put("OPEN_SINGLENOTE", new ArrayList());
        root.put("OPEN_ECBSINGLEOUT", new ArrayList());
        if(osout!=null&&osout.toString().trim().length()>0){
	        String [] single=osout.toString().trim().split(";");
	        root.put("OPEN_SINGLEOUT", single);
	        String [] fusingle=new String[single.length];
	        for(int i=0;i<single.length;i++){
	        	str=single[i].substring(0, 1);
	        	fusingle[i]=single[i].replaceFirst(str, str.toUpperCase());
	        }
	        root.put("OPEN_FUSINGLEOUT", fusingle);
	        root.put("OPEN_SINGLENOTE", usout.toString().trim().split(";"));
	        root.put("OPEN_ECBSINGLEOUT", sout.toString().trim().split(";"));
        }
        
        StringBuffer sin=new StringBuffer();//this.map.get(Global.OPEN_SINGLEOUT);
        StringBuffer usin=new StringBuffer();
        StringBuffer osin=new StringBuffer();
        List<Map> sins=fs.readFileFirstLast("in");
        for(Map map : sins){
        	sin.append(map.get(1)+";");
        	usin.append(map.get(2)+";");
        	String[] temp=map.get(1).toString().toLowerCase().split("_");
        	if(temp.length!=0){
        		osin.append(temp[0]);
	        	for(int i=1;i<temp.length;i++){
	        		str=temp[i].substring(0, 1);
	        		osin.append(temp[i].replaceFirst(str, str.toUpperCase()));
	        	}
	        	osin.append(";");
        	}
        }
        root.put("OPEN_SINGLEIN", new ArrayList());
        root.put("OPEN_FUSINGLEIN", new ArrayList());
        root.put("OPEN_SINGLENOTEIN", new ArrayList());
        root.put("OPEN_ECBSINGLEIN", new ArrayList());
        if(osin!=null&&osin.toString().trim().length()>0){
	        String [] single=osin.toString().trim().split(";");
	        root.put("OPEN_SINGLEIN", single);
	        String [] fusingle=new String[single.length];
	        for(int i=0;i<single.length;i++){
	        	str=single[i].substring(0, 1);
	        	fusingle[i]=single[i].replaceFirst(str, str.toUpperCase());
	        }
	        root.put("OPEN_FUSINGLEIN", fusingle);
	        root.put("OPEN_SINGLENOTEIN", usin.toString().trim().split(";"));
	        root.put("OPEN_ECBSINGLEIN", sin.toString().trim().split(";"));
        }
        
        StringBuffer soutm=new StringBuffer();//this.map.get(Global.OPEN_MUTILOUT);
        StringBuffer usoutm=new StringBuffer();
        StringBuffer osoutm=new StringBuffer();
        List<Map> soutms=fs.readFileFirstLast("outm");
        for(Map map : soutms){
        	soutm.append(map.get(1)+";");
        	usoutm.append(map.get(2)+";");
        	String[] temp=map.get(1).toString().toLowerCase().split("_");
        	if(temp.length!=0){
        		osoutm.append(temp[0]);
	        	for(int i=1;i<temp.length;i++){
	        		str=temp[i].substring(0, 1);
	        		osoutm.append(temp[i].replaceFirst(str, str.toUpperCase()));
	        	}
	        	osoutm.append(";");
        	}
        }
        if(osoutm!=null&&osoutm.toString().trim().length()>0){
        	String [] mutil=osoutm.toString().trim().split(";");
        	root.put("OPEN_MUTILFLAG", "TRUE");
            root.put("OPEN_MUTILOUT", mutil);
            String [] fumutil=new String[mutil.length];
            for(int i=0;i<mutil.length;i++){
            	str=mutil[i].substring(0, 1);
            	fumutil[i]=mutil[i].replaceFirst(str, str.toUpperCase());
            }
            root.put("OPEN_FUMUTILOUT", fumutil);
            root.put("OPEN_MUTILNOTE", usoutm.toString().trim().split(";"));
            root.put("OPEN_ECBMUTILOUT", soutm.toString().trim().split(";"));
        }else{
        	root.put("OPEN_MUTILFLAG", "FALSE");
        }
        

        StringBuffer sinm=new StringBuffer();//this.map.get(Global.OPEN_MUTILOUT);
        StringBuffer usinm=new StringBuffer();
        StringBuffer osinm=new StringBuffer();
        List<Map> sinms=fs.readFileFirstLast("inm");
        for(Map map : sinms){
        	sinm.append(map.get(1)+";");
        	usinm.append(map.get(2)+";");
        	String[] temp=map.get(1).toString().toLowerCase().split("_");
        	if(temp.length!=0){
        		osinm.append(temp[0]);
	        	for(int i=1;i<temp.length;i++){
	        		str=temp[i].substring(0, 1);
	        		osinm.append(temp[i].replaceFirst(str, str.toUpperCase()));
	        	}
	        	osinm.append(";");
        	}
        }
        if(osinm!=null&&osinm.toString().trim().length()>0){
        	String [] mutil=osinm.toString().trim().split(";");
        	root.put("OPEN_MUTILFLAGIN", "TRUE");
            root.put("OPEN_MUTILIN", mutil);
            String [] fumutil=new String[mutil.length];
            for(int i=0;i<mutil.length;i++){
            	str=mutil[i].substring(0, 1);
            	fumutil[i]=mutil[i].replaceFirst(str, str.toUpperCase());
            }
            root.put("OPEN_FUMUTILIN", fumutil);
            root.put("OPEN_MUTILNOTEIN", usinm.toString().trim().split(";"));
            root.put("OPEN_ECBMUTILIN", sinm.toString().trim().split(";"));
        }else{
        	root.put("OPEN_MUTILFLAGIN", "FALSE");
        }
      }
    if("0".equals(this.map.get(Global.AUTOCLASSFLAG))){
    	root.put("autoClsPath", this.map.get(Global.CLASSPATH));
    	root.put("AutoClsName", this.map.get(Global.CLASSNAME));
        root.put("AutoClsNote", this.map.get(Global.CLASSNOTE));
    	if(this.map.get(Global.METHODNAME)!=null)
    		root.put("clsMethodName", this.map.get(Global.METHODNAME).split("#"));
    	if(this.map.get(Global.METHODNOTE)!=null)
    		root.put("clsMethodNote", this.map.get(Global.METHODNOTE).split("#"));
    }
    if ("0".equals(this.map.get(Global.OTHER_FLAG))) {
      List list = new BOSSService(this.filePath).initOther();
      root.put("others", list);
      root.put("otherSQL", new BOSSService(this.filePath).initSQLOther());
    }
    root.put("busiinfo", getInfos((String)this.map.get(Global.BUSI_CODE), (String)this.map.get(Global.TRANS_CODE), (String)this.map.get(Global.INT_CODE)));
    root.put("sysdate",new SimpleDateFormat("yyyyMMdd").format(new Date()));
    root.put("author", this.map.get(Global.AUTHOR));
    return root;
  }

  private String getClassName(String tableName)
  {
    String str = "";
    for (String temp : tableName.toLowerCase().split("_")) {
      str = str + temp.replaceFirst(new StringBuilder(String.valueOf(temp.charAt(0))).toString(), new StringBuilder(String.valueOf((char)(temp.charAt(0) - ' '))).toString());
    }
    return str;
  }

  private Map<String, String> getProp()
  {
    Map m = new HashMap();
    Properties p = new Properties();
    try {
      InputStreamReader isr = new InputStreamReader(new FileInputStream(this.filePath + "conf.properties"), "gbk");
      p.load(isr);
      for (Iterator localIterator = p.keySet().iterator(); localIterator.hasNext(); ) { Object temp = localIterator.next();
        m.put((String)temp, p.getProperty((String)temp).trim());
      }
      isr.close();
    } catch (Exception e) {
      e.printStackTrace();
      this.log.error(e.getMessage());
    }
    return m;
  }

  private List<String> getInfos(String busi_code, String trans_code, String int_code)
  {
    BusiService bs = new BusiService((String)this.map.get(Global.URL), (String)this.map.get(Global.DRIVER), (String)this.map.get(Global.DBNAME), (String)this.map.get(Global.DBPASS), this.filePath);
    List list = new ArrayList();

    if ("0".equals(this.map.get(Global.BUSI_FLAG))) {
      list.addAll(bs.getTb_Bus_Busiinfo(busi_code));
      list.add("");
      list.addAll(bs.getTb_Bus_Busiparam(busi_code));
      list.add("");
      list.addAll(bs.getTb_Bus_Coreinfrule(busi_code));
      list.add("");
      list.addAll(bs.getTb_Bus_Inffield(busi_code));
      list.add("");
      list.addAll(bs.getTb_Bus_Corecheckref(busi_code));
      list.add("");
      list.addAll(bs.getTb_Bus_Coreruleref(busi_code));
      list.add("");
      list.addAll(bs.getTb_Bus_Insideconfig(busi_code));
      list.add("");
      list.addAll(bs.getTb_Bus_Transint(busi_code, trans_code));
      list.add("");
    }

    if ("0".equals(this.map.get(Global.TRANS_FLAG))) {
      list.addAll(bs.getTb_Srv_Intfield(busi_code, trans_code));
      list.addAll(bs.getTb_Srv_Transfield(busi_code, trans_code));
      list.addAll(bs.getTb_Srv_Transint_Field(busi_code, trans_code));
      list.add("");
    }

    if ("0".equals(this.map.get(Global.INT_FLAG))) {
      BOSSService bo = new BOSSService(this.filePath);
      list.add("--根据外围接口文档生成的脚本");
      String int_ecb_code = (String)this.map.get(Global.INT_ECB_CODE);
      if ((int_ecb_code != null) && (int_ecb_code.length() != 7)){
        int_ecb_code = bs.getBusiCode((String)this.map.get(Global.INT_ECB_CODE));
        map.put(Global.INT_ECB_CODE+"_true", int_ecb_code);
      }
      list.add(bs.getTb_Ecb_Busiinfo(int_ecb_code, (String)this.map.get(Global.INT_ECB_NAME)));
      list.add("");
      list.add(bs.getTb_Ecb_Busiparam(int_ecb_code, (String)this.map.get(Global.INT_ECB_NAME)));
      list.add("");
      list.add(bs.getTb_Ecb_Coreinfrule(int_ecb_code, (String)this.map.get(Global.AUTHOR)));
      list.add("");
      list.add(bs.getTb_Ecb_Corecheckref(int_ecb_code));
      list.add("");
      list.addAll(bs.getTb_Bus_Coreruleref(int_ecb_code, int_code.replace("_", ""), (String)this.map.get(Global.INT_NAME)));
      list.add("");
      list.addAll(bo.setTb_Srv_Intfield(int_code, (String)this.map.get(Global.INT_NAME)));
      list.add("");
      for (String temp : int_code.split("&&")) {
        list.addAll(bs.setTb_Srv_Transint_Field(temp));
        list.add("");
        list.addAll(bs.setTb_Srv_Transfield(temp));
        list.add("");
        list.addAll(bs.setTb_Srv_Transint(temp));
        list.add("");
      }
      list.add("");
    }

    if ("1".equals(this.map.get(Global.QZYX_FLAG))) {
      list.add("--圈子平台办理类业务的配置");
      String code = (String)this.map.get(Global.QZYX_CODE);
      String name = (String)this.map.get(Global.QZYX_NAME);
      list.add(bs.getTb_Ws_Busiinf(code, name));
      list.add("");
      list.addAll(bs.getTb_Ws_Busiinf_Field(code, (String)this.map.get(Global.QZYX_ECB_OPER)));
      list.add("");
      list.add(bs.getTb_Ecb_Busiitr2(code, name, (String)this.map.get(Global.QZYX_ECB_OPER)));
      list.add("");
    }

    if ("2".equals(this.map.get(Global.QZYX_FLAG))) {
      list.add("--圈子平台推荐类业务的配置");
      String code = (String)this.map.get(Global.QZYX_CODE);
      String name = (String)this.map.get(Global.QZYX_NAME);
      list.add(bs.getTb_Sales_Busidata(code, name));
      list.add("");
      list.addAll(bs.getTb_Sales_Busiinf(code, name));
      list.add("");
      list.addAll(bs.getTb_Ecb_Busiitr1(code, name, (String)this.map.get(Global.QZYX_ECB_OPER), (String)this.map.get(Global.QZYX_ECB_QUERY)));
      list.add("");
    }

    if ("0".equals(this.map.get(Global.OPEN_FLAG))) {
      list.add("--能力开放平台添加业务脚本(仅供开发，勿当版本提)");
      String method = (String)this.map.get(Global.OPEN_METHOD);
      String methodName = (String)this.map.get(Global.OPEN_METHODNAME);
      list.add("Insert into TB_ECOP_ABILITY"+
   " (PK_ID, RES_ID, PARENT_ID, METHOD, METHOD_NAME, "+
   " VERSION, TIMEOUT, STARTTIME, ENDTIME, STATE, "+
   " METHOD_TYPE, METHOD_DESC, INF_BUSI_CLASS, INF_BUSI_METHOD, ABILITY_PICTURE, "+
   " URL, INPUT_PARAMETERS, OUTPUT_PARAMETERS, GRADE, SEQ)"+
   " Values "+
   " ((select max(to_number(pk_id)) from TB_ECOP_ABILITY)+1,(select max(to_number(pk_id)) from TB_ECOP_ABILITY)+1, '1005', '"+method+"', '"+methodName+"',"+ 
   " '1.0', '1000', '20140101', '20151231', '0',"+ 
   " '0', '查询', NULL, NULL, 'images/yewu2.gif',"+ 
   " NULL, NULL, NULL, '3', NULL);");
      list.add("");
      list.add("Insert into TD_ECOP_APP_ABILITY_LIST "+
   "(PK_ID, APPID, STARTTIME, ENDTIME, CREATE_TIME, "+
    "UPDATE_TIME, UPDATE_MAN, STATE, METHOD_CALLTIMES, APPLY_DESC, "+
   " BEGINTIME, FINISHTIME, ALARM_TYPE, ALARM_VALUE, ALARM_TIME, "+
   " FLAG, USE_SCENE, ANNEX_PATH, USERAPPLY_DESC, ABILITY_ID) "+
 "Values "+
  " ((select max(to_number(pk_id)) from TD_ECOP_APP_ABILITY_LIST)+1, '00001', '20140101', '20501231', NULL, "+
  "  NULL, NULL, '0', 0, NULL, "+
   " '000000', '235959', NULL, 0, NULL, "+
   " '0', '0', NULL, NULL, (select PK_ID from TB_ECOP_ABILITY where method='"+method+"'));");
      list.add("");
    }
    if (list.size() != 0)
      list.add("COMMIT;");
    return list;
  }
  //读取待打版本的JAVA源代码路径
  public List<String> readVersionPathFile(String versionCreater){
		File f=new File(filePath+File.separator+versionCreater+".txt");
		List<String> list=new ArrayList<String>();
		try {
			InputStreamReader isr = new InputStreamReader(new FileInputStream(f), "GBK");
			BufferedReader br = new BufferedReader(isr);
			String str="";
			while((str=br.readLine())!=null){
				if(str==null||str.length()==0)continue;
				list.add(str.trim());
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return list;
  }
//复制文件
  private void copyFile(File sourceFile, File targetFile) throws IOException {
       BufferedInputStream inBuff = null;
       BufferedOutputStream outBuff = null;
       try {
           // 新建文件输入流并对它进行缓冲
          inBuff = new BufferedInputStream(new FileInputStream(sourceFile));

           // 新建文件输出流并对它进行缓冲
          outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));

           // 缓冲数组
          byte[] b = new byte[1024 * 5];
           int len;
           while ((len = inBuff.read(b)) != -1) {
               outBuff.write(b, 0, len);
           }
           // 刷新此缓冲的输出流
          outBuff.flush();
       } finally {
           // 关闭流
          if (inBuff != null)
               inBuff.close();
           if (outBuff != null)
               outBuff.close();
       }
   }
  //创建版本方法 (版本号名称,待打包的JAVA源代码路径,版本存放路径)
  private boolean createVersionInfo(String versionName,String srcPath,String versionPath){
	  //根据文件名读取需要打版本的JAVA源代码路径
	  List<String> list=readVersionPathFile("VersionPaths");
	  //清掉文件夹
	  File delFile=new File("D:"+File.separator+versionName.substring(0, versionName.indexOf("\\")));
	  deleteFile(delFile);
	  try {
		  //循环各JAVA源代码路径
		  for(String path : list){
			  //获取JAVA源代码包路径
			  String p=path.substring(0,path.lastIndexOf(".")).replace(".",File.separator);
			  //创建JAVA源代码路径
			  File toFilePath=new File("D:"+File.separator+versionName+File.separator+p);
			  //重新创建文件夹
			  toFilePath.mkdirs();
			  //创建JAVA待生成的源代码
			  File toFile=new File("D:"+File.separator+versionName+File.separator+path.replace(".", File.separator)+".class");
			  if(!toFile.exists())
				  toFile.createNewFile();
			  //创建JAVA目标源代码
			  File fromFile=new File(srcPath+File.separator+path.replace(".", File.separator)+".class");
			  //将目标源代码COPY到目标源代码
			  log.info("正在打包源代码："+path);
			  this.copyFile(fromFile, toFile);
			  //COPY目标子源代码$1、$1$2之类的
			  File fromPath=new File(srcPath+File.separator+path.substring(0, path.lastIndexOf(".")).replace(".", File.separator));
			  for(File f : fromPath.listFiles()){
				  if(f.isFile()&&f.getName().contains(fromFile.getName().substring(0, fromFile.getName().indexOf("."))+"$")){
					  String tempPath="D:"+File.separator+versionName+File.separator+path.substring(0, path.lastIndexOf(".")).replace(".", File.separator)+File.separator+f.getName();
					  File toFile1=new File(tempPath);
					  if(!toFile1.exists())
						  toFile1.createNewFile();
					  this.copyFile(f, toFile1);
				  }
			  }
		  }
		  //如果存在该压缩包，先删除
		  SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
		  String name=sdf.format(new Date());
		  int i=versionPath.indexOf("[");
		  versionPath=versionPath.replace(versionPath.substring(i+1, i+9), name);
		  File oFile=new File(versionPath);
		  if(oFile.exists())
			  oFile.delete();
		  //压缩目标源代码到rar版本包
		  Runtime.getRuntime().exec("cmd.exe /c rar a " + versionPath+" D:"+File.separator+versionName.substring(0, versionName.indexOf("\\")));
	  } catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
	  return true;
  }
}