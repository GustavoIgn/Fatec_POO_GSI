package edu.curso;

/*
@author:<Gustavo da Silva Ignacio 1110482313006>
 */

import java.util.HashMap;
import java.util.Map;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class PrincipalBoundary extends Application {
    private Map<String, Tela> telas = new HashMap<>();

    @Override
    public void start(Stage stage) throws Exception {
        telas.put("Consulta", new ConsultaBoundary());
        telas.put("Animal", new AnimalBoundary());
        
        BorderPane panePrincipal = new BorderPane();
        MenuBar menuBar = new MenuBar();
        Menu mnuCadastro = new Menu("Cadastro");
        Menu mnuAjuda = new Menu("Ajuda");
        
        MenuItem mnuItemConsulta = new MenuItem("Consulta");
        mnuItemConsulta.setOnAction(e -> 
            panePrincipal.setCenter(telas.get("Consulta").render())
        );
        
        MenuItem mnuItemAnimal = new MenuItem("Animal");
        mnuItemAnimal.setOnAction(e -> 
            panePrincipal.setCenter(telas.get("Animal").render())
        );
        
        MenuItem mnuItemCreditos = new MenuItem("Créditos");
        
        mnuCadastro.getItems().addAll(mnuItemAnimal, mnuItemConsulta);
        mnuAjuda.getItems().add(mnuItemCreditos);
        menuBar.getMenus().addAll(mnuCadastro, mnuAjuda);
        
        panePrincipal.setTop(menuBar);
        Scene scn = new Scene(panePrincipal, 800, 600);
        stage.setScene(scn);
        stage.setTitle("Gerenciar VetPet");
        stage.show();        
    }

    public static void main(String[] args) {
        Application.launch(PrincipalBoundary.class, args);
    }
}