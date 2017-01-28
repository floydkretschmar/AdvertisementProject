/*
 * Copyright (C) 2017 fkre
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.oth.fkretschmar.advertisementproject.ui.models;

import java.io.Serializable;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import lombok.Getter;

/**
 *
 * @author fkre
 */
@Named
@ViewScoped
public class WizardModel implements Serializable {

    // --------------- Private fields ---------------
    /**
     * Stores the counter that indicates on which step of the creation wizard
     * the user is currently on.
     */
    @Getter
    private int stepCounter = 0;

    // --------------- Public methods ---------------
    /**
     * Gets the value indicating whether or not the current page is the
     * specified page of the wizard.\
     *
     * @param page the page that will be tested against.
     * @return
     */
    public boolean isOnPage(int page) {
        return this.stepCounter == page;
    }

    // --------------- Public methods ---------------
    /**
     * Decrements the step count by one.
     *
     * @param event the event data.
     */
    public void decrementStepCount(AjaxBehaviorEvent event) {
        this.stepCounter--;
    }

    /**
     * Increments the step counter by one.
     *
     * @param event the event data.
     */
    public void incrementStepCount(AjaxBehaviorEvent event) {
        this.stepCounter++;
    }

    /**
     * Resets the wizard.
     */
    public void resetWizard() {
        this.stepCounter = 0;
    }
}
