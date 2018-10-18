package utcrudbasico.ubyquit.com.utcrudbasico;

public class Comentario {
    //compas para la db
    //Variables
    int id;
    String nombre , comentario;

    //Contructor
    public Comentario(int _id, String _nombre,String _comentario){
        id = _id;
        nombre = _nombre;
        comentario = _comentario;
    }

  //objeto como cadena de texto
    @Override
    public String toString () {return nombre;}

    //metodos de acceso a cade atributo de la clase

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getComentario() {
        return comentario;
    }


}
