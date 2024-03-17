package sandwich.de.monopoly.DennisUtilitiesPackage.Java;

import java.io.*;

/**
 *  <h2>Utilities for Java and JavaFx to create graphical programs</h2>
 *  <p>
 *  These utilities are intended to simplify programming with Java/JavaFX
 *  and keep the code clean. In this specific version saving and reading
 *  out data is simplified. Here you get methods for creating, reading,
 *  deleting, etc. data.
 *  <p>
 *  <h3>Infos for this specific version:</h3>
 *  <p>
 *  Nop, you don't have to know anything to use this.
 *  But I should specify here how to specify the path,
 *  but I still haven't done it yet
 *  <p>
 * @author bauer
 * @version 1.0
 * @since 16.03.2024
 */

public class SaveDataUtilities {

    //Error message text Codes.
    private static final String ERROR_START = "\033[1;31m";
    private static final String ERROR_END = "\033[0m";


    /**
     * This method saves an Object as a file.
     * @param fileName This is the name form the file that saves the data.
     * @param data This is the name of the object to be saved
     */
    public static void saveData(String fileName, Object data) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(fileName))) {
            outputStream.writeObject(data);
        } catch (IOException e) {
            System.out.println(ERROR_START + "Saving Object data not possible" + ERROR_END);
        }
    }

    /**
     * This method returns an Object that is saved as a file.
     * @param fileName This is the name form the file with the saved object.
     * @return Returns the object that was saved or null if it was not found.
     */
    public static Object getData(String fileName) {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(fileName))) {
            return inputStream.readObject();
        } catch (NotSerializableException ignore) {
            return null; //CHANGE
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(ERROR_START + "Reading Object data not possible" + ERROR_END);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * This method checks if a file exists.
     * @param fileName This is the name form the file.
     * @return Returns true if the file exists, false when not.
     */
    public static Boolean existTheFile(String fileName) {
        File f = new File(fileName);
        return f.exists();
    }

    /**
     * This method deletes a file.
     * @param fileName This is the name of the file to be deleted.
     */
    public static void deleteFile(String fileName) {
        File f = new File(fileName);
        if(f.exists()) {
            if(!f.delete())
                System.out.println(ERROR_START + "File deletion failed" + ERROR_END);
        } else
            System.out.println(ERROR_START + "The file that was supposed to be deleted no longer exists" + ERROR_END);
    }

    /**
     * This method returns the number of files in a folder.
     * @param folderPath This is the folder path.
     * @return Returns the number of files in the folder.
     */
    public static int countFilesInFolder(String folderPath) {
        File[] files = isFolderPathAWorkingDirectory(folderPath);

        if (files == null)
            throw new NullPointerException("Can't count Files because files is null!");

        int count = 0;
        for (File file : files)
            if (file.isFile())
                count++;
        return count;
    }

    /**
     * This method returns the names of files in a folder.
     * @param folderPath This is the folder path.
     * @return Returns the names of files in the folder.
     */
    public static String[] getFilesNameInFolder(String folderPath) {

        File[] files = isFolderPathAWorkingDirectory(folderPath);

        if (files == null)
            throw new NullPointerException("Can't get Names from Files because files is null!");

        String[] names = new String[files.length];
        for (int i = 0; i < files.length; i++) {
            names[i] = files[i].getName();
        }

        return names;
    }

    /**
     * This method tests whether a directory is correct.
     * @param path This is the folder path.
     * @return Returns the names of files in the folder.
     */
    private static File[] isFolderPathAWorkingDirectory(String path) {
        File folder = new File(path);

        if (!folder.isDirectory()) {
            System.out.println(ERROR_START + "A specified path is not a directory" + ERROR_END);
            return null;
        }

        File[] files = folder.listFiles();

        if (files == null) {
            System.out.println(ERROR_START + "No files found in directory" + ERROR_END);
            return null;
        }

        return files;
    }


}
