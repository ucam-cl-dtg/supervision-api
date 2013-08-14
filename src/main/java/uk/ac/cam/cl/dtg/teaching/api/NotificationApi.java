package uk.ac.cam.cl.dtg.teaching.api;

import java.util.Set;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.UriBuilder;

import org.jboss.resteasy.client.ClientRequestFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface NotificationApi {
	
	// Get
	@GET @Path("/api/notifications")
	public GetNotification getNotification(@QueryParam("offset") int offset,
										   @QueryParam("limit") int limit,
										   @QueryParam("section") String section,
										   // Authentication
										   @QueryParam("userId") String userId,
										   @QueryParam("key") String key);
	
	public static class GetNotification {
		private int limit;
		private int offset;
		private int total;
		private boolean read;
		// TODO refactor to user class
		private Object user;
		private String section;
		private Set<Notification> notifications;
		
		// TODO refactor data and formErrors into classes
		private String error;
		private Object data;
		private Object formErrors;
		
		// Setters and getters
		
		public int getLimit() {return limit;}
		public void setLimit(int limit) {this.limit = limit;}
		
		public int getOffset() {return offset;}
		public void setOffset(int offset) {this.offset = offset;}
		
		public int getTotal() {return total;}
		public void setTotal(int total) {this.total = total;}
		
		public boolean isRead() {return read;}
		public void setRead(boolean read) {this.read = read;}
		
		public Object getUser() {return user;}
		public void setUser(Object user) {this.user = user;}
		
		public String getSection() {return section;}
		public void setSection(String section) {this.section = section;}
		
		public Set<Notification> getNotifications() {return notifications;}
		public void setNotifications(Set<Notification> notifications) {this.notifications = notifications;}
		
		public String getError() {return error;}
		public void setError(String error) {this.error = error;}
		
		public Object getData() {return data;}
		public void setData(Object data) {this.data = data;}
		
		public Object getFormErrors() {return formErrors;}
		public void setFormErrors(Object formErrors) {this.formErrors = formErrors;}

	}
	
	public static class Notification {
		private int id;
		private boolean read;
		private String user;
		private String notification_id;
		private String message;
		private String section;
		private String link;
		private String timestamp;
		
		// Setters and getters
		
		public int getId() {return id;}
		public void setId(int id) {this.id = id;}
		
		public boolean isRead() {return read;}
		public void setRead(boolean read) {this.read = read;}
		
		public String getUser() {return user;}
		public void setUser(String user) {this.user = user;}
		
		public String getNotification_id() {return notification_id;}
		public void setNotification_id(String notification_id) {this.notification_id = notification_id;}
		
		public String getMessage() {return message;}
		public void setMessage(String message) {this.message = message;}
		
		public String getSection() {return section;}
		public void setSection(String section) {this.section = section;}
		
		public String getLink() {return link;}
		public void setLink(String link) {this.link = link;}
		
		public String getTimestamp() {return timestamp;}
		public void setTimestamp(String timestamp) {this.timestamp = timestamp;}
	}

	// Create
	
	@POST @Path("/api/notifications")
	public CreateNotification createNotification(@FormParam("message") String message,
												 @FormParam("section") String section,
												 @FormParam("link") String link,
												 @FormParam("users") String users,
												 // Authentication
												 @QueryParam("key") String key);
	
	public static class CreateNotification {
		private String redirectTo;
		
		// TODO refactor data and formErrors into classes
		private String error;
		private Object data;
		private Object formErrors;
		
		// Setters and getters
		
		public String getRedirectTo() {return redirectTo;}
		public void setRedirectTo(String redirectTo) {this.redirectTo = redirectTo;}
		
		public String getError() {return error;}
		public void setError(String error) {this.error = error;}
		
		public Object getData() {return data;}
		public void setData(Object data) {this.data = data;}
		
		public Object getFormErrors() {return formErrors;}
		public void setFormErrors(Object formErrors) {this.formErrors = formErrors;}
		
	}
	
	// Helper methods for API
	
	public static class NotificationApiWrapper {
		
		// Logger
		private static Logger log = LoggerFactory.getLogger(NotificationApiWrapper.class);
		
		// Variables
		private String dashboardUrl;
		private String apiKey;
		
		public NotificationApiWrapper(String dashboardUrl, String apiKey) {
			this.dashboardUrl = dashboardUrl;
			this.apiKey = apiKey;
		}
	
		/*
		 * Recommended usage as follows:
		 * 
		 * NotificationApiWrapper n = new NotificationApiWrapper(dashboardUrl, apiKey);
		 * GetNotification data = n.getNotifications(0, 10, "dashboard", "userCrsid1");
		 * if (data == null) {
		 *     log.error("Internal server error: could not get notifications")
		 * } else {
		 *     // Manipulate data
		 * } 
		 * 
		 */
		
		public GetNotification getNotifications(int offset, int limit, String section, String userId) {
			
			try {
				ClientRequestFactory c = new ClientRequestFactory(UriBuilder.fromUri(dashboardUrl).build());
				GetNotification gn = c.createProxy(NotificationApi.class).getNotification(offset, limit, section, userId, apiKey);
				
				if (gn == null) {
					log.error("Internal server error: could not get notifications");
					return null;
				} else if (gn.getError() != null) {
					log.error(gn.getError());
					return null;
				} else if (gn.getFormErrors() != null) {
					// TODO
					log.error("Form errors");
					return null;
				}
				
				return gn;
				
			} catch (Exception e) {
				log.error(e.getMessage());
				return null;
			}
			
		}
		
		/*
		 * Recommended usage as follows:
		 * 
		 * NotificationApiWrapper n = new NotificationApiWrapper(dashboardUrl, apiKey);
		 * if (!n.createNotification("Test message", "dashboard", "example.com", "userCrsid1,userCrsid2")) { 
		 *     log.error("Internal server error: could not create notification")
		 * } else {
		 *     log.info("Successfully create notification");
		 * }
		 * 
		 */
		
		public boolean createNotification(String message, String section, String link, String users) {
			try {
				ClientRequestFactory c = new ClientRequestFactory(UriBuilder.fromUri(dashboardUrl).build());
				CreateNotification cn = c.createProxy(NotificationApi.class).createNotification(message, section, link, users, apiKey);
				
				if (cn.getRedirectTo() != null) {
					return true;
				} else if (cn.getError() != null) {
					log.error(cn.getError());
					return false;
				} else if (cn.getFormErrors() != null) {
					// TODO
					log.error("Form errors");
					return false;
				}
				
				return false;
				
			} catch (Exception e) {
				log.error(e.getMessage());
				return false;
			}

		}
		
	}
	
}
