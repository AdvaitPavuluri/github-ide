import org.kohsuke.github.GHContent;

import java.io.File;
import java.io.IOException;

public class CommitTicker {

    private static final CommitTicker INSTANCE = new CommitTicker();

    public static CommitTicker getInstance() {
        return INSTANCE;
    }

    private String lastKnownSha = null;

    public void continuouslyCheckForReadmeUpdates() throws IOException {
        try {
            while (true) { // Infinite loop to keep checking for updates
                GHContent readme = GitHubAgent.getInstance().getReadmeContents();
                String currentSha = readme.getSha();
                if (lastKnownSha == null) {
                    lastKnownSha = currentSha;
                }
                if (!lastKnownSha.equals(currentSha)) {
                    System.out.println("➔ README.md has been updated!");
                    lastKnownSha = currentSha; // Update the last known SHA
                    FileAgent.deleteFilesWithExtensions(new File("/home/advait/idea-projects/GHIDE"));
                    FileAgent.compileAndRun();
                } else {
                    System.out.println("➔ Waiting for commit...");
                }

                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
