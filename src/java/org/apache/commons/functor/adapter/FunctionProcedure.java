/* 
 * $Header: /home/jerenkrantz/tmp/commons/commons-convert/cvs/home/cvs/jakarta-commons-sandbox//functor/src/java/org/apache/commons/functor/adapter/FunctionProcedure.java,v 1.1 2003/01/27 19:33:39 rwaldhoff Exp $
 * ====================================================================
 * The Apache Software License, Version 1.1
 *
 * Copyright (c) 2003 The Apache Software Foundation.  All rights
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution,
 *    if any, must include the following acknowledgment:
 *       "This product includes software developed by the
 *        Apache Software Foundation (http://www.apache.org/)."
 *    Alternately, this acknowledgment may appear in the software itself,
 *    if and wherever such third-party acknowledgments normally appear.
 *
 * 4. The names "The Jakarta Project", "Commons", and "Apache Software
 *    Foundation" must not be used to endorse or promote products derived 
 *    from this software without prior written permission. For written
 *    permission, please contact apache@apache.org.
 *
 * 5. Products derived from this software may not be called "Apache",
 *    nor may "Apache" appear in their name, without prior written
 *    permission of the Apache Software Foundation.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */
package org.apache.commons.functor.adapter;

import java.io.Serializable;

import org.apache.commons.functor.Function;
import org.apache.commons.functor.Procedure;

/**
 * Adapts a {@link Function Function}
 * to the {@link Procedure Procedure}
 * interface by ignoring the value returned
 * by the function.
 * <p/>
 * Note that although this class implements 
 * {@link Serializable}, a given instance will
 * only be truly <code>Serializable</code> if the
 * underlying function is.  Attempts to serialize
 * an instance whose delegate is not 
 * <code>Serializable</code> will result in an exception.
 * 
 * @version $Revision: 1.1 $ $Date: 2003/01/27 19:33:39 $
 * @author Rodney Waldhoff
 */
public final class FunctionProcedure implements Procedure, Serializable {
    /**
     * Create an {@link Procedure Procedure} wrapping
     * the given {@link Function Function}.
     * @param function the {@link Function Function} to wrap
     */
    public FunctionProcedure(Function function) {
        this.function = function;
    }
 
    /**
     * {@link Function#evaluate Evaluate} my function, 
     * but ignore its returned value.
     */
    public void run() {
        function.evaluate();
    }   

    public boolean equals(Object that) {
        if(that instanceof FunctionProcedure) {
            return equals((FunctionProcedure)that);
        } else {
            return false;
        }
    }
        
    public boolean equals(FunctionProcedure that) {
        return that == this || (null != that && (null == function ? null == that.function : function.equals(that.function)));
    }
    
    public int hashCode() {
        int hash = "FunctionProcedure".hashCode();
        if(null != function) {
            hash ^= function.hashCode();
        }
        return hash;
    }
    
    public String toString() {
        return "FunctionProcedure<" + function + ">";
    }
    /**
     * Adapt the given, possibly-<code>null</code>, 
     * {@link Function Function} to the
     * {@link Procedure Procedure} interface.
     * When the given <code>Function</code> is <code>null</code>,
     * returns <code>null</code>.
     * 
     * @param function the possibly-<code>null</code> 
     *        {@link Function Function} to adapt
     * @return a {@link Procedure Procedure} wrapping the given
     *         {@link Function Function}, or <code>null</code>
     *         if the given <code>Function</code> is <code>null</code>
     */
    public static FunctionProcedure adapt(Function function) {
        return null == function ? null : new FunctionProcedure(function);
    }

    /** The {@link Function Function} I'm wrapping. */
    private Function function = null;
}