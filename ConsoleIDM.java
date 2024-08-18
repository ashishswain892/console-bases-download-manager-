import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class DownloadTask implements Runnable {
    private String url;
    private String destination;
    private boolean resumeSupported;

    DownloadTask(String url, String destination, boolean resumeSupported) {
        this.url = url;
        this.destination = destination;
        this.resumeSupported = resumeSupported;
    }

    @Override
    public void run() {
        try {
            URL downloadUrl = new URL(url);
            InputStream inputStream;

            if (resumeSupported && Files.exists(Paths.get(destination))) {
                long fileSize = Files.size(Paths.get(destination));
                System.out.println("Resuming download from " + fileSize + " bytes");
                inputStream = downloadUrl.openStream();
                inputStream.skip(fileSize);

            } else {
                inputStream = downloadUrl.openStream();
            }

            try (FileOutputStream outputStream = new FileOutputStream(destination, true)) {
                byte[] buffer = new byte[1024];
                int bytesRead;

                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }

                System.out.println("Download complete: " + destination);
            }

        } catch (Exception e) {
            System.err.println("Error downloading file: " + e.getMessage());
        }
    }
}

public class ConsoleIDM {
    private static final List<Thread> downloadThreads = new ArrayList<>();

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
			while (true) {
			    System.out.println("1. Add download");
			    System.out.println("2. Show downloads");
			    System.out.println("3. Exit");
			    System.out.print("Enter your choice: ");
			    int choice = scanner.nextInt();

			    switch (choice) {
			        case 1:
			            addDownload();
			            break;
			        case 2:
			            showDownloads();
			            break;
			        case 3:
			            stopDownloads();
			            System.out.println("Exiting...");
			            System.exit(0);
			            break;
			        default:
			            System.out.println("Invalid choice. Please try again.");
			    }
			}
		}
    }

 // Inside the addDownload() method
    private static void addDownload() {
        Scanner scanner = new Scanner(System.in);

        // Use a sample image URL for testing
        System.out.print("Enter URL (e.g., https://example.com/sample.jpg): ");
        String url = scanner.nextLine();

        // Specify the destination path, including the file name, e.g., "C:\Users\whiff\Downloads\sample.jpg"
        System.out.print("Enter destination path (including file name): ");
        String destination = scanner.nextLine();

        boolean resumeSupported;

        while (true) {
            try {
                System.out.print("Resume supported? (true/false): ");
                String resumeInput = scanner.nextLine().toLowerCase();
                
                if (resumeInput.equals("true") || resumeInput.equals("false")) {
                    resumeSupported = Boolean.parseBoolean(resumeInput);
                    break;
                } else {
                    System.err.println("Invalid input. Please enter 'true' or 'false'.");
                }
            } catch (Exception e) {
                System.err.println("Invalid input. Please enter 'true' or 'false'.");
            }
        }

        DownloadTask downloadTask = new DownloadTask(url, destination, resumeSupported);
        Thread downloadThread = new Thread(downloadTask);
        downloadThreads.add(downloadThread);
        downloadThread.start();

        System.out.println("Download added to the queue.");
    }





    private static void showDownloads() {
        System.out.println("Current Downloads:");
        for (Thread thread : downloadThreads) {
            System.out.println(thread.getId() + " - " + thread.getState());
        }
    }

    private static void stopDownloads() {
        for (Thread thread : downloadThreads) {
            thread.interrupt();
        }
    }
}
