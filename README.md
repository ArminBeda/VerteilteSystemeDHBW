Anwendung checkIT
=========================

Kurzbeschreibung
----------------

Dies ist ein Beispiel für eine in Java realisierte, serverseitige MVC-Webanwendung.
Die Anwendung setzt dabei ganz klassisch auf der „Jakarta Enterprise Edition”
(ehemals „Java Enterprise Edition“) auf und läuft daher in einem speziell dafür
ausgelegten Applikationsserver. Sämtliche Anwendungslogik wird dabei vom Server
implementiert, so dass für jedes URL-Pattern der Anwendung ein komplett serverseitig
generierte HTML-Seite abgerufen und im Browser dargestellt wird.

Die Webanwendung „checkIT“ ermöglicht dem Benutzer eigene Projekte anzulegen und diese zu verwalten. Durch einer Registrierung wird eine sichere Privatsphäre gewährleistet. Somit kann der Benutzer durch seine Login-Daten jederzeit seinen persönlichen Bereich mit den eigenen Projekten erreichen.

Der Benutzer kann das zu erstellende Projekt einer Abteilung zuweisen. Zudem hat er die Möglichkeit, einen Status und eine Priorität für das Projekt festzulegen. Außerdem wird dem Benutzer durch checkIT die Funktionalität angeboten, neben den bereits vorhandenen Abteilungen, neue anzulegen bzw. bestimmte Abteilungen zu entfernen. Die Abteilungen werden anschließend im Dashboard als Kacheln angezeigt.

Verwendete Technologien
-----------------------

Die App nutzt Maven als Build-Werkzeug und zur Paketverwaltung. Auf diese Weise
werden die für Jakarta EE notwendigen APIs, darüber hinaus aber keine weiteren
Abhängigkeiten, in das Projekt eingebunden. Der Quellcode der Anwendung ist dabei
wie folgt strukturiert:

 * **Servlets** dienen als Controller-Schicht und empfangen sämtliche HTTP-Anfragen.
 * **Enterprise Java Beans** dienen als Model-Schicht und kapseln die fachliche Anwendungslogik.
 * **Persistence Entities** modellieren das Datenmodell und werden für sämtliche Datenbankzugriffe genutzt.
 * **Java Server Pages** sowie verschiedene statische Dateien bilden die View und generieren den
   auf dem Bildschirm angezeigten HTML-Code.

Folgende Entwicklungswerkzeuge kommen dabei zum Einsatz:

 * [NetBeans:](https://netbeans.apache.org/) Integrierte Entwicklungsumgebung für Java und andere Sprachen
 * [Maven:](https://maven.apache.org/) Build-Werkzeug und Verwaltung von Abhängigkeiten
 * [Git:](https://git-scm.com/") Versionsverwaltung zur gemeinsamen Arbeit am Quellcode
 * [TomEE:](https://tomee.apache.org/) Applikationsserver zum lokalen Testen der Anwendung
 * [Derby:](https://db.apache.org/derby/) In Java implementierte SQL-Datenbank zum Testen der Anwendung

Screenshots
-----------

<table style="max-width: 100%;">
    <tr>
        <td>
            <a href="login.png">
                <img src="login.png" style="display: block; width: 100%;" />
            </a>
        </td>
        <td>
            <a href="registrieren.png">
                <img src="registrieren.png" style="display: block; width: 100%;" />
            </a>
        </td>
    </tr>
    <tr>
        <td>
            Login
        </td>
        <td>
            Registrierung
        </td>
    </tr>
</table>

<table style="max-width: 100%;">
    <tr>
        <td>
            <a href="dashboard.png">
                <img src="dashboard.png" style="display: block; width: 100%;" />
            </a>
        </td>
        <td>
            <a href="projectlist.png">
                <img src="projectlist.png" style="display: block; width: 100%;" />
            </a>
        </td>
    </tr>
    <tr>
        <td>
            Dashboard
        </td>
        <td>
            Liste mit Projekten
        </td>
    </tr>
</table>

<table style="max-width: 100%;">
    <tr>
        <td>
            <a href="createProject.png">
                <img src="createProject.png" style="display: block; width: 100%;" />
            </a>
        </td>
        <td>
            <a href="abteilunglist.png">
                <img src="abteilunglist.png" style="display: block; width: 100%;" />
            </a>
        </td>
    </tr>
    <tr>
        <td>
            Projekt erzeugen
        </td>
        <td>
            Abteilungslist
        </td>
    </tr>
</table>

<table style="max-width: 100%;">
    <tr>
        <td>
            <a href="settings.png">
                <img src="settings.png" style="display: block; width: 100%;" />
            </a>
        </td>
        <td>
            <a href="authorization.png">
                <img src="authorization.png" style="display: block; width: 100%;" />
            </a>
        </td>
    </tr>
    <tr>
        <td>
            Einstellungen
        </td>
        <td>
            RestClient
        </td>
    </tr>
</table>

Weitere Screenshots sind in der Testdokumentation zu finden. 

Copyright
---------

Dieses Projekt ist lizenziert unter
[_Creative Commons Namensnennung 4.0 International_](http://creativecommons.org/licenses/by/4.0/)

© 2018 – 2019 Armin Beda <br/>
© 2018 – 2019 Gamze-Nur Topkaç <br/>
© 2018 – 2019 Aisha Choudhery <br/>


E-Mail: [bedaarmin@gmail.com](mailto:bedaarmin@gmail.com) <br/>
E-Mail: [gamzetopkac@hotmail.de](mailto:gamzetopkac@hotmail.de) <br/>
E-Mail: [aisha.ch@hotmail.de](mailto:aisha.ch@hotmail.de) <br/>

