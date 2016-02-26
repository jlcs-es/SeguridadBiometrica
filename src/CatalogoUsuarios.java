import java.util.*;

/**
 * Created by José Luis on 25/02/2016.
 */
public class CatalogoUsuarios {
    HashMap<UUID,Usuario> usuarios = new HashMap<UUID, Usuario>();

    private static CatalogoUsuarios catalogo = new CatalogoUsuarios();

    public static CatalogoUsuarios getInstance(){
        return catalogo;
    }

    public void addUsuario(Usuario u){
        usuarios.put(u.getId(), u);

        System.out.println("Nuevo usuario " + u.getName()  + "::" + u.getId());
    }

    public Usuario getUsuario(UUID id){
        return usuarios.get(id);
    }

    public Vector<Usuario> getUsuarios(){
        return new Vector<Usuario>(usuarios.values());
    }

    public HashSet<Usuario> findMatch(Huella huella){
        HashSet<Usuario> coincidencias = new HashSet<Usuario>();
        for (Usuario u : usuarios.values()) {
            if(u.bestMatchScore(huella)>ProgramVariables.PERCENTAGE_OF_SIMILARITY)
                coincidencias.add(u);
        }
        return coincidencias;
    }

}
