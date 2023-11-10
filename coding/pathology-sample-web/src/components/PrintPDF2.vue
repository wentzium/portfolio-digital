<template>
	<div style="display: none;">
		<button @click="printPdf">打印</button>
		<div style="padding: 10px;text-align: center;" id="leica-print-applyno">
			<section style="position: relative;padding: 10px;">
				<p style="margin:0;text-align: center;font-weight: 600;font-size: 22px;">西南医院</p>
				<p style="text-align: center;font-size: 16px;margin-bottom: 20px;">病理检查申请单</p>
				<div class="bd-l " style="position: absolute;padding: 10px;bottom: 0;right: 0;width: 100%;text-align: right;border-bottom: 1px solid #33333C;">
					<img v-if="applyInfo.imgBase64" width="100" :src="'data:image/png;base64,'+applyInfo.imgBase64">
				</div>
			</section>
			<section class="content" style="text-align: left;">
				<table style="width:100%;" cellpadding="2" cellspacing="0" border="0" align="left">
			        <tbody>
			        <tr>
			            <td>
			                <span style="font-size:14px;">姓名:{{applyInfo.patientName}}</span>
			            </td>
			            <td>
			                <span style="font-size:14px;">性别：{{applyInfo.sex}}</span>
			            </td>
			            <td>
			                <span style="font-size:14px;">年龄：{{applyInfo.age}}</span>
			            </td>
			            <td>
			                <span style="font-size:14px;">婚姻：{{applyInfo.marriageStatus}}</span>
			            </td>
			            
			        </tr>
			        <tr>
			        	<td>
			                <span style="font-size:14px;">职业：{{applyInfo.occupation}}</span>
			            </td>
			            <td colspan="3">地址：{{applyInfo.address}}</td>
			        </tr>
			        <tr>
			            <td>
			                <span style="font-size:14px;">门诊号：{{applyInfo.outpatientNo}}</span>
			            </td>
			            <td>
			                <span style="font-size:14px;">住院号：{{applyInfo.hospitalNo}}</span>
			            </td>
			            <td>
			                <span style="font-size:14px;">病房号：{{applyInfo.sickroom}}</span>
			            </td>
			            <td>
			                <span style="font-size:14px;">病床号：{{applyInfo.bedNo}}</span>
			            </td>
			        </tr>
			        <tr>
			            <td>
			                <span style="font-size:14px;">送检医院：{{applyInfo.inspectionHospital}}</span>
			            </td>
			            <td>
			                <span style="font-size:14px;">科别：{{applyInfo.department}}</span>
			            </td>
			            <td>
			                <span style="font-size:14px;">送检时间：{{applyInfo.inspectionDate}}</span>
			            </td>
			            <td>
			                <span style="font-size:14px;">收到日期：{{applyInfo.receiveDate}}</span>
			            </td>
			        </tr>
			        </tbody>
			    </table>
			    <hr>
			    <template v-if="applyInfo.sampleList && applyInfo.sampleList.length">
				    <table style="width:100%;" cellpadding="2" cellspacing="0" border="0">
				        <tbody>
					        <tr>
					            <td>
					                <span style="font-size:14px;">样本编号</span>
					            </td>
					            <td>
					                <span style="font-size:14px;">样本类型</span>
					            </td>
					            <td>
					                <span style="font-size:14px;">样本名称</span>
					            </td>
					            <td>
					                <span style="font-size:14px;">采取位置</span>
					            </td>
					            <td>
					                <span style="font-size:14px;">离体时间</span>
					            </td>
					            <td>
					                <span style="font-size:14px;">固定时间</span>
					            </td>
					        </tr>
				            <tr v-for="item in applyInfo.sampleList" :key="item.sortNum">
				                <td style="border-top:1px dashed #000">{{item.sampleNo}}</td>
				                <td style="border-top:1px dashed #000">{{item.sampleType}}</td>
				                <td style="border-top:1px dashed #000">{{item.sampleName}}</td>
				                <td style="border-top:1px dashed #000">{{item.sampleLocation}}</td>
				                <td style="border-top:1px dashed #000">{{item.separationTime}}</td>
				                <td style="border-top:1px dashed #000">{{item.fixedTime}}</td>
				            </tr>
				        </tbody>
	    			</table>
			    </template>
			    <hr />
			    <table style="width:100%;" cellpadding="2" cellspacing="0" border="0">
			        <tbody>
				        <tr>
				            <td>
				                <span style="font-size:14px;"><strong>门诊检查各项</strong></span>
				            </td>
				            <td>
				                <span style="font-size:14px;"><strong>术中所见</strong></span>
				            </td>
				        </tr>
				        <tr>
				            <td>
				                <span style="font-size:14px;">主诉：{{applyInfo.chiefComplaint}}</span>
				            </td>
				            <td>
				                <span style="font-size:14px;">手术名称：{{applyInfo.operationName}}</span>
				            </td>
				        </tr>
				        <tr>
				            <td>
				                <span style="font-size:14px;">检查项目：{{applyInfo.operationSummary}}</span>
				            </td>
				            <td>
				                <span style="font-size:14px;">术中所见：{{applyInfo.medicalHistorySummary}}</span>
				            </td>
				        </tr>
				        <tr>
				            <td>
				                <span style="font-size:14px;">检查结果：{{applyInfo.inspectResult}}</span>
				            </td>
				        </tr>
			        </tbody>
    			</table>
			    <template v-if="applyInfo.medicalHistorySummary">
				    <p style="font-size:16px;text-align:left;margin-top: -5px;">
				        <strong><span style="font-size:14px;">病史摘要及临床所见：</span></strong>
				    </p>
				    <p style="text-align:left;">
				        <span style="font-size:14px;">{{applyInfo.medicalHistorySummary}}</span>
				    </p>
			    </template>
			    <template v-if="applyInfo.imageInspect">
				    <br>
				    <p style="text-align:left;">
				        <span style="font-size:14px;"><strong>影像检查：</strong></span>
				    </p>
				    <p style="text-align:left;">
				        <span style="font-size:14px;">{{applyInfo.imageInspect}}</span>
				    </p>
			    </template>
			    <template v-if="applyInfo.laboratoryInspect">
				    <br>
				    <p style="text-align:left;">
				        <span style="font-size:14px;"><strong>实验室检查：</strong></span>
				    </p>
				    <p style="text-align:left;">
				        <span style="font-size:14px;">{{applyInfo.laboratoryInspect}}</span>
				    </p>
			    </template>
			    <template v-if="applyInfo.operationSummary">
				    <br>
				    <hr>
				    <strong><span style="font-size:14px;">手术名称和手术所见：</span></strong>
				    <p style="text-align:left;">
				        <span style="font-size:14px;">{{applyInfo.operationSummary}}</span>
				    </p>
			    </template>
			    <template v-if="applyInfo.tumorSite">
				    <br>
				    <hr>
				    <strong><span style="font-size:14px;">若系肿瘤标本请填写下列各项：</span></strong>
				    <p style="font-size:16px;text-align:left;">
				    </p><table style="width:100%;" cellpadding="2" cellspacing="0" border="0">
				        <tbody>
				        <tr>
				            <td>
				                <span style="font-size:14px;">肿瘤部位：{{applyInfo.tumorSite}}</span>
				            </td>
				            <td>
				                <span style="font-size:14px;">肿瘤大小：{{applyInfo.tumorSizeAndShape}}</span>
				            </td>
				            <td>
				                <span style="font-size:14px;">发现时间：{{applyInfo.tumorDiscoveryDate}}</span>
				            </td>
				        </tr>
				        <tr>
				            <td>
				                <span style="font-size:14px;">有无转移：{{applyInfo.transferLocation}}</span>
				            </td>
				            <td colspan="2">
				                <span style="font-size:14px;">是否接受放、化疗：{{applyInfo.hasRadiotherapy}}{{applyInfo.hasChemotherapy}}</span>
				            </td>
				        </tr>
				        </tbody>
				    </table>
			    </template>
			    <template v-if="applyInfo.menstrualDuration">
				    <hr>
				    <strong><span style="font-size:14px;">若系子宫内膜标本，请勿忘填写下列各项：</span></strong>
				    <p>
				    </p><table style="width:100%;" cellpadding="2" cellspacing="0" border="0">
				        <tbody>
				        <tr>
				            <td>
				                <span style="font-size:14px;">经期持续时间：</span><span style="font-size:14px;">{{applyInfo.menstrualDuration}}</span><br>
				            </td>
				            <td>
				                <span style="font-size:14px;">末次月经：{{applyInfo.lastMenstrual}}</span>
				            </td>
				            <td>
				                <span style="font-size:14px;">刮宫日期：{{applyInfo.dcOrSamplingDate}}</span>
				            </td>
				        </tr>
				        <tr>
				            <td>
				                <span style="font-size:14px;">激素治疗日期及剂量：{{applyInfo.contraceptiveName}}</span>
				            </td>
				            <td colspan="2">
				                <span style="font-size:14px;">有否服用避孕药（名称、日期）：{{applyInfo.contraceptiveDate}}</span>
				            </td>
				        </tr>
				        <tr>
				            <td>
				                <span style="font-size:14px;">妊次：{{applyInfo.pregnancy}}</span>
				            </td>
				            <td>
				                <span style="font-size:14px;">产次：{{applyInfo.parity}}</span>
				            </td>
				            <td>
				                <span style="font-size:14px;">末次生产或流产：{{applyInfo.lastPregnancy}}</span>
				            </td>
				        </tr>
				        <tr>
				            <td>
				                <span style="font-size:14px;">小便或血hCG：{{applyInfo.hcg}}</span>
				            </td>
				            <td colspan="2">
				                <span style="font-size:14px;">宫内节育器：{{applyInfo.iud}}</span>
				            </td>
				        </tr>
				        </tbody>
				    </table>
			    </template>
			    <template v-if="applyInfo.overPathologyNo">
				    <hr>
				    <strong><span style="font-size:14px;">若曾做过病理检查，务填写：</span></strong>
				    <table style="width:100%;" cellpadding="2" cellspacing="0" border="0">
				        <tbody>
				        <tr>
				            <td>
				                <span style="font-size:14px;">检查单位：{{applyInfo.overInspectionUnit}}</span>
				            </td>
				            <td>
				                <span style="font-size:14px;">检查日期：{{applyInfo.overInspectionDate}}</span>
				            </td>
				            <td>
				                <span style="font-size:14px;">病理号：{{applyInfo.overPathologyNo}}</span>
				            </td>
				        </tr>
				        <tr>
				            <td colspan="3">
				                <span style="font-size:14px;">病理诊断：{{applyInfo.overPathologyDiagnosis}}</span>
				            </td>
				        </tr>
				        </tbody>
				    </table>
			    </template>
			    <hr>
			    <p>
			        <span style="font-size:14px;"><b>临床诊断（映像）</b></span><b>： </b><span style="font-size:14px;">{{applyInfo.clinicalDiagnosis}}</span>
			    </p>
			    <p>
			        <strong><span style="font-size:14px;">送检目的： </span></strong><span style="font-size:14px;">{{applyInfo.inspectionPurpose}}</span>
			    </p>
			    
			</section>
			
			<section style="margin-top: 10px;border-top: 1px solid #33333C;">
				<table style="width:100%;" cellpadding="2" cellspacing="0" border="0">
			        <tbody>
			        <tr>
			            <td>
			                <span style="font-size:14px;">备注：{{applyInfo.remark}}</span>
			            </td>
			            <td>
			                <span style="font-size:14px;">送检医生：{{applyInfo.inspectionDoctor}}</span>
			            </td>
			        </tr>
			            
			            <tr>
			                <td>打印人：{{applyInfo.printUser}}</td>
			                <td>检查日期：{{applyInfo.printDate}}</td>
			            </tr>
			        </tbody>
    			</table>
			</section>
			
		</div>
	</div>
</template>

<script>
	import { getLodop } from '@/plugins/lodop/LodopFuncs'
	export default {
		name: "PrintPDF",
		components: { },
		props:{
		},
		data() {
			return {
				applyInfo:{
					sampleList: []
				},
			}
		},
		mounted(){
//			this.printPdf('http://localhost:8080/static/pdf/web/viewer.html?file=http://localhost:8080/apply.pdf')
		},
		methods: {
			moment,
			print(apply){
				this.applyInfo=apply || {sampleList: []} ;
				this.$nextTick(()=>{
					this.printPdf();
				})
			},
			printPdf() {
				let LODOP = new getLodop();
				if(LODOP && LODOP.VERSION) {
					LODOP.PRINT_INIT("打印申请单");
					LODOP.ADD_PRINT_HTM(0, 0, "100%","100%", document.getElementById("leica-print-applyno").innerHTML);

//					LODOP.ADD_PRINT_URL(0, 0, 2100, 2970,src);
					LODOP.SET_PRINT_PAGESIZE(1, 0, 0, "A4");
//			        LODOP.PRINT();
					LODOP.PREVIEW();
				} else {
					this.$Modal.confirm({
						content: "打印控件未安装<br><a href='/download/CLodop_Setup_for_Win32NT.exe'>点击下载</a>打印控件并安装，安装完成后请刷新页面",
						onCancel: () => {
							this.$Message.info('Clicked cancel');
						}
					});
				}

			},
		}
	}
</script>