/*
 * Copyright © 2019 Dennis Schulmeister-Zimolong
 * 
 * E-Mail: dhbw@windows3.de
 * Webseite: https://www.wpvs.de/
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.checkit.rest;

/**
 * Antwortstruktur für einfache Services, die nur einen Statuscode und eine
 * Meldung zurückgeben.
 */
public class StatusResponse {

    public String status;
    public String message;
}
