package org.pos.coffee.bean.poi;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Laurie on 1/21/2016.
 */
public abstract class Report {
    protected XSSFWorkbook workbook;
    protected XSSFSheet sheet;
    protected abstract void publishHeader();
    protected abstract void publishData();

    public Report(){
        this("Report");
    }

    public Report(String sheetName){
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet(sheetName);
    }

    public void publishReport(OutputStream out) throws IOException {
        publishHeader();
        publishData();
        workbook.write(out);
        out.close();
    }
}
