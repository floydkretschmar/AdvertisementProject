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
package de.oth.fkretschmar.advertisementproject.entities;

import de.oth.fkretschmar.advertisementproject.entities.base.AbstractAutoGenerateKeyedEntity;
import de.oth.fkretschmar.advertisementproject.entities.base.IDeletable;
import java.util.EnumSet;
import java.util.Set;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Defines the context according to which an advertisement should be targeted.
 * 
 * @author fkre
 */
@Entity(name = "T_TARGET_CONTEXT")
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString(callSuper = true)
public class TargetContext extends AbstractAutoGenerateKeyedEntity 
        implements IDeletable<Long> {
    
    // --------------- Private fields ---------------
    
    /**
     * Stores the targeted age group.
     */
    @NotNull
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(
            name = "T_TARGET_AGE", 
            joinColumns = @JoinColumn(name = "CONTEXT_ID"))
    @Column(name = "AGE", nullable = false)
    @Setter
    private Set<TargetAge> age;
    
    /**
     * Stores the targeted gender group.
     */
    @NotNull
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(
            name = "T_TARGET_GENDER", 
            joinColumns = @JoinColumn(name = "CONTEXT_ID"))
    @Column(name = "GENDER", nullable = false)
    @Setter
    private Set<TargetGender> gender;
    
    /**
     * Stores the targeted marital status group.
     */
    @NotNull
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(
            name = "T_TARGET_MARITAL_STATUS", 
            joinColumns = @JoinColumn(name = "CONTEXT_ID"))
    @Column(name = "MARITAL_STATUS", nullable = false)
    @Setter
    private Set<TargetMaritalStatus> maritalStatus;
    
    /**
     * Stores the targeted purposes of use.
     */
    @NotNull
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(
            name = "T_TARGET_PURPOSE_OF_USE", 
            joinColumns = @JoinColumn(name = "CONTEXT_ID"))
    @Column(name = "PURPOSE_OF_USE", nullable = false)
    @Setter
    private Set<TargetPurposeOfUse> purposeOfUse;
    
    // --------------- Public getters ---------------

    
    /**
     * Gets the targeted age group.
     * 
     * @return the {@link EnumSet} of target ages.
     */
    public EnumSet<TargetAge> getAge() {
        return (EnumSet<TargetAge>)this.age;
    }

    
    /**
     * Gets the targeted gender group.
     * 
     * @return the {@link EnumSet} of target genders.
     */
    public EnumSet<TargetGender> getGender() {
        return (EnumSet<TargetGender>)this.gender;
    }

    
    /**
     * Gets the targeted marital status group.
     * 
     * @return the {@link EnumSet} of target marital status.
     */
    public EnumSet<TargetMaritalStatus> getMaritalStatus() {
        return (EnumSet<TargetMaritalStatus>)this.maritalStatus;
    }

    
    /**
     * Gets the targeted purposes of use.
     * 
     * @return the {@link EnumSet} of target purposes of use.
     */
    public EnumSet<TargetPurposeOfUse> getPurposeOfUse() {
        return (EnumSet<TargetPurposeOfUse>)this.purposeOfUse;
    }
    
    
    // --------------- Protected methods ---------------

    /**
     * Converts the loaded sets from the database into enum sets.
     */
    @Override
    protected void postLoad() {
        this.age = EnumSet.copyOf(this.age);
        this.gender = EnumSet.copyOf(this.gender);
        this.maritalStatus = EnumSet.copyOf(this.maritalStatus);
        this.purposeOfUse = EnumSet.copyOf(this.purposeOfUse);
    }
    
    // --------------- Public static methods ---------------

    
    /**
     * The method that builds the basis of the auto generated builder:
     * Validates the input and creates the corresponding {@link TargetContext}.
     * 
     * @param targetAges            the targeted age group.
     * @param targetGenders         the targeted gender group.
     * @param targetMaritalStatus   the targeted marital status group.
     * @param targetPurposeOfUses   the targeted purposes of use.
     * @return  the built {@link TargetContext}.
     */
    @Builder(
            builderMethodName = "createTargetContext", 
            builderClassName = "TargetContextBuilder",
            buildMethodName = "build")
    private static TargetContext validateAndCreateTargetContext(
            EnumSet<TargetAge> targetAges,
            EnumSet<TargetGender> targetGenders, 
            EnumSet<TargetMaritalStatus> targetMaritalStatus, 
            EnumSet<TargetPurposeOfUse> targetPurposeOfUses) 
            throws BuilderValidationException {
        if(targetAges == null)
            throw new BuilderValidationException(
                    TargetContext.class,
                    "The set of target ages can not be null.");
        
        if(targetGenders == null)
            throw new BuilderValidationException(
                    TargetContext.class,
                    "The set of target genders can not be null.");
        
        if(targetMaritalStatus == null)
            throw new BuilderValidationException(
                    TargetContext.class,
                    "The set of target marital status can not be null.");
        
        if(targetPurposeOfUses == null)
            throw new BuilderValidationException(
                    TargetContext.class,
                    "The set of target purposes of use can not be null.");
        
        return new TargetContext(
                targetAges, 
                targetGenders, 
                targetMaritalStatus, 
                targetPurposeOfUses);
    }
    
}
