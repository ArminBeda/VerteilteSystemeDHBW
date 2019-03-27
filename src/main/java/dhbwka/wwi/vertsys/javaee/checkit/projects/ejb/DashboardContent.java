/*
 * Copyright © 2019 Dennis Schulmeister-Zimolong
 * 
 * E-Mail: dhbw@windows3.de
 * Webseite: https://www.wpvs.de/
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.checkit.projects.ejb;

import dhbwka.wwi.vertsys.javaee.checkit.common.web.WebUtils;
import dhbwka.wwi.vertsys.javaee.checkit.dashboard.ejb.DashboardContentProvider;
import dhbwka.wwi.vertsys.javaee.checkit.dashboard.ejb.DashboardSection;
import dhbwka.wwi.vertsys.javaee.checkit.dashboard.ejb.DashboardTile;
import dhbwka.wwi.vertsys.javaee.checkit.projects.jpa.Abteilung;
import dhbwka.wwi.vertsys.javaee.checkit.projects.jpa.ProjectStatus;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * EJB zur Definition der Dashboard-Kacheln für Aufgaben.
 */
@Stateless(name = "projects")
public class DashboardContent implements DashboardContentProvider {

    @EJB
    private AbteilungBean abteilungBean;

    @EJB
    private ProjectBean projectBean;

    /**
     * Vom Dashboard aufgerufenen Methode, um die anzuzeigenden Rubriken und
     * Kacheln zu ermitteln.
     *
     * @param sections Liste der Dashboard-Rubriken, an die die neuen Rubriken
     * angehängt werden müssen
     */
    @Override
    public void createDashboardContent(List<DashboardSection> sections) {
        // Zunächst einen Abschnitt mit einer Gesamtübersicht aller Aufgaben
        // in allen Abteilungen erzeugen
        DashboardSection section = this.createSection(null);
        sections.add(section);

        // Anschließend je Abteilung einen weiteren Abschnitt erzeugen
        List<Abteilung> abteilungen = this.abteilungBean.findAllSorted();

        for (Abteilung abteilung : abteilungen) {
            section = this.createSection(abteilung);
            sections.add(section);
        }
    }

    /**
     * Hilfsmethode, die für die übergebene Projekt Abteilung eine neue Rubrik
     * mit Kacheln im Dashboard erzeugt. Je Aufgabenstatus wird eine Kachel
     * erzeugt. Zusätzlich eine Kachel für alle Aufgaben innerhalb der
     * jeweiligen Abteilung.
     *
     * Ist die Abteilung null, bedeutet dass, dass eine Rubrik für alle Aufgaben
     * aus allen Abteilungen erzeugt werden soll.
     *
     * @param abteilung Projekt-Abteilung für die Kacheln erzeugt werden sollen
     * @return Neue Dashboard-Rubrik mit den Kacheln
     */
    private DashboardSection createSection(Abteilung abteilung) {
        // Neue Rubrik im Dashboard erzeugen
        DashboardSection section = new DashboardSection();
        String cssClass = "";

        if (abteilung != null) {
            section.setLabel(abteilung.getName());
        } else {
            section.setLabel("Alle Abteilungen");
            cssClass = "overview";
        }

        // Eine Kachel für alle Aufgaben in dieser Rubrik erzeugen
        DashboardTile tile = this.createTile(abteilung, null, "Alle", cssClass + " status-all", "calendar");
        section.getTiles().add(tile);

        // Ja Aufgabenstatus eine weitere Kachel erzeugen
        for (ProjectStatus status : ProjectStatus.values()) {
            String cssClass1 = cssClass + " status-" + status.toString().toLowerCase();
            String icon = "";

            switch (status) {
                case OPEN:
                    icon = "doc-text";
                    break;
                case IN_PROGRESS:
                    icon = "rocket";
                    break;
                case FINISHED:
                    icon = "ok";
                    break;
                case CANCELED:
                    icon = "cancel";
                    break;
                case POSTPONED:
                    icon = "bell-off-empty";
                    break;
            }

            tile = this.createTile(abteilung, status, status.getLabel(), cssClass1, icon);
            section.getTiles().add(tile);
        }

        // Erzeugte Dashboard-Rubrik mit den Kacheln zurückliefern
        return section;
    }

    /**
     * Hilfsmethode zum Erzeugen einer einzelnen Dashboard-Kachel. In dieser
     * Methode werden auch die in der Kachel angezeigte Anzahl sowie der Link,
     * auf den die Kachel zeigt, ermittelt.
     *
     * @param abteilung
     * @param status
     * @param label
     * @param cssClass
     * @param icon
     * @return
     */
    private DashboardTile createTile(Abteilung abteilung, ProjectStatus status, String label, String cssClass, String icon) {
        int amount = projectBean.search(null, abteilung, status).size();
        String href = "/app/projects/list/";

        if (abteilung != null) {
            href = WebUtils.addQueryParameter(href, "search_abteilung", "" +abteilung.getId());
        }

        if (status != null) {
            href = WebUtils.addQueryParameter(href, "search_status", status.toString());
        }

        DashboardTile tile = new DashboardTile();
        tile.setLabel(label);
        tile.setCssClass(cssClass);
        tile.setHref(href);
        tile.setIcon(icon);
        tile.setAmount(amount);
        tile.setShowDecimals(false);
        return tile;
    }

}
