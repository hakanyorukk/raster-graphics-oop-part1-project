package session;

import java.util.HashMap;
import java.util.Map;

/**
 * Owns every {@link Session} and tracks which one is currently active.
 * Sessions are numbered with consecutive ids starting from 1.
 */
public class SessionManager {
    private final Map<Integer, Session> sessions = new HashMap<>();
    private Session currentSession;
    private int nextId = 1;

    /** Creates a new session, makes it the current one, and returns it. */
    public Session createSession() {
        Session session = new Session(nextId++);
        sessions.put(session.getId(), session);
        currentSession = session;
        return session;
    }

    /** Makes the session with the given id the current one. */
    public void switchSession(int id) {
        currentSession = sessions.get(id);
    }

    /** Whether a session with the given id exists. */
    public boolean hasSession(int id) {
        return sessions.containsKey(id);
    }

    /** Discards the current session; afterwards there is no active session. */
    public void closeCurrentSession() {
        if (currentSession != null) {
            sessions.remove(currentSession.getId());
            currentSession = null;
        }
    }

    /** The active session, or {@code null} if none is open. */
    public Session getCurrentSession() {
        return currentSession;
    }
}
