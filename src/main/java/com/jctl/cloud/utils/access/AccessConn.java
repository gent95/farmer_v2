package com.jctl.cloud.utils.access;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jctl.cloud.manager.datection.entity.DatectionDate;
import com.jctl.cloud.manager.datection.service.DatectionDateService;
import com.jctl.cloud.manager.facility.entity.Facility;
import com.jctl.cloud.manager.facility.service.FacilityService;

/**
 * access数据库连接工具类
 * @author gent
 *
 */
@Service
public class AccessConn {
	@Autowired
	FacilityService facilityService;
	@Autowired
	DatectionDateService datectionDateService;

	public void ConnectAccessFile() throws Exception {
		Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		String dataSource = "E:/jcsoft/weathserstation/Data/Access/dat.mdb";
		String dbur = "jdbc:odbc:driver={Microsoft Access Driver (*.mdb)};DBQ=" + dataSource;
		Connection conn = DriverManager.getConnection(dbur);
		Statement stmt = conn.createStatement();
		ResultSet rs = null;
		rs = stmt.executeQuery("select * from dCurrent");
		Set<Facility> facilitys = new HashSet<Facility>();
		Set<DatectionDate> datectionDates = new HashSet<DatectionDate>();
		List<Facility> facList =  facilityService.findList(new Facility());
		List<DatectionDate> datectionList = datectionDateService.findList(new DatectionDate());
		//清空mysql
		if (facList != null && facList.size() > 0 || datectionList != null && datectionList.size() > 0) {
			for (Facility fac : facList) {
				facilityService.delete(fac);
			}
			for (DatectionDate datectionDate : datectionList) {
				datectionDateService.delete(datectionDate);
			}
		}
		
		//复制access数据库到mysql
		while (rs.next()) {
			Facility facility = new Facility();
			facility.setName(rs.getString("idfac"));
			facility.setCreateDate(rs.getDate("time"));
			facilitys.add(facility);
			DatectionDate datectionDate = new DatectionDate();
			datectionDate.setFacId(facility.getName());
			datectionDate.setWindSpeed(rs.getString("e1"));
			datectionDate.setAirTemperature(rs.getString("e2"));
			datectionDate.setHumidity(rs.getString("e3"));
			datectionDate.setRainV(rs.getString("e4"));
			datectionDate.setRadiate(rs.getString("e5"));
			datectionDate.setWindDirection(rs.getString("e6"));
			datectionDate.setEvaporation(rs.getString("e7"));
			datectionDates.add(datectionDate);
		}
		for (Facility facility : facilitys) {
			facilityService.save(facility);
		}
		for (DatectionDate datectionDate : datectionDates) {
			datectionDateService.save(datectionDate);
		}
		rs.close();
		stmt.close();
		conn.close();
	}
}
