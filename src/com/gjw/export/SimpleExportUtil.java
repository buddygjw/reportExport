package com.gjw.export;

import java.io.File;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Created by Administrator on 14-5-6.
 */
public class SimpleExportUtil {

    private static SimpleExportUtil tool = new SimpleExportUtil();
    public static int PRECISION = 3;

    private void createTitileRow(HSSFWorkbook workbook, HSSFRow row, String[] titles, HSSFSheet sheet)
    {
        int length = titles.length;
        HSSFCellStyle style = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        font.setColor((short) 12);
        font.setBoldweight((short)14);
        style.setFont(font);
        style.setFillBackgroundColor((short)13);
        for (int i = 0; i < length; ++i) {
            sheet.setColumnWidth(i, 2560);
            HSSFCell cell = row.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(style);
        }
    }

    private void createTitleRow(HSSFWorkbook workbook, HSSFRow row, List<Entity> entityList, HSSFSheet sheet)
    {
        HSSFCellStyle style = createHeadStyle(workbook);

        HSSFFont font = workbook.createFont();
        font.setColor((short)12);
        font.setBoldweight((short)700);
        style.setFont(font);
        style.setFillBackgroundColor((short)13);

        int i = 0;
        for (Iterator localIterator = entityList.iterator(); localIterator.hasNext(); ) { Entity e = (Entity)localIterator.next();
            sheet.setColumnWidth(i, 3200);
            HSSFCell cell = row.createCell(i);
            cell.setCellValue(e.getText());
            cell.setCellStyle(style);

            ++i;
        }
    }

    private HSSFCellStyle createHeadStyle(HSSFWorkbook workbook)
    {
        HSSFCellStyle style = workbook.createCellStyle();
        style.setAlignment((short)2);
        style.setVerticalAlignment((short)1);
        style.setFillForegroundColor((short)55);
        style.setFillPattern((short)1);
        style.setBorderBottom((short)1);
        style.setBorderLeft((short)1);
        style.setBorderRight((short)1);
        style.setBorderTop((short)1);
        style.setWrapText(true);
        return style;
    }

    private HSSFCellStyle createCellStyle(HSSFWorkbook workbook)
    {
        HSSFCellStyle style = workbook.createCellStyle();
        style.setAlignment((short)2);
        style.setVerticalAlignment((short)1);
        style.setFillForegroundColor((short)9);
        HSSFFont font = workbook.createFont();
        font.setColor((short)8);
        font.setFontHeightInPoints((short)12);
        style.setWrapText(true);
        return style;
    }

    private List<Entity> parseXML(Class<? extends Object> clazz)
            throws Exception
    {
        String classPath = clazz.getName();
        String xmlPath = File.separator + classPath.replace(".", File.separator) + ".ete.xml";
        List list = new ArrayList();
        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(super.getClass().getClassLoader().getResourceAsStream(xmlPath));
        NodeList nodeList = doc.getElementsByTagName("property");

        for (int i = 0; i < nodeList.getLength(); ++i)
        {
            Element element = (Element)nodeList.item(i);
            Entity e = new Entity();
            e.setNeme(element.getAttribute("name"));
            e.setText(element.getAttribute("text"));
            e.setType(element.getAttribute("type"));
            list.add(e);
        }
        return list;
    }

    private List<Entity> parseAnnotation(Class<? extends Object> clazz)
            throws ClassNotFoundException
    {
        List entities = new ArrayList();
        Field[] fields = clazz.getDeclaredFields();

        Field[] arrayOfField1 = fields; int j = fields.length; for (int i = 0; i < j; ++i) { Field field = arrayOfField1[i];
        Entity e = new Entity();
        boolean hasAnnotation = field.isAnnotationPresent(Cell.class);

        if (hasAnnotation) {
            Cell annotation = (Cell)field.getAnnotation(Cell.class);
            e.setText(annotation.title());
            e.setType(field.getType().getName());
            e.setNeme(field.getName());
            entities.add(e);
        }
    }
        return entities;
    }

    public static HSSFWorkbook exportToExcel(List<? extends Object> list, String sheetName)
            throws Exception
    {
        BigDecimal bigDecimal = null;
        MathContext mathContext = new MathContext(PRECISION, RoundingMode.HALF_UP);
        Class clazz = list.get(0).getClass();

        List entities = tool.parseAnnotation(clazz);
        if ((entities == null) || (entities.size() == 0)) {
            entities = tool.parseXML(clazz);
        }

        HSSFWorkbook workbook = new HSSFWorkbook();

        HSSFSheet sheet = workbook.createSheet(sheetName);

        HSSFRow titleRow = sheet.createRow(0);
        titleRow.setHeightInPoints(25.0F);
        tool.createTitleRow(workbook, titleRow, entities, sheet);

        int rowNum = 1;
        for (int i = 0; i < list.size(); ++i) {
            HSSFRow row = sheet.createRow(rowNum++);
            row.setHeightInPoints(23.0F);
            HSSFCellStyle cellStyle = tool.createCellStyle(workbook);

            int colNum = 0;
            for (Iterator localIterator = entities.iterator(); localIterator.hasNext(); ) { Entity entity = (Entity)localIterator.next();
                String type = entity.getType();
                String name = entity.getNeme();
                HSSFCell cell = row.createCell(colNum);

                cell.setCellStyle(cellStyle);

                Object obj = null;
                if (("boolean".equals(type)) || (type.equals("java.lang.Boolean")))
                    obj = MethodTool.executeMethod(list.get(i), MethodTool.returnIsBooleanMethodName(name));
                else {
                    obj = MethodTool.executeMethod(list.get(i), MethodTool.returnGetMethodName(name));
                }

                if (obj != null)
                    if (type.endsWith("String"))
                        cell.setCellValue((String)obj);
                    else if (("int".equals(type)) || (type.equals("java.lang.Integer")))
                        cell.setCellValue(((Integer)obj).intValue());
                    else if (("double".equals(type)) || (type.equals("java.lang.Double"))) {
                        bigDecimal = new BigDecimal(((Double)obj).doubleValue(), mathContext);
                        cell.setCellValue(bigDecimal.doubleValue());
                    } else if (("boolean".equals(type)) || (type.equals("java.lang.Boolean")))
                        cell.setCellValue(((Boolean)MethodTool.executeMethod(list.get(i), MethodTool.returnIsBooleanMethodName(name))).booleanValue());
                    else if (("float".equals(type)) || (type.equals("java.lang.Float"))) {
                        bigDecimal = new BigDecimal(((Float)obj).floatValue(), mathContext);
                        cell.setCellValue(bigDecimal.doubleValue());
                    } else if ((type.equals("java.util.Date")) || (type.endsWith("Date")))
                        cell.setCellValue((Date)obj);
                    else if (type.equals("java.util.Calendar"))
                        cell.setCellValue((Calendar)obj);
                    else if (("char".equals(type)) || (type.equals("java.lang.Character")))
                        cell.setCellValue(obj.toString());
                    else if (("long".equals(type)) || (type.equals("java.lang.Long")))
                        cell.setCellValue(((Long)obj).longValue());
                    else if (("short".equals(type)) || (type.equals("java.lang.Short")))
                        cell.setCellValue(((Short)obj).shortValue());
                    else if (type.equals("java.math.BigDecimal")) {
                        bigDecimal = (BigDecimal)obj;
                        bigDecimal = new BigDecimal(bigDecimal.doubleValue(), mathContext);
                        cell.setCellValue(bigDecimal.doubleValue());
                    }
                    else throw new Exception("data type errored!");


                ++colNum;
            }
        }

        return workbook;
    }
}
