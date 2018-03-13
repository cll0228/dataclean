package com.holyrobot.test;

import com.alibaba.fastjson.JSON;
import com.holyrobot.common.Hotelinfo;
import com.holyrobot.common.ReceiverData;
import com.holyrobot.common.Roombasicinfo;
import com.holyrobot.common.Roomprice;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by cuill on 2018/2/27.
 */
public class TestCase {
    private static String url = "http://192.168.0.149:8081/send";

    private static final Logger logger = LoggerFactory.getLogger(TestCase.class);

    public static void main(String[] args) throws Exception {
//        List<Hotelinfo> hotelDetails = readdatafromDB();
//        for (Hotelinfo detail : hotelDetails) {
//            ReceiverData data = new ReceiverData();
//            data.setType(1);
//            data.setData(detail);
//            String json = JSON.toJSONString(data);
//            System.out.println(json);
//            httpPostWithJson(json, url);
//        }
//        List<Roomprice> roomprices = readdatafromDB1();
//        for (Roomprice detail : roomprices) {
//            ReceiverData data = new ReceiverData();
//            data.setType(2);
//            data.setData(detail);
//            String json = JSON.toJSONString(data);
//            System.out.println(json);
//            httpPostWithJson(json, url);
//        }

        List<Roombasicinfo> roombasicinfos = readdatafromDB2();
        for (Roombasicinfo detail : roombasicinfos) {
            ReceiverData data = new ReceiverData();
            data.setType(3);
            data.setFlag(1);
            data.setData(detail);
            String json = JSON.toJSONString(data);
            System.out.println(json);
            httpPostWithJson(json, url);
        }

    }

    public static List<Hotelinfo> readdatafromDB() throws Exception {
        String URL = "jdbc:mysql://192.168.0.243:3306/algorithm?useUnicode=true&amp;characterEncoding=utf-8";
        String USER = "root";
        String PASSWORD = "admin";
        //1.加载驱动程序
        Class.forName("com.mysql.jdbc.Driver");
        //2.获得数据库链接
        Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
        //3.通过数据库的连接操作数据库，实现增删改查（使用Statement类）
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM `holyrobot_hotelbasicinfo`");
        //4.处理数据库的返回结果(使用ResultSet类)
        List<Hotelinfo> list = new ArrayList<>();
        while (rs.next()) {
            Hotelinfo hotel = new Hotelinfo();
            hotel.setId(rs.getString("ID"));
            hotel.setUrlid(rs.getString("UrlID"));
            hotel.setName(rs.getString("name"));
            hotel.setAddress(rs.getString("address"));
            hotel.setIntroduction(rs.getString("Introduction"));
            hotel.setLongitude(rs.getString("Longitude"));
            hotel.setLatitude(rs.getString("Latitude"));
            hotel.setStar(rs.getString("Star"));
            hotel.setPrice(rs.getString("Price"));
            hotel.setDatasource(rs.getString("DataSource"));
            hotel.setGrade(rs.getString("Grade"));
            hotel.setGradenum(rs.getString("GradeNum"));
            hotel.setBeennum(rs.getString("BeenNum"));
            hotel.setWhantto(rs.getString("WhantTo"));
            hotel.setCreatedate(rs.getDate("CreateDate"));
            hotel.setCreatedate(fm.parse(rs.getString("CreateDate")));
            hotel.setCreator(rs.getString("Creator"));
            hotel.setCreatorid(rs.getString("CreatorID"));
            list.add(hotel);
        }

        //关闭资源
        rs.close();
        st.close();
        conn.close();
        return list;


    }

    static SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public static boolean httpPostWithJson(String param, String url) {
        boolean isSuccess = false;

        HttpPost post = null;
        try {
            HttpClient httpClient = new DefaultHttpClient();

            // 设置超时时间
            httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 2000);
            httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 2000);

            post = new HttpPost(url);
            // 构造消息头
            post.setHeader("Content-type", "application/json; charset=utf-8");
            post.setHeader("Connection", "Close");
            String sessionId = getSessionId();
            post.setHeader("SessionId", sessionId);
//            post.setHeader("appid", appId);

            // 构建消息实体
            StringEntity entity = new StringEntity(param.toString(), Charset.forName("UTF-8"));
            entity.setContentEncoding("UTF-8");
            // 发送Json格式的数据请求
            entity.setContentType("application/json");
            post.setEntity(entity);

            HttpResponse response = httpClient.execute(post);

            // 检验返回码
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                logger.info("请求出错: " + statusCode);
                isSuccess = false;
            } else {
                int retCode = 0;
                String sessendId = "";
                // 返回码中包含retCode及会话Id
                for (Header header : response.getAllHeaders()) {
                    if (header.getName().equals("retcode")) {
                        retCode = Integer.parseInt(header.getValue());
                    }
                    if (header.getName().equals("SessionId")) {
                        sessendId = header.getValue();
                    }
                }

                if (200 != retCode) {
                    // 日志打印
                    logger.info("error return code,  sessionId: " + sessendId + "\t" + "retCode: " + retCode);
                    isSuccess = false;
                } else {
                    isSuccess = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            isSuccess = false;
        } finally {
            if (post != null) {
                try {
                    post.releaseConnection();
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return isSuccess;
    }

    // 构建唯一会话Id
    public static String getSessionId() {
        UUID uuid = UUID.randomUUID();
        String str = uuid.toString();
        return str.substring(0, 8) + str.substring(9, 13) + str.substring(14, 18) + str.substring(19, 23) + str.substring(24);
    }


    public static List<Roomprice> readdatafromDB1() throws Exception {
        String URL = "jdbc:mysql://192.168.0.243:3306/algorithm?useUnicode=true&amp;characterEncoding=utf-8";
        String USER = "root";
        String PASSWORD = "admin";
        //1.加载驱动程序
        Class.forName("com.mysql.jdbc.Driver");
        //2.获得数据库链接
        Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
        //3.通过数据库的连接操作数据库，实现增删改查（使用Statement类）
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM `holyrobot_roomprice`");
        //4.处理数据库的返回结果(使用ResultSet类)
        List<Roomprice> list = new ArrayList<>();
        while (rs.next()) {
            Roomprice hotel = new Roomprice();
            hotel.setId(rs.getString("ID"));
            hotel.setHotelid(rs.getString("HotelID"));
            hotel.setRoomid(rs.getString("RoomID"));
            hotel.setProductname(rs.getString("ProductName"));
            hotel.setPrice(rs.getString("Price"));
            hotel.setDate(rs.getString("Date"));
            hotel.setAvailablenum(rs.getString("AvailableNum"));
            hotel.setIsbooking(rs.getString("Isbooking"));
            hotel.setAppliyby(rs.getString("Appliyby"));
            hotel.setIshasbreakfast(rs.getString("IshasBreakfast"));
            hotel.setIswindow(rs.getString("IsWindow"));
            hotel.setIscancled(rs.getString("IsCancled"));
            hotel.setIswifi(rs.getString("IsWifi"));
            hotel.setPaymethod(rs.getString("Paymethod"));
            hotel.setIsdomesticguest(rs.getString("IsDomesticGuest"));
            hotel.setCreatedate(fm.parse(rs.getString("CreateDate")));
            hotel.setCreator(rs.getString("Creator"));
            hotel.setCreatorid(rs.getString("CreatorID"));
            list.add(hotel);
        }

        //关闭资源
        rs.close();
        st.close();
        conn.close();
        return list;


    }

    public static List<Roombasicinfo> readdatafromDB2() throws Exception {
        String URL = "jdbc:mysql://192.168.0.243:3306/algorithm?useUnicode=true&amp;characterEncoding=utf-8";
        String USER = "root";
        String PASSWORD = "admin";
        //1.加载驱动程序
        Class.forName("com.mysql.jdbc.Driver");
        //2.获得数据库链接
        Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
        //3.通过数据库的连接操作数据库，实现增删改查（使用Statement类）
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM `holyrobot_roombasicinfo`");
        //4.处理数据库的返回结果(使用ResultSet类)
        List<Roombasicinfo> list = new ArrayList<>();
        while (rs.next()) {
            Roombasicinfo hotel = new Roombasicinfo();
            hotel.setId(rs.getString("ID"));
            hotel.setHotelid(rs.getString("HotelID"));
            hotel.setRoomtype(rs.getString("RoomType"));
            hotel.setBedtype(rs.getString("BedType"));
            hotel.setBedcount(rs.getString("BedCount"));
            hotel.setIsaddbed(rs.getString("IsAddBed"));
            hotel.setBedsize(rs.getString("BedSize"));
            hotel.setPrice(rs.getString("Price"));
            hotel.setIswifi(rs.getString("IsWifi"));
            hotel.setFloor(rs.getString("Floor"));
            hotel.setPeoplecount(rs.getString("PeopleCount"));
            hotel.setCreatedate(fm.parse(rs.getString("CreateDate")));
            hotel.setCreator(rs.getString("Creator"));
            hotel.setCreatorid(rs.getString("CreatorID"));
            list.add(hotel);
        }

        //关闭资源
        rs.close();
        st.close();
        conn.close();
        return list;


    }
}
