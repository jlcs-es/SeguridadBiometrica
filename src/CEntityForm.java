import java.awt.*;
import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.event.*;
import java.lang.Exception;
import java.util.HashSet;


public class CEntityForm extends JFrame {

    private JButton calculationButton = new JButton("Calculation");
    private JButton imageProcessingButton = new JButton("Image Processing");
    private JButton oneToOneMatchButton = new JButton("1 to 1 Match");
    private JButton oneToManyMatchButton = new JButton("1 to m Match");
    private JButton newuserformButton = new JButton("Nuevo usuario");
    private JTextField matriz1TextField = new JTextField();
    private JTextField matriz2TextField = new JTextField();
    private JTextPane panelTexto = new JTextPane();

//    private CFingerPrint m_finger1 = new CFingerPrint();
//    private CFingerPrint m_finger2 = new CFingerPrint();
//    private CFingerPrintGraphics m_fingergfx = new CFingerPrintGraphics();
    private BJPanel m_panel1 = new BJPanel();
    private BJPanel m_panel2 = new BJPanel();
//    private BufferedImage m_bimage1 = new BufferedImage(m_finger1.FP_IMAGE_WIDTH, m_finger1.FP_IMAGE_HEIGHT, BufferedImage.TYPE_INT_RGB);
//    private BufferedImage m_bimage2 = new BufferedImage(m_finger2.FP_IMAGE_WIDTH, m_finger2.FP_IMAGE_HEIGHT, BufferedImage.TYPE_INT_RGB);
//    private double finger1[] = new double[m_finger1.FP_TEMPLATE_MAX_SIZE];
//    private double finger2[] = new double[m_finger2.FP_TEMPLATE_MAX_SIZE];

    private Huella h1 = new Huella(new java.io.File("").getAbsolutePath() + "/ProcessedSample1.bmp");
    private Huella h2 = new Huella(new java.io.File("").getAbsolutePath() + "/ProcessedSample2.bmp");



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
                calculateAction(e);
            }
        });
        imageProcessingButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                imageProcessAction(e);
            }
        });
        oneToOneMatchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                oneToOneMatchAction(e);
            }
        });
        oneToManyMatchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                oneToManyMatchAction(e);
            }
        });

        newuserformButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newUserAction();
            }
        });

        oneToManyMatchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                oneToManyMatchAction(e);
            }
        });

        panel.setLayout(new GridLayout(4, 1));
        panel.add(calculationButton);
        panel.add(imageProcessingButton);
        panel.add(oneToOneMatchButton);
        panel.add(oneToManyMatchButton);
        panel.add(matriz1TextField);
        panel.add(matriz2TextField);
        panel.add(newuserformButton);
        return panel;
    }


    private void newUserAction(){
        JFrame nuF = new JFrame();
        nuF.setContentPane(new NuevoUsuarioForm());
        nuF.setSize(new Dimension(400, 125));
        nuF.setTitle("Nuevo Usuario");
        nuF.setVisible(true);
    }

    private void calculateAction(ActionEvent e) {

        m_panel1.setBufferedImage(h1.getFotoDetalle());
        matriz1TextField.setText(h1.getMatrizString());


        m_panel2.setBufferedImage(h2.getFotoDetalle());
        matriz2TextField.setText(h2.getMatrizString());


    }

    private void imageProcessAction(ActionEvent e) {

        m_panel1.setBufferedImage(h1.getFoto());

        //Print skeletinized fingerprint
        m_panel2.setBufferedImage(h1.getFotoDetalle());

        //m_panel1.setBufferedImage(m_bimage1);
        matriz1TextField.setText(h1.getMatrizString());
        matriz2TextField.setText("");

    }

    private void oneToOneMatchAction(ActionEvent e) {
        //match one print
        try {
            JOptionPane.showMessageDialog(null, Double.toString(h1.coincidencia(h2, 65)), "Match %", JOptionPane.PLAIN_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error Message", JOptionPane.PLAIN_MESSAGE);
        }
    }


    private void oneToManyMatchAction(ActionEvent e) {
        HashSet<Usuario> usuarios = CatalogoUsuarios.getInstance().findMatch(h1, 20.0);
        ///TODO: mostrar información útil, si hace falta, quitar el panelTexto por una tabla
        panelTexto.setText("Usuarios\n" + usuarios.toString());
    }


    private void menuBar(){
        //Where the GUI is created:
        JMenuBar menuBar;
        JMenu menu, submenu;
        JMenuItem menuItem;
        JRadioButtonMenuItem rbMenuItem;
        JCheckBoxMenuItem cbMenuItem;

//Create the menu bar.
        menuBar = new JMenuBar();

//Build the first menu.
        menu = new JMenu("A Menu");
        menu.setMnemonic(KeyEvent.VK_A);
        menu.getAccessibleContext().setAccessibleDescription(
                "The only menu in this program that has menu items");
        menuBar.add(menu);

//a group of JMenuItems
        menuItem = new JMenuItem("A text-only menu item",
                KeyEvent.VK_T);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_1, ActionEvent.ALT_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription(
                "This doesn't really do anything");
        menu.add(menuItem);

        menuItem = new JMenuItem("Both text and icon",
                new ImageIcon("images/middle.gif"));
        menuItem.setMnemonic(KeyEvent.VK_B);
        menu.add(menuItem);

        menuItem = new JMenuItem(new ImageIcon("images/middle.gif"));
        menuItem.setMnemonic(KeyEvent.VK_D);
        menu.add(menuItem);

//a group of radio button menu items
        menu.addSeparator();
        ButtonGroup group = new ButtonGroup();
        rbMenuItem = new JRadioButtonMenuItem("A radio button menu item");
        rbMenuItem.setSelected(true);
        rbMenuItem.setMnemonic(KeyEvent.VK_R);
        group.add(rbMenuItem);
        menu.add(rbMenuItem);

        rbMenuItem = new JRadioButtonMenuItem("Another one");
        rbMenuItem.setMnemonic(KeyEvent.VK_O);
        group.add(rbMenuItem);
        menu.add(rbMenuItem);

//a group of check box menu items
        menu.addSeparator();
        cbMenuItem = new JCheckBoxMenuItem("A check box menu item");
        cbMenuItem.setMnemonic(KeyEvent.VK_C);
        menu.add(cbMenuItem);

        cbMenuItem = new JCheckBoxMenuItem("Another one");
        cbMenuItem.setMnemonic(KeyEvent.VK_H);
        menu.add(cbMenuItem);

//a submenu
        menu.addSeparator();
        submenu = new JMenu("A submenu");
        submenu.setMnemonic(KeyEvent.VK_S);

        menuItem = new JMenuItem("An item in the submenu");
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_2, ActionEvent.ALT_MASK));
        submenu.add(menuItem);

        menuItem = new JMenuItem("Another item");
        submenu.add(menuItem);
        menu.add(submenu);

//Build second menu in the menu bar.
        menu = new JMenu("Usuarios");

        menuItem = new JMenuItem("Nuevo Usuario");
        menuItem.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newUserAction();
            }
        });
        menu.add(menuItem);

        menuBar.add(menu);


        this.setJMenuBar(menuBar);
    }

}
