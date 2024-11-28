package edu.curso;

/*
@author:<Gustavo da Silva Ignacio 1110482313006>
 */

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ConsultaController {

    private ObservableList<Consulta> lista = FXCollections.observableArrayList();
    private int contador = 2;

    private IntegerProperty id = new SimpleIntegerProperty(0);
    private StringProperty data = new SimpleStringProperty("");
    private StringProperty descricao = new SimpleStringProperty("");
    private IntegerProperty animalId = new SimpleIntegerProperty(0);

    private ConsultaDAO consultaDAO = null;

    public ConsultaController() throws Exception {
         consultaDAO = new ConsultaDAOImpl();
    }

    public Consulta paraEntidade() {
        Consulta consulta = new Consulta();
        consulta.setId(id.get());
        consulta.setData(data.get());
        consulta.setDescricao(descricao.get());
        consulta.setAnimalId(animalId.get());
        return consulta;
    }

    public void excluir(Consulta consulta) throws Exception {
        consultaDAO.remover(consulta);
        pesquisarTodos();
    }

    public void limparTudo() {
        id.set(0);
        data.set("");
        descricao.set("");
        animalId.set(0);
    }

    public void paraTela(Consulta consulta) {
        if (consulta != null) {
            id.set(consulta.getId());
            data.set(consulta.getData());
            descricao.set(consulta.getDescricao());
            animalId.set(consulta.getIdAnimal());
        }
    }

    public void gravar() throws Exception {
        Consulta consulta = paraEntidade();
        if (consulta.getId() == 0) {
            this.contador += 1;
            consulta.setId(this.contador);
            consultaDAO.inserir(consulta);
        } else {
            consultaDAO.atualizar(consulta);
        }
        pesquisarTodos();
    }

    public void pesquisar() throws Exception {
        lista.clear();
        lista.addAll(consultaDAO.pesquisarPorAnimalId(this.animalId.get()));
    }

    public void pesquisarTodos() throws Exception {
        lista.clear();
        lista.addAll(consultaDAO.pesquisarTodos());
    }

    public IntegerProperty idProperty() {
        return this.id;
    }

    public StringProperty dataProperty() {
        return this.data;
    }

    public StringProperty descricaoProperty() {
        return this.descricao;
    }

    public IntegerProperty animalIdProperty() {
		return this.animalId;
	}

    public ObservableList<Consulta> getLista() {
        return this.lista;
    }
}
