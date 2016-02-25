import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by JoséLuis on 25/02/2016.
 */
public class NuevoUsuarioForm extends JPanel {
    private JTextField nombreTF = new JTextField();
    private JButton chooseButton = new JButton("Huellas...");
    private File[] ficheros;
    private JButton saveButton = new JButton("Guardar");


    private void choseFiles(ActionEvent e) {
        JFileChooser fc = new JFileChooser(new java.io.File("."));

        fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        fc.setMultiSelectionEnabled(true);
        int seleccion = fc.showOpenDialog(this);

        if (seleccion == JFileChooser.APPROVE_OPTION) {
            ficheros = fc.getSelectedFiles();
        }
    }


    public NuevoUsuarioForm(){
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.add(new JLabel("Nombre usuario:"));
        this.add(nombreTF);
        chooseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choseFiles(e);
            }
        });
        this.add(chooseButton);
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ///TODO: no se comprueba que haya puesto el nombre antes
                Usuario u = new Usuario(nombreTF.getText());
                for (File f : ficheros) {
                    Huella h = new Huella(f);
                    u.addHuella(h);
                }
                CatalogoUsuarios.getInstance().addUsuario(u);
            }
        });
        this.add(saveButton);
    }

}
