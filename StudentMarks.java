
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
            System.out.println("\nNumber of students added: " + students.size());
            for (Student student : students) {
                System.out.println(student);
            }
            
        }
        filterByThreshold(students, scanner);
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
        double threshold = scanner.nextDouble();
        System.out.println("\nStudents with total marks less than " + threshold + ":");
        for (Student student : students) {
            if (student.getTotalMarks() < threshold) {
            System.out.println(student);
            }
        }
    }
}