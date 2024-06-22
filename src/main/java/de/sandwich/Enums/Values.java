package de.sandwich.Enums;

public enum Values {
    
    MAIN_WINDOW_WIDTH(1200), // Change this to your monitor size
    MAIN_WINDOW_HEIGHT(700), // Change this to your monitor size

    //Player
    PLAYER_START_BANKACCOUNT(30000), // Default 30.000
    PLAYER_FREEJAILCARDS_START(0), // Defaul 0
    PLAYER_ROUNDS_IN_JAIL(4), // Default 4
    PLAYER_START_POSTION(0), // Default 0

    STREET_HOUSES_START(0),
    HOUSES_NUMBER(32), //Default 32

    MENU_CLOUD_SPEED(100), // In seconds, default 100

    START_FREEPARKING_MONEY(0),

    //Utilitie
    UTILITIE_PRICE(3000),
    UTILITIE_MULTIPLICATOR_ONE(80),
    UTILITIE_MULTIPLICATOR_TWO(200),

    //Station
    STATION_PRICE(400),
    STATION_RENTALONE(500),
    STATION_RENTTWO(1000),
    STATION_RENTTHREE(2000),
    STATION_RENTFOUR(4000);

    private final int value;

    Values(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
