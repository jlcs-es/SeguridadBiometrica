import java.util.Comparator;
import java.util.HashSet;
import java.util.UUID;

/**
 * Created by José Luis on 25/02/2016.
 */
public class Usuario {
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

    public double bestMatchScore(Huella h){
        double max = 0.;

        for (Huella huella : huellas) {
            double ch = huella.coincidencia(h);
            if(ch>max)
                max = ch;
        }
        return max;
    }

    public Huella bestMatch(Huella h){
        double max = 0.;
        Huella bm = null;
        for (Huella huella : huellas) {
            double ch = huella.coincidencia(h);
            if(ch>max) {
                max = ch;
                bm = huella;
            }
        }
        return bm;
    }

    @Override
    public String toString(){
        return "Usuario: " + name + " - Nºhuellas: " + huellas.size() + " - ID: " + id.toString()  + "\n";
    }
}
