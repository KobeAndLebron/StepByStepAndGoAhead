package lettcode.easy;

/**
 * Created by chenjingshuai on 17-1-11.
 */
public class Number1Bits {

    // you need to treat n as an unsigned value
    int hammingWeight(int n) {
        int numOf1Bits = 0;
        long mask = 1;
        while (mask <= ((long)Math.pow(2, 31))) {
            if ((mask & n) == mask) {
                numOf1Bits++;
            }
            mask <<= 1;
        }
        return numOf1Bits;
    }

    // you need to treat n as an unsigned value
    int hammingWeight1(int n) {
        int numOf1Bits = 0;
        int initialValue = 1;
        while (initialValue != 0) {
            if ((initialValue & n) == initialValue) {
                numOf1Bits++;
            }
            initialValue <<= 1;
        }
        return numOf1Bits;
    }

    // you need to treat n as an unsigned value
    int hammingWeight2(int n) {
        int numOf1Bits = 0;
        while (n != 0) {
            numOf1Bits++;
            n &= (n - 1);
        }
        return numOf1Bits;
    }
}
