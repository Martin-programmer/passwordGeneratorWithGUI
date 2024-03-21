import javax.swing.*;
        import java.awt.*;
        import java.awt.event.ActionEvent;

public class PasswordGeneratorGUI extends JFrame {
    private JButton generateButton;
    private JTextArea passwordArea;
    private JTextField lengthField;
    private JCheckBox includeNumbersBox, includeSymbolsBox, includeUpperLettersBox, includeLowerLettersBox;
    private JPanel mainPanel;

    public PasswordGeneratorGUI() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Modern Password Generator");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);
        setResizable(false);
        createView();
    }

    private void createView() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground(new Color(245, 245, 245));

        addComponentWithLabel("Password Length: (At least 12 symbols)", mainPanel);
        addCheckBoxes(mainPanel);
        setupPasswordArea(mainPanel);
        setupGenerateButton(mainPanel);

        setContentPane(mainPanel);
        validate();
        repaint();
    }

    private void addComponentWithLabel(String labelText, JPanel panel) {
        JPanel tempPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        tempPanel.setBackground(new Color(245, 245, 245));
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.PLAIN, 12));
        lengthField = new JTextField(10);
        lengthField.setFont(new Font("Arial", Font.PLAIN, 12));
        tempPanel.add(label);
        tempPanel.add(lengthField);
        panel.add(tempPanel);
    }

    private void addCheckBoxes(JPanel panel) {
        includeNumbersBox = new JCheckBox("Include Numbers");
        includeSymbolsBox = new JCheckBox("Include Symbols");
        includeUpperLettersBox = new JCheckBox("Include Upper Letters");
        includeLowerLettersBox = new JCheckBox("Include Lower Letters");

        // Apply the same font and background to all checkboxes
        Font checkBoxFont = new Font("Arial", Font.BOLD, 12);
        for (JCheckBox checkBox : new JCheckBox[]{includeNumbersBox, includeSymbolsBox, includeUpperLettersBox, includeLowerLettersBox}) {
            checkBox.setFont(checkBoxFont);
            checkBox.setBackground(new Color(245, 245, 245));
        }

        panel.add(includeNumbersBox);
        panel.add(includeSymbolsBox);
        panel.add(includeUpperLettersBox);
        panel.add(includeLowerLettersBox);
    }

    private void setupPasswordArea(JPanel panel) {
        passwordArea = new JTextArea(4, 20);
        passwordArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        passwordArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(passwordArea);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(5, 5, 5, 5),
                BorderFactory.createLineBorder(Color.GRAY)
        ));
        panel.add(scrollPane);
    }

    private void setupGenerateButton(JPanel panel) {
        generateButton = new JButton("Generate Password");
        generateButton.setFont(new Font("Arial", Font.BOLD, 12));
        generateButton.setBackground(new Color(100, 149, 237));
        generateButton.setForeground(Color.WHITE);
        generateButton.setFocusPainted(false);
        generateButton.addActionListener(this::generatePassword);
        panel.add(generateButton);
    }

    private void generatePassword(ActionEvent actionEvent) {
        int length = 0;
        try {
            length = Integer.parseInt(lengthField.getText());
            if (length < 12) {
                JOptionPane.showMessageDialog(this, "The length must be at least 12 characters.");
                createView();
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Wrong input for length. Check it and try again! " +
                    "Length should not contains letters or symbols");
            createView();
            return;
        }

        boolean includeNumbers = includeNumbersBox.isSelected();
        boolean includeSymbols = includeSymbolsBox.isSelected();
        boolean includeUpperLetters = includeUpperLettersBox.isSelected();
        boolean includeLowerLetters = includeLowerLettersBox.isSelected();

        if ((!includeNumbers && !includeSymbols) && (!includeUpperLetters && !includeLowerLetters)) {
            JOptionPane.showMessageDialog(this, "You cannot generate a password without any criteria. Please select at least one.");
            createView();
            return;
        }

        Generator generator = new Generator(length, includeUpperLetters, includeLowerLetters, includeNumbers, includeSymbols);

        String newPassword = generator.generate();
        passwordArea.setText(newPassword);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PasswordGeneratorGUI().setVisible(true));
    }
}


