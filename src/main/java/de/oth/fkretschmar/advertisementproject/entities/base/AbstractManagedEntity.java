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
package de.oth.fkretschmar.advertisementproject.entities.base;


import de.oth.fkretschmar.advertisementproject.entities.base.converter.LocalDateTimeAttributeConverter;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.MappedSuperclass;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Represents an entity that saves rudementary management information and allows
 * for simple data processing before and after persistence operations.
 *
 * @author  fkre    Floyd Kretschmar
 */
@MappedSuperclass
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(
        callSuper = true, exclude={"generationDate", "modificationDate"})
@ToString(callSuper = true)
abstract class AbstractManagedEntity<T> extends AbstractEntity<T> {

    // --------------- Private fields ---------------
    
    /**
     * Stores the generation date of the entity.
     */
    @Column(name = "GENDATE",
            insertable = true,
            updatable = false)
    @Getter
    @Convert(converter = LocalDateTimeAttributeConverter.class)
    private LocalDateTime generationDate;

    /**
     * Stores the last modification timestamp of the entity.
     */
    @Column(name = "MODDATE")
    @Getter
    @Convert(converter = LocalDateTimeAttributeConverter.class)
    private LocalDateTime modificationDate;

    // --------------- Protected methods ---------------
    /**
     * Allows for postprocessing of an entity after it is loaded.
     */
    protected void postLoad() {
        // do nothing further in default implementation
    }

    /**
     * Allows for postprocessing of a new after before its creation.
     */
    protected void postPersist() {
        // do nothing further in default implementation
    }

    /**
     * Allows for postprocessing of an entity after it is deleted.
     */
    protected void postRemove() {
        // do nothing further in default implementation
    }

    /**
     * Allows for postprocessing of an entity after it is updated.
     */
    protected void postUpdate() {
        // do nothing further in default implementation
    }

    /**
     * Allows for preprocessing of a new entity before its creation.
     */
    protected void prePersist() {
        // do nothing further in default implementation
    }

    /**
     * Allows for preprocessing of an entity before it is deleted.
     */
    protected void preRemove() {
        // do nothing further in default implementation
    }

    /**
     * Allows for preprocessing of an entity before it is updated.
     */
    protected void preUpdate() {
        // do nothing further in default implementation
    }

    // --------------- Private methods ---------------
    /**
     * Gets called directly after loading an entity.
     */
    @PostLoad
    private void onPostLoad() {
        this.postLoad();
    }

    /**
     * Gets called directly after creating a new entity.
     */
    @PostPersist
    private void onPostPersist() {
        this.postPersist();
    }

    /**
     * Gets called directly after removing an entity.
     */
    @PostRemove
    private void onPostRemove() {
        this.postRemove();
    }

    /**
     * Gets called directly after updating an entity.
     */
    @PostUpdate
    private void onPostUpdate() {
        this.postUpdate();
    }

    /**
     * Gets called directly before creating a new entity.
     */
    @PrePersist
    private void onPrePersist() {
        this.generationDate = LocalDateTime.now();
        this.modificationDate = this.generationDate;

        this.prePersist();
    }

    /**
     * Gets called directly before removing an entity.
     */
    @PreRemove
    private void onPreRemove() {
        this.preRemove();
    }

    /**
     * Gets called directly before updating an entity.
     */
    @PreUpdate
    private void onPreUpdate() {
        this.modificationDate = LocalDateTime.now();
        this.preUpdate();
    }
}
