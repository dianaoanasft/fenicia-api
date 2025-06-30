package com.fiapi;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ExampleUnitTest {

    @Test
    void testAddition() {
        int sum = 1 + 1;
        assertThat(sum).isEqualTo(2);
    }

    @Test
    void testSubtraction() {
        int difference = 1 - 1;
        assertThat(difference).isZero();
    }

    @Test
    void testMultiplication() {
        int product = 2 * 2;
        assertThat(product).isEqualTo(4);
    }

}
