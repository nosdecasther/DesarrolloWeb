package com.desarrolloweb.spring.app.view.excel;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import com.desarrolloweb.spring.app.entities.Cliente;

@Component("detalle-cliente-form.xlsx")
public class ClienteExcelView extends AbstractXlsxView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		

		try {
			response.setHeader("Content-Disposition", "attachment; filename=\"reporte_clientes.xlsx\"");
			Cliente cliente = (Cliente) model.get("cliente");
			Sheet sheet = workbook.createSheet("Cliente");
			
			CellStyle header = workbook.createCellStyle();
			header.setBorderBottom(BorderStyle.MEDIUM);
			header.setBorderTop(BorderStyle.MEDIUM);
			header.setBorderRight(BorderStyle.MEDIUM);
			header.setBorderLeft(BorderStyle.MEDIUM);
			header.setFillForegroundColor(IndexedColors.GREEN.index);
			header.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			
			Row row = sheet.createRow(0);
			Cell cell = row.createCell(0);
			cell.setCellValue("Datos del Cliente");
			cell.setCellStyle(header);
			
			row = sheet.createRow(1);
			cell = row.createCell(0);
			cell.setCellValue("Nombre");
			cell = row.createCell(1);
			cell.setCellValue(cliente.getNombre());
			
			row = sheet.createRow(2);
			cell = row.createCell(0);
			cell.setCellValue("Apellidos");
			cell = row.createCell(1);
			cell.setCellValue(cliente.getApellido());

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}
	
}
