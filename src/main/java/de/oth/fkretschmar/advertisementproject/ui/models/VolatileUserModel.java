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
package de.oth.fkretschmar.advertisementproject.ui.models;

import de.oth.fkretschmar.advertisementproject.business.services.base.IUserService;
import de.oth.fkretschmar.advertisementproject.entities.user.User;
import java.io.Serializable;
import java.util.function.Consumer;
import java.util.function.Function;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Floyd
 */
@Named
@ViewScoped
public class VolatileUserModel implements Serializable {

    // --------------- Private fields ---------------
    private User currentUser;

    /**
     * Stores the repository used to manage {@link User} entities.
     */
    @Inject
    private IUserService userService;

    // --------------- Public methods ---------------
    
    /**
     * Provides thread safe processing of the {@link User} that is currently
     * logged into the system.
     *
     * @param userId the id of the user.
     * @param processCallback The function used to process the current user.
     */
    public void processUser(String userId, Consumer<User> processCallback) {
        if (this.currentUser == null) {
            this.currentUser = this.userService.find(userId);
        }

        processCallback.accept(this.currentUser);
    }

    /**
     * Provides thread safe processing of the {@link User} that is currently
     * logged into the system.
     *
     * @param <T> the type of the return value.
     * @param userId the id of the user.
     * @param processCallback The function used to process the current user.
     * @return the return value of the processing or null if the specified user
     * does not exist.
     */
    public <T> T processUserAndReturn(String userId, Function<User, T> processCallback) {
        if (this.currentUser == null) {
            this.currentUser = this.userService.find(userId);
        }
        return processCallback.apply(this.currentUser);
    }
}
