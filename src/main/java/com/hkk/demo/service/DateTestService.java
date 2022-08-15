package com.hkk.demo.service;

import com.hkk.demo.domain.DateTest;
import com.hkk.demo.mapper.DateTestMapper;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户服务类
 *
 * @author hukangkang
 * @since 2021/10/27
 */
@Service
public class DateTestService {

    @Autowired
    private DateTestMapper dateTestMapper;

    public int testService() {
        DateTest dateTest = new DateTest();
        // LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toEpochSecond();
        dateTest.setDate(new Date(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toEpochSecond() * 1000));
        // dateTest.setDate("2022-08-09");

        dateTestMapper.selectById(20);
        return dateTestMapper.insert(dateTest);
        // return 1;
    }


}
