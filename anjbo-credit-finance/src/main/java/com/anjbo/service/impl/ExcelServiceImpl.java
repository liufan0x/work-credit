package com.anjbo.service.impl;

import com.anjbo.service.ExcelService;
import com.anjbo.utils.StringUtil;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2018/5/17.
 */
@Service
public class ExcelServiceImpl implements ExcelService {

    /**
     * 判断是否是Excel文件
     *
     * @param fileName
     * @return
     * @throws Exception
     */
    @Override
    public boolean isExcel(String fileName) throws Exception {
        if(StringUtil.isNotBlank(fileName)
                &&fileName.matches("^.+\\.(?i)((xls)|(xlsx))$"))
            return true;
        return false;
    }

    /**
     * 右边框实线
     *
     * @param work
     * @return
     */
    @Override
    public CellStyle getBorderRight(Workbook work) {
        CellStyle style = work.createCellStyle();
        style.setBorderRight(CellStyle.BORDER_THIN);
        return style;
    }

    /**
     * 右边框实线
     *
     * @param style
     * @return
     */
    @Override
    public void getBorderRight(CellStyle style) {
        style.setBorderRight(CellStyle.BORDER_THIN);
    }

    /**
     * 下侧边框
     *
     * @param work
     * @return
     */
    @Override
    public CellStyle getBorderBottom(Workbook work) {
        CellStyle style = work.createCellStyle();
        style.setBorderBottom(CellStyle.BORDER_THIN);
        return style;
    }

    /**
     * 下侧边框
     *
     * @param style
     * @return
     */
    @Override
    public void getBorderBottom(CellStyle style) {
        style.setBorderBottom(CellStyle.BORDER_THIN);
    }

    /**
     * 上侧边框
     *
     * @param work
     * @return
     */
    @Override
    public CellStyle getBorderTop(Workbook work) {
        CellStyle style = work.createCellStyle();
        style.setBorderTop(CellStyle.BORDER_THIN);
        return style;
    }

    /**
     * 上侧边框
     *
     * @param style
     * @return
     */
    @Override
    public void getBorderTop(CellStyle style) {
        style.setBorderTop(CellStyle.BORDER_THIN);
    }

    /**
     * 全边框
     *
     * @param work
     */
    @Override
    public CellStyle getBorderAll(Workbook work) {
        CellStyle style = work.createCellStyle();
        style.setTopBorderColor(HSSFColor.BLACK.index);
        style.setRightBorderColor(HSSFColor.BLACK.index);
        style.setBottomBorderColor(HSSFColor.BLACK.index);
        style.setLeftBorderColor(HSSFColor.BLACK.index);

        style.setBorderTop(HSSFCellStyle.BORDER_THIN); // 实线右边框
        style.setBorderRight(HSSFCellStyle.BORDER_THIN); // 实线右边框
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 实线右边框
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN); // 实线右边框
        return style;
    }

    /**
     * 全边框
     *
     * @param style
     */
    @Override
    public void getBorderAll(CellStyle style) {

        style.setTopBorderColor(HSSFColor.BLACK.index);
        style.setRightBorderColor(HSSFColor.BLACK.index);
        style.setBottomBorderColor(HSSFColor.BLACK.index);
        style.setLeftBorderColor(HSSFColor.BLACK.index);

        style.setBorderTop(HSSFCellStyle.BORDER_THIN); // 实线右边框
        style.setBorderRight(HSSFCellStyle.BORDER_THIN); // 实线右边框
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 实线右边框
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN); // 实线右边框
    }

    /**
     * 设置字体样式
     *
     * @param workbook 工作薄
     * @param fontSize 字体大小
     * @return HSSFCellStyle
     */
    @Override
    public HSSFCellStyle createCellStyle(HSSFWorkbook workbook, short fontSize) {
        HSSFCellStyle style = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        font.setFontHeightInPoints(fontSize);						//设置字体大小
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);				//设置水平居中
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);	//设置垂直居中
        style.setFont(font);
        return style;
    }

    @Override
    public Workbook createReadWork(InputStream in)throws Exception{
        Workbook workbook = WorkbookFactory.create(in);
        return workbook;
    }
    @Override
    public HSSFWorkbook createWriteWork() {
        HSSFWorkbook workbook = new HSSFWorkbook();
        return workbook;
    }

    public ByteArrayOutputStream copyStream(InputStream in)throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try{
            int bufferSize = 4096;
            byte[] buffer = new byte[bufferSize];
            int len = 0;
            while ((len=in.read(buffer))!=-1){
                out.write(buffer,0,len);
            }
            out.flush();
        }catch (Exception e){
            throw new IOException(e);
        }
        return out;
    }
}
