
import com.google.gson.Gson;
import org.redrock.bean.User;
import org.redrock.util.DBCPHelper;
import org.redrock.util.ResultSetUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class test {
    public static void main(String[] args) throws SQLException {
        System.out.println(new Date());
        SimpleDateFormat da=new SimpleDateFormat("HH:mm:ss");
        System.out.println(da.format(new Date()));
    }
}
