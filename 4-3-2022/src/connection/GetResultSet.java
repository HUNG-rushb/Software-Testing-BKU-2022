package connection;

import java.sql.ResultSet;

public class GetResultSet {
    private static GetResultSet instance;
    private GetResultSet(){}
    public static GetResultSet getInstance(){
        if (instance == null){
            instance = new GetResultSet();
        }
        return instance;
    }

    public ResultSet getResultSet(String query){
        try {
          return   GetStatement.getInstance().getStatement().executeQuery(query);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
