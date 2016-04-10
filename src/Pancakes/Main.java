package Pancakes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    private static String s;

    public static void main() {
        try {

            Scanner input = new Scanner(System.in);
            System.out.print("File name -> ");
            String fileName = input.nextLine();
            File inFile = new File(fileName + ".in");
            File outFile = new File(fileName + ".out");
            outFile.createNewFile();
            FileWriter writer = new FileWriter(outFile);

            input = new Scanner(inFile);

            int numOfTestCases = Integer.parseInt(input.nextLine());
            for (int i = 0; i < numOfTestCases; i++) {
                s = input.nextLine();
                if (!s.contains("+")) {
                    writer.write("Case #" + (i + 1) + ": 1\n");
                } else if (!s.contains("-")) {
                    writer.write("Case #" + (i + 1) + ": 0\n");
                } else {
                    int result = calc();
                    writer.write("Case #" + (i + 1) + ": " + result + "\n");
                }
            }
            writer.flush();
            writer.close();
            input.close();

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static int calc() {
        boolean isDone = false;
        int count = 0;
        while (!isDone) {
            int lastOfN = s.lastIndexOf('-');
            flip(lastOfN);
            isDone = isDone();
            count++;
        }
        return count;
    }

    private static void flip(int lastOfN) {
        String newS = "";
        for (int i = 0; i < lastOfN + 1; i++) {
            if (s.charAt(i) == '-') {
                newS = newS + '+';
            } else {
                newS = newS + '-';
            }
        }
        s = newS;
    }

    private static boolean isDone() {
        return !s.contains("-");
    }
}

