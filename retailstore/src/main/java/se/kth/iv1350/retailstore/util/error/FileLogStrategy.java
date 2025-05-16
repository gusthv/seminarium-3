package se.kth.iv1350.retailstore.util.error;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Makes a text file that is used to logg error messages. 
 */
public class FileLogStrategy implements ErrorStrategy {
    private static final String LOG_FILE = "error_log.txt";

    /**
    * Logs the given exception’s message to a text file with a timestamp.
    * If writing to the file fails, prints an error message to the console.
    * @param e The exception to be logged.
    */

    @Override
    public void handle(Exception e) {
        if (e instanceof Exception) {
            try (PrintWriter out = new PrintWriter(new FileWriter(LOG_FILE, true))) {
                out.println("[" + java.time.LocalDateTime.now() + "] " + e.getMessage());
            } catch (IOException ioException) {
                System.err.println("Kunde inte logga till fil: " + ioException.getMessage());
            }
        }
    }
}
