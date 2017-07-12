package com.ufone.transport;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import android.util.Log;


public class DatasetParser {
	
	private String[] Headers;
	private String[][] ParsedData;
	private SoapObject objresponse;
	
	public DatasetParser(SoapObject data) {
	
		try
		{
			objresponse=data;
			ParseData();
		}catch(Exception e)
		{

			String err = (e.getMessage()==null)?"User":e.getMessage(); 
			Log.e("User",err);  
			objresponse=null;
		}
	}
	 
	private void ParseData(){
		
		SoapObject tempobj=(SoapObject)objresponse.getProperty(1);
		SoapObject tempobj2=(SoapObject)tempobj.getProperty(0);
		objresponse=tempobj2;			
	}
	
	public String[] GetColumnNames()
	{
		return Headers;
	}
	
	public String[][] GetParsedData()
	{
		return ParsedData;
	}
	
	public SoapObject GetRow(int Row)
	{
		SoapObject tempobj;
		try{
		tempobj=(SoapObject)objresponse.getProperty(Row);
		}catch(Exception e)
		{
				return null;
		}
		return tempobj;
	}
	
	public String GetValue(int Row,String Colname)
	{
		try{
		SoapObject tempobj=(SoapObject)objresponse.getProperty(Row);
		String Value=tempobj.getProperty(Colname).toString();
		
		if(Value.contains("anyType{}")||Value.trim().length()==0)
			return "";
		else
			return tempobj.getProperty(Colname).toString();
		}
		catch(Exception e)
		{
			Log.i("User", e.getMessage());
		}
		return "";
	}
	
	public String[] GetColmnames(int Row)
	{
		SoapObject tempobj=(SoapObject)objresponse.getProperty(Row);
		String[] Dataarr=new String[tempobj.getPropertyCount()];
		for(int i=0;i<tempobj.getPropertyCount();i++)
		{
			PropertyInfo pi=new PropertyInfo();
			tempobj.getPropertyInfo(i, pi);
			Dataarr[i]=pi.getName();
		}
		return Dataarr;
	}
	
	public int GetColCounts(int Row)
	{
		try{
			SoapObject tempobj=(SoapObject)objresponse.getProperty(Row);
			return tempobj.getPropertyCount();
			
			}catch(NullPointerException np)
			{
				return 0;
			}	
	}
	
	public int GetRowscount()
	{
		try{
		return objresponse.getPropertyCount();
		}catch
		(NullPointerException np)
		{
			return 0;
		}
	}
}
