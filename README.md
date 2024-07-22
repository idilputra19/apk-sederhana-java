# apk-sederhana-java

Berikut adalah contoh program Java sederhana untuk memenuhi tugas pengganti UAS mata kuliah PBO 2. Program ini menampilkan data menu, menghitung total pesanan, dan memiliki beberapa fitur tambahan.

Aplikasi restoran sederhana ini adalah sebuah program berbasis Java yang menggunakan antarmuka grafis (GUI) untuk mengelola pesanan di sebuah restoran. Aplikasi ini memiliki fitur login dan logout untuk mengamankan akses ke fungsi pemesanan. Berikut adalah penjelasan rinci tentang bagaimana aplikasi ini bekerja dan komponennya:

1.Fitur Utama

#Login dan Logout: Aplikasi memerlukan pengguna untuk login dengan username dan password. Jika login berhasil, 
pengguna dapat mengakses menu restoran dan membuat pesanan. Pengguna juga dapat logout kapan saja untuk kembali ke layar login.
Menampilkan

#Menu: Aplikasi menampilkan daftar item menu yang dapat dipilih oleh pengguna. Setiap item menu memiliki nama dan harga.

#Total Pesanan: Aplikasi menghitung dan menampilkan total harga dari semua item yang telah dipesan.

#Antarmuka Pengguna (GUI): Menggunakan javax.swing untuk membuat antarmuka pengguna yang interaktif dan mudah digunakan.


Penjelasan Kode
Kelas MenuItem: Kelas ini digunakan untuk menyimpan informasi tentang item menu, termasuk nama dan harga.

java
Copy code
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
Kelas Order: Kelas ini digunakan untuk menyimpan daftar item yang dipesan dan menghitung total harga pesanan.

java
Copy code
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

Kelas RestoranSederhanaGUI: Kelas utama yang mengatur logika aplikasi dan antarmuka pengguna.

Frame Utama: Frame utama yang berisi panel-panel untuk login dan menu.
CardLayout: Layout manager yang memungkinkan peralihan antar panel (login dan menu) dengan mudah.
Login Panel: Panel untuk login dengan username dan password. Jika login berhasil, panel ini akan beralih ke panel menu.
Menu Panel: Panel untuk menampilkan menu dan mengelola pesanan. Termasuk area untuk menampilkan pesanan, total harga, dan tombol logout.
updateOrder(): Metode untuk memperbarui area pesanan dan total harga setiap kali item ditambahkan ke pesanan.
java
Copy code
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
Cara Menggunakan Aplikasi
Login: Buka aplikasi dan masukkan "admin" sebagai username dan password, kemudian klik tombol "Login".
Melihat Menu dan Membuat Pesanan: Setelah login, pengguna dapat melihat daftar item menu. Klik tombol untuk item yang ingin dipesan, dan item tersebut akan ditambahkan ke pesanan.
Melihat Total Pesanan: Total harga pesanan akan diperbarui setiap kali item ditambahkan.
Logout: Klik tombol "Logout" untuk keluar dan kembali ke layar login. Pesanan akan direset.
Aplikasi ini sederhana namun mencakup fitur dasar yang diperlukan untuk mengelola pesanan di restoran. Ini juga memberikan contoh penggunaan javax.swing untuk membuat antarmuka pengguna yang interaktif di Java.






