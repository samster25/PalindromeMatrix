/* README
Author Sammy Sidhu

This algorithm is a dynamic programming algorithm that returns all palindromes from an inputed string and runs in O(n) where n is the size of the string.

I also only consider nontrival palindromes (size > 1).

The algorithm also considers palindromes that wrap around such "racecar": 

System.out.println(findall("racecar"));
[cec, rr, aceca, carrac, racecar, arra]

The algorithm is based off the Manacher algorithm which finds the longest substring in linear time using a suffix array. 

This also uses a suffix array but in 2D for odd and even since we care about print ALL palindromes.
Once we populate our Memo table we then start from the largest radius of each palindrome and decrement the size until its atleast 2 characters long.


PROOF OF LINEARITY:

Manacher algorithm is linear in nature but I wanted to prove that print ALL palindromes is still linear. For this you can run the main of this class.

I randomly generate giant strings, but the time here is for just running the algorithm. This does check for wrap around!

Try Running the Main!

Elements: 10000 Time: 0.6132994 milliseconds Palindromes: 330
Samples: PP JSJSJ UGU VIV IPI ...

Elements: 100000 Time: 2.3919499 milliseconds Palindromes: 980
Samples: UGU SCS YOY WKW JRJ ...

Elements: 1000000 Time: 6.4535835 milliseconds Palindromes: 2821
Samples: WKW SCS FJF JRJ LCCL ...

Elements: 10000000 Time: 46.0265508 milliseconds Palindromes: 12519
Samples: BEHEB BBB PIAIP TXVXT ARTRA ...

FOR REFERENCE: 2014 Macbook Pro 2.8ghz
*/

import java.util.Arrays;
import java.util.HashSet;
import java.lang.StringBuilder;

public class FindAllPalindromes {

    public static HashSet<String> findAll(String input) {
        int len = 2 * input.length(); //allow string to wrap around
        int[][] table = new int[2][len+1]; // Construct suffix array for even and odd indices
        HashSet<String> palindromes = new HashSet<String>(); //to store all the palindromes and return

        for (int j = 0; j < 2; j++) { //check for odd and even indices
            int i = 1;
            int radius = 0;
            String currSt = "$" + input + input + "@"; //create bounds on palindrome and allow the string to wrap around
            table[j][0] = 0; 

            while (i <= len) {
                while (currSt.charAt(i - radius - 1) == currSt.charAt(i + j + radius)) { // if the left and right of the center are equal expand radius
                    radius += 1;
                }
                table[j][i] = radius; // cache the radius
                int k = 1;
                while ((table[j][i-k] != radius - k) && (k < radius)) {
                    if (table[j][i-k] < radius-k) {
                        table[j][i+k] = table[j][i-k];
                    } else {
                        table[j][i+k] = radius-k;
                    }
                    k += 1;
                }
                if (radius - k >= 0) {
                    radius -= k;
                } else {
                    radius = 0;
                }
                i += k;
            }
        }

        String currSt = input + input; //get rid of bounds
        for (int i = 1; i <= len; i++) {
            for (int j = 0; j < 2; j++) { // odd / even
                for (int radius = table[j][i]; radius > 0; radius--) { //start at largest radius and dec down to include all palindromes
                    if (2*radius+j <= len/2) { //only allow palindromes that are long as the orignal string
                        palindromes.add(currSt.substring(i-radius-1,(i-radius-1)+2*radius+j)); //add palindrome to set
                    }
                }
            }
        }
        return palindromes;
    }
    
    public static void main(String[] args) {
        System.out.println();
        for (int j = 10000; j < 100000000; j *= 10) {
            StringBuilder test = new StringBuilder();

            // Build random string of characters
            for (int i =0; i < j; i++) {
                char letter = (char) (65 + (int)(Math.random()*26)); //generates random char between {A... Z}
                test.append(letter);
            }

            String in = test.toString(); //convert stringbuilder to string

            long startTime = System.nanoTime(); 
            HashSet<String> output = findAll(in);
            long stopTime = System.nanoTime();

            System.out.println("Elements: " + j + " Time: " + (stopTime-startTime)/10e6 + " milliseconds Palindromes: " + output.size());

            System.out.print("Samples: ");

            int count = 0;
            for (String samp : output) { //iterate first 5 samples
                if (count == 5) {
                    break;
                }
                count++;
                System.out.print(samp+ " "); 
            }
            System.out.println("...\n");
        }
    }
}