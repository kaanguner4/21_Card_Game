/*
TO DO LIST
---------------------
- 
- HighScore List
_

 */

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
        Scanner scanner = new Scanner(System.in);
        List<String> deck = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Collections.addAll(deck, "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A");
        }
        Collections.shuffle(deck);

        List<String> playerHand = new ArrayList<>();
        List<String> dealerHand = new ArrayList<>();
        List<String> splitHand1 = new ArrayList<>();
        List<String> splitHand2 = new ArrayList<>();


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
            balance += (bet+(bet/2));
            return balance;
        } else if (dealerValue == 21 && playerValue <= 21) {
            System.out.println("Dealer got BLACKJACK! You lost.");
            balance -= bet;
            return balance;
        } else {
            while (true) {
                System.out.print("Press 'C' to request card, 'X' to stay, also 'D' to double, 's' for split: "); // split on process
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
                } else if (action.equals("X")) {
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
                    } else if (playerValue == dealerValue) {
                        System.out.println("Draw!");
                    } else {
                        System.out.println("Sorry, you lost!");
                        balance -= bet;
                    }
                    break;
                }else if (action.equals("S")) {
                    if (playerHand.get(0).equals(playerHand.get(1))) {
                        split(playerHand, dealerHand, splitHand1, splitHand2, deck, dealerValue, balance);
                        break;
                    } else {
                    System.out.println("Your cards are not equal. You cannot split your hand!");
                    }
                } else {
                    System.out.println("Invalid input. Type 'C', 'S', or 'D'!");
                }
            }
            return balance;
        }
    }

    public static int split(List<String>playerHand, List<String>dealerHand, List<String>splitHand1, List<String>splitHand2, List<String>deck, int dealerValue ,int balance ){
        Scanner scanner = new Scanner(System.in);

        int balanca_after_first_split_hand_play = balance;

        splitHand1.add(playerHand.get(0));
        splitHand1.add(deck.remove(deck.size() - 1));
        splitHand2.add(playerHand.get(1));
        splitHand2.add(deck.remove(deck.size() - 1));

        int splitHandValue1 = calculateHandValue(splitHand1);
        int splitHandValue2 = calculateHandValue(splitHand2);

        System.out.println("Player Hands: "+ splitHand1 +" = " + splitHandValue1 +" ---- "+splitHand2+" = "+ splitHandValue2);
        System.out.println("Dealer Hand: [X, " + dealerHand.get(1) + "]");

        // first split hand
        System.out.println("For first split hand:");
        if (splitHandValue1 == 21 && dealerValue < 21 ){
            System.out.println("First split hand won with Blackjack!");
            balance += (bet+(bet/2));
        } else {
            while (true) {
                System.out.print("Press 'C' to request card, 'X' to stay, also 'D' to double: ");
                String action = scanner.next().toUpperCase();

                if (action.equals("C")) {
                    splitHand1.add(deck.remove(deck.size() - 1));
                    splitHandValue1 = calculateHandValue(splitHand1);
                    System.out.println("Player Hand: " + splitHand1 + ", Sum: " + splitHandValue1);

                    if (splitHandValue1 > 21) {
                        System.out.println("The player hand exceeded 21. You lost your first hand!");
                        balance -= bet;
                        break;
                    }
                } else if (action.equals("X")) {
                    while (dealerValue < 17) {
                        dealerHand.add(deck.remove(deck.size() - 1));
                        dealerValue = calculateHandValue(dealerHand);
                    }
                    // System.out.println("Dealer Hand: " + dealerHand + ", Sum: " + dealerValue);

                    if (dealerValue > 21 || splitHandValue1 > dealerValue) {
                        System.out.println("Your first hand: "+ splitHandValue1);
                        balance += bet;
                    } else if (splitHandValue1 == dealerValue) {
                        System.out.println("Your first hand: "+ splitHandValue1);
                    } else {
                        System.out.println("Your first hand: "+ splitHandValue1);
                        balance -= bet;
                    }

                    break;
                } else if (action.equals("D")) {
                    splitHand1.add(deck.remove(deck.size() - 1));
                    splitHandValue1 = calculateHandValue(splitHand1);
                    System.out.println("Player Hand: " + splitHand1 + ", Sum: " + splitHandValue1);

                    if (splitHandValue1 > 21) {
                        System.out.println("The player hand exceeded 21. You lost your first hand!");
                        balance -= bet * 2;
                        break;
                    }


                    while (dealerValue < 17) {
                        dealerHand.add(deck.remove(deck.size() - 1));
                        dealerValue = calculateHandValue(dealerHand);
                    }
                    // System.out.println("Dealer Hand: " + dealerHand + ", Sum: " + dealerValue);

                    if (dealerValue > 21 || splitHandValue1 > dealerValue) {
                        System.out.println("Your first hand: "+ splitHandValue1);
                        balance += bet * 2;
                    } else if (splitHandValue1 == dealerValue) {
                        System.out.println("Your first hand: "+ splitHandValue1);
                    } else {
                        System.out.println("Your first hand: "+ splitHandValue1);
                        balance -= bet * 2;
                    }
                    break;
                } else {
                    System.out.println("Invalid input. Type 'C', 'X', or 'D'!");
                }
            }
        }
        // second split hand
        System.out.println("For second split hand:");
        if (splitHandValue2 == 21 && dealerValue < 21){
            System.out.println("Second split hand won with Blackjack!");
            balance += (bet+(bet/2));
        }else {
            while (true) {
                System.out.print("Press 'C' to request card, 'X' to stay, also 'D' to double: ");
                String action = scanner.next().toUpperCase();

                if (action.equals("C")) {
                    splitHand2.add(deck.remove(deck.size() - 1));
                    splitHandValue2 = calculateHandValue(splitHand2);
                    System.out.println("Player Hand: " + splitHand2 + ", Sum: " + splitHandValue2);

                    if (splitHandValue2 > 21) {
                        System.out.println("The player hand exceeded 21. You lost your second hand too!");
                        balance -= bet;
                        break;
                    }
                } else if (action.equals("X")) {
                    while (dealerValue < 17) {
                        dealerHand.add(deck.remove(deck.size() - 1));
                        dealerValue = calculateHandValue(dealerHand);
                    }
                    System.out.println("Dealer Hand: " + dealerHand + ", Sum: " + dealerValue);

                    if (dealerValue > 21 || splitHandValue2 > dealerValue) {
                        if (balanca_after_first_split_hand_play>balance)
                            System.out.println("Congratulations, you won! With your 2 hand.");
                        else if (balanca_after_first_split_hand_play == balance) {
                            System.out.println("Your first hand draw. Your second hand won the game.");
                        } else if (balanca_after_first_split_hand_play < balance ) {
                            System.out.println("Your first hand lost the game. But you won with your second hand.");
                        }
                        balance += bet;
                    } else if (splitHandValue2 == dealerValue) {
                        if (balanca_after_first_split_hand_play>balance)
                            System.out.println("Your first hand won. Your second hand draw.");
                        else if (balanca_after_first_split_hand_play == balance) {
                            System.out.println("Draw!");
                        } else if (balanca_after_first_split_hand_play < balance) {
                            System.out.println("Your first hand lost. Your second hand draw!");
                        }
                    } else {
                        if (balanca_after_first_split_hand_play > balance )
                            System.out.println("You won first hand and lost your second hand. Did not win anything!");
                        else if (balanca_after_first_split_hand_play == balance) {
                            System.out.println("Your first hand draw. Your second hand lost the game.");
                        } else if (balanca_after_first_split_hand_play < balance ) {
                            System.out.println("Sorry, you lost with your 2 hands.");
                        }
                        balance -= bet;
                    }
                    break;
                } else if (action.equals("D")) {
                    splitHand2.add(deck.remove(deck.size() - 1));
                    splitHandValue2 = calculateHandValue(splitHand2);
                    System.out.println("Player Hand: " + splitHand2 + ", Sum: " + splitHandValue2);

                    if (splitHandValue2 > 21) {
                        if (balanca_after_first_split_hand_play > balance )
                            System.out.println("You won first hand and your second hand exceeded 21. Did not win anything!");
                        else if (balanca_after_first_split_hand_play == balance) {
                            System.out.println("Your first hand draw. Your second hand exceeded 21.");
                        } else if (balanca_after_first_split_hand_play < balance ) {
                            System.out.println("Sorry, you lost with your 2 hands.");
                        }
                        balance -= bet * 2;
                        break;
                    }

                    while (dealerValue < 17) {
                        dealerHand.add(deck.remove(deck.size() - 1));
                        dealerValue = calculateHandValue(dealerHand);
                    }
                    System.out.println("Dealer Hand: " + dealerHand + ", Sum: " + dealerValue);

                    if (dealerValue > 21 || splitHandValue2 > dealerValue) {
                        if (balanca_after_first_split_hand_play>balance)
                            System.out.println("Congratulations, you won! With your 2 hand.");
                        else if (balanca_after_first_split_hand_play == balance) {
                            System.out.println("Your first hand draw. Your second hand won the game.");
                        } else if (balanca_after_first_split_hand_play < balance ) {
                            System.out.println("Your first hand lost the game. But you won with your second hand.");
                        }
                        balance += bet * 2;
                    } else if (splitHandValue2 == dealerValue) {
                        if (balanca_after_first_split_hand_play>balance)
                            System.out.println("Your first hand won. Your second hand draw.");
                        else if (balanca_after_first_split_hand_play == balance) {
                            System.out.println("Draw!");
                        } else if (balanca_after_first_split_hand_play < balance) {
                            System.out.println("Your first hand lost. Your second hand draw!");
                        }
                    } else {
                        if (balanca_after_first_split_hand_play > balance )
                            System.out.println("You won first hand and lost your second hand. Did not win anything!");
                        else if (balanca_after_first_split_hand_play == balance) {
                            System.out.println("Your first hand draw. Your second hand lost the game.");
                        } else if (balanca_after_first_split_hand_play < balance ) {
                            System.out.println("Sorry, you lost with your 2 hands.");
                        }
                        balance -= bet * 2;
                    }
                    break;
                } else {
                    System.out.println("Invalid input. Type 'C', 'X', or 'D'!");
                }
            }
        }

    return balance;
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
                    if (bet > balance){
                        System.out.println("The bet entered cannot be higher than the amount of money you have in your hand.");
                        continue;
                    }
                    else
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
