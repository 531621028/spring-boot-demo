package com.hkk.demo.utils.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.enums.HeadKindEnum;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.read.metadata.property.ExcelReadHeadProperty;
import com.hkk.demo.utils.JsonUtil;
import com.hkk.demo.utils.excel.EasyExcelReadTest.DemoData;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
public class DemoDataListener implements ReadListener<DemoData> {

    private final Map<Integer, String> headNameMap = new LinkedHashMap<>();
    private Map<Integer, Head> headMapData = new LinkedHashMap<>();

    @Override
    public void invokeHead(Map<Integer, ReadCellData<?>> headMap, AnalysisContext analysisContext) {
        if (analysisContext.readSheetHolder().getHeadRowNumber() >= 2) {
            buildHeadPrefixMap(headMap);
            if (analysisContext.readRowHolder().getRowIndex() == 0) {
                headMapData = analysisContext.readSheetHolder().excelReadHeadProperty().getHeadMap();
            }
            if (analysisContext.readRowHolder().getRowIndex() + 1 == analysisContext.readSheetHolder()
                .getHeadRowNumber()) {
                reBuildHead(analysisContext);
            }
        }
    }

    @Override
    public void invoke(DemoData data, AnalysisContext context) {
        log.info("invoke:{}", JsonUtil.toJsonStringOrEmpty(data));
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        log.info("所有数据解析完成！");
    }


    private void buildHeadPrefixMap(Map<Integer, ReadCellData<?>> headMap) {
        String precedingHeadName = null;
        for (Map.Entry<Integer, ReadCellData<?>> entry : headMap.entrySet()) {
            String headName = entry.getValue().getStringValue();
            final Integer columnIndex = entry.getKey();
            if (StringUtils.isBlank(headName)) {
                headName = precedingHeadName;
            }
            if (headNameMap.containsKey(columnIndex)) {
                headNameMap.put(columnIndex, String.join(",", headNameMap.get(columnIndex), headName));
            } else {
                headNameMap.put(columnIndex, headName);
            }
            precedingHeadName = headName;
        }
    }

    private void reBuildHead(AnalysisContext analysisContext) {
        if (!HeadKindEnum.CLASS.equals(analysisContext.currentReadHolder().excelReadHeadProperty().getHeadKind())) {
            return;
        }
        ExcelReadHeadProperty excelHeadPropertyData = analysisContext.readSheetHolder().excelReadHeadProperty();
        Map<Integer, Head> tmpHeadMap = new HashMap<>(headMapData.size() * 4 / 3 + 1);
        for (Map.Entry<Integer, Head> entry : headMapData.entrySet()) {
            Head headData = entry.getValue();
            if (Boolean.TRUE.equals(headData.getForceIndex()) || Boolean.TRUE.equals(!headData.getForceName())) {
                tmpHeadMap.put(entry.getKey(), headData);
                continue;
            }
            String joinHeadName = String.join(",", headData.getHeadNameList());
            for (Map.Entry<Integer, String> stringEntry : headNameMap.entrySet()) {
                if (stringEntry == null) {
                    continue;
                }
                String headString = stringEntry.getValue();
                Integer columnIndex = stringEntry.getKey();
                if (StringUtils.isEmpty(headString)) {
                    continue;
                }
                if (joinHeadName.equals(headString)) {
                    headData.setColumnIndex(columnIndex);
                    tmpHeadMap.put(columnIndex, headData);
                    break;
                }
            }
        }
        excelHeadPropertyData.setHeadMap(tmpHeadMap);
    }

}