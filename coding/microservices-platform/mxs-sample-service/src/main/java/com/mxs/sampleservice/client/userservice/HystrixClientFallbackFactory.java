//package com.mxs.sampleservice.client.userservice;
//
//import com.mxs.common.util.ResponseCode;
//import com.mxs.common.util.ResponseData;
//import feign.hystrix.FallbackFactory;
//
//public class HystrixClientFallbackFactory implements FallbackFactory<ThirdPartyServiceFeignClient> {
//	@Override
//	public ThirdPartyServiceFeignClient create(Throwable cause) {
//		return id -> ResponseData.createFailedResult(ResponseCode.SERVER_ERROR.code, "thirdparty服务调用失败");
//	}
//
//
//}