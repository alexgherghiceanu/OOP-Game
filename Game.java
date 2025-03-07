import gameboard.Cell;
import gameboard.CellEntityType;
import gameboard.Grid;
import gameboard.ImpossibleMove;
import org.example.entities.Account;
import org.example.entities.characters.Character;
import org.example.entities.characters.Enemy;
import org.example.entities.characters.spells.Earth;
import org.example.entities.characters.spells.Fire;
import org.example.entities.characters.spells.Ice;
import org.example.entities.characters.spells.Spell;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;

//The main class of the game
public class Game {
    int length;
    int width;
    Grid grid;
    Account selectedAccount = null;
    private static Game game;

    private Game() {
        Random rand = new Random();
        this.length = rand.nextInt(7) + 3;
        this.width = rand.nextInt(7) + 3;
        this.grid = Grid.generateGrid(this.length, this.width, new Cell(0, 0, CellEntityType.VOID, false, false));
    }

    ArrayList<Account> existingAccounts = JsonInput.deserializeAccounts();


    public void selectAccountGUI() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 200);
        frame.setLocationRelativeTo(null);
        Font customFont = null;
        try {
            customFont = Font.createFont(Font.TRUETYPE_FONT, new File("RagnaRunesPersonalUseRegular-Rp7AV.otf")).deriveFont(20f);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            return;
        }

        frame.setTitle("Log in");
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JLabel usrLabel = new JLabel("Username:");
        usrLabel.setForeground(Color.WHITE);
        usrLabel.setFont(customFont);
        usrLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JTextField username = new JTextField(15);
        username.setMaximumSize(new Dimension(150, 25));

        panel.add(usrLabel);
        panel.add(username);


        JLabel usrEmailLabel = new JLabel("E-mail:");
        usrEmailLabel.setForeground(Color.WHITE);
        usrEmailLabel.setFont(customFont);
        usrEmailLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JTextField email = new JTextField(15);
        email.setMaximumSize(new Dimension(150, 25));
        panel.add(usrEmailLabel);
        panel.add(email);

        JLabel usrPasswordLabel = new JLabel("Password:");
        usrPasswordLabel.setForeground(Color.WHITE);
        usrPasswordLabel.setFont(customFont);
        usrPasswordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JPasswordField password = new JPasswordField(15);
        password.setMaximumSize(new Dimension(150, 25));

        panel.add(usrPasswordLabel);
        panel.add(password);

        JButton loginButton = new JButton("Login");
        loginButton.setFont(customFont);
        loginButton.setForeground(Color.BLACK);
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usrName = username.getText();
                String usrEmail = email.getText();
                String usrPassword = password.getText();

                for (Account account : existingAccounts) {
                    if (account.getInformation().getName().equals(usrName)) {
                        if (account.getInformation().getCreds().getEmail().equals(usrEmail)) {
                            if (account.getInformation().getCreds().getPassword().equals(usrPassword)) {
                                selectedAccount = account;
//                                JOptionPane pane = new JOptionPane();
//                                pane.showMessageDialog(panel, "You have successfully logged in as " + account.getInformation().getName());
                                frame.dispose();
                            }
                        }
                    }
                }
                if (selectedAccount == null) {
                    JOptionPane pane = new JOptionPane();
                    pane.showMessageDialog(panel, "Your account doesn't exist");
                    email.setText("");
                    password.setText("");
                    username.setText("");
                }
            }
        });

        panel.add(loginButton);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.setBackground(Color.getHSBColor(281f / 360f, 0.42f, 0.48f));
        frame.add(panel);
        frame.setVisible(true);
    }

    public Character selectCharacterGUI() {
        DefaultListModel<Character> model = new DefaultListModel<>();
        ArrayList<Character> selectedChar = new ArrayList<>();
        for (Character character : selectedAccount.getCharacters()) {
            model.addElement(character);
        }
        JList<Character> list = new JList(model);

        Font customFont = null;
        try {
            customFont = Font.createFont(Font.TRUETYPE_FONT, new File("RagnaRunesPersonalUseRegular-Rp7AV.otf")).deriveFont(20f);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            return null;
        }

        final BufferedImage mageImg;
        final BufferedImage rogueImg;
        final BufferedImage warriorImg;

        try {
            mageImg = ImageIO.read(new File("D:\\POO\\Tema2\\PROIECT\\mage.jpg"));
            rogueImg = ImageIO.read(new File("D:\\POO\\Tema2\\PROIECT\\rogue.jpg"));
            warriorImg = ImageIO.read(new File("D:\\POO\\Tema2\\PROIECT\\warrior.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        list.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Character) {
                    Character character = (Character) value;
                    setText(character.getName());
                }
                return c;
            }
        });

        list.setFont(customFont.deriveFont(16f));
        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setPreferredSize(new Dimension(800, 100));
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.setLocationRelativeTo(null);
        frame.setTitle("Select Your Character");
        frame.setLayout(new BorderLayout());

        JLabel statsLabel = new JLabel("Statistics");
        statsLabel.setFont(customFont);
        statsLabel.setForeground(Color.WHITE);
        statsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        statsLabel.setAlignmentY(Component.CENTER_ALIGNMENT);
        JTextField statsField = new JTextField(15);
        statsField.setMaximumSize(new Dimension(600, 25));
        statsField.setAlignmentY(Component.BOTTOM_ALIGNMENT);

        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setBackground(Color.getHSBColor(281f / 360f, 0.42f, 0.48f));

        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new BoxLayout(statsPanel, BoxLayout.Y_AXIS));
        statsPanel.setBackground(Color.getHSBColor(281f / 360f, 0.42f, 0.48f));
        statsPanel.add(statsLabel);
        statsPanel.add(statsField);

        JLabel imgLabel = new JLabel();
        imgLabel.setHorizontalAlignment(JLabel.CENTER);
        imgLabel.setVerticalAlignment(JLabel.CENTER);

        imagePanel.add(statsPanel, BorderLayout.NORTH);
        imagePanel.add(imgLabel, BorderLayout.CENTER);

        frame.add(imagePanel, BorderLayout.CENTER);
        frame.add(scrollPane, BorderLayout.SOUTH);

        list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (list.isSelectionEmpty()) {
                    return;
                }

                Character selectedCharacter = (Character) list.getSelectedValue();
                if (selectedCharacter != null) {
                    statsField.setText(selectedCharacter.toString());

                    int width = imagePanel.getWidth();
                    int height = imagePanel.getHeight();
                    if (width <= 0)
                        width = 800;
                    if (height <= 0)
                        height = 600;

                    ImageIcon icon = null;
                    switch (selectedCharacter.getType()) {
                        case "Mage":
                            icon = new ImageIcon(mageImg.getScaledInstance(width, height, Image.SCALE_SMOOTH));
                            break;
                        case "Rogue":
                            icon = new ImageIcon(rogueImg.getScaledInstance(width, height, Image.SCALE_SMOOTH));
                            break;
                        case "Warrior":
                            icon = new ImageIcon(warriorImg.getScaledInstance(width, height, Image.SCALE_SMOOTH));
                            break;
                        default:
                            icon = null;
                            break;
                    }
                    imgLabel.setIcon(icon);
                }
            }
        });

        imagePanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                if (!list.isSelectionEmpty()) {
                    list.setSelectedIndex(list.getSelectedIndex());
                }
            }
        });

        JButton selectButton = new JButton("Select");
        selectButton.setFont(customFont);
        selectButton.setForeground(Color.BLACK);
        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Character selectedCharacter = (Character) list.getSelectedValue();
                selectedChar.add(selectedCharacter);
                frame.dispose();
            }
        });
        frame.add(selectButton, BorderLayout.WEST);
        frame.setVisible(true);

        while (selectedChar.isEmpty()) {
            try {
                Thread.sleep(100);  // Small delay to prevent CPU hogging
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return selectedChar.get(0);
    }

    public void enemyCombat(Character tempBoy, Enemy enemy)  {
        Random rand = new Random();
        if (!enemy.getAbilities().isEmpty()) {
            int enemyAbNr = rand.nextInt(enemy.getAbilities().size());
            enemy.useAbility(enemy.getAbilities().get(enemyAbNr), tempBoy);
        }

        else {
            tempBoy.receiveDamage(enemy.getDamage());
        }
    }

    public CompletableFuture<Void> abilityGUI(Character tempBoy, Enemy enemy) {
        CompletableFuture<Void> future = new CompletableFuture<>();

        Font customFont = null;
        try {
            customFont = Font.createFont(Font.TRUETYPE_FONT, new File("RagnaRunesPersonalUseRegular-Rp7AV.otf")).deriveFont(16f);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            return null;
        }

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setTitle("Ability Selection");
        frame.setLocationRelativeTo(null);
        frame.setLayout(new GridLayout(1, tempBoy.getAbilities().size()));
        frame.setPreferredSize(new Dimension(900, 900));
        frame.setMaximumSize(new Dimension(900, 900));
        frame.setMinimumSize(new Dimension(900, 900));

        DefaultListModel<Spell> spells = new DefaultListModel<>();

        for (Spell spell : tempBoy.getAbilities()) {
            spells.addElement(spell);
            JPanel panel = new JPanel();
            panel.setLayout(new BorderLayout());

            // Image panel
            JPanel imagePanel = new JPanel();
            ImageIcon spellIcon = null;
            JTextField abName = new JTextField();
            if (spell instanceof Fire) {
                try {
                    BufferedImage spellImg = ImageIO.read(new File("fire-spell.jpg"));
                    spellIcon = new ImageIcon(spellImg.getScaledInstance(280, 600, Image.SCALE_SMOOTH));
                    abName.setText("Fire");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (spell instanceof Ice) {
                try {
                    BufferedImage spellImg = ImageIO.read(new File("ice-spell.jpeg"));
                    spellIcon = new ImageIcon(spellImg.getScaledInstance(280, 600, Image.SCALE_SMOOTH));
                    abName.setText("Ice");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (spell instanceof Earth) {
                try {
                    BufferedImage spellImg = ImageIO.read(new File("earth-spell.jpg"));
                    spellIcon = new ImageIcon(spellImg.getScaledInstance(280, 600, Image.SCALE_SMOOTH));
                    abName.setText("Earth");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            JLabel spellLabel = new JLabel(spellIcon);
            imagePanel.add(spellLabel);
            panel.add(imagePanel, BorderLayout.CENTER);

            JPanel statsPanel = new JPanel();
            statsPanel.setLayout(new GridLayout(4, 2, 5, 5));
            statsPanel.setPreferredSize(new Dimension(280, 150));

            JLabel nameLabel = new JLabel("Type");
            nameLabel.setFont(customFont);
            nameLabel.setForeground(Color.WHITE);
            abName.setPreferredSize(new Dimension(80, 25));

            JLabel manaCostLabel = new JLabel("Mana Cost");
            manaCostLabel.setFont(customFont);
            manaCostLabel.setForeground(Color.WHITE);
            JTextField manaCost = new JTextField(String.valueOf(spell.getManaCost()));
            manaCost.setPreferredSize(new Dimension(80, 25));

            JLabel damageLabel = new JLabel("Damage");
            damageLabel.setFont(customFont);
            damageLabel.setForeground(Color.WHITE);
            JTextField damageField = new JTextField(String.valueOf(spell.getAbilityDamage()));
            damageField.setPreferredSize(new Dimension(80, 25));

            JButton select = new JButton("Select");
            select.setFont(customFont);
            select.setForeground(Color.WHITE);
            select.setBackground(Color.BLACK);
            select.setPreferredSize(new Dimension(80, 30));
            select.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    tempBoy.useAbility(spell, enemy);
                    tempBoy.getAbilities().remove(spell);
                    frame.dispose();
                    future.complete(null);
                }
            });

            statsPanel.add(nameLabel);
            statsPanel.add(abName);
            statsPanel.add(manaCostLabel);
            statsPanel.add(manaCost);
            statsPanel.add(damageLabel);
            statsPanel.add(damageField);
            statsPanel.add(new JLabel(""));
            statsPanel.add(select);

            panel.add(statsPanel, BorderLayout.SOUTH);
            panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            frame.add(panel);
        }

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        return future;
    }

    public CompletableFuture<Void> endScreen(Character tempBoy) {
        Font customFont = null;
        try {
            customFont  = Font.createFont(Font.TRUETYPE_FONT, new File("RagnaRunesPersonalUseRegular-Rp7AV.otf")).deriveFont(20f);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            return null;
        }


        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setTitle("The End");
        frame.setLocationRelativeTo(null);
        frame.setSize(900,900);
        frame.setLayout(new GridLayout(3, 1));
        CompletableFuture<Void> future = new CompletableFuture<>();

        final BufferedImage mageImg;
        final BufferedImage rogueImg;
        final BufferedImage warriorImg;
        final BufferedImage enemyImg;

        try {
            mageImg = ImageIO.read(new File("D:\\POO\\Tema2\\PROIECT\\mage.jpg"));
            rogueImg = ImageIO.read(new File("D:\\POO\\Tema2\\PROIECT\\rogue.jpg"));
            warriorImg = ImageIO.read(new File("D:\\POO\\Tema2\\PROIECT\\warrior.jpg"));
            enemyImg = ImageIO.read(new File("D:\\POO\\Tema2\\PROIECT\\enemy-visited.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        ImageIcon enemyIcon = new ImageIcon(enemyImg.getScaledInstance(400,400,Image.SCALE_SMOOTH));
        ImageIcon playerIcon = null;

        switch (tempBoy.getType()){
            case "Mage":
                playerIcon = new ImageIcon(mageImg.getScaledInstance(400,400,Image.SCALE_SMOOTH));
                break;
            case "Rogue":
                playerIcon = new ImageIcon(rogueImg.getScaledInstance(400,400,Image.SCALE_SMOOTH));
                break;
            case "Warrior":
                playerIcon = new ImageIcon(warriorImg.getScaledInstance(400,400,Image.SCALE_SMOOTH));
                break;
            default:
                playerIcon = null;
                break;
        }

        JPanel playerPanel = new JPanel();
        playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.Y_AXIS));
        playerPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JLabel playerLabel = new JLabel(playerIcon);
        playerLabel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        playerPanel.add(playerLabel);

        JPanel playerStats = new JPanel(new GridLayout(5,2,5,5));

        JLabel nameLabel = new JLabel("Name :");
        nameLabel.setFont(customFont);
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setBackground(Color.BLACK);
        nameLabel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JTextField nameField = new JTextField(String.valueOf(tempBoy.getName()));
        nameField.setPreferredSize(new Dimension(80, 25));
        nameField.setFont(customFont);
        nameField.setForeground(Color.BLACK);
        nameField.setBackground(Color.WHITE);
        nameField.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JLabel classLabel = new JLabel("Class :");
        classLabel.setFont(customFont);
        classLabel.setForeground(Color.WHITE);
        classLabel.setBackground(Color.BLACK);
        classLabel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JTextField classField = new JTextField(String.valueOf(tempBoy.getType()));
        classField.setPreferredSize(new Dimension(80, 25));
        classField.setFont(customFont);
        classField.setForeground(Color.BLACK);
        classField.setBackground(Color.WHITE);
        classField.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JLabel levelLabel = new JLabel("Level :");
        levelLabel.setFont(customFont);
        levelLabel.setForeground(Color.WHITE);
        levelLabel.setBackground(Color.BLACK);
        levelLabel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JTextField levelField = new JTextField(String.valueOf(tempBoy.getCurrentLevel()));
        levelField.setPreferredSize(new Dimension(80, 25));
        levelField.setForeground(Color.BLACK);
        levelField.setBackground(Color.WHITE);
        levelField.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JLabel experienceLabel = new JLabel("Experience :");
        experienceLabel.setFont(customFont);
        experienceLabel.setForeground(Color.WHITE);
        experienceLabel.setBackground(Color.BLACK);
        experienceLabel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JTextField experienceField = new JTextField(String.valueOf(tempBoy.getCurrentExperience() + tempBoy.getCurrentLevel() * 10));
        experienceField.setPreferredSize(new Dimension(80, 25));
        experienceField.setForeground(Color.BLACK);
        experienceField.setBackground(Color.WHITE);
        experienceField.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JLabel killsLabel = new JLabel("Enemies defeated :");
        killsLabel.setFont(customFont);
        killsLabel.setForeground(Color.WHITE);
        killsLabel.setBackground(Color.BLACK);
        killsLabel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JTextField killField = new JTextField(String.valueOf(tempBoy.getEnemiesSlain()));
        killField.setPreferredSize(new Dimension(80, 25));
        killField.setForeground(Color.BLACK);
        killField.setBackground(Color.WHITE);
        killField.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        playerStats.add(nameLabel);
        playerStats.add(nameField);
        playerStats.add(classLabel);
        playerStats.add(classField);
        playerStats.add(levelLabel);
        playerStats.add(levelField);
        playerStats.add(experienceLabel);
        playerStats.add(experienceField);
        playerStats.add(killsLabel);
        playerStats.add(killField);
        playerPanel.add(playerStats);

        JPanel buttonPanel = new JPanel(new GridLayout(1,2,10,10));

        JButton againButton = new JButton("Return to the fight");
        againButton.setFont(customFont);
        againButton.setForeground(Color.WHITE);
        againButton.setBackground(Color.BLACK);
        againButton.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        againButton.setPreferredSize(new Dimension(80, 25));
        againButton.setMaximumSize(new Dimension(80, 25));
        againButton.setMinimumSize(new Dimension(80, 25));
        againButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                future.complete(null);
            }
        });

        buttonPanel.add(againButton);

        JButton exitButton = new JButton("Exit");
        exitButton.setFont(customFont);
        exitButton.setForeground(Color.WHITE);
        exitButton.setBackground(Color.BLACK);
        exitButton.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        exitButton.setPreferredSize(new Dimension(80, 25));
        exitButton.setMaximumSize(new Dimension(80, 25));
        exitButton.setMinimumSize(new Dimension(80, 25));
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        buttonPanel.add(exitButton);
        frame.add(playerPanel);
        frame.add(playerStats);
        frame.add(buttonPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        return future;
    }

    public void combatEncounter(Character tempBoy) {
        Enemy enemy = new Enemy();
        tempBoy.getAbilities().clear();
        tempBoy.setAbilities(tempBoy.generateRandSpells());

        JFrame frame = new JFrame();
        frame.setTitle("Combat");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setPreferredSize(new Dimension(1000,1000));
        frame.setMinimumSize(new Dimension(1000,1000));
        frame.setMinimumSize(new Dimension(1000,1000));
        frame.setLocationRelativeTo(null);
        frame.setLayout(new GridLayout(1,3));

        Font customFont = null;
        try {
            customFont  = Font.createFont(Font.TRUETYPE_FONT, new File("RagnaRunesPersonalUseRegular-Rp7AV.otf")).deriveFont(20f);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            return;
        }

        final BufferedImage mageImg;
        final BufferedImage rogueImg;
        final BufferedImage warriorImg;
        final BufferedImage enemyImg;

        try {
            mageImg = ImageIO.read(new File("D:\\POO\\Tema2\\PROIECT\\mage.jpg"));
            rogueImg = ImageIO.read(new File("D:\\POO\\Tema2\\PROIECT\\rogue.jpg"));
            warriorImg = ImageIO.read(new File("D:\\POO\\Tema2\\PROIECT\\warrior.jpg"));
            enemyImg = ImageIO.read(new File("D:\\POO\\Tema2\\PROIECT\\enemy-visited.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        ImageIcon enemyIcon = new ImageIcon(enemyImg.getScaledInstance(400,400,Image.SCALE_SMOOTH));
        ImageIcon playerIcon = null;

        switch (tempBoy.getType()){
            case "Mage":
                playerIcon = new ImageIcon(mageImg.getScaledInstance(400,400,Image.SCALE_SMOOTH));
                break;
            case "Rogue":
                playerIcon = new ImageIcon(rogueImg.getScaledInstance(400,400,Image.SCALE_SMOOTH));
                break;
            case "Warrior":
                playerIcon = new ImageIcon(warriorImg.getScaledInstance(400,400,Image.SCALE_SMOOTH));
                break;
            default:
                playerIcon = null;
                break;
        }

        JPanel playerPanel = new JPanel();
        playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.Y_AXIS));
        playerPanel.setPreferredSize(new Dimension(1000,400));
        playerPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JLabel playerLabel = new JLabel(playerIcon);
        playerLabel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        playerPanel.add(playerLabel);

        JPanel playerStats = new JPanel(new GridLayout(6,1));

        JLabel hpLabel = new JLabel("Health");
        hpLabel.setFont(customFont.deriveFont(16f));
        hpLabel.setForeground(Color.WHITE);
        final JTextField hpField;
        hpField  = new JTextField(String.valueOf(tempBoy.getCurrentHealth()));
        hpField.setPreferredSize(new Dimension(150,30));
        hpField.setMaximumSize(new Dimension(150,30));
        hpField.setMinimumSize(new Dimension(150,30));
        hpField.setAlignmentX(Component.CENTER_ALIGNMENT);
        hpField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        hpLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        hpLabel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        hpField.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        playerStats.add(hpLabel);
        playerStats.add(hpField);

        JLabel manaLabel = new JLabel("Mana");
        manaLabel.setFont(customFont.deriveFont(16f));
        manaLabel.setForeground(Color.WHITE);
        JTextField manaField = new JTextField(String.valueOf(tempBoy.getCurrentMana()));
        manaField.setPreferredSize(new Dimension(150,30));
        manaField.setMaximumSize(new Dimension(150,30));
        manaField.setMinimumSize(new Dimension(150,30));
        manaField.setAlignmentX(Component.CENTER_ALIGNMENT);
        manaField.setBorder(BorderFactory.createLineBorder(Color.BLACK,2));
        manaLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        manaLabel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        manaField.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        playerStats.add(manaLabel);
        playerStats.add(manaField);

        JLabel abNRLabel = new JLabel("Abilities left:");
        abNRLabel.setFont(customFont.deriveFont(16f));
        abNRLabel.setForeground(Color.WHITE);
        JTextField abNRField = new JTextField(String.valueOf(tempBoy.getAbilities().size()));
        abNRField.setPreferredSize(new Dimension(150,30));
        abNRField.setMaximumSize(new Dimension(150,30));
        abNRField.setMinimumSize(new Dimension(150,30));
        abNRField.setAlignmentX(Component.CENTER_ALIGNMENT);
        abNRField.setBorder(BorderFactory.createLineBorder(Color.BLACK,2));
        abNRLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        abNRLabel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        abNRField.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        playerStats.add(abNRLabel);
        playerStats.add(abNRField);

        playerPanel.add(playerStats);

        JPanel middlePanel = new JPanel(new GridLayout(4,1,10,10));
        middlePanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        middlePanel.setPreferredSize(new Dimension(1000,200));

        JButton abButton = new JButton("Abilities");
        abButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        abButton.setFont(customFont);
        abButton.setForeground(Color.WHITE);
        abButton.setBackground(Color.BLACK);
        abButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        abButton.setPreferredSize(new Dimension(150,30));
        abButton.setMinimumSize(new Dimension(150,30));
        abButton.setMaximumSize(new Dimension(150,30));
        abButton.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        middlePanel.add(abButton);

        JButton attackButton = new JButton("Attack");
        attackButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        attackButton.setFont(customFont);
        attackButton.setForeground(Color.WHITE);
        attackButton.setBackground(Color.BLACK);
        attackButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        attackButton.setPreferredSize(new Dimension(150,30));
        attackButton.setMinimumSize(new Dimension(150,30));
        attackButton.setMaximumSize(new Dimension(150,30));
        attackButton.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        middlePanel.add(attackButton);

        JPanel combatPanel = new JPanel(new GridLayout(2,1,10,10));
        final JLabel combatLogLabel;
        combatLogLabel = new JLabel("Combat Log :");
        combatLogLabel.setFont(customFont);
        combatLogLabel.setForeground(Color.WHITE);
        combatLogLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        combatLogLabel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JPanel movesPanel = new JPanel(new GridLayout(4,1,10,10));

        JLabel playerLogLabel = new JLabel("Player's Move :");
        playerLogLabel.setFont(customFont);
        playerLogLabel.setForeground(Color.WHITE);

        JTextField comabtLogFieldPlayer = new JTextField();
        comabtLogFieldPlayer.setAlignmentX(Component.CENTER_ALIGNMENT);
        comabtLogFieldPlayer.setAlignmentY(Component.CENTER_ALIGNMENT);
        comabtLogFieldPlayer.setBorder(BorderFactory.createLineBorder(Color.BLACK,2));
        comabtLogFieldPlayer.setPreferredSize(new Dimension(200,30));
        comabtLogFieldPlayer.setMinimumSize(new Dimension(200,30));
        comabtLogFieldPlayer.setMaximumSize(new Dimension(200,30));

        JLabel enemyLogLabel = new JLabel("Enemy's move");
        enemyLogLabel.setFont(customFont);
        enemyLogLabel.setForeground(Color.WHITE);

        JTextField combatLogFieldEnemy = new JTextField();
        combatLogFieldEnemy.setAlignmentX(Component.CENTER_ALIGNMENT);
        combatLogFieldEnemy.setAlignmentY(Component.CENTER_ALIGNMENT);
        combatLogFieldEnemy.setBorder(BorderFactory.createLineBorder(Color.BLACK,2));
        combatLogFieldEnemy.setPreferredSize(new Dimension(200,30));
        combatLogFieldEnemy.setMinimumSize(new Dimension(200,30));
        combatLogFieldEnemy.setMaximumSize(new Dimension(200,30));

        movesPanel.add(playerLogLabel);
        movesPanel.add(comabtLogFieldPlayer);
        movesPanel.add(enemyLogLabel);
        movesPanel.add(combatLogFieldEnemy);

        combatPanel.add(combatLogLabel);
        combatPanel.add(movesPanel);

        middlePanel.add(combatPanel);

        JPanel enemyPanel = new JPanel();
        enemyPanel.setLayout(new BoxLayout(enemyPanel, BoxLayout.Y_AXIS));
        enemyPanel.setPreferredSize(new Dimension(1000,400));
        enemyPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JLabel enemyLabel = new JLabel(enemyIcon);
        enemyLabel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        enemyPanel.add(enemyLabel);

        JPanel enemyStats = new JPanel(new GridLayout(6,1,10,10));

        JLabel enemeyHpLabel = new JLabel("Health");
        enemeyHpLabel.setFont(customFont.deriveFont(16f));
        enemeyHpLabel.setForeground(Color.WHITE);
        final JTextField enemyHpField;
        enemyHpField  = new JTextField(String.valueOf(enemy.getCurrentHealth()));
        enemyHpField.setPreferredSize(new Dimension(150,30));
        enemyHpField.setMaximumSize(new Dimension(150,30));
        enemyHpField.setMinimumSize(new Dimension(150,30));
        enemyHpField.setAlignmentX(Component.CENTER_ALIGNMENT);
        enemyHpField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        enemeyHpLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        enemeyHpLabel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        enemyHpField.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        enemyStats.add(enemeyHpLabel);
        enemyStats.add(enemyHpField);

        JLabel enemyManaLabel = new JLabel("Mana");
        enemyManaLabel.setFont(customFont.deriveFont(16f));
        enemyManaLabel.setForeground(Color.WHITE);
        JTextField enemyManaField = new JTextField(String.valueOf(enemy.getCurrentMana()));
        enemyManaField.setPreferredSize(new Dimension(150,30));
        enemyManaField.setMaximumSize(new Dimension(150,30));
        enemyManaField.setMinimumSize(new Dimension(150,30));
        enemyManaField.setAlignmentX(Component.CENTER_ALIGNMENT);
        enemyManaField.setBorder(BorderFactory.createLineBorder(Color.BLACK,2));
        enemyManaLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        enemyManaField.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        enemyManaLabel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        enemyStats.add(enemyManaLabel);
        enemyStats.add(enemyManaField);

        JLabel enemyAbNRLabel = new JLabel("Abilities left:");
        enemyAbNRLabel.setFont(customFont.deriveFont(16f));
        enemyAbNRLabel.setForeground(Color.WHITE);
        JTextField enemyAbNRField = new JTextField(String.valueOf(enemy.getAbilities().size()));
        enemyAbNRField.setPreferredSize(new Dimension(150,30));
        enemyAbNRField.setMaximumSize(new Dimension(150,30));
        enemyAbNRField.setMinimumSize(new Dimension(150,30));
        enemyAbNRField.setAlignmentX(Component.CENTER_ALIGNMENT);
        enemyAbNRField.setBorder(BorderFactory.createLineBorder(Color.BLACK,2));
        enemyAbNRLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        enemyAbNRField.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        enemyAbNRLabel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        enemyStats.add(enemyAbNRLabel);
        enemyStats.add(enemyAbNRField);

        enemyPanel.add(enemyStats);

        frame.add(playerPanel);
        frame.add(middlePanel);
        frame.add(enemyPanel);
        frame.setVisible(true);

        attackButton.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               int ogHp = tempBoy.getCurrentHealth();
               int damageDealt = tempBoy.getDamage();
               enemy.receiveDamage(damageDealt);
               enemyCombat(tempBoy,enemy);
               comabtLogFieldPlayer.setText(tempBoy.getName() + " dealt " + damageDealt + " basic damage");
               if (enemy.getCurrentHealth() <= 0) {
                   frame.dispose();
                   JOptionPane.showMessageDialog(null,"You have defeated this foe");
                   tempBoy.setCurrentExperience(tempBoy.getCurrentExperience()+100);
                   tempBoy.setEnemiesSlain(tempBoy.getEnemiesSlain()+1);
                   if (tempBoy.getCurrentExperience() >= 10 * tempBoy.getCurrentLevel())
                       tempBoy.levelUp();
               }
               else if (tempBoy.getCurrentHealth() <= 0) {
                   frame.dispose();
               }
               combatLogFieldEnemy.setText("Enemy dealt " + (ogHp - tempBoy.getCurrentHealth()) + " damage");
               enemyHpField.setText(String.valueOf(enemy.getCurrentHealth()));
               enemyManaField.setText(String.valueOf(enemy.getCurrentMana()));
               enemyAbNRField.setText(String.valueOf(enemy.getAbilities().size()));
               hpField.setText(String.valueOf(tempBoy.getCurrentHealth()));
               manaField.setText(String.valueOf(tempBoy.getCurrentMana()));
               abNRField.setText(String.valueOf(tempBoy.getAbilities().size()));
               frame.revalidate();
               frame.repaint();
           }
        });

        abButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int ogHp = tempBoy.getCurrentHealth();
                abilityGUI(tempBoy,enemy).thenRun(() -> {
                    enemyCombat(tempBoy,enemy);
                    combatLogFieldEnemy.setText("Enemy dealt " + (ogHp - tempBoy.getCurrentHealth()) + " damage");
                    if (enemy.getCurrentHealth() <= 0) {
                        JOptionPane.showMessageDialog(null,"You have defeated this foe.");
                        frame.dispose();
                        tempBoy.setCurrentExperience(tempBoy.getCurrentExperience()+100);
                        tempBoy.setEnemiesSlain(tempBoy.getEnemiesSlain()+1);
                        if (tempBoy.getCurrentExperience() >= 10 * tempBoy.getCurrentLevel())
                            tempBoy.levelUp();
                    }
                    else if (tempBoy.getCurrentHealth() <= 0) {
                        frame.dispose();
                    }
                    enemyHpField.setText(String.valueOf(enemy.getCurrentHealth()));
                    enemyManaField.setText(String.valueOf(enemy.getCurrentMana()));
                    enemyAbNRField.setText(String.valueOf(enemy.getAbilities().size()));
                    hpField.setText(String.valueOf(tempBoy.getCurrentHealth()));
                    manaField.setText(String.valueOf(tempBoy.getCurrentMana()));
                    abNRField.setText(String.valueOf(tempBoy.getAbilities().size()));
                    frame.revalidate();
                    frame.repaint();
                });
            }
        });
    }

    public void checkBehaivour(Character tempBoy) {
        Font customFont = null;
        try {
            customFont  = Font.createFont(Font.TRUETYPE_FONT, new File("RagnaRunesPersonalUseRegular-Rp7AV.otf")).deriveFont(20f);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            return;
        }


        if (this.grid.getCurrentCell().getOriginalCellType().equals(CellEntityType.SANCTUARY)) {
            if (!this.grid.getCurrentCell().isVisited()) {
                Random rand = new Random();
                int heal = rand.nextInt(tempBoy.getMaxHealth());
                int mana = rand.nextInt(tempBoy.getMaxMana());
                tempBoy.regenHealth(heal);
                tempBoy.regenMana(mana);
                JOptionPane.showMessageDialog(null,tempBoy.getName() + " restored " + heal + " health and " + mana + " mana");
            }
            else {
                JOptionPane.showMessageDialog(null,tempBoy.getName() + " has already received this sanctuary's blessing");
            }
        }
        else if (this.grid.getCurrentCell().getOriginalCellType().equals(CellEntityType.PORTAL)) {
            tempBoy.regenHealth(tempBoy.getMaxHealth());
            tempBoy.regenMana(tempBoy.getMaxMana());
            tempBoy.setCurrentExperience(tempBoy.getCurrentExperience() + 300);
            if (tempBoy.getCurrentExperience() >= 10 * tempBoy.getCurrentLevel()) {
                tempBoy.levelUp();
            }
            UIManager.put("OptionPane.messageFont", customFont);
            JOptionPane.showMessageDialog(null,tempBoy.getName() + " moves on to the next dungeon. May the gods be in their favour again!");
            UIManager.put("OptionPane.messageFont", UIManager.getDefaults().getFont("Label.font"));
            this.grid = Grid.generateGrid(this.length,this.width,new Cell(0,0, CellEntityType.VOID,false,false));
        }
        else if (this.grid.getCurrentCell().getOriginalCellType().equals(CellEntityType.ENEMY)) {
           if(!this.grid.getCurrentCell().isVisited()) {
               combatEncounter(tempBoy);
           }
           else {
               UIManager.put("OptionPane.messageFont", customFont);
               JOptionPane.showMessageDialog(null,tempBoy.getName() + " has already defeated this foe.");
               UIManager.put("OptionPane.messageFont", UIManager.getDefaults().getFont("Label.font"));
           }
        }
    }

    public void trueEnd() {
        Font customFont = null;
        try {
            customFont = Font.createFont(Font.TRUETYPE_FONT, new File("RagnaRunesPersonalUseRegular-Rp7AV.otf")).deriveFont(20f);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            return;
        }

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBackground(Color.getHSBColor(281f / 360f, 0.42f, 0.48f));
        frame.setLayout(new BorderLayout());
        frame.setSize(800, 800);
        frame.setTitle("Journey's End");

        final BufferedImage endImg;
        try {
            endImg = ImageIO.read(new File("true-end.jpeg"));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        JLabel endLabel = new JLabel(new ImageIcon(endImg.getScaledInstance(1200, 800, Image.SCALE_SMOOTH)));
        endLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        frame.add(endLabel, BorderLayout.CENTER);

        JPanel textPanel = new JPanel();
        textPanel.setBackground(Color.getHSBColor(281f / 360f, 0.42f, 0.48f));
        textPanel.setLayout(new BorderLayout());

        JLabel journeyLabel = new JLabel("The Journey has Ended", SwingConstants.CENTER);
        journeyLabel.setFont(customFont);
        journeyLabel.setForeground(Color.WHITE);
        textPanel.add(journeyLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.BLACK);

        JButton endButton = new JButton("Depart This Land");
        endButton.setFont(customFont);
        endButton.setForeground(Color.WHITE);
        endButton.setBackground(Color.BLACK);
        endButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        endButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        buttonPanel.add(endButton);

        frame.add(textPanel, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }


    public void runGUI() throws Exception {
        Font customFont = null;
        try {
            customFont  = Font.createFont(Font.TRUETYPE_FONT, new File("RagnaRunesPersonalUseRegular-Rp7AV.otf")).deriveFont(20f);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            return;
        }

        UIManager.put("OptionPane.Background", Color.getHSBColor(281f/360f, 0.42f, 0.48f));
        UIManager.put("Panel.background", Color.getHSBColor(281f/360f, 0.42f, 0.48f));
        UIManager.put("OptionPane.messageForeground", Color.BLACK);
        UIManager.put("OptionPane.buttonFont", customFont);

        if (selectedAccount == null)
            selectAccountGUI();
        while (selectedAccount == null){
            Thread.sleep(500);
        }
        // Proceed to character selection
        final Character[] tempBoy = {selectCharacterGUI()};
        while (tempBoy[0] == null){
            Thread.sleep(500);
        }
        if (tempBoy[0].getCurrentExperience() >= 10 * tempBoy[0].getCurrentLevel())
            tempBoy[0].levelUp();

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000,1200);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
        frame.setTitle("Game :");

        JPanel panel = new JPanel();

        JLabel hpLabel = new JLabel("Health");
        hpLabel.setFont(customFont);
        hpLabel.setForeground(Color.WHITE);
        final JTextField hpField;
        hpField  = new JTextField(String.valueOf(tempBoy[0].getCurrentHealth()));
        hpField.setPreferredSize(new Dimension(150,30));
        hpField.setAlignmentX(Component.CENTER_ALIGNMENT);
        hpField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        hpLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel manaLabel = new JLabel("Mana");
        manaLabel.setFont(customFont);
        manaLabel.setForeground(Color.WHITE);
        JTextField manaField = new JTextField(String.valueOf(tempBoy[0].getCurrentMana()));
        manaField.setPreferredSize(new Dimension(150,30));
        manaField.setAlignmentX(Component.CENTER_ALIGNMENT);
        manaField.setBorder(BorderFactory.createLineBorder(Color.BLACK,2));
        manaLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel expLabel = new JLabel("Experience");
        expLabel.setFont(customFont);
        expLabel.setForeground(Color.WHITE);
        JTextField expField = new JTextField(String.valueOf(tempBoy[0].getCurrentExperience()));
        expField.setPreferredSize(new Dimension(150,30));
        expField.setAlignmentX(Component.CENTER_ALIGNMENT);
        expField.setBorder(BorderFactory.createLineBorder(Color.BLACK,2));
        expLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel levelLabel = new JLabel("Level");
        levelLabel.setFont(customFont);
        levelLabel.setForeground(Color.WHITE);
        JTextField levelField = new JTextField(String.valueOf(tempBoy[0].getCurrentLevel()));
        levelField.setPreferredSize(new Dimension(150,30));
        levelField.setAlignmentX(Component.CENTER_ALIGNMENT);
        levelField.setBorder(BorderFactory.createLineBorder(Color.BLACK,2));
        levelLabel.setAlignmentX(Component.CENTER_ALIGNMENT);


        JButton northButton = new JButton("North");
        northButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        northButton.setFont(customFont);
        northButton.setForeground(Color.WHITE);
        northButton.setBackground(Color.BLACK);
        northButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        northButton.setPreferredSize(new Dimension(150,30));
        northButton.setMinimumSize(new Dimension(150,30));
        northButton.setMaximumSize(new Dimension(150,30));


        JButton southButton = new JButton("South");
        southButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        southButton.setFont(customFont);
        southButton.setForeground(Color.WHITE);
        southButton.setBackground(Color.BLACK);
        southButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        southButton.setPreferredSize(new Dimension(150,30));
        southButton.setMinimumSize(new Dimension(150,30));
        southButton.setMaximumSize(new Dimension(150,30));


        JButton westButton = new JButton("West");
        westButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        westButton.setFont(customFont);
        westButton.setForeground(Color.WHITE);
        westButton.setBackground(Color.BLACK);
        westButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        westButton.setPreferredSize(new Dimension(150,30));
        westButton.setMinimumSize(new Dimension(150,30));
        westButton.setMaximumSize(new Dimension(150,30));


        JButton eastButton = new JButton("East");
        eastButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        eastButton.setFont(customFont);
        eastButton.setForeground(Color.WHITE);
        eastButton.setBackground(Color.BLACK);
        eastButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        eastButton.setPreferredSize(new Dimension(150,30));
        eastButton.setMinimumSize(new Dimension(150,30));
        eastButton.setMaximumSize(new Dimension(150,30));


        final JPanel[] gridPanel = {grid.gridPanel()};
        frame.add(gridPanel[0], BorderLayout.CENTER);
        frame.setVisible(true);
        northButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    grid.goNorth();
                    checkBehaivour(tempBoy[0]);
                    hpField.setText(String.valueOf(tempBoy[0].getCurrentHealth()));
                    manaField.setText(String.valueOf(tempBoy[0].getCurrentMana()));
                    expField.setText(String.valueOf(tempBoy[0].getCurrentExperience()));
                    levelField.setText(String.valueOf(tempBoy[0].getCurrentLevel()));
                    grid.updateGridPanel(gridPanel[0]);
                    panel.revalidate();
                    panel.repaint();
                    frame.revalidate();
                    frame.repaint();
                } catch (ImpossibleMove e1) {
                    JOptionPane.showMessageDialog(frame, e1.getMessage());
                }
            }
        });

        southButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    grid.goSouth();
                    checkBehaivour(tempBoy[0]);
                    hpField.setText(String.valueOf(tempBoy[0].getCurrentHealth()));
                    manaField.setText(String.valueOf(tempBoy[0].getCurrentMana()));
                    expField.setText(String.valueOf(tempBoy[0].getCurrentExperience()));
                    levelField.setText(String.valueOf(tempBoy[0].getCurrentLevel()));
                    grid.updateGridPanel(gridPanel[0]);
                    panel.revalidate();
                    panel.repaint();
                    frame.revalidate();
                    frame.repaint();
                } catch (ImpossibleMove e1) {
                    JOptionPane.showMessageDialog(frame, e1.getMessage());
                }
            }
        });

        westButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    grid.goWest();
                    checkBehaivour(tempBoy[0]);
                    hpField.setText(String.valueOf(tempBoy[0].getCurrentHealth()));
                    manaField.setText(String.valueOf(tempBoy[0].getCurrentMana()));
                    expField.setText(String.valueOf(tempBoy[0].getCurrentExperience()));
                    levelField.setText(String.valueOf(tempBoy[0].getCurrentLevel()));
                    grid.updateGridPanel(gridPanel[0]);
                    panel.revalidate();
                    panel.repaint();
                    frame.revalidate();
                    frame.repaint();
                } catch (ImpossibleMove e1) {
                    JOptionPane.showMessageDialog(frame, e1.getMessage());
                }
            }
        });

        eastButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    grid.goEast();
                    checkBehaivour(tempBoy[0]);
                    hpField.setText(String.valueOf(tempBoy[0].getCurrentHealth()));
                    manaField.setText(String.valueOf(tempBoy[0].getCurrentMana()));
                    expField.setText(String.valueOf(tempBoy[0].getCurrentExperience()));
                    levelField.setText(String.valueOf(tempBoy[0].getCurrentLevel()));
                    grid.updateGridPanel(gridPanel[0]);
                    panel.revalidate();
                    panel.repaint();
                    frame.revalidate();
                    frame.repaint();
                } catch (ImpossibleMove e1) {
                    JOptionPane.showMessageDialog(frame, e1.getMessage());
                }
            }
        });

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(northButton);
        panel.add(Box.createRigidArea(new Dimension(0,10)));
        panel.add(southButton);
        panel.add(Box.createRigidArea(new Dimension(0,10)));
        panel.add(westButton);
        panel.add(Box.createRigidArea(new Dimension(0,10)));
        panel.add(eastButton);
        panel.add(Box.createRigidArea(new Dimension(0,10)));
        panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        panel.add(hpLabel);
        panel.add(hpField);
        panel.add(manaLabel);
        panel.add(manaField);
        panel.add(expLabel);
        panel.add(expField);
        panel.add(levelLabel);
        panel.add(levelField);
        panel.setBackground(Color.getHSBColor(281f/360f, 0.42f, 0.48f));
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
        frame.add(panel, BorderLayout.WEST);
        frame.setVisible(true);

        // Replace the final while loop with:
        while (true) {
            int truEnd = 0;
            for (Character character : selectedAccount.getCharacters()) {
                if (character.getCurrentHealth() <= 0) {
                    truEnd++;
                }
            }
            if (truEnd == selectedAccount.getCharacters().size()) {
                    trueEnd();
                    break;
            }
            else if (tempBoy[0].getCurrentHealth() <= 0) {
                tempBoy[0].setCurrentHealth(999);
                endScreen(tempBoy[0]).thenRun(() -> {
                    this.grid = Grid.generateGrid(this.length, this.width, new Cell(0, 0, CellEntityType.VOID, false, false));
                    grid.updateGridPanel(gridPanel[0]);
                    panel.revalidate();
                    panel.repaint();
                });
                try {
                    tempBoy[0].setCurrentHealth(0);
                    tempBoy[0] = selectCharacterGUI();
                    while (tempBoy[0] == null) {
                        Thread.sleep(500);
                    }
                    if (tempBoy[0].getCurrentExperience() >= 10 * tempBoy[0].getCurrentLevel()) {
                        tempBoy[0].levelUp();
                    }
                    hpField.setText(String.valueOf(tempBoy[0].getCurrentHealth()));
                    manaField.setText(String.valueOf(tempBoy[0].getCurrentMana()));
                    expField.setText(String.valueOf(tempBoy[0].getCurrentExperience()));
                    levelField.setText(String.valueOf(tempBoy[0].getCurrentLevel()));
            } catch (ImpossibleMove e1) {
                e1.printStackTrace();
                }
            }
            try {
                Thread.sleep(100); // Add small delay to prevent CPU overload
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

           // checkIfTrueEnd(selectedAccount);
        }
    }

    public static Game getInstance(){
        if (game == null){
            game = new Game();
        }
        return game;
    }

    public static void main(String[] args) throws Exception {
        Game game = Game.getInstance();
        game.runGUI();
    }
}
