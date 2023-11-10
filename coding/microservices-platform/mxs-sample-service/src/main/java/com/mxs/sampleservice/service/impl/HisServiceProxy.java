package com.mxs.sampleservice.service.impl;

import com.mxs.sampleservice.bo.his.ConvertToHTMLResponse;
import com.mxs.sampleservice.bo.his.MedDocInfo;
import com.mxs.sampleservice.util.XmlUtil;
import com.mxs.sampleservice.web.vo.ConvertToHtmlFromHistReqVO;
import com.mxs.sampleservice.web.vo.GetDocInfoListFromHisReqVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/*
 *
 * Created by j.yang on 2019-08-29
 * 
 */
@Component
@Slf4j
public class HisServiceProxy {
    @Value("${his.serviceUrl}")
    private String serviceUrl;
    @Value("${his.showMockData}")
    private Boolean showMockData;

    private TestRestTemplate restTemplate = new TestRestTemplate();

    String getDocInfoListReqXmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?><soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"><soap:Body><GetDocInfos xmlns=\"http://www.healthcare.supcon.com/wsmeddoc/\"><szPatientID>{szPatientID}</szPatientID><szVisitID>{szVisitID}</szVisitID><szVisitType>IP</szVisitType><dtVisitTime>{dtVisitTime}</dtVisitTime><szDocTypeID/><lstDocInfos></lstDocInfos></GetDocInfos></soap:Body></soap:Envelope>";
    String convertToHtmlReqXmlStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?><soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body><ConvertToHTML xmlns=\"http://www.healthcare.supcon.com/wsmeddoc/\"><szPatientID>{szPatientID}</szPatientID><szVisitID>{szVisitID}</szVisitID><arrDocIDs><string>{docID}</string></arrDocIDs></ConvertToHTML></soap:Body></soap:Envelope>";

//    //获取webservice接口地址
//    String serviceUrl = "http://132.147.160.58/MDService/DocService.asmx";
//    //获取域名地址，server定义的
//    String soapAction = "http://www.healthcare.supcon.com/wsmeddoc/";

//    public List<MedDocInfo> getDocInfos(GetDocInfoListFromHisReqVO reqVO) {
//        getDocInfoListReqXmlStr = getDocInfoListReqXmlStr.replace("{szPatientID}", reqVO.getSzPatientID()).replace("{szVisitID}", reqVO.getSzVisitID()).replace("{dtVisitTime}", reqVO.getDtVisitTime());
//
//        org.apache.axis.client.Service service = new org.apache.axis.client.Service();
//        String method = "GetDocInfos";
//        try {
//            Call call = (Call) service.createCall();
//            call.setTargetEndpointAddress(serviceUrl);
//            //设置要调用的方法
//            call.setOperationName(new QName(soapAction, method));
//            //设置要返回的数据类型
//            call.setReturnType(XMLType.XSD_STRING);
//            call.setEncodingStyle("UTF-8");
//            call.setUseSOAPAction(true);
//            call.setSOAPActionURI(soapAction + method);
//            //调用方法并传递参数
//            String result = (String) call.invoke(new Object[]{getDocInfoListReqXmlStr});
//        } catch (Exception e) {
//            return null;
//        }
//    }
//
//    public String convertToHTML(ConvertToHtmlFromHistReqVO reqVO) {
//        convertToHtmlReqXmlStr = convertToHtmlReqXmlStr.replace("{szPatientID}", reqVO.getSzPatientID()).replace("{szVisitID}", reqVO.getSzVisitID()).replace("{docID}", reqVO.getDocID());
//        String method = "ConvertToHTML";
//        org.apache.axis.client.Service service = new org.apache.axis.client.Service();
//        try {
//            Call call = (Call) service.createCall();
//            call.setTargetEndpointAddress(serviceUrl);
//            //设置要调用的方法
//            call.setOperationName(new QName(soapAction, method));
//            //设置要返回的数据类型
//            call.setReturnType(XMLType.XSD_STRING);
//            call.setUseSOAPAction(true);
//            call.setSOAPActionURI(soapAction + method);
//            //调用方法并传递参数
//            String result = (String) call.invoke(new Object[]{convertToHtmlReqXmlStr});
//        } catch (Exception e) {
//            return "";
//        }
//    }

    public List<MedDocInfo> getDocInfos(GetDocInfoListFromHisReqVO reqVO) {
        // 测试环境显示mock数据
        if (null != showMockData && showMockData) {
            String result = "<?xml version=\"1.0\" encoding=\"utf-8\"?><soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"><soap:Body><GetDocInfosResponse xmlns=\"http://www.healthcare.supcon.com/wsmeddoc/\"><GetDocInfosResult>0</GetDocInfosResult><lstDocInfos><MedDocInfo><DefaultTime>1900-01-01T00:00:00</DefaultTime><DocID>8001719455_1005-5_20190405075609_1</DocID><DocTypeID>1005-5</DocTypeID><DocTitle>标题【mock】</DocTitle><DocTime>2019-04-05T08:00:07</DocTime><DocSetID>8001719455_1005-5_20190405075609</DocSetID><DocVersion>1</DocVersion><CreatorID>LY_CK2</CreatorID><CreatorName>创建人</CreatorName><ModifierID>LY_CK2</ModifierID><ModifierName>修改人</ModifierName><ModifyTime>2019-04-05T08:01:07</ModifyTime><PatientID>8001719455</PatientID><PatientName>病人姓名</PatientName><VisitID>1</VisitID><VisitTime>2019-04-01T18:30:00</VisitTime><VisitType>IP</VisitType><DeptCode>230102</DeptCode><DeptName>科室名称</DeptName><SignCode /><ConfidCode /><OrderValue>6</OrderValue><NeedCombin>false</NeedCombin><FileType>CHENPAD</FileType><FileName /><FilePath /><RecordTime>2019-04-05T08:56:00</RecordTime><StatusDesc /><RequestSignDate>1900-01-01T00:00:00</RequestSignDate><ParentSignDate>1900-01-01T00:00:00</ParentSignDate><SuperSignDate>1900-01-01T00:00:00</SuperSignDate></MedDocInfo><MedDocInfo><DefaultTime>1900-01-01T00:00:00</DefaultTime><DocID>8001719455_1001-1_20190405094035_1</DocID><DocTypeID>1001-1</DocTypeID><DocTitle>标题【mock】</DocTitle><DocTime>2019-04-05T09:40:59</DocTime><DocSetID>8001719455_1001-1_20190405094035</DocSetID><DocVersion>1</DocVersion><CreatorID>LY_CK2</CreatorID><CreatorName>创建人</CreatorName><ModifierID>LY_CK2</ModifierID><ModifierName>修改人</ModifierName><ModifyTime>2019-04-07T23:17:57</ModifyTime><PatientID>8001719455</PatientID><PatientName>病人姓名</PatientName><VisitID>1</VisitID><VisitTime>2019-04-01T18:30:00</VisitTime><VisitType>IP</VisitType><DeptCode>230102</DeptCode><DeptName>科室名称</DeptName><SignCode /><ConfidCode /><OrderValue>8</OrderValue><NeedCombin>false</NeedCombin><FileType>CHENPAD</FileType><FileName /><FilePath /><RecordTime>2019-04-06T09:40:00</RecordTime><StatusDesc /><RequestSignDate>1900-01-01T00:00:00</RequestSignDate><ParentSignDate>1900-01-01T00:00:00</ParentSignDate><SuperSignDate>1900-01-01T00:00:00</SuperSignDate></MedDocInfo></lstDocInfos></GetDocInfosResponse></soap:Body></soap:Envelope>";

            return XmlUtil.parseObject(result, "MedDocInfo", MedDocInfo.class);
        }

        String reqXmlStr = getDocInfoListReqXmlStr.replace("{szPatientID}", reqVO.getPatientNo()).replace("{szVisitID}", reqVO.getVisitId()).replace("{dtVisitTime}", reqVO.getVisitTime());
        HttpHeaders headers = new HttpHeaders();
        MediaType mediaType = MediaType.parseMediaType("application/xml;charset=UTF-8");
        headers.setContentType(mediaType);

        HttpEntity<String> formEntity = new HttpEntity<>(reqXmlStr, headers);

        String result = restTemplate.postForObject(serviceUrl, formEntity, String.class);

        return XmlUtil.parseObject(result, "MedDocInfo", MedDocInfo.class);
    }

    public String convertToHTML(ConvertToHtmlFromHistReqVO reqVO) {
        // 测试环境显示mock数据
        if (null != showMockData && showMockData) {
            return "测试数据";
        }
        String reqXmlStr = convertToHtmlReqXmlStr.replace("{szPatientID}", reqVO.getPatientNo()).replace("{szVisitID}", reqVO.getVisitId()).replace("{docID}", reqVO.getDocId());
        HttpHeaders headers = new HttpHeaders();
        MediaType mediaType = MediaType.parseMediaType("application/xml;charset=UTF-8");
        headers.setContentType(mediaType);

        HttpEntity<String> formEntity = new HttpEntity<>(reqXmlStr, headers);

        String result = restTemplate.postForObject(serviceUrl, formEntity, String.class);

        List<ConvertToHTMLResponse> list = XmlUtil.parseObject(result, "ConvertToHTMLResponse", ConvertToHTMLResponse.class);
        if (CollectionUtils.isEmpty(list)) {
            return "";
        }
        StringBuilder xmlStrBuilder = new StringBuilder(list.get(0).getConvertToHTMLResult());
        // 测试时发现返回的html内容没有编码设置，可能出现中文乱码，故手动添加
        int index = xmlStrBuilder.lastIndexOf("<head>") + 6;
        xmlStrBuilder.insert(index, "<meta charset=\"UTF-8\">");
        return xmlStrBuilder.toString();
    }
}
