/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package StarshipBattleDestiny;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 *
 * @author OWner
 */
public class CacheManager {
    
    private static final String COORDINATE_FILE = "coordinateFile.txt";
    private static final String ADMIN_PASSWORD_FILE = "adminPasswordFile.txt";
    private static final String GC_CODE_FILE = "gcCodeFile.txt";
    
    public CacheManager() throws FileNotFoundException, IOException {
        String adminPasswordOnFile = readAdminPassword();
        String coordinatesOnFile = readCoordinates();
        String gcCodeOnFile = readGcCode();
        String blankSpace = "";
        
        if (adminPasswordOnFile.equals(blankSpace)) {
            String defaultAdminPassword = "Turtle289";
            saveAdminPassword(defaultAdminPassword);
        }
        if (coordinatesOnFile.equals(blankSpace)) {
            String defaultCoordinates = "N 39 00.000  W 084 00.000";
            saveCoordinates(defaultCoordinates);
        }
        if (gcCodeOnFile.equals(blankSpace)) {
            String defaultGcCode = "GCXXXXX";
            saveGcCode(defaultGcCode);
        }
    } //End CacheManager Constructor
    
    /**
     * Saves the coordinates to the file coordinateFile.txt
     * 
     * @param newCoordinates
     * @throws IOException 
     */
    public void saveCoordinates(String newCoordinates) throws IOException {
        try (FileWriter inputFile = new FileWriter(COORDINATE_FILE)) {
            inputFile.write(newCoordinates);
        } 
        
    } //End readFile method
    
    /**
     * Reads the coordinates from the file coordinateFile.txt and returns it.
     * 
     * @return savedCoordinates
     * @throws FileNotFoundException 
     */
    public String readCoordinates() throws FileNotFoundException {
        File thisFileHere = new File (COORDINATE_FILE);
        String savedCoordinates;
        Scanner outputFile = new Scanner(thisFileHere);
        if (outputFile.hasNextLine()){
            savedCoordinates = outputFile.nextLine();     
        }
        else {
            savedCoordinates = "No coords in file.\n"
                    + "Please Contact Admin for Assistance.";
        }
        return savedCoordinates;
    } //End updateCoordinates method 
    
    /**
     * Saves the admin password to the file adminPasswordFile.txt
     * @param newAdminPassword
     * @throws IOException 
     */
    public void saveAdminPassword(String newAdminPassword) throws IOException {
        try (FileWriter inputFile = new FileWriter(ADMIN_PASSWORD_FILE)) {
            inputFile.write(newAdminPassword);
        }
    } //End readFile method
    
    /**
     * Reads the admin password from the file adminPasswordFile.txt and returns it.
     * 
     * @return savedAdminPassword
     * @throws FileNotFoundException 
     */
    public String readAdminPassword() throws FileNotFoundException {
        File thisFileHere = new File (ADMIN_PASSWORD_FILE);
        String savedAdminPassword;
        Scanner outputFile = new Scanner(thisFileHere);
        if (outputFile.hasNextLine()){
            savedAdminPassword = outputFile.nextLine();     
        }
        else {
            savedAdminPassword = "No coords in file.";
        }
        return savedAdminPassword;
    } //End updateCoordinates method 
    
    /**
     * Saves the GC Code to the file gcCodeFile.txt
     * 
     * @param newGcCode
     * @throws IOException 
     */
    public void saveGcCode(String newGcCode) throws IOException {
        String newGcCodeUpperCase = newGcCode.toUpperCase();
        try (FileWriter inputFile = new FileWriter(GC_CODE_FILE)) {
            inputFile.write(newGcCodeUpperCase);
        }
    } //End readFile method
    
    /**
     * Reads the GC Code from the file gcCodeFile.txt and returns it.
     * 
     * @return savedCoordinates
     * @throws FileNotFoundException 
     */
    public String readGcCode() throws FileNotFoundException {
        File thisFileHere = new File (GC_CODE_FILE);
        String savedCoordinates;
        Scanner outputFile = new Scanner(thisFileHere);
        if (outputFile.hasNextLine()){
            savedCoordinates = outputFile.nextLine();     
        }
        else {
            savedCoordinates = "No coords in file.";
        }
        return savedCoordinates;
    } //End updateCoordinates method 
    
    /**
     * Gives the admin the option to change the GC Code.  If yes then the admin
     * can update the GC Code.  If no then it is set to whatever the saved GC code is.
     * 
     * @return gcCode
     * @throws FileNotFoundException 
     */
    public String changeGcCode() throws FileNotFoundException {
        String gcCode;
        String gcChangeMsg = "Would you like to change the GC Code?";

        int changeGcCode = JOptionPane.showConfirmDialog(null, gcChangeMsg, "[ADMIN] GC Code Options", JOptionPane.YES_NO_OPTION);
        if (changeGcCode == JOptionPane.YES_OPTION) {
            gcCode = JOptionPane.showInputDialog("Please Enter the New GC Code:  ");
        }
        else {
            gcCode = readGcCode();
        }
        
        return gcCode;
    } //End changeGcCode method
    
    /**
     * Gives the admin the option to update the coordinates.  If yes then the admin
     * can update the coords.  If no then it is set to whatever the saved coords is.
     * 
     * @return finalCoordinates
     * @throws IOException 
     */
    public String changeCoordinates() throws IOException {
        String finalCoordinates;
        String coordChangeMsg = "Would you like to change the Coordinates?";

        int changeCoords = JOptionPane.showConfirmDialog(null, coordChangeMsg, "[ADMIN] Coordinate Options", JOptionPane.YES_NO_OPTION); 
        if (changeCoords == JOptionPane.YES_OPTION) {
            finalCoordinates = JOptionPane.showInputDialog("Please Enter the New Coordinates:  ");
        }
        else {
            finalCoordinates = readCoordinates();
        }
        return finalCoordinates;
    } //End changeCoordinates method
    
    /**
     * Gives the admin the option to update the admin password.  If yes then the admin
     * can update their password.  If no then it is set to whatever the saved password is.
     * 
     * @return adminPassword
     * @throws IOException 
     */
    public String changeAdminPassword() throws IOException {
        String adminPassword, adminPassword1, adminPassword2;
        String coordChangeMsg = "Would you like to change the Admin Password?";

        int changeAdminPassword = JOptionPane.showConfirmDialog(null, coordChangeMsg, "[ADMIN] Admin Password Options", JOptionPane.YES_NO_OPTION); 
        if (changeAdminPassword == JOptionPane.YES_OPTION) {
            adminPassword1 = JOptionPane.showInputDialog("Please Enter the new Admin Password:  ");
            adminPassword2 = JOptionPane.showInputDialog("Re-Enter the new Admin Password to confirm:  ");
            if (adminPassword1.equals(adminPassword2)) {
                adminPassword = adminPassword1;  
            } //end if-statement for if new admin passwords do match
            else {
                String newPasswordMatchErrorMsg = "~!~ New admin passwords did not match ~!~\n"
                        + "Setting password to previous admin password.";
                JOptionPane.showMessageDialog(null, newPasswordMatchErrorMsg, "[ADMIN] New Admin Password Match Error", JOptionPane.INFORMATION_MESSAGE);  
                adminPassword = readAdminPassword();
                } //end else-statement for if new admin passwords do not match
            } //end if-statement for if admin chooses "Yes" on changing admin password
        else {
            adminPassword = readAdminPassword();
        } //end else-statement for if admin chooses "No" on changing admin password
        return adminPassword;
    } //End changeAdminPassword method
    
    /**
     * Displays a JOptionPane window with the updated, saved GC Code, coordinates,
     * and admin password.
     * 
     * @throws FileNotFoundException 
     */
    public void cacheUpdateReview() throws FileNotFoundException {  
        String reviewGcCode = readGcCode();
        String reviewCoordinates = readCoordinates();
        String reviewAdminPassword = readAdminPassword();
        
        String reviewChangesMsg = "Your new GC Code is:  " + reviewGcCode +
                "\nYour new coords are:  " + reviewCoordinates +
                "\nYour new admin password is:  " + reviewAdminPassword;
        JOptionPane.showMessageDialog(null, reviewChangesMsg, "[ADMIN] Review Changes", JOptionPane.INFORMATION_MESSAGE);     
    } //End cacheUpdateReview method
    
    
} //End of CacheManager Class