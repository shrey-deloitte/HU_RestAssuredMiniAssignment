import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class javaUtility {

    //This ArrayList will store the tokens of every new registered users
    static   public ArrayList<String> STORING_TOKENS_HERE = new ArrayList<>();

    //This ArrayList will store the unique ids of every new registered users
    static   public ArrayList<String> STORING_Ids_Here= new ArrayList<>();


    public static FileInputStream fileinput; //Pre-defined class present in java for reading the file
    public static XSSFWorkbook workbook;   //XSSFWorkbook is a class For workbook, we have created an object of the workbook
    public static XSSFSheet worksheet;  //XSSFSheet is a class For worksheet, we have created an object of the worksheet
    public static XSSFRow row;       //XSSFRow is a class For row, we have created an object of the row
    public static XSSFCell cell;    //XSSFCell is a class For cell, we have created an object of the Cell



    public static int getRowCount(String FilePath, String xlsheetname) throws IOException {
        int rowcount;
        fileinput = new FileInputStream(FilePath);

        workbook = new XSSFWorkbook(fileinput);
        worksheet = workbook.getSheet(xlsheetname);

        rowcount = worksheet.getLastRowNum(); //Returns number of rows
        workbook.close();
        fileinput.close();
        return rowcount; //Row count returned
    }

    public static String getCellvalue(String FilePath, String SheetName, int rownum, int colnum) throws IOException {

        fileinput = new FileInputStream(FilePath);
        workbook = new XSSFWorkbook(fileinput);
        worksheet = workbook.getSheet(SheetName);
        row = worksheet.getRow(rownum);
        cell = row.getCell(colnum);  //This wil get us the value of that cell

        DataFormatter formatter = new DataFormatter();
        String values = formatter.formatCellValue(cell);

        return values;
    }
}





