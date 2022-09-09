package br.com.fiap;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import br.com.fiap.model.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;

public class SecondaryController implements Initializable {

    @FXML TableView<Usuario> tabela;

    // conexao (servidor, usuario, senha, base)
    String servidor = "oracle.fiap.com.br";
    //String baseDeDados = "";
    String username = "RM93205";
    String senha = "120804";
    String url = "jdbc:oracle:thin:@" + servidor + ":1521:orcl";

    public void carregar(){
        try {
            var conexao = conectar();
            var comando = conexao.createStatement();
            String sql = "SELECT * FROM T_DDD_LOJA_USUARIO";
            ResultSet resultado = comando.executeQuery(sql);
            
            while(resultado.next()){
                System.out.println(resultado.getString("nome"));
            }

            conexao.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }

    private Connection conectar() throws SQLException {
       
        System.out.println("Conectando...");
        Connection conexao = DriverManager.getConnection(url, username, senha);
        System.out.println("Conectado");
        return conexao;
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        carregar();
    }
}