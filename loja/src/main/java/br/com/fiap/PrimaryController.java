package br.com.fiap;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ResourceBundle;

import br.com.fiap.model.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class PrimaryController implements Initializable {

    @FXML private TextField textFieldNome;
    @FXML private TextField textFieldEmail;
    @FXML private PasswordField passwordField;
    @FXML private ChoiceBox<String> choiceBoxPerfil;
    
    // conexao (servidor, usuario, senha, base)
    String servidor = "oracle.fiap.com.br";
    //String baseDeDados = "";
    String username = "RM93205";
    String senha = "120804";
    String url = "jdbc:oracle:thin:@" + servidor + ":1521:orcl";
    
    public void salvar(){
        var usuario = carregarDadosDoFormulario();
        System.out.println(usuario);

        try{
           var conexao = conectar();
           Statement comando = conexao.createStatement();
           String sql = String.format( "INSERT INTO T_DDD_LOJA_USUARIO (ID, NOME, EMAIL, SENHA, PERFIL) VALUES" +
                        "(SEQ_USUARIO.nextval, '%s', '%s', '%s', '%s')",
                        usuario.getNome(),
                        usuario.getEmail(),
                        usuario.getSenha(),
                        usuario.getPerfil()
           );

           comando.execute(sql);
           conexao.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    private Connection conectar() throws SQLException {
       
            System.out.println("Conectando...");
            Connection conexao = DriverManager.getConnection(url, username, senha);
            System.out.println("Conectado");
            return conexao;
      
    }

    private Usuario carregarDadosDoFormulario() {
        return new Usuario(
            textFieldNome.getText(), 
            textFieldEmail.getText(), 
            passwordField.getText(), 
            choiceBoxPerfil.getValue()
        );
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        choiceBoxPerfil.getItems().addAll("Vendedor", "Gerente", "Administrador");  
    }

    public void abrirListaDeUsuarios() throws IOException{
        App.setRoot("secondary");
    }
}
