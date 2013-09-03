package uk.ac.cam.cl.dtg.teaching.api;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.jboss.resteasy.client.ClientRequestFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

public interface HandinsApi {
    @GET
    @Path("/api/bins/{id}")
    public GetBin getBin(@PathParam("id") long id,
                      @QueryParam("key") String key,
                      @QueryParam("userId") String user);

    @POST
    @Path("/api/bins")
    public Bin createBin(@FormParam("name") String name,
                         @FormParam("owner") String user,
                         @FormParam("key") String key);

    @POST
    @Path("/api/bins/{id}")
    public Response setBinAccessPermissions(
            @PathParam("id") long id,
            @FormParam("users[]") String[] users);

    public static class GetBin {
        Bin bin;

        public void setBin(Bin bin) {
            this.bin = bin;
        }

        public Bin getBin() {
            return bin;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Bin {
        private long id;
        private String name;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }


    public static class HandinsApiWrapper {

        // Logger
        private static Logger log = LoggerFactory.getLogger(HandinsApiWrapper.class);

        // Variables
        private String handinsUrl;
        private String apiKey;

        private HandinsApi api;


        public HandinsApiWrapper(String handinsUrl, String apiKey) {
            this.apiKey = apiKey;
            this.handinsUrl = handinsUrl;
            log.error("mata");
            ClientRequestFactory c = new ClientRequestFactory(UriBuilder.fromUri(handinsUrl).build());
            this.api = c.createProxy(HandinsApi.class);
        }

        private void logError(Exception e) {
            log.error(e.getMessage());
            for (StackTraceElement lvl: e.getStackTrace()) {
                log.error(lvl.toString());
            }
        }

        public Bin getBin(long id) {
            Bin bin = null;
            try {
                GetBin resp  = api.getBin(id, this.apiKey, "at628");
                if (resp != null)
                    bin = resp.getBin();
            } catch (Exception e) {
                logError(e);
            }

            return bin;
        }

        public Bin createBin(String name, String owner) {
            Bin bin = null;
            try {
                bin = api.createBin(name, owner, this.apiKey);
            } catch (Exception e) {
                logError(e);
            }
            return bin;
        }

        public void setUsers(long binId, String[] users) {
            try {
                api.setBinAccessPermissions(binId, users);
            } catch (Exception e) {
                logError(e);
            }
        }
        public void setUsers(Bin bin, String[] users) {
            setUsers(bin.getId(), users);
        }



    }
}
