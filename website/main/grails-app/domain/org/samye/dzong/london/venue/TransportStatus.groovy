package org.samye.dzong.london.venue

class TransportStatus {
    String link;
    boolean available;
    static belongsTo = Transport
    static constraints = {
    }
}
