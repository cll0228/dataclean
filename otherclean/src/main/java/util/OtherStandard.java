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
    public static Commentinfo standardComment(Object obj) throws Exception {
        Commentinfo commentinfo = (Commentinfo) obj;
        if (null != commentinfo) {
            switch (commentinfo.getDatasource()) {
                case "Lvmama":
                    if (commentinfo.getType()==1) {
                        commentinfo = lvmamaScenicCommentinfoParse(commentinfo);
                        break;
                    } else if (commentinfo.getType() == 2) {
                        commentinfo = lvmamaHotelCommentinfoParse(commentinfo);
                        break;
                    } else {
                        commentinfo = lvmamaRouteCommentinfoParse(commentinfo);
                        break;
                    }
                case "Ctrip":
                    if (commentinfo.getType() == 1) {
                        commentinfo = ctripScenicCommentinfoParse(commentinfo);
                        break;
                    } else if (commentinfo.getType() == 2) {
                        commentinfo = ctripHotelCommentinfoParse(commentinfo);
                        break;
                    } else {
                        commentinfo = ctripRouteCommentinfoParse(commentinfo);
                        break;
                    }
                case "Tuniu":
                    if (commentinfo.getType() == 1) {
                        commentinfo = tuniuScenicCommentinfoParse(commentinfo);
                        break;
                    } else if (commentinfo.getType() == 2) {
                        commentinfo = tuniuHotelCommentinfoParse(commentinfo);
                        break;
                    } else {
                        commentinfo = tuniuRouteCommentinfoParse(commentinfo);
                        break;
                    }
                case "Tongcheng":
                    if (commentinfo.getType() == 1) {
                        commentinfo = tongchengScenicCommentinfoParse(commentinfo);
                        break;
                    } else if (commentinfo.getType() == 2) {
                        commentinfo = tongchengHotelCommentinfoParse(commentinfo);
                        break;
                    } else {
                        commentinfo = tongchengRouteCommentinfoParse(commentinfo);
                        break;
                    }
                case "Qunaer":
                    if (commentinfo.getType() == 1) {
                        commentinfo = qunaerScenicCommentinfoParse(commentinfo);
                        break;
                    } else if (commentinfo.getType() == 2) {
                        commentinfo = qunaerHotelCommentinfoParse(commentinfo);
                        break;
                    } else {
                        commentinfo = qunaerRouteCommentinfoParse(commentinfo);
                        break;
                    }

            }
        }
        return commentinfo;
    }

    public static Commentinfo standard(Object obj) throws Exception {
        Commentinfo commoninfo = (Commentinfo) obj;
        if (null != commoninfo) {
            List<TripEntity> tripEntityList = new ArrayList<>();
            switch (commoninfo.getDatasource()) {
                case "Lvmama":
                    commoninfo = lvmamaTripRoute(commoninfo);
                    break;
                case "Ctrip":
                    commoninfo = ctripTripRoute(commoninfo);
                    break;
                case "Tongcheng":
                    commoninfo = praseTongCheng(commoninfo);
                    break;
                case "Tuniu":
                    commoninfo = praseTuniuRoute(commoninfo);
                    break;
                case "Qunaer":
                    commoninfo = qunaerRoutePrase(commoninfo);
                    break;
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
    public static Commentinfo ctripTripRoute(Commentinfo commoninfo) {

        return commoninfo;
    }

    /**
     * @return void
     * @Description 途牛行程解析
     * @author 周相儒
     * @date 2017年12月4日 上午11:09:52
     * @action praseTuniuRoute
     */
    public static Commentinfo praseTuniuRoute(Commentinfo commoninfo) {

        return commoninfo;
    }

    /**
     * @return void
     * @Description 解析Lvmama行程1
     * @author 周陈
     * @date 2017年12月5日 下午4:26:38
     * @action lvmamaTripRoute
     */
    public static Commentinfo lvmamaTripRoute(Commentinfo commoninfo) {
        return commoninfo;


    }

    /**
     * @return void
     * @Description 去哪儿行程解析
     * @author 周陈
     * @date 2017年12月13日 下午4:36:58
     * @action qunaerRoutePrase
     */
    public static Commentinfo qunaerRoutePrase(Commentinfo commoninfo) {

        return commoninfo;
    }

    /**
     * @Description: 同城行程解析
     * @author 周相儒
     * @date 2017年12月6日 上午9:57:13
     */
    public static Commentinfo praseTongCheng(Commentinfo commoninfo) {

        return commoninfo;
    }

    /*
     * @description 驴妈妈景点和门票评论解析
     * @author 谢大磊
     * @Date 2018/3/13 17:05
     * @method ctripScenicCommentinfoParse
     * @return com.holyrobot.common.Commentinfo
     * @param [commentinfo]
     */
    public static Commentinfo lvmamaScenicCommentinfoParse(Commentinfo commentinfo) {
        String content = commentinfo.getContent();
        if (StringUtils.isNotBlank(content)) {
            if (content.startsWith("{")) {
                //解析景点评论
                JSONObject json = new JSONObject(content);
                //评论内容
                content = json.getString("content");
                //评论日期
                String createDate = json.getString("createdTime");
                commentinfo.setContent(content);
                commentinfo.setCommentdate(createDate);
                commentinfo.setDatasource("Lvmama");

            } else {
                //解析门票评论
                Document document = Jsoup.parse(content);
                Elements elements = document.select("body >  div.ufeed-info > p.ufeed-score > span.ufeed-item");
                String score = elements.text();
                // 评论内容
                content = document.select("body > div.ufeed-content").text();
                //评论日期
                String date = document.select("body > div.com-userinfo > p > em").text();
                commentinfo.setScore(score);
                commentinfo.setCommentdate(date);
                commentinfo.setContent(content);
            }
        }
        return commentinfo;
    }

    /*
     * @description 驴妈妈酒店评论解析
     * @author 谢大磊
     * @Date 2018/3/15 10:03
     * @method lvmamaHotelCommentinfoParse
     * @return com.holyrobot.common.Commentinfo
     * @param [commentinfo]
     */
    public static Commentinfo lvmamaHotelCommentinfoParse(Commentinfo commentinfo) {
        String content = commentinfo.getContent();
        if (StringUtils.isNotBlank(content)) {
            Document document = Jsoup.parse(content);
            Elements elements = document.select("body > div > div.ufeed-info > p ");
            String text = elements.text();
            String score = StringUtils.substringBeforeLast(text, "体验");// 评价
            content = StringUtils
                    .substringBefore(document.select("body > div > div.ufeed-content").last().text(), "查看全部");// 评论内容
            String date = document.select("body > div > div.com-userinfo > p > em").last().text();
            commentinfo.setScore(score);
            commentinfo.setCommentdate(date);
            commentinfo.setContent(content);
            commentinfo.setType(2);
        }
        return commentinfo;
    }

    /*
     * @description 驴妈妈行程评论解析
     * @author 谢大磊
     * @Date 2018/3/15 10:07
     * @method lvmamaRouteCommentinfoParse
     * @return com.holyrobot.common.Commentinfo
     * @param [commentinfo]
     */
    public static Commentinfo lvmamaRouteCommentinfoParse(Commentinfo commentinfo) {
        String content = commentinfo.getContent();
        Document document = Jsoup.parse(content);
        //评论内容
        content = document.select("div.comment-li>div.ufeed-content").text();
        //评分
        String score = document.select("div.comment-li>div.ufeed-info>p.ufeed-score>span.ufeed-item").text();
        //封装
        commentinfo.setContent(content);
        commentinfo.setScore(score);
        return commentinfo;
    }

    /*
     * @description 携程景点和门票评论解析
     * @author 谢大磊
     * @Date 2018/3/15 10:27
     * @method ctripScenicCommentinfoParse
     * @return com.holyrobot.common.Commentinfo
     * @param [commentinfo]
     */
    public static Commentinfo ctripScenicCommentinfoParse(Commentinfo commentinfo) {
        String content = commentinfo.getContent();
        if (StringUtils.isNotBlank(content)) {
            if (content.startsWith("{")) {
                //解析门票评论
                JSONObject jsonObject = new JSONObject(content);
                //评论内容
                content = jsonObject.getString("con");
                //评分
                String score = String.valueOf(jsonObject.getInt("grade"));
                commentinfo.setContent(content);
                commentinfo.setScore(score);
            } else {
                //解析景点评论
                Document document = Jsoup.parse(content);
                Elements select = document.select("ul");
                for (Element element : select) {
                    //评分
                    String score = element.child(0).select("span.f_left > span.sblockline").text();
                    //评论内容
                    content = element.child(1).select("span").text();
                    //评论日期
                    String contentDate = element.select("li.from_link>span.f_left > span > em").text();
                    commentinfo.setContent(content);
                    commentinfo.setCommentdate(contentDate);
                    commentinfo.setScore(score);
                }
            }
        }
        return commentinfo;
    }

    /*
     * @description 携程酒店评论解析
     * @author 谢大磊
     * @Date 2018/3/15 10:29
     * @method ctripHotelCommentinfoParse
     * @return com.holyrobot.common.Commentinfo
     * @param [commentinfo]
     */
    public static Commentinfo ctripHotelCommentinfoParse(Commentinfo commentinfo) {
        String content = commentinfo.getContent();
        if (StringUtils.isNotBlank(content)) {
            Document doc = Jsoup.parse(content);
            // 分数
            String score = doc.select("div.comment_main p.comment_title span.small_c").attr("data-value");

            // 内容
            content = doc.select("div.comment_main > div.comment_txt > div.J_commentDetail").text();
            // 评论时间
            String time = doc.select("div.comment_main > div > div.comment_bar > p.comment_bar_info > span.time")
                    .text();
            if (time.contains("发表于")) {
                time = time.replace("发表于", "");
            }
            commentinfo.setContent(content);
            commentinfo.setScore(score);
            commentinfo.setCommentdate(time);
        }
        return commentinfo;
    }

    /*
     * @description 携程行程评论解析
     * @author 谢大磊
     * @Date 2018/3/15 10:31
     * @method ctripRouteCommentinfoParse
     * @return com.holyrobot.common.Commentinfo
     * @param [commentinfo]
     */
    public static Commentinfo ctripRouteCommentinfoParse(Commentinfo commentinfo) {
        String content = commentinfo.getContent();
        if (StringUtils.isNotBlank(content)) {
            JSONObject jsonObject = new JSONObject(content);
            content = jsonObject.getString("CommentContent");
            commentinfo.setContent(content);
        }
        return commentinfo;
    }

    /*
     * @description 途牛景点和门票解析
     * @author 谢大磊
     * @Date 2018/3/15 10:48
     * @method tuniuScenicCommentinfoParse
     * @return com.holyrobot.common.Commentinfo
     * @param [commentinfo]
     */
    public static Commentinfo tuniuScenicCommentinfoParse(Commentinfo commentinfo) {
        String content = commentinfo.getContent();
        if (StringUtils.isNotBlank(content)) {
            if (content.startsWith("{")) {
                //门票评论解析
                JSONObject jsonObject = new JSONObject(content);
                //评论内容
                content = jsonObject.getJSONObject("compTextContent").getString("dataSvalue");
                commentinfo.setContent(content);
            } else {
                Document document = Jsoup.parse(content);
                //评论内容
                content = document.select("div.detail>div.content").text();
                //评论时间
                String commentDate = document.select("div.detail>div.top>p>span.time").text();
                commentinfo.setContent(content);
                commentinfo.setCommentdate(commentDate);
            }
        }
        return commentinfo;
    }

    /*
     * @description 途牛酒店评论解析
     * @author 谢大磊
     * @Date 2018/3/15 10:51
     * @method tuniuHotelCommentinfoParse
     * @return com.holyrobot.common.Commentinfo
     * @param [commentinfo]
     */
    public static Commentinfo tuniuHotelCommentinfoParse(Commentinfo commentinfo) {
        String content = commentinfo.getContent();
        if (StringUtils.isNotBlank(content)) {
            Document document = Jsoup.parse(content);
            content = document.select("div.u5.clearfix>div.a2>div.b2>p.commt_data").text();
            commentinfo.setContent(content);
        }
        return commentinfo;
    }

    /*
     * @description 途牛行程评论解析
     * @author 谢大磊
     * @Date 2018/3/15 10:53
     * @method tuniuRouteCommentinfoParse
     * @return com.holyrobot.common.Commentinfo
     * @param [commentinfo]
     */
    public static Commentinfo tuniuRouteCommentinfoParse(Commentinfo commentinfo) {
        String content = commentinfo.getContent();
        if (StringUtils.isNotBlank(content)) {
            JSONObject jsonObject = new JSONObject(content);
            //评论内容
            content = jsonObject.getString("content");
            commentinfo.setContent(content);
        }
        return commentinfo;
    }

    /*
     * @description 同程景点门票评论解析
     * @author 谢大磊
     * @Date 2018/3/15 11:06
     * @method tongchengScenicCommentinfoParse
     * @return com.holyrobot.common.Commentinfo
     * @param [commentinfo]
     */
    public static Commentinfo tongchengScenicCommentinfoParse(Commentinfo commentinfo) {
        String content = commentinfo.getContent();
        JSONObject jsonObject = null;
        if (StringUtils.isNotBlank(content) && content.startsWith("{")) {
            jsonObject = new JSONObject(content);
            //解析出评论内容
            content = jsonObject.getString("dpContent");
            //解析出评论日期
            String commentDate = jsonObject.getString("dpDate");
            commentinfo.setContent(content);
            commentinfo.setCommentdate(commentDate);
        }
        return commentinfo;
    }

    /*
     * @description 同程酒店评论解析
     * @author 谢大磊
     * @Date 2018/3/15 11:08
     * @method tongchengHotelCommentinfoParse
     * @return com.holyrobot.common.Commentinfo
     * @param [commentinfo]
     */
    public static Commentinfo tongchengHotelCommentinfoParse(Commentinfo commentinfo) {
        return commentinfo;
    }

    /*
     * @description 同程行程评论解析
     * @author 谢大磊
     * @Date 2018/3/15 11:09
     * @method tongchengRouteCommentinfoParse
     * @return com.holyrobot.common.Commentinfo
     * @param [commentinfo]
     */
    public static Commentinfo tongchengRouteCommentinfoParse(Commentinfo commentinfo) {
        return commentinfo;
    }

    /*
     * @description 去哪儿景点和门票评论解析
     * @author 谢大磊
     * @Date 2018/3/15 11:18
     * @method qunaerScenicCommentinfoParse
     * @return com.holyrobot.common.Commentinfo
     * @param [commentinfo]
     */
    public static Commentinfo qunaerScenicCommentinfoParse(Commentinfo commentinfo) {
        String content = commentinfo.getContent();
        if (StringUtils.isNotBlank(content)) {
            if (content.startsWith("{")) {
                //解析门票评论
                JSONObject json = new JSONObject(content);
                //评论内容
                content = json.getString("content");
                //评分
                String score = String.valueOf(json.getInt("score"));
                //评论时间
                String commentDate = json.getString("date");
                //封装
                commentinfo.setCommentdate(commentDate);
                commentinfo.setScore(score);
                commentinfo.setContent(content);
            } else {
                //解析景点评论
                Document document = Jsoup.parse(content);
                content = document.select("div.e_comment_content>p.first").text();
                commentinfo.setContent(content);
            }
        }
        return commentinfo;
    }

    /*
     * @description 去哪儿酒店评论解析
     * @author 谢大磊
     * @Date 2018/3/15 11:20
     * @method qunaerHotelCommentinfoParse
     * @return com.holyrobot.common.Commentinfo
     * @param [commentinfo]
     */
    public static Commentinfo qunaerHotelCommentinfoParse(Commentinfo commentinfo) {
        String content = commentinfo.getContent();
        if (StringUtils.isNotBlank(content)) {
            JSONObject jsonObject = new JSONObject(content);
            content = jsonObject.getString("content");
            commentinfo.setContent(content);
        }
        return commentinfo;

    }

    /*
     * @description 去哪儿行程评论解析
     * @author 谢大磊
     * @Date 2018/3/15 11:21
     * @method qunaerRouteCommentinfoParse
     * @return com.holyrobot.common.Commentinfo
     * @param [commentinfo]
     */
    public static Commentinfo qunaerRouteCommentinfoParse(Commentinfo commentinfo) {
        String content = commentinfo.getContent();
        if (StringUtils.isNotBlank(content)) {
            JSONObject json = new JSONObject(content);
            //评论内容
            content = json.get("content").toString();
            //评论时间
            String createDate = json.getString("createdTime");
            //封装
            commentinfo.setCommentdate(createDate);
            commentinfo.setContent(content);
        }
        return commentinfo;
    }

}
