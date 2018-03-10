package util;


import com.holyrobot.common.Commentinfo;
import com.holyrobot.common.Routeinfo;
import com.holyrobot.common.TripEntity;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * 初步解析评论数据
 */
public class OtherStandard {


    public static Commentinfo  standard(Object obj) throws Exception {
        Commentinfo commoninfo = (Commentinfo)obj;
        if(null!=commoninfo){
            List<TripEntity> tripEntityList = new ArrayList<>();
            switch(commoninfo.getDatasource()){
                case "Lvmama" : commoninfo =lvmamaTripRoute(commoninfo) ;break;
                case "Ctrip" : commoninfo =ctripTripRoute(commoninfo) ;break;
                case "Tongcheng" :
                    commoninfo =praseTongCheng(commoninfo)  ;break;
                case "Tuniu" : commoninfo =praseTuniuRoute(commoninfo) ;break;
                case "Qunaer" :
                    commoninfo =qunaerRoutePrase(commoninfo)  ;break;
            }
            return commoninfo;
        }
        return null;
    }

    /**
     * @return void
     * @Description 解析携程行程
     * @author 周陈
     * @date 2017年12月5日 下午4:31:16
     * @action ctripTripRoute
     */
    public static Commentinfo ctripTripRoute( Commentinfo commoninfo) {

        return commoninfo;
    }

    /**
     * @return void
     * @Description 途牛行程解析
     * @author 周相儒
     * @date 2017年12月4日 上午11:09:52
     * @action praseTuniuRoute
     */
    public static Commentinfo praseTuniuRoute( Commentinfo commoninfo) {

        return commoninfo;
    }

    /**
     * @return void
     * @Description 解析Lvmama行程1
     * @author 周陈
     * @date 2017年12月5日 下午4:26:38
     * @action lvmamaTripRoute
     */
    public static Commentinfo lvmamaTripRoute( Commentinfo commoninfo) {


        return commoninfo;



    }

    /**
     * @Description 去哪儿行程解析
     * @author 周陈
     * @date 2017年12月13日 下午4:36:58
     * @action qunaerRoutePrase
     * @return void
     */
    public static Commentinfo qunaerRoutePrase( Commentinfo commoninfo) {

        return commoninfo;
    }

    /**
     * @Description: 同城行程解析
     * @author 周相儒
     * @date 2017年12月6日 上午9:57:13
     */
    public static Commentinfo praseTongCheng( Commentinfo commoninfo) {

        return commoninfo;
    }

}
