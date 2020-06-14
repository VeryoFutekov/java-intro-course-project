
import elements.Company;
import elements.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Demo {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        List<Company> companies = new ArrayList<>(Arrays.asList(new Company("Evel Co", 500, 0.2, -5, 100),
                new Company("Bombs Away", 400, 0.5, -10, 50),
                new Company("Clock Work Orange", 300, 1.5, -15, 35),
                new Company("Maroders unated", 200, 2, -18, 50),
                new Company("Fatcat incorporated", 100, 2.5, -25, 100),
                new Company("Macrosoft", 50, 5, -20, 10)));


        List<Player> players = readAllPlayers(sc);
        Game game = new Game(sc, companies, players);

        game.startNewGame();

    }


    private static List<Player> readAllPlayers(Scanner scanner) {
        List<Player> players = new ArrayList<>();
        System.out.println("Колко човека ще играят играта?");
        int playersCount;
        try {
            playersCount = Integer.parseInt(scanner.next());
            if (playersCount < 2 || playersCount > 4) {
                throw new Exception();
            }
        } catch (Exception e) {
            System.out.println("Невалидно");
            return players;
        }

        for (int i = 1; i <= playersCount; i++) {
            players.add(new Player());
        }

        return players;
    }

}
