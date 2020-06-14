package pers.henglin.test;

import pers.henglin.test.bean.Input;
import pers.henglin.test.bean.Output;

/**
 * Created by linheng on 26/04/2020.
 */
public class Calculator {

    public int calc(int param1, int param2) {
        Input input = new Input(param1, param2);
        Output output = new Output();

        addition(input, output);

        return output.getResult();
    }

    private void addition(Input input, Output output) {
        output.setResult(input.getParam1() + input.getParam2());
    }
}
