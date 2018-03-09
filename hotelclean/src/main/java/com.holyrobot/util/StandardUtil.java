package com.holyrobot.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.math.BigDecimal;

public class StandardUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(StandardUtil.class);

    public static Object byte2Obj(Object obj) {
        byte[] bytes = (byte[]) obj;
        Object readObject = null;
        try (ByteArrayInputStream in = new ByteArrayInputStream(bytes);
             ObjectInputStream inputStream = new ObjectInputStream(in)) {
            readObject = inputStream.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return readObject;
    }

    public static Double save2dec(Double f) {
        try {
            BigDecimal b = new BigDecimal(f);
            return b.setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();
        } catch (Throwable t) {
            System.err.println(f);
            t.printStackTrace();
            throw t;
        }
    }

    public static String replaceE2C(String str) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        try {
            return str.replace("-", "-").replace("(", "（").replace(")", "）").replace("[", "【").replace("]", "】");
        } catch (Throwable t) {
            System.err.println(str);
            t.printStackTrace();
            throw t;
        }
    }

    public static String delSpechar(String str) {
        try {
            return str.replaceAll("[^0-9a-zA-Z\u4e00-\u9fa5.，,。？“”]+", "");
        } catch (Throwable t) {
            System.err.println(str);
            t.printStackTrace();
            throw t;
        }
    }

    public static String preStar(String star) {
        try {
            if (StringUtils.isBlank(star)) {
                return "其它";
            }
            if (star.contains(FilterName.HOTEL_STAR)) {
                String num = star.substring(star.indexOf(FilterName.HOTEL_STAR) - 1, star.indexOf(FilterName.HOTEL_STAR));
                return num + FilterName.HOTEL_STAR;
            }
            if (star.contains(FilterName.HOTEL_STAR_TYPE)) {
                String type = star.substring(star.indexOf(FilterName.HOTEL_STAR_TYPE) - 2, star.indexOf(FilterName.HOTEL_STAR_TYPE));
                //途牛类型
                if (type.equals(FilterName.HOTEL_STAR_TUNIU_TYPE1)) {
                    type = FilterName.HOTEL_STAR_TYPE1;
                }
                if (type.equals(FilterName.HOTEL_STAR_TUNIU_TYPE2)) {
                    type = FilterName.HOTEL_STAR_TYPE2;
                }
                return type + FilterName.HOTEL_STAR_TYPE;
            } else {
                return "其它";
            }
        } catch (Throwable t) {
            System.err.println(star);
            t.printStackTrace();
            throw t;
        }
    }

    public static String preGrade(String grade) {
        if (StringUtils.isBlank(grade)) {
            return "0";
        }
        try {
            if (grade.contains("%")) {
                String gradeNum = StringUtils.substringBefore(grade, "%");
                if (gradeNum.contains(".")) {
                    gradeNum = Double.valueOf(gradeNum).intValue() + "";
                }
                return gradeNum;
            }
            if (grade.matches("\\d+(\\.\\d+)?")) {
                Double aDouble = Double.valueOf(grade);
                if (aDouble > 5) {
                    return aDouble.intValue() + "";
                } else if (aDouble <= 5 && aDouble > 0) {
                    Double v = aDouble * 20;
                    return v.intValue() + "";
                } else {
                    return "0";
                }
            }
            return null;
        } catch (Throwable t) {
            System.err.println(grade);
            t.printStackTrace();
            throw t;
        }
    }

    public static void main(String[] args) {
        System.out.println(Double.valueOf("64.7").intValue());
    }


    public static String captureName(String name) {
        try {
            if (StringUtils.isBlank(name)) {
                return null;
            }
            name = name.substring(0, 1).toUpperCase() + name.substring(1);
            return name;
        } catch (Throwable t) {
            System.err.println(name);
            t.printStackTrace();
            throw t;
        }
    }

    /**
     * Byte数组转对象
     *
     * @param bytes
     * @return
     */
    public static Object byteArrayToObject(byte[] bytes) {
        Object obj = null;
        ByteArrayInputStream byteArrayInputStream = null;
        ObjectInputStream objectInputStream = null;
        try {
            byteArrayInputStream = new ByteArrayInputStream(bytes);
            objectInputStream = new ObjectInputStream(byteArrayInputStream);
            obj = objectInputStream.readObject();
        } catch (Exception e) {
            LOGGER.error("byteArrayToObject failed, " + e);
        } finally {
            if (byteArrayInputStream != null) {
                try {
                    byteArrayInputStream.close();
                } catch (IOException e) {
                    LOGGER.error("close byteArrayInputStream failed, " + e);
                }
            }
            if (objectInputStream != null) {
                try {
                    objectInputStream.close();
                } catch (IOException e) {
                    LOGGER.error("close objectInputStream failed, " + e);
                }
            }
        }
        return obj;
    }


    /**
     * 对象转Byte数组
     *
     * @param obj
     * @return
     */
    public static byte[] objectToByteArray(Object obj) {
        byte[] bytes = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(obj);
            objectOutputStream.flush();
            bytes = byteArrayOutputStream.toByteArray();

        } catch (IOException e) {
            LOGGER.error("objectToByteArray failed, " + e);
        } finally {
            if (objectOutputStream != null) {
                try {
                    objectOutputStream.close();
                } catch (IOException e) {
                    LOGGER.error("close objectOutputStream failed, " + e);
                }
            }
            if (byteArrayOutputStream != null) {
                try {
                    byteArrayOutputStream.close();
                } catch (IOException e) {
                    LOGGER.error("close byteArrayOutputStream failed, " + e);
                }
            }

        }
        return bytes;
    }

    public static String preGradeNum(String gradenum) {
        try {
            return Integer.valueOf(gradenum).toString();
        } catch (Exception e) {
            LOGGER.error("评分个数处理失败，返回0,GradeNum = " + gradenum);
            return "0";
        }
    }
}
