package uk.ac.cam.cl.dtg.teaching.api;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.UriBuilder;

import org.jboss.resteasy.client.ClientRequestFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface NotificationApi {

	// Create
	
	@POST @Path("/api/notifications")
	public CreateNotification createNotification(@FormParam("message") String message,
												 @FormParam("section") String section,
												 @FormParam("link") String link,
												 @FormParam("users") String users,
												 @QueryParam("key") String key);
	
	public static class CreateNotification {
		private String error;
		private String redirectTo;
		
		// TODO refactor data and formErrors into classes
		private Object data;
		private Object formErrors;
		
		// Setters and getters
		
		public String getError() {return error;}
		public void setError(String error) {this.error = error;}
		
		public String getRedirectTo() {return redirectTo;}
		public void setRedirectTo(String redirectTo) {this.redirectTo = redirectTo;}
		
		public Object getData() {return data;}
		public void setData(Object data) {this.data = data;}
		
		public Object getFormErrors() {return formErrors;}
		public void setFormErrors(Object formErrors) {this.formErrors = formErrors;}
	}
	
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
		 * if (!n.createNotification("Test message", "dashboard", "example.com", "userCrsid1,userCrsid2")) log.error("Internal server error: could not create notification");
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
				
			} catch (Exception e) {
				log.error(e.getMessage());
				return false;
			}
			
			return false;
		}
		
	}
	
}
