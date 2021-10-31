import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

public class ProductFrame extends JFrame implements ActionListener {
    //Panel Button
    JPanel jPanel = new JPanel(new GridLayout(1,2));

    // table
    private JScrollPane scrollPane = new JScrollPane();
    private JTable table = new JTable();
    private DefaultTableModel dtm = new DefaultTableModel();

    // label
    private JLabel titleLabel = new JLabel("Product");

    //Button
    private JButton jAdd = new JButton("Add Data");
    private JButton jRefresh = new JButton("Refresh");

    // connection
    private Connect conn = new Connect();
    private ResultSet rs = null;
    private ResultSetMetaData rsm = null;
    private Vector<String> headerTable = new Vector<String>();
    private Vector<Vector<String>> dataTable = new Vector<Vector<String>>();

    public ProductFrame() throws SQLException {
        createForm();

        this.add(titleLabel, BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.CENTER);
        this.add(jPanel, BorderLayout.SOUTH);
        this.add(titleLabel, BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.CENTER);

        scrollPane.setViewportView(table);
        table.setModel(dtm);

        jAdd.setSize(80, 15);
        jRefresh.setSize(80, 15);

        jAdd.addActionListener(this);
        jRefresh.addActionListener(this);

        jPanel.add(jAdd);
        jPanel.add(jRefresh);

        headerTable.add("Index");
        headerTable.add("Name");
        headerTable.add("Type");
        headerTable.add("Price");
        headerTable.add("Stock");

        dtm.setColumnIdentifiers(headerTable);
        refreshData();

        this.setVisible(true);
    }

    public void Product() throws SQLException {
    String Sql = "Select * From Product";
    rs = conn.executeQuery(Sql);

        while(rs.next()){
        int Index =rs.getInt(1);
        String name =rs.getString(2);
        String type = rs.getString(3);
        int price = rs.getInt(4);
        int stock = rs.getInt(5);
        dtm.addRow(new Object[]{Index,name,type,price,stock});
        }
    }

    public void refreshData() throws SQLException {
        dtm.setRowCount(0);
        Product();
    }

    private void createForm() {
        this.setTitle("Product Form");
        this.setSize(400, 200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == jAdd){
            dispose();
            Create createData = new Create();
        }
        if(e.getSource()== jRefresh){
            try {
                refreshData();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
