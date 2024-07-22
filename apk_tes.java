import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

class MenuItem {
    String name;
    double price;

    MenuItem(String name, double price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        return name + " - Rp" + price;
    }
}

class Order {
    ArrayList<MenuItem> items;
    double total;

    Order() {
        items = new ArrayList<>();
        total = 0;
    }

    void addItem(MenuItem item) {
        items.add(item);
        total += item.price;
    }

    void displayOrder() {
        System.out.println("Pesanan Anda:");
        for (MenuItem item : items) {
            System.out.println(item);
        }
        System.out.println("Total Pesanan: Rp" + total);
    }
}

public class RestoranSederhanaGUI {
    private JFrame frame;
    private JPanel panel;
    private JTextArea orderArea;
    private JLabel totalLabel;
    private Order order;
    private ArrayList<MenuItem> menu;
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public RestoranSederhanaGUI() {
        order = new Order();
        menu = new ArrayList<>();

        // Menambahkan beberapa item menu
        menu.add(new MenuItem("Nasi Goreng", 20000));
        menu.add(new MenuItem("Mie Goreng", 18000));
        menu.add(new MenuItem("Ayam Bakar", 25000));
        menu.add(new MenuItem("Es Teh", 5000));
        menu.add(new MenuItem("Jus Jeruk", 8000));

        // Membuat frame utama
        frame = new JFrame("Restoran Sederhana");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        createLoginPanel();
        createMenuPanel();

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private void createLoginPanel() {
        JPanel loginPanel = new JPanel(new GridLayout(3, 2));
        JLabel userLabel = new JLabel("Username:");
        JTextField userText = new JTextField();
        JLabel passLabel = new JLabel("Password:");
        JPasswordField passText = new JPasswordField();
        JButton loginButton = new JButton("Login");

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userText.getText();
                String password = new String(passText.getPassword());

                // Sederhana validasi username dan password
                if (username.equals("admin") && password.equals("admin")) {
                    cardLayout.show(mainPanel, "menuPanel");
                } else {
                    JOptionPane.showMessageDialog(frame, "Username atau password salah!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        loginPanel.add(userLabel);
        loginPanel.add(userText);
        loginPanel.add(passLabel);
        loginPanel.add(passText);
        loginPanel.add(new JLabel()); // Placeholder
        loginPanel.add(loginButton);

        mainPanel.add(loginPanel, "loginPanel");
    }

    private void createMenuPanel() {
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BorderLayout());

        // Membuat area untuk menampilkan pesanan
        orderArea = new JTextArea();
        orderArea.setEditable(false);
        menuPanel.add(new JScrollPane(orderArea), BorderLayout.CENTER);

        // Membuat label untuk total pesanan
        totalLabel = new JLabel("Total Pesanan: Rp0");
        menuPanel.add(totalLabel, BorderLayout.SOUTH);

        // Membuat panel untuk tombol menu
        JPanel itemPanel = new JPanel();
        itemPanel.setLayout(new GridLayout(menu.size(), 1));

        // Menambahkan tombol untuk setiap item menu
        for (MenuItem item : menu) {
            JButton button = new JButton(item.toString());
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    order.addItem(item);
                    updateOrder();
                }
            });
            itemPanel.add(button);
        }

        menuPanel.add(itemPanel, BorderLayout.WEST);

        // Membuat tombol logout
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "loginPanel");
                order = new Order(); // Reset order
                updateOrder();
            }
        });
        menuPanel.add(logoutButton, BorderLayout.NORTH);

        mainPanel.add(menuPanel, "menuPanel");
    }

    private void updateOrder() {
        StringBuilder orderText = new StringBuilder();
        for (MenuItem item : order.items) {
            orderText.append(item.toString()).append("\n");
        }
        orderArea.setText(orderText.toString());
        totalLabel.setText("Total Pesanan: Rp" + order.total);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new RestoranSederhanaGUI();
            }
        });
    }
}
