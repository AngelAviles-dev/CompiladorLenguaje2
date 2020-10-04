package Compilador;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.table.*;
public class Vista extends JFrame implements ActionListener{
	JLabel corre,corre2,corre3,corre4,corre5,corre6;
	JLabel incorre,incorre2,incorre3;
	JMenuBar menuPrincipal;
	JMenu opcion,analisis,generacion;
	JRadioButton abrir;
	JFileChooser archivoSeleccionado;
	File archivo;
	JTabbedPane documentos, analizada, resultados, objetos;
	JTextArea Doc,Lex,Result,inter,objeto;
	JList<String> tokens;
	boolean ban=true;
	static DefaultTableModel modelo;
	String titulos[]={"Nombre","Tipo","Valor","Posicion","Alcance"};
	JTable tabla;
	JScrollPane Tabla;
	static DefaultTableModel modeloCuadruplo;
	String titulosCuadruplo[]={"Operador","Argumento 1","Argumento 2","Resultado"};
	JTable tablaCuadruplo;
	JScrollPane TablaCuadruplo;
	public Vista() {
		formatoWindows();
		inicializaciones();
		if(archivoSeleccionado.showOpenDialog(this)==JFileChooser.CANCEL_OPTION) 
			return;
		hazInterfaz();
		hazEscuchas();
	}
	public void inicializaciones() {
		/*Menu*/
		menuPrincipal=new JMenuBar();
		opcion=new JMenu("Archivo");
		analisis=new JMenu("Analisis");
		generacion=new JMenu("Generar");
		opcion.setIcon(Rutinas.AjustarImagen("Archivo.png", 25, 25));
		analisis.setIcon(Rutinas.AjustarImagen("Analisis.png", 25, 25));
		generacion.setIcon(Rutinas.AjustarImagen("Generacion.png", 25, 25));
		/*Opciones del menu*/
		/*Menu 1*/
		opcion.add(new JMenuItem("Guardar",Rutinas.AjustarImagen("Nuevo.png", 25, 25)));
		opcion.getItem(0).setEnabled(false);
		opcion.addSeparator();
		opcion.add(new JMenuItem("Modificar",Rutinas.AjustarImagen("Modificar.png", 25, 25)));
		/*Menu 2*/
		analisis.add(new JMenuItem("Lexico",Rutinas.AjustarImagen("Lexico.png", 25, 25)));
		analisis.addSeparator();
		analisis.add(new JMenuItem("Sintactico",Rutinas.AjustarImagen("Sintactico.png", 25, 25)));
		analisis.getItem(2).setEnabled(false);
		analisis.addSeparator();
		analisis.add(new JMenuItem("Semantico",Rutinas.AjustarImagen("Semantico.png", 25, 25)));
		analisis.getItem(4).setEnabled(false);
		/*Menu 3*/
		generacion.add(new JMenuItem("Intermedio",Rutinas.AjustarImagen("Intermedio.png", 25, 25)));
		generacion.getItem(0).setEnabled(false);
		generacion.addSeparator();
		generacion.add(new JMenuItem("Objeto",Rutinas.AjustarImagen("Objeto.png", 25, 25)));
		generacion.getItem(2).setEnabled(false);
		/*Ventana de archivo*/
		archivoSeleccionado= new JFileChooser("Abrir");
		archivoSeleccionado.setDialogTitle("Abrir");
		archivoSeleccionado.setFileSelectionMode(JFileChooser.FILES_ONLY);
		/*SubVentanas de documento, codigo y resultado*/
		Doc = new JTextArea();
		Doc.setFont(new Font("Consolas", Font.PLAIN, 12));
		Lex = new JTextArea();
		Lex.setFont(new Font("Consolas", Font.PLAIN, 12));
		Lex.setEnabled(false);
		Result = new JTextArea();
		Result.setFont(new Font("Consolas", Font.PLAIN, 12));
		Result.setEnabled(false);
		objeto = new JTextArea();
		objeto.setFont(new Font("Consolas", Font.PLAIN, 12));
		objeto.setEnabled(false);
		documentos = new JTabbedPane();
		analizada = new JTabbedPane();
		resultados = new JTabbedPane();
		objetos = new JTabbedPane();
	}
	private void hazInterfaz() {
		setTitle("Analizador");
		Dimension dim;
		dim=getToolkit().getScreenSize().getSize();
		setSize(dim);
		setLayout(null);
		setResizable(false);
		setLocationRelativeTo(null);
		setJMenuBar(menuPrincipal);
		menuPrincipal.add(opcion);
		menuPrincipal.add(analisis);
		menuPrincipal.add(generacion);
		documentos.setToolTipText("Aqui se muestra el codigo");
		archivo=archivoSeleccionado.getSelectedFile();
		documentos.addTab(archivo.getName().toString(),new JScrollPane(Doc));
		analizada.addTab("Compilador",new JScrollPane(Lex));
		resultados.addTab("Resultados",new JScrollPane(Result));
		objetos.addTab("Código Objeto",new JScrollPane(objeto));
		modelo = new DefaultTableModel(null,titulos);//Modelo de las tablas
		tabla= new JTable(modelo);//tabla de simbolos
		Tabla = new JScrollPane(tabla);
		modeloCuadruplo = new DefaultTableModel(null,titulosCuadruplo);//Modelo de las tablas
		tablaCuadruplo= new JTable(modeloCuadruplo);//tabla de simbolos
		TablaCuadruplo = new JScrollPane(tablaCuadruplo);
		/*Llenado del documento en pantalla*/
		abrir();
		/*Posicionamiento de los componentes de texto en pantalla*/
		documentos.setBounds(1,1,665,237);
		add(documentos);
		analizada.setBounds(667, 1,687,237);
		add(analizada);
		resultados.setBounds(1,236,665,237);
		add(resultados);
		objetos.setBounds(1,469,665,237);
		add(objetos);
		Tabla.setBounds(667, 282,685, 125);
		add(Tabla);
		TablaCuadruplo.setBounds(667, 451,685, 125);
		add(TablaCuadruplo);
		JLabel sim = new JLabel(Rutinas.AjustarImagen("Simnolos.png", 40, 40));
		JLabel sim2 = new JLabel("Tabla De Simbolos:");
		sim2.setFont(new Font("Consolas", Font.BOLD, 16));
		sim2.setBounds(713, 252,250,15);
		add(sim2);
		sim.setBounds(667, 240,40,40);
		add(sim);
		JLabel inte = new JLabel(Rutinas.AjustarImagen("Simnolos.png", 40, 40));
		JLabel inte2 = new JLabel("Código Intermedio: Cuadruplos:");
		inte2.setFont(new Font("Consolas", Font.BOLD, 16));
		inte2.setBounds(713, 421,350,15);
		add(inte2);
		inte.setBounds(667, 409,40,40);
		add(inte);
		//---------------------------------------------------------------------------
		corre = new JLabel(Rutinas.AjustarImagen("Correcto.png", 30, 30));
		incorre = new JLabel(Rutinas.AjustarImagen("Incorrecto.png", 30, 30));
		JLabel lacorre = new JLabel("Analisis Lexico.");
		lacorre.setFont(new Font("Consolas", Font.BOLD, 16));
		lacorre.setBounds(713, 600,250,15);
		add(lacorre);
		corre.setBounds(667, 590, 30, 30);
		corre.setVisible(false);
		incorre.setBounds(667, 590, 30, 30);
		incorre.setVisible(false);
		add(corre);
		add(incorre);
		//---------------------------------------------------------------------------
		corre2 = new JLabel(Rutinas.AjustarImagen("Correcto.png", 30, 30));
		incorre2 = new JLabel(Rutinas.AjustarImagen("Incorrecto.png", 30, 30));
		JLabel lacorre2 = new JLabel("Analisis Sintactico.");
		lacorre2.setFont(new Font("Consolas", Font.BOLD, 16));
		lacorre2.setBounds(713, 635,250,15);
		add(lacorre2);
		corre2.setBounds(667, 625, 30, 30);
		corre2.setVisible(false);
		incorre2.setBounds(667, 625, 30, 30);
		incorre2.setVisible(false);
		add(corre2);
		add(incorre2);
		//---------------------------------------------------------------------------
		corre3 = new JLabel(Rutinas.AjustarImagen("Correcto.png", 30, 30));
		incorre3 = new JLabel(Rutinas.AjustarImagen("Incorrecto.png", 30, 30));
		JLabel lacorre3 = new JLabel("Analisis Semantico.");
		lacorre3.setFont(new Font("Consolas", Font.BOLD, 16));
		lacorre3.setBounds(713, 670,250,15);
		add(lacorre3);
		corre3.setBounds(667, 660, 30, 30);
		corre3.setVisible(false);
		incorre3.setBounds(667, 660, 30, 30);
		incorre3.setVisible(false);
		add(corre3);
		add(incorre3);
		//---------------------------------------------------------------------------
		corre4 = new JLabel(Rutinas.AjustarImagen("Correcto.png", 30, 30));
		JLabel lacorre4 = new JLabel("Código Intermedio.");
		lacorre4.setFont(new Font("Consolas", Font.BOLD, 16));
		lacorre4.setBounds(963, 600,250,15);
		add(lacorre4);
		corre4.setBounds(917, 590, 30, 30);
		corre4.setVisible(false);
		add(corre4);
		//---------------------------------------------------------------------------
		corre5 = new JLabel(Rutinas.AjustarImagen("Correcto.png", 30, 30));
		JLabel lacorre5 = new JLabel("Código Objeto.");
		lacorre5.setFont(new Font("Consolas", Font.BOLD, 16));
		lacorre5.setBounds(963, 635,250,15);
		add(lacorre5);
		corre5.setBounds(917, 625, 30, 30);
		corre5.setVisible(false);
		add(corre5);
		//---------------------------------------------------------------------------
		corre6 = new JLabel(Rutinas.AjustarImagen("Correcto.png", 30, 30));
		JLabel lacorre6 = new JLabel("Analisis Completo.");
		lacorre6.setFont(new Font("Consolas", Font.BOLD, 16));
		lacorre6.setBounds(963, 670,250,15);
		add(lacorre6);
		corre6.setBounds(917, 660, 30, 30);
		corre6.setVisible(false);
		add(corre6);
		//----------------------------------------------------------------------------
		JLabel corre7 = new JLabel(Rutinas.AjustarImagen("Icono2.png", 130, 130));
		corre7.setBounds(1200, 580, 130, 130);
		add(corre7);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setIconImage(new ImageIcon("Icono.png").getImage());
	}
	public void hazEscuchas() {
		/*Escuchadores*/
		opcion.getItem(0).addActionListener(this);
		opcion.getItem(2).addActionListener(this);
		analisis.getItem(0).addActionListener(this);
		analisis.getItem(2).addActionListener(this);
		analisis.getItem(4).addActionListener(this);
		generacion.getItem(0).addActionListener(this);
		generacion.getItem(2).addActionListener(this);
	}
	public void actionPerformed(ActionEvent e) {
		/*Opciones de archivo*/
		if(e.getSource()==opcion.getItem(0)) {
			guardar();
			reinicia();
			limpiarTabla();
			opcion.getItem(0).setEnabled(false);
			analisis.getItem(0).setEnabled(true);
		}
		if(e.getSource()==opcion.getItem(2)) {
			opcion.getItem(0).setEnabled(true);
			abrir();
		}
		/*Lexico*/
		if(e.getSource()==analisis.getItem(0)) {
			new Lexico(archivo.getAbsolutePath());
			ban=false;//Es para evitar que se guarde sin darle modificar
			llena(Lex,Result,"");//Lleno las area de texto con lo analizado
			analisis.getItem(0).setEnabled(false);//Deshabilito el boton en este caso lexico
			if(Lexico.errores.get(0).equals("No hay errores lexicos"))//Si el analisis lexico se hizo correctamente se habilita el sintactico
				{
				analisis.getItem(2).setEnabled(true);
				corre.setVisible(true);
				}else
					incorre.setVisible(true);
		}
		/*Sintactico*/
		if(e.getSource()==analisis.getItem(2)) {
			new Sintactico();
			llena(Lex,Result,"");
			analisis.getItem(2).setEnabled(false);
			if(Lexico.errores.get(1).equals("No hay errores sintacticos"))//Si el analisis sintactico se hizo correctamente se habilita el semantico
				{
				analisis.getItem(4).setEnabled(true);
				corre2.setVisible(true);
				}else
					incorre2.setVisible(true);
		}
		/*Semantico*/
		if(e.getSource()==analisis.getItem(4)) {
			analisis.getItem(4).setEnabled(false);
			new GeneraTabla();
			new Semantico();
			llena(Lex,Result,"");
			if(Lexico.errores.get(2).equals("No hay errores semanticos"))//Si el analisis semantico se hizo correctamente se habilita el intermedio
				{
				generacion.getItem(0).setEnabled(true);
				corre3.setVisible(true);
				}else
					incorre3.setVisible(true);
		}
		/*Intermedio*/
		if(e.getSource()==generacion.getItem(0)) {
			new Intermedio();
			generacion.getItem(0).setEnabled(false);
			generacion.getItem(2).setEnabled(true);
			corre4.setVisible(true);
		}
		/*Objeto*/
		if(e.getSource()==generacion.getItem(2)) {
			new Ensamblador();
			generacion.getItem(2).setEnabled(false);
			String men="";
			for(int i=0;i<Ensamblador.lineas.size();i++){
				men+=Ensamblador.lineas.get(i)+"\n";
			}
			objeto.setText(men);
			corre5.setVisible(true);
			corre6.setVisible(true);
		}
	}
	public boolean guardar() {
		try {
			FileWriter fw = new FileWriter(archivo);
			BufferedWriter bf = new BufferedWriter(fw);
			bf.write(Doc.getText());
			bf.close();
			fw.close();
		}catch (Exception e) {
			System.out.println("No se ha podido modificar el archivo");
			return false;
		}
		return true;
	}
	public boolean abrir() {
		String texto="",linea;
		try {
			FileReader fr = new FileReader(archivo) ; 
			BufferedReader br= new BufferedReader(fr);
			while((linea=br.readLine())!=null) 
				texto+=linea+"\n";
			Doc.setText(texto);
			return true;
		}catch (Exception e) {
			archivo=null;
			JOptionPane.showMessageDialog(null, "El archivo no es de tipo texto", "Warning",
					JOptionPane.WARNING_MESSAGE);
			return false;
		}
	}
	public void llena(JTextArea cuadro, JTextArea result, String mensalida) {
		String muestra="",error="";
		if(mensalida.length()==0) {
		for(int i=0; i<Lexico.tokenAnalizados.size(); i++)
			muestra+=Lexico.tokenAnalizados.get(i)+"\n";
		for(int i=0; i<Lexico.errores.size(); i++)
			error+=Lexico.errores.get(i)+"\n";
		cuadro.setText(muestra);
		result.setText(error);
		}else {
			cuadro.setText(mensalida);
		}
	}
	public void formatoWindows() {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
		}
	}
	
	public void  reinicia(){
		Lexico.renglon=1;
		Result.setText("");
		objeto.setText("");
		Lex.setText("");
		corre.setVisible(false);
		corre2.setVisible(false);
		corre3.setVisible(false);
		corre4.setVisible(false);
		corre5.setVisible(false);
		corre6.setVisible(false);
		incorre.setVisible(false);
		incorre2.setVisible(false);
		incorre3.setVisible(false);
	}
	
	public void limpiarTabla(){
		while(tabla.getRowCount()!=0){
			((DefaultTableModel)tabla.getModel()).removeRow(0);
		}
		while(tablaCuadruplo.getRowCount()!=0){
			((DefaultTableModel)tablaCuadruplo.getModel()).removeRow(0);
		}
	}
}
