import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

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

    public HashSet<Usuario> findMatch(Huella huella, double umbral){
        HashSet<Usuario> coincidencias = new HashSet<Usuario>();
        for (Usuario u : usuarios.values()) {
            if(u.matches(huella, umbral))
                coincidencias.add(u);
        }
        return coincidencias;
    }

}
