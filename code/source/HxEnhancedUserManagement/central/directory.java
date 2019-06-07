package HxEnhancedUserManagement.central;

// -----( IS Java Code Template v1.2
// -----( CREATED: 2019-06-06 14:17:47 MDT
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

