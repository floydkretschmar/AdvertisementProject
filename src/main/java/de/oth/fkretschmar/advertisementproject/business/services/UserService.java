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

import de.oth.fkretschmar.advertisementproject.business.repositories.AccountRepository;
import de.oth.fkretschmar.advertisementproject.business.repositories.UserRepository;
import de.oth.fkretschmar.advertisementproject.business.services.base.IAccountService;
import de.oth.fkretschmar.advertisementproject.business.services.base.ICampaignService;
import de.oth.fkretschmar.advertisementproject.business.services.base.IUserService;
import de.oth.fkretschmar.advertisementproject.entities.billing.Account;
import de.oth.fkretschmar.advertisementproject.entities.user.Address;
import de.oth.fkretschmar.advertisementproject.entities.campaign.Campaign;
import de.oth.fkretschmar.advertisementproject.entities.campaign.CampaignState;
import de.oth.fkretschmar.advertisementproject.entities.user.Password;
import de.oth.fkretschmar.advertisementproject.entities.user.User;
import java.io.Serializable;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

/**
 * The service that offers functionality related to the management of
 * {@link User} instances.
 *
 * @author fkre Floyd Kretschmar
 */
@RequestScoped
public class UserService implements Serializable, IUserService {

    // --------------- Private fields ---------------
    
    /**
     * Stores the repository used to manage {@link Account} entities.
     */
    @Inject
    private AccountRepository accountRepository;

    /**
     * Stores the repository used to manage {@link Account} entities.
     */
    @Inject
    private IAccountService accountService;

    /**
     * Stores the repository used to manage {@link Campaign} entities.
     */
    @Inject
    private ICampaignService campaignService;

    /**
     * Stores the repository used to manage {@link User} entites.
     */
    @Inject
    private UserRepository userRepository;

    // --------------- Public methods ---------------
    /**
     * Authenticates an user using the specified e-mail and password.
     *
     * @param eMail that identifies the user.
     * @param password that is used to authenticate the user.
     * @return {@code true} if the authentication was a success, otherwise
     * {@code false}.
     * @throws UserServiceException that indicates that the login failed.
     * @throws PasswordException that indicates an error during the processing
     * of passwords.
     */
    @Override
    public User authenticateUser(
            String eMail, char[] password) throws UserServiceException {
        User user = this.userRepository.find(eMail);

        if (user == null || !PasswordService.equals(user.getPassword(), password)) {
            throw new UserServiceException("The user was not found.");
        }

        return user;
    }

    /**
     * Changes the {@link Password} of the specified {@link User}.
     *
     * @param user whose password will be changed.
     * @param newPassword that represents the new password in its unsafe form.
     * @return the User whose password was changed.
     * @throws PasswordException that indicates an error during the processing
     * of passwords.
     */
    @Transactional(Transactional.TxType.REQUIRED)
    @Override
    public User changePassword(
            User user, char[] newPassword) {
        if (user == null) {
            throw new IllegalArgumentException("The password change failed: "
                    + "the user was not set.");
        }

        Password newSafePassword = PasswordService.generate(newPassword);

        user = this.userRepository.merge(user);
        user.setPassword(newSafePassword);

        return user;
    }

    /**
     * Applies all changes of the specified changed user to the the
     * {@link User}.
     *
     * @param user that will be changed.
     * @param changedUser that contains all the changes that will be made to the
     * user.
     * @return the changed user.
     */
    @Transactional(Transactional.TxType.REQUIRED)
    @Override
    public User changeUserBasicInformation(User user, User changedUser) {
        if (user == null) {
            throw new IllegalArgumentException("The password change failed: "
                    + "the user was not set.");
        }

        user = this.userRepository.merge(user);

        user.setAddress(changedUser.getAddress());
        user.setBirthdate(changedUser.getBirthdate());
        user.setCompany(changedUser.getCompany());
        user.setFirstName(changedUser.getFirstName());
        user.setLastName(changedUser.getLastName());

        
        final User tmpOldUser = user;
        // get all the accounts that are not contained in the list of the old 
        // user and create them
        for(Account account : changedUser.getAccounts().stream()
                .filter(account -> 
                {
                    return !tmpOldUser.getAccounts().contains(account);
                })
                .collect(Collectors.toList())) {
            user = this.accountService.createAccountForUser(user, account);
        }
        
        final User tmpNewUser = changedUser;
        // get all the accounts that are not contained in the list of the new 
        // user and delete them
        for(Account account : user.getAccounts().stream()
                .filter(account -> 
                {
                    return !tmpNewUser.getAccounts().contains(account);
                })
                .collect(Collectors.toList())) {
            user = this.accountService.deleteAccountFromUser(user, account);
        }

        return user;
    }

    /**
     * Creates a new {@link User} using the data specified on the user object.
     *
     * @param user that contains the data for the new user that will be
     * created.§
     * @throws UserServiceException that indicates that the email address has
     * already been picked.
     */
    @Transactional
    @Override
    public void createUser(User user) throws UserServiceException {
        // the creation of accounts, campaigns and contents need a logged in 
        // user -> therefore: the user cannot have accounts, campaigns or 
        // contents during creation.

        if (this.userRepository.find(user.geteMailAddress()) != null) {
            throw new UserServiceException("An user already exists for the"
                    + " specified e-mail address.");
        }

        this.userRepository.persist(user);
    }

    /**
     * Removes the {@link User} and all its subsequent data.
     *
     * @param user that will be deleted.
     */
    @Transactional
    @Override
    public void deleteUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("The password change failed: "
                    + "the user was not set.");
        }

        // remove all contents
        Object[] accounts = user.getAccounts().toArray();

        for (int i = 0; i < accounts.length; i++) {
            if (accounts[i] instanceof Account) {
                user.removeAccount((Account) accounts[i]);
                this.accountRepository.remove((Account) accounts[i]);
            }
        }

        // cancel all campaigns
        Object[] campaigns = user.getCampaigns().toArray();

        for (int i = 0; i < campaigns.length; i++) {
            if (campaigns[i] instanceof Campaign
                    && ((Campaign) campaigns[i]).getCampaignState() == CampaignState.RUNNING) {
                user.removeCampaign((Campaign) campaigns[i]);
                this.campaignService.cancelCampaign((Campaign) campaigns[i]);
            }
        }

        this.userRepository.remove(user);
    }

    /**
     * Finds an {@link User} using the unique e-mail address.
     *
     * @param idAsString the id that defines the entity in text form.
     * @return the user with the specified e-mail address.
     */
    @Override
    public User find(String idAsString) {
        return this.userRepository.find(idAsString);
    }
}
