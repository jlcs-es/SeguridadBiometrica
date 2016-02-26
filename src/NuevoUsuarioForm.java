import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by JoséLuis on 25/02/2016.
 */
public class NuevoUsuarioForm extends JFrame {
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
        this.setSize(new Dimension(400, 125));
        this.setTitle("Nuevo Usuario");

        this.getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
        this.getContentPane().add(new JLabel("Nombre usuario:"));
        this.getContentPane().add(nombreTF);
        chooseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choseFiles(e);
            }
        });
        this.getContentPane().add(chooseButton);
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(nombreTF.getText().equals("")) {
                    JOptionPane.showMessageDialog(new JFrame() , "El nombre está vacío.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                Usuario u = new Usuario(nombreTF.getText());
                for (File f : ficheros) {
                    Huella h = new Huella(f);
                    u.addHuella(h);
                }
                CatalogoUsuarios.getInstance().addUsuario(u);
                dispose();
            }
        });
        this.add(saveButton);

    }

}
