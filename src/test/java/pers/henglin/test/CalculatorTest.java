package pers.henglin.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import pers.henglin.test.bean.Input;
import pers.henglin.test.bean.Output;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.*;


/**
 * Created by linheng on 26/04/2020.
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest(Calculator.class)
public class CalculatorTest {

    private Calculator calculator;

    @Before
    public void setup() {
        this.calculator = new Calculator();
    }

    @Test
    public void Should_GetResult300_When_PowerMockInputSuccess() throws Exception {
        final int PARAM1 = 100;
        final int PARAM2 = 200;

        // given
        Input input = PowerMockito.mock(Input.class);

        PowerMockito.when(input.getParam1()).thenReturn(PARAM1);
        PowerMockito.when(input.getParam2()).thenReturn(PARAM2);

        PowerMockito.whenNew(Input.class).withAnyArguments().thenReturn(input);

        // when
        int result = this.calculator.calc(anyInt(), anyInt());

        // then
        verify(input, times(1)).getParam1();
        verify(input, times(1)).getParam2();
        assertThat(result, is(PARAM1 + PARAM2));
    }

    @Test
    public void Should_GetResult300_When_PowerMockInputDoAnswerSuccess() throws Exception {
        final int PARAM1 = 100;
        final int PARAM2 = 200;

        // given
        Input input = PowerMockito.mock(Input.class);

        PowerMockito.doAnswer(invocation -> PARAM1).when(input).getParam1();
        PowerMockito.doAnswer(invocation -> PARAM2).when(input).getParam2();

        PowerMockito.whenNew(Input.class).withAnyArguments().thenReturn(input);

        // when
        int result = this.calculator.calc(anyInt(), anyInt());

        // then
        verify(input, times(1)).getParam1();
        verify(input, times(1)).getParam2();
        assertThat(result, is(PARAM1 + PARAM2));
    }

    @Test
    public void Should_GetResult1000_When_PowerMockOutputSuccess() throws Exception {
        final int PARAM1 = 100;
        final int PARAM2 = 200;
        final int OUTPUT_RESULT = 1000;

        // given
        Output output = PowerMockito.mock(Output.class);

        PowerMockito.whenNew(Output.class).withNoArguments().thenReturn(output);
        PowerMockito.when(output.getResult()).thenAnswer(invocation -> OUTPUT_RESULT);

        // when
        int result = this.calculator.calc(PARAM1, PARAM2);

        // then
        verify(output, times(1)).setResult(anyInt());
        verify(output, times(1)).getResult();
        assertThat(result, is(OUTPUT_RESULT));
    }
}
