

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

public class App {
	public static class GenClassMetaData {
		private String className = null;
		private List<Map<String, Object>> fields = new ArrayList<Map<String, Object>>();

		public String getClassName() {
			return className;
		}

		public void setClassName(String className) {
			this.className = prettyIt(className, true);
		}

		public void addField(String name, int dataType, boolean nullable) {
			HashMap<String, Object> fieldProps = new HashMap<String, Object>();
			fieldProps.put("name", prettyIt(name, false));
			fieldProps.put("dbcolumn", name);
			fieldProps.put("methodName", prettyIt(name, true));
			fieldProps.put("type", toJavaType(dataType));
			fieldProps.put("nullable", nullable);

			String tType = toTranslatorType(dataType);
			fieldProps.put("translator",
					(tType != null) ? ", translator=SqlTypedValue." + tType
							: "");
			this.fields.add(fieldProps);
		}

		public List<Map<String, Object>> getFields() {
			return fields;
		}

		public String toString() {
			return this.className + "=>" + this.fields;
		}

	}

	public static void dump(DatabaseMetaData dbmd) throws SQLException,
			IOException {
		ResultSet rs = dbmd.getTables(null, null, null,
				new String[] { "TABLE" });

		while (rs.next()) {
			String tableName = rs.getString("TABLE_NAME");
			GenClassMetaData metadata = new GenClassMetaData();
			metadata.setClassName(tableName);
			ResultSet colRs = dbmd.getColumns(rs.getString("TABLE_CAT"),
					rs.getString("TABLE_SCHEM"), rs.getString("TABLE_NAME"),
					null);
			while (colRs.next()) {
				metadata.addField(colRs.getString("COLUMN_NAME"),
						colRs.getInt("DATA_TYPE"),
						"YES".equals(colRs.getObject("IS_NULLABLE")));
			}
			generate(metadata);
		}
	}

	public static String toTranslatorType(int dataType) {
		switch (dataType) {
		case Types.BIT:
			return "Boolean";
		case Types.TINYINT:
			return "Byte";

			// case Types.INTEGER:
			// case Types.SMALLINT:
			// return "Integer";
			//
			// case Types.BIGINT:
			// return "Long";
			//
			// case Types.REAL:
			// case Types.FLOAT:
			// return "Float";
			//
			// case Types.DOUBLE:
			// return "Double";

		case Types.NUMERIC:
		case Types.DECIMAL:
			return "BigDecimal";

			// case Types.CHAR:
			// case Types.VARCHAR:
			// case Types.CLOB:
			// case Types.LONGVARCHAR:
			// case Types.LONGNVARCHAR:
			// return "String";

		case Types.BINARY:
		case Types.VARBINARY:
		case Types.LONGVARBINARY:
			return "ByteArray";

		case Types.DATE:
			return "Date";

		case Types.TIME:
			return "Time";

		case Types.TIMESTAMP:
			return "Timestamp";

		case Types.BLOB:
			return "Blob";
		default:
			return null;
		}
	}

	public static String toJavaType(int dataType) {
		switch (dataType) {
		case Types.BIT:
			return "Boolean";
		case Types.TINYINT:
			return "Byte";

		case Types.INTEGER:
		case Types.SMALLINT:
			return "Integer";

		case Types.BIGINT:
			return "Long";

		case Types.REAL:
		case Types.FLOAT:
			return "Float";

		case Types.DOUBLE:
			return "Double";

		case Types.NUMERIC:
		case Types.DECIMAL:
			return "BigDecimal";

		case Types.CHAR:
		case Types.VARCHAR:
		case Types.CLOB:
		case Types.LONGVARCHAR:
		case Types.LONGNVARCHAR:
			return "String";

		case Types.BINARY:
			return "Integer";

		case Types.VARBINARY:
		case Types.LONGVARBINARY:
			return "byte[]";

		case Types.DATE:
			return "java.sql.Date";

		case Types.TIME:
			return "java.sql.Time";

		case Types.TIMESTAMP:
			return "java.sql.Timestamp";

		case Types.BLOB:
			return "java.sql.Blob";
		default:
			throw new java.lang.UnsupportedOperationException("DataType: " + dataType);
		}
	}

	private static void generate(GenClassMetaData metadata) throws IOException {
		
		File dir = new File("/dto");
		
		if (!dir.exists() && !dir.mkdirs()) {			
			throw new IOException("Unable to create folder: " + dir.getAbsolutePath());
		}

		VelocityContext ctx = new VelocityContext();
		ctx.put("metadata", metadata);

		VelocityEngine engine = new VelocityEngine();
		engine.init();
		Template t = engine.getTemplate("dto.vtl");

		BufferedWriter out = null;

		try {
			File sourceFile = new File(dir, metadata.getClassName() + ".java");
			out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(sourceFile), "utf-8"));
			t.merge(ctx, out);
			System.out.println("Source: " + sourceFile.getAbsolutePath() + " generated.");
		} finally {
			if (out != null) {				
				out.close();
			}
		}
	}

	private final static String prettyIt(String s, boolean capitalize) {
		
		StringBuilder newStr = new StringBuilder();
		char[] seq = s.toCharArray();
		boolean doCapitalize = capitalize;
		
		for (int i = 0; i < seq.length; i++) {
			switch (seq[i]) {
			case '_':
				doCapitalize = true;
				break;
			default:
				newStr.append((doCapitalize) ? Character.toUpperCase(seq[i])
						: Character.toLowerCase(seq[i]));
				doCapitalize = false;
			}
		}
		return newStr.toString();
	}

	public static void main(String[] args) throws ClassNotFoundException,
			SQLException, IOException {
		String driver = "com.mysql.jdbc.Driver";
		String jdbcurl = "jdbc:mysql://192.168.7.68:3306/RobotDect";
		String user = "root";
		String passwd = "root";

		Class.forName(driver);
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(jdbcurl, user, passwd);
			DatabaseMetaData dbmd = conn.getMetaData();
			dump(dbmd);
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}
}
