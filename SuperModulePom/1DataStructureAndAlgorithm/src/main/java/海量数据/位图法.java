package 海量数据;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * 利用一个位(比特)数组来存储整数数组, index即代表数的大小.
 *
 * 例: 位数组的大小为5, 则[0, 1, 0, 1, 0]表示[1, 3].
 *
 * 利用Int来实现位图数组: 一个Int包含32位, 可以表示32个整数. 所以大小为n的Int位图数组可以表示 n*32 个整数.
 *
 * 整数一共有2^31, 需要2^31个位来存, (2^31)/8(8个比特等于一个字节)=2^28个字节=1GB/2^2=250MB. 对于无符号位的整数, 需要500MB的内存.
 *  2^10 bit = 1024 bit = 1KB.  2^30 bit = 2^20 kb = 2^10 MB = 1 GB.
 *
 * 位图法可用于: 数据的查找、去重、排序.
 *
 * 当内存不够的时候可以采取分治法, 先Hash, 将大文件分为小文件, 这样可以保证相同的数字肯定在一个文件里, 然后在依次处理小文件, 最后合并结果.
 *
 * 例题:
 *  如何在大量数据中找到不重复/重复的整数?
 *  如何在大量数据中判断一个数是否存在?
 *  如何统计不同电话号码的个数？
 *      Harvest 这道题本质还是 **求解数据重复** 的问题，对于这类问题，一般首先考虑位图法。
 *      对于本题，8 位电话号码可以表示的号码个数为 10^8 个，即 1 亿个。我们每个号码用一个 bit 来表示，则总共需要 1 亿个 bit，内存占用约 100M。
 *
 *  判断一个字符串是否在海量字符串出现过? 布隆过滤器 {@see BloomFilter}
 */
public class 位图法 {

    public static final int BIT_OF_INTEGER = 32;

    public static void main(String[] args) {
        int start = 0;
        //  int end = (int) (Math.pow(2, 32) - 1);
        int end = 100000;
        int numberCount = end - start + 1;

        int[] array = getRandomArray(10, start, end);
        int[] bitArray = getBitArray(numberCount, array);

        System.out.print("Array after reduplicating and sorting: " + getArrCorrespondingToBitArray(bitArray));

    }

    private static List<Integer> getArrCorrespondingToBitArray(int[] bitArray) {
        List<Integer> integerList = new LinkedList<>();
        for (int i = 0; i < bitArray.length; i++) {
            int bitNumber = bitArray[i];
            integerList.addAll(getArrOfBitNumber(bitNumber, i));
        }
        return integerList;
    }

    private static List<Integer> getArrOfBitNumber(int bitNumber, int i) {
        int basic = i * BIT_OF_INTEGER;

        List<Integer> integerList = new LinkedList<>();
        for (int j = 0; j < 31; j++) {
            int number = 1 << j;
            if ((bitNumber & number) != 0) {
                integerList.add(basic + j);
            }
        }

        return integerList;
    }


    public static int[] getBitArray(int numberCount, int[] array) {
        int[] bitArray = new int[numberCount / BIT_OF_INTEGER + 1];

        for (int number : array) {
            int bitIndex = number / BIT_OF_INTEGER;
            // bitMap[index] = (number % BIT_OF_INTEGER) | bitMap[index]; 错误.
            // bitMap[bitIndex] = (1 << (number % BIT_OF_INTEGER)) | bitMap[bitIndex];
            // Harvest: 等价于上一行的代码, 因为当n为2的x次方时, m % n = m & (n -1), 位运算效率相比于算术运算, 效率很高.
            bitArray[bitIndex] = (1 << (number & (BIT_OF_INTEGER - 1))) | bitArray[bitIndex];
        }

        return bitArray;
    }


    /**
     * 工具类: 获取包含随机数的数组.
     *
     * @param size 数组大小.
     * @param min  数组随机数中的最小值.
     * @param max  数组随机数中的最大值.
     * @return
     */
    public static int[] getRandomArray(int size, int min, int max) {
        int[] randomArr = new int[size];
        System.out.println("数组大小：" + size);

        Random r = new Random();
        for (int i = 0; i < size; i++) {
            randomArr[i] = r.nextInt(max - min + 1) + min;
        }

        System.out.println("Original Random Array: " + Arrays.toString(randomArr));
        return randomArr;
    }
}
