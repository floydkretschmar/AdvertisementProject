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

import de.oth.fkretschmar.advertisementproject.business.annotation.ContentChanged;
import de.oth.fkretschmar.advertisementproject.business.events.EntityEvent;
import de.oth.fkretschmar.advertisementproject.business.services.PasswordException;
import de.oth.fkretschmar.advertisementproject.business.services.UserServiceException;
import de.oth.fkretschmar.advertisementproject.business.services.base.IUserService;
import de.oth.fkretschmar.advertisementproject.entities.campaign.Campaign;
import de.oth.fkretschmar.advertisementproject.entities.campaign.Content;
import de.oth.fkretschmar.advertisementproject.entities.user.User;
import de.oth.fkretschmar.advertisementproject.ui.models.base.AbstractModel;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Floyd
 */
@Named
@SessionScoped
public class ApplicationModel extends AbstractModel {

    // --------------- Private static fields ---------------

    /**
     * Stores the instance of {@link Lock} used to sychronize the class.
     */
    private static final Lock LOCK = new ReentrantLock();

    // --------------- Private fields ---------------
    
    /**
     * Stores the {@link User} that is currently logged into the system.
     */
    private User currentUser;
    
    /**
     * Stores the repository used to manage {@link User} entities.
     */
    @Inject
    private IUserService userService;
    
    // --------------- Public methods ---------------
    
    
    /**
     * Authenticates an user using the specified e-mail and password.
     *
     * @param   eMail       that identifies the user.
     * @param   password    that is used to authenticate the user.
     * @return  {@code true} if the authentification was successfull, otherwise
     *          {@code false}.
     * @throws PasswordException that indicates an error during the processing
     * of passwords.
     */
    public boolean authenticateUser(String eMail, String password) {
        ApplicationModel.LOCK.lock();
        
        try {
            User user = this.userService.authenticateUser(eMail, password.toCharArray());
            this.currentUser = user;
            return true;
        }
        catch (UserServiceException ex) {
            return false;
        } finally {
            ApplicationModel.LOCK.unlock();
        }
    }
    
    
    /**
     * Gets the value indicating whether or not a user is logged into the 
     * application.
     * 
     * @return  {@code true} if a user is logged in, otherwise {@code false}
     */
    public boolean isUserAuthenticated() {
        return this.currentUser != null;
    }
    
    
    /**
     * Logs out the user from the application and redirects to the login page.
     * 
     * @return  the navigation point for the login page.
     */
    public String logOut() {
        ApplicationModel.LOCK.lock();
        
        try {
            // cleanup: invalidate session as well as set the user to null
            FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
            this.currentUser = null;
            return "login";
        } finally {
            ApplicationModel.LOCK.unlock();
        }
    }
    
    /**
     * Provides thread safe processing of the {@link User} that is currently
     * logged into the system.
     *
     * @param processCallback The function used to process the current user.
     */
    public void processAndChangeCurrentUser(Function<User, User> processCallback) {
        ApplicationModel.LOCK.lock();

        try {
            this.currentUser = processCallback.apply(this.currentUser);
        } finally {
            ApplicationModel.LOCK.unlock();
        }
    }
    
    
    /**
     * Provides thread safe processing of the {@link User} that is currently
     * logged into the system.
     *
     * @param <T>   the type of the return value.
     * @param processCallback The function used to process the current user.
     * @return the return value of the processing.
     */
    public <T> T processCurrentUser(Function<User, T> processCallback) {
        ApplicationModel.LOCK.lock();

        try {
            return processCallback.apply(this.currentUser);

        } finally {
            ApplicationModel.LOCK.unlock();
        }
    }
    
    
    /**
     * Gets the value indicating whether or not a user has logged in.
     * 
     * @return  {@code true} if a user is logged in, otherwise {@code false}.
     */
    public String redirectFirstPage() {
        if(this.currentUser != null)
            return "overview";
        
        return null;
    }
    
    // --------------- Private methods ---------------
    
    
    /**
     * Listens to any event that indicates that a content has changed and 
     * replaces all relevant contents 
     * @param contentChangedEvent 
     */
    private void contentChangedListener(
            @Observes @ContentChanged EntityEvent<Content> contentChangedEvent) {
        ApplicationModel.LOCK.lock();

        try {
            Campaign eventCampaign = contentChangedEvent.getEntity().getCampaign();
            
            if(this.currentUser.getId().equals(eventCampaign.getComissioner().getId())) {
                Campaign targetCampaign = null;
                for(Campaign campaign : this.currentUser.getCampaigns()) {
                    if(campaign.getId().longValue() == eventCampaign.getId().longValue()) {
                        targetCampaign = campaign;
                        break;
                    }
                }
                
                if (targetCampaign != null) {
                    for(Content content : targetCampaign.getContents()) {
                        if(content.getId().equals(contentChangedEvent.getEntity().getId())) {
                            targetCampaign.removeContent(content);
                            targetCampaign.addContent(contentChangedEvent.getEntity());
                            break;
                        }
                    }
                }
            }
        } finally {
            ApplicationModel.LOCK.unlock();
        }
    }
}
