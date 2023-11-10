package com.mxs.sampleservice.util;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

/**
 *
 *  Created by j.yang on 2019-07-25
 * 
 */

public class Generator {

    public static void main(String[] args) {
        String packageName = "com.mxs.sampleservice";
        //auth -> UserService, 设置成true: auth -> IUserService
        boolean serviceNameStartWithI = false;
        generateByTables(serviceNameStartWithI, packageName, "zhangyingxuan", "leica", "t_check_item");

        System.out.println("completed...");
    }

    /**
     * @param serviceNameStartWithI
     * @param packageName           包名
     * @param author                作者
     * @param database              数据库名
     * @param tableNames            表名
     */
    private static void generateByTables(boolean serviceNameStartWithI, String packageName, String author, String database, String... tableNames) {
        GlobalConfig config = new GlobalConfig();
        String dbUrl = "jdbc:mysql://120.132.97.11:3306/" + database + "?useUnicode=true&characterEncoding=utf8&autoReconnect=true&useSSL=false";
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.MYSQL)
                .setUrl(dbUrl)
                .setUsername("leica")
                .setPassword("Leica_123")
                .setDriverName("com.mysql.jdbc.Driver");
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig
                .setCapitalMode(true)
                .setEntityLombokModel(true)
                .setDbColumnUnderline(true)
                .setNaming(NamingStrategy.underline_to_camel)
//              .setSuperMapperClass("cn.saytime.mapper.BaseMapper")
                //修改替换成你需要的表名，多个表名传数组
                .setInclude(tableNames)
                .setTablePrefix("t_");
        config.setActiveRecord(false)
                .setAuthor(author)
                .setOutputDir("C:\\Users\\C.SY\\Desktop\\GEN")
                .setFileOverride(true)
                .setEnableCache(false);
        if (!serviceNameStartWithI) {
            config.setServiceName("%sService");
        }
        new AutoGenerator().setGlobalConfig(config)
                .setDataSource(dataSourceConfig)
                .setStrategy(strategyConfig)
                .setPackageInfo(
                        new PackageConfig()
                                .setParent(packageName)
                                .setController("web")
                                .setEntity("entity")
                                .setMapper("mapper")
                                .setService("service")
                                .setServiceImpl("service.impl")
                                .setXml("mybatis.mappers")
                ).execute();
    }

}