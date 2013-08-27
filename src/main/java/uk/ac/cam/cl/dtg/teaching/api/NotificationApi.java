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
										   @QueryParam("foreignId") String foreignId,
										   // Authentication
										   @QueryParam("userId") String userId,
										   @QueryParam("key") String key);
	
	public static class GetNotification {
		private int limit;
		private int offset;
		private int total;
		private boolean read;
		private User user;
		private String section;
		private String foreignId;
		private Set<Notification> notifications;
		
		private String error;
		private GetData data;
		private GetFormError formErrors;
		
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
		public void setUser(User user) {this.user = user;}
		
		public String getSection() {return section;}
		public void setSection(String section) {this.section = section;}
		
		public Set<Notification> getNotifications() {return notifications;}
		public void setNotifications(Set<Notification> notifications) {this.notifications = notifications;}
		
		public String getError() {return error;}
		public void setError(String error) {this.error = error;}
		
		public GetData getData() {return data;}
		public void setData(GetData data) {this.data = data;}
		
		public String getForeignId() {return foreignId;}
		public void setForeignId(String foreignId) {this.foreignId = foreignId;}
		
		public GetFormError getFormErrors() {return formErrors;}
		public void setFormErrors(GetFormError formErrors) {this.formErrors = formErrors;}

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
	
	public static class GetFormError {
		private String[] limit;
		private String[] offset;
		private String[] section;
		private String[] foreignId;
		
		public String[] getLimit() {return limit;}
		public void setLimit(String[] limit) {this.limit = limit;}
		
		public String[] getOffset() {return offset;}
		public void setOffset(String[] offset) {this.offset = offset;}
		
		public String[] getSection() {return section;}
		public void setSection(String[] section) {this.section = section;}
		
		public String[] getForeignId() {return foreignId;}
		public void setForeignId(String[] foreignId) {this.foreignId = foreignId;}
	}
	
	public static class GetData {
		private String offset;
		private String limit;
		private String section;
		private String foreignId;
		
		public String getOffset() {return offset;}
		public void setOffset(String offset) {this.offset = offset;}
		
		public String getLimit() {return limit;}
		public void setLimit(String limit) {this.limit = limit;}
		
		public String getSection() {return section;}
		public void setSection(String section) {this.section = section;}
		
		public String getForeignId() {return foreignId;}
		public void setForeignId(String foreignId) {this.foreignId = foreignId;}
	}
	
	public static class User {
		private String crsid;
		private String name;
		
		// Setters and getters
		
		public String getCrsid() {return crsid;}
		public void setCrsid(String crsid) {this.crsid = crsid;}
		
		public String getName() {return name;}
		public void setName(String name) {this.name = name;}
		
	}	

	// Create
	
	@POST @Path("/api/notifications")
	public CreateNotification createNotification(@FormParam("message") String message,
												 @FormParam("section") String section,
												 @FormParam("link") String link,
												 @FormParam("users") String users,
												 @FormParam("foreignId") String foreignId,
												 // Authentication
												 @QueryParam("key") String key);
	
	public static class CreateNotification {
		private String redirectTo;
		
		private String error;
		private CreateData data;
		private CreateFormError formErrors;
		
		// Setters and getters
		
		public String getRedirectTo() {return redirectTo;}
		public void setRedirectTo(String redirectTo) {this.redirectTo = redirectTo;}
		
		public String getError() {return error;}
		public void setError(String error) {this.error = error;}
		
		public CreateData getData() {return data;}
		public void setData(CreateData data) {this.data = data;}
		
		public CreateFormError getFormErrors() {return formErrors;}
		public void setFormErrors(CreateFormError formErrors) {this.formErrors = formErrors;}
		
	}
	
	public static class CreateFormError {
		private String[] message;
		private String[] users;
		private String[] link;
		private String[] section;
		
		// Setters and getters
		
		public String[] getMessage() {return message;}
		public void setMessage(String[] message) {this.message = message;}
		
		public String[] getUsers() {return users;}
		public void setUsers(String[] users) {this.users = users;}
		
		public String[] getLink() {return link;}
		public void setLink(String[] link) {this.link = link;}
		
		public String[] getSection() {return section;}
		public void setSection(String[] section) {this.section = section;}
	}
	
	public static class CreateData {
		private String message;
		private String section;
		private String link;
		private String users;
		private String foreignId;
		
		public String getMessage() {return message;}
		public void setMessage(String message) {this.message = message;}
		
		public String getSection() {return section;}
		public void setSection(String section) {this.section = section;}
		
		public String getLink() {return link;}
		public void setLink(String link) {this.link = link;}
		
		public String getUsers() {return users;}
		public void setUsers(String users) {this.users = users;}
		
		public String getForeignId() {return foreignId;}
		public void setForeignId(String foreignId) {this.foreignId = foreignId;}
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
			return getNotificationsWithForeignId(offset, limit, section, userId, "");
		}
		
		public GetNotification getNotificationsWithForeignId(int offset, int limit, String section, String userId, String foreignId) {
			
			try {
				ClientRequestFactory c = new ClientRequestFactory(UriBuilder.fromUri(dashboardUrl).build());
				GetNotification gn = c.createProxy(NotificationApi.class).getNotification(offset, limit, section, foreignId, userId, apiKey);
				
				if (gn == null) {
					log.error("Internal server error: could not get notifications");
					throw new NotificationException("Internal server error: could not get notifications");
				} else if (gn.getError() != null) {
					log.error(gn.getError());
					throw new NotificationException(gn.getError());
				} else if (gn.getFormErrors() != null) {
					// TODO refactor to loop through arrays
					String errors = gn.getFormErrors().getForeignId().toString() +
									gn.getFormErrors().getLimit().toString() +
									gn.getFormErrors().getOffset().toString() +
									gn.getFormErrors().getSection().toString();
			
					log.error("Form errors: " + errors);
					throw new NotificationException("Form errors " + errors);
				}
				
				return gn;
				
			} catch (NotificationException e) {
				log.error(e.getMessage());
				return null;
			}
		}
		/*
		 * Recommended usage as follows:
		 * 
		 * NotificationApiWrapper n = new NotificationApiWrapper(dashboardUrl, apiKey);
		 * try {
		 *     n.createNotification("Test message", "dashboard", "example.com", "userCrsid1,userCrsid2"));
		 *     log.info("Successfully created notification");
		 * } catch (NotificationException e) {
		 *     log.error(e.getMessage());
		 * }
		 * 
		 */
		
		public void createNotification(String message, String section, String link, String users) throws NotificationException {
			createNotificationWithForeignId(message, section, link, users, null);
		}
		
		public void createNotificationWithForeignId(String message, String section, String link, String users, String foreignId) throws NotificationException {
			ClientRequestFactory c = new ClientRequestFactory(UriBuilder.fromUri(dashboardUrl).build());
			CreateNotification cn = c.createProxy(NotificationApi.class).createNotification(message, section, link, users, foreignId, apiKey);

			if (cn.getRedirectTo() != null) {
				return;
			} else if (cn.getError() != null) {
				log.error(cn.getError());
				throw new NotificationException(cn.getError());
			} else if (cn.getFormErrors() != null) {
				// TODO refactor to loop through arrays
				String errors = cn.getFormErrors().getLink().toString() +
								cn.getFormErrors().getMessage().toString() +
								cn.getFormErrors().getSection().toString() +
								cn.getFormErrors().getUsers().toString();
				
				log.error("Form errors: " + errors);
				throw new NotificationException("Form errors " + errors);
			}
		}
		
	}
	
}
