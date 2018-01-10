

import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 提供一个简单的模板替换方法 目前只支持变量替换 不支持条件判断等功能
 * Created by Taoxs on 2017/11/8.
 */
public class TemplateUtil {

    private  static final Pattern SMS_DEFAULT_TPL_REGEX=Pattern.compile("\\%\\{(.+?)\\}");//变量定义模式 %{name}

    /**
     * 默认使用变量格式为 %{name}
     * 默认忽略 未命中的变量为 ""
     * @param vars
     * @param template
     * @return
     */
    public static String parse(Map<String,Object> vars,String template){
        return parse(vars,template,null,true);
    }

    /**
     * 默认使用变量格式为 %{name}
     * @param vars
     * @param template
     * @param filter 过滤器
     * @return
     */
    public static String parse(Map<String,Object> vars,String template,Filter filter,boolean isgore){
        return parseTemplate(vars,template,isgore,SMS_DEFAULT_TPL_REGEX,filter);
    }

    /**
     *
     * @param vars
     * @param template
     * @param isgore 是否忽略没有命中的变量替换为""
     * @param pattern
     * @param filter
     * @return
     */
    public static String parseTemplate(Map<String,Object> vars,String template,boolean isgore,Pattern pattern,Filter filter){
        Matcher matcher = pattern.matcher(template);
        StringBuffer buffer=new StringBuffer();
        while (matcher.find()) {
            String name = matcher.group(1);
            String _val= (vars.get(name)==null )?null:Objects.toString(vars.get(name));
            if(filter!=null){
                _val=filter.doFilter(vars,name);
            }
            //如果要忽略 那么就把空数据设置为""
            if(_val==null && isgore){
                _val="";
            }
            if(_val!=null){
                matcher.appendReplacement(buffer, _val);
            }
        }
        matcher.appendTail(buffer);
        return buffer.toString();
    }

    /**
     * 增强替换功能
     * 业务场景: 对指定字段特殊处理一下 比如显示格式 动态控制 等
     * <pre>
     *
     * </pre>
     */
    public static interface Filter{
        /**
         * 返回的值 会覆盖默认替换得值
         * @return
         */
        String doFilter(Map<String, Object> vars, String key);
    }
}
