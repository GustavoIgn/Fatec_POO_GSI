package edu.curso;

/*
@author:<Gustavo da Silva Ignacio 1110482313006>
 */

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;

public class AnimalBoundary implements Tela {

    private Label lblId = new Label("");
    private TextField txtNome = new TextField();
    private TextField txtEspecie = new TextField();
    private TextField txtRaca = new TextField();
    private TextField txtIdade = new TextField();
    private TextField txtCpfDono = new TextField();

    private AnimalController control = null;

    private TableView<Animal> tableView = new TableView<>();

    @Override
    public Pane render() {
        try {
            control = new AnimalController();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Erro ao iniciar o sistema", ButtonType.OK).showAndWait();
        }

        BorderPane panePrincipal = new BorderPane();
        GridPane paneForm = new GridPane();

        Button btnGravar = new Button("Gravar");
        btnGravar.setOnAction(e -> {
            try {
                control.gravar();
            } catch (Exception err) {
                new Alert(Alert.AlertType.ERROR, "Erro ao gravar o animal", ButtonType.OK).showAndWait();
            }
            tableView.refresh();
        });

        Button btnPesquisar = new Button("Pesquisar");
        btnPesquisar.setOnAction(e -> {
            try {
                control.pesquisar();
            } catch (Exception err) {
                new Alert(Alert.AlertType.ERROR, "Erro ao pesquisar por nome", ButtonType.OK).showAndWait();
            }
        });

        Button btnNovo = new Button("*");
        btnNovo.setOnAction(e -> control.limparTudo());

        paneForm.add(new Label("Id: "), 0, 0);
        paneForm.add(lblId, 1, 0);
        paneForm.add(new Label("Nome: "), 0, 1);
        paneForm.add(txtNome, 1, 1);
        paneForm.add(btnNovo, 2, 1);
        paneForm.add(new Label("Espécie: "), 0, 2);
        paneForm.add(txtEspecie, 1, 2);
        paneForm.add(new Label("Raça: "), 0, 3);
        paneForm.add(txtRaca, 1, 3);
        paneForm.add(new Label("Idade: "), 0, 4);
        paneForm.add(txtIdade, 1, 4);
        paneForm.add(new Label("CPF Dono: "), 0, 5);
        paneForm.add(txtCpfDono, 1, 5);

        paneForm.add(btnGravar, 0, 6);
        paneForm.add(btnPesquisar, 1, 6);

        ligacoes();
        gerarColunas();

        panePrincipal.setTop(paneForm);
        panePrincipal.setCenter(tableView);

        return panePrincipal;
    }

    public void gerarColunas() {
        TableColumn<Animal, Integer> col1 = new TableColumn<>("Id");
        col1.setCellValueFactory(new PropertyValueFactory<Animal, Integer>("id"));

        TableColumn<Animal, String> col2 = new TableColumn<>("Nome");
        col2.setCellValueFactory(new PropertyValueFactory<Animal, String>("nome"));

        TableColumn<Animal, String> col3 = new TableColumn<>("Espécie");
        col3.setCellValueFactory(new PropertyValueFactory<Animal, String>("especie"));

        TableColumn<Animal, String> col4 = new TableColumn<>("Raça");
        col4.setCellValueFactory(new PropertyValueFactory<Animal, String>("raca"));

        TableColumn<Animal, Integer> col5 = new TableColumn<>("Idade");
        col5.setCellValueFactory(new PropertyValueFactory<Animal, Integer>("idade"));

        TableColumn<Animal, String> col6 = new TableColumn<>("CPF Dono");
        col6.setCellValueFactory(new PropertyValueFactory<Animal, String>("cpfDono"));

        tableView.getSelectionModel().selectedItemProperty()
                .addListener((obs, antigo, novo) -> {
                    if (novo != null) {
                        control.paraTela(novo);
                    }
                });

        Callback<TableColumn<Animal, Void>, TableCell<Animal, Void>> cb = new Callback<>() {
                    @Override
                    public TableCell<Animal, Void> call(TableColumn<Animal, Void> param) {
						TableCell<Animal, Void> celula = new TableCell<>() {
							final Button btnApagar = new Button("Apagar");
							
							{
								btnApagar.setOnAction( e-> {
									Animal animal = tableView.getItems().get(getIndex());
									try {
										control.excluir(animal);
									} catch (Exception err) {
										new Alert(AlertType.ERROR, "Erro ao excluir o animal", ButtonType.OK).showAndWait();
									}
								});
							}
						
							@Override
							public void updateItem(Void item, boolean empty) {
								if (!empty) {
									setGraphic(btnApagar);
								} else {
									setGraphic(null);
								}
							}
						};
					return celula;
				}
			};

        TableColumn<Animal, Void> col7 = new TableColumn<>("Ação");
        col7.setCellFactory(cb);

        tableView.getColumns().addAll(col1, col2, col3, col4, col5, col6, col7);
        tableView.setItems(control.getLista());
    }

    public void ligacoes() {
    	IntegerStringConverter integerConverter = new IntegerStringConverter();
        control.idProperty().addListener((obs, antigo, novo) -> lblId.setText(String.valueOf(novo)));
        Bindings.bindBidirectional(control.nomeProperty(), txtNome.textProperty());
        Bindings.bindBidirectional(control.especieProperty(), txtEspecie.textProperty());
        Bindings.bindBidirectional(control.racaProperty(), txtRaca.textProperty());
        Bindings.bindBidirectional(txtIdade.textProperty(), control.idadeProperty(), (StringConverter) integerConverter);
        Bindings.bindBidirectional(control.cpfDonoProperty(), txtCpfDono.textProperty());
    }
}
