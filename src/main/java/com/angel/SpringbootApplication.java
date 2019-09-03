package com.angel;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringbootApplication {

    public static void main(String[] args)
    {
        SpringApplication app = new SpringApplication(SpringbootApplication.class);
        //设置控制台输出图案
        app.setBannerMode(Banner.Mode.CONSOLE);
        //通过是否允许通过命令行设置属性值
        app.setAddCommandLineProperties(false);
        app.run(args);
    }

}
