package CoinJam;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.*;

public class Main {
    private static final BigInteger TWO = new BigInteger("2");
    private static int sN;
    private static int sJ;
    private static FileWriter sWriter;
    private static List<String> sDivisors;
    private static HashMap<BigInteger, String> sNonPrimes;
    private static Set<BigInteger> sPrimes;
    private static long sTimeTaken = 0L;
    private static long sTimeRelaxation = 0L;
    private static List<BigInteger> sPending;

    public static void main() {
        try {

            Scanner input = new Scanner(System.in);
            System.out.print("File name -> ");
            String fileName = input.nextLine();
            File inFile = new File(fileName + ".in");
            File outFile = new File(fileName + ".out");
            outFile.createNewFile();
            sWriter = new FileWriter(outFile);
            input = new Scanner(inFile);
            input.nextInt();
            sDivisors = new ArrayList<>();
            sNonPrimes = new HashMap<>();
            sPending = new ArrayList<>();
            sPrimes = new HashSet<>();
            sN = input.nextInt();
            sJ = input.nextInt();
            sWriter.write("Case #" + (1) + ": \n");
            calc();
            sWriter.flush();
            sWriter.close();
            input.close();

        } catch (FileNotFoundException ex) {
            System.out.println("Need correct file name please\n");
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void calc() throws IOException {
        System.out.println(sN);
        System.out.println(sJ);

        BigInteger upper = TWO.pow(sN).subtract(BigInteger.ONE);
        BigInteger lower = TWO.pow(sN - 1);
        System.out.println(lower + " " + upper);

        parent:
        for (BigInteger index = lower; index.compareTo(upper) < 0 && sJ > 0; index = index.add(BigInteger.ONE)) {

            BigInteger i = new BigInteger(index.toString(2));
            String temp = i.toString(2);
            for (int filter = 2; filter < 10; filter++) {
                if (temp.contains(filter + "")) {
                    continue parent;
                }
            }
            if (i.mod(TWO).equals(BigInteger.ZERO)) {
                continue;
            }
            boolean isJam = checkCoinJam(i);
            if (isJam) {
                writeToOut(i);
            }
        }
        if (sJ > 0) {
            sTimeRelaxation = 1000L;
            for (BigInteger bigInteger : sPending) {
                boolean isJam = checkCoinJam(bigInteger);
                writeToOut(bigInteger);
            }
        }
    }

    private static void writeToOut(BigInteger i) throws IOException {

        sWriter.write(i.toString() + " ");
        System.out.println(i.toString());
        for (int q = 0; q < 9; q++) {
            sWriter.write(sDivisors.get(q) + " ");
        }
        sWriter.write("\n");
        sJ--;
    }

    private static boolean checkCoinJam(BigInteger sNumber) {
        boolean isJam = true;
        sDivisors.clear();
        for (int i = 2; i < 11; i++) {
            BigInteger n = changeBaseTo(sNumber, i);
            sTimeTaken = System.currentTimeMillis();
            boolean isprime = isPrime(n);
            isJam = isJam & !isprime;
            if (isprime)
                break;
        }
        return isJam;
    }

    private static BigInteger changeBaseTo(BigInteger sNumber, int n) {
        try {
            return new BigInteger(sNumber.toString(), n);
        } catch (NumberFormatException e) {
            System.out.println(sNumber);
            e.printStackTrace();
            System.exit(-1);
            return null;
        }
    }

    private static boolean isPrime(BigInteger n) {
        if (sNonPrimes.containsKey(n)) {
            sDivisors.add(sNonPrimes.get(n));
            return false;
        } else if (sPrimes.contains(n)) {
            return true;
        }
        if (n.isProbablePrime(1)) {
            sPrimes.add(n);
            return true;
        }
        if (n.compareTo(TWO) < 0) return false;
        if (n.mod(TWO).equals(BigInteger.ZERO)) {
            sNonPrimes.put(n, "2");
            sDivisors.add("2");
            return false;
        } else if (n.mod(new BigInteger("3")).equals(BigInteger.ZERO)) {
            sNonPrimes.put(n, "3");
            sDivisors.add("3");
            return false;
        }
        for (BigInteger i = new BigInteger("6"); i.multiply(i).compareTo(n) < 0; i = i.add(new BigInteger("6"))) {
            if (System.currentTimeMillis() - sTimeTaken > 500 + sTimeRelaxation) {
                sPending.add(n);
                return true;
            }
            if (n.mod(i.subtract(BigInteger.ONE)).equals(BigInteger.ZERO)) {
                sNonPrimes.put(n, i.subtract(BigInteger.ONE).toString());
                sDivisors.add(i.subtract(BigInteger.ONE).toString());
                return false;
            } else if (n.mod(i.add(BigInteger.ONE)).equals(BigInteger.ZERO)) {
                sNonPrimes.put(n, i.add(BigInteger.ONE).toString());
                sDivisors.add(i.add(BigInteger.ONE).toString());
                return false;
            }
        }
        return true;
    }
}