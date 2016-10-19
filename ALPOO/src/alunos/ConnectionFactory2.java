package alunos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectionFactory2 {

    private String driver = "org.postgresql.Driver"; // "com.mysql.jdbc.Driver"; org.postgresql.Driver
    private String url = "jdbc:postgresql://localhost/alpoo";    // "jdbc:mysql://localhost/locaCar"; jdbc:postgresql://localhost/aula_neri
    private String usuario = "postgres";// "root";  postgres
    private String senha = "dmc123";  // "dmc123"; dmc123
    private static Connection conexao;

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public ConnectionFactory2() {
    }

    public ConnectionFactory2(String driver, String url, String usuario, String senha) {
        this.driver = driver;
        this.url = url;
        this.usuario = usuario;
        this.senha = senha;
    }

    private Connection Connect(String driver, String url, String usuario, String senha) {
        try {
            Class.forName(driver);
            return DriverManager.getConnection(url, usuario, senha);
        } catch (ClassNotFoundException | SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public Connection getConnection(String driver, String url, String usuario, String senha) {
        try {
            if (conexao == null || conexao.isClosed()) {
                conexao = Connect(driver, url, usuario, senha);
            }
            return conexao;
        } catch (Exception e) {
            fechaConexao(conexao);
            throw new RuntimeException(e);
        } 
    }

    public Connection getConnection(){
        return getConnection(this.driver, this.url, this.usuario, this.senha);
    }
    
    public static void fechaConexao(Connection conn) {

        try {
            if (conn != null) {
                conn.close();
                //System.out.println("Fechada a conexão com o banco de dados");
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
            //System.out.println("Não foi possível fechar a conexão com o banco de dados " + e.getMessage());
        }
    }

    public static void fechaConexao(Connection conn, PreparedStatement stmt) {

        try {
            if (conn != null) {
                fechaConexao(conn);
            }
            if (stmt != null) {
                stmt.close();
                //System.out.println("Statement fechado com sucesso");
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
            //System.out.println("Não foi possível fechar o statement " + e.getMessage());
        }
    }

    public static void fechaConexao(Connection conn, PreparedStatement stmt, ResultSet rs) {

        try {
            if (conn != null || stmt != null) {
                fechaConexao(conn, stmt);
            }
            if (rs != null) {
                rs.close();
                //System.out.println("ResultSet fechado com sucesso");
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
            //System.out.println("Não foi possível fechar o ResultSet " + e.getMessage());
        }
    }
}
