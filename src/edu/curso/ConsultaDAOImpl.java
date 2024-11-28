package edu.curso;

/*
@author:<Gustavo da Silva Ignacio 1110482313006>
 */

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConsultaDAOImpl implements ConsultaDAO {

	private final static String DB_CLASS = "org.mariadb.jdbc.Driver";
    private final static String DB_URL = "jdbc:mariadb://localhost:3306/agendadb?useUnicode=true&characterEncoding=utf8mb4&allowPublicKeyRetrieval=true";
    private final static String DB_USER = "root";
    private final static String DB_PASS = "GStv62072";

    private Connection con = null;

    public ConsultaDAOImpl() throws Exception {
        try {
            Class.forName(DB_CLASS);
            con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        } catch (ClassNotFoundException | SQLException e) {
            throw new Exception("Erro ao conectar ao banco de dados", e);
        }
    }

    @Override
    public void inserir(Consulta c) throws Exception {
        try {
            String SQL = """
                    INSERT INTO consultas (id, data, descricao, animal_id)
                    VALUES (?, ?, ?, ?)
                    """;
            PreparedStatement stm = con.prepareStatement(SQL);
            stm.setInt(1, c.getId());
            stm.setString(2, c.getData());
            stm.setString(3, c.getDescricao());
            stm.setInt(4, c.getIdAnimal());
            stm.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Erro ao inserir a consulta", e);
        }
    }

    @Override
    public void atualizar(Consulta c) throws Exception {
        try {
            String SQL = """
                    UPDATE consultas SET data = ?, descricao = ?, animal_id = ?
                    WHERE id = ?
                    """;
            PreparedStatement stm = con.prepareStatement(SQL);
            stm.setString(1, c.getData());
            stm.setString(2, c.getDescricao());
            stm.setInt(3, c.getIdAnimal());
            stm.setInt(4, c.getId());
            stm.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Erro ao atualizar a consulta", e);
        }
    }

    @Override
    public void remover(Consulta c) throws Exception {
        try {
            String SQL = """
                    DELETE FROM consultas WHERE id = ?
                    """;
            PreparedStatement stm = con.prepareStatement(SQL);
            stm.setInt(1, c.getId());
            stm.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Erro ao remover a consulta", e);
        }
    }

    @Override
    public List<Consulta> pesquisarPorAnimalId(int id) throws Exception { 
        List<Consulta> lista = new ArrayList<>();
        try {
            String SQL = """
                    SELECT * FROM consultas WHERE animal_id = ?
                    """;
            PreparedStatement stm = con.prepareStatement(SQL);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Consulta c = new Consulta();
                c.setId(rs.getInt("id"));
                c.setData(rs.getString("data"));
                c.setDescricao(rs.getString("descricao"));
                c.setAnimalId(rs.getInt("animal_id"));
                lista.add(c);
            }
        } catch (SQLException e) {
            throw new Exception("Erro ao pesquisar consultas por animal ID", e);
        }
        return lista;
    }
	
	    @Override
    public List<Consulta> pesquisarTodos() throws Exception { 
        List<Consulta> lista = new ArrayList<>();
        try {
            String SQL = """
                    SELECT * FROM consultas
                    """;
            PreparedStatement stm = con.prepareStatement(SQL);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Consulta c = new Consulta();
                c.setId(rs.getInt("id"));
                c.setData(rs.getString("data"));
                c.setDescricao(rs.getString("descricao"));
                c.setAnimalId(rs.getInt("animal_id"));
                lista.add(c);
            }
        } catch (SQLException e) {
            throw new Exception("Erro ao pesquisar consultas", e);
        }
        return lista;
    }
}
