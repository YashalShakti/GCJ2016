package CountingSheep;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    private static HashMap<Integer, Boolean> mIsDigitDoneMap = new HashMap<>();
    private static String mIToS;

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

            int numOfTestCases = input.nextInt();
            for (int i = 0; i < numOfTestCases; i++) {
                initHashMap();
                int N = input.nextInt();
                System.out.println("In " + N);
                if (N == 0) {
                    writer.write("Case #" + (i + 1) + ": INSOMNIA\n");
                } else {
                    int result = calc(N);
                    writer.write("Case #" + (i + 1) + ": " + result + "\n");
                }
            }
            writer.flush();
            writer.close();
            input.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static int calc(int n) {
        boolean done = false;
        int number = n;
        int multiplier = 0;
        while (!done) {
            mIToS = Integer.toString(n);
            System.out.println("mIToS " + mIToS);
            boolean containsAllDigits = true;
            for (int i = 0; i < 10; i++) {
                containsAllDigits = containsAllDigits & processDigit(i);
            }
            if (containsAllDigits) {
                done = true;
            } else {
                n = number * ++multiplier;
            }
        }
        return Integer.parseInt(mIToS);
    }

    private static boolean processDigit(int digit) {
        if (!mIsDigitDoneMap.get(digit)) {
            if (mIToS.contains(Integer.toString(digit))) {
                mIsDigitDoneMap.put(digit, true);
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    private static void initHashMap() {
        mIsDigitDoneMap.clear();
        for (int i = 0; i < 10; i++) {
            mIsDigitDoneMap.put(i, false);
        }
    }
}

