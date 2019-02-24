package com.env.config.client.configclient;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.net.*;
import java.nio.file.StandardCopyOption;


@RestController
public class WelcomeController
{

    @Value("${app.service-name}")
    private String serviceName;

    @Value("${app.cws.keystore}")
    private String cwsKeyStore;

    @Value("${spring.cloud.config.uri}")
    private String cloudConfigUri;

    @Value("${spring.application.name}")
    private String appName;

    @Value("${app.greet.message}")
    private String message;



    @GetMapping("/service")
    public String getServiceName() throws Exception{

        try{
            HttpURLConnection con = (HttpURLConnection) new URL(cloudConfigUri + "/"  + appName + "/default/files/" + cwsKeyStore).openConnection();
            con.setRequestProperty("Accept", MediaType.APPLICATION_OCTET_STREAM_VALUE);
            InputStream is = con.getInputStream();
            File targetFile = new File(cwsKeyStore);
            java.nio.file.Files.copy(is,targetFile.toPath(),StandardCopyOption.REPLACE_EXISTING);
            IOUtils.closeQuietly(is);
            con.disconnect();
        } finally {

        }

        return "service name [" + this.serviceName +  this.message + "]";
    }





}
