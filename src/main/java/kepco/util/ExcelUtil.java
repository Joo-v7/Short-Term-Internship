package kepco.util;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExcelUtil {
    @Value("${global.file.base-path}")
    static Path basePath;
    private final Logger log = LoggerFactory.getLogger(getClass());

    /**
     * @param filePath String 파일경로
     * @param fileName String 파일명
     * @param data     데이터 List<Map<String, Object>>
     * @param response HttpServletResponse
     * @throws IOException
     * @brief 엑셀생성 - 템플릿 o
     */
    public static void makeExcelByTemplate(String filePath, String fileName, List<Map<String, Object>> data, HttpServletResponse response) throws IOException {
        // template 불러오기
        FileInputStream file = new FileInputStream(filePath);
        XSSFWorkbook workbook = new XSSFWorkbook(file);

        // 첫번째 시트 적용
        Sheet sheet = workbook.getSheetAt(0);

        // 행, 열
        Row row = null;
        Cell cell = null;

        // header가 몇번째 row까지 위치하는지 찾아야함
        int startRowNum = sheet.getPhysicalNumberOfRows();

        // data 입력
        for (int i = 0; i < data.size(); i++) {
            // key List
            List<String> keyList = new ArrayList<>(data.get(i).keySet());

            row = sheet.createRow(startRowNum + i);

            int cellIdx = 0;
            for (int j = cellIdx; j < keyList.size(); j++) {
                cell = row.getCell(j);
                if (cell == null) {
                    // cell값이 null 이라면 cell 생성 후 기존 cellStyle 적용
                    cell = row.createCell(cellIdx++);
                    cell.setCellStyle(styleSetting(cell));
                }
                cell.setCellValue(data.get(i).get(keyList.get(j)).toString());
            }
        }

        // 파일 다운로드
        downloadExcel(workbook, fileName, response);
    }

    /**
     * @param sheetName String 시트명
     * @param fileName  String 파일명
     * @param header    String[] 헤더
     * @param data      List<Map<String, Object>> 데이터
     * @param response  HttpServletResponse 객체
     * @return
     * @brief 엑셀 생성 - 템플릿 X
     */
    public static void makeExcel(String sheetName, String fileName, String[] header, List<Map<String, Object>> data, HttpServletResponse response) throws IOException {
        // 엑셀 파일 생성
        XSSFWorkbook workbook = new XSSFWorkbook();

        // 엑셀 시트명
        Sheet sheet = workbook.createSheet(sheetName);

        // 행, 열
        Row row = null;
        Cell cell = null;

        // 헤더 스타일
        CellStyle headerStyle = cellStyleSetting(workbook, "header");
        // 데이터 스타일
        CellStyle dataStyle = cellStyleSetting(workbook, "data");

        row = sheet.createRow(0);
        // header 입력
        for (int i = 0; i < header.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(header[i]);
            cell.setCellStyle(headerStyle);
        }

        // data 입력
        for (int i = 0; i < data.size(); i++) {
            // keyList
            List<String> keyList = new ArrayList<>(data.get(i).keySet());

            row = sheet.createRow(i + 1);
            int cellIdx = 0;

            for (int j = cellIdx; j < keyList.size(); j++) {
                cell = row.createCell(cellIdx++);
                cell.setCellValue(String.valueOf(data.get(i).get(keyList.get(j))));
                cell.setCellStyle(dataStyle);
            }
        }

        // 셀 너비 자동 조정
        for (int i = 0; i < header.length; i++) {
            sheet.autoSizeColumn(i);
            sheet.setColumnWidth(i, sheet.getColumnWidth(i));
        }

        // 파일 다운로드
        downloadExcel(workbook, fileName, response);
    }

    /**
     * @param workbook XSSFWorkbook
     * @param fileName String
     * @param response HttpServletResponse
     * @throws IOException
     * @brief 엑셀 다운로드
     */
    public static void downloadExcel(XSSFWorkbook workbook, String fileName, HttpServletResponse response) throws IOException {
        OutputStream outs = null;
        try {
            outs = response.getOutputStream();
            String encFileName = new String(fileName.getBytes("KSC5601"), "8859_1");
            response.reset();
            response.setHeader("Content-Disposition", "attachment; filename=\"" + encFileName + ".xlsx\"");
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            outs.flush();
            workbook.write(outs);
            outs.flush();
        } finally {
            if (outs != null) {
                outs.close();
            }
            if (workbook != null) {
                workbook.close();
            }
        }
    }

    /**
     * @param cell
     * @return
     * @brief template cell style 설정
     */
    public static CellStyle styleSetting(Cell cell) {
        // 기존 적용된 cell style 가져오기
        // cell style null 일 경우 row style 가져옴
        // row style null 일 경우 column style 가져옴
        // 이외는 모름
        CellStyle cellStyle = cell.getCellStyle();
        if (cellStyle.getIndex() == 0) cellStyle = cell.getRow().getRowStyle();
        if (cellStyle == null) cellStyle = cell.getSheet().getColumnStyle(cell.getColumnIndex());
        if (cellStyle == null) cellStyle = cell.getCellStyle();
        return cellStyle;
    }

    /**
     * @param workbook
     * @param kind
     * @return
     * @brief cellStyle 설정
     */
    public static CellStyle cellStyleSetting(XSSFWorkbook workbook, String kind) {
        // 테이블 스타일
        CellStyle cellStyle = workbook.createCellStyle();

        // 경계선
        // cellStyle.setBorderTop(BorderStyle.THIN);
        // cellStyle.setBorderBottom(BorderStyle.THIN);
        // cellStyle.setBorderLeft(BorderStyle.THIN);
        // cellStyle.setBorderRight(BorderStyle.THIN);

        // 헤더일 경우
        if (kind.equals("header")) {
            // 배경색 회색
            cellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        }

        // 데이터 가운데 정렬
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        // 폰트 설정
        Font fontOfGothic = workbook.createFont();
        fontOfGothic.setFontName("맑은 고딕");
        cellStyle.setFont(fontOfGothic);

        return cellStyle;
    }
}
