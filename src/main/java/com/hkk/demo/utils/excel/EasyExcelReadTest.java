package com.hkk.demo.utils.excel;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.annotation.ExcelProperty;
import com.hkk.demo.utils.JsonUtil;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author kang
 * @date 2022/3/29
 */
@Slf4j
public class EasyExcelReadTest {

    public static void main(String[] args) {

        final List<DemoData> excelItems = EasyExcelFactory.read("C:\\Users\\KANG\\Desktop\\导入测试.xlsx")
            .registerReadListener(new DemoDataListener())
            .head(DemoData.class).sheet(1).doReadSync();
        log.info("excel items is {}", JsonUtil.toJsonStringOrEmpty(excelItems));
    }

    @Getter
    @Setter
    @EqualsAndHashCode
    public static class DemoData {

        // @ExcelProperty(index = 0, value = {"标题1", "字符串标题"})
        // private String string;
        // @ExcelProperty(index = 1, value = {"标题1", "数字标题"})
        // private String doubleData;
        // @ExcelProperty(index = 2, value = {"标题2", "字符串标题"})
        // private String date;
        // @ExcelProperty(index = 5, value = {"标题2", "数字标题"})
        // private String date2;
        @ExcelProperty(value = {"标题2", "字符串标题"})
        private String data3;
        @ExcelProperty(value = {"标题2", "数字标题"})
        private String data4;
        @ExcelProperty(value = {"标题1", "字符串标题"})
        private String data1;
        @ExcelProperty(value = {"标题1", "数字标题"})
        private String data2;
    }

}
