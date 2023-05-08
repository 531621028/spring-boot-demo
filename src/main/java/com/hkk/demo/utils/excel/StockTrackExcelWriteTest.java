package com.hkk.demo.utils.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ContentStyle;
import com.alibaba.excel.enums.poi.HorizontalAlignmentEnum;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.util.MapUtils;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillConfig;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import com.hkk.demo.utils.JsonUtil;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;

/**
 * @author kang
 * @date 2022/2/11
 */
@Slf4j
public class StockTrackExcelWriteTest {

    public static void main(String[] args) {
        complexFill();
    }


    public static void complexFill() {
        // 模板注意 用{} 来表示你要用的变量 如果本来就有"{","}" 特殊字符 用"\{","\}"代替
        // {} 代表普通变量 {.} 代表是list的变量
        String templateFileName = getPath() + "demo" + File.separator + "fill" + File.separator + "StockTrackTemplate.xlsx";

        String fileName = getPath() + "complexFill" + System.currentTimeMillis() + ".xlsx";
        // 方案1
        try (ExcelWriter excelWriter = EasyExcel.write(fileName).withTemplate(templateFileName).build()) {
            WriteSheet projectSheet = EasyExcel.writerSheet(0).head(ProjectData.class).build();
            excelWriter.write(projectData(), projectSheet);
            WriteSheet totalSheet = EasyExcel.writerSheet(1).head(ProjectData.class).build();
            excelWriter.write(projectData(), totalSheet);
            WriteSheet deptSheet = EasyExcel.writerSheet(2).build();
            excelWriter.fill(complexData(), deptSheet);
            Map<String, Object> map = MapUtils.newHashMap();
            map.put("stock", "HKD 10");
            map.put("date", "2023-04-01");
            map.put("month", "202304");
            excelWriter.fill(map, deptSheet);

        }
    }


    public static String getPath() {
        return Objects.requireNonNull(StockTrackExcelWriteTest.class.getResource("/")).getPath();
    }

    private static List<Map<String, Object>> complexData() {
        List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Map<String, Object> row = new HashMap<>();
            for (int j = 1; j < 18; j++) {
                row.put("date" + j, i + "value" + j);
            }
            list.add(row);
        }
        return list;
    }

    private static List<ProjectData> projectData() {
        List<ProjectData> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ProjectData row = new ProjectData();
            row.setProjectName("项目" + i);
            row.setYearTotal("年度总预算" + i);
            row.setGrantedStock("已授予股数" + i);
            row.setUseRate("使用率" + i);
            list.add(row);
        }
        return list;
    }

    @Getter
    @Setter
    @ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER)
    public static class ProjectData {

        @ExcelProperty({"当年数据监测", "项目"})
        private String projectName;
        @ExcelProperty({"当年数据监测", "年度总预算"})
        private String yearTotal;
        @ExcelProperty({"当年数据监测", "已授予股数"})
        private String grantedStock;
        @ExcelProperty({"当年数据监测", "使用率"})
        private String useRate;

    }

}
