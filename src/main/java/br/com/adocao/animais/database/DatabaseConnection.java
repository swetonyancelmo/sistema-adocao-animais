package br.com.adocao.animais.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public final class DatabaseConnection {

    private static final String DATABASE_URL = "jdbc:sqlite:adocao_animais.db";

    private static DatabaseConnection instance;

    private DatabaseConnection(){
        try {
            Class.forName("org.sqlite.JDBC");
            initializeDatabase();
        } catch (ClassNotFoundException e){
            System.out.println("Erro: Driver JDBC do SQLite não encontrado.");
            e.printStackTrace();
        }
    }

    public static synchronized DatabaseConnection getInstance() {
        if(instance == null){
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL);
    }

    public void initializeDatabase(){
        String sqlAnimal = "CREATE TABLE IF NOT EXISTS animais (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome TEXT NOT NULL," +
                "raca TEXT," +
                "idade INTEGER," +
                "cor TEXT," +
                "status TEXT NOT NULL," +
                "tipo TEXT NOT NULL" +
                ");";

        String sqlAdotante = "CREATE TABLE IF NOT EXISTS adotantes (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome TEXT NOT NULL," +
                "cpf TEXT NOT NULL UNIQUE," +
                "endereco TEXT," +
                "telefone TEXT," +
                "quantidade_adocoes INTEGER DEFAULT 0" +
                ");";

        String sqlAdocao = "CREATE TABLE IF NOT EXISTS adocoes (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "animal_id INTEGER NOT NULL," +
                "adotante_id INTEGER NOT NULL," +
                "data_adocao TEXT NOT NULL," + // SQLite armazena datas como TEXT no formato 'YYYY-MM-DD'
                "FOREIGN KEY (animal_id) REFERENCES animais(id)," +
                "FOREIGN KEY (adotante_id) REFERENCES adotantes(id)" +
                ");";

        try (Connection conn = this.getConnection();
             Statement stmt = conn.createStatement()){
            stmt.execute(sqlAnimal);
            stmt.execute(sqlAdotante);
            stmt.execute(sqlAdocao);
        } catch (SQLException e){
            System.err.println("Erro ao inicializar o banco de dados: " + e.getMessage());
        }
    }
}
