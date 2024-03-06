package agenda.vista.swing;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import agenda.modelo.Contacto;
import agenda.negocio.Agenda;
import agenda.negocio.AgendaImpl;

public class VInicial extends JFrame {
	private static final long serialVersionUID = 1L;
	private String separadorPrincipal=";", separadorSecundario="/";
	private JTextField tBuscaContacto;
	JButton bBuscar;
	private JLabel cantidadContactos;
	private JButton bAgregar;
	private JButton bEliminar;
	private JButton bEditar;
	private JButton bConsultar;
	private JButton bSalir;
	private JLabel nombre;
	private ArrayList<Contacto> listaContactos;
	private Font labelFont = new Font(Font.DIALOG, Font.BOLD, 16);
	private Font textFont = new Font(Font.DIALOG_INPUT, Font.ITALIC, 16);
	private Font listaFont = new Font(Font.DIALOG, Font.PLAIN, 16);
	JTable tabla;
	private DefaultTableModel modelo;
	private String[][] datosTabla;// = new String[0][0];
	private String[] titulosTabla = { "Nombre", "Apellidos", "Apodo",
			"Tipo Via", "Via", "N�mero", "Piso", "Puerta", "Cod.Postal",
			"Ciudad", "Provincia" };
	private Contacto contactoActual;
	private int AGREGAR = 0;
	private int EDITAR = 1;
	private int CONSULTAR = 2;
	private int ELIMINAR = 3;
	int filaActualTabla;
	
	private Agenda agenda = new AgendaImpl();

	public VInicial() {
		super("Agenda de Contactos");
		setResizable(false);
		setBounds(100, 10, 918, 746);
		getContentPane().setLayout(null);

		ManejaEventos manejador = new ManejaEventos();

		armaMenu();
		
		nombre = new JLabel("Contacto: ");
		nombre.setBounds(10, 10, 80, 30);
		nombre.setFont(labelFont);
		add(nombre);

		tBuscaContacto = new JTextField();
		tBuscaContacto.setBounds(10, 40, 400, 30);
		tBuscaContacto.setFont(textFont);
		add(tBuscaContacto);
		tBuscaContacto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				bBuscar.doClick();
			}
		});

		bBuscar = new JButton("Buscar");
		bBuscar.setBounds(420, 40, 80, 30);
		add(bBuscar);
		bBuscar.addActionListener(manejador);
		
		cantidadContactos = new JLabel();
		cantidadContactos.setBounds(600, 40, 250, 30);
		cantidadContactos.setFont(labelFont);
		add(cantidadContactos);

		modelo = new DefaultTableModel(datosTabla, titulosTabla);
		tabla = new JTable(modelo);
		tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabla.setFont(this.listaFont);
		tabla.addMouseListener(manejador);
		tabla.addKeyListener(manejador);
		tabla.setAutoCreateRowSorter(true);
		tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tablaSetEditableFalse();
		actulizaTabla();

		JScrollPane barras = new JScrollPane(tabla,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		barras.setBounds(10, 90, 890, 530);
		add(barras);

		bAgregar = new JButton("Agregar");
		bAgregar.setBounds(10, 10, 80, 30);
		bAgregar.addActionListener(manejador);

		bEditar = new JButton("Editar");
		bEditar.setBounds(100, 10, 80, 30);
		bEditar.addActionListener(manejador);
		bEditar.setEnabled(false);

		bEliminar = new JButton("Eliminar");
		bEliminar.setBounds(190, 10, 80, 30);
		bEliminar.addActionListener(manejador);
		bEliminar.setEnabled(false);

		bConsultar = new JButton("Detalle");
		bConsultar.setBounds(280, 10, 80, 30);
		bConsultar.addActionListener(manejador);
		bConsultar.setEnabled(false);

		bSalir = new JButton("Salir");
		bSalir.setBounds(800, 10, 80, 30);
		bSalir.addActionListener(manejador);

		JPanel panelBotones = new JPanel();
		panelBotones.setBounds(10, 630, 890, 50);
		panelBotones.setLayout(null);
		panelBotones.setBackground(Color.LIGHT_GRAY);
		panelBotones.add(bAgregar);
		panelBotones.add(bEditar);
		panelBotones.add(bEliminar);
		panelBotones.add(bConsultar);
		panelBotones.add(bSalir);

		add(panelBotones);
		setVisible(true);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent arg0) {
				bSalir.doClick();
			}
		});

	}
	
	private void armaMenu(){
		JMenu menuArchivo = new JMenu( "Archivo" ); 
		menuArchivo.setMnemonic( 'A' ); 

		JMenuItem importar = new JMenuItem( "Importar..." );
		importar.setMnemonic( 'I' );
		menuArchivo.add(importar);
		importar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				importar();
			}
		});

		JMenuItem exportar = new JMenuItem( "Exportar..." );
		importar.setMnemonic( 'E' );
		menuArchivo.add(exportar);
		exportar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				exportar();
			}
		});

		JMenuItem opciones = new JMenuItem( "Opciones..." );
		opciones.setMnemonic( 'O' );
		menuArchivo.add(opciones);
		opciones.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				opciones();
			}
		});

		JMenuItem elementoAcercaDe = new JMenuItem( "Acerca de..." );
		elementoAcercaDe.setMnemonic( 'c' );
		menuArchivo.add( elementoAcercaDe );
		elementoAcercaDe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, "Agenda 1.0\n");				
			}
		});
		
		JMenuItem elementoSalir = new JMenuItem("Salir");
		elementoSalir.setMnemonic('S');
		menuArchivo.add(elementoSalir);
		elementoSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				bSalir.doClick();
			}
		});
		
		JMenuBar barra = new JMenuBar();
		setJMenuBar( barra );
		barra.add( menuArchivo );
		
		
	}
	
	private void tablaSetEditableFalse(){
		for (int c = 0; c < tabla.getColumnCount(); c++)
		{
		    Class<?> col_class = tabla.getColumnClass(c);
		    tabla.setDefaultEditor(col_class, null);        // remove editor
		}
	}

	private void actulizaTabla() {
		modelo.setDataVector(datosTabla, titulosTabla);
		tabla.getColumn("Nombre").setMinWidth(90);
		tabla.getColumn("Apellidos").setMinWidth(150);
		tabla.getColumn("Apodo").setMinWidth(90);
		tabla.getColumn("Via").setMinWidth(180);
		tabla.getColumn("Ciudad").setMinWidth(150);
		tabla.getColumn("Provincia").setMinWidth(150);
	}
	
	void cargaTabla(){
		if (datosTabla.length != 0) {
			contactoActual = listaContactos.get(tabla
				.convertRowIndexToModel(tabla.getSelectedRow()));
		}else{
			contactoActual = null;
			bEditar.setEnabled(false);
			bEliminar.setEnabled(false);
			bConsultar.setEnabled(false);
			tBuscaContacto.requestFocus();

		}
	}
	
	private void importar(){
		File f=null;
		JFileChooser chooser = new JFileChooser();
		String[] datosImportar;

		FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos de Texto *.txt, *.csv", "txt", "csv");
		chooser.setFileFilter(filter);
		
		int returnVal = chooser.showOpenDialog(this);
		
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			try {
				f = new File(chooser.getSelectedFile().getCanonicalPath());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else{
			return;
		}
		if (JOptionPane.showConfirmDialog(null,
				"Desea Importar el fichero " + f.getAbsolutePath(), "Importar csv", 2) == 0){
			datosImportar = new String[3];
			datosImportar[0] = f.getAbsolutePath();
			datosImportar[1] = separadorPrincipal;
			datosImportar[2] = separadorSecundario;
			int cant = 0;
			try {
				cant = agenda.importarCSV(f.getAbsolutePath());
			} catch (IOException e) {
				e.printStackTrace();
			}// probar el 2
			JOptionPane.showMessageDialog(null,	"Se han importado " + cant + " contactos con Exito");
			bBuscar.doClick();
		} else {
			return;
		}
		
	}

	private void exportar(){
		File f=null;
		JFileChooser chooser = new JFileChooser();
		String[] datosExportar;

		FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos de Texto *.txt, *.csv", "txt", "csv");
		chooser.setFileFilter(filter);
		chooser.setDialogType(JFileChooser.SAVE_DIALOG);
		
		int returnVal = chooser.showOpenDialog(this);
		
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			try {
				f = new File(chooser.getSelectedFile().getCanonicalPath());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else{
			return;
		}
		if (JOptionPane.showConfirmDialog(null,
				"Desea Exportar la Base de Datos al fichero " + f.getAbsolutePath(), "Exportar csv", 2) == 0){
			datosExportar = new String[2];
			datosExportar[0] = f.getAbsolutePath();
			datosExportar[1] = separadorPrincipal;
//			datosExportar[2] = separadorSecundario;
//			int cant = control.exportar(datosExportar);
			int cant = 0;
			JOptionPane.showMessageDialog(null,	"Se han exportado " + cant + " contactos con Exito");
			bBuscar.doClick();
		} else {
			return;
		}
	}

	private void opciones(){
		new VentanaOpciones(this);
	}

	private class ManejaEventos extends MouseAdapter implements ActionListener,
			KeyListener {

		public void keyPressed(KeyEvent arg0) {
		}

		public void keyReleased(KeyEvent arg0) {
		}
		
		public void mouseClicked(MouseEvent eve) {
			if (eve.getSource() == tabla && eve.getClickCount() == 2) {
				bConsultar.doClick();
			}
		}

		public void mousePressed(MouseEvent eve) {
			if (eve.getSource() == tabla) {
				contactoActual = listaContactos.get(tabla
						.convertRowIndexToModel(tabla.getSelectedRow()));
				bEditar.setEnabled(true);
				bEliminar.setEnabled(true);
				bConsultar.setEnabled(true);
				bConsultar.requestFocus();
			}
		}

		public void keyTyped(KeyEvent eve) {
			if (eve.getSource() == tabla) {
				if (eve.getKeyChar() == KeyEvent.VK_ENTER) {
					contactoActual = listaContactos.get(tabla
							.convertRowIndexToModel(tabla.getSelectedRow()));
					bEditar.setEnabled(true);
					bEliminar.setEnabled(true);
					bConsultar.setEnabled(true);
					bConsultar.requestFocus();
				} else if (eve.getKeyChar() == KeyEvent.VK_TAB) {
					bEditar.setEnabled(true);
					bEditar.requestFocus();
					bEliminar.setEnabled(true);
					bEliminar.requestFocus();
					bConsultar.setEnabled(true);
					bConsultar.requestFocus();
				}
			}
		}

		private String[][] contactosToMatriz() {
			ArrayList<Contacto> lista = listaContactos;
			String[] filas = new String[lista.size()];
			String[][] resultado;
			resultado = new String[filas.length][];
			for (int i = 0; i < filas.length; i++) {
				resultado[i] = contactoToArray(lista.get(i));
			}
			return resultado;
		}
		
		private String[] contactoToArray(Contacto c){
			String[] res = null;
			if (c != null) {
				res = new String[11];
				res[0] = c.getNombre();
				res[1] = c.getApellidos();
				res[2] = c.getApodo();
				res[3] = c.getDom().getTipoVia();
				res[4] = c.getDom().getVia();
				res[5] = c.getDom().getNumero() + "";
				res[6] = c.getDom().getPiso() + "";
				res[7] = c.getDom().getPuerta();
				res[8] = c.getDom().getCodigoPostal();
				res[9] = c.getDom().getCiudad();
				res[10] = c.getDom().getProvincia();
			}
			return res;
		}

		public void actionPerformed(ActionEvent ev) {
			if (ev.getSource() == bBuscar) {
				listaContactos = new ArrayList<>();
				if(!tBuscaContacto.getText().equals("")){
					listaContactos.addAll(agenda.buscarContactoPorNombre(tBuscaContacto.getText()));
				}else{
					listaContactos.addAll(agenda.buscarTodos());
				}
				datosTabla = contactosToMatriz();
				cantidadContactos.setText("Encontrados: " + datosTabla.length + " contactos");
				actulizaTabla();
				if (datosTabla.length == 0) {
					bEditar.setEnabled(false);
					bEliminar.setEnabled(false);
					bConsultar.setEnabled(false);
					;
				}
			}
			if (ev.getSource() == bAgregar) {
				new VentanaEdicion(agenda, contactoActual, AGREGAR, VInicial.this);
			}
			if (ev.getSource() == bEditar) {
				filaActualTabla = tabla.getSelectedRow();
				new VentanaEdicion(agenda, contactoActual, EDITAR, VInicial.this);
			}
			if (ev.getSource() == bEliminar) {
				filaActualTabla = tabla.getSelectedRow();
				new VentanaEdicion(agenda, contactoActual, ELIMINAR, VInicial.this);
			}
			if (ev.getSource() == bConsultar) {
				filaActualTabla = tabla.getSelectedRow();
				new VentanaEdicion(agenda, contactoActual, CONSULTAR, VInicial.this);
			}
			if (ev.getSource() == bSalir) {
				if (JOptionPane.showConfirmDialog(null,
						"Desea salir de la aplicaci�n", "Salir de Agenda", 2) == 0){
//					try {
//						control.grabar();
//					} catch (IOException | SQLException e) {
//						e.printStackTrace();
//					}
//					try {
//						control.finalizar();
//					} catch (IOException e) {
//						JOptionPane.showMessageDialog(null,
//								"No se pudo realizar la grabaci�n de los datos");
//						e.printStackTrace();
//					} catch (SQLException e){
//						e.printStackTrace();
//					}
					System.exit(0);
				}
			}
		}
	}
}
