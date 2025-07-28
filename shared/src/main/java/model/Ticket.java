package model;

import java.time.LocalDateTime;

public class Ticket {

    //Identificador único do Ticket (chave primária)
    private int id;

    //titulo do Ticket
    private String title;

    //Descrição breve do Ticket
    private String description;

    //ID do Status do Ticket
    private int status_id;

    //ID do User que criou Ticket
    private int created_by;

    //ID do User com a role Técnico responsável pelo Ticket
    private Integer assigned_to;

    //Timestamp de quando o comentário foi enviado
    private LocalDateTime created_at;

    //Constructor
    public Ticket() {}

    public Ticket(int id, String title, String description, int status_id, int created_by, Integer assigned_to) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status_id = status_id;
        this.created_by = created_by;
        this.assigned_to = assigned_to;
    }
    //
    /*
    Gets e Setters do Objeto Ticket
     */

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public int getStatus_id() { return status_id; }
    public void setStatus_id(int status_id) { this.status_id = status_id; }
    public int getCreated_by() { return created_by; }
    public void setCreated_by(int created_by) { this.created_by = created_by; }
    public Integer getAssigned_to() { return assigned_to; }
    public void setAssigned_to(Integer assigned_to) { this.assigned_to = assigned_to; }
    public LocalDateTime getCreated_at() { return created_at; }
    public void setCreated_at(LocalDateTime created_at) { this.created_at = created_at; }


}
