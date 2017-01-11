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
 * (https://support.google.com/adsense/answer/6002621?hl=en)
 * 
 * @author Floyd
 */
@RequiredArgsConstructor
public enum ContentFormat {
    
    // --------------- Enum fields ---------------
    
    /**
     * Indicates that the content has the size of a half page (300x600).
     */
    HALF_PAGE(new Rectangle(300, 600)),
    
    /**
     * Indicates that the content has the size of a large rectangle (336x280).
     */
    LARGE_RECTANGLE(new Rectangle(336, 280)),
    
    /**
     * Indicates that the content has the size of a leaderboard (728x90).
     */
    LEADERBOARD(new Rectangle(728, 90)),
    
    /**
     * Indicates that the content has the size of a medium rectangle (300x250).
     */
    MEDIUM_RECTANGLE(new Rectangle(300, 250)),
   
    /**
     * Indicates that the content has the size of a wide skyscraper (160x600).
     */
    WIDE_SKYSCRAPER(new Rectangle(160, 600));
    
    // --------------- Private fields ---------------
    
    
    /**
     * Stores the client area of the specified format.
     */
    @Getter
    private final Rectangle area;
}
