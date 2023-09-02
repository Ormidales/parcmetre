import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;

class Transaction {
    int minutes;
    double paiement;
    double total;

    public Transaction(int minutes, double paiement, double total) {
        this.minutes = minutes;
        this.paiement = paiement;
        this.total = total;
    }
}

public class ParcmetreGUI {
    private static final int DUREE_MIN = 15;
    private static final int DUREE_MAX = 180;

    private static ArrayList<Transaction> transactions = new ArrayList<>();

    public static double getTarifParMinute() {
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        // Exemple : tarif moins cher le dimanche et plus cher en heures de pointe
        if (dayOfWeek == Calendar.SUNDAY) {
            return 0.03;
        } else if (hour >= 17 && hour <= 19) {
            return 0.07;
        } else {
            return 0.05;
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Parcmètre Virtuel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));

        JLabel label1 = new JLabel("Minutes:");
        JTextField textField1 = new JTextField();

        JLabel label2 = new JLabel("Paiement:");
        JTextField textField2 = new JTextField();

        JLabel label3 = new JLabel("Total à payer:");
        JLabel label4 = new JLabel("0.0");

        JButton calculateButton = new JButton("Calculer");
        JButton historyButton = new JButton("Historique");

        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int minutes = Integer.parseInt(textField1.getText());

                    if (minutes < DUREE_MIN || minutes > DUREE_MAX) {
                        JOptionPane.showMessageDialog(frame,
                                "La durée doit être comprise entre " + DUREE_MIN + " et " + DUREE_MAX + " minutes.");
                        return;
                    }

                    double tarifParMinute = getTarifParMinute();
                    double total = tarifParMinute * minutes;
                    label4.setText(String.valueOf(total));

                    double paiement = Double.parseDouble(textField2.getText());
                    if (paiement < total) {
                        JOptionPane.showMessageDialog(frame, "Paiement insuffisant.");
                    } else {
                        transactions.add(new Transaction(minutes, paiement, total));
                        JOptionPane.showMessageDialog(frame, "Paiement accepté. Votre reste est de " + (paiement - total) + " euros.");
                    }

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Veuillez entrer des nombres valides.");
                }
            }
        });

        historyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringBuilder history = new StringBuilder();
                for (Transaction t : transactions) {
                    history.append("Minutes: ").append(t.minutes)
                            .append(", Paiement: ").append(t.paiement)
                            .append(", Total: ").append(t.total)
                            .append("\n");
                }
                JOptionPane.showMessageDialog(frame, history.length() > 0 ? history.toString() : "Pas d'historique");
            }
        });

        panel.add(label1);
        panel.add(textField1);
        panel.add(label2);
        panel.add(textField2);
        panel.add(label3);
        panel.add(label4);
        panel.add(calculateButton);
        panel.add(historyButton);

        frame.add(panel);
        frame.setVisible(true);
    }
}
