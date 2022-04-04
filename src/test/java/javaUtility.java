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
    static   public ArrayList<String> Tokens = new ArrayList<>();

    //This ArrayList will store the unique ids of every new registered users
    static   public ArrayList<String> ID= new ArrayList<>();



    public static FileInputStream fileinput;
    public static XSSFWorkbook workbook;
    public static XSSFSheet worksheet;
    public static XSSFRow row;
    public static XSSFCell cell;



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





