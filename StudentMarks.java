
/**
 * Calculating Student Marks
 *
 * @author Ferdinand Rothkopf
 * @version (a version number or a date)
 */
import java.io.*;
import java.util.*;

public class StudentMarks {
    public static void main(String[] args) throws IOException {
        
        Scanner scanner = new Scanner(System.in); // Instantiate Scanner once at the beginning
        System.out.println("Enter the file name (default: prog5001_students_grade_2022.csv):");
        String fileName = scanner.nextLine().trim();

        if (fileName.isEmpty()) {
            fileName = "prog5001_students_grade_2022.csv"; // Default file
        }
        
        List<Student> students = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) { //opens file and initializes BufferedReader (try: closes if exception occures)
            String line; //holds each line as it is read
            
            System.out.println("Reading file...");
            int lineNumber = 0; // Declare and initialize lineNumber before the loop

            while ((line = br.readLine()) != null) { //reads each line of the file till the end
                System.out.println("Processing line: " + line); // Debugging output
                 
                if (!line.startsWith("#")) {  //making sure comments are not read
                    
                    String[] parts = line.split(","); //splits each line into parts
                    System.out.println("Parts length: " + parts.length); // Debugging output
                    
                    if (parts.length == 6) { //ensure line has exactly 6 components
                        
                        String firstName = parts[0].trim(); //extracts first component & removes trailing space
                        String secondName = parts[1].trim(); //extracts first component & removes trailing 
                        String id = parts[2].trim(); //extracts second component & trims it
                        
                        try {  //Attempt to parse third component as double
                            double assignment1Mark = Double.parseDouble(parts[3].trim()); //converts string into double
                            double assignment2Mark = Double.parseDouble(parts[4].trim());
                            double assignment3Mark = Double.parseDouble(parts[5].trim());
                            
                            students.add(new Student(firstName, secondName, id, assignment1Mark, assignment2Mark, assignment3Mark)); //creates new student object with parsed data
                            
                        } catch (NumberFormatException e) { // incase marks are not a valid integer
                            
                            System.err.println("Invalid mark format in line (line beeing ignored): " + line); //prints error message incase mark is not valid integer
                            
                        }
                    } else {
                        System.err.println("Wrong amount of parts in line (line beeing ignored): " + line); //prints error message incase there are not 6 parts in the line
                    }
                }
                
            }
            
            if (students.isEmpty()) { // User feedback if File is empty
                System.out.println("No student data found in the file.");
                return;
            }
            
            boolean exit = false;
            while (!exit) {
                System.out.println("\nMenu:");
                System.out.println("1. Display all students and their total marks");
                System.out.println("2. Filter students by threshold");
                System.out.println("3. Display top 5 highest and lowest scoring students");
                System.out.println("4. Exit");
                System.out.print("Enter your choice: ");

                int choice = 0;
                try {
                    choice = scanner.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter an integer value.");
                    scanner.nextLine(); // Clear the invalid input
                    continue;
                }

                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        System.out.println("\nStudents and their marks:");
                        for (Student student : students) {
                            System.out.println(student);
                        }
                        break;
                    case 2:
                        filterByThreshold(students, scanner);
                        break;
                    case 3:
                        sortAndPrintTopStudents(students);
                        break;
                    case 4:
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + fileName);
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }

    static class Student {
        private String firstName;
        private String secondName;
        private String id;
        private double assignment1Mark;
        private double assignment2Mark;
        private double assignment3Mark;
        public Student(String firstName, String secondName, String id, double assignment1Mark, double assignment2Mark, double assignment3Mark) {
            this.firstName = firstName;
            this.secondName = secondName;
            this.id = id;
            this.assignment1Mark = assignment1Mark;
            this.assignment2Mark = assignment2Mark;
            this.assignment3Mark = assignment3Mark;
        }
        public double getTotalMarks() {
            return assignment1Mark + assignment2Mark + assignment3Mark;
        }
        @Override
        public String toString() {
            return String.format("%s (%s) %s: %.2f, %.2f, %.2f | Total: %.2f",
                firstName, secondName, id, assignment1Mark, assignment2Mark, assignment3Mark, getTotalMarks());
        }
    }
    
    public static void filterByThreshold(List<Student> students, Scanner scanner) {
        System.out.println("\nEnter the threshold value:");
        double threshold = 0;
        try {
            threshold = scanner.nextDouble();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a double value.");
            scanner.nextLine(); // Clear the invalid input
            return;
        }
        System.out.println("\nStudents with total marks less than " + threshold + ":");
        boolean found = false;
        for (Student student : students) {
            if (student.getTotalMarks() < threshold) {
                System.out.println(student);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No students found with total marks less than " + threshold);
        }
    }
    
    public static void sortAndPrintTopStudents(List<Student> students) {
        int n = students.size(); // Bubble sort
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (students.get(j).getTotalMarks() > students.get(j + 1).getTotalMarks()) { // Swap students[j] and students[j + 1]
                    Student temp = students.get(j);
                    students.set(j, students.get(j + 1));
                    students.set(j + 1, temp);
                }
            }
        }

        System.out.println("\nTop 5 Lowest Scoring Students:");
        for (int i = 0; i < 5 && i < n; i++) {
            System.out.println(students.get(i));
        }

        System.out.println("\nTop 5 Highest Scoring Students:");
        for (int i = n - 1; i >= n - 5 && i >= 0; i--) {
            System.out.println(students.get(i));
        }
    }
}