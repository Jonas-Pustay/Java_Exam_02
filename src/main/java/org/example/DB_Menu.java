package org.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.SQLException;
import java.util.Scanner;

public class DB_Menu
{
    private static boolean running;
    private static int value;

    public DB_Menu()
    {
        
    }
    
    public void startMenu()
    {
        running = true;
    }

    public void stopMenu()
    {
        running = false;
    }
    
    public boolean isRunning()
    {
        return running;
    }
    
    private void checkValue(int min, int max)
    {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nEntrer une valeur entre " + min + " et " + max + " : ");

        try
        {
            value = scanner.nextInt();
        }
        catch (Exception e)
        {
            checkValue(min, max);
        }

        if (value < min || value > max)
        {
            checkValue(min, max);
        }
    }
    
    public void showMenu()
    {
        System.out.println("\nBienvenue dans le Menu : ");
        System.out.println("");
        System.out.println("1 : Ajouter des informations dans la base de donnée");
        System.out.println("2 : Afficher les moyennes des étudiants par matière");
        System.out.println("3 : Afficher le meilleur étudiant par matière");
        System.out.println("4 : Afficher pour chaque étudiant les matières à retravailler");
        System.out.println("5 : Modifier des informations dans la base de donnée");

        checkValue(1,5);
        
        switch (value)
        {
            case 1:
                insertToDB();
                break;
            case 2:
                displayStudentBySubjectAVG();
                break;
            case 3:
                displayGreaterStudentBySubjectAVG();
                break;
            case 4:
                displayStudentByGradeLessThan15();
                break;
            case 5:
                System.out.println("Vous avez choisi 5");
                break;
        }
    }
    
    private void insertToDB() {
        System.out.println("\nQue faut t-il ajouter : ");
        System.out.println("");
        System.out.println("1 : Ajouter un étudiant");
        System.out.println("2 : Ajouter une matière");
        System.out.println("3 : Ajouter une note");
        
        checkValue(1,3);
        
        switch (value)
        {
            case 1:
                try
                {
                    if (DB_Menu_CRUB.addStudent())
                    {
                        System.out.println("\nétudiant ajouter avec success");
                        writeToFile("L'ajout de l'étudiant', s'est passer correctement");
                        showMenu();
                    }
                    else
                    {
                        System.out.println("\nl'étudiant n'a pas plus être ajouter");
                        writeToFile("\nl'étudiant n'a pas plus être ajouter");
                        showMenu();
                    }
                }
                catch (Exception e) { System.out.println(e.getMessage()); writeToFile(e.getMessage()); }
                break;
            case 2:
                try
                {
                    if (DB_Menu_CRUB.addSubject())
                    {
                        System.out.println("\nMatière ajouter avec success");
                        writeToFile("L'ajout de la matière, s'est passer correctement");
                        showMenu();
                    }
                    else
                    {
                        System.out.println("\nMatière n'a pas plus être ajouter");
                        writeToFile("\nMatière n'a pas plus être ajouter");
                        showMenu();
                    }
                }
                catch (Exception e) { System.out.println(e.getMessage()); writeToFile(e.getMessage()); }
                break;
            case 3:
                try
                {
                    if (DB_Menu_CRUB.addGrad())
                    {
                        System.out.println("\nNote ajouter avec success");
                        writeToFile("L'ajout de la note, s'est passer correctement");
                        showMenu();
                    }
                    else
                    {
                        System.out.println("\nLa note n'a pas plus être ajouter");
                        writeToFile("\nLa note n'a pas plus être ajouter");
                        showMenu();
                    }
                }
                catch (Exception e) { System.out.println(e.getMessage()); writeToFile(e.getMessage()); }
                break;
        }
    }
    
    private void displayStudentBySubjectAVG()
    {
        try
        {
            DB_Menu_CRUB.getStudentBySubjectAVG();
            writeToFile("L'affichage des élèves par une matière, s'est afficher correctement");
            showMenu();
        }
        catch (Exception e)
        {
            System.out.println("Le resultat ne pas être afficher");
            System.out.println(e.getMessage());
            writeToFile(e.getMessage());
            showMenu();
        }
        
    }
    
    private void displayGreaterStudentBySubjectAVG()
    {
        try
        {
            DB_Menu_CRUB.getGreaterStudentBySubjectAVG();
            writeToFile("L'affichage des meilleurs par matière, s'est afficher correctement");
            showMenu();
        }
        catch (Exception e)
        {
            System.out.println("Le resultat ne pas être afficher");
            System.out.println(e.getMessage());
            writeToFile(e.getMessage());
            showMenu();
        }
    }
    
    private void displayStudentByGradeLessThan15()
    {
        try
        {
            DB_Menu_CRUB.getStudentByGradeLessThan15();
            writeToFile("L'affichage des élèves avec une note inférieure à 15, s'est afficher correctement");
            showMenu();
        }
        catch (Exception e)
        {
            System.out.println("Le resultat ne pas être afficher");
            System.out.println(e.getMessage());
            writeToFile(e.getMessage());

            showMenu();
        }
    }
    
    private void writeToFile(String content)
    {
        BufferedWriter writer = null;
        try
        {
            // Open the file for writing (append mode)
            writer = new BufferedWriter(new FileWriter("C:\\Users\\jonas\\IdeaProjects\\Exam_Java_BDD\\src\\main\\java\\org\\example\\log.txt", true));

            // Write data to the file
            String data = content;
            writer.write(data);

            // Write a new line
            writer.newLine();

            // Flush the buffer to ensure all data is written to the file
            writer.flush(); // Optional but good practice
        }
        catch (Exception e)
            {
                e.printStackTrace();
            }
        finally
        {
            try
            {
                if (writer != null)
                {
                    // Close the file
                    writer.close();
                }
            }
            catch (Exception e)
                {
                    e.printStackTrace();
                }
        }
    }

}
