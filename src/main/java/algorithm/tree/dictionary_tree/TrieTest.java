package algorithm.tree.dictionary_tree;


import org.junit.Test;

/**
 * Created by Niki on 2020/8/6 12:45
 * @see{@link:https://blog.csdn.net/Together_CZ/article/details/74078655}
 */
public class TrieTest {
    /**
     * 测试字典树
     *
     * @author chenleixing
     */
    @Test
    public void testTrie(){
        //创建一个字典树(其实可以在创建时指定字典树各节点的大小，大小根据存入字符种类的数量)
        Trie trie=new Trie();
        //测试字符串（当然越庞大越能展现它的优势）
        String[] testStrs=new String[]{"chefsd","chen","hahi","ch","cxing","hahha","ch","my","home","chh"};
        for(String s:testStrs){//向字典树中存入字符串
            trie.insertStr(s);
        }

        //测试是否包含指定前缀的字符串
        boolean isCont=trie.isContainPrefix("ch");
        System.out.println("包含ch数量"+isCont);//输出true

        //测试包含指定前缀的字符串的数量
        int countPrefix=trie.countPrefix("ch");
        System.out.println(countPrefix);//输出3

        //测试包含指定字符串的数量
        int countStr=trie.countStr("ch");
        System.out.println("包含ch数量"+countStr);//输出1


        //测试包含指定前缀的字符串的数量
        int countPre=trie.countPrefix("chee");
        System.out.println(countPre);//输出0

        //测试子节点的数量和树的深度
        int numNode=trie.getNumNode();//为22
        int dept=trie.getDept();//为6
        System.out.println("字典树子节点的数量："+numNode+"  树的深度："+dept);
    }

}