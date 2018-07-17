package com.example.app4.tool;

import com.example.app4.util.DefaultUtil;
import com.example.app4.util.GetSchoolListUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/5/16 0016.
 * <p>
 * 获取学校编号
 */

public class GetSchoolNumTool {
    public static String getSchoolNum() {
        String xxbh = "";
        try {
            List<Map<String, String>> maps = new ArrayList<>();
            maps = GetSchoolListUtil.getSchoolData("select * from schools where xxmc='" + DefaultUtil.getDefaultSchool() + "'", maps);
            xxbh = maps.get(0).get("xxbh");
        } catch (Exception ignored) {

        }

        return xxbh;
    }
}
