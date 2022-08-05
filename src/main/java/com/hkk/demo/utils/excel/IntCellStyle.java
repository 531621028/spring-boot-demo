package com.hkk.demo.utils.excel;

import com.alibaba.excel.constant.OrderConstant;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.handler.context.CellWriteHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Workbook;

@Slf4j
public class IntCellStyle implements CellWriteHandler {

    @Override
    public int order() {
        return OrderConstant.DEFAULT_DEFINE_STYLE;
    }

    @Override
    public void afterCellCreate(CellWriteHandlerContext context) {
        //设置单元格格式为文本
        // Workbook workbook = context.getWriteSheetHolder().getSheet().getWorkbook();
        // CellStyle cellStyle = workbook.createCellStyle();
        // final Cell cell = context.getCell();
        // if (NumberUtils.isCreatable(cell.getStringCellValue())) {
        //     DataFormat dataFormat = workbook.createDataFormat();
        //     cellStyle.setDataFormat(dataFormat.getFormat("#,##0"));
        //     cell.setCellStyle(cellStyle);
        // }
    }

    @Override
    public void afterCellDispose(CellWriteHandlerContext context) {
        Workbook workbook = context.getWriteSheetHolder().getSheet().getWorkbook();
        final Cell cell = context.getCell();
        if (CellType.STRING.equals(cell.getCellType()) && NumberUtils.isCreatable(cell.getStringCellValue())) {
            CellStyle cellStyle = workbook.createCellStyle();
            if (StringUtils.contains(cell.getStringCellValue(), ".")) {
                cell.setCellValue(NumberUtils.createDouble(cell.getStringCellValue()));
                cellStyle.setDataFormat((short) BuiltinFormats.getBuiltinFormat("0.00"));
            } else {
                cell.setCellValue(NumberUtils.createLong(cell.getStringCellValue()));
                cellStyle.setDataFormat((short) BuiltinFormats.getBuiltinFormat("0"));
            }
            cell.setCellStyle(cellStyle);
        }
    }

}