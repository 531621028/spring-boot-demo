package com.hkk.demo.utils.excel;

import com.alibaba.excel.constant.OrderConstant;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.write.handler.context.CellWriteHandlerContext;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.DefaultStyle;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;

@Slf4j
public class DemoHeadCellStyle extends DefaultStyle {

    @Override
    public int order() {
        return OrderConstant.DEFAULT_DEFINE_STYLE;
    }

    public DemoHeadCellStyle() {
        super();
    }

    @Override
    protected void setHeadCellStyle(CellWriteHandlerContext context) {
        WriteCellData<?> cellData = context.getFirstCellData();
        WriteCellStyle.merge(customerCellStyle(), cellData.getOrCreateStyle());
    }

    private WriteCellStyle customerCellStyle() {
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        headWriteCellStyle.setWrapped(true);
        headWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        headWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        headWriteCellStyle.setLocked(true);
        headWriteCellStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
        headWriteCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headWriteCellStyle.setBorderTop(BorderStyle.THIN);
        headWriteCellStyle.setBorderBottom(BorderStyle.THIN);
        headWriteCellStyle.setBorderLeft(BorderStyle.THICK);
        headWriteCellStyle.setBorderRight(BorderStyle.THICK);
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setFontName("宋体");
        headWriteFont.setFontHeightInPoints((short) 14);
        headWriteFont.setBold(true);
        headWriteCellStyle.setWriteFont(headWriteFont);
        return headWriteCellStyle;
    }

}