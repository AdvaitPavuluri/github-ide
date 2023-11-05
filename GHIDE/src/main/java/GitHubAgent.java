import org.kohsuke.github.GHContent;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;

//import javax.swing.*;
import java.io.*;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class GitHubAgent {

    private static final GitHubAgent INSTANCE = new GitHubAgent();
    private GitHub gitHub;
    private GHRepository repository;

    public static GitHubAgent getInstance() {
        return INSTANCE;
    }

    public void authUser(String oAuthToken) throws IOException {
        this.gitHub = new GitHubBuilder()
                .withOAuthToken(oAuthToken).build();
    }

    public void connectRepository(String repoName) throws IOException {
        this.repository = gitHub.getRepository(repoName);
    }

    public GHContent getReadmeContents() throws IOException {
        return repository.getFileContent("README.md");
    }

    public ArrayList<String> getReadmeText() throws IOException {
        GHContent readme = repository.getFileContent("README.md");

        ArrayList<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(readme.read()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }

    public void writeConsoleToReadMe(ArrayList<String> consoleOutput) throws IOException {
        GHContent readme = repository.getFileContent("README.md");
        StringBuilder newContents = new StringBuilder();
        for (String s : getReadmeText()) {
            if (s.equals("----------")) {
                break;
            }
            newContents.append(s).append("\n");
        }

        newContents.append("----------\n");

        StringBuilder consoleOutputAsString = new StringBuilder();
        for (String s : consoleOutput) {
            consoleOutputAsString.append(s).append("\n");
        }
        newContents.append(consoleOutputAsString);
        newContents.append("\n");

        newContents.append("Ran at ");
        newContents.append(getCurrentTime());
        newContents.append(" EST");

        System.out.println("➔ Committing to GitHub...");
        readme.update(String.join("\n", newContents), "Committed at " + getCurrentTime() + " EST");
    }

    private String getCurrentTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
        ZonedDateTime estTime = ZonedDateTime.now(ZoneId.of("America/New_York"));
        return estTime.format(formatter);
    }

}
