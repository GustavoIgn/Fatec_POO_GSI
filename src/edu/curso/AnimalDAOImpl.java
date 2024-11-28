package edu.curso;

/*
@author:<Gustavo da Silva Ignacio 1110482313006>
 */

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnimalDAOImpl implements AnimalDAO {

    private final static String DB_CLASS = "org.mariadb.jdbc.Driver";
    private final static String DB_URL = "jdbc:mariadb://localhost:3306/agendadb?useUnicode=true&characterEncoding=utf8&allowPublicKeyRetrieval=true";
    private final static String DB_USER = "root";
    private final static String DB_PASS = "GStv62072";

    private Connection con = null;

    public AnimalDAOImpl() throws Exception {
        try {
            Class.forName(DB_CLASS);
            con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        } catch (ClassNotFoundException | SQLException e) {
            throw new Exception("Erro ao conectar ao banco de dados", e);
        }
    }

    @Override
    public void inserir(Animal a) throws Exception {
        try {
            String SQL = """
                    INSERT INTO animais (id, nome, especie, raca, idade, cpf_dono)
                    VALUES (?, ?, ?, ?, ?, ?)
                    """;
            PreparedStatement stm = con.prepareStatement(SQL);
            stm.setInt(1, a.getId());
            stm.setString(2, a.getNome());
            stm.setString(3, a.getEspecie());
            stm.setString(4, a.getRaca());
            stm.setInt(5, a.getIdade());
            stm.setString(6, a.getCPFDono());
            stm.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Erro ao inserir o animal", e);
        }
    }

    @Override
    public void atualizar(Animal a) throws Exception {
        try {
            String SQL = """
                    UPDATE animais SET nome = ?, especie = ?, raca = ?, idade = ?, cpf_dono = ?
                    WHERE id = ?
                    """;
            PreparedStatement stm = con.prepareStatement(SQL);
            stm.setString(1, a.getNome());
            stm.setString(2, a.getEspecie());
            stm.setString(3, a.getRaca());
            stm.setInt(4, a.getIdade());
            stm.setString(5, a.getCPFDono());
            stm.setInt(6, a.getId());
            stm.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Erro ao atualizar o animal", e);
        }
    }

    @Override
    public void remover(Animal a) throws Exception {
        try {
            String SQL = """
                    DELETE FROM animais WHERE id = ?
                    """;
            PreparedStatement stm = con.prepareStatement(SQL);
            stm.setInt(1, a.getId());
            stm.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Erro ao remover o animal", e);
        }
    }

    @Override
    public List<Animal> pesquisarPorNome(String nome) throws Exception {
        List<Animal> lista = new ArrayList<>();
        try {
            String SQL = """
                    SELECT * FROM animais WHERE nome LIKE ?
                    """;
            PreparedStatement stm = con.prepareStatement(SQL);
            stm.setString(1, "%" + nome + "%");
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Animal a = new Animal();
                a.setId(rs.getInt("id"));
                a.setNome(rs.getString("nome"));
                a.setEspecie(rs.getString("especie"));
                a.setRaca(rs.getString("raca"));
                a.setIdade(rs.getInt("idade"));
                a.setCPFDono(rs.getString("cpf_dono"));
                lista.add(a);
            }
        } catch (SQLException e) {
            throw new Exception("Erro ao pesquisar animais por nome", e);
        }
        return lista;
	}
}
