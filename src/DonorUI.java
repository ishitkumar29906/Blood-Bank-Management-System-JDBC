import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class DonorUI extends JFrame {
    DefaultTableModel model = new DefaultTableModel(
            new String[]{"ID", "Name", "Age", "Gender","Blood Group", "Address", "Phone"}, 0
    );

    JTable table = new JTable(model);

    public DonorUI() {
        setTitle("Blood Bank - Donors");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel buttons = new JPanel();

        JButton addButton = new JButton("Add");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");

        buttons.add(addButton);
        buttons.add(updateButton);
        buttons.add(deleteButton);

        add(buttons, BorderLayout.SOUTH);

        addButton.addActionListener(e -> addDonor());
        updateButton.addActionListener(e -> updateDonor());
        deleteButton.addActionListener(e -> deleteDonor());
    }

    // Add Records
    void addDonor() {
        JTextField name = new JTextField();
        JTextField age = new JTextField();
        JTextField gender = new JTextField();
        JTextField bloodGroup = new JTextField();
        JTextField address = new JTextField();
        JTextField phone = new JTextField();

        Object[] fields = {
                "Name:", name,"Age:", age,"Gender:", gender,"Blood Group:", bloodGroup,"Address:", address,"Phone:", phone
        };

        JOptionPane.showConfirmDialog(this, fields, "Add Donor",
                JOptionPane.OK_CANCEL_OPTION);

        try {
            Connection c = DatabaseConnection.getConnection();

            String sql = "INSERT INTO Donors " +
                    "(name, age, gender, blood_group, address, phone_no) " +"VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement p = c.prepareStatement(sql);
            p.setString(1, name.getText());
            p.setInt(2, Integer.parseInt(age.getText()));
            p.setString(3, gender.getText());
            p.setString(4, bloodGroup.getText());
            p.setString(5, address.getText());
            p.setString(6, phone.getText());

            p.executeUpdate();
            c.close();

            viewDonors();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // Read values
    void viewDonors() {
        model.setRowCount(0);

        try {
            Connection c = DatabaseConnection.getConnection();
            Statement s = c.createStatement();
            ResultSet r = s.executeQuery("SELECT * FROM Donors");

            while (r.next()) {
                model.addRow(new Object[]{
                        r.getInt("donor_id"),
                        r.getString("name"),
                        r.getInt("age"),
                        r.getString("gender"),
                        r.getString("blood_group"),
                        r.getString("address"),
                        r.getString("phone_no")
                });
            }

            c.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // Update values
    void updateDonor() {
    int row = table.getSelectedRow();

    int id = (int) model.getValueAt(row, 0);

    JTextField name = new JTextField(model.getValueAt(row, 1).toString());
    JTextField age = new JTextField(model.getValueAt(row, 2).toString());
    JTextField gender = new JTextField(model.getValueAt(row, 3).toString());
    JTextField bloodGroup = new JTextField(model.getValueAt(row, 4).toString());
    JTextField address = new JTextField(model.getValueAt(row, 5).toString());
    JTextField phone = new JTextField(model.getValueAt(row, 6).toString());

    Object[] fields = {
            "Name:", name,"Age:", age,"Gender:", gender,"Blood Group:", bloodGroup,"Address:", address,"Phone:", phone
    };

    JOptionPane.showConfirmDialog(this, fields, "Update Donor",JOptionPane.OK_CANCEL_OPTION);

    try {
        Connection c = DatabaseConnection.getConnection();

        String sql = "UPDATE Donors SET name=?, age=?, gender=?, " +"blood_group=?, address=?, phone_no=? WHERE donor_id=?";

        PreparedStatement p = c.prepareStatement(sql);
        p.setString(1, name.getText());
        p.setInt(2, Integer.parseInt(age.getText()));
        p.setString(3, gender.getText());
        p.setString(4, bloodGroup.getText());
        p.setString(5, address.getText());
        p.setString(6, phone.getText());
        p.setInt(7, id);

        p.executeUpdate();
        c.close();

        viewDonors();
    } catch (Exception e) {
        System.out.println(e);
    }
}
    // Delete selected values
    void deleteDonor() {
        int row = table.getSelectedRow();
        int id = (int) model.getValueAt(row, 0);

        try {
            Connection c = DatabaseConnection.getConnection();
            PreparedStatement p =c.prepareStatement("DELETE FROM Donors WHERE donor_id=?");

            p.setInt(1, id);
            p.executeUpdate();
            c.close();

            viewDonors();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        new DonorUI().setVisible(true);
    }
}