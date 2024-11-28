package edu.curso;

/*
@author:<Gustavo da Silva Ignacio 1110482313006>
 */

import java.util.List;

public interface ConsultaDAO {

    void inserir( Consulta c ) throws Exception;
    void atualizar( Consulta c ) throws Exception;
    void remover( Consulta c ) throws Exception;
	List<Consulta> pesquisarPorAnimalId(int id) throws Exception;
	List<Consulta> pesquisarTodos() throws Exception;
}
