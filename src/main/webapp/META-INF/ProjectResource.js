"use strict";
/**
Klasse, die von der Ressourcenklasse der Abteilung 
des Servers abgeleitet ist, die grundsätzlich dieselben 
Methoden verwendet. Hier rufen wir jedoch den REST-Webservice des Servers auf,
 anstatt direkt auf eine Datenbank zuzugreifen.
 * @author BEDAAR
 */
class ProjectResource {

    /**
     * Konstruktor.
     * @param {String} url Basis-URL des REST-Webservices (optional)
     */
    constructor(url) {
        this.url = url || "https://localhost:8443/checkIT/api/Projects/";
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

    async findProject(project_query) {

        let url = this.url;
        url = url + "?query=" + project_query;
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

    async getProjectList() {
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
