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

public class ConsultaBoundary implements Tela {

    private Label lblId = new Label("");
    private TextField txtAnimalId = new TextField();
    private TextField txtData = new TextField();
    private TextField txtDescricao = new TextField();

    private ConsultaController control = null;

    private TableView<Consulta> tableView = new TableView<>();

    @Override
    public Pane render() {
        try {
            control = new ConsultaController();
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
                new Alert(Alert.AlertType.ERROR, "Erro ao gravar a consulta", ButtonType.OK).showAndWait();
            }
            tableView.refresh();
        });

        Button btnPesquisar = new Button("Pesquisar");
        btnPesquisar.setOnAction(e -> {
            try {
                control.pesquisar();
            } catch (Exception err) {
                new Alert(Alert.AlertType.ERROR, "Erro ao pesquisar por ID", ButtonType.OK).showAndWait();
            }
        });

        Button btnNovo = new Button("*");
        btnNovo.setOnAction(e -> control.limparTudo());

        paneForm.add(new Label("Id: "), 0, 0);
        paneForm.add(lblId, 1, 0);
        paneForm.add(new Label("Animal Id: "), 0, 1);
        paneForm.add(txtAnimalId, 1, 1);
        paneForm.add(btnNovo, 2, 1);
        paneForm.add(new Label("Data: "), 0, 2);
        paneForm.add(txtData, 1, 2);
        paneForm.add(new Label("Descrição: "), 0, 3);
        paneForm.add(txtDescricao, 1, 3);

        paneForm.add(btnGravar, 0, 4);
        paneForm.add(btnPesquisar, 1, 4);

        ligacoes();
        gerarColunas();

        panePrincipal.setTop(paneForm);
        panePrincipal.setCenter(tableView);

        return panePrincipal;
    }

    public void gerarColunas() {
        TableColumn<Consulta, Integer> col1 = new TableColumn<>("Id");
        col1.setCellValueFactory(new PropertyValueFactory<Consulta, Integer>("id"));

        TableColumn<Consulta, Integer> col2 = new TableColumn<>("Animal Id");
        col2.setCellValueFactory(new PropertyValueFactory<Consulta, Integer>("idAnimal"));

        TableColumn<Consulta, String> col3 = new TableColumn<>("Data");
        col3.setCellValueFactory(new PropertyValueFactory<Consulta, String>("data"));

        TableColumn<Consulta, String> col4 = new TableColumn<>("Descrição");
        col4.setCellValueFactory(new PropertyValueFactory<Consulta, String>("descricao"));

        tableView.getSelectionModel().selectedItemProperty()
            .addListener((obs, antigo, novo) -> {
                if (novo != null) {
                    System.out.println("Consulta selecionada: " + novo.getDescricao());
                    control.paraTela(novo);
                }
            });

        Callback<TableColumn<Consulta, Void>, TableCell<Consulta, Void>> cb = new Callback<>() {
					@Override
                    public TableCell<Consulta, Void> call(TableColumn<Consulta, Void> param) {
						TableCell<Consulta, Void> celula = new TableCell<>() {
							final Button btnApagar = new Button("Apagar");
							
							{
								btnApagar.setOnAction( e-> {
									Consulta c = tableView.getItems().get(getIndex());
									try {
										control.excluir(c);
									} catch (Exception err) {
										new Alert(AlertType.ERROR, "Erro ao excluir a Consulta", ButtonType.OK).showAndWait();
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
			                 
        TableColumn<Consulta, Void> col5 = new TableColumn<>("Ação");
        col5.setCellFactory(cb);

        tableView.getColumns().addAll(col1, col2, col3, col4, col5);
        tableView.setItems(control.getLista());
    }

    public void ligacoes() {
        control.idProperty().addListener((obs, antigo, novo) -> lblId.setText(String.valueOf(novo)));
		IntegerStringConverter intergerConverter = new IntegerStringConverter();
		Bindings.bindBidirectional(txtAnimalId.textProperty(), control.animalIdProperty(), (StringConverter) intergerConverter);
        Bindings.bindBidirectional(control.dataProperty(), txtData.textProperty());
        Bindings.bindBidirectional(control.descricaoProperty(), txtDescricao.textProperty());
    }
}
