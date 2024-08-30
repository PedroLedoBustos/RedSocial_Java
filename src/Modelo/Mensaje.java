package Modelo;

public class Mensaje {
    private Integer id;
    private String texto;
    private Integer emisario;
    private Integer destinatario;
    
    public Mensaje(String texto, Integer emisario,Integer destinatario){
        this.texto= texto;
        this.emisario=emisario;
        this.destinatario=destinatario;
    }

    public Mensaje(Integer id, String texto, Integer emisario,Integer destinatario){
        this.id=id;
        this.texto= texto;
        this.emisario=emisario;
        this.destinatario=destinatario;
    }

    public Integer getId(){
        return this.id;
    }

    public String getTexto(){
        return this.texto;
    }

    public Integer getEmisario(){
        return this.emisario;
    }

    public Integer getDestinatario(){
        return this.destinatario;
    }

    

}
