package HxEnhancedUserManagement.central;

// -----( IS Java Code Template v1.2
// -----( CREATED: 2019-06-08 09:40:34 MDT
// -----( ON-HOST: 192.168.241.213

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import com.wm.app.b2b.server.cds.CDSUserManager;
import com.webmethods.sc.directory.DirectoryException;
import com.webmethods.sc.directory.IDirectoryRole;
import com.webmethods.sc.directory.IDirectoryService;
import com.webmethods.sc.directory.IDirectorySession;
import com.webmethods.sc.directory.IDirectoryPrincipal;
import com.webmethods.sc.directory.IDirectoryUser;
import com.softwareag.util.IDataMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
// --- <<IS-END-IMPORTS>> ---

public final class directory

{
	// ---( internal utility methods )---

	final static directory _instance = new directory();

	static directory _newInstance() { return new directory(); }

	static directory _cast(Object o) { return (directory)o; }

	// ---( server methods )---




	public static final void getIdByName (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(getIdByName)>> ---
		// @sigtype java 3.5
		// [i] field:0:required directoryServiceName
		// [o] field:0:required directoryServiceId
		IDataMap pipelineMap = new IDataMap(pipeline);
		String directoryServiceName = pipelineMap.getAsString("directoryServiceName");
		
		String directoryServiceId = null;
		IDirectorySession session = CDSUserManager.getSession();
		List<IDirectoryService> directoryServices = session.listDirectoryServices();
		for (IDirectoryService directoryService : directoryServices){
			if (directoryService.getName().equals(directoryServiceName)){
				directoryServiceId = directoryService.getID();
			}
		}
		
		pipelineMap.put("directoryServiceId", directoryServiceId);
		// --- <<IS-END>> ---

                
	}



	public static final void getNameById (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(getNameById)>> ---
		// @sigtype java 3.5
		// [i] field:0:required directoryServiceId
		// [o] field:0:required directoryServiceName
		IDataMap pipelineMap = new IDataMap(pipeline);
		String directoryServiceId = pipelineMap.getAsString("directoryServiceId");
		
		String directoryServiceName = null;
		IDirectorySession session = CDSUserManager.getSession();
		IDirectoryService directoryService = session.getDirectoryService(directoryServiceId);
		if (directoryService != null){
			directoryServiceName = directoryService.getName();
		}
		
		pipelineMap.put("directoryServiceName", directoryServiceName);
		// --- <<IS-END>> ---

                
	}



	public static final void listDirecotryServices (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(listDirecotryServices)>> ---
		// @sigtype java 3.5
		// [o] record:1:required directoryServices
		// [o] - field:0:required id
		// [o] - field:0:required name
		IDirectorySession session = CDSUserManager.getSession();
		List<IDirectoryService> directoryServices = session.listDirectoryServices();
		List<IData> directoryServicesData = new ArrayList<IData>();
		for (IDirectoryService directoryService : directoryServices){
			IData directoryServiceData = IDataFactory.create();
			directoryServicesData.add(directoryServiceData);
			IDataMap directoryServiceDataMap = new IDataMap(directoryServiceData);
			directoryServiceDataMap.put("id", directoryService.getID());
			directoryServiceDataMap.put("name", directoryService.getName());
		}
		
		IDataMap pipelineMap = new IDataMap(pipeline);
		pipelineMap.put("directoryServices", directoryServicesData.toArray(new IData[directoryServicesData.size()]));
		// --- <<IS-END>> ---

                
	}
}

