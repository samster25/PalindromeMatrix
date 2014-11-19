/* README
Author: Sammy Sidhu

This algorithms takes in a 2D matrix of characters and finds every possible Palindrome in every direction. 

We look Row-wise        Left<->Right
We look Column-wise     Up<->Down
We look Up-Diagonally   Lower-Left <-> Upper-Right
We look Down-Diagonally Upper-Left <-> Lower-Right

Notes:
-We assume a palindrome is non-trival (size > 1)
-For time complexity: N = rows*cols = Total items in matrix
-We also allow text to wrap around in prior directions as well. See FindAllPalindromes.java for more info.


Time Complexity:

We first get all all the rows of the matrix as a LinkedList of Strings which takes O(Rows) Time
We then get all the columns which takes (Rows*Cols) or O(N) where N is the amount of elements in the matrix.
Getting both Diagonals also take O(N) time.

So processing the matrix takes a total of O(N) time complexity.

To find all the palindromes in a string takes O(n) where n is the string size. SEE FindAllPalindromes.java for more info

So if we feed all our strings into the findAll method the total time to find all our palindromes is just O(N).

The total time complexity is O(N) or O(rows*cols) for the program which is pretty quick!

USAGE:
This program will randomly generate a char[][] matrix and run the algorithm.

To run the default random samples- run without any arguments:

java PalindromeMatrix
-This will output a bunch of samples. See bottom of page for sample output!

To run a specific size:

java PalindromeMatrix rows cols
i.e.
java PalindromeMatrix 10 15

To run the benchmark:

java PalindromeMatrix benchmark

This will output a table like this:

Size: 100x10 Elements: 1000 Time: 0.5720167 milliseconds
Palindromes: 182

Size: 100x100 Elements: 10000 Time: 1.4009133 milliseconds
Palindromes: 747

Size: 1000x100 Elements: 100000 Time: 9.3908377 milliseconds
Palindromes: 1759

Size: 1000x1000 Elements: 1000000 Time: 23.102347 milliseconds
Palindromes: 6934

Size: 10000x1000 Elements: 10000000 Time: 203.9491004 milliseconds
Palindromes: 22914

Size: 10000x10000 Elements: 100000000 Time: 2093.0445313 milliseconds
Palindromes: 55958

FOR REFERENCE: 2014 Macbook Pro 2.8ghz

The bottleneck for waiting is the random generation of the char[][] matrix, but the times only reflect for the algorithm to run, not the time for generation.

SEE FindAllPalindromes.java for more info!
*/

import java.util.LinkedList;
import java.lang.StringBuilder;
import java.util.HashSet;
import java.util.Arrays;

public class PalindromeMatrix {
    char[][] matrix;

    public PalindromeMatrix(char[][] input) {
        this.matrix = input;
    }

    public LinkedList<String> getDownDiagonals() { //Upper-Left <-> Lower-Right 
        int rows = this.matrix.length;
        int cols = this.matrix[0].length;
        LinkedList<String> output = new LinkedList<String>();

        for (int k = 0; k < cols; k++) {
            StringBuilder buffer = new StringBuilder();
            for (int i = 0; i < rows && (i+k) < cols; i++) {
                buffer.append(this.matrix[i][i+k]);
            }
            output.add(buffer.toString());
        }

        for (int k = 1; k < rows; k++) {
            StringBuilder buffer = new StringBuilder();

            for (int i = 0; (i+k) < rows && i < cols; i++) {
                buffer.append(this.matrix[i+k][i]);
            }
            output.add(buffer.toString());
        }
        return output;
    }

    public LinkedList<String> getUpDiagonals() { // Lower-Left <-> Upper-Right
        int rows = this.matrix.length;
        int cols = this.matrix[0].length;
        LinkedList<String> output = new LinkedList<String>();

        for (int k = 0; k < cols; k++) {
            StringBuilder buffer = new StringBuilder();
            for (int i = 0; i < rows && (k-i) >= 0; i++) {
                buffer.append(this.matrix[i][(k-i)]);
            }
            output.add(buffer.toString());
        }

        for (int k = 1; k < rows; k++) {
            StringBuilder buffer = new StringBuilder();

            for (int i = 0; (i+k) < rows && cols-1-i >= 0; i++) {
                buffer.append(this.matrix[i+k][cols-1-i]);
            }
            output.add(buffer.toString());
        }
        return output;
    }

    public LinkedList<String> getRows() { 
        int rows = this.matrix.length;
        int cols = this.matrix[0].length;
        LinkedList<String> output = new LinkedList<String>();

        for (int i = 0; i < rows; i++) {
            output.add(new String(this.matrix[i]));
        }
        return output;
    }

    public LinkedList<String> getCols() { 
        int rows = this.matrix.length;
        int cols = this.matrix[0].length;
        LinkedList<String> output = new LinkedList<String>();

        for (int i = 0; i < cols; i++) {
            StringBuilder buffer = new StringBuilder();
            for (int j = 0; j < rows; j++) {
                buffer.append(this.matrix[j][i]);
            }
            output.add(buffer.toString());
        }
        return output;
    }

    public static HashSet<String> getAllPalindromes(char[][] input) {
        PalindromeMatrix mat = new PalindromeMatrix(input);
        HashSet<String> palindromes = new HashSet<String>(); //For maintaining the palindromes

        LinkedList<String> seq = mat.getCols();
        for (String s : seq) palindromes.addAll(FindAllPalindromes.findAll(s));

        seq = mat.getRows();
        for (String s : seq) palindromes.addAll(FindAllPalindromes.findAll(s));

        seq = mat.getDownDiagonals();
        for (String s : seq) palindromes.addAll(FindAllPalindromes.findAll(s));

        seq = mat.getUpDiagonals();
        for (String s : seq) palindromes.addAll(FindAllPalindromes.findAll(s));

        return palindromes;
    }

    public static char[][] generateRandomMatrix(int rows, int cols) {
        char[][] out = new char[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                char letter = (char) (65 + (int)(Math.random()*26)); //Generate random Capital letter
                out[i][j] = letter;
            }
        }
        return out;
    }

    public static void palindromesRandomMatrix(int rows, int cols) {
        char[][] mat = generateRandomMatrix(rows,cols);
        long startTime = System.nanoTime(); 
        HashSet<String> out = getAllPalindromes(mat);
        long stopTime = System.nanoTime();
        System.out.println(rows + "x" + cols);
        for (char[] chArr: mat) {
            System.out.println(Arrays.toString(chArr));
        }
        System.out.println("\nelements: "+ rows * cols + "\nTime: " + (stopTime-startTime)/10e6 +" milliseconds");
        System.out.println("\nPalindromes:\n" + out + "\n");
    }

    public static void runSamples() {
        palindromesRandomMatrix(5,5);
        palindromesRandomMatrix(10,10);
        palindromesRandomMatrix(15,10);
        palindromesRandomMatrix(20,20);
        palindromesRandomMatrix(15,25);
    }

    public static void benchMark() {
        System.out.println();
        for (int i = 100; i < 100000; i *= 10 ) {
            for (int j = 0; j < 2; j++) {
                int size;
                char[][] toUse;
                if (j == 0) {
                    toUse = generateRandomMatrix(i,i/10);
                    size = i*i/10;
                } else {
                    toUse = generateRandomMatrix(i,i);
                    size = i*i;
                }
                long startTime = System.nanoTime(); 
                HashSet<String> out = getAllPalindromes(toUse);
                long stopTime = System.nanoTime();

                System.out.println("Size: " + i + "x" + size/i + " Elements: " + size + " Time: " + (stopTime-startTime)/10e6 + " milliseconds\nPalindromes: " + out.size());
                System.out.println();
            }
        }
    }

    public static void main(String[] args) {
        if (args.length > 2) {
            System.out.println("Invalid Input");
            return;
        } 
        if (args.length == 0) {
            runSamples();
        } else if (args.length == 2) {
            palindromesRandomMatrix(Integer.parseInt(args[0]),Integer.parseInt(args[1]));
        } else if (args[0].equals("benchmark")) {
            benchMark();
        }
    }
}


/*
EXAMPLE OUTPUT FOR NO ARGUMENT MAIN: --- runSamples()

5x5
[S, F, X, Q, B]
[L, K, G, W, S]
[Y, T, N, Q, D]
[Q, G, T, A, A]
[O, W, F, L, K]

elements: 25
Time: 0.1044393 milliseconds

Palindromes:
[AA, TT, LL, QQ, KSK, LTTL, TLLT, QWQ]

10x10
[M, V, K, M, J, J, D, C, O, M]
[J, J, Y, A, U, G, E, X, S, L]
[C, P, H, V, Z, F, M, Q, H, A]
[T, X, Y, C, J, S, I, C, L, D]
[G, N, R, E, J, Z, C, Z, W, U]
[X, R, V, G, B, V, B, W, V, S]
[D, U, D, J, Z, B, H, W, P, Q]
[D, E, R, L, V, Y, X, C, R, N]
[K, M, A, L, W, T, N, K, O, R]
[M, O, C, Y, S, O, M, L, I, L]

elements: 100
Time: 0.0974268 milliseconds

Palindromes:
[DD, MM, LL, JJ, WW, RR, BB, CC, VJV, LML, YHY, MLILM, MMM, LIL, BVB, ZCZ, OMLILMO, DUD, VYV, OIO, TMT, YWY]

15x10
[Q, R, C, H, Y, H, U, V, B, T]
[I, N, C, C, P, L, A, C, S, G]
[E, I, W, L, X, A, N, E, E, U]
[T, A, O, E, W, J, H, Y, C, Y]
[L, L, I, L, F, W, G, R, W, D]
[I, W, Y, V, U, W, T, U, Q, G]
[B, J, W, K, K, E, O, O, T, P]
[F, E, E, I, P, Q, T, X, Z, U]
[V, L, X, Y, B, X, L, C, D, K]
[N, Q, I, F, S, X, E, N, O, W]
[Y, I, Z, C, Z, M, J, J, H, K]
[R, N, T, J, O, F, W, Y, L, K]
[T, Z, V, Y, J, L, M, D, T, E]
[G, B, I, Y, H, Y, D, R, X, K]
[I, J, Y, L, Q, Y, X, N, E, W]

elements: 150
Time: 0.0913927 milliseconds

Palindromes:
[XX, LL, JJ, YQY, DD, TET, YMY, EBE, YCY, JFJ, ZCZ, LHL, EUE, HYH, UTU, PHP, KWK, KK, IQI, CC, YY, WW, EE, OO, II, AA, XLX, LOL, YHY, LIL, KEK, LEL, FZF, TOT]

20x20
[J, I, C, O, E, M, A, Z, V, Q, K, K, X, T, I, J, I, J, B, E]
[N, Q, M, B, I, Z, M, W, X, Z, P, M, E, C, L, C, L, U, G, F]
[Q, F, G, P, V, Y, F, U, Q, U, U, D, X, E, L, C, L, N, P, W]
[M, N, M, D, K, Q, H, R, Z, Z, T, H, V, C, E, W, E, N, W, N]
[P, N, B, U, R, J, Q, R, O, D, B, X, A, S, R, G, V, L, R, G]
[P, V, I, H, X, R, T, N, G, F, U, R, K, X, F, K, D, H, O, Y]
[N, Z, J, I, C, U, A, P, L, Z, C, G, D, M, V, X, V, I, Y, E]
[U, S, Q, Q, W, Q, Z, A, H, R, E, V, W, P, K, L, X, S, Q, K]
[M, F, O, X, X, H, Y, I, J, N, E, M, B, B, U, E, U, O, P, R]
[H, N, R, A, Z, B, T, F, E, Z, V, X, W, I, R, G, W, V, A, F]
[M, D, T, Y, K, H, E, I, I, O, G, Y, R, O, F, B, R, H, A, H]
[E, G, X, I, V, W, K, Z, N, I, Y, E, Y, G, H, E, W, L, K, B]
[Z, Q, V, N, O, O, G, X, G, A, Q, G, I, H, R, Q, V, Z, Q, B]
[R, B, P, F, J, S, T, D, H, K, D, S, L, N, O, F, F, E, Y, R]
[G, Q, V, C, K, K, P, Z, N, B, K, X, P, J, H, S, K, M, K, U]
[K, R, S, E, U, D, K, C, W, R, V, O, Z, N, T, B, U, U, W, F]
[J, J, Y, Q, O, S, U, N, G, X, F, B, M, R, C, R, F, R, L, Z]
[S, K, T, A, B, J, A, M, F, K, D, P, F, H, K, R, L, W, A, C]
[B, K, I, W, W, X, V, P, P, S, H, A, E, B, A, J, F, E, K, E]
[N, A, Y, Z, Z, K, N, M, N, R, U, L, I, D, C, M, E, W, N, S]

elements: 400
Time: 0.1253821 milliseconds

Palindromes:
[PP, LL, XX, HH, XEX, ZIZ, MPM, HBH, MHM, NJN, UXU, WBRBW, AIA, XVX, RFR, EE, CEC, AA, UU, QQ, II, YY, MM, JSJ, NWN, EEE, GIG, KMK, NSN, LCL, WYW, EVE, DTD, UQU, DPD, RGR, ENE, NN, RR, BB, ZZ, FF, JJ, RCR, VKV, FLF, UEU, WEW, IJI, GFG, YEY, IFI, MNM, QRQ, EWE, VXV, MFM, ESE, BIB, VPV, WRW, SJS, EKE, FMF, KK, CC, OO, WW, QBQ, MWM, JMJ, UMMU, VDV, JIJ, QWQ, MOM, WBW, ZHZ, NMN, HAH, QSQ, BVB, MGM, RQR, BRB, GXG, CLC, ZUZ, QCQ, RER, BFB]

15x25
[P, O, I, V, P, N, V, I, G, Y, T, W, T, H, E, Y, C, H, S, S, W, R, Y, R, R]
[I, Z, I, I, P, K, O, X, D, N, M, S, P, B, E, O, D, F, A, P, N, Q, J, R, C]
[G, O, O, J, L, D, O, M, Z, Q, C, I, G, C, R, R, C, B, G, O, Z, U, Q, B, V]
[T, Y, H, I, G, D, S, W, D, M, M, J, Y, X, E, G, Q, V, F, M, P, K, A, A, H]
[K, F, S, I, T, P, B, B, P, K, K, R, H, A, O, M, D, N, C, J, Y, Q, V, C, M]
[Q, M, V, X, A, A, M, H, Y, K, R, F, F, U, M, P, L, Y, G, F, M, L, O, X, Q]
[B, T, Q, D, U, S, U, O, I, M, M, E, N, V, Y, K, C, R, P, G, Q, R, N, W, M]
[I, V, Y, H, O, J, Q, I, S, U, D, P, D, S, C, O, T, N, P, K, P, Y, Z, N, F]
[I, H, I, N, Z, E, Z, F, L, N, V, K, C, K, V, H, C, B, T, Z, F, M, G, I, H]
[E, F, C, E, Y, J, R, M, Z, M, I, N, Q, J, T, S, F, P, Q, K, L, R, Q, X, A]
[J, J, H, E, P, L, T, Y, M, M, B, K, R, T, Y, Z, J, I, U, T, R, Z, R, D, Y]
[O, R, R, M, Y, J, A, O, L, A, H, G, M, D, X, O, X, M, E, N, I, P, G, M, N]
[V, Q, Y, M, D, M, K, J, P, O, N, K, B, I, S, D, I, D, K, K, C, S, E, A, R]
[Y, Z, U, A, L, R, M, S, V, G, D, L, N, C, V, B, K, L, P, L, Y, W, Q, J, H]
[V, V, T, C, B, R, V, T, F, I, E, R, A, V, H, J, X, F, F, A, O, A, W, Y, V]

elements: 375
Time: 0.0782096 milliseconds

Palindromes:
[PP, DD, TT, CDC, VAV, IHI, RZR, CYC, ZEZ, EYE, AQA, MDM, VVV, BGB, II, EE, MM, AA, QQ, YY, YPY, MKKM, KQK, MQM, AVA, IHIHI, TWT, CVC, PKP, ANA, ERE, IZI, DPD, KZK, RR, VV, BB, FF, JJ, XOX, NN, MZM, GNG, OZO, FHF, VKCKV, IJI, JLJ, KNK, LPL, SVS, AOA, DID, OO, KK, SS, WW, GG, THT, PBBP, YNY, HIH, ZLZ, RYR, JEJ, KGK, DZD, VYV, KCK, CTC, MCM, USU, RMR, CRRC]

*/
