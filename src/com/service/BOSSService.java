package com.service;

import com.base.Docx4jUtils;
import com.base.Logger;
import com.entity.BOSS;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BOSSService
{
  private String filePath;
  private Logger log;

  public BOSSService(String path)
  {
    this.filePath = path;
    this.log = new Logger(path);
  }
  public List<BOSS> readBoss(String inout) {
    File f = new File(this.filePath + File.separator + inout + ".txt");
    List list = new ArrayList();
    try {
      InputStreamReader isr = new InputStreamReader(new FileInputStream(f), "GBK");
      BufferedReader br = new BufferedReader(isr);
      String str = "";

      while ((str = br.readLine()) != null) {
        BOSS b = new BOSS();
        String[] temp = str.split("\t");
        b.setCode(temp[0].trim());
        b.setName(temp[(temp.length - 1)]);
        if (!b.isNull())
          list.add(b);
      }
    } catch (Exception e) {
      e.printStackTrace();
      this.log.error(e.getMessage());
    }
    return list;
  }

  public List<String> setTb_Srv_Intfield(String int_code, String int_name)
  {
    List list = new ArrayList();
    if (("".equals(int_name)) || (int_name == null))
      return list;
    List in = readBoss("in");
    List out = readBoss("out");
    for (int i = 1; i < in.size() + 1; i++) {
      BOSS temp = (BOSS)in.get(i - 1);
      list.add("INSERT INTO TB_SRV_INTFIELD (INTCODE,INTCNAME,FIELDENAME,FIELDCNAME,INOUT,ISMULTIROW,FIELDTYPE,SEQ,CONSTRAINTS) VALUES ('" + 
        int_code + "','" + int_name + "','" + temp.getCode() + "','" + temp.getName() + "'," + 
        "'in','0','5'," + i + ",NULL);");
    }
    for (int i = 1; i < out.size() + 1; i++) {
      BOSS temp = (BOSS)out.get(i - 1);
      list.add("INSERT INTO TB_SRV_INTFIELD (INTCODE,INTCNAME,FIELDENAME,FIELDCNAME,INOUT,ISMULTIROW,FIELDTYPE,SEQ,CONSTRAINTS) VALUES ('" + 
        int_code + "','" + int_name + "','" + temp.getCode() + "','" + temp.getName() + "'," + 
        "'out','0','5'," + i + ",NULL);");
    }
    return list;
  }

  public List<String[]> initOther()
  {
    File f = new File(this.filePath + File.separator + "other.txt");
    List list = new ArrayList();
    try {
      InputStreamReader isr = new InputStreamReader(new FileInputStream(f), "GBK");
      BufferedReader br = new BufferedReader(isr);
      String str = "";
      boolean flag = true;
      int i = 0;
      while ((str = br.readLine()) != null) {
        String[] temp = str.split("\t");
        if (flag) {
          i = temp.length;
          flag = false;
        }
        String[] tem = new String[i];
        if (temp.length < i)
          System.arraycopy(temp, 0, tem, 0, temp.length);
        else
          tem = temp;
        for (int j = 0; j < tem.length; j++) {
          if (tem[j] == null)
            tem[j] = "";
        }
        list.add(tem);
      }
    } catch (Exception e) {
      e.printStackTrace();
      this.log.error(e.getMessage());
    }
    return list;
  }

  public String initSQLOther() {
    StringBuffer sql = new StringBuffer();
    List<String[]> list = initOther();
    String[] temp = { "attr1", "attr2", "attr3", "attr4", "attr5", "attr6", "attr7", "attr8", "attr9", "attr10" };
    sql.append("select * from (\r");
    for (String[] strs : list) {
      sql.append("select ");
      for (int i = 0; i < strs.length; i++) {
        sql.append("'" + strs[i] + "' as " + temp[i] + ",");
      }
      sql.deleteCharAt(sql.length() - 1);
      sql.append(" from dual union all \r");
    }
    sql.delete(sql.length() - 11, sql.length() - 1);
    sql.append(")");
    return sql.toString();
  }
  public void createDocx(Map map){
	  Docx4jUtils.writeDocx(map,readBoss("in"), readBoss("inm"), readBoss("out"), readBoss("outm"));
  }
}