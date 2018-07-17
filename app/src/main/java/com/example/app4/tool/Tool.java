package com.example.app4.tool;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2018/4/14 0014.
 */

public class Tool {
    /**
     * 获取十二位随机英文字母字符串
     */
    public static   List<String> charList = null;

    public static String stochasticString() {
        printEnglish();
        String result = "";
        Random random = new Random();

        for (int i = 0; i < 12; i++) {
            int position = random.nextInt(charList.size());
            result += charList.get(position);
        }
        return result;
    }

    /**
     * 获取英文字母
     */
    private static void printEnglish() {
        if (charList == null) {
            charList = new ArrayList<>();
        }
        int firstEnglish, lastEnglish;
        char firstE = 'A', lastE = 'Z';      //获取首字母与末字母的值

        firstEnglish = (int) firstE;
        lastEnglish = (int) lastE;

        for (int i = firstEnglish; i <= lastEnglish; ++i) {
            char uppercase, lowercase;

            uppercase = (char) i;
            lowercase = (char) (i + 32);

            charList.add(uppercase + "");
            charList.add(lowercase + "");
        }

    }
}
