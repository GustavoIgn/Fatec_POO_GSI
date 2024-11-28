package edu.curso;

/*
@author:<Gustavo da Silva Ignacio 1110482313006>
 */

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AnimalController {

    private ObservableList<Animal> lista = FXCollections.observableArrayList();
    private int contador = 2;

    private IntegerProperty id = new SimpleIntegerProperty(0);
    private StringProperty nome = new SimpleStringProperty("");
    private StringProperty especie = new SimpleStringProperty("");
    private StringProperty raca = new SimpleStringProperty("");
    private IntegerProperty idade = new SimpleIntegerProperty(0);
    private StringProperty cpfDono = new SimpleStringProperty("");

    private AnimalDAO animalDAO = null;

    public AnimalController() throws Exception {
        animalDAO = new AnimalDAOImpl();
    }

    public Animal paraEntidade() {
        Animal animal = new Animal();
        animal.setId(id.get());
        animal.setNome(nome.get());
        animal.setEspecie(especie.get());
        animal.setRaca(raca.get());
        animal.setIdade(idade.get());
        animal.setCPFDono(cpfDono.get());
        return animal;
    }

    public void excluir(Animal animal) throws Exception {
        animalDAO.remover(animal);
        pesquisarTodos();
    }

    public void limparTudo() {
        id.set(0);
        nome.set("");
        especie.set("");
        raca.set("");
        idade.set(0);
        cpfDono.set("");
    }

    public void paraTela(Animal animal) {
        if (animal != null) {
            id.set(animal.getId());
            nome.set(animal.getNome());
            especie.set(animal.getEspecie());
            raca.set(animal.getRaca());
            idade.set(animal.getIdade());
            cpfDono.set(animal.getCPFDono());
        }
    }

    public void gravar() throws Exception {
        Animal animal = paraEntidade();
        if (animal.getId() == 0) {
            this.contador += 1;
            animal.setId(this.contador);
            animalDAO.inserir(animal);
        } else {
            animalDAO.atualizar(animal);
        }
        pesquisarTodos();
    }

    public void pesquisar() throws Exception {
        lista.clear();
        lista.addAll(animalDAO.pesquisarPorNome(nome.get()));
    }

    public void pesquisarTodos() throws Exception {
        lista.clear();
        lista.addAll(animalDAO.pesquisarPorNome(""));
    }

    public IntegerProperty idProperty() {
        return this.id;
    }

    public StringProperty nomeProperty() {
        return this.nome;
    }

    public StringProperty especieProperty() {
        return this.especie;
    }

    public StringProperty racaProperty() {
        return this.raca;
    }

    public IntegerProperty idadeProperty() {
        return this.idade;
    }

    public StringProperty cpfDonoProperty() {
        return this.cpfDono;
    }

    public ObservableList<Animal> getLista() {
        return this.lista;
    }
}
