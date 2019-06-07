package HxEnhancedUserManagement.central;

// -----( IS Java Code Template v1.2
// -----( CREATED: 2019-06-07 10:42:09 MDT
// -----( ON-HOST: 192.168.241.213

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

public final class role

{
	// ---( internal utility methods )---

	final static role _instance = new role();

	static role _newInstance() { return new role(); }

	static role _cast(Object o) { return (role)o; }

	// ---( server methods )---




	public static final void addGroupToRole (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(addGroupToRole)>> ---
		// @sigtype java 3.5
		// [i] field:0:required groupName
		// [i] field:0:required roleName
		IDataMap pipelineMap = new IDataMap(pipeline);
		String groupName = pipelineMap.getAsString("groupName");
		String roleName = pipelineMap.getAsString("roleName");
		
		IDirectorySession session = CDSUserManager.getSession();
		IDirectoryPrincipal group = session.lookupPrincipalByName(groupName, IDirectoryPrincipal.TYPE_GROUP );
		IDirectoryPrincipal role = session.lookupPrincipalByName(roleName, IDirectoryPrincipal.TYPE_ROLE );
		if (group == null){
			throw new ServiceException("Not able to find group " + groupName);
		}
		if (role == null){
			throw new ServiceException("Not able to find role " + roleName);
		}
		session.addPrincipalToRole(group.getID(), role.getID());
		// --- <<IS-END>> ---

                
	}



	public static final void addRoleToRole (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(addRoleToRole)>> ---
		// @sigtype java 3.5
		// [i] field:0:required childRoleName
		// [i] field:0:required parentRoleName
		IDataMap pipelineMap = new IDataMap(pipeline);
		String childRoleName = pipelineMap.getAsString("childRoleName");
		String parentRoleName = pipelineMap.getAsString("parentRoleName");
		
		IDirectorySession session = CDSUserManager.getSession();
		IDirectoryPrincipal childRole = session.lookupPrincipalByName(childRoleName, IDirectoryPrincipal.TYPE_ROLE );
		IDirectoryPrincipal parentRole = session.lookupPrincipalByName(parentRoleName, IDirectoryPrincipal.TYPE_ROLE );
		if (childRole == null){
			throw new ServiceException("Not able to find child role " + childRoleName);
		}
		if (parentRole == null){
			throw new ServiceException("Not able to find role " + parentRoleName);
		}
		session.addPrincipalToRole(childRole.getID(), parentRole.getID());
		// --- <<IS-END>> ---

                
	}



	public static final void addUserToRole (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(addUserToRole)>> ---
		// @sigtype java 3.5
		// [i] field:0:required username
		// [i] field:0:required roleName
		IDataMap pipelineMap = new IDataMap(pipeline);
		String username = pipelineMap.getAsString("username");
		String roleName = pipelineMap.getAsString("roleName");
		
		IDirectorySession session = CDSUserManager.getSession();
		IDirectoryPrincipal user = session.lookupPrincipalByName(username, IDirectoryPrincipal.TYPE_USER );
		IDirectoryPrincipal role = session.lookupPrincipalByName(roleName, IDirectoryPrincipal.TYPE_ROLE );
		if (user == null){
			throw new ServiceException("Not able to find user " + username);
		}
		if (role == null){
			throw new ServiceException("Not able to find role " + roleName);
		}
		session.addPrincipalToRole(user.getID(), role.getID());
		// --- <<IS-END>> ---

                
	}



	public static final void createRole (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(createRole)>> ---
		// @sigtype java 3.5
		// [i] field:0:required roleName
		// [i] field:0:required roleType {"STATIC","DB","LDAP"}
		// [i] record:1:optional attributes
		// [i] - field:0:required key
		// [i] - field:0:required value
		// [o] field:0:required roleId
		IDataMap pipelineMap = new IDataMap(pipeline);
		String roleName = pipelineMap.getAsString("roleName");
		String roleType = pipelineMap.getAsString("roleType");
		IData[] attributesData = pipelineMap.getAsIDataArray("attributes");
		
		if (roleName != null && roleType != null){
			IDirectorySession session = CDSUserManager.getSession();
			Properties attributes = new Properties();
			if (attributesData != null){
				for (IData attributeData : attributesData){
					IDataMap attributeDataMap = new IDataMap(attributeData);
					String key = attributeDataMap.getAsString("key");
					String value = attributeDataMap.getAsString("value");
					attributes.put(key, value);
				}
			}
			switch (roleType){
			case "STATIC":
				roleType = "role.static.provider";
				break;
			case "LDAP":
				roleType = "role.ldap.query.provider";
				break;
			case "DB":
				roleType = "role.db.provider";
				break;
			default:
				throw new ServiceException("Found unsupported role type " + roleType);
			}
			IDirectoryPrincipal role = session.createRole(roleType, roleName, attributes);
			pipelineMap.put("roleId", role.getID());
		}else{
			throw new ServiceException("Found null input.");
		}
			
		// --- <<IS-END>> ---

                
	}



	public static final void deleteRole (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(deleteRole)>> ---
		// @sigtype java 3.5
		// [i] field:0:required roleName
		IDataMap pipelineMap = new IDataMap(pipeline);
		String roleName = pipelineMap.getAsString("roleName");
		
		IDirectorySession session = CDSUserManager.getSession();
		IDirectoryPrincipal principal = session.lookupPrincipalByName(roleName, IDirectoryPrincipal.TYPE_ROLE );
		
		if (principal != null){
			session.deletePrincipal(principal.getID());
		}
			
		// --- <<IS-END>> ---

                
	}



	public static final void getBelongedRolesByGroup (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(getBelongedRolesByGroup)>> ---
		// @sigtype java 3.5
		// [i] field:0:required groupName
		// [o] field:1:required roleNames
		IDataMap pipelineMap = new IDataMap(pipeline);
		String groupName = pipelineMap.getAsString("groupName");
		
		IDirectorySession session = CDSUserManager.getSession();
		IDirectoryPrincipal principal = session.lookupPrincipalByName(groupName, IDirectoryPrincipal.TYPE_GROUP );
		
		if (principal != null){
			List<IDirectoryRole> roles = session.getRoleMembership(principal.getID());
			String[] roleNames = new String[roles.size()];
			for (int i = 0; i < roleNames.length; i++){
				roleNames[i] = roles.get(i).getName();
			}
			pipelineMap.put("roleNames", roleNames);
		}
			
		// --- <<IS-END>> ---

                
	}



	public static final void getBelongedRolesByUser (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(getBelongedRolesByUser)>> ---
		// @sigtype java 3.5
		// [i] field:0:required username
		// [o] field:1:required roleNames
		IDataMap pipelineMap = new IDataMap(pipeline);
		String username = pipelineMap.getAsString("username");
		
		IDirectorySession session = CDSUserManager.getSession();
		IDirectoryPrincipal principal = session.lookupPrincipalByName(username, IDirectoryPrincipal.TYPE_USER );
		
		if (principal != null){
			List<IDirectoryRole> roles = session.getRoleMembership(principal.getID());
			String[] roleNames = new String[roles.size()];
			for (int i = 0; i < roleNames.length; i++){
				roleNames[i] = roles.get(i).getName();
			}
			pipelineMap.put("roleNames", roleNames);
		}
			
		// --- <<IS-END>> ---

                
	}



	public static final void getRelongedRolesByRole (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(getRelongedRolesByRole)>> ---
		// @sigtype java 3.5
		// [i] field:0:required roleName
		// [o] field:1:required roleNames
		IDataMap pipelineMap = new IDataMap(pipeline);
		String roleName = pipelineMap.getAsString("roleName");
		
		IDirectorySession session = CDSUserManager.getSession();
		IDirectoryPrincipal principal = session.lookupPrincipalByName(roleName, IDirectoryPrincipal.TYPE_ROLE );
		
		if (principal != null){
			List<IDirectoryRole> roles = session.getRoleMembership(principal.getID());
			String[] roleNames = new String[roles.size()];
			for (int i = 0; i < roleNames.length; i++){
				roleNames[i] = roles.get(i).getName();
			}
			pipelineMap.put("roleNames", roleNames);
		}
			
		// --- <<IS-END>> ---

                
	}



	public static final void getRoleMembers (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(getRoleMembers)>> ---
		// @sigtype java 3.5
		// [i] field:0:required roleName
		// [o] record:1:required members
		// [o] - field:0:required id
		// [o] - field:0:required name
		// [o] - field:0:required type
		// [o] - field:0:optional directoryService
		IDataMap pipelineMap = new IDataMap(pipeline);
		String roleName = pipelineMap.getAsString("roleName");
		
		IDirectorySession session = CDSUserManager.getSession();
		IDirectoryPrincipal principal = session.lookupPrincipalByName(roleName, IDirectoryPrincipal.TYPE_ROLE );
		
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



	public static final void listRoles (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(listRoles)>> ---
		// @sigtype java 3.5
		// [o] record:1:required roles
		// [o] - field:0:required id
		// [o] - field:0:required name
		// [o] - field:0:required type
		IDirectorySession session = CDSUserManager.getSession();
		List<IDirectoryRole> roles = session.listRoles();
		List<IData> rolesData = new ArrayList<IData>();
		for (IDirectoryRole role : roles){
			IData roleData = IDataFactory.create();
			rolesData.add(roleData);
			IDataMap roleDataMap = new IDataMap(roleData);
			roleDataMap.put("id", role.getID());
			roleDataMap.put("name", role.getName());
			switch (role.getRoleType()){
			case 1:
				roleDataMap.put("type", "STATIC");
				break;
			case 2:
				roleDataMap.put("type", "LDAP");
				break;
			case 3:
				roleDataMap.put("type", "DB");
				break;
			default:
				roleDataMap.put("type", "UNKNOWN TYPE " + role.getType());
			}
		}
		
		IDataMap pipelineMap = new IDataMap(pipeline);
		pipelineMap.put("roles", rolesData.toArray(new IData[rolesData.size()]));
		// --- <<IS-END>> ---

                
	}



	public static final void removeGroupFromRole (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(removeGroupFromRole)>> ---
		// @sigtype java 3.5
		// [i] field:0:required groupName
		// [i] field:0:required roleName
		IDataMap pipelineMap = new IDataMap(pipeline);
		String groupName = pipelineMap.getAsString("groupName");
		String roleName = pipelineMap.getAsString("roleName");
		
		IDirectorySession session = CDSUserManager.getSession();
		IDirectoryPrincipal group = session.lookupPrincipalByName(groupName, IDirectoryPrincipal.TYPE_GROUP );
		IDirectoryPrincipal role = session.lookupPrincipalByName(roleName, IDirectoryPrincipal.TYPE_ROLE );
		if (group == null){
			throw new ServiceException("Not able to find group " + groupName);
		}
		if (role == null){
			throw new ServiceException("Not able to find role " + roleName);
		}
		session.removePrincipalFromRole(group.getID(), role.getID());
		// --- <<IS-END>> ---

                
	}



	public static final void removeRoleFromRole (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(removeRoleFromRole)>> ---
		// @sigtype java 3.5
		// [i] field:0:required childRoleName
		// [i] field:0:required parentRoleName
		IDataMap pipelineMap = new IDataMap(pipeline);
		String childRoleName = pipelineMap.getAsString("childRoleName");
		String parentRoleName = pipelineMap.getAsString("parentRoleName");
		
		IDirectorySession session = CDSUserManager.getSession();
		IDirectoryPrincipal childRole = session.lookupPrincipalByName(childRoleName, IDirectoryPrincipal.TYPE_ROLE );
		IDirectoryPrincipal parentRole = session.lookupPrincipalByName(parentRoleName, IDirectoryPrincipal.TYPE_ROLE );
		if (childRole == null){
			throw new ServiceException("Not able to find child role " + childRoleName);
		}
		if (parentRole == null){
			throw new ServiceException("Not able to find parent role " + parentRoleName);
		}
		session.removePrincipalFromRole(childRole.getID(), parentRole.getID());
		// --- <<IS-END>> ---

                
	}



	public static final void removeUserFromRole (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(removeUserFromRole)>> ---
		// @sigtype java 3.5
		// [i] field:0:required username
		// [i] field:0:required roleName
		IDataMap pipelineMap = new IDataMap(pipeline);
		String username = pipelineMap.getAsString("username");
		String roleName = pipelineMap.getAsString("roleName");
		
		IDirectorySession session = CDSUserManager.getSession();
		IDirectoryPrincipal user = session.lookupPrincipalByName(username, IDirectoryPrincipal.TYPE_USER );
		IDirectoryPrincipal role = session.lookupPrincipalByName(roleName, IDirectoryPrincipal.TYPE_ROLE );
		if (user == null){
			throw new ServiceException("Not able to find user " + username);
		}
		if (role == null){
			throw new ServiceException("Not able to find role " + roleName);
		}
		session.removePrincipalFromRole(user.getID(), role.getID());
		// --- <<IS-END>> ---

                
	}



	public static final void searchRoles (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(searchRoles)>> ---
		// @sigtype java 3.5
		// [i] field:0:required searchString
		// [o] field:1:required roleNames
		IDataMap pipelineMap = new IDataMap(pipeline);
		String searchString = pipelineMap.getAsString("searchString");
		
		List<String> roleNames = new ArrayList<String>();
		
		IDirectorySession session = CDSUserManager.getSession();
		for (IDirectoryRole role : session.listRoles()){
			if (searchString == null || role.getName().toUpperCase().indexOf(searchString.toUpperCase()) >= 0){
				roleNames.add(role.getName());
			}
		}		
		pipelineMap.put("roleNames", roleNames.toArray(new String[roleNames.size()]));
		// --- <<IS-END>> ---

                
	}
}

