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
import de.oth.fkretschmar.advertisementproject.entities.base.TargetAgeAttributeConverter;
import de.oth.fkretschmar.advertisementproject.entities.base.TargetGenderAttributeConverter;
import de.oth.fkretschmar.advertisementproject.entities.base.TargetMaritalStatusAttributeConverter;
import de.oth.fkretschmar.advertisementproject.entities.base.TargetPurposeOfUseAttributeConverter;
import java.util.EnumSet;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import lombok.AccessLevel;
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
@ToString(callSuper = true)
public class TargetContext extends AbstractAutoGenerateKeyedEntity 
        implements IDeletable<Long> {
    
    // --------------- Private fields ---------------
    
    /**
     * Stores the targeted age group.
     */
    @NotNull
    @Column(name = "AGE", nullable = false, columnDefinition = "BIGINT")
    @Getter
    @Setter
    @Convert(converter = TargetAgeAttributeConverter.class)
    private EnumSet<TargetAge> age;
    
    /**
     * Stores the targeted gender group.
     */
    @NotNull
    @Column(name = "GENDER", nullable = false, columnDefinition = "BIGINT")
    @Getter
    @Setter
    @Convert(converter = TargetGenderAttributeConverter.class)
    private EnumSet<TargetGender> gender;
    
    /**
     * Stores the targeted marital status group.
     */
    @NotNull
    @Column(name = "MARITAL_STATUS", nullable = false, columnDefinition = "BIGINT")
    @Getter
    @Setter
    @Convert(converter = TargetMaritalStatusAttributeConverter.class)
    private EnumSet<TargetMaritalStatus> maritalStatus;
    
    /**
     * Stores the targeted purposes of use.
     */
    @NotNull
    @Column(name = "PURPOSE_OF_USE", nullable = false, columnDefinition = "BIGINT")
    @Getter
    @Setter
    @Convert(converter = TargetPurposeOfUseAttributeConverter.class)
    private EnumSet<TargetPurposeOfUse> purposeOfUse;
    
    
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
