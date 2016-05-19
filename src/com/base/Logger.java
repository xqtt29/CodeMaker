package com.base;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
	private String path;
	public Logger(String path){
		this.path=path;
	}
	private void write(String data){
		try {
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy_MM_dd");
			String date=sdf.format(new Date());
			File fp=new File(path + "log");
			if(!fp.exists())
				fp.mkdirs();
			File f = new File(path + "log\\" + date + ".log");
			if (!f.exists())
				f.createNewFile();
			BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f,true),"gbk"));
			bw.write("\r\n");
			bw.write(data);
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("write() --->"+e.getMessage());
		}
	}
	public void error(String err){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date=sdf.format(new Date());
		write(date+"  [error]  "+err);
	}
	public void info(String info){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date=sdf.format(new Date());
		write(date+"  [info]  "+info);
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
}
