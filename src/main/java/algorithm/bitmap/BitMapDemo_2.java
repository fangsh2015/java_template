package algorithm.bitmap;

/**
 * 采用映射的方式判断大量数据是否存在。将数据按照int位大小分段，1bit上保存一个数据
 * 例如：100000000个数据，则需要的内存大小为：100000000/8/1024/1024 = 12M
 * 这个的主要思想是根据Integer有32位，所以一个int 可以保存32个数字。每一个byte位保存一个
 * 然后使用一个int数组，第一个数[0]就是0-31区间的数， 第二个数[1]就是32-63区间的数，这个区间通过除法计算 n/32
 * 然后计算数字在区间中32位的哪一位 ,这个下标通过取余计算 n % 32，并且通过位运算转换为对应二进制  1 << (n % 32)
 * 在根据或运算的 n | m 只有其中数字有一位为1，则变为1 例如 1100 | 1110 = 1110。 [n/32] | (1<<(n % 32))
 * 当判断一个数是否存在时则通过与运算  [n/32] & (1<<(n % 32)) 如果大于0则存在
 * 位图的优势：
 * 1. 节约内存， 2. 与、或运算效率高
 * 位图的缺点：
 * 1. 不能直接进行非运算， 2. 稀疏数据时浪费空间， 3. 只能存储布尔类型
 * https://juejin.im/post/5d1c3dc6e51d45595319e396
 *
 * Created by Niki on 2018/4/6 11:24
 */
public class BitMapDemo_2 {
    private static final int INT_BIT = 32;

    //保存数据的Map映射
    private static Integer[] space = null;

    /**
     * 为数字分配存储空间
     * int数组的大小
     * 假设数字最大的为Long.max = 9223372036854775807
     *
     * @param num
     * @return
     */
    private static int distributionSpace(int num) {
        return (1 + num / 32);
    }

    /**
     * 初始化数组
     *
     * @param num
     */
    public static void initSpace(int num) {
        int capacity = distributionSpace(num);
        space = new Integer[capacity];
    }

    /**
     * 插入一个数字
     *
     * @param number
     */
    public static void insert(int number) {
        //计算数字属于第几个分区
        int index = number / 32;

        //计算数字属于某分区下的第几个下标
        int i = number % 32;

        //将数字存入Map中
        space[index] |= (1 << i);
    }

    //判断一个数字是否存在
    public static boolean judgeExist(int number) {
        int index = number / 32;
        int i = number % 32;

        return (space[index] & (1 << i)) > 0;
    }


}
