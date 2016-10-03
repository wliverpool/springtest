package com.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;

import com.pojo.UserFile;

/**
 * 生成excel2003格式
 * @author 吴福明
 *
 */
public class HSSFExcelAdapter implements IExcelAdapter<UserFile> {
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	public HSSFExcelAdapter(){
		
	}

	@Override
	public String generateExcel(String realPath,String sheetName,List<UserFile> datas) {
		// 先创建工作簿对象
		HSSFWorkbook workbook2003 = new HSSFWorkbook();
		// 创建工作表对象并命名
		HSSFSheet sheet = workbook2003.createSheet(sheetName);
		// 设置行列的默认宽度和高度
		sheet.setColumnWidth(0, 32 * 80);// 对A列设置宽度为80像素
		sheet.setColumnWidth(1, 32 * 80);
		sheet.setColumnWidth(2, 32 * 80);
		sheet.setColumnWidth(3, 32 * 80);
		sheet.setColumnWidth(4, 32 * 80);
		
		sheet.autoSizeColumn((short)0); //根据单元格中内容自动调整宽度

		// 创建样式
		HSSFFont font = workbook2003.createFont();
		HSSFCellStyle headerStyle = workbook2003.createCellStyle();
		HSSFCellStyle cellStyle = workbook2003.createCellStyle();
		// 设置垂直居中
		headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		headerStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// 设置边框
		headerStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		headerStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		headerStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		headerStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		// 字体加粗
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 设置长文本自动换行
		headerStyle.setWrapText(true);
		headerStyle.setFont(font);

		HSSFRow headerRow = sheet.createRow(0);
		headerRow.setHeightInPoints(25f);// 设置行高度
		HSSFCell nameHeader = headerRow.createCell(0);
		nameHeader.setCellValue("文件名");
		nameHeader.setCellStyle(headerStyle);
		HSSFCell genderHeader = headerRow.createCell(1);
		genderHeader.setCellValue("上传时间");
		genderHeader.setCellStyle(headerStyle);
		HSSFCell ageHeader = headerRow.createCell(2);
		ageHeader.setCellValue("文件id");
		ageHeader.setCellStyle(headerStyle);
		HSSFCell classHeader = headerRow.createCell(3);
		classHeader.setCellValue("上传人id");
		classHeader.setCellStyle(headerStyle);
		HSSFCell scoreHeader = headerRow.createCell(4);
		scoreHeader.setCellValue("文件大小");
		scoreHeader.setCellStyle(headerStyle);
		
		// 遍历集合对象创建行和单元格
		for (int i = 0; i < datas.size(); i++) {
			// 取出Student对象
			UserFile userFile = datas.get(i);
			// 创建行
			HSSFRow row = sheet.createRow(i + 1);
			row.setHeightInPoints(20f);
			// 开始创建单元格并赋值
			HSSFCell nameCell = row.createCell(0);
			nameCell.setCellValue(userFile.getFileName());
			nameCell.setCellStyle(cellStyle);
			HSSFCell genderCell = row.createCell(1);
			genderCell.setCellStyle(cellStyle);
			genderCell.setCellValue(sdf.format(userFile.getOperateTime()));
			HSSFCell ageCell = row.createCell(2);
			ageCell.setCellStyle(cellStyle);
			ageCell.setCellValue(userFile.getId());
			HSSFCell sclassCell = row.createCell(3);
			sclassCell.setCellStyle(cellStyle);
			sclassCell.setCellValue(userFile.getUserId());
			HSSFCell scoreCell = row.createCell(4);
			scoreCell.setCellStyle(cellStyle);
			scoreCell.setCellValue(Float.parseFloat(userFile.getFileSize()));
		}

		// 合并单元格
		//sheet.addMergedRegion(new CellRangeAddress(1, 4, 3, 3));

		// 数据分析行
		int dadaRowNum = sheet.getLastRowNum();
		HSSFRow totalRow = sheet.createRow(dadaRowNum + 1);// 获取已有的行数，加1再出新行
		totalRow.setHeightInPoints(25f);
		HSSFCell analyticsCell = totalRow.createCell(0);
		analyticsCell.setCellValue("数据分析");
		analyticsCell.setCellStyle(headerStyle);
		HSSFCell avgAgeCell = totalRow.createCell(1);
		avgAgeCell.setCellValue("平均文件id");
		avgAgeCell.setCellStyle(headerStyle);
		HSSFCell avgAgeValueCell = totalRow.createCell(2);
		avgAgeValueCell.setCellStyle(headerStyle);
		avgAgeValueCell.setCellFormula("AVERAGE(C2:C" + (dadaRowNum + 1) + ")");
		HSSFCell sumScoreCell = totalRow.createCell(3);
		sumScoreCell.setCellValue("总文件大小");
		sumScoreCell.setCellStyle(headerStyle);
		HSSFCell sumScoreValueCell = totalRow.createCell(4);
		sumScoreValueCell.setCellStyle(headerStyle);
		sumScoreValueCell.setCellFormula("SUM(E2:E" + (dadaRowNum + 1) + ")");

		// 生成文件
		File file = new File(realPath+File.separator+System.currentTimeMillis()+".xls");
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			workbook2003.write(fos);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return file.getAbsolutePath();
	}

	@Override
	public List<UserFile> readFromExcel(String filePath) {
		File excelFile = null;// Excel文件对象
		InputStream is = null;// 输入流对象
		String cellStr = null;// 单元格，最终按字符串处理
		List<UserFile> userFileList = new ArrayList<UserFile>();// 返回封装数据的List
		UserFile userFile = null;
		try {
			excelFile = new File(filePath);
			is = new FileInputStream(excelFile);// 获取文件输入流
			HSSFWorkbook workbook2003 = new HSSFWorkbook(is);// 创建Excel2003文件对象
			HSSFSheet sheet = workbook2003.getSheetAt(0);// 取出第一个工作表，索引是0
			// 开始循环遍历行，表头不处理，从1开始
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				userFile = new UserFile();// 实例化Student对象
				HSSFRow row = sheet.getRow(i);// 获取行对象
				if (row == null) {// 如果为空，不处理
					continue;
				}
				//判断上传excel中数据是否合格
				boolean isDataQualified = true;
				// 循环遍历单元格
				for (int j = 0; j < row.getLastCellNum(); j++) {
					HSSFCell cell = row.getCell(j);// 获取单元格对象
					if (cell == null) {// 单元格为空设置cellStr为空串
						cellStr = "";
					} else if (cell.getCellType() == HSSFCell.CELL_TYPE_BOOLEAN) {// 对布尔值的处理
						cellStr = String.valueOf(cell.getBooleanCellValue());
					} else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {// 对数字值的处理
						cellStr = cell.getNumericCellValue() + "";
					} else if (cell.getCellType() == HSSFCell.CELL_TYPE_FORMULA) {// 对公式的处理
						cellStr = cell.getCellFormula() + "";
					} else {// 其余按照字符串处理
						cellStr = cell.getStringCellValue();
					}
					// 下面按照数据出现位置封装到bean中
					if (j == 0) {
						userFile.setFileName(cellStr);
					} else if (j == 1) {
						Date operateTime = new Date();
						try {
							operateTime = sdf.parse(cellStr);
						} catch (ParseException e) {
							isDataQualified = false;
							continue;
						}
						userFile.setOperateTime(operateTime);
					} else if (j == 2) {
						try {
							new Float(cellStr).longValue();
						} catch (NumberFormatException e) {
							isDataQualified = false;
							continue;
						}
						userFile.setId(new Float(cellStr).longValue());
					} else if (j == 3) {
						try {
							new Float(cellStr).longValue();
						} catch (NumberFormatException e) {
							isDataQualified = false;
							continue;
						}
						userFile.setUserId(new Float(cellStr).longValue());
					} else {
						try {
							new Float(cellStr);
						} catch (NumberFormatException e) {
							isDataQualified = false;
							continue;
						}
						userFile.setFileSize(cellStr);
					}
				}
				if(isDataQualified){
					userFileList.add(userFile);// 数据装入List
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {// 关闭文件流
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return userFileList;
	}

}
