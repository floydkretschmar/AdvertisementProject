/*
 * Copyright (C) 2016 fkre
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
package de.oth.fkretschmar.advertisementproject.business.services;

import de.oth.fkretschmar.advertisementproject.business.repositories.AddressRepository;
import de.oth.fkretschmar.advertisementproject.business.repositories.AccountRepository;
import de.oth.fkretschmar.advertisementproject.business.repositories.ContentRepository;
import de.oth.fkretschmar.advertisementproject.business.repositories.UserRepository;
import de.oth.fkretschmar.advertisementproject.entities.Account;
import de.oth.fkretschmar.advertisementproject.entities.Address;
import de.oth.fkretschmar.advertisementproject.entities.Campaign;
import de.oth.fkretschmar.advertisementproject.entities.CampaignState;
import de.oth.fkretschmar.advertisementproject.entities.Content;
import de.oth.fkretschmar.advertisementproject.entities.Password;
import de.oth.fkretschmar.advertisementproject.entities.User;
import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

/**
 * The service that offers functionality related to the management of 
 * {@link User} instances.
 * 
 * @author  fkre    Floyd Kretschmar
 */
@RequestScoped
public class UserService implements Serializable {

    // --------------- Private fields ---------------
    /**
     * Stores the repository used to manage {@link Address} entites.
     */
    @Inject
    private AddressRepository addressRepository;
    
    /**
     * Stores the repository used to manage {@link Account} entities.
     */
    @Inject
    private AccountRepository accountRepository;
    
    /**
     * Stores the repository used to manage {@link Campaign} entities.
     */
    @Inject
    private CampaignService campaignService;
    
    /**
     * Stores the service used to manage {@link Content} entities.
     */
    @Inject
    private ContentService contentService;
    
    /**
     * Stores the service that manages {@link Password} entities.
     */
    @Inject
    private PasswordService passwordService;

    /**
     * Stores the repository used to manage {@link User} entites.
     */
    @Inject
    private UserRepository userRepository;

    // --------------- Public methods ---------------
    
    
    /**
     * Creates a new {@link Account} and links it to the already existing 
     * specified {@link User}.
     * 
     * @param   user        to which a new account will be added.
     * @param   account     that will be added to the user.
     * @return              the changed user.
     */
    @Transactional
    public User addAccountToUser(User user, Account account) {
        if (user == null) {
            throw new IllegalArgumentException("The user was not set.");
        }
        
        this.accountRepository.persist(account);
        user = this.userRepository.merge(user);
        user.addAccount(account);
        return user;
    }
    
    
    /**
     * Creates a new {@link Campaign} and links it to the already existing 
     * specified {@link User}.
     * 
     * @param user      to which the new campaign will be added.
     * @param campaign  that will be added to the user.
     * @return          the changed user.
     */
    @Transactional
    public User addCampaignToUser(User user, Campaign campaign) {
        if (user == null) {
            throw new IllegalArgumentException("The user was not set.");
        }
        
        this.campaignService.create(campaign);
        user = this.userRepository.merge(user);
        user.addCampaign(campaign);
        return user;
    }
    
    
    /**
     * Creates a new {@link Content} and links it to the already existing 
     * specified {@link User}.
     * 
     * @param user      to which the new content will be added.
     * @param content   that will be added to the user.
     * @return          the changed user.
     */
    @Transactional
    public User addContentToUser(User user, Content content) {
        if (user == null) {
            throw new IllegalArgumentException("The user was not set.");
        }
        
        this.contentService.create(content);
        user = this.userRepository.merge(user);
        user.addContent(content);
        return user;
    }
    
    /**
     * Changes the {@link Password} of the specified {@link User}.
     * 
     * @param   user                    whose password will be changed.
     * @param   newPassword             that represents the new password in its
     *                                  unsafe form.
     * @return  the User whose password was changed.
     * @throws  PasswordException       that indicates an error during the 
     *                                  processing of passwords.
     */
    @Transactional(Transactional.TxType.REQUIRED)
    public User changePassword(
            User user, char[] newPassword) throws PasswordException {
        if (user == null) {
            throw new IllegalArgumentException("The password change failed: "
                    + "the user was not set.");
        }
        
        Password newSafePassword = PasswordService.generate(newPassword);
        Password currentPassword = user.getPassword();
        
        user = this.userRepository.merge(user);
        this.passwordService.delete(currentPassword);
        user.setPassword(this.passwordService.create(newSafePassword));
        
        return user;
    }
    
    
    /**
     * Creates a new {@link User} using the data specified on the user object.
     * 
     * @param   user    that contains the data for the new user that will be 
     *                  created.§
     */
    @Transactional
    public void create(User user) {
        // the creation of accounts, campaigns and contents need a logged in 
        // user -> therefore: the user cannot have accounts, campaigns or 
        // contents during creation.
        
        if (user == null) {
            throw new IllegalArgumentException("The password change failed: "
                    + "the user was not set.");
        }
        else if (this.userRepository.find(user.geteMailAddress()) != null) {
            throw new UserServiceException("An user already exists for the"
                    + " specified e-mail address.");
        }
        
        final Address address = user.getAddress();
        this.addressRepository.persist(address);

        final Password password = user.getPassword();
        this.passwordService.create(password);
        this.userRepository.persist(user);
    }
    
    
    /**
     * Removes the {@link User} and all its subsequent data.
     * 
     * @param   user    that will be deleted.
     */
    @Transactional
    public void delete(User user) {
        if (user == null) {
            throw new IllegalArgumentException("The password change failed: "
                    + "the user was not set.");
        }
        
        // remove address
        this.addressRepository.remove(user.getAddress());
        user.setAddress(null);
        
        // remove password
        this.passwordService.delete(user.getPassword());
        user.setPassword(null);
        
        // remove all contents
        Object[] accounts = user.getAccounts().toArray();
        
        for (int i = 0; i < accounts.length; i++) {
            if(accounts[i] instanceof Account){
                user.removeAccount((Account)accounts[i]);
                this.accountRepository.remove((Account)accounts[i]);
            }
        }
        
        // remove all contents
        Object[] contents = user.getContents().toArray();
        
        for (int i = 0; i < contents.length; i++) {
            if(contents[i] instanceof Content){
                user.removeContent((Content)contents[i]);
                this.contentService.delete((Content)contents[i]);
            }
        }
        
        // cancel all campaigns
        Object[] campaigns = user.getCampaigns().toArray();
        
        for (int i = 0; i < campaigns.length; i++) {
            if(campaigns[i] instanceof Campaign 
                    && ((Campaign)campaigns[i]).getCampaignState() == CampaignState.RUNNING){
                user.removeCampaign((Campaign)campaigns[i]);
                this.campaignService.cancel((Campaign)campaigns[i]);
            }
        }
        
        //TODO: remove all campaigns
        
        this.userRepository.remove(user);
    }
    
    
    /**
     * Finds an {@link User} using the unique e-mail address.
     * 
     * @param   eMailAddress    used to identify the user.
     * @return  the user with the specified e-mail address.
     */
    public User findForEMail(String eMailAddress) {
        return this.userRepository.find(eMailAddress);
    }

    
    /**
     * Deletes an {@link Account} from an already existing {@link User}.
     * 
     * @param   user    from which the account will be deleted.
     * @param   account that will be deleted.
     * @return          the changed user.
     */
    @Transactional
    public User removeAccountFromUser(User user, Account account) {
        if (user == null) {
            throw new IllegalArgumentException("The password change failed: "
                    + "the user was not set.");
        }
        
        user = this.userRepository.merge(user);
        user.removeAccount(account);
        this.accountRepository.remove(account);
        return user;
    }
    
    
    /**
     * Deletes an {@link Content} from an already existing {@link User}.
     * 
     * @param   user    from which the content will be deleted.
     * @param   content that will be deleted.
     * @return          the changed user.
     */
    @Transactional
    public User removeContentFromUser(User user, Content content) {
        if (user == null) {
            throw new IllegalArgumentException("The password change failed: "
                    + "the user was not set.");
        }
        
        user = this.userRepository.merge(user);
        user.removeContent(content);
        this.contentService.delete(content);
        return user;
    }
    
    /**
     * Validates that the specified password is valid for the specified 
     * {@link User}.
     * 
     * @param user                  whose password will be checked against.
     * @param validationPassword    that reprents the password that will be 
     *                              checked against the password of the user.
     * @return                      {@code true} if the password is valid for
     *                              the user, otherwise {@code false}.
     * @throws  PasswordException       that indicates an error during the 
     *                                  processing of passwords.
     */
    public boolean validatePassword(
            User user, char[] validationPassword) throws PasswordException {
        Password currentPassword = user.getPassword();
        return PasswordService.equals(currentPassword, validationPassword);
    }
}
