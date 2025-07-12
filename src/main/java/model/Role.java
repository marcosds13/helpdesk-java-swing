package model;

public class Role {

    //Identificador único da Role (chave primária)
    private int id;
    //Nome da Role
    private String name;

    public Role() {}
    public Role(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /*
    Gets e Setters do Objeto Role
     */
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
