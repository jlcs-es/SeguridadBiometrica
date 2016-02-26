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
        return huella.Match(this.getMatriz(),h2.getMatriz(),ProgramVariables.NUMBER_OF_POINTS,false);
    }


}
