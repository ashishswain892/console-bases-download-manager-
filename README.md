# console-bases-download-manager-
This code is a simple console-based download manager written in Java. It allows users to download files from the internet, resume interrupted downloads (if supported by the server), and monitor the progress of multiple downloads. Below is a detailed description of each component and how the program works:

1. Overview
The program enables users to:
Add new downloads by specifying the URL and destination path.
Choose whether to support resuming interrupted downloads.
View the current status of all ongoing downloads.
Stop all downloads and exit the program gracefully.
2. Components of the Program
A. DownloadTask Class
Purpose:

This class represents an individual download task. It implements the Runnable interface, which allows each download to run in a separate thread.
Attributes:

url: The URL of the file to be downloaded.
destination: The local path where the file will be saved.
resumeSupported: A boolean flag indicating whether the download should attempt to resume if it’s interrupted.
Constructor:

Initializes the URL, destination path, and the resume capability.
run Method:

Functionality:
This method contains the logic for downloading the file.
It checks if the file already exists at the destination and if resuming is supported.
If resuming is supported, it starts the download from where it left off by skipping the already downloaded bytes.
Otherwise, it starts a fresh download.
The file is written in chunks of 1024 bytes until the entire file is downloaded.
If the download is successful, it prints a "Download complete" message. If an error occurs (e.g., network issues), it catches the exception and prints an error message.
B. ConsoleIDM Class
Purpose:

This class is the main entry point of the application. It provides a simple console interface for interacting with the download manager.
Attributes:

downloadThreads: A list that holds all the threads running the download tasks.
main Method:

Functionality:
The main method displays a menu with options to add a download, show the current downloads, or exit the program.
It continuously prompts the user for input, processes their selection, and performs the corresponding action.
The program runs in an infinite loop, only terminating when the user selects the "Exit" option.
addDownload Method:

Functionality:
This method is invoked when the user chooses to add a new download.
It prompts the user for the URL of the file to download, the destination path where the file should be saved, and whether resuming is supported.
A DownloadTask object is created with the provided information, and a new thread is started to handle the download. This thread is added to the downloadThreads list.
The method then prints a message indicating that the download has been added to the queue.
showDownloads Method:

Functionality:
This method prints the current status of all ongoing download threads.
It lists each thread’s ID and state (e.g., RUNNABLE, TERMINATED).
stopDownloads Method:

Functionality:
This method is invoked when the user chooses to exit the program.
It interrupts all active download threads, allowing the program to shut down gracefully.
3. Program Flow
Starting the Program:

The program starts by displaying a menu with three options: add a download, show downloads, or exit.
Adding a Download:

When the user selects "Add download," they are prompted to enter the URL, the destination path, and whether resuming is supported.
The program then creates a new DownloadTask and starts it in a separate thread.
Monitoring Downloads:

The user can check the status of all downloads by selecting "Show downloads."
This displays the thread ID and current state of each download.
Exiting the Program:

When the user selects "Exit," the program stops all active downloads by interrupting their threads and then exits.
4. Error Handling
The program includes basic error handling to catch and report issues such as network failures, invalid URLs, or file I/O errors.
If an error occurs during the download process, an error message is printed, and the download is stopped.
5. Potential Enhancements
Download Progress Display:

Currently, the program does not display download progress. Adding progress tracking would improve user experience.
Handling More Complex Scenarios:

The program could be extended to handle more complex scenarios, such as authentication-required downloads or automatic retry mechanisms for failed downloads.
User Interface:

The current user interface is text-based and simple. A GUI version could be developed for better user interaction.
This program is a good example of how to use threading in Java to manage multiple tasks simultaneously. It also demonstrates basic file I/O and networking in Java, making it a useful tool for learning about these concepts.






