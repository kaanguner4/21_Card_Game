import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class blackjack {
    private static int bet;

    public static int calculateHandValue(List<String> hand) {
        int value = 0;
        int numAces = 0;

        for (String card : hand) {
            if (card.equals("A")) {
                numAces++;
                value += 11;
            } else if (card.equals("K") || card.equals("Q") || card.equals("J")) {
                value += 10;
            } else {
                value += Integer.parseInt(card);
            }
        }

        while (value > 21 && numAces > 0) {
            value -= 10;
            numAces--;
        }

        return value;
    }

    public static int playBlackjack(int balance) {
        List<String> deck = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Collections.addAll(deck, "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A");
        }
        Collections.shuffle(deck);

        List<String> playerHand = new ArrayList<>();
        List<String> dealerHand = new ArrayList<>();

        playerHand.add(deck.remove(deck.size() - 1));
        playerHand.add(deck.remove(deck.size() - 1));
        dealerHand.add(deck.remove(deck.size() - 1));
        dealerHand.add(deck.remove(deck.size() - 1));

        System.out.println("WELCOME TO THE BLACKJACK GAME!");
        System.out.println("Player Hand: " + playerHand + ", Sum: " + calculateHandValue(playerHand));
        System.out.println("Dealer Hand: [X, " + dealerHand.get(1) + "]");

        int playerValue = calculateHandValue(playerHand);
        int dealerValue = calculateHandValue(dealerHand);

        if (playerValue == 21 && dealerValue <= 21) {
            System.out.println("BLACKJACK! You won.");
            balance += bet;
            return balance;
        } else if (dealerValue == 21 && playerValue <= 21) {
            System.out.println("Dealer got BLACKJACK! You lost.");
            balance -= bet;
            return balance;
        } else {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.print("Press 'C' to request card, 'S' to stay, also 'D' to double: ");
                String action = scanner.next().toUpperCase();

                if (action.equals("C")) {
                    playerHand.add(deck.remove(deck.size() - 1));
                    playerValue = calculateHandValue(playerHand);
                    System.out.println("Player Hand: " + playerHand + ", Sum: " + playerValue);

                    if (playerValue > 21) {
                        System.out.println("The player hand exceeded 21. You lost!");
                        balance -= bet;
                        break;
                    }
                } else if (action.equals("S")) {
                    while (dealerValue < 17) {
                        dealerHand.add(deck.remove(deck.size() - 1));
                        dealerValue = calculateHandValue(dealerHand);
                    }
                    System.out.println("Dealer Hand: " + dealerHand + ", Sum: " + dealerValue);

                    if (dealerValue > 21 || playerValue > dealerValue) {
                        System.out.println("Congratulations, you won!");
                        balance += bet;
                    } else if (playerValue == dealerValue) {
                        System.out.println("Draw!");
                    } else {
                        System.out.println("Sorry, you lost!");
                        balance -= bet;
                    }

                    break;
                } else if (action.equals("D")) {
                    playerHand.add(deck.remove(deck.size() - 1));
                    playerValue = calculateHandValue(playerHand);
                    System.out.println("Player Hand: " + playerHand + ", Sum: " + playerValue);

                    if (playerValue > 21) {
                        System.out.println("The player hand exceeded 21. You lost!");
                        balance -= bet * 2;
                        break;
                    }

                    bet *= 2;

                    while (dealerValue < 17) {
                        dealerHand.add(deck.remove(deck.size() - 1));
                        dealerValue = calculateHandValue(dealerHand);
                    }
                    System.out.println("Dealer Hand: " + dealerHand + ", Sum: " + dealerValue);

                    if (dealerValue > 21 || playerValue > dealerValue) {
                        System.out.println("Congratulations, you won!");
                        balance += bet;
                    } else if (playerValue > 21) {
                        System.out.println("The player hand exceeded 21. You lost!");
                        balance -= bet;
                    } else if (playerValue == dealerValue) {
                        System.out.println("Draw!");
                    } else {
                        System.out.println("Sorry, you lost!");
                        balance -= bet;
                    }
                    break;
                } else {
                    System.out.println("Invalid input. Type 'C', 'S', or 'D'!");
                }
            }
            return balance;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Username: ");
        String playerName = scanner.next();
        System.out.print("Age: ");
        int age = scanner.nextInt();
        int balance = 50;

        if (age >= 18) {
            while (true) {
                System.out.println("Current balance: $" + balance);
                System.out.print("Do you want to play a game? (Y/N): ");
                String playAgain = scanner.next().toUpperCase();

                if (playAgain.equals("Y")) {
                    System.out.print("Bet: ");
                    bet = scanner.nextInt();
                    balance = playBlackjack(balance);

                    if (balance <= 0) {
                        System.out.println("You're out of money! Game over.");
                        break;
                    }
                } else if (playAgain.equals("N")) {
                    System.out.println("Thanks for playing! Your final balance: " + balance);
                    if (balance <= 100) {
                        System.out.println("Why did you come here?");
                    } else if (balance > 100 && balance <= 200) {
                        System.out.println("You won your taxi payment :D");
                    } else if (balance > 200 && balance <= 400) {
                        System.out.println("You are lucky.");
                    } else if (balance > 400 && balance <= 600) {
                        System.out.println("You should go to VEGASSSS!");
                    } else if (balance > 600) {
                        System.out.println("Okay... I think you are a cheater. I am calling the police! :o");
                        System.out.println("*********************************************************************************");
                        System.out.println("- 9 1 1. What's your emergency?");
                    }
                    break;
                } else {
                    System.out.println("Invalid input. Type 'Y' or 'N'!");
                }
            }
        } else {
            System.out.println("Get older, child!");
        }
    }
}
