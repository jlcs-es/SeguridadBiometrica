import java.util.Comparator;
import java.util.HashSet;
import java.util.UUID;

/**
 * Created by José Luis on 25/02/2016.
 */
public class Usuario implements Comparable<Object>{
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

    @Override
    public int compareTo(Object o) {
        if(o instanceof Usuario)
            return this.id.compareTo(((Usuario)o).getId());
        if(o instanceof Huella)
            return 0;
        return 0;
    }
}
