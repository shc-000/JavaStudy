package com.frog.study.collection;

/**
 * @author haichao.shao (shaohaichao@shanshu.ai)
 * @since 2021/4/1
 */
public class StringCombinations {

    private final StringBuilder output = new StringBuilder();
    private final String inputstring;
    private static int counter = 0;


    public StringCombinations(final String str) {
        inputstring = str;
        System.out.println("The input string  is  : " + inputstring);
    }


    public static void main(String[] args) {
        StringCombinations combobj = new StringCombinations("rahul");
        System.out.println("--");
        System.out.println("All possible combinations are :  ");
        System.out.println("--");
        combobj.combine();
        System.out.println("--");
        System.out.println("Total combinations :: " + counter);
    }


    public void combine() {
        combine(0);
    }


    private void combine(int start) {

        for (int i = start; i < inputstring.length(); ++i) {

            output.append(inputstring.charAt(i));
            System.out.println(output);
            counter++;

            combine(i + 1);

            output.setLength(output.length() - 1);
        }
    }
}
