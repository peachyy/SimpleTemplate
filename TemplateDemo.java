

import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 提供一个简单的模板替换方法 目前只支持变量替换 不支持条件判断等功能
 * Created by Taoxs on 2017/11/8.
 */
public class TemplateDemo {

    public static void main(String[] args) {

        String tpled= TemplateUtil.parse(templateVar,tpl);
    }
}
