import query from './request';

function apiAxios(method,url,data,params) {
  let dData = data;
//if(data && Util.getUserInfo().platformType){
//  Object.assign(dData, data, Util.getUserInfo());
//}
  return query({
    method: method,
    url,
    data: dData,//body data
    params: params,//url param
  })
}

export default apiAxios