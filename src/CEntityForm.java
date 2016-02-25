import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.awt.image.*;
import javax.imageio.*;
import java.io.File;
import java.lang.Exception ;
import java.awt.Color;
import java.awt.event.*;



public class CEntityForm extends JFrame {

  class BJPanel extends JPanel 
  {
    public BufferedImage bi;
    public BJPanel (){
        this.addMouseListener(new MouseAdapter() {
        public void mouseClicked(MouseEvent m){
        JOptionPane.showMessageDialog (null,"("+Integer.toString(m.getPoint().x)+";"+Integer.toString(m.getPoint().y)+")","Point",JOptionPane.PLAIN_MESSAGE);
        }
        });
    }
    public BJPanel (BufferedImage bi){
      this.bi = bi;
      setPreferredSize(new Dimension(bi.getWidth(),bi.getHeight())) ;
    }
    public void setBufferedImage(BufferedImage bi)
    {
      this.bi = bi;
      setPreferredSize(new Dimension(bi.getWidth(),bi.getHeight())) ;
      this.repaint();
    }
    public void paintComponent(Graphics g)
    {
      g.drawImage(bi,0,0,this) ;
    }
  }


    class NuevoUsuarioForm extends JPanel{
        private JTextField nombreTF = new JTextField();
        private JButton boton = new JButton("Huellas...");
        private File[] ficheros;
        private JButton save = new JButton("Guardar");


        private void choseFiles(ActionEvent e){
            JFileChooser fc=new JFileChooser(new java.io.File("."));

            fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            fc.setMultiSelectionEnabled(true);
            int seleccion=fc.showOpenDialog(this);

            if(seleccion==JFileChooser.APPROVE_OPTION){
                ficheros = fc.getSelectedFiles();
            }
        }



        public NuevoUsuarioForm(){
            this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
            this.add(new JLabel("Nombre usuario:"));
            this.add(nombreTF);
            boton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    choseFiles(e);
                }
            });
            this.add(boton);
            save.addActionListener(new ActionListener() {
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
            this.add(save);
        }

    }


  private JToolBar jtool = new JToolBar();
  private JPanel jimage = new JPanel();
  private JButton jButtonStep1 = new JButton("Calculation");
  private JButton jButtonStep2 = new JButton("Image Processing");
  private JButton jButtonStep3 = new JButton("1 to 1 Match");
  private JButton jButtonStep4 = new JButton("1 to m Match");
  private JTextField jTextField1 = new JTextField();
  private JTextField jTextField2 = new JTextField();
  
  //uses our finger print libery
  private CFingerPrint m_finger1 = new CFingerPrint();
  private CFingerPrint m_finger2 = new CFingerPrint();
  private CFingerPrintGraphics m_fingergfx = new CFingerPrintGraphics();
  private BJPanel m_panel1 = new BJPanel();
  private BJPanel m_panel2 = new BJPanel();
  private BufferedImage m_bimage1 = new BufferedImage(m_finger1.FP_IMAGE_WIDTH ,m_finger1.FP_IMAGE_HEIGHT,BufferedImage.TYPE_INT_RGB );
  private BufferedImage m_bimage2 = new BufferedImage(m_finger2.FP_IMAGE_WIDTH ,m_finger2.FP_IMAGE_HEIGHT,BufferedImage.TYPE_INT_RGB );
  private double finger1[] = new double[m_finger1.FP_TEMPLATE_MAX_SIZE];
  private double finger2[] = new double[m_finger2.FP_TEMPLATE_MAX_SIZE];

    private Huella h1 = new Huella(new java.io.File("").getAbsolutePath()+"/ProcessedSample1.bmp");
    private Huella h2 = new Huella(new java.io.File("").getAbsolutePath()+"/ProcessedSample2.bmp");


    private JButton newuserformButton = new JButton("Nuevo usuario");
  
  public CEntityForm() {
   jButtonStep1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButtonStep1_actionPerformed(e);
      }
    });
    jButtonStep2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButtonStep2_actionPerformed(e);
      }
    });
    jButtonStep3.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButtonStep3_actionPerformed(e);
      }
    });

      newuserformButton.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
              JFrame nuF = new JFrame();
              nuF.setContentPane(new NuevoUsuarioForm());
              nuF.setSize(new Dimension(200,125));
              nuF.setVisible(true);
          }
      });
  
    jtool.setLayout(new GridLayout(4,1));
    jtool.add(jButtonStep1);
    jtool.add(jButtonStep2);
    jtool.add(jButtonStep3);
    jtool.add(jTextField1);
    jtool.add(jTextField2);
      jtool.add(newuserformButton);

      
    this.getContentPane().setLayout(new GridLayout(2,2));
    this.getContentPane().add(m_panel1);
    this.getContentPane().add(m_panel2);
    this.getContentPane().add(jtool);

   
    this.setTitle("Entity");
    this.setSize(new Dimension(900, 700));


      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


      try {
          UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      }catch(Exception ex) {
          ex.printStackTrace();
      }
  }

  private void jButtonStep1_actionPerformed(ActionEvent e)
  {

	    m_panel1.setBufferedImage(h1.getFotoDetalle());
	    jTextField1.setText(h1.getMatrizString());
	        

	    m_panel2.setBufferedImage(h2.getFotoDetalle());
	    jTextField2.setText(h2.getMatrizString());


  }

  private void jButtonStep2_actionPerformed(ActionEvent e)
  {

      //Creamos el objeto JFileChooser
      JFileChooser fc=new JFileChooser();

      fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
      fc.setMultiSelectionEnabled(true);

    //Abrimos la ventana, guardamos la opcion seleccionada por el usuario
      int seleccion=fc.showOpenDialog(this.getContentPane());

    //Si el usuario, pincha en aceptar
      if(seleccion==JFileChooser.APPROVE_OPTION){

          //Seleccionamos el fichero
          File[] ficheros = fc.getSelectedFiles();

          File fichero = ficheros[0];
          //Ecribe la ruta del fichero seleccionado en el campo de texto
          h1 = new Huella(fichero);


      }

    	    m_panel1.setBufferedImage(h1.getFoto());

    	    //Print skeletinized fingerprint
    	    m_panel2.setBufferedImage(h1.getFotoDetalle());

    	    //m_panel1.setBufferedImage(m_bimage1);
    	    jTextField1.setText(h1.getMatrizString());
    	    jTextField2.setText("");

  }

  private void jButtonStep3_actionPerformed(ActionEvent e)
  {
      //match one print
     try
      {
        JOptionPane.showMessageDialog (null,Double.toString(h1.coincidencia(h2,65)),"Match %",JOptionPane.PLAIN_MESSAGE);
      }
      catch (Exception ex)
      {
      JOptionPane.showMessageDialog (null,ex.getMessage() ,"Error Message",JOptionPane.PLAIN_MESSAGE);
      }
  }



}
