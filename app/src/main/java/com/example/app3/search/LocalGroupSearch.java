package com.example.app3.search;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/10/17.
 */

public class LocalGroupSearch {
    /**
     * 按群号-群名拼音搜索
     *
     * @param str
     */
    public static ArrayList<Group> searchGroup(CharSequence str,
                                               ArrayList<Group> allContacts) {
        ArrayList<Group> groupList = new ArrayList<Group>();
        // 如果搜索条件以0 1 +开头则按号码搜索
        if (str.toString().startsWith("0") || str.toString().startsWith("1")
                || str.toString().startsWith("+")) {
            for (Group group : allContacts) {
                if (getGroupName(group) != null
                        && group.getFunction_id() != null) {
                    if ((group.getFunction_id() + "").contains(str)
                            || group.getFunction_name().contains(str)) {
                        groupList.add(group);
                    }
                }
            }
            return groupList;
        }
        CharacterParser finder = CharacterParser.getInstance();

        String result = "";
        for (Group group : allContacts) {
            // 先将输入的字符串转换为拼音
            finder.setResource(str.toString());
            result = finder.getSpelling();
            if (contains(group, result)) {
                groupList.add(group);
            } else if ((group.getFunction_id() + "").contains(str)) {
                groupList.add(group);
            }
        }
        return groupList;
    }

    /**
     * 根据拼音搜索
     * <p>
     * 正则表达式
     * 拼音
     * 搜索条件是否大于6个字符
     *
     * @return
     */
    private static boolean contains(Group group, String search) {
        if (TextUtils.isEmpty(group.getFunction_name())
                && TextUtils.isEmpty(group.getFunction_name())) {
            return false;
        }

        boolean flag = false;

        // 简拼匹配,如果输入在字符串长度大于6就不按首字母匹配了
        if (search.length() < 6) {
            String firstLetters = FirstLetterUtil
                    .getFirstLetter(getGroupName(group));
            // 不区分大小写
            Pattern firstLetterMatcher = Pattern.compile(search,
                    Pattern.CASE_INSENSITIVE);
            flag = firstLetterMatcher.matcher(firstLetters).find();
        }

        if (!flag) { // 如果简拼已经找到了，就不使用全拼了
            // 全拼匹配
            CharacterParser finder = CharacterParser.getInstance();
            finder.setResource(getGroupName(group));
            // 不区分大小写
            Pattern pattern2 = Pattern
                    .compile(search, Pattern.CASE_INSENSITIVE);
            Matcher matcher2 = pattern2.matcher(finder.getSpelling());
            flag = matcher2.find();
        }

        return flag;
    }

    private static String getGroupName(Group group) {
        String strName = null;
        if (!TextUtils.isEmpty(group.getFunction_name())) {
            strName = group.getFunction_name();
        } else if (!TextUtils.isEmpty(group.getFunction_name())) {
            strName = group.getFunction_name();
        } else {
            strName = "";
        }

        return strName;
    }

}

