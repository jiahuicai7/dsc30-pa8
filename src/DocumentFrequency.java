/*
 * NAME: Jiahui Cai
 * PID: A16637420
 */

import java.io.*;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

/**
 * This class records the frequency of the words in a file.
 *
 * @author Jiahui Cai
 * @since 05/25/2022
 */
public class DocumentFrequency {
    // Initialize a new ArrayList uses hash table.
    private ArrayList<HashTable> tableLst;

    /**
     * This method takes a path to a text file and read all documents in the file.
     * @param path text file that was passed in.
     * @throws IOException if the file is not found.
     */
    public DocumentFrequency(String path) throws IOException {
        // Initialize it to an empty Arraylist.
        tableLst = new ArrayList<>();
        // Used the codes from homework 6.
        File file = new File(path);
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                // Create a new hash table.
                HashTable eleTable = new HashTable();
                // Get the next line and seperated by empty string.
                String[] lines = scanner.nextLine().split(" ");
                // Loop in to the word, make it lowercase, and insert words into hash table.
                for(int i = 0; i < lines.length; i++) {
                    String lower = lines[i].toLowerCase();
                    eleTable.insert(lower);
                }
                // Add this hash table into the List.
                this.tableLst.add(eleTable);
            }
            scanner.close();
        }
        catch (FileNotFoundException e) {
        }
    }

    /**
     * This method returns the number of documents stored in this object.
     * @return the number of documents stored.
     */
    public int numDocuments() {
        return this.tableLst.size();
    }

    /**
     * This method takes in a word and return the document frequency of the word.
     * @param word The word we want to find frequency for.
     * @return The document frequency of the word.
     */
    public int query(String word) {
        // Initialize a counter.
        int stringCount = 0;
        // Loop through the number of documents stored in this object.
        for(int i = 0; i < this.numDocuments(); i++) {
            // Make it to lowercase.
            String lower = word.toLowerCase();
            // If the word is in the hash table, increase count by 1.
            if(this.tableLst.get(i).lookup(lower)) {
                stringCount ++;
            }
        }
        // Return the count.
        return stringCount;
    }

    public static void main (String[] args) throws IOException{
        DocumentFrequency test = new DocumentFrequency("./src/files/test.txt");
        System.out.println(test.numDocuments());
        System.out.println(test.query("quick"));
    }

}
