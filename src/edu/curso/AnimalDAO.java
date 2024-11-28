package edu.curso;

/*
@author:<Gustavo da Silva Ignacio 1110482313006>
 */

import java.util.List;

public interface AnimalDAO {
    
    void inserir(Animal a ) throws Exception;
    void atualizar( Animal a ) throws Exception;
    void remover( Animal a ) throws Exception;
    List<Animal> pesquisarPorNome( String nome ) throws Exception;
    
}
