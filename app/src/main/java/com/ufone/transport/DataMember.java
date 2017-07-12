package com.ufone.transport;


import org.ksoap2.serialization.SoapObject;

public class DataMember {


	public static String getProperty(SoapObject soap_obj, String string) {
		String str = " ";
		if(soap_obj!=null){
			try{
				str = soap_obj.getProperty(string).toString();
			}
			catch (Exception e) {
				str = " ";
			}
		}
		return str;
	}


	/*public List<Destination> getOrganizations(){

		
				Connection conection = new Connection("GetOrganizations");
				conection.Connect();
				SoapObject soapObject = conection.Result();

				if (soapObject !=null) {
					int count = ((SoapObject) soapObject).getPropertyCount();
					organizaitoList = new ArrayList<Destination>();
					for (int i = 0; i < count; i++) {
						Destination organizationVO = new Destination();
						organizationVO.setOrganizationId(DataMember.getProperty((SoapObject)soapObject.getProperty(i), "OrganizationId"));
						organizationVO.setOrganizationName(DataMember.getProperty((SoapObject)soapObject.getProperty(i), "OrganizationName"));
						organizaitoList.add(organizationVO);
					}

				}
			


		return organizaitoList;
	}

	public List<OrganizationVO> getSites(final String org_id){

				Connection conection = new Connection("GetOrganizationsSites");
				conection.addProperties("org_id", org_id);
				conection.Connect();
				SoapObject soapObject = conection.Result();

				if (soapObject !=null) {
					int count = ((SoapObject) soapObject).getPropertyCount();
					organizaitoList = new ArrayList<OrganizationVO>();
					for (int i = 0; i < count; i++) {
						OrganizationVO organizationVO = new OrganizationVO();
						organizationVO.setSiteName(DataMember.getProperty((SoapObject)soapObject.getProperty(i), "School_Name"));
						organizationVO.setSiteAddress(DataMember.getProperty((SoapObject)soapObject.getProperty(i), "school_address"));
						organizaitoList.add(organizationVO);
					}

				}
	
		return organizaitoList;

	}*/

}