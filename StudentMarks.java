
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
                        
                        String name = parts[0].trim(); //extracts first component & removes trailing space
                        String id = parts[1].trim(); //extracts second component & trims it
                        
                        try {  //Attempt to parse third component as integer
                            double assignment1Mark = Double.parseDouble(parts[2].trim()); //converts string into double
                            double assignment2Mark = Double.parseDouble(parts[3].trim());
                            double assignment3Mark = Double.parseDouble(parts[4].trim());
                            
                            students.add(new Student(name, id, assignment1Mark, assignment2Mark, assignment3Mark)); //creates new student object with parsed data
                            
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
    }
    
    static class Student {
        private String name;
        private String id;
        private double assignment1Mark;
        private double assignment2Mark;
        private double assignment3Mark;
        public Student(String name, String id, double assignment1Mark, double assignment2Mark, double assignment3Mark) {
            this.name = name;
            this.id = id;
            this.assignment1Mark = assignment1Mark;
            this.assignment2Mark = assignment2Mark;
            this.assignment3Mark = assignment3Mark;
        }
        @Override
        public String toString() {
            return String.format("%s (%s): %.2f, %.2f, %.2f",
                name, id, assignment1Mark, assignment2Mark, assignment3Mark);
}
    }
}