package pers.henglin.test;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.reflect.Whitebox;
import pers.henglin.test.bean.Data;
import pers.henglin.test.exception.DatabaseException;


import javax.servlet.http.HttpServletRequest;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;
/**
 * Created by linheng on 25/04/2020.
 */

@RunWith(MockitoJUnitRunner.class)
public class WebControllerTest {
    private final Gson GSON = new Gson();

    private HttpServletRequest request;
    private WebDao webDao;
    private WebController webController;

    @Before
    public void setup() {
        this.request = mock(HttpServletRequest.class);
        this.webDao = mock(WebDao.class);

        this.webController = new WebController();
        // use powermock to mock private field
        Whitebox.setInternalState(this.webController, WebDao.class, this.webDao);
    }

    @Test
    public void Should_ReturnData_When_GetDataById() throws DatabaseException {
        final int ID = 1;
        final String COL1 = "col1";
        final String COL2 = "col2";

        // given
        Optional<Data> data = Optional.of(new Data(ID, COL1, COL2));

        when(this.request.getParameter("id")).thenReturn("1");
        when(this.webDao.findDataById(anyInt())).thenReturn(data);

        // when
        String response = this.webController.read(this.request);

        // then
        try{
            Data result = this.GSON.fromJson(response, Data.class);

            verify(this.webDao, times(1)).findDataById(anyInt());
            assertThat(result.getId(), is(ID));
            assertThat(result.getCol1(), is(COL1));
            assertThat(result.getCol2(), is(COL2));
        } catch (JsonSyntaxException e){
            fail("parser response data fail.");
        }
    }

    @Test
    public void Should_Fail_When_UnableConnDatabase() throws DatabaseException {
        final String EXPECT_EXCEPTION_MSG = "Unable to connect to database.";

        // given
        DatabaseException exception = new DatabaseException();

        when(this.request.getParameter("id")).thenReturn("1");
        when(this.webDao.findDataById(anyInt())).thenThrow(exception);

        // when
        String response = this.webController.read(this.request);

        // then
        verify(this.webDao, times(1)).findDataById(anyInt());
        assertThat(response, is(EXPECT_EXCEPTION_MSG));
    }

    @Test
    public void Should_Fail_When_NotFindData() throws DatabaseException {
        final String EXPECT_EXCEPTION_MSG = "not find data by id";

        // given
        Optional<Data> data = Optional.empty();

        when(this.request.getParameter("id")).thenReturn("1");
        when(this.webDao.findDataById(anyInt())).thenReturn(data);

        // when
        String response = this.webController.read(this.request);

        // then
        assertThat(response, anything(EXPECT_EXCEPTION_MSG));
    }

    @Test
    public void Should_GetChangeData_When_UpdateDataValue() throws DatabaseException {
        final int ID = 1;
        final String ORI_COL1 = "col1";
        final String ORI_COL2 = "col2";

        final String NEW_COL1 = "new col1";
        final String NEW_COL2 = "new col2";

        // given
        Optional<Data> newData = Optional.of(new Data(ID, NEW_COL1, NEW_COL2));
        ArgumentCaptor<Data> dataCaptor = ArgumentCaptor.forClass(Data.class);

        when(this.request.getParameter("id")).thenReturn(String.valueOf(ID));
        when(this.request.getParameter("col1")).thenReturn(ORI_COL1);
        when(this.request.getParameter("col2")).thenReturn(ORI_COL2);
        when(this.webDao.updateData(any())).thenReturn(newData);

        // when
        String response = this.webController.update(this.request);

        // then
        try{
            Data result = this.GSON.fromJson(response, Data.class);

            verify(this.webDao, times(1)).updateData(dataCaptor.capture());

            Data inputData = dataCaptor.getValue();
            assertThat(ID, is(inputData.getId()));
            assertThat(ORI_COL1, is(inputData.getCol1()));
            assertThat(ORI_COL2, is(inputData.getCol2()));

            assertThat(result.getId(), is(ID));
            assertThat(result.getCol1(), is(NEW_COL1));
            assertThat(result.getCol2(), is(NEW_COL2));
        } catch (JsonSyntaxException e){
            fail("parser response data fail.");
        }
    }
}
