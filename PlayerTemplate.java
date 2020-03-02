package main;

public interface PlayerTemplate {
    int APPLE_KING_BONUS = 20;
    int APPLE_QUEEN_BONUS = 10;
    int CHEESE_KING_BONUS = 15;
    int CHEESE_QUEEN_BONUS = 10;
    int BREAD_KING_BONUS = 15;
    int BREAD_QUEEN_BONUS = 10;
    int CHICKEN_KING_BONUS = 10;
    int CHICKEN_QUEEN_BONUS = 5;
    int MAX_CARDS_TO_DRAW = 5;
    int SMALL_BRIBE = 5;
    int BIG_BRIBE = 10;
    int START_GOLD = 50;
    int BIG_BRIBE_MIN_NR_OBJECTS = 3;
    int SUSPECT_NR_CARDS = 4;
    int APPLE_ID = 0;
    int CHEESE_ID = 1;
    int BREAD_ID = 2;
    int CHICKEN_ID = 3;
    void print();
}
