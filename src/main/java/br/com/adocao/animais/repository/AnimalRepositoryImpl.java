package br.com.adocao.animais.repository;

import br.com.adocao.animais.database.DatabaseConnection;
import br.com.adocao.animais.model.Animal;
import br.com.adocao.animais.model.Cachorro;
import br.com.adocao.animais.model.Gato;
import br.com.adocao.animais.model.StatusAnimal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnimalRepositoryImpl implements AnimalRepository{

    @Override
    public void salvar(Animal animal) {
        String sql = (animal.getId() == 0)
                ? "INSERT INTO animais (nome, raca, idade, cor, status, tipo) VALUES (?, ?, ?, ?, ?, ?)"
                : "UPDATE animais SET nome = ?, raca = ?, idade = ?, cor = ?, status = ? WHERE id = ?";

        // Usaremos try-with-resources para garantir que a conexão seja fechada
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, animal.getNome());
            pstmt.setString(2, animal.getRaca());
            pstmt.setInt(3, animal.getIdade());
            pstmt.setString(4, animal.getCor());
            pstmt.setString(5, animal.getStatus().name());

            if (animal.getId() == 0) {
                String tipo = (animal instanceof Cachorro) ? "CACHORRO" : "GATO";
                pstmt.setString(6, tipo);
            } else {
                pstmt.setLong(6, animal.getId());
            }

            int affectedRows = pstmt.executeUpdate();

            if (animal.getId() == 0 && affectedRows > 0) {
                // A forma correta de pegar a última ID inserida no SQLite
                try (Statement stmt = conn.createStatement();
                     ResultSet generatedKeys = stmt.executeQuery("SELECT last_insert_rowid()")) {
                    if (generatedKeys.next()) {
                        animal.setId(generatedKeys.getLong(1));
                    }
                }
            }

        } catch (SQLException e) {
            // Esta é a mudança importante: em vez de apenas imprimir,
            // nós lançamos uma RuntimeException que vai parar o programa e mostrar o erro exato.
            throw new RuntimeException("Erro ao salvar animal no banco de dados.", e);
        }
    }

    @Override
    public Animal buscarPorId(long id) {
        String sql = "SELECT * FROM animais WHERE id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return extrairAnimalDoResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar animal por ID: " + e.getMessage());
        }
        return null; // Retorna null se não encontrar
    }

    @Override
    public List<Animal> listarTodos() {
        List<Animal> animais = new ArrayList<>();
        String sql = "SELECT * FROM animais";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                animais.add(extrairAnimalDoResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar todos os animais: " + e.getMessage());
        }
        return animais;
    }

    @Override
    public void remover(long id) {
        String sql = "DELETE FROM animais WHERE id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao remover animal: " + e.getMessage());
        }
    }

    /**
     * Método utilitário para converter uma linha do ResultSet em um objeto Animal.
     * Isso evita a repetição de código em buscarPorId() e listarTodos().
     */
    private Animal extrairAnimalDoResultSet(ResultSet rs) throws SQLException {
        String tipo = rs.getString("tipo");
        Animal animal;

        String nome = rs.getString("nome");
        String raca = rs.getString("raca");
        int idade = rs.getInt("idade");
        String cor = rs.getString("cor");

        if ("CACHORRO".equals(tipo)) {
            animal = new Cachorro(nome, raca, idade, cor);
        } else {
            animal = new Gato(nome, raca, idade, cor);
        }

        animal.setId(rs.getLong("id"));
        animal.setStatus(StatusAnimal.valueOf(rs.getString("status")));

        return animal;
    }
}
