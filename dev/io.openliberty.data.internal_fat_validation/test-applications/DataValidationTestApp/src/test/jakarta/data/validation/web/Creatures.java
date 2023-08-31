/*******************************************************************************
 * Copyright (c) 2023 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package test.jakarta.data.validation.web;

import jakarta.data.repository.CrudRepository;
import jakarta.data.repository.Repository;
import jakarta.validation.Valid;
import jakarta.validation.executable.ValidateOnExecution;

/**
 * Repository for a Jakarta Persistence entity with bean validation annotations.
 */
@Repository(dataStore = "java:module/jdbc/DerbyDataSource")
@ValidateOnExecution
public interface Creatures extends CrudRepository<Creature, Long> {

    Iterable<Creature> saveAllValid(@Valid Iterable<Creature> entities);

    Creature saveValid(@Valid Creature entity);

    boolean updateByIdSetWeight(long id, float newWeight);
}