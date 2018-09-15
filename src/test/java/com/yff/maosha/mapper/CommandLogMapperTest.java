package com.yff.maosha.mapper;

import com.yff.maosha.MaoshaApplication;
import com.yff.maosha.service.CommandLogService;
import com.yff.maosha.utils.CommandLogStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MaoshaApplication.class)
@MapperScan("com.yff.maosha.mapper")
public class CommandLogMapperTest {

    @Autowired
    private CommandLogService commandLogService;

    @Test
    public void updateLogStatusTest() {
        commandLogService.updateCommandLogStatus("f7261ed0b8d711e8baf11640bbeb3ac7",
                CommandLogStatus.SUCCESS);
    }
}
