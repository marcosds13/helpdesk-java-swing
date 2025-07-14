package model;

public class Status {

    //Identificador de Status do Ticket (chave primária)
    private int id;
    //Nome de Status do Ticket
    private String name;

    //Constructor
    public Status() {}

    public Status(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Gets e Setters do Objeto Status
     * **/

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    // Este metodo é chamado automaticamente quando se tenta converter o objeto para String
    // Por exemplo, ao preencher uma JComboBox, será mostrada o nome do Status
    @Override
    public String toString() { return name; };
}

