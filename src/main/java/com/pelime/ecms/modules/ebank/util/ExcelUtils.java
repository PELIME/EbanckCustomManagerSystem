package com.pelime.ecms.modules.ebank.util;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelUtils.class);
    public static List<Object> excelToShopIdList(InputStream inputStream) {
        List<Object> list = new ArrayList<>();
        Workbook workbook = null;
        try {
            workbook = WorkbookFactory.create(inputStream);
            inputStream.close();
            //工作表对象
            Sheet sheet = workbook.getSheetAt(0);
            //总行数
            int rowLength = sheet.getLastRowNum() -1;
            System.out.println("总行数有多少行"+rowLength);
            //工作表的列
            Row row = sheet.getRow(0);
            //总列数
            int colLength = row.getLastCellNum();
            //获取列名
            List<String> colName=new ArrayList<>(colLength);
            for(int i=0;i<colLength;i++){
                colName.add(row.getCell(i).getStringCellValue());
            }
            //将列名加入到数组的第一个位置
            list.add(colName);
            //处理数据
            Cell cell =null;
            for(int i=1;i<rowLength;i++){
                row=sheet.getRow(i);
                List<Object> rowData=new ArrayList<>(colLength);
                for(int j=0;j<colLength;j++){
                    cell = row.getCell(j);
                    if (cell != null) {
                        //cell.setCellType(CellType.STRING);
                        CellType type= cell.getCellType();
                        if(type==CellType.STRING){

                            rowData.add(cell.getStringCellValue().trim());
                        }
                        else if(type==CellType.NUMERIC){
                            if(HSSFDateUtil.isCellDateFormatted(cell)){
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                                rowData.add(sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue())));
                            }
                            else {
                                rowData.add(cell.getNumericCellValue());
                            }
                        }
                        else {
                            cell.setCellType(CellType.STRING);
                            String data = cell.getStringCellValue();
                            data = data.trim().replace("\t","");
                            rowData.add(data);
                        }

                    }
                }
                list.add(rowData);
            }
        } catch (Exception e) {
            LOGGER.error("parse excel file error :", e);
        }
        return list ;
    }

    public static boolean isDateString(String datevalue, String dateFormat) {
        if (datevalue==null||datevalue.equals("")) {
            return false;
        }
        try {
            SimpleDateFormat fmt = new SimpleDateFormat(dateFormat);
            java.util.Date dd = fmt.parse(datevalue);
            if (datevalue.equals(fmt.format(dd))) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }
    public static boolean isNumber(String datavalue){
        if (datavalue==null||datavalue.equals("")) {
            return false;
        }
        try {
            Integer.parseInt(datavalue);
        }catch (NumberFormatException e){
            return false;
        }
        return true;
    }
}
