package com.mxs.thirdpartyservice.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mxs.common.entity.*;
import com.mxs.thirdpartyservice.entity.ExamItems;
import com.mxs.thirdpartyservice.entity.PatsInHospital;
import com.mxs.thirdpartyservice.service.ExamAppointsService;
import com.mxs.thirdpartyservice.service.ExamItemsService;
import com.mxs.thirdpartyservice.service.ICommonService;
import com.mxs.thirdpartyservice.service.PatsInHospitalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CommonServiceImpl implements ICommonService {

    @Autowired
    private Environment env;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ExamItemsService examItemsService;

    @Autowired
    private ExamAppointsService examAppointsService;

    @Autowired
    private PatsInHospitalService patsInHospitalService;

//    @Override
//    public TpPatientInfo findPatientById(String patientId) {
//        List<TpPatientInfo> patientInfos = jdbcTemplate.query(env.getProperty("custom.thirdparty.patient.sql"), (resultSet, i) -> {
//            TpPatientInfo tpPatientInfo = new TpPatientInfo();
//            tpPatientInfo.setPatientNo(getColumnValue(resultSet, "PATIENTID"));
//            tpPatientInfo.setName(getColumnValue(resultSet, "NAME"));
//            tpPatientInfo.setSex(getColumnValue(resultSet, "GENDER"));
//            tpPatientInfo.setAge(getColumnValue(resultSet, "AGE"));
//            tpPatientInfo.setMarriageStatus(getColumnValue(resultSet, "MARRIAGE"));
//            tpPatientInfo.setBirthPlace(getColumnValue(resultSet, "BIRTHPLACE"));
//            tpPatientInfo.setOccupation(getColumnValue(resultSet, "BUSINESS"));
//            tpPatientInfo.setAddress(getColumnValue(resultSet, "ADDRESS"));
//            tpPatientInfo.setInspectionHospital(getColumnValue(resultSet, "HOSPITAL"));
//            tpPatientInfo.setDepartment(getColumnValue(resultSet, "CATEGORY"));
//            tpPatientInfo.setOutPatientNo(getColumnValue(resultSet, "OUTPATIENT"));
//            tpPatientInfo.setSickroom(getColumnValue(resultSet, "WARDNO"));
//            tpPatientInfo.setHospitalNo(getColumnValue(resultSet, "INPINFO"));
//            tpPatientInfo.setBedNo(getColumnValue(resultSet, "BEDNO"));
//            return tpPatientInfo;
//        }, patientId);
//
//        if (CollectionUtils.isEmpty(patientInfos)) {
//            return null;
//        }
//        return patientInfos.get(0);
//    }

    @Override
    public TpPatientInfo findPatientById(String patientId) {
        String sql = "SELECT DISTINCT A .PATIENT_ID AS PATIENTID, A . NAME, A .SEX AS GENDER, A .LAST_VISIT_DATE AS LASTVISITDATE, '' AS BIRTHPLACE, A .INP_NO AS INPINFO, decode(floor((sysdate - a.DATE_OF_BIRTH) / 365), null, ' ', floor((sysdate - a.DATE_OF_BIRTH) / 365)) AGE FROM medrec.pat_master_index A WHERE A.patient_id = ? ORDER BY A .create_date DESC ";
        List<TpPatientInfo> patientInfoList = jdbcTemplate.query(sql, ((resultSet,i) -> {
            TpPatientInfo tpPatientInfo = new TpPatientInfo();
            tpPatientInfo.setPatientNo(getColumnValue(resultSet, "PATIENTID"));
            tpPatientInfo.setName(getColumnValue(resultSet, "NAME"));
            tpPatientInfo.setSex(getColumnValue(resultSet, "GENDER"));
            tpPatientInfo.setAge(getColumnValue(resultSet, "AGE"));
            tpPatientInfo.setBirthPlace(getColumnValue(resultSet, "BIRTHPLACE"));
            tpPatientInfo.setHospitalNo(getColumnValue(resultSet, "INPINFO"));
            return tpPatientInfo;
        }), patientId);
        if (CollectionUtils.isEmpty(patientInfoList)) {
            return null;
        }

        TpPatientInfo result = patientInfoList.get(0);

//        List<PatsInHospital> patsInHospitals = patsInHospitalService.selectList(new EntityWrapper<PatsInHospital>().eq("PATIENT_ID", patientId));
//
//        if (!CollectionUtils.isEmpty(patsInHospitals)) {
//            result.setDiagnosis(patsInHospitals.get(0).getDiagnosis());
//        }

        List<String> diagnosisList = jdbcTemplate.query("select * from INPADM.PATS_IN_HOSPITAL where PATIENT_ID = ?", ((resultSet, i) -> {
            String value = getColumnValue(resultSet, "DIAGNOSIS");
            return value;
        }), patientId);

        if (!CollectionUtils.isEmpty(diagnosisList)) {
            result.setDiagnosis(diagnosisList.get(0));
        }

        List<TpPatientInfo> infoList = jdbcTemplate.query("select b.PATIENT_ID      AS PATIENTID,\n" +
                "       b.MARITAL_STATUS  AS MARRIAGE,\n" +
                "       k.occupation_name AS BUSINESS,\n" +
                "       b.MAILING_ADDRESS AS ADDRESS\n" +
                "  from medrec.pat_visit b, comm.OCCUPATION_DICT k\n" +
                " where b.occupation = k.occupation_code\n" +
                "   and b.PATIENT_ID = ? \n" +
                "   and rownum < 2\n" +
                " order by b.VISIT_ID desc", ((resultSet, i) -> {
            TpPatientInfo tpPatientInfo = new TpPatientInfo();
            tpPatientInfo.setMarriageStatus(getColumnValue(resultSet, "MARRIAGE"));
            tpPatientInfo.setOccupation(getColumnValue(resultSet, "BUSINESS"));
            tpPatientInfo.setAddress(getColumnValue(resultSet, "ADDRESS"));
            tpPatientInfo.setPatientNo(getColumnValue(resultSet, "PATIENTID"));
            return tpPatientInfo;
        }), patientId);

        if (!CollectionUtils.isEmpty(infoList)) {
            result.setMarriageStatus(infoList.get(0).getMarriageStatus());
            result.setOccupation(infoList.get(0).getOccupation());
            result.setAddress(infoList.get(0).getAddress());
        }

        infoList = jdbcTemplate.query("select b.PATIENT_ID AS PATIENTID, b.SERIAL_NO AS OUTPATIENT from  outpadm.clinic_master b where b.PATIENT_ID = ? and rownum < 2 order by b.VISIT_DATE desc", ((resultSet, i) -> {
            TpPatientInfo tpPatientInfo = new TpPatientInfo();
//            tpPatientInfo.setOutPatientNo(getColumnValue(resultSet, "OUTPATIENT"));
            tpPatientInfo.setOutPatientNo("");
            tpPatientInfo.setPatientNo(getColumnValue(resultSet, "PATIENTID"));
            return tpPatientInfo;
        }), patientId);

        if (!CollectionUtils.isEmpty(infoList)) {
            result.setOutPatientNo(infoList.get(0).getOutPatientNo());
        }

        infoList = jdbcTemplate.query("SELECT\n" +
                "\tPATIENT_ID AS PATIENTID,\n" +
                "\tFACILITY AS HOSPITAL\n" +
                "FROM\n" +
                "\texam.exam_master\n" +
                "WHERE\n" +
                "\tPATIENT_ID = ? \n" +
                "AND ROWNUM < 2\n" +
                "ORDER BY\n" +
                "\tVISIT_ID DESC", ((resultSet,i) -> {
            TpPatientInfo tpPatientInfo = new TpPatientInfo();
            tpPatientInfo.setPatientNo(getColumnValue(resultSet, "PATIENTID"));
            tpPatientInfo.setInspectionHospital(getColumnValue(resultSet, "HOSPITAL"));
            return tpPatientInfo;
        }), patientId);

        if (!CollectionUtils.isEmpty(infoList)) {
            result.setInspectionHospital(infoList.get(0).getInspectionHospital());

        }

        infoList = jdbcTemplate.query("SELECT\n" +
                "\tE .PATIENT_ID AS PATIENTID,\n" +
                "\tE .WARD_CODE AS WARDNO,\n" +
                "\tE .BED_NO AS BEDNO,\n" +
                "\tK .DEPT_NAME AS CATEGORY\n" +
                "FROM\n" +
                "\tinpadm.pats_in_hospital E,\n" +
                "\tcomm.DEPT_DICT K\n" +
                "WHERE\n" +
                "\tE .DEPT_CODE = K .DEPT_CODE\n" +
                "AND PATIENT_ID = ? \n" +
                "AND ROWNUM < 2\n" +
                "ORDER BY\n" +
                "\tVISIT_ID DESC", ((resultSet,i) -> {
            TpPatientInfo tpPatientInfo = new TpPatientInfo();
            tpPatientInfo.setPatientNo(getColumnValue(resultSet, "PATIENTID"));
            tpPatientInfo.setDepartment(getColumnValue(resultSet, "CATEGORY"));
            tpPatientInfo.setSickroom(getColumnValue(resultSet, "WARDNO"));
            tpPatientInfo.setBedNo(String.valueOf(resultSet.getInt("BEDNO")));
            return tpPatientInfo;
        }), patientId);

        if (!CollectionUtils.isEmpty(infoList)) {
            result.setDepartment(infoList.get(0).getDepartment());
            result.setSickroom(infoList.get(0).getSickroom());
            result.setBedNo(infoList.get(0).getBedNo());
        }

        return result;
    }

//    private String getColumnValue(ResultSet rs, String column) {
//        try {
//            return new String(rs.getBytes(column), "GBK");
//            String value = rs.getString(column);
//            if (null != value) {
//                if (CharacterCodingUtil.checkISO(value)) {
//                    return new String(value.getBytes("ISO-8859-1"), Charset.forName(env.getProperty("custom.oracle.charset", "GBK")));
//                }
//                return value;
//            }
//        } catch (Exception e) {
//            log.error("获取字段error", e);
//        }
//        return null;
//    }

    private String getColumnValue(ResultSet rs, String column) {
        try {
            byte[] resultBytes = rs.getBytes(column);
            if (null == resultBytes || 0 == resultBytes.length)
                return new String();
            else
                return new String(resultBytes, "GBK");
//            String value = rs.getString(column);
//            if (null != value) {
//                if (CharacterCodingUtil.checkISO(value)) {
//                    return new String(value.getBytes("ISO-8859-1"), Charset.forName(env.getProperty("custom.oracle.charset", "GBK")));
//                }
//                return value;
//            }
        }
        /*
 *      catch (NullPointerException npe) {
            return null;
        }
        */
        catch (Exception e) {
            log.error("在获取Column " + column + "字段时出现错误", e);
        }
        return null;
    }

    @Override
    public Long getAppointmentNoFromHis() {
        Long value = jdbcTemplate.queryForObject(env.getProperty("custom.thirdparty.examno.sql", "select EXAM.EXAM_NO_SEQ.nextval as exam_no from dual"), Long.class);
        return value;
    }

    @Override
    public int addExamAppoints(TpExamAppoints tpExamAppoints) {
        List<PatsInHospital> patsInHospitals = patsInHospitalService.selectList(new EntityWrapper<PatsInHospital>().eq("PATIENT_ID", tpExamAppoints.getPatientId()));
        if (!CollectionUtils.isEmpty(patsInHospitals)) {
            String diagnosis = patsInHospitals.get(0).getDiagnosis();
            tpExamAppoints.setClinDiag(diagnosis);
            tpExamAppoints.setClinSymp(diagnosis);
        }
        StringBuilder insertMasterSql = new StringBuilder();
        insertMasterSql.append("INSERT INTO EXAM.EXAM_APPOINTS( EXAM_NO," +
                "LOCAL_ID_CLASS," +
                "PATIENT_LOCAL_ID," +
                "PATIENT_ID," +
                "VISIT_ID," +
                "NAME," +
                "SEX," +
                "DATE_OF_BIRTH," +
                "EXAM_CLASS," +
                "EXAM_SUB_CLASS," +
                "CLIN_SYMP," +
                "PHYS_SIGN," +
                "RELEVANT_LAB_TEST," +
                "RELEVANT_DIAG," +
                "CLIN_DIAG," +
                "EXAM_GROUP,         " +
                "PERFORMED_BY," +
                "PATIENT_SOURCE," +
                "FACILITY," +
                "REQ_DATE_TIME," +
                "REQ_DEPT," +
                "REQ_PHYSICIAN," +
                "REQ_MEMO," +
                "technician");
        insertMasterSql.append(") VALUES (");
        insertMasterSql.append(convertNullToBlank(tpExamAppoints.getExamNo() , true));
        insertMasterSql.append(convertNullToBlank(tpExamAppoints.getLocalIdClass() , true));
        insertMasterSql.append(convertNullToBlank(tpExamAppoints.getPatientLocalId() , true));
        insertMasterSql.append(convertNullToBlank(tpExamAppoints.getPatientId() , true));
        insertMasterSql.append(convertNullToBlank(tpExamAppoints.getVisitId() , true));//vistid住院次数，
        insertMasterSql.append(convertNullToBlank(tpExamAppoints.getName() , true));
        insertMasterSql.append(convertNullToBlank(tpExamAppoints.getSex() , true));
        if (null != tpExamAppoints.getDateOfBirth()) {
            insertMasterSql.append("to_date('" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(tpExamAppoints.getDateOfBirth()) + "','yyyy-mm-dd hh24:mi:ss'),");
        } else {
            insertMasterSql.append("null,");
        }
        insertMasterSql.append(convertNullToBlank(tpExamAppoints.getExamClass() , true));
        insertMasterSql.append(convertNullToBlank(tpExamAppoints.getExamSubClass() , true));
        insertMasterSql.append(convertNullToBlank(tpExamAppoints.getClinSymp() , true));
        insertMasterSql.append(convertNullToBlank(tpExamAppoints.getPhysSign() , true));
        insertMasterSql.append(convertNullToBlank(tpExamAppoints.getRelevantLabTest() , true));
        insertMasterSql.append(convertNullToBlank(tpExamAppoints.getRelevantDiag() , true));
        insertMasterSql.append(convertNullToBlank(tpExamAppoints.getClinDiag() , true));
        insertMasterSql.append(convertNullToBlank(tpExamAppoints.getExamGroup() , true));
        insertMasterSql.append(convertNullToBlank(tpExamAppoints.getPerformedBy() , true));//执行科室代码 ，默认为病理科代码4104
        insertMasterSql.append(convertNullToBlank(tpExamAppoints.getPatientSource() , true));//病人类别 2住院
        insertMasterSql.append(convertNullToBlank(tpExamAppoints.getFacility() , true));
        if (null != tpExamAppoints.getReqDateTime()) {
            insertMasterSql.append("to_date('" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(tpExamAppoints.getReqDateTime()) + "','yyyy-mm-dd hh24:mi:ss'),");//申请时间  新建申请单的时间
        } else {
            insertMasterSql.append("null,");
        }
        insertMasterSql.append(convertNullToBlank(tpExamAppoints.getReqDept() , true)); //申请科室编码
        insertMasterSql.append(convertNullToBlank(tpExamAppoints.getReqPhysician() , true)); //申请医生

        insertMasterSql.append(convertNullToBlank(tpExamAppoints.getReqMemo(), true));//备注

        insertMasterSql.append(convertNullToBlank(tpExamAppoints.getTechnician(), false));

        insertMasterSql.append(")");
        log.info("添加预约信息, {}", insertMasterSql);


        return jdbcTemplate.update(insertMasterSql.toString());
    }

    private String convertNullToBlank(Object obj, boolean containSemicolon) {
        if (containSemicolon) {
            if (obj != null) {
                return "'" + obj + "',";
            } else {
                return "null,";
            }
        } else {
            if (obj != null) {
                return "'" + obj + "'";
            } else {
                return "null";
            }
        }
    }

    @Override
    public List<TpExamMaster> findExamMasterByPatientId(String patientId) {
        List<TpExamMaster> examMasterList = jdbcTemplate.query("select PATIENT_ID, '' AS PATIENT_LOCAL_ID, VISIT_ID, EXAM_DATE_TIME, EXAM_CLASS, EXAM_SUB_CLASS, CLIN_SYMP, RELEVANT_DIAG, CLIN_DIAG from EXAM.EXAM_MASTER where PATIENT_ID = ?  order by exam_date_time desc", (resultSet, i) -> {
            TpExamMaster examMaster = new TpExamMaster();
            examMaster.setPatientId(getColumnValue(resultSet, "PATIENT_ID"));
            examMaster.setExamDateTime(resultSet.getTimestamp("EXAM_DATE_TIME"));
            examMaster.setPatientLocalId(getColumnValue(resultSet, "PATIENT_LOCAL_ID"));
            examMaster.setVisitId(resultSet.getLong("VISIT_ID"));
            examMaster.setExamClass(getColumnValue(resultSet, "EXAM_CLASS"));
            examMaster.setExamSubClass(getColumnValue(resultSet, "EXAM_SUB_CLASS"));
            examMaster.setClinSymp(getColumnValue(resultSet, "CLIN_SYMP"));
            examMaster.setRelevantDiag(getColumnValue(resultSet, "RELEVANT_DIAG"));
            examMaster.setClinDiag(getColumnValue(resultSet, "CLIN_DIAG"));
            return examMaster;
        }, patientId);
        return examMasterList;
    }

    @Override
    public List<TpExamRptPattern> findExamRptPattern() {
//        List<TpExamRptPattern> rptPatternList = jdbcTemplate.query("SELECT EXAM_CLASS,EXAM_SUB_CLASS,DESC_ITEM,DESC_NAME,DESCRIPTION,DESCRIPTION_CODE,INPUT_CODE,FLAG FROM COMM.EXAM_RPT_PATTERN WHERE EXAM_CLASS = '病理' and DESC_ITEM = '检查项目'", (resultSet, i) -> {
        List<TpExamRptPattern> rptPatternList = jdbcTemplate.query("SELECT EXAM_CLASS,EXAM_SUB_CLASS,DESC_ITEM,DESC_NAME,DESCRIPTION,DESCRIPTION_CODE,INPUT_CODE,FLAG FROM COMM.EXAM_RPT_PATTERN ", (resultSet, i) -> {
            TpExamRptPattern rptPattern = new TpExamRptPattern();
            rptPattern.setDescItem(getColumnValue(resultSet, "DESC_ITEM"));
            rptPattern.setDescName(getColumnValue(resultSet,"DESC_NAME"));
            rptPattern.setDescription(getColumnValue(resultSet, "DESCRIPTION"));
            rptPattern.setDescriptionCode(getColumnValue(resultSet, "DESCRIPTION_CODE"));
            rptPattern.setExamClass(getColumnValue(resultSet, "EXAM_CLASS"));
            rptPattern.setExamSubClass(getColumnValue(resultSet, "EXAM_SUB_CLASS"));
            rptPattern.setInputCode(getColumnValue(resultSet, "INPUT_CODE"));
            rptPattern.setFlag(resultSet.getInt("FLAG"));
            return rptPattern;
        });

        List<TpExamRptPattern> result = rptPatternList.stream().filter(dto -> "病理".equals(dto.getExamClass()) && "检查项目".equals(dto.getDescItem())).collect(Collectors.toList());

        return result;
    }

    @Override
    public int addExamItems(TpExamItems examItems) {
        if (null == examItems) {
            return 0;
        }
//        int update = jdbcTemplate.update("insert into EXAM.EXAM_ITEMS(EXAM_NO, EXAM_ITEM_NO, EXAM_ITEM, EXAM_ITEM_CODE, COSTS, EXAM_SUB_CLASS) values (?, ?, ?, ?, ?, ?)",
//                examItems.getExamNo(), examItems.getExamItemNo(), examItems.getExamItem(), examItems.getExamItemCode(), examItems.getCosts(), examItems.getExamSubClass());

        List<ExamItems> examItemsList = examItemsService.selectList(new EntityWrapper<ExamItems>().eq("EXAM_NO", examItems.getExamNo()));

        if (!CollectionUtils.isEmpty(examItemsList)) {
            boolean b = examItemsService.delete(new EntityWrapper<ExamItems>().eq("EXAM_NO", examItems.getExamNo()));
            log.info("刪除ExamItems成功, {}", examItems);
        }

        ExamItems entity = new ExamItems();
        BeanUtils.copyProperties(examItems, entity);
        entity.setCosts(null != examItems.getCosts() ? examItems.getCosts().doubleValue() : 0.0);
        Boolean insert = examItemsService.insert(entity);
        return insert ? 1 : 0;
    }
}
