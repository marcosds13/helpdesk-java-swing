package main;

// importa a interface de login
import controller.LoginController;

/**
 * Classe principal para arrancar a aplicação e.
 * Esta classe serve como ponto de entrada da aplicação,
 */
public class Main {

    public static void main(String[] args) {
        // inicia a interface de login, passando os argumentos da consola
        new LoginController();
    }
}
