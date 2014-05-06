package com.gjw.export.test;


import java.io.File;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import com.gjw.export.SimpleExportUtil;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.junit.BeforeClass;
import org.junit.Test;


/**
 * Created by Administrator on 14-5-6.
 */
/**
 * 测试导出Excel 通用
 * @author guojw
 */
public class TestExporToExcel {

    private static List<Medicine> medicines =  null;
    private static List<Student> students =  null;
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        medicines = new ArrayList<Medicine>();
        students = new ArrayList<Student>();
        for(int i=100;  i < 200; i++){
            Student stu = new Student(i,"张学友","北京");
            students.add(stu);
        }

        for(int i=100;  i < 200; i++){
            Medicine m  = new Medicine("药品"+i,i+"药柜","ISO_cn_m"+i,30,567,"主治:伤风感冒",0.9,46);
            medicines.add(m);
        }
    }

    /**
     * 测试Annotation 方式
     * 注意：配置文件和对应的域型型应在同一个包中且命名格式为简单域模型名.ete.xml,ete:(export to excel)
     * 数据分析部分根据具体的业务生产
     */
    @Test
    public void testExportToExcelOfAnnotation() {

        try {
            //指定是数字列的精度并四舍五入(整形除外)
            SimpleExportUtil.PRECISION = 3;
            File outputFile = new File("E:\\students.xls");
            OutputStream fOut = new PrintStream(outputFile);
            // 把相应的Excel 工作簿存盘
            HSSFWorkbook workbook = SimpleExportUtil.exportToExcel(students, "学生信息表");
            //得到第一个sheet
            HSSFSheet sheet = workbook.getSheetAt(0);
            //原始sheet 中总的行数
            int dadaRowNum = sheet.getLastRowNum();
            //得到行高
            float lineHeght = sheet.getRow(dadaRowNum).getHeightInPoints();
            //得到列的样式
            HSSFCellStyle cellStyle =  sheet.getRow(dadaRowNum).getCell(0).getCellStyle();
            //学生总人数
            int studentCount = students.size();
            //获取已有的行数，加1再出新行
            HSSFRow totalRow = sheet.createRow(dadaRowNum + 1);
            totalRow.setHeightInPoints(lineHeght);

            //创建列
            HSSFCell cell = totalRow.createCell(0);
            cell.setCellStyle(cellStyle);
            cell.setCellValue("学生总人数");

            cell = totalRow.createCell(1);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(studentCount);
            cell = totalRow.createCell(2);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(studentCount);
            //合并列参数：开始行，结束行，开始列，结束列
            sheet.addMergedRegion(new CellRangeAddress(dadaRowNum+1, dadaRowNum+1, 1, 2));

            workbook.write(fOut);
            fOut.flush();
            // 操作结束，关闭文件
            fOut.close();
        } catch (Exception e) {
            e.getStackTrace();
        }

    }

    /**
     * 测试xml配置文件方式
     * com/hyl/utility/model/Medicine.ete.xml
     * 注意：配置文件和对应的域型型应在同一个包中且命名格式为:简单域模型名.ete.xml,ete:(export to excel)
     * 数据分析部分根据具体的业务生产
     */
    @Test
    public void testExportToExcelOfXMl() {

        try {
            File outputFile = new File("E:\\medicines.xls");
            OutputStream fOut = new PrintStream(outputFile);
            // 把相应的Excel 工作簿存盘
            SimpleExportUtil.exportToExcel(medicines, "药品信息表").write(fOut);
            fOut.flush();
            // 操作结束，关闭文件
            fOut.close();
        } catch (Exception e) {
            e.getStackTrace();
        }

    }
}
