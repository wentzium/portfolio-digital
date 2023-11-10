package com.mxs.sampleservice.util;

import com.mxs.sampleservice.bo.his.ConvertToHTMLResponse;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *  Created by j.yang on 2019-07-17
 * 
 */
public class XmlUtil {
    /**
     * 解析XML转换为Object
     *
     * @param strXML      xml字符串
     * @param elementName 解析根标签名
     * @param className   类名全路径（包名+类名）
     * @return
     */
    public static <T> List<T> parseObject(String strXML, String elementName, Class<T> className) {
        List<T> list = new ArrayList<T>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new ByteArrayInputStream(strXML.getBytes("utf-8")));
            NodeList nodelist = doc.getElementsByTagName(elementName);
            for (int i = 0; i < nodelist.getLength(); i++) {
                Node node = nodelist.item(i);
                NodeList chlist = node.getChildNodes();
                T bean = className.newInstance();
                Class<?> cls = bean.getClass();
                Method methods[] = cls.getDeclaredMethods();
                Field fields[] = cls.getDeclaredFields();
                for (int j = 0; j < chlist.getLength(); j++) {
                    Node chnode = chlist.item(j);
                    if (chnode instanceof Element) {
                        //System.out.println(chnode.getNodeName()+","+chnode.getTextContent());
                        for (Field field : fields) {
                            String fieldName = field.getName();
                            if (fieldName.equals(chnode.getNodeName())) {
                                String fldtype = field.getType().getSimpleName();
                                String setMethod = pareSetName(fieldName);
                                if (!checkMethod(methods, setMethod)) {
                                    continue;
                                }
                                Object value = chnode.getTextContent();
                                Method method = cls.getMethod(setMethod, field.getType());
                                if (null != value) {
                                    if ("String".equals(fldtype)) {
                                        method.invoke(bean, value.toString());
                                    } else if ("Date".equals(fldtype)) {
                                        Date temp = parseDate(value.toString());
                                        method.invoke(bean, temp);
                                    } else if ("Integer".equals(fldtype) || "int".equals(fldtype)) {
                                        Integer intval = Integer.parseInt(value.toString());
                                        method.invoke(bean, intval);
                                    } else if ("Long".equalsIgnoreCase(fldtype)) {
                                        Long temp = Long.parseLong(value.toString());
                                        method.invoke(bean, temp);
                                    } else if (fldtype.equalsIgnoreCase("Float")) {
                                        Float f = Float.parseFloat(value.toString());
                                        method.invoke(bean, f);
                                    } else if ("Double".equalsIgnoreCase(fldtype)) {
                                        Double temp = Double.parseDouble(value.toString());
                                        method.invoke(bean, temp);
                                    } else if ("Boolean".equalsIgnoreCase(fldtype)) {
                                        Boolean temp = Boolean.parseBoolean(value.toString());
                                        method.invoke(bean, temp);
                                    } else {
                                        System.out.println("not supper type" + fldtype);
                                    }
                                }
                                break;
                            }
                        }
                    }
                }
                list.add(bean);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 拼接某属性set 方法
     *
     * @param fldname
     * @return
     */
    public static String pareSetName(String fldname) {
        if (null == fldname || "".equals(fldname)) {
            return null;
        }
        String pro = "set" + fldname.substring(0, 1).toUpperCase() + fldname.substring(1);
        return pro;
    }

    /**
     * 判断该方法是否存在
     *
     * @param methods
     * @param met
     * @return
     */
    public static boolean checkMethod(Method methods[], String met) {
        if (null != methods) {
            for (Method method : methods) {
                if (met.equals(method.getName())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 格式化string为Date
     *
     * @param datestr
     * @return date
     */
    public static Date parseDate(String datestr) {
        if (null == datestr || "".equals(datestr)) {
            return null;
        }
        try {
            String fmtstr = null;
            if (datestr.indexOf(':') > 0) {
                fmtstr = "yyyy-MM-dd HH:mm:ss";
            } else {
                fmtstr = "yyyy-MM-dd";
            }
            SimpleDateFormat sdf = new SimpleDateFormat(fmtstr);
            return sdf.parse(datestr);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @SuppressWarnings("rawtypes")
    public static void main(String[] args) {
        String xmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?><soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"><soap:Body><ConvertToHTMLResponse xmlns=\"http://www.healthcare.supcon.com/wsmeddoc/\"><ConvertToHTMLResult>&lt;html&gt;\n" +
                "&lt;head&gt;\n" +
                "&lt;title&gt;解放军&lt;/title&gt;\n" +
                "&lt;style type=\"text/css\"&gt;\n" +
                ".table1 {\n" +
                "BORDER-RIGHT: #000000 0px solid; BORDER-TOP: #000000 1px solid; BORDER-LEFT: #000000 1px solid; BORDER-BOTTOM: #000000 0px solid\n" +
                "}\n" +
                ".td1 {\n" +
                "\tBORDER-RIGHT: #000000 1px solid; BORDER-TOP: #000000 0px solid; BORDER-LEFT: #000000 0px solid; BORDER-BOTTOM: #000000 1px solid\n" +
                "}\n" +
                "&lt;/style&gt;\n" +
                "&lt;/head&gt;\n" +
                "&lt;body bgcolor=\"#9099ae\"&gt;\n" +
                "&lt;table width=\"794\" align=\"center\"  border=\"1\" cellspacing=\"0\" bordercolor=black  rules=none bgcolor=\"#FFFFFF\"&gt;\n" +
                "&lt;tr&gt;&lt;td&gt;\n" +
                "&lt;table width=\"606\" align=\"center\"&gt;\n" +
                "&lt;tr&gt;&lt;td&gt;\n" +
                "&lt;table  height=\"42\" align=\"center\"&gt;\n" +
                "&lt;tr&gt;&lt;td&gt;\n" +
                "&lt;/td&gt;&lt;/tr&gt;\n" +
                "&lt;/table&gt;\n" +
                "&lt;p align=\"center\"&gt;&lt;span style=\"font-family: 宋体; font-size: 21px;\"&gt;陆军军医大学第一附属医院（西南医院）&lt;/span&gt;&lt;/p&gt;\n" +
                "&lt;table  height=\"7\" align=\"center\"&gt;\n" +
                "&lt;tr&gt;&lt;td&gt;\n" +
                "&lt;/td&gt;&lt;/tr&gt;\n" +
                "&lt;/table&gt;\n" +
                "&lt;p align=\"center\"&gt;&lt;span style=\"font-family: 宋体; font-size: 28px;font-weight: bold;\"&gt;病&amp;nbsp;&amp;nbsp;&amp;nbsp;历&lt;/span&gt;&lt;/p&gt;\n" +
                "&lt;table width=\"600\" height=\"21\"&gt;\n" +
                "&lt;tr style=\"display:none\"&gt;\n" +
                "&lt;td width=\"40\"&gt;&amp;nbsp;&lt;/td&gt;\n" +
                "&lt;td width=\"67\"&gt;&amp;nbsp;&lt;/td&gt;\n" +
                "&lt;td width=\"40\"&gt;&amp;nbsp;&lt;/td&gt;\n" +
                "&lt;td width=\"72\"&gt;&amp;nbsp;&lt;/td&gt;\n" +
                "&lt;td width=\"56\"&gt;&amp;nbsp;&lt;/td&gt;\n" +
                "&lt;td width=\"40\"&gt;&amp;nbsp;&lt;/td&gt;\n" +
                "&lt;td width=\"48\"&gt;&amp;nbsp;&lt;/td&gt;\n" +
                "&lt;td width=\"52\"&gt;&amp;nbsp;&lt;/td&gt;\n" +
                "&lt;td width=\"64\"&gt;&amp;nbsp;&lt;/td&gt;\n" +
                "&lt;td width=\"44\"&gt;&amp;nbsp;&lt;/td&gt;\n" +
                "&lt;td width=\"77\"&gt;&amp;nbsp;&lt;/td&gt;\n" +
                "&lt;/tr&gt;\n" +
                "&lt;tr&gt;\n" +
                "&lt;td&gt;\n" +
                "&lt;p align=\"right\"&gt;&lt;span style=\"font-family: 宋体; font-size: 12px;\"&gt;姓名&lt;/span&gt;&lt;/p&gt;\n" +
                "&lt;/td&gt;\n" +
                "&lt;td&gt;\n" +
                "&lt;p&gt;&lt;span style=\"font-family: 宋体; font-size: 12px;\"&gt;王先琴&lt;/span&gt;&lt;/p&gt;\n" +
                "&lt;/td&gt;\n" +
                "&lt;td&gt;\n" +
                "&lt;p align=\"right\"&gt;&lt;span style=\"font-family: 宋体; font-size: 12px;\"&gt;病区&lt;/span&gt;&lt;/p&gt;\n" +
                "&lt;/td&gt;\n" +
                "&lt;td colspan=\"2\"&gt;\n" +
                "&lt;p&gt;&lt;span style=\"font-family: 宋体; font-size: 12px;\"&gt;消化内科治疗A区&lt;/span&gt;&lt;/p&gt;\n" +
                "&lt;/td&gt;\n" +
                "&lt;td&gt;\n" +
                "&lt;p align=\"right\"&gt;&lt;span style=\"font-family: 宋体; font-size: 12px;\"&gt;床号&lt;/span&gt;&lt;/p&gt;\n" +
                "&lt;/td&gt;\n" +
                "&lt;td&gt;\n" +
                "&lt;p&gt;&lt;span style=\"font-family: 宋体; font-size: 12px;\"&gt;26&lt;/span&gt;&lt;/p&gt;\n" +
                "&lt;/td&gt;\n" +
                "&lt;td&gt;\n" +
                "&lt;p align=\"right\"&gt;&lt;span style=\"font-family: 宋体; font-size: 12px;\"&gt;住院号&lt;/span&gt;&lt;/p&gt;\n" +
                "&lt;/td&gt;\n" +
                "&lt;td&gt;\n" +
                "&lt;p&gt;&lt;span style=\"font-family: 宋体; font-size: 12px;\"&gt;E52156&lt;/span&gt;&lt;/p&gt;\n" +
                "&lt;/td&gt;\n" +
                "&lt;td&gt;\n" +
                "&lt;p align=\"right\"&gt;&lt;span style=\"font-family: 宋体; font-size: 14px;\"&gt;ID号&lt;/span&gt;&lt;/p&gt;\n" +
                "&lt;/td&gt;\n" +
                "&lt;td&gt;\n" +
                "&lt;p&gt;&lt;span style=\"font-family: 宋体; font-size: 12px;\"&gt;7000119582&lt;/span&gt;&lt;/p&gt;\n" +
                "&lt;/td&gt;\n" +
                "&lt;/tr&gt;\n" +
                "&lt;/table&gt;\n" +
                "&lt;hr&gt;&lt;/hr&gt;\n" +
                "&lt;p&gt;&lt;span style=\"font-family: 宋体; font-size: 14px;\"&gt;2018-12-20，12:36&lt;/span&gt;&lt;span style=\"font-family: 宋体; font-size: 14px;font-weight: bold;\"&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;吉清副教授首次查房记录&lt;/span&gt;&lt;/p&gt;\n" +
                "&lt;p&gt;&lt;span style=\"font-family: 宋体; font-size: 14px;\"&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;患者&lt;/span&gt;&lt;span style=\"font-family: 宋体; font-size: 14px;\"&gt;神志&lt;/span&gt;&lt;span style=\"font-family: 宋体; font-size: 14px;\"&gt;清楚&lt;/span&gt;&lt;span style=\"font-family: 宋体; font-size: 14px;\"&gt;，精神&lt;/span&gt;&lt;span style=\"font-family: 宋体; font-size: 14px;\"&gt;较差&lt;/span&gt;&lt;span style=\"font-family: 宋体; font-size: 14px;\"&gt;，睡眠&lt;/span&gt;&lt;span style=\"font-family: 宋体; font-size: 14px;\"&gt;可&lt;/span&gt;&lt;span style=\"font-family: 宋体; font-size: 14px;\"&gt;，禁食、水&lt;/span&gt;&lt;span style=\"font-family: 宋体; font-size: 14px;\"&gt;，小便&lt;/span&gt;&lt;span style=\"font-family: 宋体; font-size: 14px;\"&gt;正常&lt;/span&gt;&lt;span style=\"font-family: 宋体; font-size: 14px;\"&gt;，卧床，今日凌晨解暗红色糊状便约100g，今晨解褐色糊状便约200g。无呕血、便鲜血，无腹痛、腹胀，诉手术创面轻微疼痛，可睁眼，有轻微异物感，无咳嗽、咳痰。查体：体温:36.4℃，呼吸：20次/分，脉搏&amp;nbsp;90次/分，血压102/75mmHg。双眼肿胀，右侧结膜肿胀，轻度充血，右眼睑闭合不全。头部、左侧踝足部、足背部敷料覆盖，未见明显渗血渗液。辅助检查：(2018-12-19&amp;nbsp;)肝功Ⅱ号+肾功Ⅱ号：白蛋白26.00g/L↓、总胆红素27.73μmol/L↑、肌酐46.47μmol/L↓、葡萄糖7.83mmol/L↑、磷1.50mmol/L↑；血常规+网织+CRP：血红蛋白(HGB)115g/L、血小板数目(PLT)80×10^9/L↓、中性粒细胞数目(Neu#)7.59×10^9/L↑、网织红细胞百分比(Ret%)2.5%↑；TEG凝血全貌分析：凝血综合指数(CI)2.10；(2018-12-20&amp;nbsp;)肝功Ⅱ号+肾功Ⅱ号：白蛋白27.90g/L↓、总胆红素40.56μmol/L↑、肌酐44.67μmol/L↓；降钙素原测定(PCT)0.05ng/ml；：B型利钠肽测定(pro-BNP)42.6pg/ml↓。行超声(腹部)检查提示：1.轻度脂肪肝&lt;/span&gt;&lt;span style=\"font-family: 宋体; font-size: 14px;\"&gt;2.胆囊壁毛糙&lt;/span&gt;&lt;span style=\"font-family: 宋体; font-size: 14px;\"&gt;3.脾、双肾超声未见明显异常&lt;/span&gt;&lt;span style=\"font-family: 宋体; font-size: 14px;\"&gt;4.腹腔内未探及明显积液。&lt;/span&gt;&lt;/p&gt;\n" +
                "&lt;p&gt;&lt;span style=\"font-family: 宋体; font-size: 14px;\"&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;吉清副教仔细查看患者后分析指示：因“机器绞伤致头皮撕脱”于2018-12-15入整形科，并于2018-12-15日急诊在全麻下行头皮再植术+血管吻合术，术后患者因出现静脉危象于2018-12-16日急诊在全麻下行头皮清创+刃厚皮片回植术，昨日患者突然出现消化道出血，并在内镜下止血。患者突然出现大量便血，原因尚不完全明确，结合患者既往有宫颈癌并行放疗病史、本次术后使用抗凝药物及术后应激，现考虑抗凝血药物诱发出血可能性大，亦不能排除放疗、应激引起，由于直肠以上部分肠道情况尚不清楚，亦不能排除同时合并其他部位的出血。入我科后患者未再持续便鲜红血，生命体征正常，复查血红蛋白较前上升，考虑暂无活动性出血，需要警惕再次出血的可能。患者头部为机器绞伤，伤后污染严重，手术时间长，失血量大，免疫力低，虽现在感染指标基本正常，但感染加重降导致及其严重的后果，故可继续予以抗感染治疗，余治疗方案无大的调整。现患者头部敷料覆盖，遵整形科医嘱可术后4-6日打开敷料，观察回植皮片存活情况，并请整形科会诊并协助诊治，调整用药及观察术后恢复情况。遵嘱执行。&lt;/span&gt;&lt;/p&gt;\n" +
                "&lt;p align=\"right\"&gt;&lt;span style=\"font-family: 宋体; font-size: 14px;\"&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;上级医师:&amp;nbsp;&lt;/span&gt;&lt;span style=\"font-family: 宋体; font-size: 14px;\"&gt;吉清&lt;/span&gt;&lt;span style=\"font-family: 宋体; font-size: 14px;\"&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;医师:&lt;/span&gt;&lt;span style=\"font-family: 宋体; font-size: 14px;\"&gt;何金龙&lt;/span&gt;&lt;span style=\"font-family: 宋体; font-size: 14px;\"&gt;&amp;nbsp;&lt;/span&gt;&lt;/p&gt;\n" +
                "&lt;p&gt;&lt;span style=\"font-family: 宋体; font-size: 14px;\"&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&lt;/span&gt;&lt;/p&gt;\n" +
                "&lt;p&gt;&lt;span style=\"font-family: 宋体; font-size: 14px;\"&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;第&amp;nbsp;&lt;/span&gt;1\n" +
                "&lt;span style=\"font-family: 宋体; font-size: 14px;\"&gt;&amp;nbsp;页&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&lt;/span&gt;&lt;/p&gt;\n" +
                "&lt;table  height=\"40\" align=\"center\"&gt;\n" +
                "&lt;tr&gt;&lt;td&gt;\n" +
                "&lt;/td&gt;&lt;/tr&gt;\n" +
                "&lt;/table&gt;\n" +
                "&lt;/td&gt;&lt;/tr&gt;\n" +
                "&lt;/table&gt;\n" +
                "&lt;/td&gt;&lt;/tr&gt;\n" +
                "&lt;/table&gt;\n" +
                "&lt;/body&gt;\n" +
                "&lt;/html&gt;\n" +
                "</ConvertToHTMLResult></ConvertToHTMLResponse></soap:Body></soap:Envelope>";
        String strXML = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
                "<soapenv:Header xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" >" +
                "<ns3:requesthead xmlns:ns3=\"http://pub.webservice.cmp.com\" xmlns:ns2=\"http://pan.prpall.webservice.cmp.com\">" +
                "<ns3:request_type>Q101</ns3:request_type><ns3:uuid>8918211j-12121212</ns3:uuid>" +
                "<ns3:sender>0545</ns3:sender><ns3:server_version>2.0</ns3:server_version>" +
                "<ns3:user>0545</ns3:user><ns3:password>4005973D0841EAC706DC9B8B32858D9D</ns3:password>" +
                "<ns3:areacode>45000000</ns3:areacode><ns3:ChnlNo>yntc</ns3:ChnlNo>" +
                "<ns3:flowintime>2017-05-24 15:00:21</ns3:flowintime></ns3:requesthead>" +
                "</soapenv:Header><soap:Body xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
                "<ns2:GETORIGINPOLICYREQ xmlns:ns2=\"http://pan.prpall.webservice.cmp.com\" xmlns:ns3=\"http://pub.webservice.cmp.com\">" +
                "<ns2:BIZ_ENTITY><Policy><LicenseNo>桂QW1231</LicenseNo>" +
                "<LicenseType>02</LicenseType><ComCode>45010200</ComCode>" +
                "<Resource>0524</Resource><InsuredName>寒冰</InsuredName>" +
                "<IdentifyType>01</IdentifyType>" +
                "<IdentifyNumber>220381199203164520</IdentifyNumber>" +
                "<ChannelCode>000002000001</ChannelCode></Policy></ns2:BIZ_ENTITY>" +
                "</ns2:GETORIGINPOLICYREQ></soap:Body></soapenv:Envelope>";
        List<ConvertToHTMLResponse> list = XmlUtil.parseObject(xmlStr, "ConvertToHTMLResponse", ConvertToHTMLResponse.class);
        StringBuilder xmlStrbuilder = new StringBuilder(list.get(0).getConvertToHTMLResult());
        int index = xmlStrbuilder.indexOf("<head>")+6;
        xmlStrbuilder.insert(index, "<meta charset=\"UTF-8\">");
        System.out.println(xmlStrbuilder.toString());
    }
}
