package util;


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
 * 初步解析行程数据
 */
public class RouteStandard {


    public static List<TripEntity> standardRoute(Object obj) throws Exception {
        Routeinfo route = (Routeinfo)obj;
        if(!"".equals(route.getItinerarydetails()) && null!=route.getDestination() && "跟团游".equals(route.getTeamtype())){
            List<TripEntity> tripEntityList = new ArrayList<>();
            switch(route.getDatasource()){
                case "Lvmama" : tripEntityList =lvmamaTripRoute(route) ;break;
                case "Ctrip" : tripEntityList =ctripTripRoute(route) ;break;
                case "Tongcheng" :
                    tripEntityList =praseTongCheng(route)  ;break;
                case "Tuniu" : tripEntityList =praseTuniuRoute(route) ;break;
                case "Qunaer" :
                    tripEntityList =qunaerRoutePrase(route)  ;break;
            }
            return tripEntityList;
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
    public static List<TripEntity> ctripTripRoute(Routeinfo routeinfoDto) {
        List<TripEntity> tripEntities = new ArrayList<>();
        String featureService = routeinfoDto.getItinerarydetails();
        Document doc = Jsoup.parse(featureService);
        Elements select = doc.select("table.simple_journey_table tr");
        for (int i = 1; i < select.size(); i++) {
            TripEntity tripEntity = new TripEntity();
            Elements element = select.get(i).select("td");
            String time = element.get(0).select("strong").text();
            String spot = element.get(2).text();
            if (StringUtils.isEmpty(spot)) {
                spot = element.get(0).select("p").text();
            }
            String hotel = element.get(3).text();
            tripEntity.setDatasource(routeinfoDto.getDatasource());
            tripEntity.setDeparture(routeinfoDto.getDeparture());
            tripEntity.setDestination(routeinfoDto.getDestination());
            tripEntity.setHotel(hotel);
            tripEntity.setRouid(routeinfoDto.getId());
            tripEntity.setUrl(routeinfoDto.getUrl());
            tripEntity.setTime(time);
            tripEntity.setPrice(routeinfoDto.getPrice());
            tripEntity.setSpot(spot);
            tripEntities.add(tripEntity);
        }
        return tripEntities;
    }

    /**
     * @return void
     * @Description 途牛行程解析
     * @author 周相儒
     * @date 2017年12月4日 上午11:09:52
     * @action praseTuniuRoute
     */
    public static List<TripEntity>  praseTuniuRoute(Routeinfo routeinfoDto) {

        List<TripEntity> tripEntities = new ArrayList<>();
        String info = routeinfoDto.getItinerarydetails();
        if (info.contains("detail-journey-table")) {
            Document doc = Jsoup.parse(info);//解析HTML
            Elements elements = doc.select("div.detail-journey-table > table > tbody > tr");//获取HTML的节点
            for (Element element : elements) {//遍历取值
                TripEntity tripEntity = new TripEntity();
                String time = element.select("td").get(0).text().substring(1, 2);
                String spot = element.select("td").get(2).text();
                String hotel = element.select("td").get(3).text();

                tripEntity.setDatasource(routeinfoDto.getDatasource());
                tripEntity.setDeparture(routeinfoDto.getDeparture());
                tripEntity.setDestination(routeinfoDto.getDestination());
                tripEntity.setHotel(hotel);
                tripEntity.setRouid(routeinfoDto.getId());
                tripEntity.setUrl(routeinfoDto.getUrl());
                tripEntity.setTime(time);
                tripEntity.setPrice(routeinfoDto.getPrice());
                tripEntity.setSpot(spot);
                tripEntities.add(tripEntity);
            }
        }
        return tripEntities;
    }

    /**
     * @return void
     * @Description 解析Lvmama行程1
     * @author 周陈
     * @date 2017年12月5日 下午4:26:38
     * @action lvmamaTripRoute
     */
    public static List<TripEntity> lvmamaTripRoute(Routeinfo routeinfoDto) {

        List<TripEntity> tripEntities = new ArrayList<>();

        String featureService = routeinfoDto.getItinerarydetails();
        Document parse = Jsoup.parse(featureService);
        Elements select = parse.select("div.instance_box ul.instance_list li dl.instance_list_text");// 解析景点用的
        Elements elements = parse.select("div.instance_list2_box div.instance-travel-xc-gb "); // 解析酒店用的
        Elements ele_ul = elements.select("ul.instance-travel-xc-gb_ul.instance-travel-xc-gb_ul_old");
        // 处理景点
        int size = 0;
        int count = size;
        for (Element element : select) {
            if (element.text().contains("线路概况")) {
                Elements select2 = element.select("dd p");
                for (Element element2 : select2) {
                    TripEntity tripEntity = new TripEntity();
                    String text = element2.text();
                    String[] split = text.split("】 ");
                    String spot = "";
                    String time = "";
                    if (split.length > 1) {
                        time = split[0].substring(2, 3);
                        spot = split[1];
                    }
                    tripEntity.setSpot(spot);
                    tripEntity.setTime(time);
                    tripEntity.setDatasource(routeinfoDto.getDatasource());
                    tripEntity.setDeparture(routeinfoDto.getDeparture().replace("出发", ""));
                    tripEntity.setDestination(routeinfoDto.getDestination());
                    tripEntity.setPrice(routeinfoDto.getPrice());
                    tripEntity.setRouid(routeinfoDto.getId());
                    tripEntity.setUrl(routeinfoDto.getUrl());
                    tripEntities.add(tripEntity);
                    size++;
                }
            }
        }

        // 处理酒店
        if (count != 0) {
            for (int i = 0;i < size-count; i++) {
                TripEntity tripEntity = tripEntities.get( count + i);
                String hotel = "";
                if (i < ele_ul.size() - 1) {
                    Elements ul_p_hotel = ele_ul.get(i)
                            .select("p.instance-travel-xc-info-title a.instance-travel-xc-hotel-09c");
                    hotel = ul_p_hotel.text();
                    if (StringUtils.isEmpty(hotel)) {
                        ul_p_hotel = ele_ul.get(i).select("p.instance-travel-xc-info-title");
                        hotel = ul_p_hotel.text();
                    }
                    ul_p_hotel = ele_ul.get(i).select("p.instance-travel-xc-info-single");
                    if (StringUtils.isEmpty(hotel) && !StringUtils.isEmpty(ul_p_hotel.text())) {
                        hotel = ul_p_hotel.last().text().replace("入住", "");
                    }
                    if (hotel.contains("指定") || hotel.contains("当地") || hotel.length() == 2) {
                        hotel = "";
                    }
                    if (StringUtils.isEmpty(hotel)) {
                        ul_p_hotel = ele_ul.get(i).select("p.instance-travel-xc-det-630");
                        hotel = ul_p_hotel.text();
                        if (hotel.contains("参考酒店：")) {
                            hotel.substring(hotel.indexOf("参考酒店：") + 5);
                        }
                        String[] split = hotel.split(" ");
                        if (split.length > 1) {
                            hotel = split[0];
                        }
                    }

                }
                tripEntity.setHotel(hotel);
            }
        }

        return tripEntities;



    }

    /**
     * @Description 去哪儿行程解析
     * @author 周陈
     * @date 2017年12月13日 下午4:36:58
     * @action qunaerRoutePrase
     * @return void
     */
    public static List<TripEntity> qunaerRoutePrase(Routeinfo routeinfoDto) {
        List<TripEntity> tripEntities = new ArrayList<>();
        String featureService = routeinfoDto.getItinerarydetails();
        JSONArray jsonArra = new JSONArray(featureService);
        // 状态控制，去掉一个恶心模板
        // https://xclx4.package.qunar.com/user/detail.jsp?id=1366135675&rttp=%E5%9B%BD%E5%86%85%E6%B8%B8&dep=5p2t5bee&arr=5LiJ5Lqa#vid=qb2c_xclx4&func=6Lef5Zui5ri4&pid=1366135675&rid=19894698&vd=5pC656iL5peF6KGM572R77yI5Luj55CG77yJ
        boolean flag = true;
        for (int i = 0; flag && i < jsonArra.length(); i++) {
            TripEntity tripEntity = new TripEntity();
            JSONObject json = (JSONObject) jsonArra.get(i);

            String time = json.get("daySeq").toString();
            String spot = "";
            // 第一天和最后一天不做解析，直接去行程简介
            if (i == 0 || i == jsonArra.length() - 1) {
                if (json.has("dayTitle")) {
                    spot = json.getString("dayTitle");
                }
                if (StringUtils.isEmpty(spot) && json.has("tourTitle")) {
                    spot = json.getString("tourTitle");
                }
            }
            // 景点的多模板解析
            if (StringUtils.isEmpty(spot)) {
                // 模板1
                if (json.has("scheduleTourDetails")) {
                    // System.out.println(json.get("scheduleTourDetails").toString());
                    JSONArray jsonArraySpot = new JSONArray(json.get("scheduleTourDetails").toString());
                    for (int j = 0; j < jsonArraySpot.length(); j++) {
                        JSONObject object = (JSONObject) jsonArraySpot.get(j);
                        if (object.has("tourSpot")) {
                            String tourSpot = object.getString("tourSpot");
                            spot += tourSpot + "—";
                        }
                    }
                }
                // 模板2
                if (!json.has("scheduleTourDetails")) {
                    String description = json.getString("description");
                    Document doc = Jsoup.parse(description);
                    Elements select = doc.select("p");
                    for (Element element : select) {
                        if (element.text().contains("【")) {
                            Elements select2 = element.select("strong");
                            for (Element element2 : select2) {
                                if (element2.text().contains("【")) {
                                    if (element2.text().contains("/")) {
                                        String[] split = element2.text().split("/");
                                        spot += split[0].replace("【", "") + "—";
                                    }else{
                                        String replace = element2.text().replace("【", "");
                                        spot += replace.replace("】", "");
                                    }
                                }
                            }
                        }
                    }
                }
                // 去掉最后的 '-'
                if (StringUtils.isNotEmpty(spot) && spot.charAt(spot.length() - 1) == '—') {
                    spot = spot.substring(0, spot.length() - 1);
                }

                // 模板3
                if (StringUtils.isEmpty(spot)) {
                    spot = json.getString("dayTitle");
                }

            }

            // 酒店的多种模板解析
            String hotel = "";
            if (i != jsonArra.length() - 1) {
                // 模板1
                if (json.has("hotelList")) {
                    JSONArray hotelList = json.getJSONArray("hotelList");
                    JSONObject hoteJson = (JSONObject) hotelList.get(0);
                    if (hoteJson.has("name")) {
                        hotel = hoteJson.getString("name");
                    }
                }
                // 模板2
                if (StringUtils.isEmpty(hotel) && json.has("hotelLevel")) {
                    hotel = json.getString("hotelLevel");
                }
                // 模板3 跳过
                if (StringUtils.isEmpty(hotel)) {
                    flag = false;
                    continue;
                }
            }

            tripEntity.setDatasource(routeinfoDto.getDatasource());
            tripEntity.setDeparture(routeinfoDto.getDeparture());
            tripEntity.setDestination(routeinfoDto.getDestination());
            tripEntity.setHotel(hotel);
            tripEntity.setRouid(routeinfoDto.getId());
            tripEntity.setUrl(routeinfoDto.getUrl());
            tripEntity.setTime(time);
            tripEntity.setPrice(routeinfoDto.getPrice());
            tripEntity.setSpot(spot);
            tripEntities.add(tripEntity);
        }
        return tripEntities;
    }

    /**
     * @Description: 同城行程解析
     * @author 周相儒
     * @date 2017年12月6日 上午9:57:13
     */
    public static  List<TripEntity> praseTongCheng(Routeinfo routeinfoDto) {
        List<TripEntity> tripEntities = new ArrayList<>();
        System.out.println(routeinfoDto.getItinerarydetails());
        Document document = Jsoup.parse(routeinfoDto.getItinerarydetails());
        Elements elements = document.select("body > div.travelModeList_cal.none > table > tbody > tr");

        for (Element element : elements) {
            TripEntity tripEntity = new TripEntity();
            String time = StringUtils.substringBetween(element.select("td").get(0).text(), "第", "天");
            String spot = element.select("td").get(2).text();
            String hotel = element.select("td").get(3).text();

            tripEntity.setDatasource(routeinfoDto.getDatasource());
            tripEntity.setDeparture(routeinfoDto.getDeparture());
            tripEntity.setDestination(routeinfoDto.getDestination());
            tripEntity.setHotel(hotel);
            tripEntity.setRouid(routeinfoDto.getId());
            tripEntity.setUrl(routeinfoDto.getUrl());
            tripEntity.setTime(time);
            tripEntity.setPrice(routeinfoDto.getPrice());
            tripEntity.setSpot(spot);
            tripEntities.add(tripEntity);
        }
        return tripEntities;
    }

}
