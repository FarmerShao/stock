package com.farmershao.stock.trade.model.suport;

import com.farmershao.stock.trade.util.CloseableUtils;
import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Serializable;
import java.io.StringReader;
import java.util.*;

/**
 * DataTable数据结构
 * <p>
 * Created by kongkp on 2017/1/7.
 */
public class DataTable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 676089425903169021L;
	private String[] headers;
	private final Map<String, Integer> headerIndex = new HashMap<>();
	private final List<String[]> data = new ArrayList<>();
	private String colSeperator = "\t";
	private String name = "undefined";

	public DataTable(String tableText) {
		this("undefined", tableText);
	}

	public DataTable(String name, String tableText) {
		Preconditions.checkArgument(StringUtils.isNotEmpty(name), "name参数不能为空");
		this.name = name;
		this.setTableText(tableText);
	}

	public DataTable(String[] headers) {
		Preconditions.checkArgument(headers != null, "headers参数不能为null");
		Preconditions.checkArgument(headers.length > 0, "headers个数不能为0");
		this.headers = headers;
	}

	public DataTable(String name, String[] headers) {
		Preconditions.checkArgument(StringUtils.isNotEmpty(name), "name参数不能为空");
		Preconditions.checkArgument(headers != null, "headers参数不能为null");
		Preconditions.checkArgument(headers.length > 0, "headers个数不能为0");
		this.name = name;
		this.headers = headers;
	}

	public void setTableText(String tableText) {
		BufferedReader reader = new BufferedReader(new StringReader(tableText));
		try {
			parseHeader(reader);
			parseData(reader);
		} catch (IOException e) {
			throw new IllegalStateException("Unexcepted exception occured.", e);
		} finally {
			CloseableUtils.close(reader);
		}
	}

	public String get(String columnName, int rowIndex) {
		if (rowIndex < data.size()) {
			String[] rowData = data.get(rowIndex);
			Integer columnIndex = headerIndex.get(columnName);
			if (columnIndex != null) {
				return rowData[columnIndex];
			}
		}
		return null;
	}

	public String get(int columnIndex, int rowIndex) {
		if (headers != null && columnIndex < headers.length && rowIndex < data.size()) {
			String[] rowData = data.get(rowIndex);
			return rowData[columnIndex];
		}
		return null;
	}

	public String[] getRow(int rowIndex) {
		return data.get(rowIndex);
	}

	public void addRow(String[] row) {
		Preconditions.checkArgument(row != null, "row参数不能为null");
		Preconditions.checkArgument(row.length == headers.length, "row个数不与headers个数一致");
		data.add(row);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isValid() {
		return headers != null && headers.length > 0;
	}

	private void parseData(BufferedReader reader) throws IOException {
		String line = reader.readLine();
		while (line != null) {
			if (StringUtils.isNotBlank(line)) {
				String[] rowData = StringUtils.splitByWholeSeparatorPreserveAllTokens(line, colSeperator);
				data.add(align(rowData, headers.length));
			}
			line = reader.readLine();
		}
	}

	private void parseHeader(BufferedReader reader) throws IOException {
		String line = reader.readLine();
		while (line != null) {
			if (StringUtils.isNotBlank(line)) {
				headers = StringUtils.splitByWholeSeparatorPreserveAllTokens(line, colSeperator);
				headerIndex.clear();
				for (int i = 0; i < headers.length; i++) {
					headerIndex.put(headers[i], i);
				}
				break;
			}
			line = reader.readLine();
		}
	}

	private String[] align(String[] rowData, int length) {
		if (rowData.length == length) {
			return rowData;
		}
		String[] data = new String[length];
		for (int i = 0; i < length; i++) {
			if (i < rowData.length) {
				data[i] = rowData[i];
			} else {
				data[i] = "";
			}
		}
		return data;
	}

	public int rows() {
		return data.size();
	}

	public int columns() {
		return headers == null ? 0 : headers.length;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (isValid()) {
			sb.append("tableName:").append(name).append("\n");
			writeRow(sb, headers);
			for (String[] row : data) {
				writeRow(sb, row);
			}
		} else {
			sb.append("tableName:").append(name).append("<no data>\n");
		}
		return sb.toString();
	}

	private void writeRow(StringBuilder sb, String[] row) {
		for (int i = 0, len = row.length; i < len; i++) {
			sb.append(row[i]);
			if (i != len - 1) {
				sb.append(colSeperator);
			} else {
				sb.append("\n");
			}
		}
	}

	public void browse(Browser action) {
		Objects.requireNonNull(action);
		for (String[] row : data) {
			boolean goon = action.browse(row);
			if (!goon) {
				break;
			}
		}
	}

	@FunctionalInterface
	public interface Browser {
		boolean browse(String[] row);
	}

}
