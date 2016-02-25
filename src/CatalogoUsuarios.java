import java.util.HashMap;
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

}
