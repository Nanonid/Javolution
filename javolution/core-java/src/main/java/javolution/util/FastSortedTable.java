/*
 * Javolution - Java(TM) Solution for Real-Time and Embedded Systems
 * Copyright (C) 2012 - Javolution (http://javolution.org/)
 * All rights reserved.
 * 
 * Permission to use, copy, modify, and distribute this software is
 * freely granted, provided that this notice is preserved.
 */
package javolution.util;

import static javolution.lang.RealTime.Limit.LOG_N;
import javolution.internal.util.table.sorted.FastSortedTableImpl;
import javolution.internal.util.table.sorted.SharedSortedTableImpl;
import javolution.internal.util.table.sorted.SubSortedTableImpl;
import javolution.internal.util.table.sorted.UnmodifiableSortedTableImpl;
import javolution.lang.RealTime;
import javolution.util.function.Comparators;
import javolution.util.function.EqualityComparator;
import javolution.util.function.FullComparator;
import javolution.util.service.SortedTableService;

/**
 * <p> A high-performance sorted table with {@link RealTime real-time} behavior; 
 *     smooth capacity increase/decrease and minimal memory footprint.
 *     Fast sorted table have significantly faster {@link #contains}, 
 *     {@link #indexOf} and {@link #remove} methods).</p>
 *     
 * <p>This class is comparable to {@link FastSortedSet} in performance, 
 *    but it allows for duplicate and implements the {@link java.util.List}
 *    interface.</p>
 *     
 * @author  <a href="mailto:jean-marie@dautelle.com">Jean-Marie Dautelle</a>
 * @version 6.0, July 21, 2013
 */
public class FastSortedTable<E> extends FastTable<E> {

    private static final long serialVersionUID = 0x600L; // Version.

    /**
      * Creates an empty table sorted using its elements natural order.     
     */
    public FastSortedTable() {
        this(Comparators.STANDARD);
    }

    /**
     * Creates an empty table sorted using the specified element comparator.
     */
    public FastSortedTable(FullComparator<? super E> comparator) {
        super(new FastSortedTableImpl<E>(comparator));
    }

    /**
     * Creates a sorted table backed up by the specified service implementation.
     */
    protected FastSortedTable(SortedTableService<E> service) {
        super(service);
    }

    /***************************************************************************
     * Views.
     */

    @Override
    public FastSortedTable<E> unmodifiable() {
        return new FastSortedTable<E>(new UnmodifiableSortedTableImpl<E>(service()));
    }

    @Override
    public FastSortedTable<E> shared() {
        return new FastSortedTable<E>(new SharedSortedTableImpl<E>(service()));
    }

    @Override
    public FastSortedTable<E> subTable(int fromIndex, int toIndex) {
         return new FastSortedTable<E>(new SubSortedTableImpl<E>(service(), fromIndex, toIndex));
    }

    /***************************************************************************
     * Sorted table operations optimizations.
     */

    @SuppressWarnings("unchecked")
    @RealTime(limit = LOG_N)
    public boolean contains(Object obj) {
        return service().indexOf((E) obj) >= 0;
    }

    @SuppressWarnings("unchecked")
    @RealTime(limit = LOG_N)
    public boolean remove(Object obj) {
        return service().remove((E) obj);
    }

    @SuppressWarnings("unchecked")
    @Override
    @RealTime(limit = LOG_N)
    public int indexOf(final Object obj) {
        return service().indexOf((E) obj);
    }

    /***************************************************************************
     * Misc.
     */

    /** 
     * Adds the specified element only if not already present.
     *  
     * @return the index of the element added or already present.
     */
    public int addIfAbsent(E element) {
        return service().addIfAbsent(element);
    }
    
    /** 
     * Returns the would index of the specified element if it were
     * to be added to this sorted table.
     */
    @RealTime(limit = LOG_N)
    public int slotOf(E element) {
        return service().slotOf(element);
    }

    @Override
    public FastSortedTable<E> addAll(E... elements) {
        return (FastSortedTable<E>) super.addAll(elements);
    }

    @Override
    protected SortedTableService<E> service() {
        return (SortedTableService<E>) super.service();
    }

}