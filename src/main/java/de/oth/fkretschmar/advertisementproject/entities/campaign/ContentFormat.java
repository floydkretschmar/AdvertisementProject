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
package de.oth.fkretschmar.advertisementproject.entities.campaign;

import java.awt.Rectangle;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Defines the different formats that an advertisement can come with.
 * 
 * @author Floyd
 */
@RequiredArgsConstructor
public enum ContentFormat {
    
    // --------------- Enum fields ---------------
    
    /**
     * Indicates that the content has the size of a full banner (468x60).
     */
    FULL_BANNER(new Rectangle(468, 60)),
    
    /**
     * Indicates that the content has the size of a leaderboard (728x90).
     */
    LEADERBOARD(new Rectangle(728, 90)),
    
    /**
     * Indicates that the content has the size of a pop under (720x300).
     */
    POP_UNDER(new Rectangle(720, 300)),
   
    /**
     * Indicates that the content has the size of a skyscraper (120x600).
     */
    SKYSCRAPER(new Rectangle(120, 600)),
    
    /**
     * Indicates that the content has the size of a vertical rectangle (240x400).
     */
    VERTICAL_RECTANGLE(new Rectangle(240, 400));
    
    // --------------- Private fields ---------------
    
    
    /**
     * Stores the client area of the specified format.
     */
    @Getter
    private final Rectangle area;
}
