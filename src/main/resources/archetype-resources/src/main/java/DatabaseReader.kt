package `archetype-resources`.src.main.java

class DatabaseReader {
    import java.sql.*;
    import java.util.ArrayList;
    import java.util.List;

    public class DatabaseReader {
        // 数据库连接配置
        private static final String URL = "jdbc:mysql://localhost:3306/world?useSSL=false&serverTimezone=UTC";
        private static final String USER = "root";
        private static final String PASSWORD = "nqy";

        public static void main(String[] args) {
            try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
                // 获取数据库元数据
                DatabaseMetaData metaData = conn.getMetaData();

                // 获取所有表名
                List<String> tableNames = new ArrayList<>();
                try (ResultSet tables = metaData.getTables(null, null, "%", new String[]{"TABLE"})) {
                    while (tables.next()) {
                        tableNames.add(tables.getString("TABLE_NAME"));
                    }
                }

                    System.out.println("数据库包含表：" + tableNames);

                    // 遍历所有表并读取数据
                    for (String tableName : tableNames) {
                        System.out.println("\n=== 表 " + tableName + " 数据 ===");

                        String query = "SELECT * FROM " + tableName;
                        try (Statement stmt = conn.createStatement();
                            ResultSet rs = stmt.executeQuery(query)) {

                                // 获取列信息
                                ResultSetMetaData rsmd = rs.getMetaData();
                                int columnCount = rsmd.getColumnCount();

                                // 打印列头
                                for (int i = 1; i <= columnCount; i++) {
                                System.out.print(rsmd.getColumnName(i) + "\t");
                            }
                                System.out.println();

                                // 打印数据
                                while (rs.next()) {
                                    for (int i = 1; i <= columnCount; i++) {
                                        System.out.print(rs.getString(i) + "\t");
                                    }
                                    System.out.println();
                                }
                            }
                        }
                } catch (SQLException e) {
                handleSQLException(e);
            }
            }

        private static void handleSQLException(SQLException e) {
            System.err.println("数据库操作错误：");
            System.err.println("错误代码: " + e.getErrorCode());
            System.err.println("SQL状态: " + e.getSQLState());
            System.err.println("错误信息: " + e.getMessage());

            // 常见问题排查提示
            if (e.getErrorCode() == 1045) {
                System.err.println(">> 可能原因：用户名或密码错误");
            } else if (e.getErrorCode() == 1049) {
                System.err.println(">> 可能原因：数据库不存在");
            } else if (e.getErrorCode() == 0 && e.getMessage().contains("Communications link failure")) {
                System.err.println(">> 可能原因：MySQL服务未启动");
            }
        }
    }
}