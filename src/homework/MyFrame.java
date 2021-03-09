package homework;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;

public class MyFrame extends JFrame{
    Connection conn=null;
    PreparedStatement state=null;
    ResultSet result;
    int id=-1;

    private JPanel  panel1;
    private JPanel  panel2;
    private JPanel  panel3;

    JPanel tabsPanel=new JPanel();
    JPanel upPanel=new JPanel();
    JPanel midPanel=new JPanel();
    JPanel dounPanel=new JPanel();
    //upPanel........................................................................................
    JLabel fnameL=new JLabel("Име:");
    JLabel lnameL=new JLabel("Фамилия:");
    JLabel sexL=new JLabel("Пол:");
    JLabel ageL=new JLabel("Години:");
    JLabel salaryL=new JLabel("Заплата:");

    JTextField fnameTF=new JTextField();
    JTextField lnameTF=new JTextField();
    JTextField ageTF=new JTextField();
    JTextField salaryTF=new JTextField();

    String[] item= {"Мъж","Жена"};
    JComboBox<String> sexCombo=new JComboBox<String>(item);
    // midPanel........................................................................................
    JButton addBt=new JButton("Добавяне");
    JButton deleteBt=new JButton("Изтриване");
    JButton editBt=new JButton("Редактиране");

    JComboBox<String> refreshCombo=new JComboBox<String>();

    // dounPanel ......................................................................................
    JTable table=new JTable();
    JScrollPane myScroll=new JScrollPane(table);

    public MyFrame() {
        this.setSize(400, 600);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new GridLayout(4,1));

        JTabbedPane tabPane = new JTabbedPane();
        tabPane.addTab( "Tab 1", panel1);
        tabPane.addTab( "Tab 2", panel2);
        tabPane.addTab( "Tab 3", panel3);
        tabsPanel.add(tabPane);
        this.add(tabsPanel);

        upPanel.setLayout(new GridLayout(5,2));
        //upPanel --------------------------------------------------------------
        upPanel.add(fnameL);
        upPanel.add(fnameTF);
        upPanel.add(lnameL);
        upPanel.add(lnameTF);
        upPanel.add(sexL);
        upPanel.add(sexCombo);
        upPanel.add(ageL);
        upPanel.add(ageTF);
        upPanel.add(salaryL);
        upPanel.add(salaryTF);

        this.add(upPanel);
        // midPanel -------------------------------------------------------------
        midPanel.add(addBt);
        midPanel.add(deleteBt);
        midPanel.add(editBt);

        midPanel.add(refreshCombo);

        this.add(midPanel);
        // dounPanel ------------------------------------------------------------
        myScroll.setPreferredSize(new Dimension(350, 150));
        dounPanel.add(myScroll);
        this.add(dounPanel);
        refreshTable("persona");
        refreshComboSex();



        addBt.addActionListener(new AddAction());
        deleteBt.addActionListener(new DeleteAction());
        table.addMouseListener(new MouseAction());

        this.setVisible(true);
    }// край конструктор


    public void refreshTable(String mytable) {
        conn=DBConnection.getConnection();

        try {
            state=conn.prepareStatement("select * from "+mytable);
            result=state.executeQuery();
            table.setModel(new MyModel(result));

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }// край на метод refreshTable

    public void refreshComboSex() {
        refreshCombo.removeAllItems();
        String item="";
        String sql="select id, fname, lname from persona";
        conn=DBConnection.getConnection();
        try {
            state=conn.prepareStatement(sql);
            result=state.executeQuery();
            while(result.next()) {
                item=result.getObject(1).toString()+"."+result.getObject(2).toString()+" "+result.getObject(3);
                refreshCombo.addItem(item);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }// край на метод refreshComboSex

    public void clearForm() {
        fnameTF.setText("");
        lnameTF.setText("");
        ageTF.setText("");
        salaryTF.setText("");
    }// край на метод clearForm

    class AddAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent arg0) {
            conn=DBConnection.getConnection();
            String sql="insert into persona values(null, ?,?,?,?,?)";

            try {
                state=conn.prepareStatement(sql);
                state.setString(1, fnameTF.getText());
                state.setString(2, lnameTF.getText());
                state.setString(3, sexCombo.getSelectedItem().toString());
                state.setInt(4, Integer.parseInt(ageTF.getText()));
                state.setFloat(5, Float.parseFloat(salaryTF.getText()));

                state.execute();
                refreshTable("persona");
                refreshComboSex();
                clearForm();

            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

    }// край на клас AddAction

    class MouseAction implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent e) {
            int row=table.getSelectedRow();
            id=Integer.parseInt(table.getValueAt(row, 0).toString());
            if(e.getClickCount()>1) {
                fnameTF.setText(table.getValueAt(row, 1).toString());
                lnameTF.setText(table.getValueAt(row, 2).toString());
                ageTF.setText(table.getValueAt(row, 4).toString());
                salaryTF.setText(table.getValueAt(row, 5).toString());
                if(table.getValueAt(row, 3).toString().equals("Мъж")) {
                    sexCombo.setSelectedIndex(0);
                }
                else {
                    sexCombo.setSelectedIndex(1);
                }

            }

        }

        @Override
        public void mouseEntered(MouseEvent e) {
            // TODO Auto-generated method stub

        }

        @Override
        public void mouseExited(MouseEvent e) {
            // TODO Auto-generated method stub

        }

        @Override
        public void mousePressed(MouseEvent e) {
            // TODO Auto-generated method stub

        }

        @Override
        public void mouseReleased(MouseEvent e) {
            // TODO Auto-generated method stub

        }

    }// край на клас MouseAction

    class DeleteAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent arg0) {
            conn=DBConnection.getConnection();
            String sql="delete from persona where id=?";

            try {
                state=conn.prepareStatement(sql);
                state.setInt(1, id);
                state.execute();

                refreshTable("persona");
                clearForm();
                id=-1;

            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

    }// край на клас DeleteAction

}// край на клас MyFrame
