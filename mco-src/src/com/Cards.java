package com;

/**
 * The Cards class represents a trading card with attributes like name, rarity, variant, and base value.
 * It includes methods for managing the count of a specific card, as well as calculating its value based on its variant.
 */
public class Cards {
    private String name;
    private String rarity;
    private String variant;
    private final double baseValue;
    private double finalValue;
    private int self_count; // count of the card in the collection


    /**
     * Cards Constructor
     *
     * @param name - the name of the card
     * @param rarity - the rarity of the card (e.g., COMMON, UNCOMMON, RARE, LEGENDARY)
     * @param variant - the variant of the card (e.g., NORMAL, EXTENDED-ART, FULL-ART, ALT-ART)
     * @param baseValue - the base dollar value of the card
     *
     * This constructor initializes the card with the provided details, validates rarity and variant,
     * and calculates its final value based on the variant.
     */
    public Cards(String name, String rarity, String variant, double baseValue) {
        this.name = name;

        // Validate and set rarity
        if (Helper.isValidRarity(rarity)) {
            this.rarity = rarity.toUpperCase();
        } else {
            this.rarity = "COMMON";  // default value is COMMON if invalid rarity is provided
        }

        // Set variant based on rarity
        if (this.rarity.equals("COMMON") || this.rarity.equals("UNCOMMON")) {
            this.variant = "NORMAL";  // Default variant for COMMON and UNCOMMON cards
        } else {
            // Validate variant for RARE and LEGENDARY cards
            if (Helper.isValidVariant(variant)) {
                this.variant = variant.toUpperCase();
            } else {
                this.variant = "NORMAL";  // Default to NORMAL if invalid variant is provided
            }
        }

        // Set the base value and calculate the final value
        this.baseValue = baseValue;
        this.finalValue = calculateValue(baseValue, this.variant);

        // Set initial count
        this.self_count = 1;
    }

    /**
     * calculateValue
     *
     * @param baseValue - the base value of the card
     * @param variant - the variant of the card
     *
     * @return double - returns the final value of the card after applying the multiplier based on variant
     *
     * This method calculates the final value of the card based on its variant.
     */
    public double calculateValue(double baseValue, String variant){
        return switch (variant) {
            case "EXTENDED-ART" -> baseValue * 1.50;
            case "FULL-ART" -> baseValue * 2.0;
            case "ALT-ART" -> baseValue * 3.0;
            default -> baseValue;
        };
    }

    /**
     * increaseCount
     *
     *
     * Increases the count of the card in the collection by 1.
     */
    public void increaseCount(){
        this.self_count++;
    }

    /**
     * decreaseCount
     *
     *
     * Decreases the count of the card in the collection by 1, ensuring the count does not go below zero.
     */
    public void decreaseCount(){
        if (self_count > 0) {
            this.self_count--;
        }
        else
            System.out.println("You cannot decrease the count of a card that is already at 0.");
    }

    /**
     * getName
     *
     * @return String - the name of the card
     *
     * This method returns the name of the card.
     */
    public String getName(){
        return this.name;
    }

    /**
     * getRarity
     *
     * @return String - the rarity of the card
     *
     * This method returns the rarity of the card.
     */
    public String getRarity(){
        return this.rarity;
    }

    /**
     * getVariant
     *
     * @return String - the variant of the card
     *
     * This method returns the variant of the card.
     */
    public String getVariant(){
        return this.variant;
    }

    /**
     * getFinalValue
     *
     * @return double - the final value of the card
     *
     * This method returns the final calculated value of the card.
     */
    public double getFinalValue(){
        return this.finalValue;
    }

    /**
     * getBaseValue
     *
     * @return double - the base value of the card
     *
     * This method returns the base value of the card.
     */
    public double getBaseValue() {
        return baseValue;
    }

    /**
     * getSelfCount
     *
     * @return int - the count of the card in the collection
     *
     * This method returns the current count of the card in the collection.
     */
    public int getSelfCount() {
        return self_count;
    }
}
