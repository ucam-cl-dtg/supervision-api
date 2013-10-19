package uk.ac.cam.cl.dtg.teaching.api;

import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.UriBuilder;

import org.jboss.resteasy.client.ClientRequestFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface DashboardApi {
	
	// Settings	
	@GET @Path("/api/account/{crsid}")
	public Settings getSettings(@PathParam("crsid") String userId, @QueryParam("key") String key);
	
	
	
	public static class Settings {
		private List<MenuItem> sidebar;
		private Map<String, Object> settings;
		private String user;
		private String projectUrl;
		private String error;
		private List<String> keys;
		
		public List<MenuItem> getSidebar() {return sidebar;}
		public void setSidebar(List<MenuItem> sidebar) {this.sidebar = sidebar;}
		
		public Map<String, Object> getSettings() {return settings;}
		public void setSettings(Map<String, Object> settings) {this.settings = settings;}
		
		public String getError() {return error;}
		public void setError(String error) {this.error = error;}
		
		public String getUser() {return user;}
		public void setUser(String user) {this.user = user;}
		
		public List<String> getKeys() {return keys;}
		public void setKeys(List<String> keys) {this.keys = keys;}
		
		public String getProjectUrl() {return projectUrl;}
		public void setProjectUrl(String projectUrl) {this.projectUrl = projectUrl;}
	}
	
	public static class MenuItem {
		private String name;
		private String icon;
		private String section;
		private int iconType;
		private int notificationCount;
		private List<SubMenuItem> links;
		
		public String getName() {return name;}
		public void setName(String name) {this.name = name;}
		
		public String getIcon() {return icon;}
		public void setIcon(String icon) {this.icon = icon;}
		
		public String getSection() {return section;}
		public void setSection(String section) {this.section = section;}
		
		public int getIconType() {return iconType;}
		public void setIconType(int iconType) {this.iconType = iconType;}
		
		public List<SubMenuItem> getLinks() {return links;}
		public void setLinks(List<SubMenuItem> links) {this.links = links;}
		
		public int getNotificationCount() {return notificationCount;}
		public void setNotificationCount(int notificationCount) {this.notificationCount = notificationCount;}
	}
	
	public static class SubMenuItem {
		private String name;
		private String icon;
		private int iconType;
		private int notificationCount;
		private String link;
		
		public String getName() {return name;}
		public void setName(String name) {this.name = name;}
		
		public String getIcon() {return icon;}
		public void setIcon(String icon) {this.icon = icon;}
		
		public int getIconType() {return iconType;}
		public void setIconType(int iconType) {this.iconType = iconType;}
		
		public String getLink() {return link;}
		public void setLink(String link) {this.link = link;}
		
		public int getNotificationCount() {return notificationCount;}
		public void setNotificationCount(int notificationCount) {this.notificationCount = notificationCount;}
	}
	
	// Permissions
	
	@GET @Path("/api/keys/type/{key}")
	public ApiPermissions getApiPermissions(@PathParam("key") String key);

	public static class ApiPermissions {
		private String userId;
		private String type;
		private String error;
		
		public String getUserId() {return userId;}
		public void setUserId(String userId) {this.userId = userId;}
		
		public String getType() {return type;}
		public void setType(String type) {this.type = type;}
		
		public String getError() {return error;}
		public void setError(String error) {this.error = error;}
	}

    @GET
    @Path("/api/account/{crsid}/doses/{inst}")
    public GetDos getDoses(@PathParam("crsid") String crsid,
                           @PathParam("inst") String inst,
                           @QueryParam("key") String key);

    public static class GetDos {
        private List<String> doses;

        public List<String> getDoses() {
            return doses;
        }

        public void setDoses(List<String> doses) {
            this.doses = doses;
        }
    }
	
	public static class DashboardApiWrapper{
		private String dashboardUrl;
		private String apiKey;
		
		private static Logger log = LoggerFactory.getLogger(DashboardApi.class);
		
		public DashboardApiWrapper(String dashboardUrl, String apiKey){
			this.dashboardUrl = dashboardUrl;
			this.apiKey = apiKey;
		}
		
		public Settings getUserSettings(String crsid){
			try{
				ClientRequestFactory c = new ClientRequestFactory(UriBuilder.fromUri(dashboardUrl).build());
				Settings s = c.createProxy(DashboardApi.class).getSettings(crsid, apiKey);
				
				if(s == null){
					throw new DashboardException("Internal server error: couldn't get user settings");
				} else if(s.getError() != null) {
					throw new DashboardException(s.getError());
				}
				
				return s;
			} catch (DashboardException e) {
				log.error(e.getMessage());
				return null;
			} 
		}

        public List<String> getDosesForInstitution(String crsid, String inst) {
            try {
                DashboardApi api = new ClientRequestFactory(UriBuilder.fromUri(dashboardUrl).build()).createProxy(DashboardApi.class);
                GetDos req = api.getDoses(crsid, inst, this.apiKey);
                if (req != null)
                    return req.getDoses();
            } catch (Exception e)  {
                log.error(e.getMessage());
            }
            return null;
        }
		
	}
	
}
