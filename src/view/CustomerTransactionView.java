package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import connection.Connect;

public class CustomerTransactionView extends JFrame implements ActionListener {
	private JPanel mainPanel, tablePanel, footerPanel, textPanel, buttonPanel;
	private JTable table;
	private JScrollPane scroll;
	private Connect connect;
	
	private static CustomerTransactionView view = null;
	
	private CustomerTransactionView() {}
	
	public static CustomerTransactionView getInstance() {
		if(view == null) view = new CustomerTransactionView();
		return view;
	}

	public void showTransactionCustomer() {
		setSize(1000, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Transaction");
		
		connect = Connect.getConnection();
		
		mainPanel = new JPanel(new BorderLayout());
		tablePanel = new JPanel(new GridLayout(1,1));
		footerPanel = new JPanel(new GridLayout(1,1));
		textPanel = new JPanel();
		buttonPanel = new JPanel();
		
		table = new JTable();
		prepareTableModel();
		
		scroll = new JScrollPane(table, 
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		tablePanel.add(scroll);
		
		footerPanel.add(textPanel);
		footerPanel.add(buttonPanel);
		
		mainPanel.add(tablePanel, BorderLayout.CENTER);
		mainPanel.add(footerPanel, BorderLayout.SOUTH);
		add(mainPanel);
		
		setVisible(true);
	}
	
	public void prepareTableModel() {
		DefaultTableModel dtm = new DefaultTableModel();
		ResultSet rs;
		ResultSetMetaData rsmd;
		
		try {
			rs = connect.executeQuery("SELECT * FROM product");
			rsmd = rs.getMetaData();
			
			for(int i = 1; i <= rsmd.getColumnCount(); i++) {
				dtm.addColumn(rsmd.getColumnName(i));
			}
			dtm.addColumn("Action");
			while(rs.next()) {
				dtm.addRow(new Object[] {
						rs.getInt("id"),
						rs.getString("name"),
						rs.getString("description"),
						rs.getInt("price"),
						rs.getInt("stock"),
				});
			}
			table.getColumn("Action").setCellRenderer(new ButtonRenderer());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		table.setModel(dtm);
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	class ButtonRenderer extends JButton implements TableCellRenderer {
		public ButtonRenderer() {
		  setOpaque(true);
		}
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		  setText((value == null) ? "Modify" : value.toString());
		  return this;
		}
	}

}
