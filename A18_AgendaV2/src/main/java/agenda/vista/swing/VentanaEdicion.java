package agenda.vista.swing;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.JTextComponent;

import agenda.modelo.Contacto;
import agenda.modelo.Domicilio;
import agenda.negocio.Agenda;

@SuppressWarnings("serial")
public class VentanaEdicion extends JFrame {

	private JPanel contentPane;
	private JTextField tNombre;
	private JTextField tApellidos;
	private JTextField tApodo;
	private JTextField tVia;
	private JTextField tTipoVia;
	private JTextField tNro;
	private JTextField tPiso;
	private JTextField tPuerta;
	private JTextField tCodPostal;
	private JTextField tCiudad;
	private JTextField tProvincia;
	private JTextField tTelefono;
	private JTextField tCorreo;
	private JButton btnAgregarTelefono;
	private JButton btnBorrarTelefono;
	private JButton btnAgregarCorreo;
	private JButton btnBorrarCorreo;
	private JButton btnGrabar;
	private JButton btnCancelar;
	private JPanel panelDatos;
	private JPanel panelDomicilio;
	private JPanel panelTelCorreos;
	private JPanel panelBotones;
	private DefaultListModel<String> modeloTelefonos;
	private JList<String> listTelefonos;
	private DefaultListModel<String> modeloCorreos;
	private JList<String> listCorreos;

	private ManejaEventos manejador;
	private boolean edicion = false;
	private boolean modifico = false;
	private Agenda agenda;
	private VInicial padre;
	private Contacto contactoActual;
	private int modo;
	private int AGREGAR = 0;
	private int EDITAR = 1;
	private int CONSULTAR = 2;
	private int ELIMINAR = 3;

	/**
	 * Create the frame.
	 */
	public VentanaEdicion(Agenda agenda, Contacto contactoActual,
			int modo, VInicial padre) {
		setTitle("Contactos");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 942, 573);
		setResizable(false);
		setVisible(true);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		this.padre = padre;
		this.modo = modo;
		this.agenda = agenda;
		bloquearPadre();
//		this.contactoActual = contactoActual;
		if (modo == AGREGAR) 
			contactoActual = new Contacto();
		else
			this.contactoActual = agenda.buscar(contactoActual.getIdContacto());
		manejador = new ManejaEventos();

		panelDatos = new JPanel();
		panelDatos.setBackground(new Color(224, 255, 255));
		panelDatos.setForeground(Color.BLACK);
		panelDatos.setBounds(12, 13, 897, 433);
		contentPane.add(panelDatos);
		panelDatos.setLayout(null);

		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(38, 13, 56, 16);
		panelDatos.add(lblNombre);

		tNombre = new JTextField();
		tNombre.setBounds(38, 42, 209, 22);
		panelDatos.add(tNombre);
		tNombre.setColumns(10);

		JLabel lblApellidos = new JLabel("Apellidos");
		lblApellidos.setBounds(283, 13, 56, 16);
		panelDatos.add(lblApellidos);

		tApellidos = new JTextField();
		tApellidos.setBounds(283, 42, 293, 22);
		panelDatos.add(tApellidos);
		tApellidos.setColumns(10);

		JLabel lblApodo = new JLabel("Apodo");
		lblApodo.setBounds(610, 13, 56, 16);
		panelDatos.add(lblApodo);

		tApodo = new JTextField();
		tApodo.setBounds(610, 42, 170, 22);
		panelDatos.add(tApodo);
		tApodo.setColumns(10);

		panelDomicilio = new JPanel();
		panelDomicilio.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(
				0, 0, 0)));
		panelDomicilio.setBackground(SystemColor.controlHighlight);
		panelDomicilio.setBounds(38, 98, 824, 158);
		panelDatos.add(panelDomicilio);
		panelDomicilio.setLayout(null);

		JLabel lblVia = new JLabel("Via");
		lblVia.setBounds(166, 13, 56, 16);
		panelDomicilio.add(lblVia);

		tVia = new JTextField();
		tVia.setBounds(166, 31, 205, 22);
		panelDomicilio.add(tVia);
		tVia.setColumns(10);

		JLabel lblTipoVia = new JLabel("Tipo Via");
		lblTipoVia.setBounds(25, 13, 56, 16);
		panelDomicilio.add(lblTipoVia);

		tTipoVia = new JTextField();
		tTipoVia.setBounds(25, 31, 116, 22);
		panelDomicilio.add(tTipoVia);
		tTipoVia.setColumns(10);

		JLabel lblNro = new JLabel("Nro");
		lblNro.setBounds(396, 13, 42, 16);
		panelDomicilio.add(lblNro);

		tNro = new JTextField("0");
		tNro.setBounds(396, 31, 116, 22);
		panelDomicilio.add(tNro);
		tNro.setColumns(10);
		tNro.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent arg0) {
				try {
					Integer.parseInt(tNro.getText());
				} catch (NumberFormatException e) {
					tNro.setText("0");
				}
			}
		});

		JLabel lblPiso = new JLabel("Piso");
		lblPiso.setBounds(537, 13, 56, 16);
		panelDomicilio.add(lblPiso);

		tPiso = new JTextField("0");
		tPiso.setBounds(537, 31, 116, 22);
		panelDomicilio.add(tPiso);
		tPiso.setColumns(10);
		tPiso.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent arg0) {
				try {
					Integer.parseInt(tPiso.getText());
				} catch (NumberFormatException e) {
					tPiso.setText("0");
				}
			}
		});

		JLabel lblPuerta = new JLabel("Puerta");
		lblPuerta.setBounds(678, 13, 56, 16);
		panelDomicilio.add(lblPuerta);

		tPuerta = new JTextField();
		tPuerta.setBounds(678, 31, 116, 22);
		panelDomicilio.add(tPuerta);
		tPuerta.setColumns(10);

		JLabel lblCodpostal = new JLabel("Cod.Postal");
		lblCodpostal.setBounds(31, 91, 81, 16);
		panelDomicilio.add(lblCodpostal);

		tCodPostal = new JTextField();
		tCodPostal.setBounds(31, 109, 116, 22);
		panelDomicilio.add(tCodPostal);
		tCodPostal.setColumns(10);

		JLabel lblCiudad = new JLabel("Ciudad");
		lblCiudad.setBounds(181, 91, 56, 16);
		panelDomicilio.add(lblCiudad);

		tCiudad = new JTextField();
		tCiudad.setBounds(181, 109, 205, 22);
		panelDomicilio.add(tCiudad);
		tCiudad.setColumns(10);

		JLabel lblProvincia = new JLabel("Provincia");
		lblProvincia.setBounds(420, 91, 56, 16);
		panelDomicilio.add(lblProvincia);

		tProvincia = new JTextField();
		tProvincia.setBounds(420, 109, 173, 22);
		panelDomicilio.add(tProvincia);
		tProvincia.setColumns(10);

		JLabel lblDomicilio = new JLabel("DOMICILIO");
		lblDomicilio.setForeground(Color.BLUE);
		lblDomicilio.setBounds(38, 77, 93, 16);
		panelDatos.add(lblDomicilio);
		lblDomicilio.setFont(new Font("Tahoma", Font.BOLD, 15));

		panelTelCorreos = new JPanel();
		panelTelCorreos.setBorder(new MatteBorder(1, 1, 1, 1,
				(Color) new Color(0, 0, 0)));
		panelTelCorreos.setBackground(SystemColor.controlHighlight);
		panelTelCorreos.setForeground(Color.BLACK);
		panelTelCorreos.setBounds(38, 279, 824, 137);
		panelDatos.add(panelTelCorreos);
		panelTelCorreos.setLayout(null);

		listCorreos = new JList<>();
		listCorreos.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null,
				null, null));
		listCorreos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		panelTelCorreos.add(listCorreos);
		listCorreos.addMouseListener(manejador);
		listCorreos.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				btnBorrarCorreo.setVisible(true);
			}
		});
		modeloCorreos = new DefaultListModel<String>();

		JLabel lblTelefonos = new JLabel("TELEFONOS");
		lblTelefonos.setForeground(Color.BLUE);
		lblTelefonos.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblTelefonos.setBounds(59, 13, 91, 16);
		panelTelCorreos.add(lblTelefonos);

		JLabel lblCorreos = new JLabel("CORREOS");
		lblCorreos.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblCorreos.setForeground(Color.BLUE);
		lblCorreos.setBounds(380, 14, 98, 16);
		panelTelCorreos.add(lblCorreos);

		listTelefonos = new JList<>();
		listTelefonos.setBorder(new BevelBorder(BevelBorder.LOWERED, null,
				null, null, null));
		listTelefonos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		panelTelCorreos.add(listTelefonos);
		listTelefonos.addMouseListener(manejador);
		listTelefonos.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				btnBorrarTelefono.setVisible(true);
			}
		});
		modeloTelefonos = new DefaultListModel<String>();

		JScrollPane scrollTelefonos = new JScrollPane(listTelefonos,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollTelefonos.setBounds(48, 55, 203, 69);
		panelTelCorreos.add(scrollTelefonos);

		JScrollPane scrollCorreos = new JScrollPane(listCorreos,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollCorreos.setBounds(380, 55, 388, 69);
		panelTelCorreos.add(scrollCorreos);

		tTelefono = new JTextField();
		tTelefono.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null,
				null, null));
		tTelefono.setBounds(48, 32, 180, 22);
		panelTelCorreos.add(tTelefono);
		tTelefono.setColumns(10);
		tTelefono.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnAgregarTelefono.doClick();
			}
		});

		tCorreo = new JTextField();
		tCorreo.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null,
				null, null));
		tCorreo.setBounds(380, 32, 366, 22);
		panelTelCorreos.add(tCorreo);
		tCorreo.setColumns(10);
		tCorreo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnAgregarCorreo.doClick();
			}
		});
		// tCorreo.addFocusListener(new FocusAdapter() {
		// public void focusLost(FocusEvent arg0) {
		// if (tCorreo.getText().indexOf("@") == -1 ||
		// tCorreo.getText().indexOf(".") == -1) {
		// btnAgregarCorreo.setEnabled(false);
		// }
		// else{
		// btnAgregarCorreo.setEnabled(true);
		// }
		// }
		// });

		btnAgregarTelefono = new JButton("");
		btnAgregarTelefono.setIcon(new ImageIcon(VentanaEdicion.class
				.getResource("/images/icono.ok.png")));
		btnAgregarTelefono.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnAgregarTelefono.setBounds(230, 33, 21, 25);
		btnAgregarTelefono.setEnabled(false);
		btnAgregarTelefono.addActionListener(manejador);
		panelTelCorreos.add(btnAgregarTelefono);

		btnAgregarCorreo = new JButton("");
		btnAgregarCorreo.setIcon(new ImageIcon(VentanaEdicion.class
				.getResource("/images/icono.ok.png")));
		btnAgregarCorreo.setBounds(746, 33, 21, 25);
		btnAgregarCorreo.setEnabled(false);
		btnAgregarCorreo.addActionListener(manejador);
		panelTelCorreos.add(btnAgregarCorreo);

		btnBorrarTelefono = new JButton("");
		btnBorrarTelefono.setIcon(new ImageIcon(VentanaEdicion.class
				.getResource("/images/cancel-remove-icone-5993-96.png")));
		btnBorrarTelefono.setBounds(263, 54, 21, 25);
		btnBorrarTelefono.addActionListener(manejador);
		panelTelCorreos.add(btnBorrarTelefono);
		btnBorrarTelefono.setVisible(false);

		btnBorrarCorreo = new JButton("");
		btnBorrarCorreo.setIcon(new ImageIcon(VentanaEdicion.class
				.getResource("/images/cancel-remove-icone-5993-96.png")));
		btnBorrarCorreo.setBounds(779, 54, 21, 25);
		btnBorrarCorreo.addActionListener(manejador);
		panelTelCorreos.add(btnBorrarCorreo);
		btnBorrarCorreo.setVisible(false);

		panelBotones = new JPanel();
		panelBotones.setBackground(Color.LIGHT_GRAY);
		panelBotones.setBounds(12, 459, 897, 58);
		panelPreparaListeners();
		contentPane.add(panelBotones);
		panelBotones.setLayout(null);

		if (modo != ELIMINAR) {
			btnGrabar = new JButton("Grabar");
			btnGrabar.setEnabled(false);
		} else {
			btnGrabar = new JButton("Eliminar");
			btnGrabar.setEnabled(true);
		}
		btnGrabar.setBounds(40, 13, 90, 30);
		panelBotones.add(btnGrabar);
		btnGrabar.addActionListener(manejador);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(145, 13, 90, 30);
		panelBotones.add(btnCancelar);
		btnCancelar.addActionListener(manejador);

		panelSetEditable(false);

		if (modo != AGREGAR) {
			cargaDatos();
		}
		if (modo == AGREGAR || modo == EDITAR) {
			panelSetEditable(true);
		}

		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent arg0) {
				btnCancelar.doClick();;
			}
		});

	}

	private void inicializaPantalla() {
		for (int i = 0; i < panelDatos.getComponentCount(); i++) {
			if (panelDatos.getComponent(i) instanceof JTextComponent) {
				((JTextComponent) panelDatos.getComponent(i)).setText("");
			}
		}
		for (int i = 0; i < panelDomicilio.getComponentCount(); i++) {
			if (panelDomicilio.getComponent(i) instanceof JTextComponent) {
				((JTextComponent) panelDomicilio.getComponent(i)).setText("");
			}
		}
		for (int i = 0; i < panelTelCorreos.getComponentCount(); i++) {
			if (panelTelCorreos.getComponent(i) instanceof JTextComponent) {
				((JTextComponent) panelTelCorreos.getComponent(i)).setText("");
			}
		}
		modeloTelefonos.clear();
		listTelefonos.setModel(modeloTelefonos);
		modeloCorreos.clear();
		listCorreos.setModel(modeloCorreos);

		btnGrabar.setEnabled(false);
	}

	private void cargaDatos() {
		tNombre.setText(contactoActual.getNombre());
		tApellidos.setText(contactoActual.getApellidos());
		tApodo.setText(contactoActual.getApodo());
		tTipoVia.setText(contactoActual.getDom().getTipoVia());
		tVia.setText(contactoActual.getDom().getVia());
		tNro.setText(contactoActual.getDom().getNumero() + "");
		tPiso.setText(contactoActual.getDom().getPiso() + "");
		tPuerta.setText(contactoActual.getDom().getPuerta());
		tCodPostal.setText(contactoActual.getDom().getCodigoPostal());
		tCiudad.setText(contactoActual.getDom().getCiudad());
		tProvincia.setText(contactoActual.getDom().getProvincia());

		modeloTelefonos = new DefaultListModel<String>();
		for (String telefono : contactoActual.getTelefonos()) {
			modeloTelefonos.addElement(telefono);
		}
		listTelefonos.setModel(modeloTelefonos);

		modeloCorreos = new DefaultListModel<String>();
		for (String correo : contactoActual.getCorreos()) {
			modeloCorreos.addElement(correo);
		}
		listCorreos.setModel(modeloCorreos);
	}

	private void bloquearPadre() {
		padre.setEnabled(false);
		padre.setFocusableWindowState(false);
	}

	private void salir() {
		padre.setEnabled(true);
		padre.setFocusableWindowState(true);
		padre.bBuscar.doClick();
		if (modo == AGREGAR) {
			padre.tabla.changeSelection(padre.tabla.getRowCount() - 1, 0,
					false, false);
		}
		if (modo == EDITAR || modo == CONSULTAR || modo == ELIMINAR) {
			padre.tabla.changeSelection(padre.filaActualTabla, 0, false, false);
		}
		padre.tabla.requestFocus();
		padre.cargaTabla();
		dispose();
	}

	private void panelSetEditable(boolean editable) {
		for (int i = 0; i < panelDatos.getComponentCount(); i++) {
			if (panelDatos.getComponent(i) instanceof JTextComponent) {
				((JTextComponent) panelDatos.getComponent(i))
						.setEditable(editable);
			}
		}
		for (int i = 0; i < panelDomicilio.getComponentCount(); i++) {
			if (panelDomicilio.getComponent(i) instanceof JTextComponent) {
				((JTextComponent) panelDomicilio.getComponent(i))
						.setEditable(editable);
			}
		}
		for (int i = 0; i < panelTelCorreos.getComponentCount(); i++) {
			if (panelTelCorreos.getComponent(i) instanceof JTextComponent) {
				((JTextComponent) panelTelCorreos.getComponent(i))
						.setEditable(editable);
			}
		}
		listTelefonos.setEnabled(editable);
		listCorreos.setEnabled(editable);
	}

	private void panelPreparaListeners() {
		for (int i = 0; i < panelDatos.getComponentCount(); i++) {
			if (panelDatos.getComponent(i) instanceof JTextComponent) {
				((JTextComponent) panelDatos.getComponent(i))
						.addKeyListener(manejador);
			}
		}
		for (int i = 0; i < panelDomicilio.getComponentCount(); i++) {
			if (panelDomicilio.getComponent(i) instanceof JTextComponent) {
				((JTextComponent) panelDomicilio.getComponent(i))
						.addKeyListener(manejador);
			}
		}
		tCorreo.addKeyListener(manejador);
		tTelefono.addKeyListener(manejador);
	}

	private boolean verificaCamposObligatorios() {
		if (tNombre.getText().trim().equals("")
				|| tApellidos.getText().trim().equals("")) {
			return false;
		} else {
			return true;
		}
	}

	private Contacto armaContacto() {
		if (modo == AGREGAR) {
			contactoActual = new Contacto();
		}
		contactoActual.setNombre(tNombre.getText());
		contactoActual.setApellidos(tApellidos.getText());
		contactoActual.setApodo(tApodo.getText());
		Domicilio dom = new Domicilio();
		dom.setTipoVia(tTipoVia.getText());
		dom.setVia(tVia.getText());
		if (tNro.getText().equals("")) {
			dom.setNumero(0);
		} else {
			dom.setNumero(Integer.parseInt(tNro.getText()));
		}
		if (tPiso.getText().equals("")) {
			dom.setNumero(0);
		} else {
			dom.setPiso(Integer.parseInt(tPiso.getText()));
		}
		dom.setPuerta(tPuerta.getText());
		dom.setCodigoPostal(tCodPostal.getText());
		dom.setCiudad(tCiudad.getText());
		dom.setProvincia(tProvincia.getText());
		contactoActual.setDom(dom);

		Object[] obj = modeloTelefonos.toArray();
		Set<String> temp = new LinkedHashSet<String>();
		for (Object tel : obj) {
			temp.add((String)tel);
		}
		contactoActual.setTelefonos(temp);

		obj = modeloCorreos.toArray();
		temp = new LinkedHashSet<String>();
		for (Object cor : obj) {
			temp.add((String)cor);
		}
		contactoActual.setCorreos(temp);

		return contactoActual;
	}
	
	private boolean correoCorrecto(String email){
		final String PATTERN_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		
		// Compilar la expresion regular en un patron
        Pattern pattern = Pattern.compile(PATTERN_EMAIL);
 
        // Realiza el macheo entre el patr�n y el correo
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
	}

	private class ManejaEventos extends MouseAdapter implements KeyListener,
			ActionListener {

		public void keyPressed(KeyEvent ev) {
		}

		public void keyTyped(KeyEvent ev) {
			if (ev.getKeyChar() == KeyEvent.VK_ESCAPE) {
				btnCancelar.doClick();
			}else if (modo != CONSULTAR && modo != ELIMINAR) {
				btnGrabar.setEnabled(true);
				edicion = true;
			}
		}

		public void keyReleased(KeyEvent ev) {
			if (ev.getSource() == tTelefono) {
				if (!tTelefono.getText().equals("")) {
					btnAgregarTelefono.setEnabled(true);
				} else {
					btnAgregarTelefono.setEnabled(false);
				}
				btnBorrarTelefono.setVisible(false);
			}
			if (ev.getSource() == tCorreo) {
				if (!tCorreo.getText().equals("") && correoCorrecto(tCorreo.getText())) {
					btnAgregarCorreo.setEnabled(true);
				} else {
					btnAgregarCorreo.setEnabled(false);
				}
				btnBorrarCorreo.setVisible(false);
			}
		}

		public void mouseClicked(MouseEvent eve) {
			if (eve.getSource() == listTelefonos && eve.getClickCount() == 2) {
				tTelefono.setText(listTelefonos.getSelectedValue().toString());
				modifico = true;
				btnBorrarTelefono.setVisible(false);
			}
			if (eve.getSource() == listCorreos && eve.getClickCount() == 2) {
				tCorreo.setText(listCorreos.getSelectedValue().toString());
				modifico = true;
				btnBorrarCorreo.setVisible(false);
			}
		}

		public void actionPerformed(ActionEvent ev) {
			if (ev.getSource() == btnCancelar) {
				if (!edicion || JOptionPane.showConfirmDialog(null,
										"Desea abandonar la ventana\n Se perder�n los cambios realizados",
										"Salir de Contactos", 2) == 0) {
					salir();
				}
			}

			if (ev.getSource() == btnGrabar) {
				if (modo == ELIMINAR) {
					agenda.eliminar(contactoActual);
					JOptionPane.showMessageDialog(null,
							contactoActual.getNombre() + " "
									+ contactoActual.getApellidos()
									+ "Se ha eliminado");
					if (padre.filaActualTabla != 0){
						padre.filaActualTabla--;
					}
					salir();
				} else {
					if (verificaCamposObligatorios()) {
						Contacto nuevo = armaContacto();
						if (modo == AGREGAR) {
							agenda.insertarContacto(nuevo);
							if (JOptionPane
									.showConfirmDialog(
											null,
											"El Contacto ha sido grabado con Exito\nDesea cargar m�s contactos",
											"Salir de Contactos", 2) == 0) {
								inicializaPantalla();
							} else {
								salir();
							}
						}
						if (modo == EDITAR) {
								agenda.modificar(contactoActual);
								JOptionPane
										.showMessageDialog(null,
												"El Contacto ha sido modificado con Exito");
								salir();
						}
					} else {
						JOptionPane.showMessageDialog(null,
								"Debe ingresar un Nombre y Apellido v�lidos");

					}
				}
			}
			if (ev.getSource() == btnAgregarTelefono) {
				if (modifico) {
					modeloTelefonos.set(listTelefonos.getSelectedIndex(),
							tTelefono.getText());

				} else {
					modeloTelefonos.addElement(tTelefono.getText());
				}
				listTelefonos.setModel(modeloTelefonos);
				tTelefono.setText("");
				btnAgregarTelefono.setEnabled(false);
				btnBorrarTelefono.setVisible(false);
				modifico = false;
			}
			if (ev.getSource() == btnAgregarCorreo) {
				if (!correoCorrecto(tCorreo.getText())) {
					btnAgregarCorreo.setEnabled(false);
					Toolkit.getDefaultToolkit().beep();
				} else {
					btnAgregarCorreo.setEnabled(true);

					if (modifico) {
						modeloCorreos.set(listCorreos.getSelectedIndex(),
								tCorreo.getText());

					} else {
						modeloCorreos.addElement(tCorreo.getText());
					}
					listCorreos.setModel(modeloCorreos);
					tCorreo.setText("");
					btnAgregarCorreo.setEnabled(false);
					btnBorrarCorreo.setVisible(false);
					modifico = false;
				}
			}
			if (ev.getSource() == btnBorrarTelefono) {
				modeloTelefonos.removeElementAt(listTelefonos
						.getSelectedIndex());
				listTelefonos.setModel(modeloTelefonos);
				btnBorrarTelefono.setVisible(false);
				btnGrabar.setEnabled(true);
				modifico = false;
				tTelefono.setText("");
				edicion = true;
			}
			if (ev.getSource() == btnBorrarCorreo) {
				modeloCorreos.removeElementAt(listCorreos.getSelectedIndex());
				listCorreos.setModel(modeloCorreos);
				btnBorrarCorreo.setVisible(false);
				btnGrabar.setEnabled(true);
				modifico = false;
				tCorreo.setText("");
				edicion = true;
			}
		}
	}

}
