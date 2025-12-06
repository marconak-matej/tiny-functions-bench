package io.github.mm.vowels.checker;

/**
 * Defines the contract for checking whether a string contains vowels.
 * All implementations should follow these rules:
 * <ul>
 *   <li>Input can be null - should return false for null inputs</li>
 *   <li>Only ASCII vowels are considered (a, e, i, o, u, A, E, I, O, U)</li>
 *   <li>Non-ASCII characters (including accented vowels like á, é) are not considered vowels</li>
 *   <li>Empty strings contain no vowels and should return false</li>
 * </ul>
 */
public interface VowelChecker {
    /**
     * Checks if the input string contains any vowels.
     *
     * @param text the input string to check, may be null
     * @return true if the string contains at least one vowel, false if the string is null, empty, or contains no vowels
     */
    boolean hasVowels(String text);
}
