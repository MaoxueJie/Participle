package com.gohda.participle;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Participle {
	public static void main(String args[]) throws Exception
	{
		
		URL url =Participle.class.getClassLoader().getResource("condition.txt");
		File address = new File(url.getFile());
		BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(address),"UTF-8"));
		String str = null;
		List<String> addressList = new ArrayList<String>();
	    while((str = br.readLine()) != null){
	    	if (str!=null && !"".equals(str.trim()))
	    		addressList.add(str);//
	    }
	    br.close(); 
	    Map<Integer,String> mMap = new HashMap<Integer,String>();
	    Map<String,Map<String,Object>> ciMap = new HashMap<String,Map<String,Object>>();
	    for(int m=0;m<addressList.size();m++) {
	    	String add = addressList.get(m);
	    	for(int i=2;i<=add.length();i++)
	    	{
	    		for(int j=0;j<add.length();j++)
		    	{
	    			if (j+i<=add.length()) {
	    				String temp = add.substring(j, j+i).trim();
	    				if (temp.length()>0)
	    				{
		    				if (isAllChinese(temp))
			    				if (ciMap.get(temp)==null) {
			    					Map<String,Object> ciInfo = new HashMap<String,Object>();
		    						ciInfo.put("name", temp);
		    						ciInfo.put("count", 1);
		    						List<Integer> indexs = new ArrayList<Integer>();
		    						indexs.add(m);
		    						ciInfo.put("indexs",indexs);
			    					ciMap.put(temp, ciInfo);
			    					if (mMap.get(m)==null)
			    					{
			    						mMap.put(m,add);
			    					}
			    				}
			    				else
			    				{
			    					Map<String,Object> ciInfo = ciMap.get(temp);
			    					ciInfo.put("count", (Integer)(ciInfo.get("count"))+1);
			    					List indexs =(List)(ciInfo.get("indexs"));
			    					if (!(indexs.contains(m)))
			    						indexs.add(m);
			    					if (mMap.get(m)==null)
			    					{
			    						mMap.put(m,add);
			    					}
			    				}
		    				}
	    			}else
	    			{
	    				break;
	    			}
		    	}
	    	}
	    }
	    

	    
	    List<Map.Entry<String,Map<String,Object>>> ci = new ArrayList<Map.Entry<String,Map<String,Object>>>();
	    
	    for(Map.Entry<String,Map<String,Object>> entry:ciMap.entrySet())
	    {
	    	if (entry.getKey().length()>=2 && !"".equals(entry.getKey().trim()))
	    	{
	    		boolean s = false;
	    		for(Integer m:(List<Integer>)entry.getValue().get("indexs"))
	    		{
	    			String add = mMap.get(m);
					if (add.indexOf(entry.getKey())>0)
					{
						s = s||false;
					}else
					{
						s = s||true;
					}
	    		}
	    		if (s)
	    			ci.add(entry);
	    	}
	    }
	    
	    Collections.sort(ci, new Comparator<Map.Entry<String,Map<String,Object>>>(){

			public int compare(Entry<String, Map<String,Object>> o1, Entry<String, Map<String,Object>> o2) {
				
				return o2.getKey().compareTo(o1.getKey());
				
			}
	    	
	    });
	    
	    /*
	    Map.Entry<String,Map<String,Object>> tempCi = null;
	    Iterator<Map.Entry<String,Map<String,Object>>> it = ci.iterator();
	    while(it.hasNext())
	    {
	    	Map.Entry<String,Map<String,Object>> entry = it.next();
	    	if (tempCi == null)
	    	{
	    		tempCi = entry;
	    	}else
	    	{
	    		if (tempCi.getKey().indexOf(entry.getKey()) == 0)
	    		{
	    			if (tempCi.getValue().get("count").equals(entry.getValue().get("count")))
	    			{
	    				it.remove();
	    			}else
	    			{
	    				tempCi = entry;
	    			}
	    		}else
	    		{
	    			tempCi = entry;
	    		}
	    	}
	    }*/
	    
	    Collections.sort(ci, new Comparator<Map.Entry<String,Map<String,Object>>>(){

			public int compare(Entry<String, Map<String,Object>> o1, Entry<String, Map<String,Object>> o2) {
				
				return o1.getKey().compareTo(o2.getKey());
				
			}
	    	
	    });
	    /*
	    tempCi = null;
	    it = ci.iterator();
	    while(it.hasNext())
	    {
	    	Map.Entry<String,Map<String,Object>> entry = it.next();
	    	if (tempCi == null)
	    	{
	    		tempCi = entry;
	    	}else
	    	{
	    		if (entry.getKey().indexOf(tempCi.getKey()) == 0)
	    		{
	    			
	    			it.remove();
	    		}else
	    		{
	    			tempCi = entry;
	    		}
	    	}
	    }
	    */
	    for(Map.Entry<String,Map<String,Object>> entry:ci)
	    {
	    	if ((Integer)entry.getValue().get("count")>0)
	    		System.out.println(entry.getKey() + ": " + entry.getValue().get("count"));
	    }

	    System.out.println("¹²" + ci.size()+"Ìõ¼ÇÂ¼");
	}
	
	public static boolean isAllChinese(String str)
	{
		for(int i=0;i<str.length();i++)
		{
			char ch = str.charAt(i);
			if (!(ch >= 0x4e00 && ch <= 0x9fa5))
				return false;
		}
		return true;
	}
}
