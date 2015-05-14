/*	165a HOMEWORK 4
 * 	Creates the tables that will be queried for the various parts of number 3
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
 
int main(int argc, char* argv[]) { 
  string connect, sql, s, s1, s2, s3, s4, s5, s6;
  
	string user_name = getenv("USER");
    connect = "dbname=postgres user=" + user_name + " password=1";
  try {
    // Inorder to connect to the postgresql database, you need to create a database (testdb) and an user (dbuser). 
    connection C(connect); 
    
    if (C.is_open()) { 
      cout << "Opened database successfully: " << C.dbname() << endl; 
    } else { 
      cout << "Can't open database" << endl; 
      return 1; 
    } 
	
	
	work W(C); 
	
	s1 = "CREATE TABLE EIA_MkWh("
	"MSN_MkWh 		CHAR(7),"
	"YYYYMM			REAL,"
	"Value_MkWh 	REAL );";
	
	W.exec(s1); 
	
	s2 = "CREATE TABLE EIA_Electric("
	"MSN_Electric 		CHAR(7),"
	"YYYYMM				REAL,"
	"Value_Electric		REAL );";
	
	W.exec(s2);
	
	s3 = "CREATE TABLE EIA_Trans("
	"MSN_Trans 		CHAR(7),"
	"YYYYMM			REAL,"
	"Value_Trans 	REAL);";
	
	W.exec(s3);
	
	s4 = "CREATE TABLE NHTS_VEH("
	"HOUSEID 		INT,"
	"ODREADING		REAL,"
	"ANNMILES 		REAL,"
	"EPATMPG		REAL,"
	"VEHICLEID		REAL);";
	
	W.exec(s4);
	
	s5 = "CREATE TABLE NHTS_PER("
	"HOUSEID 		INT,"
	"PERSONID		INT,"
	"YEARMILE 		INT,"
	"TRAVELDATE		INT);";
	
	W.exec(s5);
	
	s6 = "CREATE TABLE NHTS_DAY("
	"HOUSEID 			INT,"
	"PERSONID			INT,"
	"TRAVELCASEID 		REAL,"
	"TRIPMILES			REAL,"
	"VEHICLEID			REAL,"
	"VehicleTravelMiles	REAL,"
	"VEHICLETYPE		INT,"
	"TravelDate			INT);";
	
	W.exec(s6);

	cout << "Creating initial tables..." << endl;
  char line[100000];
  string string1, string2, string3, string4, string5, string6, string7, string8, ins;
  char *a;
  ifstream read("EIA_MkWh_2014.csv");
  unsigned long i=0;
  read.getline(line,100000);
  while(!read.eof())
  {
	read.getline(line,100000);
	if(i<7332)
	{
		a = strtok(line,",");
		string1 = a;
		a = strtok(NULL,",");
		string2 = a;
		a = strtok(NULL,",");
		if(strcmp(a,"Not Available") == 0) {
			string3 = "NULL";
		}
		else 
			string3 = a;

		ins = "INSERT INTO EIA_MkWh (MSN_MkWh, YYYYMM, Value_MkWh) " 
			  "VALUES( \'" + string1 + "\' ," + "\'" + string2 + "\' ," + string3 + "); ";
		W.exec(ins);
	}
	i++;
	}
	read.close();
   //------------------------ INSERT TO EIA_ELECTRIC
  ifstream read2("EIA_CO2_Electric_2014.csv");
  i=0;
  read2.getline(line,100000);
  while(!read2.eof())
  {
	read2.getline(line,100000);
	if(i<4860)
	{
		a = strtok(line,",");
		string1 = a;
		a = strtok(NULL,",");
		string2 = a;
		a = strtok(NULL,",");
		if(strcmp(a,"Not Available")==0) 
			string3 = "NULL";
		else 
			string3 = a;
			
		//cout << string1 <<" " << string2 << " " << string3;
		ins = "INSERT INTO EIA_Electric (MSN_Electric, YYYYMM, Value_Electric) " 
			  "VALUES( \'" + string1 + "\' ," + "\'" + string2 + "\' ," + string3 + "); ";
		W.exec(ins);
	}
	i++;
  }
  read2.close();
  
  //------------------------------INSERT TO EIA_TRANS
  ifstream read3("EIA_CO2_Transportation_2014.csv");
  i=0;
  read3.getline(line,100000);
  while(!read3.eof())
  {
	read3.getline(line,100000);
	if(i<6480)
	{
		a = strtok(line,",");
		string1 = a;
		a = strtok(NULL,",");
		string2 = a;
		a = strtok(NULL,",");
		if(strcmp(a,"Not Available")==0)
		
			string3 = "NULL";
		else 
			string3 = a;

		ins = "INSERT INTO EIA_Trans (MSN_Trans, YYYYMM, Value_Trans) " 
			  "VALUES( \'" + string1 + "\' ," + string2 + "," + string3 + "); ";
		W.exec(ins);
	}
	i++;
  }
  read3.close();
  
  unsigned long count =0;
	//INSERT TO NHTS_VEH
	ifstream read4("VEHV2PUB.CSV");
	i=0;
	//read4.getline(line,10000, delimiter = '\n');
	read4.getline(line,100000);
	while(!read4.eof())
	{
		count = 0;
		read4.getline(line,100000);
		
		if(i<309163)
		{
			a = strtok(line,",");	//Get HOUSEID
			string1 = a;
			count++;
			a = strtok(NULL,",");
			count++;
			while(a = strtok(NULL,",")){
				count++;
				if(count == 20) {
					if(strcmp(a,"Not Available")==0)
		
						string2 = "NULL";
					else 
						string2 = a;
				}
				//GETODREADING
				else if(count == 39) {	
					if(strcmp(a,"Not Available")==0)
		
						string3 = "NULL";
					else 
						string3 = a;	//GET ANNMILES
				}
				else if(count == 56)
				{
					if(strcmp(a,"Not Available")==0)
		
						string4 = "NULL";
					else 
						string4 = a;	//EPATMPG
					
				}
				else if(count == 3)
				{
					if(strcmp(a,"Not Available")==0)
		
						string5 = "NULL";
					else 
						string5 = a;	//EPATMPG
					
				}
			}
			//cout << string1 <<" " << string2 << " " << string3;
			ins = "INSERT INTO NHTS_VEH (HOUSEID, ODREADING, ANNMILES, EPATMPG, VEHICLEID) " 
				  "VALUES(" + string1 + "," + string2 + "," + string3 + "," + string4 + "," + string5 + "); ";
			W.exec(ins);
		}
		i++;
	}
	read4.close();

	count =0;
	//INSERT TO NHTS_PER
	ifstream read5("PERV2PUB.CSV");
	i=0;
	read5.getline(line,200000);
	while(!read5.eof())
	{
		count = 0;
		read5.getline(line,200000);
		
		if(i<308901)
		{
			a = strtok(line,",");	//Get HOUSEID
			string1 = a;
			count++;
			a = strtok(NULL,",");
			string2 = a;			//Get second thing
			while(a = strtok(NULL,",")){
				count++;
				if(count == 100) {	
					if(strcmp(a,"Not Available")==0)
		
						string3 = "NULL";
					else 
						string3 = a;	//GET YEARMILE
				}
				else if(count == 104)
				{
					if(strcmp(a,"Not Available")==0)
		
						string4 = "NULL";
					else 
						string4 = a;	//GET TRAVELDATE
				}
			}
			//cout << string1 <<" " << string2 << " " << string3;
			ins = "INSERT INTO NHTS_PER (HOUSEID, PERSONID, YEARMILE, TRAVELDATE) " 
				  "VALUES(" + string1 + "," + string2 + "," + string3 + "," + string4 +"); ";
			W.exec(ins);
		}
		i++;
	}
	read5.close();
	
	count =0;
	//INSERT TO NHTS_DAY
	ifstream read6("DAYV2PUB.CSV");
	i=0;
	read6.getline(line,200000);
	while(!read6.eof())
	{
		count = 0;
		read6.getline(line,200000);
		
		if(i<1048575)
		{
			a = strtok(line,",");	//Get HOUSEID
			string1 = a;
			count++;
			a = strtok(NULL,",");
			string2 = a;			//Get PERSONID
			while(a = strtok(NULL,",")){
				count++;
				if(count == 19) {	
					if(strcmp(a,"Not Available")==0)
		
						string3 = "NULL";
					else 
						string3 = a;	//GET YEARMILE
				}
				else if(count == 94)
				{
					if(strcmp(a,"Not Available")==0)
		
						string4 = "NULL";
					else 
						string4 = a;	//GET TRipmiles
				}				
				else if(count == 83)
				{
					if(strcmp(a,"Not Available")==0)
		
						string5 = "NULL";
					else 
						string5 = a;	//GEt vehicleid
				}							
				else if(count == 96)
				{
					if(strcmp(a,"Not Available")==0)
		
						string6 = "NULL";
					else 
						string6 = a;	//GET vmt_mile
				}							
				else if(count == 109)
				{
					if(strcmp(a,"Not Available")==0)
		
						string7 = "NULL";
					else 
						string7 = a;	//GET Vehicletype
				}							
				else if(count == 93)
				{
					if(strcmp(a,"Not Available")==0)
		
						string8 = "NULL";
					else 
						string8 = a;	//GET TRAVELDATE
				}				
			}

			ins = "INSERT INTO NHTS_DAY (HOUSEID, PERSONID, TRAVELCASEID, TRIPMILES, VEHICLEID, VehicleTravelMiles, VEHICLETYPE, TravelDate)"
				  "VALUES(" + string1 + "," + string2 + "," + string3 + "," + string4 + "," + string5 + "," + string6 + "," + string7 + "," + string8 + "); ";
			W.exec(ins);
		}
		i++;
	}
	read6.close();
    
    cout << "Creating table for part 3 c..." << endl;
    //GENERATE PART 3c table
	s = "SELECT houseid, epatmpg, vehicleid, vehicletravelmiles, traveldate "
		"INTO bigtable "
		"FROM (SELECT HouseId, epatmpg, vehicleid FROM nhts_veh) AS veh "
			"INNER JOIN (SELECT HouseId AS hh, vehicletravelmiles, vehicleid AS vv, traveldate FROM nhts_day) AS day "
			"ON (veh.HouseId = day.hh AND veh.vehicleid = day.vv) "
		"WHERE vehicletravelmiles > 0;";

	W.exec(s);
	
	//GENERATE PART 3d tables
	cout << "Creating tables for part 3 d..." << endl;
	
	//CO2 Ratio table
	s = "SELECT msn_mkwh, msn_electric, yyyymm, value_mkwh, value_electric, (value_electric/value_mkwh) AS CO2Ratio "
		"INTO mytable "
		"FROM " 
			"(SELECT MSN_Mkwh, YYYYMM as yearmm, value_mkwh FROM EIA_mkwh) as eiam "
		"INNER JOIN (SELECT MSN_Electric, YYYYMM, value_electric FROM EIA_Electric) as eiae "
		"ON (eiae.yyyymm = eiam.yearmm) "
		"WHERE eiae.MSN_Electric = 'TXEIEUS' AND eiam.MSN_Mkwh = 'ELETPUS' AND eiae.YYYYMM = eiam.yearmm; ";
		
	W.exec(s);
	
	//Miles < 20 tables
	//THIS SELECTS ALL TUPLES OF MILES LESS THAN 20
	s = "SELECT HOUSEID, PERSONID, VEHICLEID, VEHICLETRAVELMILES, TravelDate, epatmpg, (vehicletravelmiles/(epatmpg * 0.090634441)) as electmkwh, (vehicletravelmiles-vehicletravelmiles) as leftovermile "
		"INTO Lessthan20 "
		"FROM (SELECT HOUSEID, PERSONID, VEHICLEID, VEHICLETRAVELMILES, TravelDate FROM NHTS_DAY) as day "
		"INNER JOIN (SELECT HouseId as hid, epatmpg, vehicleid as vid FROM nhts_veh) AS veh "
		"ON (veh.Hid = day.houseID AND veh.vid = day.vehicleid) "
		"WHERE TravelDate >= 200803 AND TravelDate <= 200904 AND TravelDate <> 200813 AND TravelDate <> 200913 AND VehicleTravelMiles > 0 AND VehicleTravelMiles < 20; ";
		
	W.exec(s);

	//GETS TUPLES AND MKWH AND LEFTOVERMILES FOR ALL TRIPS GREATER THAN 20, NEED TO CHANGE TO 40 & 60
	s = "SELECT HOUSEID, PERSONID, VEHICLEID, VEHICLETRAVELMILES, TravelDate, epatmpg, (20/(epatmpg * 0.090634441)) as electmkwh, (vehicletravelmiles-20) as leftovermile "
		"INTO Greaterthan20 "
		"FROM (SELECT HOUSEID, PERSONID, VEHICLEID, VEHICLETRAVELMILES, TravelDate FROM NHTS_DAY) as day "
		"INNER JOIN (SELECT HouseId as hid, epatmpg, vehicleid as vid FROM nhts_veh) AS veh "
		"ON (veh.Hid = day.houseID AND veh.vid = day.vehicleid) "
		"WHERE TravelDate >= 200803 AND TravelDate <= 200904 AND TravelDate <> 200813 AND TravelDate <> 200913 AND VehicleTravelMiles >= 20; ";
		
	W.exec(s);	

	//CREATES THE BIG TABLE OF ALL MKWH AND LEFTOVER MILES FOR ALL TRIPS need to change the 20s
	s = "SELECT * " 
		"INTO totaldata20 "
		"FROM (SELECT * FROM Lessthan20 "
			"UNION "
			"SELECT * FROM greaterthan20 ) a; ";
		
	W.exec(s);
	
	//THIS COMBINES RATIO AND MKWH AND GALLONS
	s = "SELECT VEHICLETRAVELMILES, TravelDAte, epatmpg, electmkwh, leftovermile, CO2Ratio, houseid, vehicleid "
		"INTO foo20 "
		"FROM (SELECT * FROM totaldata20) as foo "
		"INNER JOIN "
		"(SELECT * FROM mytable) as foo2 "
		"ON foo.traveldate = foo2.yyyymm " 
		"WHERE TravelDate >= 200803 AND TravelDate <= 200904 AND TravelDate <> 200813 AND TravelDate <> 200913; ";

	W.exec(s);		
	
	//Miles < 40
	s = "SELECT HOUSEID, PERSONID, VEHICLEID, VEHICLETRAVELMILES, TravelDate, epatmpg, (vehicletravelmiles/(epatmpg * 0.090634441)) as electmkwh, (vehicletravelmiles-vehicletravelmiles) as leftovermile "
		"INTO Lessthan40 "
		"FROM (SELECT HOUSEID, PERSONID, VEHICLEID, VEHICLETRAVELMILES, TravelDate FROM NHTS_DAY) as day "
		"INNER JOIN (SELECT HouseId as hid, epatmpg, vehicleid as vid FROM nhts_veh) AS veh "
		"ON (veh.Hid = day.houseID AND veh.vid = day.vehicleid) "
		"WHERE TravelDate >= 200803 AND TravelDate <= 200904 AND TravelDate <> 200813 AND TravelDate <> 200913 AND VehicleTravelMiles > 0 AND VehicleTravelMiles < 40; ";
		
	W.exec(s);		
	
	s = "SELECT HOUSEID, PERSONID, VEHICLEID, VEHICLETRAVELMILES, TravelDate, epatmpg, (40/(epatmpg * 0.090634441)) as electmkwh, (vehicletravelmiles-40) as leftovermile "
		"INTO Greaterthan40 "
		"FROM (SELECT HOUSEID, PERSONID, VEHICLEID, VEHICLETRAVELMILES, TravelDate FROM NHTS_DAY) as day "
		"INNER JOIN (SELECT HouseId as hid, epatmpg, vehicleid as vid FROM nhts_veh) AS veh "
		"ON (veh.Hid = day.houseID AND veh.vid = day.vehicleid) "
		"WHERE TravelDate >= 200803 AND TravelDate <= 200904 AND TravelDate <> 200813 AND TravelDate <> 200913 AND VehicleTravelMiles >= 40; ";
		
	W.exec(s);
	
	s = "SELECT * " 
		"INTO totaldata40 "
		"FROM ( "
		"SELECT * FROM Lessthan40 "
		"UNION " 
		"SELECT * FROM greaterthan40 ) a; ";
				
	W.exec(s);
	
	s = "SELECT houseid, vehicleid, VEHICLETRAVELMILES, TravelDAte, epatmpg, electmkwh, leftovermile, CO2Ratio "
		"INTO foo40 "
		"FROM (SELECT * FROM totaldata40) as foo "
		"INNER JOIN "
		"(SELECT * FROM mytable) as foo2 "
		"ON foo.traveldate = foo2.yyyymm " 
		"WHERE TravelDate >= 200803 AND TravelDate <= 200904 AND TravelDate <> 200813 AND TravelDate <> 200913; ";
		
	W.exec(s);
		
	//Miles < 60
	s = "SELECT HOUSEID, PERSONID, VEHICLEID, VEHICLETRAVELMILES, TravelDate, epatmpg, (vehicletravelmiles/(epatmpg * 0.090634441)) as electmkwh, (vehicletravelmiles-vehicletravelmiles) as leftovermile "
		"INTO Lessthan60 "
		"FROM (SELECT HOUSEID, PERSONID, VEHICLEID, VEHICLETRAVELMILES, TravelDate FROM NHTS_DAY) as day "
		"INNER JOIN (SELECT HouseId as hid, epatmpg, vehicleid as vid FROM nhts_veh) AS veh "
		"ON (veh.Hid = day.houseID AND veh.vid = day.vehicleid) "
		"WHERE TravelDate >= 200803 AND TravelDate <= 200904 AND TravelDate <> 200813 AND TravelDate <> 200913 AND VehicleTravelMiles > 0 AND VehicleTravelMiles < 60; ";
	
	W.exec(s);
	
	s = "SELECT HOUSEID, PERSONID, VEHICLEID, VEHICLETRAVELMILES, TravelDate, epatmpg, (60/(epatmpg * 0.090634441)) as electmkwh, (vehicletravelmiles-60) as leftovermile "
		"INTO Greaterthan60 "
		"FROM (SELECT HOUSEID, PERSONID, VEHICLEID, VEHICLETRAVELMILES, TravelDate FROM NHTS_DAY) as day "
		"INNER JOIN (SELECT HouseId as hid, epatmpg, vehicleid as vid FROM nhts_veh) AS veh "
		"ON (veh.Hid = day.houseID AND veh.vid = day.vehicleid) "
		"WHERE TravelDate >= 200803 AND TravelDate <= 200904 AND TravelDate <> 200813 AND TravelDate <> 200913 AND VehicleTravelMiles >= 60; ";
		
	W.exec(s);	
	
	s = "SELECT * " 
		"INTO totaldata60 "
		"FROM (SELECT * FROM Lessthan60 "
		"UNION " 
		"SELECT * FROM greaterthan60 ) a; ";
		
	W.exec(s);	
	
	s = "SELECT houseid, vehicleid, VEHICLETRAVELMILES, TravelDAte, epatmpg, electmkwh, leftovermile, CO2Ratio "
		"INTO foo60 "
		"FROM (SELECT * FROM totaldata60) as foo "
		"INNER JOIN "
		"(SELECT * FROM mytable) as foo2 "
		"ON foo.traveldate = foo2.yyyymm " 
		"WHERE TravelDate >= 200803 AND TravelDate <= 200904 AND TravelDate <> 200813 AND TravelDate <> 200913;	";
	W.exec(s);	
			
    cout << "Records created successfully" << endl;
    W.commit();  
    C.disconnect (); 
  } catch (const std::exception &e) { 
    cerr << e.what() << std::endl; 
    return 1; 
  } 
 
  return 0; 
} 
