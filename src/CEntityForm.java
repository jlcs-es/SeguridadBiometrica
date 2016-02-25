import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.lang.Exception;


public class CEntityForm extends JFrame {

    private JButton calculationButton = new JButton("Calculation");
    private JButton imageProcessingButton = new JButton("Image Processing");
    private JButton oneToOneMatchButton = new JButton("1 to 1 Match");
    private JButton oneToManyMatchButton = new JButton("1 to m Match");
    private JButton newuserformButton = new JButton("Nuevo usuario");
    private JTextField matriz1TextField = new JTextField();
    private JTextField matriz2TextField = new JTextField();

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

        this.getContentPane().setLayout(new GridLayout(2, 2));
        this.getContentPane().add(m_panel1);
        this.getContentPane().add(m_panel2);
        this.getContentPane().add(panelBotones());
        this.getContentPane().add(new JTextPane());


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

        newuserformButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame nuF = new JFrame();
                nuF.setContentPane(new NuevoUsuarioForm());
                nuF.setSize(new Dimension(400, 125));
                nuF.setTitle("Nuevo Usuario");
                nuF.setVisible(true);
            }
        });

        oneToManyMatchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ///TODOS
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


}
