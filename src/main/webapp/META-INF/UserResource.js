"use strict";

/**
 * Von der Klasse UserResource des Servers abgeleitete Klasse, die im Prinzip
 * dieselben Methoden besitzt. Hier rufen wir jedoch den REST-Webservice des
 * Servers auf, anstelle direkt auf eine Datenbank zuzugreifen.
 * @author BEDAAR
 */
class UserResource {

    /**
     * Konstruktor.
     * @param {String} url Basis-URL des REST-Webservices (optional)
     */
    constructor(url) {
        this.url = url || "https://localhost:8443/checkIT/api/Users/";
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
    
    async authorize() {
        
        let login = document.getElementById("loginsucces");
        login.style.display = 'none';

        let url = this.url;
        url = url + "userid/" + this.username;
        
        let response = await fetch(url, {
            headers: {
                "accept": "application/json",
                "authorization": "Basic " + btoa(this.username + ":" + this.password)
            }
        });
        var ex = response.status;
        
        if (ex == 500) {
            let loginSucces = document.getElementById("loginsucces");
            loginSucces.style.display = 'none';
            alert(`Logindaten nicht korrekt eingegeben oder unvollständig.`);
            return await false;
        }
        
        if (ex == 403) {
            let loginSucces = document.getElementById("loginsucces"); 
            loginSucces.style.display = 'none';
            alert(`Logindaten nicht korrekt eingegeben oder unvollständig`);
            return await false;
        }
        
        if (ex != 400 && ex != 401 && ex != 403 && ex != 404 && ex != 405 && ex != 409 && ex != 410 && ex != 411 && ex != 412 && ex != 413 && ex != 416 && ex != 429 && ex != 500 && ex != 502 && ex != 503) {
           let loginSucces = document.getElementById("loginsucces"); 
          loginSucces.style.display = 'block';
            return await true;
             
        }
        else {
            let loginSucces = document.getElementById("loginsucces");
            loginSucces.style.display = 'none';
            alert(`Logindaten nicht korrekt eingegeben oder unvollständig`);
            return await false;
        }
       
        //return await response.json();
    }
    
    

    async findUser(username_query) {

        let url = this.url;
        url = url + "?query=" + username_query;
        
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
