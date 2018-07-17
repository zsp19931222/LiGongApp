package yh.app.utils;

import java.util.List;

/**
 * Created by lyd10892 on 2016/8/23.
 */

public class CompileUtils {
    public static <D> boolean isEmpty(List<D> list) {
        return list == null || list.isEmpty();
    }
}
