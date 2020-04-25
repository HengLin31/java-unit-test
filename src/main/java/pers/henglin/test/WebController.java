package pers.henglin.test;

import com.google.gson.Gson;
import pers.henglin.test.bean.Data;
import pers.henglin.test.exception.DatabaseException;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * Created by linheng on 25/04/2020.
 */
public class WebController {
    private final Gson GSON = new Gson();
    private WebDao webDao;

    public String read(HttpServletRequest request){
        int id = Integer.parseInt(request.getParameter("id"));
        Optional<Data> data;
        try {
            data = webDao.findDataById(id);
        } catch (DatabaseException e) {
            return e.getMessage();
        }
        if(data.isEmpty()){
            return "not find data by id: " + id;
        }
        return GSON.toJson(data.get());
    }
}
