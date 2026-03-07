package session;

import image.Image;
import transformations.Transformation;

import java.util.HashMap;
import java.util.Map;

public class SessionManager {
    private Map<Integer, Session> sessions;
    private Session currentSession;
    private int sessionId = 1;

    public SessionManager() {
        sessions = new HashMap<>();
    }

    public Session createSession() {
        Session session = new Session(sessionId++);
        sessions.put(session.getId(), session);
        currentSession = session;
        return session;
    }

    public void switchSession(int id) {
        currentSession = sessions.get(id);
    }

    public Session getCurrentSession() {
        return currentSession;
    }

    public String getSessionInfo() {
        StringBuilder result = new StringBuilder("Name of images in the session: ");
        for(Image img: currentSession.getImages()) {
            result.append(img.getName()).append(" ");
        }

        result.append("\nPending transformations: ");
        for(Transformation transformation: currentSession.getTransformations()) {
            result.append(transformation.getName()).append(", ");
        }
        return result.toString();
    }

}
