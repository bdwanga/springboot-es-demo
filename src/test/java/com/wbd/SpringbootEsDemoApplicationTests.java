package com.wbd;

import com.wbd.service.EsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootEsDemoApplicationTests
{

    @Autowired
    private EsService esService;

    @Test
    public void contextLoads()
    {
    }

    @Test
    public void teEs()
    {
        esService.queryEsData("application_code", "192", 1, 10);
    }
}
