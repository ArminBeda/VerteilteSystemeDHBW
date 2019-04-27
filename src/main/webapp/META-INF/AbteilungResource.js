"use strict";

/**
 * Von der Klasse DepResource des Servers abgeleitete Klasse, 
 * die grundsätzlich die gleichen Methoden besitzt. Hier rufen wir 
 * jedoch den REST-Webservice von Servern auf, 
 * anstatt direkt auf eine Datenbank zuzugreifen.
 * @author BEDAAR
 */
class AbteilungResource {

    /**
     * Konstruktor.
     * @param {String} url Basis-URL des REST-Webservices (optional)
     */
    constructor(url) {
        this.url = url || "https://localhost:8443/checkIT/api/Abteilungen/";
        this.username = "";
        this.password = "";
    }

    /**
     * Benutzername und Passwort für die Authentifizierung merken.
     * @param {String} username Benutzername
     * @param {String} password Passwort
     */
    setAuthData(username, password) {
        this.username = username;
        this.password = password;
    }

    async findDep(dep_query) {

        let url = this.url;
        url = url + "?query=" + dep_query;

        let response = await fetch(url, {
            headers: {
                "accept": "application/json",
                "authorization": "Basic " + btoa(this.username + ":" + this.password)
            }
        });
        var ex = response.status;
        
        if (ex == 500) {
            alert(`HTTP Status 500 – Internal Server Error. Exception Report: Login failed.`);
            location.reload(true);
            return;
        } 

        return await response.json();
    }
    
    async getDepList() {
        let response = await fetch(this.url,{
            headers: {
                "accept": "application/json",
                "authorization": "Basic " + btoa(this.username + ":" + this.password)
            }
        });
        var ex = response.status;
        
        if (ex == 500) {
            alert(`HTTP Status 500 – Internal Server Error. Exception Report: Login failed.`);
            location.reload(true);
            return;
        } 

        return await response.json();
    }
}
