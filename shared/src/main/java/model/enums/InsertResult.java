package model.enums;

/**
 * Represents the possible outcomes of an insertion operation.
 * This enum is typically used to indicate the result of
 * adding a new user or record in a system.
 *
 * SUCCESS - The insertion operation was successful.
 * USERNAME_ALREADY_EXISTS - The username provided already exists.
 * EMAIL_ALREADY_EXISTS - The email address provided already exists.
 * INVALID_EMAIL - The email address provided is not valid.
 * INVALID_USERNAME - The username provided is not valid.
 * ERROR - A generic error occurred during the insertion operation.
 */
public enum InsertResult {
        SUCCESS,
        USERNAME_ALREADY_EXISTS,
        EMAIL_ALREADY_EXISTS,
        INVALID_EMAIL,
        INVALID_USERNAME,
        ERROR
    }

