package org.example;

import java.sql.*;
import java.util.Scanner;

public class DB_Menu_CRUB
{
    private static String driver = "com.mysql.cj.jdbc.Driver";
    private static String url = "jdbc:mysql://localhost:3306/school";
    private static String user = "root";
    private static String password = "";
    
    private static Connection connection;

    static
    {
        try
        {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, user, password);
        }
        catch (ClassNotFoundException | SQLException e)
        {
            throw new RuntimeException(e);
        }
    }
    
    //Insert
    public static boolean addStudent() throws ClassNotFoundException, SQLException
    {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("\nVeuillez rentrer le matricule de l'élève : ");
        String matricule = scanner.nextLine();
        
        System.out.println("\nVeuillez rentrer le prenom de l'élève : ");
        String prenom = scanner.nextLine();
        
        System.out.println("\nVeuillez rentrer le nom de l'élève : ");
        String nom = scanner.nextLine();
        
        String insertionQuery = "INSERT INTO student (id, firstName, lastName) VALUES (?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(insertionQuery);
        preparedStatement.setString(1, matricule);
        preparedStatement.setString(2, prenom);
        preparedStatement.setString(3, nom);
        int rowsAffected = preparedStatement.executeUpdate();
        return rowsAffected > 0;
    }
    
    public static boolean addSubject() throws ClassNotFoundException, SQLException
    {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nVeuillez rentrer l'identifiant de la matière : ");
        String id = scanner.nextLine();

        System.out.println("\nVeuillez rentrer le nom de la matière ");
        String name = scanner.nextLine();

        int factor = 0;
        boolean valid = false;
        
        while(!valid)
        {
            System.out.println("\nVeuillez rentrer le quotient de la matière");
            
            try
            {
                factor = scanner.nextInt();
                
                if (factor > 0)
                {
                     valid = true;   
                }
                else
                {
                    System.out.println("\nLe quotient ne peut pas être éqale à 0 ou être négative"); 
                }
                
            }
            catch (Exception e)
            {
                
            }
        }

        String insertionQuery = "INSERT INTO subject (id, name, factor) VALUES (?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(insertionQuery);
        preparedStatement.setString(1, id);
        preparedStatement.setString(2, name);
        preparedStatement.setInt(3, factor);
        int rowsAffected = preparedStatement.executeUpdate();
        return rowsAffected > 0;
    }
    
    public static boolean addGrad() throws ClassNotFoundException, SQLException
    {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nVeuillez rentrer le matricule de l'élève");
        String matricule = scanner.nextLine();

        System.out.println("\nVeuillez rentrer l'identifiant de la matière : ");
        String id = scanner.nextLine();
        
        int grade = 0;
        boolean valid = false;

        while(!valid)
        {
            System.out.println("\nVeuillez rentrer la note de l'élève entre 0 et 20 : ");

            try
            {
                grade = scanner.nextInt();

                if (grade >= 0 && grade <= 20)
                {
                    valid = true;   
                }
                else
                {
                    System.out.println("\nLa note doit être entre 0 et 20"); 
                }

            }
            catch (Exception e)
            {

            }
        }

        String insertionQuery = "INSERT INTO grade (id, student_id, subject_id, grade) VALUES (?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(insertionQuery);
        preparedStatement.setInt(1, 0);
        preparedStatement.setString(2, matricule);
        preparedStatement.setString(3, id);
        preparedStatement.setInt(4, grade);
        int rowsAffected = preparedStatement.executeUpdate();
        return rowsAffected > 0;
    }
    
    public static void getStudentBySubjectAVG() throws ClassNotFoundException, SQLException
    {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nVeuillez rentrer le nom de la matière : ");
        String name = scanner.nextLine();
        
        System.out.println("");

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT student.firstName, student.lastName, subject.name, AVG(grade.grade) FROM subject INNER JOIN grade ON subject.id = grade.subject_id INNER JOIN student ON grade.student_id = student.id where subject.name = ? GROUP BY student.firstName, subject.name");
        preparedStatement.setString(1, name);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next())
        {
            System.out.println(" l'élève " + resultSet.getString("firstName") + " " + resultSet.getString("lastName") + " à " + resultSet.getFloat("AVG(grade.grade)") + " de moyenne dans la matière " + resultSet.getString("subject.name"));
        }
    }
    
    public static void getGreaterStudentBySubjectAVG() throws ClassNotFoundException, SQLException
    {
        System.out.println("\nVoici les meilleurs élève par matière : ");
        System.out.println("");

        PreparedStatement preparedStatement = connection.prepareStatement("select * from ( select student.firstName, student.lastName, Subject.name, avg(grade.grade), rank() over(partition by Subject.id order by avg(grade.grade) desc) rn from student inner join grade on student.id = grade.student_id inner join subject on subject.id = grade.subject_id group by student.id, subject.id) t where rn = 1");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next())
        {
            System.out.println("L'élève " + resultSet.getString("firstName") + " " + resultSet.getString("lastName") + " à " + resultSet.getFloat("AVG(grade.grade)") + " de moyenne dans la matière " + resultSet.getString("name") + "et est le meilleur dans la matière");
        }
    }
    
    public static void getStudentByGradeLessThan15() throws ClassNotFoundException, SQLException
    {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nVoici la liste des élève qui doivent retravailler la matière : ");

        System.out.println("");

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT student.firstName, student.lastName, subject.name, grade FROM subject INNER JOIN grade ON subject.id = grade.subject_id INNER JOIN student ON grade.student_id = student.id where grade.grade < 15 GROUP BY student.firstName, subject.name");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next())
        {
            System.out.println(" l'élève " + resultSet.getString("firstName") + " " + resultSet.getString("lastName") + " avec la note de " + resultSet.getFloat("grade") + " doit retravailler dans la matière " + resultSet.getString("name"));
        }
    }
}