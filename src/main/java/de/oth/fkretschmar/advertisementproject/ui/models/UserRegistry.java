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

import de.oth.fkretschmar.advertisementproject.business.annotation.BillCreated;
import de.oth.fkretschmar.advertisementproject.business.annotation.ContentChanged;
import de.oth.fkretschmar.advertisementproject.business.events.EntityEvent;
import de.oth.fkretschmar.advertisementproject.business.services.PasswordException;
import de.oth.fkretschmar.advertisementproject.entities.billing.Bill;
import de.oth.fkretschmar.advertisementproject.entities.campaign.Campaign;
import de.oth.fkretschmar.advertisementproject.entities.campaign.Content;
import de.oth.fkretschmar.advertisementproject.entities.user.User;
import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

/**
 *
 * @author Floyd
 */
@ApplicationScoped
public class UserRegistry implements Serializable {

    // --------------- Private fields ---------------
    /**
     * Stores all the {@link User} instances that are currently logged into the
     * system.
     */
    private ConcurrentMap<String, User> currentUsers = new ConcurrentHashMap<>();

    // --------------- Public methods ---------------
    /**
     * Adds an user that will be managed by the registry.
     *
     * @param user the new user that will be managed.
     * @throws PasswordException that indicates an error during the processing
     * of passwords.
     */
    public void addUser(User user) {
        // use the thread safe atomic puf ot the concurrent map
        this.currentUsers.putIfAbsent(user.getId(), user);
    }

    /**
     * Gets the value indicating whether or not a user is logged into the
     * application.
     *
     * @param userId the id of the user.
     * @return {@code true} if a user is logged in, otherwise {@code false}
     */
    public boolean containsUser(String userId) {
        return this.currentUsers.containsKey(userId);
    }

    /**
     * Provides thread safe processing of the {@link User} that is currently
     * logged into the system.
     *
     * @param userId the id of the user.
     * @param processCallback The function used to process the current user.
     * @return {@code true} if a user is logged in, otherwise {@code false}
     */
    public boolean processAndChangeUser(String userId, Function<User, User> processCallback) {
        User user = this.currentUsers.getOrDefault(userId, null);
        if (user == null) {
            return false;
        }

        // only lock on the scope of the specified user and not on the entire map
        synchronized (user) {
            this.currentUsers.replace(userId, processCallback.apply(user));
        }
        return true;
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
    public <T> T processCurrentUser(String userId, Function<User, T> processCallback) {
        User user = this.currentUsers.getOrDefault(userId, null);
        if (user == null) {
            return null;
        }

        // only lock on the scope of the specified user and not on the entire map
        synchronized (user) {
            return processCallback.apply(this.currentUsers.get(userId));
        }
    }

    /**
     * Removes an user from the registry.
     *
     * @param userId the id of the user.
     */
    public void removeUser(String userId) {
        // remove of the concurrent map is atomic
        this.currentUsers.remove(userId);
    }

    // --------------- Private methods ---------------
    
    /**
     * Listens to any event that indicates that a bill has been created.
     *
     * @param billCreatedEvent the event being fired when a bill was created.
     */
    public void billCreatedListener(
            @Observes @BillCreated EntityEvent<Bill> billCreatedEvent) {
        Campaign eventCampaign = billCreatedEvent.getEntity().getCampaign();

        this.processAndChangeUser(eventCampaign.getComissioner().getId(), (user) -> {
            Campaign targetCampaign = null;
            for (Campaign campaign : user.getCampaigns()) {
                if (campaign.getId().longValue() == eventCampaign.getId().longValue()) {
                    targetCampaign = campaign;
                    break;
                }
            }

            if (targetCampaign != null) {
                targetCampaign.addBill(billCreatedEvent.getEntity());
            }
            return user;
        });
    }

    /**
     * Listens to any event that indicates that a content has changed and
     * replaces all relevant contents
     *
     * @param contentChangedEvent the event being fired when a content has changed.
     */
    public void contentChangedListener(
            @Observes
            @ContentChanged EntityEvent<Content> contentChangedEvent) {
        Campaign eventCampaign = contentChangedEvent.getEntity().getCampaign();

        this.processAndChangeUser(eventCampaign.getComissioner().getId(), (user) -> {
            Campaign targetCampaign = null;
            for (Campaign campaign : user.getCampaigns()) {
                if (campaign.getId().longValue() == eventCampaign.getId().longValue()) {
                    targetCampaign = campaign;
                    break;
                }
            }

            if (targetCampaign != null) {
                for (Content content : targetCampaign.getContents()) {
                    if (content.getId().equals(contentChangedEvent.getEntity().getId())) {
                        targetCampaign.removeContent(content);
                        targetCampaign.addContent(contentChangedEvent.getEntity());
                        break;
                    }
                }
            }
            return user;
        });
    }
}
