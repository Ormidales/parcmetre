import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Classe ParcmetreVirtuel qui simule le fonctionnement d'un parcmètre virtuel.
 */
public class Parcmetre {

    /**
     * Méthode pour saisir le nombre de minutes de stationnement.
     *
     * @param scanner L'objet Scanner pour lire l'entrée de l'utilisateur.
     * @return Le nombre de minutes saisi.
     */
    public static final int DUREE_MIN = 15;  // Durée minimale en minutes
    public static final int DUREE_MAX = 180; // Durée maximale en minutes

    public static int saisirMinutes(Scanner scanner) {
        int minutes = 0;
        while (true) {
            try {
                System.out.println("Combien de minutes voulez-vous stationner ?");
                minutes = scanner.nextInt();
                if (minutes < DUREE_MIN || minutes > DUREE_MAX) {
                    System.out.printf("La durée doit être comprise entre %d et %d minutes.\n", DUREE_MIN, DUREE_MAX);
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Entrée invalide. Veuillez entrer un nombre entier.");
                scanner.next();
            } catch (Exception e) {
                System.out.println("Une erreur inattendue est survenue: " + e.getMessage());
                return 0;
            }
        }
        return minutes;
    }

    /**
     * Méthode pour saisir le paiement.
     *
     * @param scanner L'objet Scanner pour lire l'entrée de l'utilisateur.
     * @param cout    Le coût du stationnement.
     * @return Le montant payé par l'utilisateur.
     */
    public static double saisirPaiement(Scanner scanner, double cout) {
        double paiement = 0.0;
        while (true) {
            try {
                // Demande à l'utilisateur d'entrer le montant du paiement
                System.out.println("Veuillez entrer le montant que vous payez :");
                paiement = scanner.nextDouble();

                // Vérifier si l'entrée est positive
                if (paiement < 0) {
                    System.out.println("Veuillez entrer un montant positif.");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                // Gestion des erreurs d'entrée
                System.out.println("Entrée invalide. Veuillez entrer un nombre.");
                scanner.next();
            } catch (Exception e) {
                // Gestion d'erreurs inattendues
                System.out.println("Une erreur inattendue est survenue: " + e.getMessage());
                return 0;
            }
        }
        return paiement;
    }

    /**
     * Méthode pour calculer le coût du stationnement.
     *
     * @param minutes        Le nombre de minutes de stationnement.
     * @param tarifParMinute Le tarif par minute.
     * @return Le coût total du stationnement.
     */
    public static double calculerCout(int minutes, double tarifParMinute) {
        try {
            return tarifParMinute * minutes;
        } catch (Exception e) {
            // Gestion d'erreurs inattendues lors du calcul
            System.out.println("Une erreur est survenue lors du calcul du coût: " + e.getMessage());
            return 0;
        }
    }

    /**
     * Le point d'entrée du programme.
     *
     * @param args Arguments en ligne de commande (non utilisés dans ce cas).
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double tarifParMinute = 0.05; // Coût par minute

        // Saisir le nombre de minutes et calculer le coût
        int minutes = saisirMinutes(scanner);
        if (minutes == 0) {
            System.out.println("Opération annulée en raison d'une erreur.");
            return;
        }

        // Calculer et afficher le coût
        double cout = calculerCout(minutes, tarifParMinute);
        System.out.printf("Le coût pour %d minutes est de %.2f euros.\n", minutes, cout);

        // Saisir le montant du paiement
        double paiement = saisirPaiement(scanner, cout);
        if (paiement == 0) {
            System.out.println("Opération annulée en raison d'une erreur.");
            return;
        }

        // Vérifier si le paiement est suffisant
        if (paiement >= cout) {
            double reste = paiement - cout;
            System.out.printf("Paiement accepté. Votre reste est de %.2f euros.\n", reste);
            System.out.println("Vous pouvez maintenant stationner.");
        } else {
            System.out.println("Paiement insuffisant. Opération annulée.");
        }

        scanner.close(); // Fermer le scanner
    }
}
