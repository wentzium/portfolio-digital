<template>
	<div class="dpN" style="display: none;">
		<form id="form1">
			<table width="200" height="90" cellspacing="0" style="font-size: 9px;border-color: #F2F2F2;">
				<tr >
					<td width="40" style="text-align: center;">
						<div id="print-tag-qrcode" style="width: 40px;height: 40px;margin: 0 auto;"></div>
					</td>
					<td width="160" style="padding-left: 5px;">
						<div><font>姓名：{{sample.patientName}}</font>  性别：{{sample.sex}}</div>
						<div><font>科室：{{sample.department}}</font></div>
						<div><font>离体时间:{{sample.separationTime}}</font></div>
						<div><font>固定时间:{{sample.fixedTime}}</font></div>
					</td>
				</tr>
				<tr>
					<td colspan="2" height="18" style="text-align: center;font-size: 18;" >{{sample.sampleNo}}</td>
				</tr>
			</table>
		</form>
	</div>
</template>

<script>
	import { getLodop } from '@/plugins/lodop/LodopFuncs'
	import QRCode  from "qrcodejs2"
	import {tagPrintedNotify} from '@/api/sampleApi'
	export default {
		name: "PrintTag",
		components: { QRCode },
		props:{
			sample:{
				type:Object,
  				default:{}
			},
			siteId:[String,Number],
		},
		data() {
			return {
				qrcode:null,
			}
		},
		mounted(){
		},
		methods: {
			moment,
			print(){
				let el = document.getElementById('print-tag-qrcode');
				let childs = el.childNodes; 
				if(childs.length){
					for(var i = childs.length - 1; i >= 0; i--) {
					  el.removeChild(childs[i]);
					}
				}
				
				this.qrcode = new QRCode('print-tag-qrcode', {
                    width: 40,
                    height: 40,        // 高度
                    text:  this.sample.applyNo,   // 二维码内容
                });
                setTimeout(()=>{
                	this.printPdf();
                },300)
				
				return tagPrintedNotify({
					sampleNo:this.sample.sampleNo,
					siteId:this.siteId,
				})
				
			},
			printPdf() {
				let LODOP = new getLodop();
				if(LODOP && LODOP.VERSION) {
					LODOP.PRINT_INIT(0, 0, "50mm", "30mm", "申请单");
					LODOP.SET_PRINT_PAGESIZE(1,490,290)
					LODOP.ADD_PRINT_HTM(5, 5, 490, 290,
					document.getElementById("form1").innerHTML);
//			        LODOP.PRINT();
					LODOP.PREVIEW();
					this.qrcode.clear()
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