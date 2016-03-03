import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.lang.Exception;
import java.util.HashSet;


public class CEntityForm extends JFrame {

    private JButton calculationButton = new JButton("Calcular 1 huella");
    private JButton compareButton = new JButton("Comparar 2 huellas");
    private JButton oneToOneMatchButton = new JButton("1 to 1 Match");
    private JButton oneToManyMatchButton = new JButton("1 to N Match");
    private JButton newuserformButton = new JButton("Nuevo usuario");
    private JTextField matriz1TextField = new JTextField("Matriz huella IZQ");
    private JTextField matriz2TextField = new JTextField("Matriz huella DER*");
    private Label lblPoints = new Label("Nº puntos comparados: " + ProgramVariables.NUMBER_OF_POINTS);
    private Label lblPercentage = new Label("Mínimo % match: " + ProgramVariables.PERCENTAGE_OF_SIMILARITY);
    private JTextPane panelTexto = new JTextPane();


    private BJPanel m_panel1 = new BJPanel();
    private BJPanel m_panel2 = new BJPanel();



    public CEntityForm() {

        panelTexto.setEditable(false);
        this.getContentPane().setLayout(new GridLayout(2, 2));
        this.getContentPane().add(m_panel1);
        this.getContentPane().add(m_panel2);
        this.getContentPane().add(panelBotones());
        this.getContentPane().add(panelTexto);
        menuBar();

        this.setTitle("Entity");
        this.setSize(new Dimension(670, 750));


        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private JPanel panelBotones(){
        JPanel panel = new JPanel();
        calculationButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                calculateAction();
            }
        });
        compareButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                compareAction();
            }
        });
        oneToOneMatchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                oneToOneMatchAction();
            }
        });
        oneToManyMatchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                oneToManyMatchAction();
            }
        });
        newuserformButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newUserAction();
            }
        });

        panel.setLayout(new GridLayout(4, 1));
        panel.add(calculationButton);
        panel.add(compareButton);
        panel.add(oneToOneMatchButton);
        panel.add(oneToManyMatchButton);
        panel.add(matriz1TextField);
        panel.add(matriz2TextField);
        panel.add(newuserformButton);

        JPanel jp = new JPanel();
        jp.setLayout(new FlowLayout());
        jp.add(lblPoints);
        jp.add(lblPercentage);
        jp.add(new Label("Ver menú 'Parámetros'"));
        panel.add(jp);

        return panel;
    }


    private void newUserAction(){
        JFrame NUForm = new NuevoUsuarioForm();
        NUForm.setVisible(true);
    }

    private void calculateAction() {

        JFileChooser fc = new JFileChooser(new java.io.File("."));

        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fc.setMultiSelectionEnabled(false);
        int seleccion = fc.showOpenDialog(this);

        File fichero;

        if (seleccion == JFileChooser.APPROVE_OPTION) {
            fichero = fc.getSelectedFile();
            Huella huella = new Huella(fichero);

            m_panel1.setBufferedImage(huella.getFoto());
            m_panel2.setBufferedImage(huella.getFotoDetalle());
            matriz1TextField.setText(huella.getMatrizString());

        }

        panelTexto.setText("[IZQ] huella elegida  --  [DER] detalles\n");

    }

    private void compareAction() {

        Huella huella = null;
        Huella huella2 = null;

        JFileChooser fc = new JFileChooser(new java.io.File("."));

        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fc.setMultiSelectionEnabled(false);
        int seleccion = fc.showOpenDialog(this);

        File fichero;

        if (seleccion == JFileChooser.APPROVE_OPTION) {
            fichero = fc.getSelectedFile();
            huella = new Huella(fichero);

            m_panel1.setBufferedImage(huella.getFotoDetalle());
            matriz1TextField.setText(huella.getMatrizString());

        }

        seleccion = fc.showOpenDialog(this);

        if (seleccion == JFileChooser.APPROVE_OPTION) {
            fichero = fc.getSelectedFile();
            huella2 = new Huella(fichero);

            m_panel2.setBufferedImage(huella2.getFotoDetalle());
            matriz2TextField.setText(huella2.getMatrizString());

        }

        panelTexto.setText("[IZQ] huella 1  --  [DER] huella 2\n" +
                "Porcentaje: " + huella.coincidencia(huella2) +"%\n");

    }

    private void oneToOneMatchAction() {


        JFileChooser fc = new JFileChooser(new java.io.File("."));

        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fc.setMultiSelectionEnabled(false);
        int seleccion = fc.showOpenDialog(this);

        File fichero;

        if (seleccion == JFileChooser.APPROVE_OPTION) {
            fichero = fc.getSelectedFile();
            final Huella huella = new Huella(fichero);

            m_panel1.setBufferedImage(huella.getFotoDetalle());
            matriz1TextField.setText(huella.getMatrizString());

            //Choose user
            final JFrame jf = new JFrame();
            jf.setLayout(new GridLayout(2,1));
            jf.setSize(new Dimension(300,100));
            final JComboBox<Usuario> cb = new JComboBox<Usuario>(CatalogoUsuarios.getInstance().getUsuarios());
            jf.getContentPane().add(cb);
            JButton bton = new JButton("OK");
            bton.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Usuario chosen = (Usuario) cb.getSelectedItem();
                    jf.dispose();
                    double porcentaje = chosen.bestMatchScore(huella);
                    Huella h2 = chosen.bestMatch(huella);
                    m_panel2.setBufferedImage(h2.getFotoDetalle());
                    matriz2TextField.setText(h2.getMatrizString());

                    panelTexto.setText( chosen + "\n" +
                            "Mejor porcentaje entre las huellas registradas del usuario: " + porcentaje + "%\n" +
                            "\n[IZQ] huella elegida  --  [DER] huella del usuario\n"
                    );
                }
            });
            jf.getContentPane().add(bton);
            jf.setVisible(true);


        }

    }


    private void oneToManyMatchAction() {
        JFileChooser fc = new JFileChooser(new java.io.File("."));

        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fc.setMultiSelectionEnabled(false);
        int seleccion = fc.showOpenDialog(this);

        File fichero;

        if (seleccion == JFileChooser.APPROVE_OPTION) {
            fichero = fc.getSelectedFile();

            Huella huella = new Huella(fichero);

            m_panel1.setBufferedImage(huella.getFoto());
            m_panel2.setBufferedImage(huella.getFotoDetalle());
            matriz1TextField.setText(huella.getMatrizString());

            HashSet<Usuario> usuarios = CatalogoUsuarios.getInstance().findMatch(huella);
            String text = "";
            for(Usuario u : usuarios){
                text += u.bestMatchScore(huella) + "% - " + u.toString() + "\n";
            }

            panelTexto.setText(text + "\n[IZQ] huella elegida  --  [DER] detalle huella elegida");
        }

    }


    private void menuBar(){
        JMenuBar menuBar;
        JMenu menu, submenu;
        JMenuItem menuItem;
        JRadioButtonMenuItem rbMenuItem;
        JCheckBoxMenuItem cbMenuItem;


        menuBar = new JMenuBar();

        menu = new JMenu("Parámetros");

        menuItem = new JMenuItem("Puntos entre huellas");
        menuItem.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final JFrame jf = new JFrame();
                jf.getContentPane().setLayout(new GridLayout(3,1));
                jf.getContentPane().add(new Label("Número de puntos a comparar:"));
                final JFormattedTextField tf = new JFormattedTextField(new Integer(ProgramVariables.NUMBER_OF_POINTS));
                jf.getContentPane().add(tf);
                JButton btn = new JButton("Guardar");
                btn.addActionListener(new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ProgramVariables.NUMBER_OF_POINTS = (Integer)tf.getValue();
                        lblPoints.setText("Nº puntos comparados: " + ProgramVariables.NUMBER_OF_POINTS);
                        jf.dispose();
                    }
                });
                jf.getContentPane().add(btn);
                jf.setSize(new Dimension(200,100));
                jf.setVisible(true);
            }
        });
        menu.add(menuItem);


        menuItem = new JMenuItem("Porcentaje de similitud");
        menuItem.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final JFrame jf = new JFrame();
                jf.getContentPane().setLayout(new GridLayout(3,1));
                jf.getContentPane().add(new Label("Porcentaje mínimo de similitud:"));
                final JFormattedTextField tf = new JFormattedTextField(new Double(ProgramVariables.PERCENTAGE_OF_SIMILARITY));
                jf.getContentPane().add(tf);
                JButton btn = new JButton("Guardar");
                btn.addActionListener(new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ProgramVariables.PERCENTAGE_OF_SIMILARITY = (Double) tf.getValue();
                        lblPercentage.setText("Mínimo % match: " + ProgramVariables.PERCENTAGE_OF_SIMILARITY);
                        jf.dispose();
                    }
                });
                jf.getContentPane().add(btn);
                jf.setSize(new Dimension(200,100));
                jf.setVisible(true);
            }
        });
        menu.add(menuItem);

        menuBar.add(menu);


        menu = new JMenu("Usuarios");

        menuItem = new JMenuItem("Nuevo Usuario");
        menuItem.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newUserAction();
            }
        });
        menu.add(menuItem);

        menuItem = new JMenuItem("1 to N match");
        menuItem.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                oneToManyMatchAction();
            }
        });
        menu.add(menuItem);


        menuItem = new JMenuItem("1 to 1 match");
        menuItem.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                oneToOneMatchAction();
            }
        });
        menu.add(menuItem);

        menuBar.add(menu);


        this.setJMenuBar(menuBar);
    }

}
