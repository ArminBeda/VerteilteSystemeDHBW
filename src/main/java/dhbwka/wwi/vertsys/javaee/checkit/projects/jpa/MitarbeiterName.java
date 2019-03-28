/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dhbwka.wwi.vertsys.javaee.checkit.projects.jpa;

/**
 *
 * @author Gamze
 */
public enum MitarbeiterName {
      MITARBEITER1, MITARBEITER2, MITARBEITER3, MITARBEITER4, MITARBEITER5,MITARBEITER6,MITARBEITER7,MITARBEITER8,MITARBEITER9,MITARBEITER10,MITARBEITER11;

    /**
     * Bezeichnung ermitteln
     *
     * @return Bezeichnung
     */
    public String getLabel() {
        switch (this) {
            case MITARBEITER1:
                return "-select-";
            case MITARBEITER2:
                return "Alexander Hell";
                 case MITARBEITER3:
                return "Yannick Grübel";
            case MITARBEITER4:
                return "Lena Meyer";
                case MITARBEITER5:
                return "Christine Herrlich";
                 case MITARBEITER6:
                return "Gunter Fröhlich";
            case MITARBEITER7:
                return "Ömer Kabak";
            case MITARBEITER8:
                return "Konstantin Füller";
                 case MITARBEITER9:
                return "Andrei Sanchez";
            case MITARBEITER10:
                return "Ranjid Singh";
            case MITARBEITER11:
                return "Hannah Schneider";     
            
            default:
                return this.toString();
        }
    }


}


