import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;

public class Ventana extends JFrame {
    private JTabbedPane tabbedPane1;
    private JPanel panelPrincipal;
    private JCheckBox direccionadoCheckBox;
    private JCheckBox conPesoCheckBox;
    private JButton crearButton;
    private JButton quemarButton;
    private JTextField VerticetextField1;
    private JButton insertarButton;
    private JComboBox VerticeIniciocomboBox1;
    private JComboBox VerticeFinalcomboBox1;
    private JTextField PesotextField1;
    private JButton insertarButton1;
    private JButton mostrarGrafoButton;
    private JComboBox BusVerticeInicialcomboBox1;
    private JButton DFSButton;
    private JButton BFSButton;
    private JButton DIJASKSTRAButton;
    private JTextArea textArea1;
    private Grafo grafo;
    public Ventana() {

        crearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean seleccionadoDireccion,seleccionadoPeso;
                seleccionadoPeso=conPesoCheckBox.isSelected();
                if(!seleccionadoPeso){
                    PesotextField1.setText("1");
                    PesotextField1.setEnabled(false);
                }else{
                    PesotextField1.setText("");
                    PesotextField1.setEnabled(true);
                }
                seleccionadoDireccion=direccionadoCheckBox.isSelected();
                grafo=new Grafo(seleccionadoDireccion,seleccionadoPeso);
                JOptionPane.showMessageDialog(null, "Grafo creado");

            }
        });
        insertarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!VerticetextField1.getText().isBlank()){
                    if(grafo.getVertexByValue(VerticetextField1.getText())==null){
                        grafo.addVertice(VerticetextField1.getText());
                        cargarComboBox();
                        JOptionPane.showMessageDialog(null, "Vertice añadido con éxito");
                    }else{
                        JOptionPane.showMessageDialog(null, "Ya hay un vertice con ese nombre,pruebe con otro");
                    }

                }else{
                    JOptionPane.showMessageDialog(null, "Llene correctamente el campo de vertice");
                    textArea1.setText("No se añadio el vertice, reintente...");
                }
            }
        });
        insertarButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(grafo.getVertexByValue(VerticeIniciocomboBox1.getSelectedItem().toString()).aristaUnica(VerticeFinalcomboBox1.getSelectedItem().toString())){
                    grafo.addArista(VerticeIniciocomboBox1.getSelectedItem().toString(),VerticeFinalcomboBox1.getSelectedItem().toString(),Integer.parseInt(PesotextField1.getText()));
                    JOptionPane.showMessageDialog(null, "Aristas agregada con éxito");
                }else{
                    JOptionPane.showMessageDialog(null, "Esta arista ya existe");
                }

            }
        });
        mostrarGrafoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea1.setText("");
                for (int i = 0; i < grafo.getVertices().size(); i++) {
                    textArea1.append(grafo.getVertices().get(i).imprimir(grafo.isConPesos())+"\n");
                }

            }
        });
        DFSButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Vertice> vertices=new ArrayList<Vertice>();
                vertices.add(grafo.getVertexByValue(BusVerticeInicialcomboBox1.getSelectedItem().toString()));
                textArea1.setText(grafo.depthFirstTraversal(grafo.getVertexByValue(BusVerticeInicialcomboBox1.getSelectedItem().toString()),vertices));
            }
        });
        BFSButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea1.setText(grafo.breadthFirstTraversal(grafo.getVertexByValue(BusVerticeInicialcomboBox1.getSelectedItem().toString())));
            }
        });
        DIJASKSTRAButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Dictionary[] result = grafo.Dijsktra(grafo.getVertexByValue(BusVerticeInicialcomboBox1.getSelectedItem().toString()));
                Dictionary<String, Integer> distances = result[0];
                Dictionary<String, Vertice> previous = result[1];

                StringBuilder resultText = new StringBuilder();
                Enumeration<String> keys = distances.keys();
                while (keys.hasMoreElements()) {
                    String vertexData = keys.nextElement();
                    int distance = distances.get(vertexData);
                    Vertice previousVertex = previous.get(vertexData);

                    resultText.append("El Vertice: ").append(vertexData).append(", ");
                    resultText.append("La Distancia es de: ").append(distance).append(", ");
                    resultText.append("El Vértice anterior es: ").append(previousVertex.getDato()).append(". ");
                    resultText.append("\n");

                }
                textArea1.setText(resultText.toString());
            }
        });
        quemarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                grafo.addVertice("1");
                grafo.addVertice("2");
                grafo.addVertice("3");
                grafo.addVertice("4");
                grafo.addArista("1","2",8);
                grafo.addArista("1","3",12);
                grafo.addArista("2","3",15);
                grafo.addArista("2","4",4);
                JOptionPane.showMessageDialog(null, "Datos quemados con éxito");

            }
        });

    }

    public JPanel getMainPanel() {
        return panelPrincipal;
    }
    private void cargarComboBox(){
        DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>();
        DefaultComboBoxModel<String> comboBoxModel2 = new DefaultComboBoxModel<>();
        DefaultComboBoxModel<String> comboBoxModel3 = new DefaultComboBoxModel<>();
        VerticeIniciocomboBox1.setModel(comboBoxModel);
        VerticeFinalcomboBox1.setModel(comboBoxModel2);
        BusVerticeInicialcomboBox1.setModel(comboBoxModel3);
        for (Vertice vertice : grafo.getVertices()) {
            String data = vertice.getDato();
            comboBoxModel.addElement(data);
            comboBoxModel2.addElement(data);
            comboBoxModel3.addElement(data);
        }
    }
}
