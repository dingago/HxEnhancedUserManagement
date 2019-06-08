package HxEnhancedUserManagement.central;

// -----( IS Java Code Template v1.2
// -----( CREATED: 2019-06-08 09:31:44 MDT
// -----( ON-HOST: 192.168.241.179

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import com.wm.app.b2b.server.cds.CDSUserManager;
import com.webmethods.sc.directory.DirectoryException;
import com.webmethods.sc.directory.DirectorySearchQuery;
import com.webmethods.sc.directory.IDirectoryGroup;
import com.webmethods.sc.directory.IDirectoryPagingCookie;
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

public final class group

{
	// ---( internal utility methods )---

	final static group _instance = new group();

	static group _newInstance() { return new group(); }

	static group _cast(Object o) { return (group)o; }

	// ---( server methods )---




	public static final void addGroupToGroup (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(addGroupToGroup)>> ---
		// @sigtype java 3.5
		// [i] field:0:required childGroupName
		// [i] field:0:required parentGroupName
		IDataMap pipelineMap = new IDataMap(pipeline);
		String childGroupName = pipelineMap.getAsString("childGroupName");
		String parentGroupName = pipelineMap.getAsString("parentGroupName");
		
		IDirectorySession session = CDSUserManager.getSession();
		IDirectoryPrincipal childGroup = session.lookupPrincipalByName(childGroupName, IDirectoryPrincipal.TYPE_GROUP );
		IDirectoryPrincipal parentGroup = session.lookupPrincipalByName(parentGroupName, IDirectoryPrincipal.TYPE_GROUP );
		if (childGroup == null){
			throw new ServiceException("Not able to find child group " + childGroupName);
		}
		if (parentGroup == null){
			throw new ServiceException("Not able to find group " + parentGroupName);
		}
		session.addPrincipalToGroup(childGroup.getID(), parentGroup.getID());
		// --- <<IS-END>> ---

                
	}



	public static final void addUserToGroup (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(addUserToGroup)>> ---
		// @sigtype java 3.5
		// [i] field:0:required username
		// [i] field:0:required groupName
		IDataMap pipelineMap = new IDataMap(pipeline);
		String username = pipelineMap.getAsString("username");
		String groupName = pipelineMap.getAsString("groupName");
		
		IDirectorySession session = CDSUserManager.getSession();
		IDirectoryPrincipal user = session.lookupPrincipalByName(username, IDirectoryPrincipal.TYPE_USER );
		IDirectoryPrincipal group = session.lookupPrincipalByName(groupName, IDirectoryPrincipal.TYPE_GROUP );
		if (user == null){
			throw new ServiceException("Not able to find user " + username);
		}
		if (group == null){
			throw new ServiceException("Not able to find group " + group);
		}
		session.addPrincipalToGroup(user.getID(), group.getID());
		// --- <<IS-END>> ---

                
	}



	public static final void createGroup (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(createGroup)>> ---
		// @sigtype java 3.5
		// [i] field:0:required groupName
		// [i] field:0:required directoryServiceId
		// [i] record:1:optional attributes
		// [i] - field:0:required key
		// [i] - field:0:required value
		// [o] field:0:required groupId
		IDataMap pipelineMap = new IDataMap(pipeline);
		String groupName = pipelineMap.getAsString("groupName");
		String directoryServiceId = pipelineMap.getAsString("directoryServiceId");
		IData[] attributesData = pipelineMap.getAsIDataArray("attributes");
		
		if (groupName != null){
			IDirectorySession session = CDSUserManager.getSession();
			Properties attributes = new Properties();
			attributes.setProperty("groupname", groupName);
			if (attributesData != null){
				for (IData attributeData : attributesData){
					IDataMap attributeDataMap = new IDataMap(attributeData);
					String key = attributeDataMap.getAsString("key");
					String value = attributeDataMap.getAsString("value");
					attributes.put(key, value);
				}
			}
			IDirectoryPrincipal group = session.createPrincipal(directoryServiceId, IDirectoryPrincipal.TYPE_GROUP, groupName, attributes);
			pipelineMap.put("groupId", group.getID());
		}else{
			throw new ServiceException("Found null input.");
		}
			
		// --- <<IS-END>> ---

                
	}



	public static final void deleteGroup (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(deleteGroup)>> ---
		// @sigtype java 3.5
		// [i] field:0:required groupName
		IDataMap pipelineMap = new IDataMap(pipeline);
		String groupName = pipelineMap.getAsString("groupName");
		
		IDirectorySession session = CDSUserManager.getSession();
		IDirectoryPrincipal principal = session.lookupPrincipalByName(groupName, IDirectoryPrincipal.TYPE_GROUP );
		
		if (principal != null){
			session.deletePrincipal(principal.getID());
		}
			
		// --- <<IS-END>> ---

                
	}



	public static final void getBelongedGroupsByGroup (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(getBelongedGroupsByGroup)>> ---
		// @sigtype java 3.5
		// [i] field:0:required groupName
		// [o] field:1:required groupNames
		IDataMap pipelineMap = new IDataMap(pipeline);
		String groupName = pipelineMap.getAsString("groupName");
		
		IDirectorySession session = CDSUserManager.getSession();
		IDirectoryPrincipal principal = session.lookupPrincipalByName(groupName, IDirectoryPrincipal.TYPE_GROUP );
		
		if (principal != null){
			List<IDirectoryGroup> groups = session.getGroupMembership(principal.getID());
			String[] groupNames = new String[groups.size()];
			for (int i = 0; i < groupNames.length; i++){
				groupNames[i] = groups.get(i).getName();
			}
			pipelineMap.put("groupNames", groupNames);
		}
			
		// --- <<IS-END>> ---

                
	}



	public static final void getBelongedGroupsByUser (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(getBelongedGroupsByUser)>> ---
		// @sigtype java 3.5
		// [i] field:0:required username
		// [o] field:1:required groupNames
		IDataMap pipelineMap = new IDataMap(pipeline);
		String username = pipelineMap.getAsString("username");
		
		IDirectorySession session = CDSUserManager.getSession();
		IDirectoryPrincipal principal = session.lookupPrincipalByName(username, IDirectoryPrincipal.TYPE_USER );
		
		if (principal != null){
			List<IDirectoryGroup> groups = session.getGroupMembership(principal.getID());
			String[] groupNames = new String[groups.size()];
			for (int i = 0; i < groupNames.length; i++){
				groupNames[i] = groups.get(i).getName();
			}
			pipelineMap.put("groupNames", groupNames);
		}
			
		// --- <<IS-END>> ---

                
	}



	public static final void getGroupMembers (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(getGroupMembers)>> ---
		// @sigtype java 3.5
		// [i] field:0:required groupName
		// [o] record:1:required members
		// [o] - field:0:required id
		// [o] - field:0:required name
		// [o] - field:0:required type
		// [o] - field:0:optional directoryService
		IDataMap pipelineMap = new IDataMap(pipeline);
		String groupName = pipelineMap.getAsString("groupName");
		
		IDirectorySession session = CDSUserManager.getSession();
		IDirectoryPrincipal principal = session.lookupPrincipalByName(groupName, IDirectoryPrincipal.TYPE_GROUP );
		
		if (principal != null){
			List<IDirectoryPrincipal> members = session.getMembers(principal.getID());
			List<IData> membersData = new ArrayList<IData>();
			for (IDirectoryPrincipal member : members){
				String type = null;
				switch (member.getType()){
				case IDirectoryPrincipal.TYPE_USER:
					type = "user";
					break;
				case IDirectoryPrincipal.TYPE_GROUP:
					type = "group";
					break;
				case IDirectoryPrincipal.TYPE_ROLE:
					type = "role";
					break;
				}
				IData memberData = IDataFactory.create();
				IDataMap memberDataMap = new IDataMap(memberData);
				memberDataMap.put("id", member.getID());
				memberDataMap.put("name", member.getName());
				memberDataMap.put("type", type);
				if (member.getDirectoryService() != null){
					memberDataMap.put("directoryService", member.getDirectoryService().getName());
				}
				membersData.add(memberData);
			}
			pipelineMap.put("members", membersData.toArray(new IData[membersData.size()]));
		}
			
		// --- <<IS-END>> ---

                
	}



	public static final void listGroups (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(listGroups)>> ---
		// @sigtype java 3.5
		// [o] record:1:required groups
		// [o] - field:0:required id
		// [o] - field:0:required name
		// [o] - field:0:required directoryServiceId
		List<IData> groups = new ArrayList<IData>();
		
		IDirectorySession session = CDSUserManager.getSession();
		for (IDirectoryService directoryService : session.listDirectoryServices()){
			IDirectoryPagingCookie pgCookie = session.createPagingCookie(directoryService.getID());
			pgCookie.setPageSize(-1);
			List<IDirectoryPrincipal> results = session.searchDirectory(directoryService.getID(), IDirectoryPrincipal.TYPE_GROUP, new DirectorySearchQuery(null, 200, null), pgCookie);
			for (IDirectoryPrincipal result : results){
				IData group = IDataFactory.create();
				IDataMap groupMap = new IDataMap(group);
				groupMap.put("id", result.getID());
				groupMap.put("name", result.getName());
				groupMap.put("directoryServiceId", directoryService.getID());
				groups.add(group);
			}
		}
		
		IDataMap pipelineMap = new IDataMap(pipeline);
		pipelineMap.put("groups", groups.toArray(new IData[groups.size()]));
		// --- <<IS-END>> ---

                
	}



	public static final void removeGroupFromGroup (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(removeGroupFromGroup)>> ---
		// @sigtype java 3.5
		// [i] field:0:required childGroupName
		// [i] field:0:required parentGroupName
		IDataMap pipelineMap = new IDataMap(pipeline);
		String childGroupName = pipelineMap.getAsString("childGroupName");
		String parentGroupName = pipelineMap.getAsString("parentGroupName");
		
		IDirectorySession session = CDSUserManager.getSession();
		IDirectoryPrincipal childGroup = session.lookupPrincipalByName(childGroupName, IDirectoryPrincipal.TYPE_GROUP );
		IDirectoryPrincipal parentGroup = session.lookupPrincipalByName(parentGroupName, IDirectoryPrincipal.TYPE_GROUP );
		if (childGroup == null){
			throw new ServiceException("Not able to find child group " + childGroupName);
		}
		if (parentGroup == null){
			throw new ServiceException("Not able to find group " + parentGroupName);
		}
		session.removePrincipalFromGroup(childGroup.getID(), parentGroup.getID());
		// --- <<IS-END>> ---

                
	}



	public static final void removeUserFromGroup (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(removeUserFromGroup)>> ---
		// @sigtype java 3.5
		// [i] field:0:required username
		// [i] field:0:required groupName
		IDataMap pipelineMap = new IDataMap(pipeline);
		String username = pipelineMap.getAsString("username");
		String groupName = pipelineMap.getAsString("groupName");
		
		IDirectorySession session = CDSUserManager.getSession();
		IDirectoryPrincipal user = session.lookupPrincipalByName(username, IDirectoryPrincipal.TYPE_USER );
		IDirectoryPrincipal group = session.lookupPrincipalByName(groupName, IDirectoryPrincipal.TYPE_GROUP );
		if (user == null){
			throw new ServiceException("Not able to find user " + username);
		}
		if (group == null){
			throw new ServiceException("Not able to find role " + groupName);
		}
		session.removePrincipalFromGroup(user.getID(), group.getID());
		// --- <<IS-END>> ---

                
	}



	public static final void searchGroups (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(searchGroups)>> ---
		// @sigtype java 3.5
		// [i] field:0:required searchString
		// [i] field:0:optional directoryServiceId
		// [o] record:1:required groups
		// [o] - field:0:required name
		// [o] - field:0:required directoryServiceId
		IDataMap pipelineMap = new IDataMap(pipeline);
		String searchString = pipelineMap.getAsString("searchString");
		String directoryServiceId = pipelineMap.getAsString("directoryServiceId");
		
		List<IData> groups = new ArrayList<IData>();
		
		IDirectorySession session = CDSUserManager.getSession();
		if (directoryServiceId != null && !directoryServiceId.isEmpty()){
			IDirectoryPagingCookie pgCookie = session.createPagingCookie(directoryServiceId);
			pgCookie.setPageSize(-1);
			List<IDirectoryPrincipal> results = session.searchDirectory(directoryServiceId, IDirectoryPrincipal.TYPE_GROUP, new DirectorySearchQuery(searchString, 200, null), pgCookie);
			for (IDirectoryPrincipal result : results){
				IData group = IDataFactory.create();
				IDataMap groupMap = new IDataMap(group);
				groupMap.put("name", result.getName());
				groupMap.put("directoryServiceId", directoryServiceId);
				groups.add(group);
			}
		}else{
			for (IDirectoryService directoryService : session.listDirectoryServices()){
				IDirectoryPagingCookie pgCookie = session.createPagingCookie(directoryService.getID());
				pgCookie.setPageSize(-1);
				List<IDirectoryPrincipal> results = session.searchDirectory(directoryService.getID(), IDirectoryPrincipal.TYPE_GROUP, new DirectorySearchQuery(searchString, 200, null), pgCookie);
				for (IDirectoryPrincipal result : results){
					IData group = IDataFactory.create();
					IDataMap groupMap = new IDataMap(group);
					groupMap.put("name", result.getName());
					groupMap.put("directoryServiceId", directoryService.getID());
					groups.add(group);
				}
			}
		}
		
		pipelineMap.put("groups", groups.toArray(new String[groups.size()]));
		// --- <<IS-END>> ---

                
	}
}

