package electricity.billing.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import net.proteanit.sql.DbUtils;

public class DepositDetails extends JFrame implements ActionListener {
    Choice meternumber, cmonth;
    JTable table;
    JButton search, print;
    DepositDetails(){
        super("Deposit Details");
        setSize(700, 700);
        setLocation(400, 100);
        
        setLayout(null);
        getContentPane().setBackground(Color.white);
        
        JLabel lblmeternumber=new JLabel("Search By Meter Number");
        lblmeternumber.setBounds(20, 20, 150, 20);
        add(lblmeternumber);
        
        
        meternumber=new Choice();
        meternumber.setBounds(180, 20, 150, 20);
        add(meternumber);
        
        try{
            Conn c=new Conn();
            ResultSet rs=c.s.executeQuery("select * from customer");
            while(rs.next()){
                meternumber.add(rs.getString("meter_no"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        
        JLabel lblmonth=new JLabel("Search By Month");
        lblmonth.setBounds(380, 20, 100, 20);
        add(lblmonth);
        
        
        cmonth=new Choice();
        cmonth.setBounds(500, 20, 150, 20);
        cmonth.add("Jan");
        cmonth.add("Feb");
        cmonth.add("Mar");
        cmonth.add("Apr");
        cmonth.add("May");
        cmonth.add("Jun");
        cmonth.add("Jul");
        cmonth.add("Aug");
        cmonth.add("Sep");
        cmonth.add("Oct");
        cmonth.add("Nov");
        cmonth.add("Dec");
        add(cmonth);
        
        
        table=new JTable();
        
        try{
            Conn c=new Conn();
            ResultSet rs=c.s.executeQuery("select * from bill");
            
            table.setModel(DbUtils.resultSetToTableModel(rs));
        }catch(Exception e){
            e.printStackTrace();
        }
        JScrollPane sp=new JScrollPane(table);
        sp.setBounds(0, 100, 700, 600);
        add(sp);
        
        search = new JButton("Search");
        search.setBounds(20, 70, 80, 20);
        search.addActionListener(this);
        add(search);
        
        print = new JButton("Print");
        print.setBounds(120, 70, 80, 20);
        print.addActionListener(this);
        add(print);
        
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae){
        if (ae.getSource()==search) {
            String query="select * from bill where meter_no='"+meternumber.getSelectedItem()+"' and month='"+cmonth.getSelectedItem()+"'";
            
            try{
                Conn c= new Conn();
                ResultSet rs=c.s.executeQuery(query);
                table.setModel(DbUtils.resultSetToTableModel(rs));
                
            }catch(Exception e){
                e.printStackTrace();
            }
        }else{
            try{
                table.print();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    
    }
    
    public static void main(String[] args) {
        new DepositDetails();
    }
    
}
