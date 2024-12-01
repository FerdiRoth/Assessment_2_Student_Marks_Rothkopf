
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
        System.out.println("Enter the file name:");
        String fileName = scanner.nextLine().trim();

        List<Student> students = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            
            System.out.println("Reading file...");
            
            while ((line = br.readLine()) != null) {
                if (!line.startsWith("#")) {
                    
                    String[] parts = line.split(",");
                    
                    if (parts.length == 5) {
                        
                        String name = parts[0].trim();
                        String id = parts[1].trim();
                        
                        try {
                            int mark1 = Integer.parseInt(parts[2].trim());
                            int mark2 = Integer.parseInt(parts[3].trim());
                            int mark3 = Integer.parseInt(parts[4].trim());
                            
                            students.add(new Student(name, id, mark1, mark2, mark3));
                            
                        } catch (NumberFormatException e) {
                            
                            System.err.println("Invalid mark format in line: " + line);
                            
                        }
                    }
                }
                
            }
        }
    }
    
    static class Student {
        private String name;
        private String id;
        private int assignement1Mark;
        private int assignment2Mark;
        private int assignment3Mark;
        public Student(String name, String id, int assignment1Mark, int assignment2Mark, int assignment3Mark) {
            this.name = name;
            this.id = id;
            this.assignment1Mark = assignment1Mark;
            this.assignment2Mark = assignment2Mark;
            this.assignment3Mark = assignment3Mark;
        }
    }
}