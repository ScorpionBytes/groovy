/*
 * Copyright 2003-2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package groovy.transform.stc

/**
 * Unit tests for static type checking : type checking mode.
 *
 * @author Cedric Champeau
 */
class TypeCheckingModeTest extends StaticTypeCheckingTestCase {
    void testShouldThrowErrorBecauseTypeCheckingIsOn() {
        shouldFailWithMessages '''
            int foo() { 'foo' }
            1
        ''', 'Cannot return value of type java.lang.String on method returning type int'
    }

    void testShouldNotThrowErrorBecauseTypeCheckingIsOff() {
        assertScript '''
            @groovy.transform.TypeChecked(groovy.transform.TypeCheckingMode.SKIP)
            int foo() { 'foo' }
            1
        '''
    }

    void testShouldNotThrowErrorBecauseTypeCheckingIsOffUsingImports() {
        assertScript '''
            import groovy.transform.TypeChecked
            import static groovy.transform.TypeCheckingMode.*
            @TypeChecked(SKIP)
            int foo() { 'foo' }
            1
        '''
    }

    void testShouldThrowErrorBecauseTypeCheckingIsOnIntoClass() {
        shouldFailWithMessages '''
            class A {
                int foo() { 'foo' }
            }
            1
        ''', 'Cannot return value of type java.lang.String on method returning type int'
    }

    void testShouldNotThrowErrorBecauseTypeCheckingIsOffIntoClass() {
        assertScript '''
            @groovy.transform.TypeChecked(groovy.transform.TypeCheckingMode.SKIP)
            class A {
                int foo() { 'foo' }
            }
            1
        '''
    }

    void testShouldNotThrowErrorBecauseTypeCheckingIsOff2IntoClass() {
        assertScript '''
            class A {
                @groovy.transform.TypeChecked(groovy.transform.TypeCheckingMode.SKIP)
                int foo() { 'foo' }
            }
            1
        '''
    }

    void testShouldNotThrowErrorBecauseTypeCheckingIsOff3IntoClass() {
        assertScript '''
            @groovy.transform.TypeChecked(groovy.transform.TypeCheckingMode.SKIP)
            class A {
                int foo() { 'foo' }
            }
            try {
                new A().foo()
            } catch (e) {
                // silent
            }
        '''
    }

}
