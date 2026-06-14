package command;

import io.ImageReader;
import io.ImageWriter;
import session.SessionManager;

/**
 * The services a {@link Command} needs from the running application. The app's
 * main class implements this interface, so commands depend only on this small
 * contract rather than on the concrete application class.
 */
public interface AppContext {

    /** The session manager that owns all sessions. */
    SessionManager sessions();

    /** The reader used to load images from disk. */
    ImageReader reader();

    /** The writer used to save images to disk. */
    ImageWriter writer();

    /** Requests that the command loop stop (used by {@code exit}). */
    void stop();
}
