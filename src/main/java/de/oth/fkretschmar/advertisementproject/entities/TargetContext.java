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

import de.oth.fkretschmar.advertisementproject.entities.base.AbstractStringKeyedEntity;
import java.io.Serializable;
import java.util.EnumSet;
import java.util.Set;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Defines the context according to which an advertisement should be targeted.
 * 
 * @author fkre
 */
@Embeddable
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@EqualsAndHashCode
@ToString
public class TargetContext implements Serializable {
    
    // --------------- Private fields ---------------
    
    /**
     * Stores the targeted age group.
     */
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(
            name = "T_TARGET_AGE_GROUP", 
            joinColumns = @JoinColumn(name = "ID"))
    @Column(name = "TARGET_AGE_GROUPS", nullable = false)
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private Set<TargetAge> age;
    
    /**
     * Stores the targeted gender group.
     */
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(
            name = "T_TARGET_GENDER_GROUP", 
            joinColumns = @JoinColumn(name = "ID"))
    @Column(name = "TARGET_GENDER_GROUPS", nullable = false)
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private Set<TargetGender> gender;
    
    /**
     * Stores the targeted marital status group.
     */
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(
            name = "T_TARGET_MARTIAL_STATUS_GROUP", 
            joinColumns = @JoinColumn(name = "ID"))
    @Column(name = "TARGET_AGE_GROUPS", nullable = false)
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private Set<TargetMaritalStatus> maritalStatus;
    
    /**
     * Stores the targeted purposes of use.
     */
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(
            name = "T_TARGET_PURPOSE_OF_USE_GROUP", 
            joinColumns = @JoinColumn(name = "ID"))
    @Column(name = "TARGET_PURPOSE_OF_USE_GROUP", nullable = false)
    @Getter(AccessLevel.PUBLIC)
    @Setter(AccessLevel.PUBLIC)
    private Set<TargetPurposeOfUse> purposeOfUse;
    
    
    // --------------- Private fields ---------------
    
    
    /**
     * Creates a new instance of {@link TargetContext} using the specified age
     * groups, genders, marital status and purposes of use.
     * 
     * @param targetAges            the targeted age group.
     * @param targetGenders         the targeted gender group.
     * @param targetMaritalStatus   the targeted marital status group.
     * @param targetPurposeOfUses   the targeted purposes of use.
     */
    private TargetContext(
            EnumSet<TargetAge> targetAges,
            EnumSet<TargetGender> targetGenders, 
            EnumSet<TargetMaritalStatus> targetMaritalStatus, 
            EnumSet<TargetPurposeOfUse> targetPurposeOfUses) {
        this.age = targetAges;
        this.gender = targetGenders;
        this.maritalStatus = targetMaritalStatus;
        this.purposeOfUse = targetPurposeOfUses;
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
            EnumSet<TargetPurposeOfUse> targetPurposeOfUses) {
        if(targetAges.isEmpty())
            throw new BuilderValidationException(
                    TargetContext.class,
                    "At least one targeted age group hast to be chosen");
        
        if(targetGenders.isEmpty())
            throw new BuilderValidationException(
                    TargetContext.class,
                    "At least one targeted gender group hast to be chosen");
        
        if(targetMaritalStatus.isEmpty())
            throw new BuilderValidationException(
                    TargetContext.class,
                    "At least one targeted marital status group hast to be "
                            + "chosen");
        
        if(targetPurposeOfUses.isEmpty())
            throw new BuilderValidationException(
                    TargetContext.class,
                    "At least one targeted purpose of use group hast to be "
                            + "chosen");
        
        return new TargetContext(
                targetAges, 
                targetGenders, 
                targetMaritalStatus, 
                targetPurposeOfUses);
    }
    
}
