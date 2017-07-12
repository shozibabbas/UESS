package com.ufone.transport;

import java.io.IOException;

import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.content.Context;

public class Connection{      

	public static String Soap_Action="";
	public static String Method="";
	public static  String NameSpace="http://tempuri.org/";
	private SoapObject object;
	private static SoapObject result; 
	private SoapSerializationEnvelope envelop;
	public static boolean isValidUser= true;
	String name ;
	String userNameHeader ;
	String passwordHeader ; 
	public static String multipleSaveResult;
	public static String URL = "http://172.16.105.190/WebService.asmx" ;
	com.ufone.transport.HttpsTransporSE androidHttpsTransport = null;
	HttpTransportSE androidHttpTransport = null;
	public Connection(String method){         

		NameSpace="http://tempuri.org/";

		Method=method;    
		Soap_Action=NameSpace+Method;                 
		object=new SoapObject(NameSpace, Method);  

		envelop=new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelop.dotNet=true;

		//envelop.headerOut = new Element[1];   
		//envelop.headerOut[0] =  buildAuthHeader();	

	}
	public void connect(){        
		try { 
			envelop.setOutputSoapObject(object);           

			if(URL.startsWith("https"))
			{
				androidHttpsTransport = new com.ufone.transport.HttpsTransporSE(URL, 0, "", 30000);  
				androidHttpsTransport.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
				androidHttpsTransport.debug=true;
				androidHttpsTransport.call( Soap_Action, envelop); 
			}
			else
			{
				androidHttpTransport = new HttpTransportSE(URL) ; //for Local
				androidHttpTransport.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
				androidHttpTransport.debug=true;
				androidHttpTransport.call( Soap_Action, envelop); 
			}

			try {
				result=(SoapObject)envelop.bodyIn; 
				result=(SoapObject)result.getProperty(0);
				result=(SoapObject)result.getProperty(1);
				result=(SoapObject)result.getProperty(0);
				if(!isValidUser){
					isValidUser = true ;
				}
			}    
			catch (Exception e) {   	 
				isValidUser = false;
			} 
		}
		catch (Exception e) { 
		}
	}
	public SoapObject Result(){
		return result;
	}  
	public void cancelKsoap() throws IOException
	{
		if(androidHttpsTransport!=null){
			androidHttpsTransport.reset();
			androidHttpsTransport.getConnection().disconnect();
		}else if(androidHttpTransport!=null){
			androidHttpTransport.reset();
			androidHttpTransport.getConnection().disconnect();
		}
	}
	public void connectForSingleNode(){
		try {
			envelop.setOutputSoapObject(object);
			if(URL.startsWith("https"))
			{
				com.ufone.transport.HttpsTransporSE androidHttpTransport = new com.ufone.transport.HttpsTransporSE(URL, 0, "", 30000);  
				androidHttpTransport.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
				androidHttpTransport.debug=true;
				androidHttpTransport.call( Soap_Action, envelop); 
			}
			else
			{
				HttpTransportSE androidHttpTransport = new HttpTransportSE(URL) ; //for Local
				androidHttpTransport.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
				androidHttpTransport.debug=true;
				androidHttpTransport.call( Soap_Action, envelop); 
			}

			result = null ;
			result=(SoapObject)envelop.bodyIn;
			//result=(SoapObject)result.getProperty(0);

		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getNameSpace(){
		return NameSpace;
	}

	public void addProperties(String name,Object value){  
		object.addProperty(name, value);  
	}
}