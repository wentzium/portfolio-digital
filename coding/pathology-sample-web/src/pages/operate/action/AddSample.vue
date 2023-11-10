<template>
	<div>
		<Modal
	        v-model="visible"
	        :width="840">
	        <template slot="header">
	        	<div style="width: 18px;height: 18px;" class="assets-icon assets-icon-modal-new"></div>
	        	新建样本
	        </template>
	        <div v-if="visible" style="max-height: 300px;overflow-y: auto;">
	        	<Row>
	        		<Col span="10" style="padding-bottom: 50px;" >
	        			<!--样本编号
	        			<Input v-model="sample.sampleNo">
	        				<template slot="suffix">
	        					<div class="dpIB" style="width: 32px;height: 32px;padding: 15%;">
	        						<div class="w100pct h100pct assets-icon assets-icon-input-scan"></div>
	        					</div>
	        				</template>
	        			</Input>-->
	        			新建样本个数
	        			<InputNumber :max="1000" :min="1" :step="1" v-model="sample.nums" style="width: 100%;"></InputNumber>
	        			离体时间
	        			<DatePicker v-model="sample.separationTime" class="ivu-leica-date" type="datetime" placeholder="请选择" style="width: 100%"></DatePicker>
	        			采取部位
	        			<Select v-model="sample.collectionLocationDicId" @on-change="collectionLocationChange" style="width:100%">
					        <Option  v-for="c in collectionLocation" :value="c.id" :key="c.id">{{c.desc}}</Option>
				        </Select>
	        			样本类型
	        			<Select v-model="sample.typeDicId" @on-change="sampleTypeChange" style="width:100%">
					        <Option  v-for="c in sampleTypeList" :value="c.id" :key="c.id">{{c.desc}}</Option>
					    </Select>
					 	组织块数
	        			<InputNumber :max="1000" :min="1" :step="1" v-model="sample.num" style="width: 100%;"></InputNumber>
						固定时间
					    <DatePicker :transfer="Boolean(true)" @on-open-change="sample.fixedTime?sample.fixedTime:(sample.fixedTime=moment().format('YYYY-MM-DD HH:mm:ss'))" type="datetime" v-model="sample.fixedTime" show-week-numbers size="small" style="width: 100%"></DatePicker>
					   	固定液类型
					   	<Select v-model="sample.fixativeType" @on-change="fixedLiquidTypeChange" style="width:100%">
					        <Option  v-for="c in fixedLiquidTypeList" :value="c.id" :key="c.id">{{c.desc}}</Option>
					    </Select>
					    <!--样本重量
					    <Input v-model="sample.sampleWeight" ><span slot="append">g</span></Input>
					    固定液体积
					    <Input v-model="sample.fixativeVolume" ><span slot="append">ml</span></Input>
					    转运容量
					    <Input v-model="sample.transferContainer" ><span slot="append">ml</span></Input>-->
					    手术类型
					    <Select v-model="sample.operationTypeDicId" @on-change="operationTypeChange" >
			                <Option  v-for="c in operationTypeList" :value="c.id" :key="c.id">{{c.desc}}</Option>
			            </Select>
	        			样本说明
	        			<div v-if="sample.operationTypeDesc == '活体检查'">
	        				{{sample.collectionLocationDesc}}
	        				<InputNumber v-model="memo.size" style="width:60px" max="9999" min="0" step="1"></InputNumber>
	        				点包块
	        				<!--<Select v-model="memo.side" transfer="true" placeholder="" style="width:40px">
						        <Option value="左">左</Option>
						        <Option value="右">右</Option>
						    </Select>侧
						    <Select v-model="memo.chunkLocation" transfer="true" placeholder="" style="width:70px">
						        <Option value="腋窝">腋窝</Option>
						        <Option value="乳房">乳房</Option>
						        <Option value="胸壁">胸壁</Option>
						        <Option value="甲状腺">甲状腺</Option>
						    </Select>包块
						    <InputNumber v-model="memo.size" style="width:60px" max="9999" min="0" step="1"></InputNumber>mm-->
	        			</div>
	        			<template v-else>
		        			<Input v-model="sample.desc" type="textarea" :rows="4" placeholder="样本说明" />
	        			</template>
	        		</Col>
	        		<Col span="14" style="padding: 10px;">
	        			<div style="width: 100%;height: 200px;position: relative;">
	        				<img style="top: 25%;left: 25%;position: absolute;" width="50%" height="50%" src="@/assets/default_img.png" />
	        				<!--<img width="100%" height="100%" src="@/assets/eg.png" />-->
	        				<!--<video id="video" class="w100pct h100pct" autoplay playsinline></video>
	        				<canvas style="display: none;" id="canvas" width="240" height="160"></canvas>-->
	        			</div>
	        			<div style="margin: 10px 0;background: #454545;color: #fff;line-height: 30px;">
	        				<Row class="ta-c" >
	        					<!--<Col span="7">补光</Col>
	        					<Col span="1"><Divider type="vertical" /></Col>-->
	        					<!--<Col span="7" ><span style="cursor: pointer;" @click="getPhoto">拍摄</span></Col>-->
	        					<Col span="11" ><span style="cursor: pointer;">拍摄</span></Col>
							    <Col span="2"><Divider type="vertical" /></Col>
	        					<Col span="11">全屏</Col>
	        				</Row>
	        			</div>
	        			<div class="word-line" style="overflow: auto;">
	        				<img v-for="img in sample.imgList" style="width: 130px;height: 130px;margin-right: 10px;" :src="img" />
	        			</div>
	        		</Col>
	        	</Row>
	        </div>
	        <template slot="footer">
	        	<div class="vaM fl"><Checkbox v-model="autoPrint">自动打印</Checkbox></div>
	        	<Button @click="visible=false">取消</Button>
	        	<Button @click="okCheck" type="primary">确认</Button>
	        </template>
	    </Modal>
	    <Modal
	        v-model="confirmModal"
	        :width="300">
	        <div style="padding-top: 30px;">批量新建样本{{sample.nums}}个，请确认</div>
	        <template slot="footer">
	        	<Button @click="confirmModal=false">取消</Button>
	        	<Button @click="ok" type="primary">确认</Button>
	        </template>
	    </Modal>
	</div>
</template>

<script>
import {imageUpload,saveSampleInfo} from '@/api/sampleApi'
import {mapActions,mapGetters} from 'vuex'

export default {
  name: 'AddSmaple',
  components: {},
  data () {
    return {
    	visible:false,
    	confirmModal:false,
    	autoPrint:true,
    	collectionLocation:[],
    	sampleTypeList:[],
    	fixedLiquidTypeList:[],
    	invalidReasonList:[],
    	rejectReasonList:[],
    	operationTypeList:[],
    	defaultFixedLiquidType:{
    		feml:{
    			name:'福尔马林',
    			id:''
    		},
    		jj:{
    			name:'酒精',
    			id:''
    		}
    	},
    	sample:{
		  "applyNo": "",
		  "collectionLocationDesc": "",
		  "collectionLocationDicId": '',
		  "desc": "",
		  "imgList": [],
		  "num": 1,
		  "nums":1,
		  "separationTime": "",
		  "fixedTime":'',
		  "typeDesc": "",
		  "typeDicId": '',
		  "memo":'',
		  "fixativeType":'',
		  "fixativeTypeDesc":'',
		  "operationTypeDicId":'',
		  "operationTypeDesc":'',
		  "sampleWeight":'',
		  "fixativeVolume":'',
		  "transferContainer":'',
		  "siteId":''
		},
		memo:{
			side:'',
			chunkLocation:'',
			size:''
		}
    }
  },
  mounted(){
  	//采集部位
  	this.getDictList('100').then(res=>{this.collectionLocation = res;})
  	//样本类型
  	this.getDictList('200').then(res=>{this.sampleTypeList = res;})
  	//固定液类型
  	this.getDictList('300').then(res=>{
  		this.fixedLiquidTypeList = res;
  		this.fixedLiquidTypeList.map(item=>{
  			if(item.desc == '福尔马林') this.defaultFixedLiquidType.feml.id = item.id;
  			if(item.desc == '酒精') this.defaultFixedLiquidType.jj.id = item.id;
  		})
  	})
  	//作废原因
  	this.getDictList('400').then(res=>{this.invalidReasonList = res;})
  	//退单原因
  	this.getDictList('500').then(res=>{this.rejectReasonList = res;})
  	//手术类型
  	this.getDictList('600').then(res=>{this.operationTypeList = res;})
  },
  methods: {
  	...mapActions({
		getDictList: 'getDictList'
	}),
	moment,
	operationTypeChange(e){
  		this.operationTypeList.map(item=>{
  			if(item.id == e) this.sample.operationTypeDesc = item.desc;
  		})
  	},
  	collectionLocationChange(e){
  		this.collectionLocation.map(item=>{
  			if(item.id == e) this.sample.collectionLocationDesc = item.desc;
  		})
  	},
  	sampleTypeChange(e){
  		this.sampleTypeList.map(item=>{
  			if(item.id == e) {
  				this.sample.typeDesc = item.desc;
  				if(this.sample.typeDesc == '常规样品') {
  					this.sample.fixativeType = this.defaultFixedLiquidType.feml.id;
  					this.sample.fixativeTypeDesc = '福尔马林'
				} else if (this.sample.typeDesc == '细胞学样品'){
					this.sample.fixativeType = this.defaultFixedLiquidType.jj.id;
					this.sample.fixativeTypeDesc = '酒精'
				}
  			}
  			
  		})
  	},
  	fixedLiquidTypeChange(e){
  		this.fixedLiquidTypeList.map(item=>{
  			if(item.id == e) this.sample.fixativeTypeDesc = item.desc;
  		})
  	},
  	show(applyNo,siteId){
  		this.visible = true;
  		this.sample = {//初始化
  		  "applyNo": "",
		  "collectionLocationDesc": "",
		  "collectionLocationDicId": '',
		  "desc": "",
		  "imgList": [],
		  "num": 1,
		  "nums":1,
		  "separationTime": moment().format("YYYY-MM-DD HH:mm:ss"),
		  "fixativeType": '',
  		  "fixativeTypeDesc": "",
  		  "operationTypeDicId":'',
		  "operationTypeDesc":'',
		  "fixedTime":'',
		  "typeDesc": "",
		  "typeDicId": '',
		  "sampleWeight":'5',
		  "fixativeVolume":'50',
		  "transferContainer":'100',
		  "siteId":''
  		}
  		this.sample.applyNo = applyNo
  		
  		this.sample.siteId = siteId
  		
//		this.$nextTick(()=>{
//			this.showCamera()
//		})
  	},
  	getPhoto(){
  		var video = document.getElementById("video");
	    var canvas = document.getElementById("canvas");
	    var context = canvas.getContext("2d");
	    context.drawImage(video,0,0,240,160);
	    let base64Str = canvas.toDataURL("image/png");
	    imageUpload({
	    	base64Str:base64Str
	    }).then(res=>{
	    	if(res.data) this.sample.imgList.push(res.data);
	    	else this.sample.imgList.push(base64Str);
	    },err=>{
	    	console.log(err)
	    })
  	},
  	okCheck(){
  		if(this.sample.nums){
  			if(this.sample.nums == 1) this.ok(); else this.confirmModal = true;
  		} else {
  			this.$Message.info("请输入新建样本个数")
  		}
  	},
  	ok(){
  		this.confirmModal = false;
  		let sampleInfo = _.cloneDeep(this.sample);
  		let sampleNums = _.cloneDeep(this.sample.nums);
  		sampleInfo.memo = JSON.stringify(this.memo)
  		if(sampleInfo.operationTypeDesc == '活体检查'){
//			sampleInfo.desc = this.memo.side + '侧' +  this.memo.chunkLocation + '包块' + this.memo.size + 'mm';
			sampleInfo.desc = this.sample.collectionLocationDesc + this.memo.size + '点包块'
  		}
  		
  		if(sampleInfo.separationTime) sampleInfo.separationTime = moment(sampleInfo.separationTime).format("YYYY-MM-DD HH:mm:ss");
  		if(sampleInfo.fixedTime) sampleInfo.fixedTime = moment(sampleInfo.fixedTime).format("YYYY-MM-DD HH:mm:ss");
  		for(let i =0;i<sampleNums;i++){
  			saveSampleInfo(sampleInfo).then(res=>{
  				if(this.autoPrint) this.$emit('refresh',res.data);
  				else this.$emit('refresh');
  				if(res.msg) this.$Message.success(res.msg)
  				this.visible = false;
  			},err=>{console.log(err)})
  		}
  		
  	},
  	showCamera(){
  		const video = document.querySelector('#video')

	    // 如果浏览器支持，该方法的更新是向后兼容，新版将所有功能都使用navigator.mediaDevices进行了封装
	    navigator.mediaDevices.enumerateDevices().then(function (sourceInfos) {
	      // 如果支持新的方法，那么就使用新的方法来获取，当然这是一种比较主流的判断方法
	      // 如果是想旧的方法兼容，可以使用下面作为判断条件，除IOS和PC以外，均使用旧的获取方式
	      // !(navigator.userAgent.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/) || !/(iPhone|iPad|iPod|iOS|Android)/i.test(navigator.userAgent))
	      
	      /**
	       * 无论是旧的写法还是新的标准，思路都是通过设备信息，获取摄像头的视频流，通过转换变成blob的格式交给video的src
	       */
	      if (!navigator.mediaDevices.getUserMedia) {
	        // 声明一个数组，用于装载设备媒体设备的相关信息，由于回调中sourceInfos对象中携带有所有媒体对象的相关信息
	        // 这里对信息进行遍历筛选，只选出摄像头的Id并保存在数组中
	        var exArray = [];  
	        for (var i = 0; i < sourceInfos.length; ++i) {
	          if (sourceInfos[i].kind == 'videoinput') {
	            exArray.push(sourceInfos[i].deviceId);
	          }
	        }
	        // 通过navigator的getUserMedia获取摄像头的视频流，并在成功的回调中将视频流交给video
	        getMedia();
	
	        function getMedia() {
	          if (navigator.getUserMedia) {
	            // 该方法可以传递3个参数，分别为获取媒体信息的配置，成功的回调函数和失败的回调函数
	            navigator.getUserMedia({
	              audio: false, // 表明是否获取音频
	              video: {  // 对视频信息进行配置
	                optional: [{
	                  'sourceId': exArray[1] // 下标为0是前置摄像头，1为后置摄像头，所以PC不能进入该判断，否则画面会保持在第一帧不动
	                }]
	              },
	            }, successFunc, errorFunc); //success是获取成功的回调函数  
	          } else {
	            alert('Native device media streaming (getUserMedia) not supported in this browser.');
	          }
	        }
	        // 这里是获取媒体信息成功的回调函数
	        function successFunc(stream) {
	          // 对FireFox进行兼容，这里对返回流数据的处理不同
	          if (video.mozSrcObject !== undefined) {
	            //Firefox中，video.mozSrcObject最初为null，而不是未定义的，我们可以靠这个来检测Firefox的支持  
	            video.mozSrcObject = stream;
	          } else {
	            // 一般的浏览器需要使用createObjectURL对流数据进行处理，再交给video元素的src
	            video.src = window.URL && window.URL.createObjectURL(stream) || stream;
	          }
	        }
	        // 获取媒体信息失败的回调
	        function errorFunc(e) {
	          alert('Error！' + e);
	        }
	      } else {  // 当采用最新的标准方式获取视频时
	        // 这里对生成视频进行配置
	        var userMediaConstraints = {
	          audio: false, // 是否获取音频
	          video: {
	            facingMode: 'environment'  // 环境表示后置摄像头，使用user表示采用前置
	          }
	        }
	        // 这里就采用新的方法来获取视频
	        navigator.mediaDevices.getUserMedia(userMediaConstraints).then(function success(stream) {
	          video.srcObject = stream;
	 
	        }).catch(function (error) {
	          alert(error.name + error.message)
	        });
	      }
	    }).catch(function(error) {
	      alert(error.name + error.message)
	    })
  	}
  }
}
</script>

<style>
</style>