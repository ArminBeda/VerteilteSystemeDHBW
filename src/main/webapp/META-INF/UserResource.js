"use strict";

/**
 * Von der Klasse UserResource des Servers abgeleitete Klasse, die im Prinzip
 * dieselben Methoden besitzt. Hier rufen wir jedoch den REST-Webservice des
 * Servers auf, anstelle direkt auf eine Datenbank zuzugreifen.
 * @author yusefoenkol
 */
class UserResource {

    /**
     * Konstruktor.
     * @param {String} url Basis-URL des REST-Webservices (optional)
     */
    constructor(url) {
        this.url = url || "https://localhost:8443/myTravelator/api/user/";
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

    async findUser(username_query) {

        let url = this.url;
        url = url + username_query;
        
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

    async getUserList() {
        let response = await fetch(this.url, {
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
