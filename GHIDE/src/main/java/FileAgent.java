import java.io.*;
import java.util.ArrayList;

public class FileAgent {

    public static void compileAndRun() throws IOException, InterruptedException {
        ArrayList<String> fileContents = GitHubAgent.getInstance().getReadmeText();
        File file;
        try {
            file = new File(fileContents.get(0));
            if (file.createNewFile()) {
                System.out.println("➔ File created: " + file.getName());
            } else {
                System.out.println("➔ File already exists.");
            }
        } catch (IOException e) {
            System.out.println("➔ An error occurred.");
            e.printStackTrace();
        }

        try {
            FileWriter myWriter = new FileWriter(fileContents.get(0));
            for (int i = 2; i < fileContents.size(); i++) {
                if (fileContents.get(i).equals("----------"))
                    break;
                myWriter.write(fileContents.get(i) + "\n");
            }
            myWriter.close();
            System.out.println("➔ Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("➔ An error occurred using FileWriter.");
            e.printStackTrace();
        }

        String extension = fileContents.get(0).split("\\.")[1]; // cpp, py, or java
        ArrayList<String> finalConsoleOutput = new ArrayList<>();

        switch (extension) {
            case "cpp" -> {
                String command1 = "g++ ./" + fileContents.get(0) + " -g -Wall -Wextra -o test.exe";
                finalConsoleOutput.add("$ " + command1);
                finalConsoleOutput.addAll(runAndGetConsoleOutput(command1));

                String command2 = "./test.exe " + fileContents.get(1);
                finalConsoleOutput.add("$ " + command2);
                finalConsoleOutput.addAll(runAndGetConsoleOutput(command2));

                GitHubAgent.getInstance().writeConsoleToReadMe(finalConsoleOutput);
            }
            case "py" -> {
                // Only one command is used in Python.
                String command = "python3 " + fileContents.get(0) + " " + fileContents.get(1);
                finalConsoleOutput.add("$ " + command);
                finalConsoleOutput.addAll(runAndGetConsoleOutput(command));
                GitHubAgent.getInstance().writeConsoleToReadMe(finalConsoleOutput);
            }
            case "java" -> {
                String command1 = "javac " + fileContents.get(0);
                if (!fileContents.get(1).equals("")) {
                    command1 = " " + fileContents.get(1);
                }
                finalConsoleOutput.add("$ " + command1);
                finalConsoleOutput.addAll(runAndGetConsoleOutput(command1));

                String command2 = "java " + fileContents.get(0).split("\\.")[0];
                finalConsoleOutput.add("$ " + command2);
                finalConsoleOutput.addAll(runAndGetConsoleOutput(command2));

                GitHubAgent.getInstance().writeConsoleToReadMe(finalConsoleOutput);
            }
            default -> System.out.println("➔ Invalid file name.");
        }
    }

    public static void deleteFilesWithExtensions(File directory) {
        System.out.println("➔ Deleting compiler files...");
        String[] extensions = {".java", ".class", ".exe", ".py", ".cpp", ".out", ".txt", ".Identifier"};
        FilenameFilter filter = (dir, name) -> {
            for (String ext : extensions) {
                if (name.endsWith(ext)) {
                    return true;
                }
            }
            return false;
        };

        File[] filesToDelete = directory.listFiles(filter);

        if (filesToDelete != null) {
            for (File file : filesToDelete) {
                if (!file.isDirectory()) { // Ensures we don't delete directories
                    boolean deleted = file.delete();
                    System.out.println(file.getName() + " deleted: " + deleted);
                }
            }
        } else {
            System.out.println("➔ No files found matching the given extensions or an error occurred.");
        }
    }

    public static ArrayList<String> runAndGetConsoleOutput(String command) throws IOException, InterruptedException {
        command = command.trim();
        ArrayList<String> output = new ArrayList<>();
        String[] commands = command.split("\\s+");
        try {
            ProcessBuilder builder = new ProcessBuilder(commands);
            builder.redirectErrorStream(true);
            Process process = builder.start();
            process.waitFor();
            InputStream is = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            String line;
            while ((line = reader.readLine()) != null) {
                output.add(line);
            }
        } catch (Exception e) {
            output.add("➔ Command not run (did the previous command throw an error?)");
        }
        return output;
    }

}
