import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Book {
    private JPanel main;
    private JTextField txtbookname;
    private JTextField txtPrice;
    private JButton saveButton;
    private JButton deleteButton;
    private JButton updateButton;
    private JTextField txtbookid;
    private JButton searchButton;
    private JTextField txtQty;
    private JTable table1;
    private JTextField txtqty;

    private JButton calculationButton;
    private JTextField txtprice;
    private JTextField txtsold;

    private JTextField txtsoldprice;

    private  JScrollPane table_1;

    public static void main(String[] args) {//main
        JFrame frame = new JFrame("Mahadi Nabi Mumu");
        frame.setContentPane(new Book().main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    Connection con;
    PreparedStatement pst;

    public Book() {
        Connect();
        table_load();
        //save

        saveButton.addActionListener(new ActionListener() {//save
            @Override
            public void actionPerformed(ActionEvent e) {


         String bookname,bookid,qty,sold,price,soldprice;

         bookname = txtbookname.getText();
         bookid = txtbookid.getText();

         qty = txtqty.getText();

         sold = txtsold.getText();

         price = txtprice.getText();
         soldprice = txtsoldprice.getText();



         try{
             pst = con.prepareStatement("insert into bookshop(bookname,bookid,qty,sold,price,soldprice)values(?,?,?,?,?,?)");
            pst.setString(1,bookname);
            pst.setString(2,bookid);

            pst.setString(3,qty);

            pst.setString(4,sold);

            pst.setString(5,price);
            pst.setString(6,soldprice);


            pst.executeUpdate();
            JOptionPane.showMessageDialog(null,"Saved");
            txtbookname.setText("");
            txtbookid.setText("");

            txtqty.setText("");


            txtsold.setText("");

            txtprice.setText("");
            txtsoldprice.setText("");

            txtbookname.requestFocus();



         }
         catch (SQLException e1){
             e1.printStackTrace();
         }




            }
        });
        //save b end


        //search
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{


                    String bookid = txtbookid.getText();

                    pst = con.prepareStatement("select bookname,qty,sold,price,soldprice from bookshop where bookid = ?");
                     //bookname,bookid,qty,sold,price,soldprice
                    pst.setString(1,bookid);
                    ResultSet rs = pst.executeQuery();
                    if(rs.next()==true){
                        String bookname = rs.getString(1);

                        String qty = rs.getString(2);


                        String sold = rs.getString(3);

                        String price = rs.getString(4);
                        String soldprice = rs.getString(5);



                        txtbookname.setText(bookname);

                        txtqty.setText(qty);


                        txtsold.setText(sold);

                        txtprice.setText(price);
                        txtsoldprice.setText(soldprice);


                    }
                    else{
                        txtbookname.setText("");

                        txtqty.setText("");


                        txtsold.setText("");
                        txtprice.setText("");
                        txtsoldprice.setText("");



                        JOptionPane.showMessageDialog(null,"invalid");

                    }
                }
                catch (SQLException ex){
                    ex.printStackTrace();

                }

            }
        });


        //end
        //update
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String bookname,bookid,qty,sold,price,soldprice;

                bookname = txtbookname.getText();
                bookid = txtbookid.getText();

                qty = txtqty.getText();

                sold = txtsold.getText();

                price = txtprice.getText();
                soldprice = txtsoldprice.getText();


                try {
                    pst = con.prepareStatement("update bookshop set bookname = ?,qty = ? ,sold = ?,price = ?,soldprice = ?  where bookid = ? ");

                    pst.setString(1,bookname);


                    pst.setString(2,qty);

                    pst.setString(3,sold);

                    pst.setString(4,price);
                    pst.setString(5,soldprice);
                    pst.setString(6,bookid);



                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null,"update");

                    txtbookname.setText("");

                    txtqty.setText("");


                    txtsold.setText("");
                    txtprice.setText("");
                    txtsoldprice.setText("");

                    txtbookname.requestFocus();
                    txtbookid.setText("");
                }
                catch (SQLException e1){
                    e1.printStackTrace();
                }

            }
        });
        //end

        //delete
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               String bid;
               bid = txtbookid.getText();
                try {
                    pst = con.prepareStatement("delete from bookshop where bookid = ? ");
                    pst.setString(1,bid);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null,"delete");

                    txtbookname.setText("");

                    txtqty.setText("");


                    txtsold.setText("");
                    txtprice.setText("");
                    txtsoldprice.setText("");

                    txtbookname.requestFocus();
                    txtbookid.setText("");
                }
                catch (SQLException e1){
                    e1.printStackTrace();
                }

            }
        });



    }

void table_load(){

        try {
            pst = con.prepareStatement("select * from bookshop");
            ResultSet rs = pst.executeQuery();
            table1.setModel(DbUtils.resultSetToTableModel(rs));
            table1.setAutoResizeMode(table1.AUTO_RESIZE_OFF);

        }
        catch (SQLException e1){
            e1.printStackTrace();
        }


}


    public void Connect()
    {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/book","root","");
            System.out.println("Success");

        }
        catch (ClassNotFoundException ex){
            ex.printStackTrace();
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
        //calculator
        calculationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                int m1,m2,t;//5 num input and total



                m1 = Integer.parseInt(txtprice.getText());
                m2 = Integer.parseInt(txtsold.getText());

                //total
                t = m1*m2;
                txtsoldprice.setText(String.valueOf(t));

                //done ;;;;;;



            }
        });
    }
}
