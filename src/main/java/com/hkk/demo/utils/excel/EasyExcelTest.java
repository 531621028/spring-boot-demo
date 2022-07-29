package com.hkk.demo.utils.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.util.ListUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.hkk.demo.utils.JsonUtil;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.RandomUtils;

/**
 * @author kang
 * @date 2022/3/29
 */
public class EasyExcelTest {

    public static final List<String> STRINGS = Lists.newArrayList(Splitter.on(" ")
        .split("积 秧 秩 称 秘 透 笔 笑 笋 债 借 值 倚 倾 倒 倘 俱 倡 候 俯 倍 倦 健 臭 射 躬 息 徒 徐 舰 舱 般 航 途 拿 爹 爱"));

    public static void main(String[] args) throws IOException {
        final Map<String, Object> map = JsonUtil.toObject(new DemoData("标题1", 123.4d, new Date(), "忽略1"),
            new TypeReference<Map<String, Object>>() {
            });
        System.out.println(map);
        dynamicHeadWrite();
    }


    public static void dynamicHeadWrite() throws IOException {
        File directory = new File("/tmp/excel");
        if (!directory.exists()) {
            boolean success = directory.mkdirs();
            if (success) {
                System.out.println("success");
            }

        }
        File excelFile = File.createTempFile("excel", ".xlsx", directory);

        EasyExcel.write(excelFile)
            // 这里放入动态头
            .head(demoHead()).sheet("模板")
            // 当然这里数据也可以用 List<List<String>> 去传入
            .doWrite(demoMapData());
        // List<List<String>> head = head();
        // List<List<String>> data = data();
        // Stopwatch stopwatch = Stopwatch.createStarted();
        // EasyExcel.write(excelFile)
        //     .inMemory(true)
        //     .excelType(ExcelTypeEnum.XLSX)
        //     // 这里放入动态头
        //     .head(head).sheet("模板")
        //     .registerWriteHandler(new WaterMarkStrategy())
        //     .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
        //     // 当然这里数据也可以用 List<List<String>> 去传入
        //     .doWrite(data);
        // System.out.println("耗时" + stopwatch.elapsed(TimeUnit.SECONDS));
    }

    // private static Collection<DemoData> demoData() {
    //     return Lists.newArrayList(new DemoData("标题1", 123.4d, new Date(), "忽略1"),
    //         new DemoData("标题2", 123.4d, new Date(), "忽略2"));
    // }

    private static Collection<Map<Integer, String>> demoMapData() {
        Map<Integer, String> item = new LinkedHashMap<>();
        item.put(0, "标题1");
        item.put(1, "标题2");
        item.put(2, "标题3");
        item.put(3, "标题4");
        return Lists.newArrayList(item);
    }

    private static List<List<String>> head() {
        List<List<String>> head = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            head.add(Lists.newArrayList(randomValue()));
        }
        return head;
    }

    private static List<List<String>> demoHead() {
        List<List<String>> head = new ArrayList<>();
        head.add(Lists.newArrayList("标题", "字符串标题"));
        head.add(Lists.newArrayList("标题", "数字标题"));
        head.add(Lists.newArrayList("标题", "日期标题"));
        head.add(Lists.newArrayList("标题2", "字符串标题"));
        return head;
    }

    private static String randomValue() {
        StringBuilder sb = new StringBuilder();
        int nextInt = RandomUtils.nextInt(1, 15);
        for (int i = 0; i < nextInt; i++) {
            sb.append(STRINGS.get(RandomUtils.nextInt(1, STRINGS.size())));
        }
        return sb.toString();
    }

    private static List<List<String>> data() {
        List<List<String>> data = ListUtils.newArrayList();
        for (int i = 0; i < 50000; i++) {
            List<String> row = new ArrayList<>(50);
            for (int j = 0; j < 50; j++) {
                row.add(randomValue());
            }
            data.add(row);
        }
        return data;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class DemoData {

        @ExcelProperty(value = {"标题", "字符串标题"})
        private String string;
        @ExcelProperty(value = {"标题", "数字标题"})
        private Double doubleData;
        @ExcelProperty(value = {"标题", "日期标题"})
        private Date date;
        // /**
        //  * 忽略这个字段
        //  */
        @ExcelIgnore
        @ExcelProperty(value = {"标题", "忽略"})
        private String ignore;
    }

}
