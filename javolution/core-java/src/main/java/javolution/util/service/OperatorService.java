/*
 * Javolution - Java(TM) Solution for Real-Time and Embedded Systems
 * Copyright (C) 2012 - Javolution (http://javolution.org/)
 * All rights reserved.
 * 
 * Permission to use, copy, modify, and distribute this software is
 * freely granted, provided that this notice is preserved.
 */
package javolution.util.service;

import javolution.util.function.Function;

/**
 * <p> An operator upon multiple elements of a collection yielding a result 
 *     of the collection type.</p>
 * 
 * @author  <a href="mailto:jean-marie@dautelle.com">Jean-Marie Dautelle</a>
 * @version 6.0, December 12, 2012
 * @see javolution.util.Operators
 */
public interface OperatorService<T> extends Function<CollectionService<T>, T> {

   
}