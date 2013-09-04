package uk.ac.cam.cl.dtg.teaching.api;


import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.jboss.resteasy.client.ClientRequestFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.UriBuilder;
import java.util.List;

public interface QuestionsApi {

    @GET
    @Path("/api/sets/{id}")
    public GetQuestionSet getQuestionSet(@PathParam("id") long id,
                                      @QueryParam("key") String key,
                                      @QueryParam("impostorUser") String user);

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class GetQuestionSet {
        private QuestionSet set;

        public QuestionSet getSet() {
            return set;
        }

        public void setSet(QuestionSet set) {
            this.set = set;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class QuestionSet {
        private List<Question> questions;

        public List<Question> getQuestions() {
            return questions;
        }

        public void setQuestions(List<Question> questions) {
            this.questions = questions;
        }
    }


    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Question {
        private long id;
        private String title;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
            if (this.title == null)
                setTitle("Question #" + id);
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }


    public static class QuestionsApiWrapper {

        // Logger
        private static Logger log = LoggerFactory.getLogger(QuestionsApiWrapper.class);

        // Variables
        private String handinsUrl;
        private String apiKey;

        private QuestionsApi api;


        public QuestionsApiWrapper(String handinsUrl, String apiKey) {
            this.apiKey = apiKey;
            this.handinsUrl = handinsUrl;

            ClientRequestFactory c = new ClientRequestFactory(UriBuilder.fromUri(handinsUrl).build());
            this.api = c.createProxy(QuestionsApi.class);
        }

        private void logError(Exception e) {
            log.error(e.getMessage());
            for (StackTraceElement lvl: e.getStackTrace()) {
                log.error(lvl.toString());
            }
        }

        public QuestionSet getQuestionSet(long id, String impostor) {
            QuestionSet set = null;
            try {
                GetQuestionSet req = api.getQuestionSet(id, this.apiKey, impostor);
                if (req != null)
                    set = req.getSet();
            } catch (Exception e) {
                logError(e);
            }

            return set;
        }
    }
}
