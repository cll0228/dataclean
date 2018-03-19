package util;
/*
 * @Author:       谢大磊
 * @ClassName:    DateJsonValueProcessor
 * @Description:  json转换日期工具类
 * @CreateDate:   2018/3/16 17:36
 */

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateJsonValueProcessor implements JsonValueProcessor {
    private String format ;
    public String getFormat(){
        return format;
    }
    public void setFormat(String format){
        this.format=format;
    }
    public DateJsonValueProcessor() {
    }

    public DateJsonValueProcessor(String format) {
        this.format = format;
    }
    @Override
    public Object processArrayValue(Object o, JsonConfig jsonConfig) {
        String[] obj = {};
        if (o instanceof Date[]) {
            SimpleDateFormat sf = new SimpleDateFormat(format);
            Date[] dates = (Date[]) o;
            obj = new String[dates.length];
            for (int i = 0; i < dates.length; i++) {
                obj[i] = sf.format(dates[i]);
            }
        }
        return obj;
    }

    @Override
    public Object processObjectValue(String s, Object o, JsonConfig jsonConfig) {
        if (o instanceof Date) {
            String str = new SimpleDateFormat(format).format((Date) o);
            return str;
        }
        return o.toString();
    }
}
