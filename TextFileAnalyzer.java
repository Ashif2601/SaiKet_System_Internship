import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class TextFileAnalyzer {
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("----- Text File Analyzer -----");
        System.out.print("Enter path to text file: ");
        String path = sc.nextLine().trim();
        File f = new File(path);
        if(!f.exists() || !f.isFile()) {
            System.out.println("File not found: " + path);
            return;
        }
        try{
            AnalysisResult result = analyzeFile(f);
            System.out.println("Lines: " + result.lines);
            System.out.println("Words: " + result.words);
            System.out.println("Characters: " + result.characters);
            System.out.printf("Average words per line: %.2f%n", result.lines == 0 ? 0.0 : (double) result.words / result.lines);
            System.out.println("Enter a word to search (press Enter to skip): ");
            String query = sc.nextLine().trim();
            if(!query.isEmpty()){
                int occurrences = countWordOccurrences(f, query);
                System.out.println("Occurrences of \"" + query + "\": " + occurrences);
            }
        } catch (IOException e){
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    static class AnalysisResult{
        int lines = 0;
        int words = 0;
        int characters = 0;
    }

    private static AnalysisResult analyzeFile(File f) throws IOException{
        AnalysisResult res = new AnalysisResult();
        try (BufferedReader br = new BufferedReader(new FileReader(f))){
            String line;
            while ((line = br.readLine()) != null){
                res.lines++;
                res.characters += line.length() + System.lineSeparator().length();
                String[] tokens = line.trim().isEmpty() ? new String[0] : line.trim().split("\\s+");
                res.words += tokens.length;
            }
        }
        return res;
    }

    private static int countWordOccurrences(File f, String query) throws IOException{
        int count = 0;
        String qLower = query.toLowerCase();
        try(BufferedReader br = new BufferedReader(new FileReader(f))){
            String line;
            while((line =br.readLine()) != null){
                String[] tokens = line.split("\\W+");
                for(String t : tokens){
                    if(t.equalsIgnoreCase(qLower)) count++;
                }
            }
        }
        return count;
    }
}
