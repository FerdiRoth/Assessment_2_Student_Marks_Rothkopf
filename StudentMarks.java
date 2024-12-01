
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
            
            while ((line = br.readLine()) != null) { //reads each line of the file till the end
                System.out.println("Processing line: " + line); // Debugging output
                
                if (!line.startsWith("#")) {  //making sure comments are not read
                    
                    String[] parts = line.split(","); //splits each line into parts
                    System.out.println("Parts length: " + parts.length); // Debugging output
                    
                    if (parts.length == 6) { //ensure line has exactly 5 components
                        
                        String name = parts[0].trim(); //extracts first component & removes trailing space
                        String id = parts[1].trim(); //extracts second component & trims it
                        
                        try {  //Attempt to parse third component as integer
                            int mark1 = Integer.parseInt(parts[2].trim()); //convert string into integer
                            int mark2 = Integer.parseInt(parts[3].trim());
                            int mark3 = Integer.parseInt(parts[4].trim());
                            
                            students.add(new Student(name, id, mark1, mark2, mark3)); //creates new student object with parsed data
                            
                        } catch (NumberFormatException e) { // incase marks are not a valid integer
                            
                            System.err.println("Invalid mark format in line: " + line); //prints error message incase mark is not valid integer
                            
                        }
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
        private int assignment1Mark;
        private int assignment2Mark;
        private int assignment3Mark;
        public Student(String name, String id, int assignment1Mark, int assignment2Mark, int assignment3Mark) {
            this.name = name;
            this.id = id;
            this.assignment1Mark = assignment1Mark;
            this.assignment2Mark = assignment2Mark;
            this.assignment3Mark = assignment3Mark;
        }
        @Override
        public String toString() {
            return String.format("%s (%s): %d, %d, %d",
                    name, id, assignment1Mark, assignment2Mark, assignment3Mark);
        }
    }
}