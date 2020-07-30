package algorithm.bitmap;

import java.util.BitSet;

/**
 * 位图的优势：
 * 1. 节约内存， 2. 与、或运算效率高
 * 位图的缺点：
 * 1. 不能直接进行非运算， 2. 稀疏数据时浪费空间， 3. 只能存储布尔类型
 * https://juejin.im/post/5d1c3dc6e51d45595319e396
 * created by Niki on 2018/1/18
 */
public class BitMapDemo {

    public static void byteMove(int n){
        int temp = 1<<(n-1);
        System.out.println(temp);
    }

    public static void main(String[] args) {
        BitSet bitSet = new BitSet();
        bitSet.set(0);

        BitMap bitMap = new BitMap(8);
//        bitMap.setBit(0);
        bitMap.setBit(4);
        bitMap.setBit(7);
        bitMap.setBit(2);
        bitMap.setBit(5);
        bitMap.setBit(3);

        byteMove(7);

//        System.out.println(1<<16);
//        System.out.println(1<<32);
//        System.out.println(1<<64);
//        System.out.println(1<<128);
//        System.out.println(1<<192);
    }


    /**
     * BitMap使用bit来标记元素。一个byte等于8个bit
     */
    static class BitMap{
        private byte[] bytes;
        public BitMap(byte[] bytes) {
            this.bytes = bytes;
        }
        public BitMap(int size) {
            int number = size/8 +1;
            bytes = new byte[number];
        }

        public void setBit(int n){
            if(n<=0){
                return;
            }
            int index = -1;
            int offset = -1;
            if (0 == n%8){
                index = n/8 -1;
                offset = 7;
            }else{
                index = n / 8;
                offset = n%8 -1;
            }
            switch (offset){
                case 0:
                    bytes[index] = (byte)(bytes[index]|0x01);
                    break;
                case 1:
                    bytes[index] = (byte)(bytes[index]|0x02);
                    break;
                case 2:
                    bytes[index] = (byte)(bytes[index]|0x04);
                    break;
                case 3:
                    bytes[index] = (byte)(bytes[index]|0x08);
                    break;
                case 4:
                    bytes[index] = (byte)(bytes[index]|0x10);
                    break;
                case 5:
                    bytes[index] = (byte)(bytes[index]|0x20);
                    break;
                case 6:
                    bytes[index] = (byte)(bytes[index]|0x40);
                    break;
                case 7:
                    bytes[index] = (byte)(bytes[index]|0x80);
                    break;

            }
        }

        public void setBit2(int n){
            int index = n>>3;
            System.out.println(Integer.toBinaryString(n));
            //bytes[index] |= (1L << n)
            int temp = bytes[index];
            temp = temp | (1<<(n-1) ) ;
            bytes[index] = (byte) temp;
        }



        public boolean get(int n){
            if(n<=0){
                return false;
            }
            int index = -1;
            int offset = -1;
            if(0 == n %8){
                index = n/8;
                offset = 7;
            }else{
                index = n / 8;
                offset = n % 8 -1;
            }
            switch (offset) {
                case 0:
                    return (byte)(bytes[index]&0x01)!=0;//2^0
                case 1:
                    return (byte)(bytes[index]&0x02)!=0;
                case 2:
                    return (byte)(bytes[index]&0x04)!=0;
                case 3:
                    return (byte)(bytes[index]&0x08)!=0;
                case 4:
                    return (byte)(bytes[index]&0x10)!=0;
                case 5:
                    return (byte)(bytes[index]&0x20)!=0;
                case 6:
                    return (byte)(bytes[index]&0x40)!=0;
                case 7:
                    return (byte)(bytes[index]&0x80)!=0;
            }
            return false;
        }
    }
}
