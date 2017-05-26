package com.jctl.cloud.manager.grow.service;

import com.jctl.cloud.manager.datection.entity.Datection;
import com.jctl.cloud.utils.sqlServer.ConnectionUtil;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by gent on 2017/5/25.
 */
@Service
public class WeatherStationService {
   private Connection connection =  ConnectionUtil.getConnection();
   private Statement stmt = null;
    public List getData()throws Exception {
        ResultSet rs = null;
        List<Datection> list = new LinkedList<>();
        try{
            stmt = connection.createStatement();
//            rs = stmt.executeQuery("SELECT top 8 * from dHistory WHERE time LIKE '%:00%' ORDER BY time DESC ");
            rs = stmt.executeQuery("SELECT top 8 * from dHistory WHERE 1=1 ORDER BY time DESC ");
            while (rs.next()) {
                Datection datection = new Datection();
                datection.setWindSpeed(rs.getString("e1"));
                datection.setRainV(rs.getString("e2"));
                datection.setWindDirection(rs.getString("e3"));
                datection.setAirTemperature(rs.getString("e4"));
                datection.setHumidity(rs.getString("e5"));
                datection.setRadiate(rs.getString("e6")); //气压
                datection.setEvaporation(rs.getString("e7")); //光照
                datection.setCreateDate(rs.getString("time").replace(".0",""));
                list.add(datection);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
//            ConnectionUtil.close(rs,stmt,connection);
            return list;
        }
    }

    public void deleteData(){
        try{
            stmt = connection.prepareCall("DELETE from dHistory WHERE time LIKE '%:[01-59]%'");
        }catch (Exception e){

        }finally {
//            ConnectionUtil.close(null,stmt,connection);
        }

    }
}
