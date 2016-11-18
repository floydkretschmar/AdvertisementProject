///*
// * Copyright (C) 2016 fkre
// *
// * This program is free software: you can redistribute it and/or modify
// * it under the terms of the GNU General Public License as published by
// * the Free Software Foundation, either version 3 of the License, or
// * (at your option) any later version.
// *
// * This program is distributed in the hope that it will be useful,
// * but WITHOUT ANY WARRANTY; without even the implied warranty of
// * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// * GNU General Public License for more details.
// *
// * You should have received a copy of the GNU General Public License
// * along with this program.  If not, see <http://www.gnu.org/licenses/>.
// */
//package de.oth.fkretschmar.advertisementproject.entities.interfaces;
//
//import de.oth.fkretschmar.advertisementproject.entities.EntityBuilderValidationException;
//import de.oth.fkretschmar.advertisementproject.entities.IEntity;
//
///**
// * Interface that defines the methods that are being offered by an builder that
// * creates entites.
// *
// * @author  fkre    Floyd Kretschmar
// * @param   <S>     the type of the id identifying the entity that is built by
// *                  this builder.
// * @param   <T>     the type of entity that is built by this builder.
// */
//public interface IEntityBuilder<S, T extends IEntity<S>> {
//    
//    // --------------- Methods ---------------
//    
//    /**
//     * Builds an new entity using the data specified by the other builder
//     * methods.
//     * 
//     * @return  the entity after it has been built.
//     * @throws  EntityBuilderValidationException that occurs when the 
//     *          validation of the values of an entity failed.
//     */
//    public T build() throws EntityBuilderValidationException;
//    
//    /**
//     * Creates the entity using a specific description.
//     * 
//     * @param   description    the text that describes an entity.
//     * @return  the address builder used to build the entity.
//     */
//    public IEntityBuilder<S, T> withDescription(String description);
//}
