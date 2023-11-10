package com.mxs.sampleservice.service.impl;

import com.mxs.sampleservice.service.TemplateService;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Slf4j
@Service
public class TemplateServiceImpl implements TemplateService, InitializingBean {
    private static final String TEMPLATE_PATH = "/";
    private static final Map<String, Template> TEMPLATE_CACHE = new HashMap();
    private Configuration configuration;

    public TemplateServiceImpl() {
    }

    public String process(String templateFullPath, Object parameters) {
        try {
            Template e = (Template)TEMPLATE_CACHE.get(templateFullPath);
            if(e == null) {
                e = this.configuration.getTemplate(templateFullPath);
                TEMPLATE_CACHE.put(templateFullPath, e);
            }

            return this.doProcess(e, parameters);
        } catch (Exception var4) {
            log.error("TemplateServiceImpl process error:", var4);
            throw new RuntimeException(var4);
        }
    }

    public String processByContent(String content, Object parameters) {
        try {
            Template e = new Template((String)null, new StringReader(content), this.configuration);
            return this.doProcess(e, parameters);
        } catch (Exception var4) {
            log.error("TemplateServiceImpl processByContent error:", var4);
            throw new RuntimeException(var4);
        }
    }

    private String doProcess(Template template, Object parameters) throws TemplateException, IOException {
        StringWriter stringWriter = new StringWriter();
        template.process(parameters, stringWriter);
        return stringWriter.toString();
    }

    public void afterPropertiesSet() throws Exception {
        this.configuration = new Configuration();
        this.configuration.setTemplateLoader(new ClassTemplateLoader(TemplateServiceImpl.class, "/"));
        this.configuration.setEncoding(Locale.getDefault(), "UTF-8");
        this.configuration.setDateFormat("yyyy-MM-dd HH:mm:ss");
    }
}
