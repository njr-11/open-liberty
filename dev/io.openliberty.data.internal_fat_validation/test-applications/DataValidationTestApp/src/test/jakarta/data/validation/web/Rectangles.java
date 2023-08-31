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

import java.util.List;

import jakarta.data.repository.Repository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.executable.ValidateOnExecution;

/**
 * Repository for a record with bean validation annotations.
 */
@Repository(dataStore = "java:module/jdbc/DerbyDataSource")
public interface Rectangles {
    @ValidateOnExecution
    List<Rectangle> findByWidth(@Positive int width);

    int findWidthById(String id);

    @ValidateOnExecution
    void save(@Valid Rectangle r);

    @ValidateOnExecution
    void saveAll(@Valid Rectangle... rectangles);
}