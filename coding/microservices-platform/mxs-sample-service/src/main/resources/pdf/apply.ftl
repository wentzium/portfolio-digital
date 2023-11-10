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
        <div style="text-align:center;">
            <span style="font-size:25px;"><strong>西南医院</strong></span>
        </div>
        <div style="text-align:center;">
            <span style="font-size:20px;">病理检查申请单</span>
        </div>
    </div>
    <div style="float:left;width:20%;">
        <img src="data:image/png;base64,${imgBase64}" />
    </div>
    <div style="text-align:right; padding-right: 140px;">
        <span style="font-size:16px;">病理号：${pathologyNo}</span>
    </div>
    <hr/>
    <table style="width:100%;" cellpadding="2" cellspacing="0" border="0" align="left">
        <tbody>
        <tr>
            <td>
                <span style="font-size:14px;">姓名:${patientName}</span>
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
            <td>
                <span style="font-size:14px;">职业：${occupation}</span>
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
                <span style="font-size:14px;">科别：${department}</span>
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
                <span style="font-size:14px;">送检日期：${inspectionDate}</span>
            </td>
            <td>
                <span style="font-size:14px;">收到日期：${receiveDate}</span>
            </td>
            <td>
                <br/>
            </td>
            <td>
                <br/>
            </td>
        </tr>
        <tr>
            <td>
                <span style="font-size:14px;">联系信息：${concat}</span>
            </td>
            <td>
                <span style="font-size:14px;">电话：${phone}</span>
            </td>
            <td>
                <span style="font-size:14px;">接收员：${receiver}</span>
            </td>
            <td>
                <br/>
            </td>
            <td>
                <br/>
            </td>
        </tr>
        </tbody>
    </table>
    <hr/>
    <p style="font-size:16px;text-align:left;margin-top: -5px;">
        <strong><span style="font-size:14px;">病史摘要及临床所见：</span></strong>
    </p>
    <p style="text-align:left;">
        <span style="font-size:14px;">${medicalHistorySummary}</span>
    </p>
    <br/>
    <p style="text-align:left;">
        <span style="font-size:14px;"><strong>影像检查：</strong></span>
    </p>
    <p style="text-align:left;">
        <span style="font-size:14px;">${imageInspect}</span>
    </p>
    <br/>
    <p style="text-align:left;">
        <span style="font-size:14px;"><strong>实验室检查：</strong></span>
    </p>
    <p style="text-align:left;">
        <span style="font-size:14px;">${laboratoryInspect}</span>
    </p>
    <br/>
    <hr/>
    <strong><span style="font-size:14px;">手术名称和手术所见：</span></strong>
    <p style="text-align:left;">
        <span style="font-size:14px;">${operationSummary}</span>
    </p>
    <br/>
    <hr/>
    <strong><span style="font-size:14px;">若系肿瘤标本请填写下列各项：</span></strong>
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
                <span style="font-size:14px;">是否接受放、化疗：${hasRadiotherapy}、${hasChemotherapy}</span>
            </td>
        </tr>
        </tbody>
    </table>
    </p>
    <hr/>
    <strong><span style="font-size:14px;">若系子宫内膜标本，请勿忘填写下列各项：</span></strong>
    <p>
    <table style="width:100%;" cellpadding="2" cellspacing="0" border="0">
        <tbody>
        <tr>
            <td>
                <span style="font-size:14px;">经期持续时间：${menstrualDuration}</span><span
                    style="font-size:14px;"></span><br/>
            </td>
            <td>
                <span style="font-size:14px;">末次月经：${lastMenstrual}</span>
            </td>
            <td>
                <span style="font-size:14px;">刮宫日期：${dcOrSamplingDate}</span>
            </td>
        </tr>
        <tr>
            <td>
                <span style="font-size:14px;">激素治疗日期及剂量：${HRTDateAndDose}</span>
            </td>
            <td colspan="2">
                <span style="font-size:14px;">有否服用避孕药（名称、日期）：${contraceptiveName}、${contraceptiveDate}</span>
            </td>
        </tr>
        <tr>
            <td>
                <span style="font-size:14px;">妊次：${pregnancy}</span>
            </td>
            <td>
                <span style="font-size:14px;">产次：${parity}</span>
            </td>
            <td>
                <span style="font-size:14px;">末次生产或流产：${lastPregnancy}</span>
            </td>
        </tr>
        <tr>
            <td>
                <span style="font-size:14px;">小便或血hCG：${HCG}</span>
            </td>
            <td colspan="2">
                <span style="font-size:14px;">宫内节育器：${IUD}</span>
            </td>
        </tr>
        </tbody>
    </table>
    </p>
    <hr/>
    <strong style="pa"><span style="font-size:14px;">若曾做过病理检查，务填写：</span></strong>
    <p>
    <table style="width:100%;" cellpadding="2" cellspacing="0" border="0">
        <tbody>
        <tr>
            <td>
                <span style="font-size:14px;">检查单位：${overInspectionUnit}</span>
            </td>
            <td>
                <span style="font-size:14px;">检查日期：${overInspectionDate}</span>
            </td>
            <td>
                <span style="font-size:14px;">病理号：${overPathologyNo}</span>
            </td>
        </tr>
        <tr>
            <td colspan="3">
                <span style="font-size:14px;">病理诊断：${overPathologyDiagnosis}</span>
            </td>
        </tr>
        </tbody>
    </table>
    </p>
    <hr/>
    <p>
        <span style="font-size:14px;"><b>临床诊断（映像）</b></span><b>： </b><span
            style="font-size:14px;">${clinicalDiagnosis}</span>
    </p>
    <p>
        <strong><span style="font-size:14px;">送检目的： </span></strong><span
            style="font-size:14px;">${inspectionPurpose}</span>
    </p>
    <br/>
    <hr/>
    <br/>
    <table style="width:100%;" cellpadding="2" cellspacing="0" border="0">
        <tbody>
        <tr>
            <td>
                <span style="font-size:14px;">序号</span>
            </td>
            <td>
                <span style="font-size:14px;">条码号</span>
            </td>
            <td>
                <span style="font-size:14px;">样本类型</span>
            </td>
            <td>
                <span style="font-size:14px;">样本位置</span>
            </td>
        </tr>
        <#if sampleList?? && (sampleList ? size > 0)>
            <#list sampleList as sam>
            <tr>
                <td style="border-bottom:1px dashed #000">
                ${sam.sortNum}
                </td>
                <td style="border-bottom:1px dashed #000">
                ${sam.sampleNo}
                </td>
                <td style="border-bottom:1px dashed #000">
                ${sam.sampleType}
                </td>
                <td style="border-bottom:1px dashed #000">
                ${sam.sampleLocation}
                </td>
            </tr>
            </#list>
        </#if>
        </tbody>
    </table>
</div>
</body>
</html>