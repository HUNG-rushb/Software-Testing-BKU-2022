package connection;

import java.sql.Statement;

public class GetStatement {
    private static GetStatement instance;
    private GetStatement(){}
    public static GetStatement getInstance(){
        if (instance==null){
            instance = new GetStatement();
        }
        return instance;
    }

    public Statement getStatement(){
        try {
            return GetConnection.getConnection().createStatement();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
