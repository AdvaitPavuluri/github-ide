import org.kohsuke.github.GHContent;

import java.io.*;

public class Main {

    public static void main(String[] args) throws IOException {
        GitHubAgent.getInstance().authUser(""); // Personal token here
        GitHubAgent.getInstance().connectRepository(""); // Personal repository here
        CommitTicker.getInstance().continuouslyCheckForReadmeUpdates();
    }

}
