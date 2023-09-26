package Main;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JPanel;

public class Panel extends JPanel {
	
	public static String readFromFile(String filePath) {
        StringBuilder content = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
            	line = line + "\n";
                content.append(line);
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading from the file: " + e.getMessage());
        }

        return content.toString();
    }
	
	public static void writeToFile(String filePath, String content, boolean append) {
	    try {
	        File file = new File(filePath);

	        // Create a FileWriter object
	        FileWriter fileWriter = new FileWriter(file, append);

	        // Create a BufferedWriter object to write to the file
	        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

	        // Write content to the file
	        bufferedWriter.write(content);

	        // Close the resources
	        bufferedWriter.close();
	        fileWriter.close();
	    } catch (IOException e) {
	        System.out.println("An error occurred while writing to the file: " + e.getMessage());
	    }
	}
}
