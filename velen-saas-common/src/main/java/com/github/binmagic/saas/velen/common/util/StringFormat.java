package com.github.binmagic.saas.velen.common.util;

import cn.hutool.core.bean.BeanUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class StringFormat {

    private static Pattern pattern = Pattern.compile("\\$\\{[\\w\\.]*\\}");

    public StringFormat() {
    }

    public static String format(String value, Map<String, Object> params) {

        Matcher matcher = pattern.matcher(value);

        String tmp = value;
        while (matcher.find()) {
            String key = matcher.group();
            String entryKey = key.replace("${app.", "")
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

    /*public static void main(String[] args){
        BeanUtil.beanToMap()

        Map<String, Object> params = new HashMap();
        params.put("id", "123213");

        App app = new App();
        app.setId("121231");
        Map<String, Object> map = BeanUtil.beanToMap(app);
        for (Map.Entry<String,Object> entry: map.entrySet()){
            entry.setValue("123123");
        }
        String value = "${app.id}";

        System.err.println(format(value, map));
    }

    public static class App {
        String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }*/

}
