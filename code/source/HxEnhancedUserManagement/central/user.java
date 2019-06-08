package HxEnhancedUserManagement.central;

// -----( IS Java Code Template v1.2
// -----( CREATED: 2019-06-08 09:34:21 MDT
// -----( ON-HOST: 192.168.241.179

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import com.wm.app.b2b.server.cds.CDSUserManager;
import com.webmethods.sc.directory.DirectoryException;
import com.webmethods.sc.directory.DirectorySearchQuery;
import com.webmethods.sc.directory.IDirectoryPagingCookie;
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

public final class user

{
	// ---( internal utility methods )---

	final static user _instance = new user();

	static user _newInstance() { return new user(); }

	static user _cast(Object o) { return (user)o; }

	// ---( server methods )---




	public static final void authenticateUser (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(authenticateUser)>> ---
		// @sigtype java 3.5
		// [i] field:0:required username
		// [i] field:0:required password
		// [o] field:0:required authorized
		// [o] field:0:optional directoryServiceName
		IDataMap pipelineMap = new IDataMap(pipeline);
		String username = pipelineMap.getAsString("username");
		String password = pipelineMap.getAsString("password");
		
		IDirectorySession session = CDSUserManager.getSession();
		try{
			IDirectoryUser user = session.authenticateUser(username, password);
			if (user == null){
				pipelineMap.put("authorized", "false");
			}else{
				pipelineMap.put("authorized", "true");
				if (user.getDirectoryService() != null){
					pipelineMap.put("directoryServiceName", user.getDirectoryService().getName());
				}
			}
		}catch(DirectoryException e){
			pipelineMap.put("authorized", "false");
		}
		// --- <<IS-END>> ---

                
	}



	public static final void createUser (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(createUser)>> ---
		// @sigtype java 3.5
		// [i] field:0:required username
		// [i] field:0:required password
		// [i] field:0:required directoryServiceId
		// [i] field:0:required firstName
		// [i] field:0:required lastName
		// [i] field:0:optional email
		// [i] record:1:optional attributes
		// [i] - field:0:required key
		// [i] - field:0:required value
		// [o] field:0:required userId
		IDataMap pipelineMap = new IDataMap(pipeline);
		String username = pipelineMap.getAsString("username");
		String password = pipelineMap.getAsString("password");
		String directoryServiceId = pipelineMap.getAsString("directoryServiceId");
		String firstName = pipelineMap.getAsString("firstName");
		String lastName = pipelineMap.getAsString("lastName");
		String email = pipelineMap.getAsString("email");
		IData[] attributesData = pipelineMap.getAsIDataArray("attributes");
		
		if (username != null && password != null && directoryServiceId != null && firstName != null && lastName != null){
			IDirectorySession session = CDSUserManager.getSession();
			Properties attributes = new Properties();
			attributes.put("password",password);
			attributes.put("firstname", firstName);
			attributes.put("lastname", lastName);
			if (email != null){
				attributes.put("email", email);
			}
			if (attributesData != null){
				for (IData attributeData : attributesData){
					IDataMap attributeDataMap = new IDataMap(attributeData);
					String key = attributeDataMap.getAsString("key");
					String value = attributeDataMap.getAsString("value");
					attributes.put(key, value);
				}
			}
			IDirectoryPrincipal user = session.createPrincipal(directoryServiceId, IDirectoryPrincipal.TYPE_USER, username, attributes);
			pipelineMap.put("userId", user.getID());
		}else{
			throw new ServiceException("Found null input.");
		}
			
		// --- <<IS-END>> ---

                
	}



	public static final void deleteUser (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(deleteUser)>> ---
		// @sigtype java 3.5
		// [i] field:0:required username
		IDataMap pipelineMap = new IDataMap(pipeline);
		String username = pipelineMap.getAsString("username");
		
		IDirectorySession session = CDSUserManager.getSession();
		IDirectoryPrincipal principal = session.lookupPrincipalByName(username, IDirectoryPrincipal.TYPE_USER );
		
		if (principal != null){
			session.deletePrincipal(principal.getID());
		}
			
		// --- <<IS-END>> ---

                
	}



	public static final void getUserId (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(getUserId)>> ---
		// @sigtype java 3.5
		// [i] field:0:required username
		// [o] field:0:required userId
		IDataMap pipelineMap = new IDataMap(pipeline);
		String username = pipelineMap.getAsString("username");
		
		IDirectorySession session = CDSUserManager.getSession();
		IDirectoryPrincipal principal = session.lookupPrincipalByName(username, IDirectoryPrincipal.TYPE_USER );
		
		if (principal != null){
		   pipelineMap.put("userId", principal.getID());
		}
		// --- <<IS-END>> ---

                
	}



	public static final void readUser (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(readUser)>> ---
		// @sigtype java 3.5
		// [i] field:0:required username
		// [o] field:0:required firstName
		// [o] field:0:required lastName
		// [o] field:0:optional email
		// [o] record:1:optional attributes
		// [o] - field:0:required key
		// [o] - field:0:required value
		IDataMap pipelineMap = new IDataMap(pipeline);
		String username = pipelineMap.getAsString("username");
		
		if (username != null){
			IDirectorySession session = CDSUserManager.getSession();
			IDirectoryPrincipal principal = session.lookupPrincipalByName(username, IDirectoryPrincipal.TYPE_USER );
			if (principal != null && principal instanceof IDirectoryUser){
				IDirectoryUser user = (IDirectoryUser)principal;
				pipelineMap.put("firstName", user.getFirstName());
				pipelineMap.put("lastName", user.getLastName());
				pipelineMap.put("email", user.getEmail());
				List<IData> attributes = new ArrayList<IData>();
				for (Entry<String, Object> attribute : user.getAllAttributes().entrySet()){
					IData attributeData = IDataFactory.create();
					IDataMap attributeDataMap = new IDataMap(attributeData);
					attributeDataMap.put("key", attribute.getKey());
					attributeDataMap.put("value", String.valueOf(attribute.getValue()));
					attributes.add(attributeData);
				}
				pipelineMap.put("attributes", attributes.toArray(new IData[attributes.size()]));
			}
		}else{
				throw new ServiceException("Found null input.");
			}
		// --- <<IS-END>> ---

                
	}



	public static final void searchUsers (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(searchUsers)>> ---
		// @sigtype java 3.5
		// [i] field:0:required searchString
		// [i] field:0:required directoryServiceId
		// [o] record:1:required users
		// [o] - field:0:required username
		// [o] - field:0:required firstName
		// [o] - field:0:required lastName
		// [o] - field:0:optional email
		// [o] - field:0:required directoryServiceId
		IDataMap pipelineMap = new IDataMap(pipeline);
		String searchString = pipelineMap.getAsString("searchString");
		
		List<IData> users = new ArrayList<IData>();
		
		IDirectorySession session = CDSUserManager.getSession();
		String directoryServiceId = pipelineMap.getAsString("directoryServiceId");
		
		if (directoryServiceId != null && !directoryServiceId.isEmpty()){
			IDirectoryPagingCookie pgCookie = session.createPagingCookie(directoryServiceId);
			pgCookie.setPageSize(-1);
			List<IDirectoryPrincipal> results = session.searchDirectory(directoryServiceId, IDirectoryPrincipal.TYPE_USER, new DirectorySearchQuery(searchString, -1, null), pgCookie);
			for (IDirectoryPrincipal result : results){
				if (result instanceof IDirectoryUser){
					IDirectoryUser user = (IDirectoryUser)result;
					IData userData = IDataFactory.create();
					IDataMap userDataMap = new IDataMap(userData);
					userDataMap.put("username", user.getName());
					userDataMap.put("firstName", user.getFirstName());
					userDataMap.put("lastName", user.getLastName());
					if (user.getEmail() != null && !user.getEmail().isEmpty()){
						userDataMap.put("email", user.getEmail());
					}
					userDataMap.put("directoryServiceId", directoryServiceId);
					users.add(userData);
				}
			}
		}else{
			for (IDirectoryService directoryService : session.listDirectoryServices()){
				IDirectoryPagingCookie pgCookie = session.createPagingCookie(directoryService.getID());
				pgCookie.setPageSize(-1);
				List<IDirectoryPrincipal> results = session.searchDirectory(directoryService.getID(), IDirectoryPrincipal.TYPE_USER, new DirectorySearchQuery(searchString, -1, null), pgCookie);
				for (IDirectoryPrincipal result : results){
					if (result instanceof IDirectoryUser){
						IDirectoryUser user = (IDirectoryUser)result;
						IData userData = IDataFactory.create();
						IDataMap userDataMap = new IDataMap(userData);
						userDataMap.put("username", user.getName());
						userDataMap.put("firstName", user.getFirstName());
						userDataMap.put("lastName", user.getLastName());
						if (user.getEmail() != null && !user.getEmail().isEmpty()){
							userDataMap.put("email", user.getEmail());
						}
						userDataMap.put("directoryServiceId", directoryService.getID());
						users.add(userData);
					}
				}
			}
		}
		
		pipelineMap.put("users", users.toArray(new IData[users.size()]));
		// --- <<IS-END>> ---

                
	}



	public static final void updatePassword (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(updatePassword)>> ---
		// @sigtype java 3.5
		// [i] field:0:required username
		// [i] field:0:required password
		IDataMap pipelineMap = new IDataMap(pipeline);
		String username = pipelineMap.getAsString("username");
		String password = pipelineMap.getAsString("password");
		
		IDirectorySession session = CDSUserManager.getSession();
		IDirectoryPrincipal principal = session.lookupPrincipalByName(username, IDirectoryPrincipal.TYPE_USER );
		if (principal != null){
		    Map attributes = principal.getAllAttributes();
		    attributes.put("password", password);
		    session.modifyPrincipal(principal.getID(), attributes);
		}else{
			throw new ServiceException("Not found user " + username);
		}
		// --- <<IS-END>> ---

                
	}



	public static final void updateUser (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(updateUser)>> ---
		// @sigtype java 3.5
		// [i] field:0:required username
		// [i] field:0:optional password
		// [i] field:0:optional firstName
		// [i] field:0:optional lastName
		// [i] field:0:optional email
		// [i] record:1:optional attributes
		// [i] - field:0:required key
		// [i] - field:0:required value
		IDataMap pipelineMap = new IDataMap(pipeline);
		String username = pipelineMap.getAsString("username");
		String password = pipelineMap.getAsString("password");
		String firstName = pipelineMap.getAsString("firstName");
		String lastName = pipelineMap.getAsString("lastName");
		String email = pipelineMap.getAsString("email");
		IData[] attributesData = pipelineMap.getAsIDataArray("attributes");
		
		IDirectorySession session = CDSUserManager.getSession();
		IDirectoryPrincipal principal = session.lookupPrincipalByName(username, IDirectoryPrincipal.TYPE_USER );
		if (principal != null){
		    Map attributes = principal.getAllAttributes();
		    if (password != null){
				attributes.put("password",password);
		    }
		    if (firstName != null){
				attributes.put("firstname", firstName);
		    }
		    if (lastName != null){
				attributes.put("lastname", lastName);
		    }
			if (email != null){
				attributes.put("email", email);
			}
			if (attributesData != null){
				for (IData attributeData : attributesData){
					IDataMap attributeDataMap = new IDataMap(attributeData);
					String key = attributeDataMap.getAsString("key");
					String value = attributeDataMap.getAsString("value");
					attributes.put(key, value);
				}
			}
		    session.modifyPrincipal(principal.getID(), attributes);
		}else{
			throw new ServiceException("Not found user " + username);
		}
		// --- <<IS-END>> ---

                
	}
}

