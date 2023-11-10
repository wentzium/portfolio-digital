说明：  
    一共包括8个服务，分别是：  
    注册中心mxs-eureka，端口：8761    
    配置中心mxs-config，端口：6000  
    鉴权服务mxs-auth-service，端口：9000  
    用户服务mxs-user-service，端口：9001  
    样本服务mxs-sample-service，端口：9002  
    熔断监控mxs-turbine，端口：7000  
    服务调用追踪mxs-zipkin-server ，端口：9411 

运行起来项目有两种方式  
    1、本机启动所有项目
       启动注册中心mxs-eureka
       启动配置中心mxs-config
       启动鉴权服务mxs-auth-service
           项目启动参数配置Configuration --> Environment --> Program Argument 输入：   --spring.profiles.active=dev
       启动用户服务mxs-user-service
           项目启动参数配置Configuration --> Environment --> Program Argument 输入：   --spring.profiles.active=dev
       启动样本服务mxs-sample-service
           项目启动参数配置Configuration --> Environment --> Program Argument 输入：   --spring.profiles.active=dev
       启动熔断监控服务mxs-turbine（可选）
           项目启动参数配置Configuration --> Environment --> Program Argument 输入：   --spring.profiles.active=dev
       启动调用追踪服务mxs-zipkin-server（可选）
           项目启动参数配置Configuration --> Environment --> Program Argument 输入：   --spring.profiles.active=dev
    2、本机只运行需要的项目
       启动用户服务mxs-user-service
           项目启动参数配置Configuration --> Environment --> Program Argument 输入：   --spring.cloud.config.uri=http://120.132.97.11:8888
       启动样本服务mxs-sample-service
           项目启动参数配置Configuration --> Environment --> Program Argument 输入：   --spring.cloud.config.uri=http://120.132.97.11:8888

需要自动刷新的属性， 类上面加上注解@RefreshScope，如果不是属性，则需要写一个配置类，加上@RefreshScope注解