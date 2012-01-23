/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.functor.generator;

import org.apache.commons.functor.UnaryPredicate;
import org.apache.commons.functor.UnaryProcedure;
import org.apache.commons.lang3.Validate;

/**
 * Wrap another {@link Generator} such that {@link #run(UnaryProcedure)} terminates once
 * a condition has been satisfied.
 *
 * @param <E> the type of elements held in this generator.
 * @version $Revision$ $Date$
 */
public class UntilGenerate<E> extends BaseGenerator<E> {

    /**
     * The condition has to verified in order to execute the generation.
     */
    private final UnaryPredicate<? super E> test;

    /**
     * Create a new UntilGenerate.
     * @param wrapped {@link Generator}
     * @param test {@link UnaryPredicate}
     */
    public UntilGenerate(UnaryPredicate<? super E> test, Generator<? extends E> wrapped) {
        super(Validate.notNull(wrapped, "Generator argument was null"));
        this.test = Validate.notNull(test, "UnaryPredicate argument was null");
    }

    /**
     * {@inheritDoc}
     */
    public void run(final UnaryProcedure<? super E> proc) {
        getWrappedGenerator().run(new UnaryProcedure<E>() {
            public void run(E obj) {
                if (test.test(obj)) {
                    getWrappedGenerator().stop();
                } else {
                    proc.run(obj);
                }
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    protected Generator<? extends E> getWrappedGenerator() {
        return (Generator<? extends E>) super.getWrappedGenerator();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof UntilGenerate<?>)) {
            return false;
        }
        UntilGenerate<?> other = (UntilGenerate<?>) obj;
        return other.getWrappedGenerator().equals(getWrappedGenerator()) && other.test.equals(test);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result = "UntilGenerate".hashCode();
        result <<= 2;
        Generator<?> gen = getWrappedGenerator();
        result ^= gen == null ? 0 : gen.hashCode();
        result <<= 2;
        result ^= test == null ? 0 : test.hashCode();
        return result;
    }
}
