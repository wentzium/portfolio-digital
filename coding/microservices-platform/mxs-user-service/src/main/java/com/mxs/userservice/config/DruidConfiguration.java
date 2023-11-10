package com.mxs.userservice.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author zh
 * @ClassName cn.saytime.config.DruidConfiguration
 * @Description
 */
@Configuration
public class DruidConfiguration {

    @Bean(destroyMethod = "close", initMethod = "init")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource druidDataSource() {
//        DruidDataSource druidDataSource = new DruidDataSource();
//        druidDataSource.setBreakAfterAcquireFailure(true);
//        return druidDataSource;
        return new DruidDataSource();
    }

//
//  /**
//   * 注册一个StatViewServletpackage com.mxsoft.tiger.user.config;
//
//import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
//import org.mybatis.spring.annotation.MapperScan;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * @author zh
// * @ClassName cn.saytime.config.MybatisConfiguration
// * @Description
// */
//@Configuration
//@MapperScan("com.mxsoft.tiger.user.mapper*")
//public class MybatisConfiguration {
//
//    /**
//    * 分页插件，自动识别数据库类型
//    * 多租户，请参考官网【插件扩展】
//    */
//    @Bean
//    public PaginationInterceptor paginationInterceptor() {
//        return new PaginationInterceptor();
//    }
//
//   /*
//    * oracle数据库配置JdbcTypeForNull
//    * 参考：https://gitee.com/baomidou/mybatisplus-boot-starter/issues/IHS8X
//    不需要这样配置了，参考 yml:
//    mybatis-plus:
//      confuguration
//        dbc-type-for-null: 'null'
//   @Bean
//   public ConfigurationCustomizer configurationCustomizer(){
//       return new MybatisPlusCustomizers();
//   }
//
//   class MybatisPlusCustomizers implements ConfigurationCustomizer {
//
//       @Override
//       public void customize(org.apache.ibatis.session.Configuration configuration) {
//           configuration.setJdbcTypeForNull(JdbcType.NULL);
//       }
//   }
//   */
//
//}
//   *
//   * @return
//   */
//  @Bean
//  public ServletRegistrationBean druidStatViewServlet() {
//      //org.springframework.boot.context.embedded.ServletRegistrationBean提供类的进行注册.
//      ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
//
//      //添加初始化参数：initParams
//      //白名单：
//      servletRegistrationBean.addInitParameter("allow", "127.0.0.1");
//      //IP黑名单 (存在共同时，deny优先于allow) : 如果满足deny的话提示:Sorry, you are not permitted to view this page.
////        servletRegistrationBean.addInitParameter("deny", "192.168.1.73");
//      //登录查看信息的账号密码.
//      servletRegistrationBean.addInitParameter("loginUsername", "admin");
//      servletRegistrationBean.addInitParameter("loginPassword", "123456");
//      //是否能够重置数据.
//      servletRegistrationBean.addInitParameter("resetEnable", "false");
//      return servletRegistrationBean;
//  }
//
//  /**
//   * 注册一个：filterRegistrationBean
//   *
//   * @return
//   */
//  @Bean
//  public FilterRegistrationBean druidStatFilter() {
//
//      FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
//
//      //添加过滤规则.
//      filterRegistrationBean.addUrlPatterns("/*");
//
//      //添加不需要忽略的格式信息.
//      filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
//      return filterRegistrationBean;
//  }
}