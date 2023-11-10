const path = require('path')
const webpack = require('webpack');

const resolve = dir => {
  return path.join(__dirname, dir)
}

// 项目部署基础
// 默认情况下，我们假设你的应用将被部署在域的根目录下,
// 例如：https://www.my-app.com/
// 默认：'/'
// 如果您的应用程序部署在子路径中，则需要在这指定子路径
// 例如：https://www.foobar.com/my-app/
// 需要将它改为'/my-app/'
// iview-admin线上演示打包路径： https://file.iviewui.com/admin-dist/
const BASE_URL = process.env.NODE_ENV === 'production'
  ? '/'
  : '/'
  
let target = 'http://120.132.97.11:5555/'

module.exports = {
  publicPath: BASE_URL,
  lintOnSave: false,
  css: {
    loaderOptions:{
      less: {
        javascriptEnabled: true
      }
    }
  },
  chainWebpack: config => {
    config.resolve.alias
      .set('@', resolve('src')) // key,value自行定义，比如.set('@@', resolve('src/components'))
      .set('_c', resolve('src/components'))
    config.plugin('provide')
	    .use(webpack.ProvidePlugin, [{
	    		_: "lodash",
					moment:"moment"
	    }]);
  },
  // 设为false打包时不生成.map文件
  productionSourceMap: false,
  devServer: {
    proxy: {
    	'/api': {
				target,
				changeOrigin: true
			},
		}
	}
}