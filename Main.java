package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public final class Main {
    private Main() {
    }
    private static final class GameInputLoader {
        private final String mInputPath;

        private GameInputLoader(final String path) {
            mInputPath = path;
        }

        public GameInput load() {
            List<Integer> assetsIds = new ArrayList<>();
            List<String> playerOrder = new ArrayList<>();

            try {
                BufferedReader inStream = new BufferedReader(new FileReader(mInputPath));
                String assetIdsLine = inStream.readLine().replaceAll("[\\[\\] ']", "");
                String playerOrderLine = inStream.readLine().replaceAll("[\\[\\] ']", "");

                for (String strAssetId : assetIdsLine.split(",")) {
                    assetsIds.add(Integer.parseInt(strAssetId));
                }

                for (String strPlayer : playerOrderLine.split(",")) {
                    playerOrder.add(strPlayer);
                }
                inStream.close();


            } catch (IOException e) {
                e.printStackTrace();
            }
            return new GameInput(assetsIds, playerOrder);
        }
    }

    public static void main(final String[] args) {
        GameInputLoader gameInputLoader = new GameInputLoader(args[0]);
        GameInput gameInput = gameInputLoader.load();

        // TODO Implement the game logic.
        /* Convertim strategiile date in input intr-o lista de jucatori*/
        List<Player> players = new LinkedList<>();
        for (String temp : gameInput.getPlayerNames()) {
            if (temp.equals("basic")) {
                Player aux = new Basic(temp);
                players.add(aux);
            }
            if (temp.equals("greedy")) {
                Player aux = new Greedy(temp);
                players.add(aux);
            }
            if (temp.equals("bribed")) {
                Player aux = new Bribed(temp);
                players.add(aux);
            }
            if (temp.equals("wizard")) {
                Player aux = new Wizard(temp);
                players.add(aux);
            }
        }
        /* Convertim idurile date in input intr-o lista de carti*/
        List<Card> cards = new LinkedList<>();

        for (Integer temp : gameInput.getAssetIds()) {
            Card aux = new Card(temp);
            cards.add(aux);
        }
        /* Pregatirea jocului*/
        int round = 1;
        int roundOfGreedy = 1;
        int index = 0;
        for (Player temp : players) {
            temp.fillHand(cards);
        }
        /* Jocul propriu-zis*/
        while (round <= (2 * players.size())) {
            players.get(index).setSheriff(true);
            for (Player temp : players) {
                temp.fillSack(roundOfGreedy);
                if ((temp.getStrategy().equals("greedy")) && (!temp.getSheriff())) {
                    roundOfGreedy++;
                }
            }
            for (Player temp : players) {
                temp.inspection(players, cards);
            }
            for (Player temp : players) {
                temp.fillHand(cards);
                temp.clearSack();
            }
            round++;
           index = (index + 1) % players.size();
           players.get(index).setSheriff(false);
        }

        /* Adaugare bonusuri*/
        BonusSetter bonus = new BonusSetter(players);
        players = bonus.getPlayers();

        /* Afisare*/
        Player maxScore;
        while (!players.isEmpty()) {
            maxScore = players.get(0);
            for (Player temp : players) {
                if (maxScore.getScor() < temp.getScor()) {
                    maxScore = temp;
                }
            }
            maxScore.print();
            players.remove(maxScore);
        }
    }
}
