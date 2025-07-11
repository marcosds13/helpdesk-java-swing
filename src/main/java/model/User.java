package model;

/**
 * Classe modelo que representa um username no sistema
 * Guarda os dados básicos de autenticação e associação de role
 */
public class User {

    //identificador único de user (chave primária=
    private int id;
    //username usado no login
    private String username;
    //password cifrada (hash)
    private String password;
    //nome completo do user
    private String name;
    //email do user
    private String email;
    //role associado ao user
    private int role_id;

    /**
     * Gets e Setters do Objeto User
     * **/

    private int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public int getRole_id() { return role_id; }
    public void setRole_id(int role_id) { this.role_id = role_id; }
}
