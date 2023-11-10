<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="Content-Style-Type" content="text/css"/>
    <meta name="generator" content="Aspose.Words for .NET 15.1.0.0"/>
</head>
<body style="font-family: SimSun;">
<div>
    <div style="float:left;width:80%;">
        <div style="text-align:center;margin-top:10px;">
            <span style="font-size:40px;"><strong>西南医院</strong></span>
        </div>
        <div style="text-align:center;margin-top:10px;">
            <span style="font-size:22px;">病理检查申请单</span>
        </div>
    </div>
    <div style="float:left;width:20%;">
        <img src="data:image/png;base64,${imgBase64}"/>
    </div>
    <div style="clear:both;"></div>
    <hr/>
    <table style="width:100%;" cellpadding="2" cellspacing="0" border="0" align="left">
        <tbody>
        <tr>
            <td>
                <span style="font-size:14px;">姓名：${patientName}</span>
            </td>
            <td>
                <span style="font-size:14px;">性别：${sex}</span>
            </td>
            <td>
                <span style="font-size:14px;">年龄：${age}</span>
            </td>
            <td>
                <span style="font-size:14px;">婚姻：${marriageStatus}</span>
            </td>
        </tr>
        <tr>
            <td>
                <span style="font-size:14px;">职业：${occupation}</span>
            </td>
            <td colspan="3">
                <span style="font-size:14px;">地址：${address}</span>
            </td>
        </tr>
        <tr>
            <td>
                <span style="font-size:14px;">门诊号：${outpatientNo}</span>
            </td>
            <td>
                <span style="font-size:14px;">住院号：${hospitalNo}</span>
            </td>
            <td>
                <span style="font-size:14px;">病房：${sickroom}</span>
            </td>
            <td>
                <span style="font-size:14px;">床号：${bedNo}</span>
            </td>
        </tr>
        <tr>
            <td>
                <span style="font-size:14px;">送检医院：${inspectionHospital}</span>
            </td>
            <td>
                <span style="font-size:14px;">科别：${department}</span>
            </td>
            <td>
                <span style="font-size:14px;">送检时间：${inspectionDate}</span>
            </td>
            <td>
                <span style="font-size:14px;">收到日期：${receiveDate}</span>
            </td>
        </tr>
        </tbody>
    </table>
    <hr/>
    <strong><span style="font-size:14px;padding-left: 20px;">样本列表</span></strong>
    <br/>
    <div>
        <div style="width: 330px;height: ${(sampleList?size+1)*22};float:left;text-align:center">
            <table cellpadding="2" cellspacing="0" border="0">
                <tr>
                    <td width="40px">
                        <span style="font-size: 8px;">样本编号</span>
                    </td>
                    <td width="50px">
                        <span style="font-size: 8px;">样本类型</span>
                    </td>
                    <td width="80px">
                        <span style="font-size: 8px;">样本名称</span>
                    </td>
                    <td width="40px">
                        <span style="font-size: 8px;">采集部位</span>
                    </td>
                    <td width="40px">
                        <span style="font-size: 8px;">组织块数</span>
                    </td>
                    <td width="50px">
                        <span style="font-size: 8px;">离体时间</span>
                    </td>
                    <td width="50px">
                        <span style="font-size: 8px;">固定时间</span>
                    </td>
                </tr>
            <#if sampleList?? && (sampleList ? size > 0)>
                <#list sampleList as sam>
                <#--奇数行显示在左侧-->
                    <#if (sam_index)%2 = 0>
                    <#--偶数行加背景色-->
                        <#if ((sam_index/2)%2) = 0>
                        <tr style="background: rgb(242,242,242)">
                        <#else>
                        <tr>
                        </#if>
                        <td>
                            <span style="font-size: 8px;">${sam.sampleNo}</span>
                        </td>
                        <td>
                            <span style="font-size: 8px;">${sam.sampleType}</span>
                        </td>
                        <td>
                            <span style="font-size: 8px;">${sam.sampleName}</span>
                        </td>
                        <td>
                            <span style="font-size: 8px;">${sam.sampleLocation}</span>
                        </td>
                        <td>
                            <span style="font-size: 8px;">${sam.num}</span>
                        </td>
                        <td>
                            <span style="font-size: 8px;">${sam.separationTime}</span>
                        </td>
                        <td>
                            <span style="font-size: 8px;">${sam.fixedTime}</span>
                        </td>
                    </tr>
                    </#if>
                </#list>
            </#if>
            </table>
        </div>
        <div style="float:left;width: 2px;">
            <div style="width: 1px;height: ${(sampleList?size+1)*22}; background: #000;"></div>
        </div>
        <div style="width: 330px;height: ${(sampleList?size+1)*22};float:left;text-align:center">
            <table cellpadding="2" cellspacing="0" border="0">
                <tr>
                    <td width="40px">
                        <span style="font-size: 8px;">样本编号</span>
                    </td>
                    <td width="50px">
                        <span style="font-size: 8px;">样本类型</span>
                    </td>
                    <td width="80px">
                        <span style="font-size: 8px;">样本名称</span>
                    </td>
                    <td width="40px">
                        <span style="font-size: 8px;">采集部位</span>
                    </td>
                    <td width="40px">
                        <span style="font-size: 8px;">组织块数</span>
                    </td>
                    <td width="50px">
                        <span style="font-size: 8px;">离体时间</span>
                    </td>
                    <td width="50px">
                        <span style="font-size: 8px;">固定时间</span>
                    </td>
                </tr>
            <#if sampleList?? && (sampleList ? size > 0)>
                <#list sampleList as sam>
                <#--偶数行显示在右侧-->
                    <#if (sam_index)%2 != 0>
                    <#--偶数行加背景色-->
                        <#if ((sam_index/2)%2) = 0>
                        <tr style="background: rgb(242,242,242)">
                        <#else>
                        <tr>
                        </#if>
                        <td>
                            <span style="font-size: 8px;">${sam.sampleNo}</span>
                        </td>
                        <td>
                            <span style="font-size: 8px;">${sam.sampleType}</span>
                        </td>
                        <td>
                            <span style="font-size: 8px;">${sam.sampleName}</span>
                        </td>
                        <td>
                            <span style="font-size: 8px;">${sam.sampleLocation}</span>
                        </td>
                        <td>
                            <span style="font-size: 8px;">${sam.num}</span>
                        </td>
                        <td>
                            <span style="font-size: 8px;">${sam.separationTime}</span>
                        </td>
                        <td>
                            <span style="font-size: 8px;">${sam.fixedTime}</span>
                        </td>
                    </tr>
                    </#if>
                </#list>
            </#if>
            </table>
        </div>
    </div>
    <div style="clear:both"></div>
    <hr/>
    <p style="font-size:16px;text-align:left;">
    <table style="width:100%;" cellpadding="2" cellspacing="0" border="0">
        <tbody>
        <tr>
            <td>
                <strong><span style="font-size:14px;">门诊检查各项</span></strong>
            </td>
            <td>
                <strong><span style="font-size:14px;">术中所见</span></strong>
            </td>
        </tr>
        <tr>
            <td>
                <span style="font-size:14px;">主诉：${chiefComplaint}</span>
            </td>
            <td>
                <span style="font-size:14px;">手术名称：${operationName}</span>
            </td>
        </tr>
        <tr>
            <td>
                <span style="font-size:14px;">检查项目：${inspectItem}</span>
            </td>
            <td>
                <span style="font-size:14px;">术中所见：${operationSummary}</span>
            </td>
        </tr>
        <tr>
            <td>
                <span style="font-size:14px;">检查结果：${inspectResult}</span>
            </td>
            <td>
            </td>
        </tr>
        </tbody>
    </table>
    </p>
    <hr/>
    <strong><span style="font-size:14px;">肿瘤标本各项</span></strong>
    <p style="font-size:16px;text-align:left;">
    <table style="width:100%;" cellpadding="2" cellspacing="0" border="0">
        <tbody>
        <tr>
            <td>
                <span style="font-size:14px;">肿瘤部位：${tumorSite}</span>
            </td>
            <td>
                <span style="font-size:14px;">肿瘤大小：${tumorSizeAndShape}</span>
            </td>
            <td>
                <span style="font-size:14px;">发现时间：${tumorDiscoveryDate}</span>
            </td>
        </tr>
        <tr>
            <td>
                <span style="font-size:14px;">有无转移：${transferLocation}</span>
            </td>
            <td colspan="2">
                <span style="font-size:14px;">有无放、化疗：</span>
            </td>
        </tr>
        </tbody>
    </table>
    </p>
    <hr/>
    <strong><span style="font-size:14px;">子宫内膜标本各项</span></strong>
    <p>
    <table style="width:100%;" cellpadding="2" cellspacing="0" border="0">
        <tbody>
        <tr>
            <td>
                <span style="font-size:14px;">末次月经日期：${lastMenstrual}</span>
            </td>
            <td>
                <span style="font-size:14px;">周期及持续时间：${menstrualDuration}</span>
            </td>
            <td>
                <span style="font-size:14px;">刮宫日期：${dcOrSamplingDate}</span>
            </td>
        </tr>
        <tr>
            <td>
                <span style="font-size:14px;">是否经过内分泌治疗：${(endocrineTherapyFlag == 1) ?string( '是','否')}</span>
            </td>
            <td>
                <span style="font-size:14px;">治疗日期：${treatmentDate}</span>
            </td>
            <td>
                <span style="font-size:14px;">剂量：${dose}</span>
            </td>
        </tr>
        <tr>
            <td>
                <span style="font-size:14px;">服用避孕药名称：</span>
            </td>
            <td>
                <span style="font-size:14px;">小便或血hCG：</span>
            </td>
            <td>
                <span style="font-size:14px;">宫内节育器：</span>
            </td>
        </tr>
        </tbody>
    </table>
    </p>
    <div style="width: 50%; text-align: right; float:left;">送检医生：${inspectionDoctor}</div>
</div>
</body>
</html>