/*
 * Copyright (C) 2016 Floyd
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
package de.oth.fkretschmar.advertisementproject.ui.models.base;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * The abstract base implementation of an ui model.
 * 
 * @author Floyd
 */
public abstract class AbstractModel implements Serializable {
    
    // --------------- Private fields ---------------
    
    /**
     * Stores the stage of the page flow the application is currently in.
     */
    @Getter
    @Setter
    private NavigationPoint nextNavigationPoint;

    // --------------- Public methods ---------------
    
    /**
     * Initializes the model data for display operations.
     */
    public final void initialize() {
        this.nextNavigationPoint = NavigationPoint.UNDEFINED;
        this.initializeCore();
    }
    
    
    /**
     * Performs the work of selecting the next navigation point in the page 
     * flow.
     * 
     * @return  the text that represents the name of the next navigation point.
     */
    public final String navigate() {
        if (this.nextNavigationPoint == NavigationPoint.UNDEFINED) {
            throw new NavigationException("The next navigation point of the model"
                    + " can not be null after initialization of the model. "
                    + " Please set a valid stage.");
        }
        
        return this.nextNavigationPoint.getValue();
    }

    // --------------- Protected methods ---------------
    
    /**
     * Initializes the model data for display operations.
     */
    protected abstract void initializeCore();
}
