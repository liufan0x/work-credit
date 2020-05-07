package com.anjbo.service;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2018/5/17.
 */
public interface ExcelService {
    /**
     * 判断是否是Excel文件
     * @param fileName
     * @return
     * @throws Exception
     */
    public boolean isExcel(String fileName)throws Exception;

    /**
     * 右边框实线
     * @param work
     * @return
     */
    public CellStyle getBorderRight(Workbook work);

    /**
     * 右边框实线
     * @param style
     * @return
     */
    public void getBorderRight(CellStyle style);
    /**
     * 下侧边框
     * @param work
     * @return
     */
    public  CellStyle getBorderBottom(Workbook work);
    /**
     * 下侧边框
     * @param style
     * @return
     */
    public  void getBorderBottom(CellStyle style);

    /**
     * 上侧边框
     * @param work
     * @return
     */
    public  CellStyle getBorderTop(Workbook work);
    /**
     * 上侧边框
     * @param style
     * @return
     */
    public  void getBorderTop(CellStyle style);

    /**
     * 全边框
     */
    public CellStyle getBorderAll(Workbook work);
    /**
     * 全边框
     */
    public void getBorderAll(CellStyle style);
    /**
     * 设置字体样式
     * @param workbook 工作薄
     * @param fontSize 字体大小
     * @return HSSFCellStyle
     */
    public HSSFCellStyle createCellStyle(HSSFWorkbook workbook, short fontSize);

    public Workbook createReadWork(InputStream in)throws Exception;

    public HSSFWorkbook createWriteWork();

    public ByteArrayOutputStream copyStream(InputStream in)throws IOException;
}
