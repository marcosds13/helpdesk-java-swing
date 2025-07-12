package model;

import java.time.LocalDateTime;

public class Comment {

    //Identificador único do Comentário (chave primária)
    private int id;

    //Identificador do Ticket onde comentário foi efetuado
    private int ticket_id;

    //Identificador do User que fez comentário
    private int user_id;

    //Mensagem do comentário
    private String message;

    //Timestamp de quando o comentário foi enviado
    private LocalDateTime sent_at;

    public Comment() {}
    public Comment(int id, int ticket_id, int user_id, String message) {
        this.id = id;
        this.ticket_id = ticket_id;
        this.user_id = user_id;
        this.message = message;
        this.sent_at = LocalDateTime.now();
    }

     /*
    Gets e Setters do Objeto Comment
     */

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getTicket_id() { return ticket_id; }
    public void setTicket_id(int ticket_id) { this.ticket_id = ticket_id; }
    public int getUser_id() { return user_id; }
    public void setUser_id(int user_id) { this.user_id = user_id; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public LocalDateTime getSent_at() { return sent_at; }
    public void setSent_at(LocalDateTime sent_at) { this.sent_at = sent_at; }

}
