package com.github.binmagic.saas.velen.common.util;

import cn.hutool.core.bean.BeanUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class StringFormat {

    private static Pattern pattern = Pattern.compile("\\$\\{[\\w]*\\}");

    public StringFormat() {
    }

    public static String format(String value, Map<String, Object> params) {

        Matcher matcher = pattern.matcher(value);

        String tmp = value;
        while (matcher.find()) {
            String key = matcher.group();
            String entryKey = key.replace("${", "")
                    .replace("}", "");
            String replaced = null;
            if (params.containsKey(entryKey)) {
                replaced = params.get(entryKey).toString();
            }
            if (replaced == null) {
                continue;
            }
            tmp = tmp.replace(key, replaced);

        }
        return tmp;
    }

    public static void main(String[] args){
       /* BeanUtil.beanToMap()

        Map<String, String> params = new HashMap();
        params.put("appId", "123213");


        String value = "123123dasdcc${appId}";

        System.err.println(format(value, params));*/
    }

}
