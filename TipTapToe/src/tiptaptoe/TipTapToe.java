package tiptaptoe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

public class TipTapToe{
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Menu().setVisible(true); 
            
        });
    }
}

class GameSystem {
    private static final int MAX_PLAYERS = 100;
    Player[] players;
    int playerCount;
    Player loggedInPlayer;
    Player player2;
    Game currentGame;
    JFrame currentFrame;
    
    
    public GameSystem() {
        players = new Player[MAX_PLAYERS];
        playerCount = 0;
        loggedInPlayer = null;
        currentGame = null;
        
        // Agregar algunos jugadores por defecto para pruebas
        registerPlayer("admin", "admin123", "Administrador", 0);
        registerPlayer("user1", "pass1", "Usuario Uno", 0);
        registerPlayer("user2", "pass2", "Usuario Dos", 0);
    }
    void startGame() {
        currentGame = new Game(loggedInPlayer, player2, this);
        currentGame.showGameBoard();
    }

    void showRanking() {
        // Ordenar jugadores por puntos (descendente)
        Player[] sortedPlayers = Arrays.copyOf(players, playerCount);
        Arrays.sort(sortedPlayers, (p1, p2) -> Integer.compare(p2.getPoints(), p1.getPoints()));
        
        JFrame frame = new JFrame("Ranking de Jugadores");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
        public void windowClosed(WindowEvent e) {
            PanelJuego dialog = new PanelJuego(null, true);
            dialog.setVisible(true);
        }
        });
        frame.setSize(400, 300);

        String[] columnNames = {"Posición", "Nombre", "Usuario", "Puntos"};
        Object[][] data = new Object[playerCount][4];

        for (int i = 0; i < playerCount; i++) {
            data[i][0] = i + 1;
            data[i][1] = sortedPlayers[i].getName();
            data[i][2] = sortedPlayers[i].getUsername();
            data[i][3] = sortedPlayers[i].getPoints();
        }

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane);

        frame.setLocationRelativeTo(currentFrame);
        frame.setVisible(true);
    }

    public void gameFinished(Player winner) {
        if (winner != null) {
            winner.addPoints(10); // 10 puntos por victoria
            JOptionPane.showMessageDialog(currentFrame, "¡" + winner.getName() + " ha ganado!", "Fin del juego", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(currentFrame, "¡Empate!", "Fin del juego", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    Player authenticate(String username, String password) {
        for (int i = 0; i < playerCount; i++) {
            if (players[i].getUsername().equals(username) && players[i].checkPassword(password)) {
                return players[i];
            }
        }
        return null;
    }

    boolean usernameExists(String username) {
        for (int i = 0; i < playerCount; i++) {
            if (players[i].getUsername().equalsIgnoreCase(username)) {
                return true;
            }
        }
        return false;
    }

    Player findPlayer(String username) {
        for (int i = 0; i < playerCount; i++) {
            if (players[i].getUsername().equalsIgnoreCase(username)) {
                return players[i];
            }
        }
        return null;
    }

    void registerPlayer(String username, String password, String name, int points) {
        if (playerCount < MAX_PLAYERS) {
            players[playerCount++] = new Player(username, password, name, points);
        }
    }
}

class Player {
    private String username;
    private String password;
    private String name;
    private int points;

    public Player(String username, String password, String name, int points) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.points = points;
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public int getPoints() {
        return points;
    }

    public void addPoints(int points) {
        this.points += points;
    }
}

class Game {
    private Player player1;
    private Player player2;
    private char[][] board;
    private boolean player1Turn;
    private GameSystem gameSystem;
    private JFrame gameFrame;

    public Game(Player player1, Player player2, GameSystem gameSystem) {
        this.player1 = player1;
        this.player2 = player2;
        this.gameSystem = gameSystem;
        this.board = new char[3][3];
        this.player1Turn = true;
        initializeBoard();
    }

    void initializeBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = '-';
            }
        }
    }

    public void showGameBoard() {
        gameFrame = new JFrame("X-O Game: " + player1.getName() + " vs " + player2.getName());    
        gameFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        gameFrame.setSize(400, 400);
        gameFrame.setLayout(new BorderLayout());
        gameFrame.addWindowListener(new WindowAdapter() {
            
        public void windowClosed(WindowEvent e) {
            PanelJuego dialog = new PanelJuego(null, true);
            dialog.setVisible(true);
        }
        });
        JPanel boardPanel = new JPanel(new GridLayout(3, 3));
        JButton[][] buttons = new JButton[3][3];

        for (int fila = 0; fila < 3; fila++) {
            for (int colum= 0; colum < 3; colum++) {
                buttons[fila][colum] = new JButton();
                buttons[fila][colum].setFont(new Font("Arial", Font.BOLD, 60));
                final int row = fila;
                final int col = colum;
                buttons[fila][colum].addActionListener(e -> makeMove(row, col, buttons));
                boardPanel.add(buttons[fila][colum]);
            }
        }

        JLabel statusLabel = new JLabel("Turno de: " + (player1Turn ? player1.getName() + " (X)" : player2.getName() + " (O)"), SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 16));

        gameFrame.add(statusLabel, BorderLayout.NORTH);
        gameFrame.add(boardPanel, BorderLayout.CENTER);

        gameFrame.setLocationRelativeTo(null);
        gameFrame.setVisible(true);
    }

    void makeMove(int fila, int col, JButton[][] buttons) {
        if (board[fila][col] != '-') {
        JOptionPane.showMessageDialog(buttons[fila][col], 
            "Casilla ocupada. Elige otra.", 
            "Movimiento inválido", 
            JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        char symbol = player1Turn ? 'X' : 'O';
        board[fila][col] = symbol;
        buttons[fila][col].setText(String.valueOf(symbol));
        buttons[fila][col].setEnabled(true);
        
        if (checkWin()) {
            gameFrame.dispose();
            gameSystem.gameFinished(player1Turn ? player1 : player2);
            PanelJuego dialog = new PanelJuego(null,true);
            dialog.setVisible(true);
            
        } else if (isBoardFull()) {
            gameFrame.dispose();
            gameSystem.gameFinished(null);
            PanelJuego dialog = new PanelJuego(null,true);
            dialog.setVisible(true);
        } else {
            player1Turn = !player1Turn;
            updateStatusLabel();
        }
    }

    void updateStatusLabel() {
        for (Component comp : gameFrame.getContentPane().getComponents()) {
            if (comp instanceof JLabel && ((JLabel) comp).getText().startsWith("Turno de:")) {
                ((JLabel) comp).setText("Turno de: " + (player1Turn ? player1.getName() + " (X)" : player2.getName() + " (O)"));
                break;
            }
        }
    }

    boolean checkWin() {
        // Verificar filas
        for (int i = 0; i < 3; i++) {
            if (board[i][0] != '-' && board[i][0] == board[i][1] && board[i][0] == board[i][2]) {
                return true;
            }
        }

        // Verificar columnas
        for (int j = 0; j < 3; j++) {
            if (board[0][j] != '-' && board[0][j] == board[1][j] && board[0][j] == board[2][j]) {
                return true;
            }
        }

        // Verificar diagonales
        if (board[0][0] != '-' && board[0][0] == board[1][1] && board[0][0] == board[2][2]) {
            return true;
        }

        if (board[0][2] != '-' && board[0][2] == board[1][1] && board[0][2] == board[2][0]) {
            return true;
        }

        return false;
    }
    
    boolean isBoardFull() {
        for (int fila = 0; fila < 3; fila++) {
            for (int col = 0; col < 3; col++) {
                if (board[fila][col] == '-') {
                    return false;
                }
            }
        }
        return true;
    }
}