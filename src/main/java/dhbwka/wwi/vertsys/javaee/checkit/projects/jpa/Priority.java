/*
 * Copyright © 2019 Armin Beda
 * 
 * E-Mail: bedaarmin@gmail.com
 * 
 * Copyright © 2019 Gamze-Nur Topkaç
 * 
 * E-Mail: gamzetopkac@hotmail.de
 * 
 * Copyright © 2019 Aisha Choudhery
 * 
 * E-Mail: aisha.ch@hotmail.de
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.checkit.projects.jpa;

/**
 *
 * @author aishach
 */
public enum Priority {
    HIGH_PRIORITY, MIDDLE_PRIORITY, LOW_PRIORITY;
    
    public String getLabel() {
        switch (this) {
            case HIGH_PRIORITY:
                return "Hohe Priorität";
            case MIDDLE_PRIORITY:
                return "Mittlere PrioritäFt";
            case LOW_PRIORITY:
                return "Niedrige Priorität";
            default:
                return this.toString();
        }
   
    }
}
