/*	165a HOMEWORK 4
 * 	Executes queries on the tables created from insert.cpp
 * 
 * 	AUTHORS:
 * 	Ashton Yon, ID: 997582495
 * 	Giovanni Tenorio, ID: 997410282
 * 
 */

#include <iostream> 
#include <pqxx/pqxx>  
#include <string>
#include <fstream> 
#include <string.h>
#include <stdlib.h>
 
using namespace std; 
using namespace pqxx; 

pqxx::result getans(pqxx::transaction_base &W, string q){
	return W.exec(q);
}

int main (int argc, char* argv[]){
	string connect, q1, q2;
	
	string user_name = getenv("USER");
    connect = "dbname=postgres user="+user_name+" password=1";
	
    connection C(connect); 

	work W(C);
	
	pqxx::result answer;
	
	//PART A
	//Get people for various percentages
	cout << "//===== Executing Part a queries =====//" << endl;
	for(int i = 5; i <= 100; i+=5){
		q1 = "SELECT (pcount/tcount) *100 AS percent "
			"FROM "
				"(SELECT COUNT(*)::float AS pcount "
				"FROM (SELECT HouseId, PersonId, traveldate, SUM(Tripmiles) as TotalTripMiles "
					"FROM NHTS_DAY GROUP BY HouseId, PersonId, traveldate) AS PersonMiles "
					"WHERE TotalTripMiles < " + to_string(i) + ") AS foo, "
				"(SELECT COUNT(*)::float AS TCOUNT "
				"FROM (SELECT HouseId, PersonId, SUM(Tripmiles) as TotalTripMiles FROM NHTS_DAY GROUP BY HouseId, PersonId) AS we) AS foo2;";
				
		answer = getans(W, q1);
		
		cout << "Percent of individuals that traveled less than " << i << " miles: " << answer[0]["percent"].c_str() << endl; 
	}
	
	//PART B
	cout << endl << "//===== Executing Part b queries =====//" << endl;
	for(int i = 5; i <= 100; i+=5){
		q1	= "SELECT AVG(epatmpg) AS epatmpg "
				"FROM (SELECT * "
					"FROM (SELECT HouseId, epatmpg, vehicleid FROM nhts_veh) AS veh "
					"INNER JOIN (SELECT HouseId, Tripmiles, vehicleid AS vd FROM nhts_day) AS day "
					"ON (veh.HouseId = day.HouseId AND veh.vehicleid = day.vd)) AS totaltrips "
				"WHERE totaltrips.tripmiles < "+ to_string(i) +" AND vehicleid > 0;";
				
		answer = getans(W, q1);
		
		cout << "AVG fuel economy (< " << i << " miles): " << answer[0]["epatmpg"].c_str() << endl; 
	} 
	
	//PART C
	cout << endl << "//===== Executing Part c queries =====//" << endl;
	for(int i = 0; i < 14; i++){
	
		q1	= "SELECT eia.yyyymm AS date, (totalCo2HH/value_trans)*100 AS value "
			"FROM "
				"(SELECT traveldate, ((AVG(blah.s/blah.epatmpg)* 30 * 117538000 * 0.008887)/1000000) AS totalCo2HH "
				"FROM "
					"(SELECT  vehicleid, epatmpg, houseid, traveldate, SUM(vehicletravelmiles) AS s " 
					"FROM bigtable "
					"GROUP BY vehicleid, houseid, traveldate, epatmpg) AS blah "
				"WHERE traveldate >= 200803 AND traveldate <= 200904 "
				"GROUP BY traveldate) AS HH, "
				"(SELECT MSN_trans, YYYYMM, value_trans "
				"FROM eia_trans "
				"WHERE MSN_trans = 'MMACEUS' AND yyyymm >= 200803 AND yyyymm <= 200904 AND yyyymm <> 200813 AND yyyymm <> 200913 ) AS EIA "
			"WHERE HH.traveldate = eia.yyyymm; ";
			
		answer = getans(W, q1);
		cout << "Month:  " << answer[i]["date"].c_str() << "	Percent of CO2 from HH vehicles:  " << answer[i]["value"].c_str() << endl;
	}
	
	//PART D
	cout << endl << "//===== Executing Part d queries =====//" << endl;
	
	cout << endl <<  "//===== 20 =====//" << endl;
	q1 = "select traveldate, ((value_trans - combinedco2)/value_trans)*100 as co2difference "
		"FROM "
			"(SELECT TravelDate, ((AVG(foo21.totalelect) * 30 * foo21.CO2Ratio * 117538000)/1000000) as CO2Electric, ((AVG(foo21.left/foo21.epatmpg) * 30 * 117538000 * 0.008887)/1000000) as LeftoverCO2, (((AVG(foo21.totalelect) * 30 * foo21.CO2Ratio * 117538000)/1000000)+((AVG(foo21.left/foo21.epatmpg) * 30 * 117538000 * 0.008887)/1000000)) as combinedco2 "
			"FROM " 
			"(SELECT CO2Ratio, houseid, vehicleid, epatmpg, Traveldate, sum(leftovermile) as left, sum(electmkwh) as totalelect FROM foo20 GROUP BY CO2Ratio, houseid, vehicleid, epatmpg, Traveldate) as foo21 "
			"GROUP BY traveldate, co2ratio "
			"ORDER BY traveldate) as ans20, "
			"(SELECT YYYYMM, MSN_Trans, Value_Trans FROM EIA_Trans " 
			"WHERE YYYYMM >= 200803 AND YYYYMM <= 200904 AND YYYYMM <> 200813 AND YYYYMM <> 200913 AND EIA_Trans.MSN_Trans = 'TEACEUS') as eiat "
		"WHERE eiat.YYYYMM = ans20.traveldate;";
	
	answer = getans(W, q1);
	for(int i = 0; i < 14; i++){
		cout << "Month:  " << answer[i]["traveldate"].c_str() << "	Percent difference:  " << answer[i]["co2difference"].c_str() << endl;
	}
	
	cout << endl <<  "//===== 40 =====//" << endl;
	q1 = "SELECT traveldate, ((value_trans - combinedco2)/value_trans)*100 as co2difference "
		"FROM "
		"(SELECT TravelDate, ((AVG(foo21.totalelect) * 30 * foo21.CO2Ratio * 117538000)/1000000) as CO2Electric, ((AVG(foo21.left/foo21.epatmpg) * 30 * 117538000 * 0.008887)/1000000) as LeftoverCO2, (((AVG(foo21.totalelect) * 30 * foo21.CO2Ratio * 117538000)/1000000)+((AVG(foo21.left/foo21.epatmpg) * 30 * 117538000 * 0.008887)/1000000)) as combinedco2 "
		"FROM " 
		"(SELECT CO2Ratio, houseid, vehicleid, epatmpg, Traveldate, sum(leftovermile) as left, sum(electmkwh) as totalelect FROM foo40 GROUP BY CO2Ratio, houseid, vehicleid, epatmpg, Traveldate) as foo21 "
		"GROUP BY traveldate, co2ratio "
		"ORDER BY traveldate) as ans40, "
		"(SELECT YYYYMM, MSN_Trans, Value_Trans FROM EIA_Trans "
		"WHERE YYYYMM >= 200803 AND YYYYMM <= 200904 AND YYYYMM <> 200813 AND YYYYMM <> 200913 AND EIA_Trans.MSN_Trans = 'TEACEUS') as eiat "
		"WHERE eiat.YYYYMM = ans40.traveldate; ";
	
	answer = getans(W, q1);
	for(int i = 0; i < 14; i++){
		cout << "Month:  " << answer[i]["traveldate"].c_str() << "	Percent difference:  " << answer[i]["co2difference"].c_str() << endl;
	}
	
	cout << endl <<  "//===== 60 =====//" << endl;
	q1 = "SELECT traveldate, ((value_trans - combinedco2)/value_trans)*100 as co2difference "
		"FROM "
		"(SELECT TravelDate, ((AVG(foo21.totalelect) * 30 * foo21.CO2Ratio * 117538000)/1000000) as CO2Electric, ((AVG(foo21.left/foo21.epatmpg) * 30 * 117538000 * 0.008887)/1000000) as LeftoverCO2, (((AVG(foo21.totalelect) * 30 * foo21.CO2Ratio * 117538000)/1000000)+((AVG(foo21.left/foo21.epatmpg) * 30 * 117538000 * 0.008887)/1000000)) as combinedco2 "
		"FROM "
		"(SELECT CO2Ratio, houseid, vehicleid, epatmpg, Traveldate, sum(leftovermile) as left, sum(electmkwh) as totalelect FROM foo60 GROUP BY CO2Ratio, houseid, vehicleid, epatmpg, Traveldate) as foo21 "
		"GROUP BY traveldate, co2ratio "
		"ORDER BY traveldate) as ans60, "
		"(SELECT YYYYMM, MSN_Trans, Value_Trans FROM EIA_Trans " 
		"WHERE YYYYMM >= 200803 AND YYYYMM <= 200904 AND YYYYMM <> 200813 AND YYYYMM <> 200913 AND EIA_Trans.MSN_Trans = 'TEACEUS') as eiat "
		"WHERE eiat.YYYYMM = ans60.traveldate; ";
	answer = getans(W, q1);
	for(int i = 0; i < 14; i++){
		cout << "Month:  " << answer[i]["traveldate"].c_str() << "	Percent difference:  " << answer[i]["co2difference"].c_str() << endl;
	}	
	
	W.commit();
	C.disconnect ();  
}
