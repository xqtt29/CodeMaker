package com.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.base.Logger;

public class FileService {
	
	private String filePath;
	private Logger log;

	public FileService(String path)
	{
		this.filePath = path;
		this.log = new Logger(path);
	}
	
	public List<Map> readFileAllInfo(String fileName) {
	    File f = new File(this.filePath + File.separator + fileName + ".txt");
	    List<Map> list = new ArrayList<Map>();
	    try {
	        InputStreamReader isr = new InputStreamReader(new FileInputStream(f), "GBK");
	        BufferedReader br = new BufferedReader(isr);
	        String str = "";

	        while ((str = br.readLine()) != null) {
		        Map map = new HashMap();
		        String[] temp = str.split("\t");
		        for(int i = 1 ; i <= temp.length ; i++){
			        if (temp[i-1].length()!=0){
			        	map.put(i, temp[i-1]);
			        }
		        }
		        if(!map.isEmpty())
		        	list.add(map);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        this.log.error(e.getMessage());
	    }
	    return list;
	}

	public List<Map> readFileFirstLast(String fileName) {
	    File f = new File(this.filePath + File.separator + fileName + ".txt");
	    List<Map> list = new ArrayList<Map>();
	    try {
	        InputStreamReader isr = new InputStreamReader(new FileInputStream(f), "GBK");
	        BufferedReader br = new BufferedReader(isr);
	        String str = "";

	        while ((str = br.readLine()) != null) {
		        Map map = new HashMap();
		        String[] temp = str.split("\t");
		        map.put(1,temp[0].trim());
		        map.put(2,temp[(temp.length - 1)]);
		        if(!map.isEmpty())
		        	list.add(map);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        this.log.error(e.getMessage());
	    }
	    return list;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FileService fs=new FileService("E:\\tempfunc\\");
		List<Map> list=fs.readFileFirstLast("in");
		for(Map map : list){
			System.out.println(map);
		}
	}

}
