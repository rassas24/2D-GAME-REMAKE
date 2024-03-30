package Main;
import javax.swing.*;
import java.awt.*;
import java.io.File;

class LoginApp {

    private String username;
    private String password;
    private String phone;
    private ImageIcon profilePicture;

    private JFrame frame;

    public LoginApp() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Login App");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        showMainMenu();

        frame.setVisible(true);
    }

    private void showMainMenu() {
        JPanel mainMenuPanel = new JPanel(new FlowLayout());
        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");

        loginButton.addActionListener(e -> showLoginScreen());
        registerButton.addActionListener(e -> showRegisterScreen());

        mainMenuPanel.add(loginButton);
        mainMenuPanel.add(registerButton);

        frame.getContentPane().removeAll();
        frame.add(mainMenuPanel, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
    }

    private void showLoginScreen() {
        JPanel loginPanel = new JPanel(new GridLayout(3, 2));
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        JButton loginSubmitButton = new JButton("Login");
        loginSubmitButton.addActionListener(e -> {
            if (checkCredentials(usernameField.getText(), new String(passwordField.getPassword()))) {
                showUserProfileScreen();
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid credentials");
            }
        });

        loginPanel.add(new JLabel("Username:"));
        loginPanel.add(usernameField);
        loginPanel.add(new JLabel("Password:"));
        loginPanel.add(passwordField);
        loginPanel.add(new JLabel(""));
        loginPanel.add(loginSubmitButton);

        frame.getContentPane().removeAll();
        frame.add(loginPanel, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
    }

    private void showRegisterScreen() {
        JPanel registerPanel = new JPanel();
        registerPanel.setLayout(new BoxLayout(registerPanel, BoxLayout.Y_AXIS));

        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JTextField phoneField = new JTextField();
        JButton chooseButton = new JButton("Choose Profile Picture");

        chooseButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(frame);

            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                profilePicture = new ImageIcon(selectedFile.getPath());
            }
        });

        JButton registerSubmitButton = new JButton("Register");
        registerSubmitButton.addActionListener(e -> {
            username = usernameField.getText();
            password = new String(passwordField.getPassword());
            phone = phoneField.getText();

            showUserProfileScreen();
        });

        registerPanel.add(new JLabel("Username:"));
        registerPanel.add(usernameField);
        registerPanel.add(new JLabel("Password:"));
        registerPanel.add(passwordField);
        registerPanel.add(new JLabel("Phone:"));
        registerPanel.add(phoneField);
        registerPanel.add(new JLabel("Profile Picture:"));
        registerPanel.add(chooseButton);
        registerPanel.add(registerSubmitButton);

        frame.getContentPane().removeAll();
        frame.add(registerPanel, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
    }

    private void showUserProfileScreen() {
        JPanel userProfilePanel = new JPanel(new BorderLayout());
        JPanel userInfoPanel = new JPanel(new GridLayout(4, 2));
        JLabel profilePictureLabel = new JLabel();

        if (profilePicture != null) {
            profilePictureLabel.setIcon(profilePicture);
        }

        JLabel usernameLabel = new JLabel("Username: " + username);
        JLabel passwordLabel = new JLabel("Password: " + password);
        JLabel phoneLabel = new JLabel("Phone: " + phone);

        JButton launchButton = new JButton("Launch");
        JButton editProfileButton = new JButton("Edit Profile");

        launchButton.addActionListener(e -> new Game());
        editProfileButton.addActionListener(e -> showEditProfileScreen());

        userInfoPanel.add(new JLabel("Profile Picture:"));
        userInfoPanel.add(profilePictureLabel);
        userInfoPanel.add(new JLabel("Username:"));
        userInfoPanel.add(usernameLabel);
        userInfoPanel.add(new JLabel("Password:"));
        userInfoPanel.add(passwordLabel);
        userInfoPanel.add(new JLabel("Phone:"));
        userInfoPanel.add(phoneLabel);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(launchButton);
        buttonPanel.add(editProfileButton);

        userProfilePanel.add(userInfoPanel, BorderLayout.CENTER);
        userProfilePanel.add(buttonPanel, BorderLayout.SOUTH);

        frame.getContentPane().removeAll();
        frame.add(userProfilePanel, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
    }

    private void showEditProfileScreen() {
        JPanel editProfilePanel = new JPanel(new GridLayout(5, 2));
        JTextField editUsernameField = new JTextField(username);
        JPasswordField editPasswordField = new JPasswordField(password);
        JTextField editPhoneField = new JTextField(phone);

        JButton chooseButton = new JButton("Choose Profile Picture");

        chooseButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(frame);

            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                profilePicture = new ImageIcon(selectedFile.getPath());
            }
        });

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            username = editUsernameField.getText();
            password = new String(editPasswordField.getPassword());
            phone = editPhoneField.getText();

            showUserProfileScreen();
        });

        // Set preferred size for the text fields
        Dimension textFieldSize = new Dimension(150, 20);
        editUsernameField.setPreferredSize(textFieldSize);
        editPasswordField.setPreferredSize(textFieldSize);
        editPhoneField.setPreferredSize(textFieldSize);

        editProfilePanel.add(new JLabel("Username:"));
        editProfilePanel.add(editUsernameField);
        editProfilePanel.add(new JLabel("Password:"));
        editProfilePanel.add(editPasswordField);
        editProfilePanel.add(new JLabel("Phone:"));
        editProfilePanel.add(editPhoneField);
        editProfilePanel.add(new JLabel(""));
        editProfilePanel.add(chooseButton);
        editProfilePanel.add(new JLabel(""));
        editProfilePanel.add(saveButton);

        frame.getContentPane().removeAll();
        frame.add(editProfilePanel, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
    }


    private boolean checkCredentials(String inputUsername, String inputPassword) {
        return username.equals(inputUsername) && password.equals(inputPassword);
    }

    public static void main(String[] args) {
        //SwingUtilities.invokeLater(() -> new LoginApp());
        new Game();
    }
}








