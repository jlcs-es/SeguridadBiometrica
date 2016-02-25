import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by José Luis on 25/02/2016.
 */
public class Huella {
    private BufferedImage foto;
    private CFingerPrint huella = new CFingerPrint();

    public Huella(String ruta){
        this(new File(ruta));
    }

    public Huella(File fichero){
        try {
            foto = ImageIO.read(fichero) ;
            huella.setFingerPrintImage(foto);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedImage getFoto(){
        return foto;
        //return huella.getFingerPrintImage();
    }

    public BufferedImage getFotoDetalle(){
        return huella.getFingerPrintImageDetail();
    }

    public double[] getMatriz(){
        return huella.getFingerPrintTemplate();
    }

    public String getMatrizString(){
        return huella.ConvertFingerPrintTemplateDoubleToString(huella.getFingerPrintTemplate());
    }

    public double coincidencia(Huella h2){
        return coincidencia(h2, 65);
    }

    public double coincidencia(Huella h2, int threshold){
        return huella.Match(this.getMatriz(),h2.getMatriz(),threshold,false);
    }

    public boolean coincide(Huella h2, double umbral){
        ///TODO: ¿el 65 de CEntityForm de donde sale?
        return umbral < huella.Match(this.getMatriz(),h2.getMatriz(),65,false);
    }


}
