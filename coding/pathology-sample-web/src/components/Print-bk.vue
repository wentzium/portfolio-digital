<template>
	<div class="demo">
		<button @click="printPdf">打印</button>
		<form id="form1">
			<table width="400" height="200" cellspacing="0" style="font-size: 20px;border-color: #F2F2F2;">
				<tr >
					<td width="250" style="text-align: center;">
						<div id="qrcode" style="width: 150px;height: 150px;margin: 0 auto;"></div>
					</td>
					<td width="250" style="padding-left: 20px;">
						<div><font>{{infos.applyNo}}</font></div>
						<div><font>{{infos.sampleType}}</font></div>
						<div><font>{{infos.bw}}</font></div>
					</td>
				</tr>
				<tr>
					<td colspan="2" height="40" style="text-align: center;" >{{moment().format('YYYY-MM-DD HH:mm:ss')}}</td>
				</tr>
			</table>
		</form>
	</div>
</template>

<script>
	import { getLodop } from '@/plugins/lodop/LodopFuncs'
	import QRCode  from "qrcodejs2"
	
	export default {
		name: "demo",
		components: { QRCode },
		data() {
			return {
				infos:{
					applyNo:'S12345678',
					sampleType:'样本类型',
					bw:'头部'
				}
			}
		},
		mounted(){
			this.qrcode();
		},
		methods: {
			moment,
			qrcode () {
                let that = this;
                let qrcode = new QRCode('qrcode', {
                    width: 150,
                    height: 150,        // 高度
                    text:  this.infos.applyNo,   // 二维码内容
                })
           },
			// 查询数据
			printPdf() {
				let LODOP = getLodop();
				if(LODOP && LODOP.VERSION) {
					LODOP.PRINT_INIT("订货单");
					LODOP.SET_PRINT_STYLE("FontSize", 18);
					LODOP.SET_PRINT_STYLE("Bold", 1);
					LODOP.ADD_PRINT_HTM(50, 50, 500, 300,
					document.getElementById("form1").innerHTML);
					//	        LODOP.PRINT();
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