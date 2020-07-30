package algorithm.sample;

/**
 * 打印水仙花数 如果一个数是水仙花数，那么该数为n位则他就等于每一位上数的n次方的和
 * Created by Niki on 2018/5/3 13:39
 */
public class NarcissusNumber {
    /**
     * 计算数字的位数
     *
     * @param number
     * @return
     * @author 80002165 @date 2017年4月11日 上午9:11:20
     */
    private static int numbers(int number) {
        int i = 1;
        for (; number != 0; i++) {
            number = number / 10;
            if (number == 0)
                break;
        }
        return i;
    }

    private static void printNarcissus(int begin, int end) {
        int amount = 0;
        for (; begin <= end; begin++) {
            int n = numbers(begin);
            int flagNum = begin;
            int modelNum = 0; // 各个位数
            int sum = 0; // 各位数计算后的和

            while (flagNum != 0) {
                modelNum = flagNum % 10;
                sum += Math.pow(modelNum, n);
                flagNum = flagNum / 10;
            }
            if (sum == begin) {
                amount++;
                System.out.println("我是水仙花数：" + begin);
            }
        }
        if (amount == 0) {
            System.out.println("在" + begin + "到" + end + "之间，没有水仙花数！");
        }
    }

    public static void main(String[] args) {
        NarcissusNumber.printNarcissus(100, 999);
    }
}
