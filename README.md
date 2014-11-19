PalindromeMatrix
================

Finds all palindromes in a matrix in linear time


FROM PalindromeMatrix.java
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

FROM FindAllPalindromes.java
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

