/**
 * Uy, Chenny A.
 * Case Study 5 - This is a program that will store and update a small telephone directory file "telephoneDirectory.txt".
 * It prints the original directory and allows user to make insertions and deletions to the record.
 * It then prints the final directory in alphabetical order.
 */
import java.io.*;
import java.util.*;

public class CaseStudy5 {
    static String filePath = "telephoneDirectory.txt";              // File path of the telephone directory
    static List<String> lines = new ArrayList<>();                  // Creates a list to store all record in the file

    // Method that will print the initial list stored in the file alphabetically
    public static void printInitialList() {
        lines.clear(); // Clear in case reused

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            // Reads each line from the file and stores it in the list
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            sortLines();                                    // Calls the method that will sort the record alphabetically
            
            // Display the headings
            System.out.println("Initial List:");
            System.out.printf("%-25s %-25s %-15s\n", "Name", "Address", "Telephone");
            // Prints the sorted record
            for (String sortedLine : lines) {
                String[] parts = sortedLine.split(";");
                if (parts.length == 3) {
                    System.out.printf("%-25s %-25s %-15s\n", parts[0].trim(), parts[1].trim(), parts[2].trim());
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    // Method that handles user input and updates the record
    public static void updateListFromInput() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            // Asks user if they want to update the record
            System.out.print("\nDo you want to update the record? (Y/N): ");
            char choice = Character.toUpperCase(scanner.next().charAt(0));
            scanner.nextLine(); // Consume leftover newline

            if (choice == 'Y') {
                // Prompts user to Insert or Delete a record in the file
                System.out.print("Enter code (I for insert, D for delete): ");
                String code = scanner.nextLine().trim().toUpperCase();

                if (code.equals("I")) {
                    // Asks user for the name, address, and telephone to be inserted in the directory file
                    System.out.print("Enter full name (last, first, middle): ");
                    String name = scanner.nextLine().trim();

                    System.out.print("Enter address: ");
                    String address = scanner.nextLine().trim();

                    System.out.print("Enter phone number: ");
                    String phone = scanner.nextLine().trim();
                    
                    lines.add(name + ";" + address + ";" + phone);              // Adds the new record to the list with ; as delimiter

                    System.out.println("Record inserted successfully.");            

                } else if (code.equals("D")) {
                    // Asks user for the name, address, and telephone to be deleted in the directory file
                    System.out.print("Enter full name (last, first, middle): ");
                    String name = scanner.nextLine().trim();

                    System.out.print("Enter address: ");
                    String address = scanner.nextLine().trim();

                    System.out.print("Enter phone number: ");
                    String phone = scanner.nextLine().trim();

                    // Checks if the details given matches a record in the file
                    boolean removed = lines.removeIf(entry -> {String[] parts = entry.split(";");
                        return parts.length == 3 &&
                           parts[0].trim().equalsIgnoreCase(name) &&
                           parts[1].trim().equalsIgnoreCase(address) &&
                           parts[2].trim().equalsIgnoreCase(phone);
                    });

                    if (removed) {// Deletes the matched record
                        System.out.println("Record deleted successfully.");
                    } else {
                        System.out.println("No matching record found.");
                    }
                }else {
                    System.out.println("Invalid input.");
                }

                // Sorts and saves the updated list after each execution
                sortLines();
                saveToFile();

            } else if (choice == 'N') {
                System.out.println("Exiting update process...");
                break; // Exit the loop
            } else {
                System.out.println("Invalid choice.");
            }
    }
}

    // Method that will print the updated record alphabetically
    public static void printUpdatedList() {
        System.out.println("\nUpdated List:");
        System.out.printf("%-25s %-25s %-15s\n", "Name", "Address", "Telephone");

        for (String line : lines) {
            String[] parts = line.split(";");
            if (parts.length == 3) {
                System.out.printf("%-25s %-25s %-15s\n", parts[0].trim(), parts[1].trim(), parts[2].trim());
            }
        }
    }

    // Method that will compare the last name of each record and sort them alphabetically 
    private static void sortLines() {
        lines.sort((a, b) -> {
            String lastNameA = a.split(",")[0].trim();          // Extract the last name from first record
            String lastNameB = b.split(",")[0].trim();          // Extract the last name from second record
            return lastNameA.compareToIgnoreCase(lastNameB);          // Compare the last names
        });
    }
    
    // Method that will save the updated record into the file
    private static void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
    
    // Main method that runs the full directory workflow
    public static void main(String[] args) {
        printInitialList();                 // Calls the method that will display the initial record list
        updateListFromInput();              // Calls the method that allow user to update the record and save the changes into the file
        printUpdatedList();                 // Call the method that will display the updated directory records
    }   
}

