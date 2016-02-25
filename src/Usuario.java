import java.util.Comparator;
import java.util.HashSet;
import java.util.UUID;

/**
 * Created by José Luis on 25/02/2016.
 */
public class Usuario implements Comparable<Usuario>{
    private final HashSet<Huella> huellas = new HashSet<Huella>() ;
    private UUID id = UUID.randomUUID();
    private String name;

    public Usuario(String name){
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addHuella(Huella h){
        huellas.add(h);
    }

    public boolean matches(Huella h, double umbral){
        for (Huella huella : huellas) {
            if(huella.coincide(h,umbral))
                return true;
        }
        return false;
    }

    @Override
    public int compareTo(Usuario o) {
        return this.id.compareTo(((Usuario)o).getId());
    }

    @Override
    public String toString(){
        return "Usuario: " + id.toString() + " - " + name + " - No. huellas: " + huellas.size() + "\n";
    }
}
