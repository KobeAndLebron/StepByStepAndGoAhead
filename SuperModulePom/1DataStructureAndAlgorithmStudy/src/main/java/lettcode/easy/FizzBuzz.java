package lettcode.easy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Write a program that outputs the string representation of numbers from 1 to n.
 *
 *   But for multiples of three it should output “Fizz” instead of the number
 * and for the multiples of five output “Buzz”. For numbers which are multiples
 * of both three and five output “FizzBuzz”.
 */
public class FizzBuzz {
    public List<String> fizzBuzz(int n) {
        // avoid dynamic extend.
        List<String> list = Collections.emptyList();
        if (n >= 1) {
            list = new ArrayList<>(n);
            for(int i = 1; i <=n; i++) {
                list.add((i % 3 == 0) ? ((i % 5 == 0) ? "FizzBuzz" : "Fizz")
                        : ((i % 5 == 0) ? "Buzz" : i + ""));
            }
        }
        return list;
    }
}