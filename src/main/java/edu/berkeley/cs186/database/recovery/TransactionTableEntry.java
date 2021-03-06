package edu.berkeley.cs186.database.recovery;

import edu.berkeley.cs186.database.Transaction;

import java.util.*;

class TransactionTableEntry {
    // Transaction object for the transaction.
    Transaction transaction;
    // lastLSN of transaction, or 0 if no log entries for the transaction exist.
    long lastLSN = 0;
    // Set of page numbers of all pages this transaction has modified in some way.
    Set<Long> touchedPages = new HashSet<>();
    // map of transaction's savepoints
    private Map<String, Long> savepoints = new HashMap<>();

    TransactionTableEntry(Transaction transaction) {
        this.transaction = transaction;
    }

    void addSavepoint(String name) {
        savepoints.put(name, lastLSN);
    }

    long getSavepoint(String name) {
        if (!savepoints.containsKey(name)) {
            throw new NoSuchElementException("transaction " + transaction.getTransNum() + " has no savepoint " +
                                             name);
        }
        return savepoints.get(name);
    }

    void deleteSavepoint(String name) {
        if (!savepoints.containsKey(name)) {
            throw new NoSuchElementException("transaction " + transaction.getTransNum() + " has no savepoint " +
                                             name);
        }
        savepoints.remove(name);
    }

    @Override
    public String toString() {
        return "TransactionTableEntry{" +
               "transaction=" + transaction +
               ", lastLSN=" + lastLSN +
               ", touchedPages=" + touchedPages +
               '}';
    }
}
