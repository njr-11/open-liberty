/*******************************************************************************
 * Copyright (c) 2012 IBM Corporation and others.
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
package com.ibm.ws.sib.processor.impl.indexes;

import com.ibm.websphere.ras.TraceComponent;
import com.ibm.ws.sib.processor.SIMPConstants;
import com.ibm.ws.sib.processor.utils.index.Index;
import com.ibm.ws.sib.processor.utils.index.IndexFilter;
import com.ibm.ws.sib.utils.ras.SibTr;

public class SubscriptionTypeFilter implements IndexFilter
{
  public Boolean LOCAL = null;
  public Boolean DURABLE = null;

  /**
   * Trace for the component
   */
  private static final TraceComponent tc =
    SibTr.register(
      SubscriptionTypeFilter.class,
      SIMPConstants.MP_TRACE_GROUP,
      SIMPConstants.RESOURCE_BUNDLE);

 
  public boolean matches(Index.Type type)
  {
    if(type == null) return false;
    if(type instanceof SubscriptionIndex.SubscriptionType)
    {
      SubscriptionIndex.SubscriptionType subType = (SubscriptionIndex.SubscriptionType) type;
      if((LOCAL == null || LOCAL.booleanValue() == subType.local) &&
         (DURABLE == null || DURABLE.booleanValue() == subType.durable))
      {
        return true;
      }
    }
    return false;
  }
}
