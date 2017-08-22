package com.ibm.tatzia.query.parser;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ConsoleErrorListener;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import com.ibm.tatzia.kg.query.CompExpr;
import com.ibm.tatzia.kg.query.OQLIdentifier;
import com.ibm.tatzia.kg.query.OQLNestedQuery;
import com.ibm.tatzia.kg.query.OQLQuery;
import com.ibm.wcs.kg.OQLLexer;
import com.ibm.wcs.kg.OQLParser;
import com.ibm.wcs.kg.OQLParser.NestedQueryContext;
//import org.json.simple.JSONObject;



public class OQLParseHelper {
	
	public static List<String> getLines(String filename) {
		List<String> lines = new ArrayList<String>();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(filename)));

			String strline = null;
			while ((strline = reader.readLine()) != null) {
				lines.add(strline);
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lines;
	}
	
	public OQLNestedQuery parse(String str) throws OQLParseException{
		 ByteArrayOutputStream baos = new ByteArrayOutputStream();
		    PrintStream ps = new PrintStream(baos);
		    // IMPORTANT: Save the old System.out!
		    PrintStream old = System.err;
		    // Tell Java to use your special stream
		    System.setErr(ps);
		   
		   
		
			// Create a stream to hold the output
		    // Show what happened
		   // System.out.println("BAOS: " + baos.toString()); 
		    CharStream stream = new ANTLRInputStream(str.trim());
		    Lexer lexer = new OQLLexer(stream);
		    lexer.removeErrorListener(ConsoleErrorListener.INSTANCE);
		    CommonTokenStream tokens = new CommonTokenStream(lexer);
		    OQLParser parser = new OQLParser(tokens);
		    //parser.removeErrorListener(ConsoleErrorListener.INSTANCE);
		    //parser.setBuildParseTree(true);
		    for(int i = 0; i<tokens.size();++i){
		    	System.out.println(tokens.get(i).getText());
		    }
		    //System.out.println(tokens.getText());
		    NestedQueryContext c=  parser.nestedQuery();
		    
			ParseTreeWalker walker = new ParseTreeWalker();
			GrammarVisitor visit = new GrammarVisitor();
			walker.walk(visit, c);
			OQLNestedQuery q = visit.start;
			
			//System.out.println(q);
			//XStream xstream=new XStream();
			//System.out.println(xstream.toXML(q));
			 System.setErr(old);
			 System.out.println("BAOS: " + baos.toString());
		    if( !baos.toString().equalsIgnoreCase(""))
		    	throw new OQLParseException(baos.toString());
		    else
		    	return q;
		    
//		    parser.
    	
		
	}
	
	
	
	public static void main(String[] args) {
//	    List<String> lines = getLines("Data/lex.txt");
//	    for(String str: lines){		
//	    	str = str.trim();
//	    	if(!str.equals("")){
//		    	
//		    	str = str.substring(0, str.length()-1);
//	    		String[] a = str.split(":");
//	    		String[] b = a[1].split("\\|");
//	    		for(String b1: b){
////	    	    	System.out.println("\"" + b1+ "\"            return '" + a[0]+"';");	    			
//	    	    	System.out.println(b1.replace("'", "\"")+ "            return '" + a[0]+"';");	    			
//	    		}
//	    	}
//	    }	
		
		
		OQLParseHelper tr = new OQLParseHelper();
		String Query1="select a.p from c a union select a.p from c a  unionaa distinct select a.p from c a";
		String testKGQuery = 
				" SELECT  sum(ipo.shares), ic.name FROM IPO ipo, IssuerCompany ic "
				+ "WHERE ipo.foreign = true and ipo->ofCompany->xyz = ic	GROUP BY ic.name "
				+ "ORDER BY ic.name";
			//String testKGQuery1="SELECT SUM(oInstitutionalInvestment.amount),oInstitutionalInvestment.period_of_report_year,oInvestorCompany.name,oInstitutionalInvestment.period_of_report_quarter FROM InstitutionalInvestment oInstitutionalInvestment,InvestorCompany oInvestorCompany,Company oCompany WHERE oCompany.name in ('Alibaba Pictures Group Limited','Alibaba,Alibaba Capital Partners','Alibaba Group Holding Ltd') and oInstitutionalInvestment->Investment_isa_InstitutionalInvestment->investedBy->InvestorCompany_unionof_Investor = oInvestorCompany and oInstitutionalInvestment->Investment_isa_InstitutionalInvestment->investedOn->Security_unionof_Investee->issuedBy = oCompany GROUP BY oInstitutionalInvestment.period_of_report_year,oInvestorCompany.name,oInstitutionalInvestment.period_of_report_quarter";
			String testKGQuery2="SELECT SUM(oBankSubsidiaryMetric.metric_value),oBankSubsidiaryMetric.metric_year as year FROM BankSubsidiaryMetric oBankSubsidiaryMetric,BankingSubsidiary oBankingSubsidiary WHERE oBankSubsidiaryMetric.metric_name = 'TOTAL NONINTEREST INCOME' AND oBankingSubsidiary.name IN ('BANK OF AMERICA RHODE ISLAND, NATIONAL ASSOCIATION','BANK OF AMERICA OREGON, NATIONAL ASSOCIATION','BANK OF AMERICA CALIFORNIA, NATIONAL ASSOCIATION') AND oBankSubsidiaryMetric->forBankingSubsidiary = oBankingSubsidiary GROUP BY oBankSubsidiaryMetric.metric_year ";
			String testKGQuery3="SELECT SUM(oBankSubsidiaryMetric.metric_value),oBankSubsidiaryMetric.metric_year,oBankSubsidiaryMetric.metric_quarter FROM BankSubsidiaryMetric oBankSubsidiaryMetric,BankingSubsidiary oBankingSubsidiary WHERE oBankingSubsidiary.name = 'CITIBANK, N.A.' AND oBankSubsidiaryMetric.metric_name = 'TOTAL INTEREST INCOME' AND oBankSubsidiaryMetric.metric_year >= '2014' AND oBankSubsidiaryMetric.metric_year <= '2100' AND oBankSubsidiaryMetric->forBankingSubsidiary = oBankingSubsidiary GROUP BY oBankSubsidiaryMetric.metric_year,oBankSubsidiaryMetric.metric_quarter";
			String testKGQuery4="SELECT SUM(oFinancialMetric.metric_value),oFinancialMetric.metric_year FROM FinancialMetric oFinancialMetric,Company oCompany WHERE oFinancialMetric.metric_kind = 'quarterly' AND oFinancialMetric.metric_year >= '2015' AND oFinancialMetric.metric_year <= '2015' AND oCompany.name IN ('CITIBANK SWITZERLAND','Citibank N.A.','CITIBANK INTERNATIONAL LIMITED','CITIBANK CANADA','Citibank','CITIBANK TAIWAN LTD','CITIBANK, N.A.','Citibank India','CITIBANK SINGAPORE LIMITED','Citibank Canada','CITIBANK EUROPE PLC','Citibank, N.A.','CITIBANK NIGERIA LIMITED','CITIBANK JAPAN LTD') AND oFinancialMetric.metric_name IN ('Revenues','Revenue') AND oFinancialMetric->forCompany = oCompany GROUP BY oFinancialMetric.metric_year";
			String testKGQuery5="SELECT SUM(oFinancialMetric.metric_value),oFinancialMetric.metric_end_quarter FROM FinancialMetric oFinancialMetric,Company oCompany WHERE oFinancialMetric.metric_kind = 'quarterly' AND oFinancialMetric.metric_year >= '2015' AND oFinancialMetric.metric_year <= '2015' AND oCompany.name IN ('CITIGROUP INC','Citigroup') AND oFinancialMetric.metric_name IN ('Revenues','Revenue') AND oFinancialMetric->forCompany = oCompany GROUP BY oFinancialMetric.metric_end_quarter ";
			String testKGQuery6="SELECT SUM(oFinancialMetric.metric_value),oFinancialMetric.metric_year,oFinancialMetric.metric_end_quarter FROM FinancialMetric oFinancialMetric,Company oCompany WHERE oFinancialMetric.metric_kind = 'quarterly' AND oCompany.name = 'NETFLIX INC' AND oFinancialMetric.metric_name IN ('Revenues','Revenue') AND oFinancialMetric->forCompany = oCompany GROUP BY oFinancialMetric.metric_year,oFinancialMetric.metric_end_quarter ";
			String testKGQuery7="SELECT SUM(oInstitutionalInvestment.amount),oInstitutionalInvestment.period_of_report_year,oInvestorCompany.name,oInstitutionalInvestment.period_of_report_quarter FROM InstitutionalInvestment oInstitutionalInvestment,InvestorCompany oInvestorCompany,Company oCompany WHERE oCompany.name IN ('Alibaba Pictures Group Limited','Alibaba','Alibaba Capital Partners','Alibaba Group Holding Ltd') AND oInstitutionalInvestment->Investment_isa_InstitutionalInvestment->investedBy->InvestorCompany_unionof_Investor = oInvestorCompany AND oInstitutionalInvestment->Investment_isa_InstitutionalInvestment->investedOn->Security_unionof_Investee->issuedBy = oCompany GROUP BY oInstitutionalInvestment.period_of_report_year,oInvestorCompany.name,oInstitutionalInvestment.period_of_report_quarter ";
			String testKGQuery8="SELECT SUM(oCommitment.amount),oLoanAgreement.period_of_report_year,oCommitment.lender_name,oLoanAgreement.period_of_report_quarter FROM Commitment oCommitment,LoanAgreement oLoanAgreement,Borrower oBorrower WHERE oBorrower.name = 'CATERPILLAR INC' AND oCommitment->forLoanAgreement = oLoanAgreement AND oCommitment->forLoanAgreement->forBorrower = oBorrower GROUP BY oLoanAgreement.period_of_report_year,oCommitment.lender_name,oLoanAgreement.period_of_report_quarter ";
			String testKGQuery9="SELECT SUM(oFundingRound.amount),oFundingRound.round_date_year,oFundingRound.type FROM FundingRound oFundingRound WHERE oFundingRound.round_date >= '2011-01-01T00:00:00.000+05:30' AND oFundingRound.round_date <= '2100-01-01T00:00:00.000+05:30' GROUP BY oFundingRound.round_date_year,oFundingRound.type ";
			String testKGQuery10="SELECT SUM(oCompensation.total),oCompensation.year FROM Compensation oCompensation,Person oPerson WHERE oPerson.name IN ('Timothy Cook','Timothy D Cook','Tim Cook') AND oCompensation->compensationForPerson = oPerson GROUP BY oCompensation.year ";
			String testKGQuery11="SELECT SUM(oBankSubsidiaryMetric.metric_value),oBankSubsidiaryMetric.metric_year FROM BankSubsidiaryMetric oBankSubsidiaryMetric,BankingSubsidiary oBankingSubsidiary WHERE oBankingSubsidiary.name IN ('WELLS FARGO BANK, LTD.','WELLS FARGO BANK, NATIONAL ASSOCIATION') AND oBankSubsidiaryMetric.metric_name = 'TOTAL NONINTEREST INCOME' AND oBankSubsidiaryMetric->forBankingSubsidiary = oBankingSubsidiary GROUP BY oBankSubsidiaryMetric.metric_year ";
			String testKGQuery12="SELECT SUM(oCommitment.amount),oLoanAgreement.period_of_report_year FROM Commitment oCommitment,LoanAgreement oLoanAgreement WHERE oCommitment.lender_name IN ('Citibank, N.A.,','Citibank International PLC','CITIBANK N.A.','CITIBANK','CITIBANK, N.A.','CitiBank','Citibank N.A.','CitiBank, N.A.','Citibank','Citibank, N.A.') AND oLoanAgreement.period_of_report >= '2010-01-01T00:00:00.000+05:30' AND oLoanAgreement.period_of_report <= '2100-01-01T00:00:00.000+05:30' AND oCommitment->forLoanAgreement = oLoanAgreement GROUP BY oLoanAgreement.period_of_report_year ";
			String testKGQuery13="SELECT SUM(oTransaction.shares),oTransaction.period_of_report_year FROM Transaction oTransaction,InsiderPerson oInsiderPerson WHERE oInsiderPerson.name = 'James Dimon' AND oTransaction.period_of_report >= '2011-01-01T00:00:00.000+05:30' AND oTransaction.period_of_report <= '2100-01-01T00:00:00.000+05:30' AND oTransaction->forInsider->InsiderPerson_unionof_Insider = oInsiderPerson GROUP BY oTransaction.period_of_report_year ";
			String testKGQuery14="SELECT SUM(oFinancialMetric.metric_value) , oFinancialMetric.metric_year , oFinancialMetric.metric_end_quarter FROM FinancialMetric oFinancialMetric , Company oCompany WHERE oFinancialMetric.metric_kind = 'quarterly' AND oFinancialMetric.metric_name IN ('Revenues' , 'Revenue') AND oCompany.name = 'SOUTHWEST AIRLINES CO' AND oFinancialMetric->forCompany = oCompany GROUP BY oFinancialMetric.metric_year , oFinancialMetric.metric_end_quarter ";
			String testKGQuery15="SELECT SUM(oFinancialMetric.metric_value) , oFinancialMetric.metric_year , oFinancialMetric.metric_end_quarter FROM FinancialMetric oFinancialMetric , Company oCompany WHERE oFinancialMetric.metric_kind = 'quarterly' AND oFinancialMetric.metric_name IN ( 'Revenues' , 'Revenue' ) AND oCompany.name = 'SELECT SUM(oFinancialMetric.metric_value) , oFinancialMetric.metric_year , oFinancialMetric.metric_end_quarter FROM FinancialMetric oFinancialMetric , Company oCompany WHERE oFinancialMetric.metric_kind = 'quarterly' AND oFinancialMetric.metric_name IN ( 'Revenues' , 'Revenue' ) AND oCompany.name = 'SOUTHWEST AIRLINES CO' AND oFinancialMetric->forCompany = oCompany GROUP BY oFinancialMetric.metric_year , oFinancialMetric.metric_end_quarter";
			String testKGQuery16="SELECT AVG(oFinancialMetric.metric_value) , oFinancialMetric.metric_year FROM FinancialMetric oFinancialMetric , Company oCompany WHERE oFinancialMetric.metric_kind = 'quarterly' AND oFinancialMetric.metric_name IN  ( 'SalesRevenueNet' , 'Revenues' , 'Revenue' , 'SalesRevenueServicesNet' )  AND oCompany.name = 'SOUTHWEST AIRLINES CO' AND oFinancialMetric.metric_year >= '2010' AND oFinancialMetric.metric_year <= '2100' AND oFinancialMetric->forCompany = oCompany GROUP BY oFinancialMetric.metric_year";
			String testKGQuery17="select oCompany from CompanyRE oCompany where oCompany.name='CITI BANK'";
			
			//SOLR Queries
			String testKGQuery18="select oDocument as oDocument from Document oDocument, SECDocument oSECDocument "
					+ " where oSECDocument.file_date >= '2011-01-01T00:00:00.000-08:00' and "
					+ " oSECDocument.file_Date <= '2011-12-31T00:00:00.000-08:00' and"
					+ " oDocument match ('rigs','eagle ford') and"
					+ " oSECDocuemnt -> oSECDocument_isa_oDocument = oDocument";
			String testKGQuery19="select oFinancialMetric.metric_year, Sum(oFinancialMetric.metric_value) "
					+ "from FinancialMetric oFinancialMetric, Company oCompany "
					+ "where oFinancialMetric.metric_kind='quarterly' and  "
					+ "oCompany.name in ('Citigroup','CITIGROUP INC') and"
					+ "	 oFinancialMetric.metric_year >='2012' and "
					+ "oFinancialMetric.metric_year <='2100' and "
					+ "oFinancialMetric.metric_name in ('SalesRevenueServicesNet','Revenue','SalesRevenueNet','Revenues') and "
					+ "oCompany->forCompany=oFinancialMetric";
			
			String testKGQuery20="select oDocument as oDocument "
					+ "from Document oDocuemnt, SECDocument oSECDocument "
					+ "where oSECDocument.filer_date >= '2011-01-01T00:00:00.000-08:00' and "
					+ "oSECDocument.filer_date<='2011-12-31T00:00:00.000-08:00' and "
					+ "oDocument match ('wells','completed','Williston Basin') and "
					+ "oSECDocument->oSECDocument_isa_oDocument = oDocuemnt";
			String testKGQuery21="select oDocument as oDocument "
					+ " from Document oDocument, SECDocument oSECDocument "
					+ "where oSECDocument.file_date >='2013-12-31T00:00:00.000-08:00' and  "
					+ "oSECDocument.file_date <= '2013-12-31T00:00:00.000-08:00' and "
					+ "oDocument match('processing capacity','Belfield') and "
					+ "oSECDocument->oSECDocument_isa_oDocument = oDocuemnt";
			String testKGQuery22="select oDocument as oDocument "
					+ "from Document oDocuemnt, SECDocument oSECDocument "
					+ "where oSECDocument.filer_name='Voyager Oil and Gas Incorporated' and "
					+ "oDocument match ('drilling locations','Niobrara' ) and "
					+ "oSECDocument->oSECDocument_isa_oDocument = oDocuemnt";
			String testKGQuery23="select oDocument as oDocument "
					+ "from Document oDocuemnt, SECDocument oSECDocument "
					+ "where oSECDocument.filer_date >= '2015-01-01T00:00:00.000-08:00' and "
					+ "oSECDocument.filer_date<='2015-12-31T00:00:00.000-08:00' and "
					+ "oSECDocument.filer_name = 'Whiting Petroleum' and "
					+ "oDocument match ('guidance','capital budget') and "
					+ "oSECDocument->oSECDocument_isa_oDocument = oDocuemnt";
			
			
			String testKGQuery24="SELECT COUNT(oMorningstarSentiment) , oFundingRound.round_date_quarter FROM MorningstarSentiment oMorningstarSentiment , FundingRound oFundingRound , InvestorCompany oInvestorCompany WHERE oFundingRound.round_date >= '2014-01-01T00:00:00.000+05:30' AND oFundingRound.round_date <= '2014-12-31T23:59:59.999+05:30' AND oInvestorCompany.name = 'Citibank N.A.' AND oMorningstarSentiment.aspect_name = 'INTEREST EXPENSE' AND oMorningstarSentiment->forReport->forSecurity->issuedBy->Company_isa_InvestorCompany->InvestorCompany_unionof_Investor->ToFundingRound = oFundingRound AND oMorningstarSentiment->forReport->forSecurity->issuedBy->Company_isa_InvestorCompany = oInvestorCompany GROUP BY oFundingRound.round_date_quarter";
			String testKGQuery25="SELECT SUM(oAcquisition.acquisition_price) , oAcquisition.event_date_year FROM Acquisition oAcquisition GROUP BY oAcquisition.event_date_year";
			String testKGQuery26="SELECT oWeatherData.Date_day , oWeatherData.Date_month , oDailyForecast.avgWindspeedMPH , oDailyForecast.minTempF , oDailyForecast.maxTempF FROM DailyForecast oDailyForecast , WeatherData oWeatherData , Location oLocation WHERE oWeatherData.Date_day >= '16' AND oWeatherData.Date_month = '4' AND oWeatherData.Date_year = '2016' AND oWeatherData.Date_day <= '17' AND oLocation.city = 'Sunnyvale' AND oDailyForecast->Forecast_isa_DailyForecast->WeatherData_isa_Forecast = oWeatherData AND oDailyForecast->Forecast_isa_DailyForecast->WeatherData_isa_Forecast->location = oLocation ORDER BY oWeatherData.Date_day ";
			String testKGQuery27="SELECT oWeatherData.Date_day , oWeatherData.Date_month , oDailyForecast.avgWindspeedMPH , oDailyForecast.minTempF , oDailyForecast.maxTempF FROM DailyForecast oDailyForecast , Location oLocation WHERE oWeatherData.Date_day >= '5' AND oWeatherData.Date_month = '4' AND oWeatherData.Date_year = '2016' AND oWeatherData.Date_day <= '15' AND oLocation.city = 'Sunnyvale' AND oDailyForecast->Forecast_isa_DailyForecast->WeatherData_isa_Forecast->location = oLocation ORDER BY oWeatherData.Date_day";
			String testKGQuery28="SELECT oWeatherData.Date_day , oWeatherData.Date_month , avg(oDailyForecast.hot_day) FROM DailyForecast oDailyForecast , WeatherData oWeatherData , Location oLocation WHERE oWeatherData.Date_day >= '10' AND oWeatherData.Date_month = '4' AND oWeatherData.Date_year = '2016' AND oWeatherData.Date_day <= '10' AND oLocation.city = 'San Jose' AND oDailyForecast->Forecast_isa_DailyForecast->WeatherData_isa_Forecast = oWeatherData AND oWeatherData->location = oLocation ORDER BY oWeatherData.Date_day";
			String testKGQuery29="SELECT oWeatherData.Date_day, MAX(oHourlyForecast.windchill) FROM HourlyForecast oHourlyForecast , WeatherData oWeatherData , Location oLocation WHERE oWeatherData.Date_day >= '22' AND oWeatherData.Date_month = '4' AND oWeatherData.Date_year ='2016' AND oWeatherData.Date_day <= '30' AND oLocation.city = 'San Jose' AND oLocation.state = 'CA' AND oHourlyForecast->Forecast_isa_HourlyForecast->WeatherData_isa_Forecast = oWeatherData AND oHourlyForecast->Forecast_isa_HourlyForecast->WeatherData_isa_Forecast->location = oLocation GROUP BY oWeatherData.Date_day ORDER BY oWeatherData.Date_day asc ";
			String testKGQuery30="SELECT oWeatherData.Date_day,avg(oHourlyForecast.windchill) FROM HourlyForecast oHourlyForecast , Location oLocation , WeatherData oWeatherData WHERE oLocation.city = 'Seattle' AND oWeatherData.Date_day >= '24' AND oWeatherData.Date_month = '4' AND oWeatherData.Date_year = '2016' AND oWeatherData.Date_day <= '30' AND oHourlyForecast->Forecast_isa_HourlyForecast->WeatherData_isa_Forecast->location = oLocation AND oHourlyForecast->Forecast_isa_HourlyForecast->WeatherData_isa_Forecast = oWeatherData group by oWeatherData.Date_day ORDER BY oWeatherData.Date_day asc";
			String testKGQuery31="SELECT oWeatherData.Date_month , oWeatherData.Date_day , AVG(oHourlyForecast.temperature) FROM HourlyForecast oHourlyForecast , WeatherData oWeatherData , Location oLocation WHERE oWeatherData.Date_day >= '22' AND oWeatherData.Date_month = '4' AND oWeatherData.Date_year = '2016' AND oWeatherData.Date_day <= '30' AND oLocation.city = 'San Jose' AND oLocation.state = 'CA' AND oHourlyForecast->Forecast_isa_HourlyForecast->WeatherData_isa_Forecast = oWeatherData AND oHourlyForecast->Forecast_isa_HourlyForecast->WeatherData_isa_Forecast->location = oLocation GROUP BY oWeatherData.Date_day , oWeatherData.Date_month ORDER BY oWeatherData.Date_day";
			
			String testKGQuery32="SELECT obj.sic_major_group_code , obj.sic_division_code , obj.sic_major_group , obj.sic_code , obj.sic_group , obj.sic_products , obj.sic_division , obj.Industryid FROM Industry obj WHERE obj.sic_major_group IN ('TRANSPORTATION BY AIR')";
			String testKGQuery33="SELECT SUM(oFinancialMetric.metric_value) , oFinancialMetric.metric_year FROM FinancialMetric oFinancialMetric , Company oCompany WHERE oFinancialMetric.metric_kind = 'quarterly' AND oCompany.name IN  ( 'HP Software' , 'HI HOLDINGS HP CABO BV' , 'HEWLETT PACKARD CO' , 'HP NASHVILLE LLC' , 'AERC HP MERGER LLC' , 'HP (BVI) LIMITED' , 'HP OMAHA LLC' , 'HP AUSTIN LLC' , 'HP SAN JUAN LLC' , 'CHATHAM CHERRY CREEK HP LEASECO LLC' , 'DAIRYLAND HP LLC (1)' , 'HP INTEGRATION INC' , 'HP M STREET LLC' , 'HP FOODS HOLDINGS LIMITED' , 'HP ROUTE 46 TEXAS LLC' , 'HP FOODS INTERNATIONAL LIMITED' , 'CHATHAM PITTSBURGH HP LEASECO LLC' , 'HP LANSDOWNE HOSPITALITY LLC' , 'BMC HP LLC' , 'SPT LNR HP UK LTD' , 'RL REGI-AL HP LLC' , 'HHR HP WAIKIKI GP LLC' , 'HP BOCA HOSPITALITY LLC' , 'HP NASHVILLE TRS INC' , 'HP SAN FRANCISCO LLC' , 'HP GLENDALE LLC' , 'LEX SUNCAP HP GP LLC' , 'WMPT BELLAIRE HP LP' , 'HHR HP WAIKIKI LP' , 'HP BEVERAGE SUGAR LAND LLC' , 'HIHCL HP AMSTERDAM AIRPORT BV' , 'LEX SUNCAP HP LP' , 'HP LAS VEGAS BEVERAGE LLC' , 'CHATHAM PITTSBURGH HP LLC' , 'WMPT BELLAIRE HP PROPERTIES LLC' , 'HP INDIA HOLDINGS LIMITED' , 'HP Growth Partners' , 'HI HOLDINGS HP TIJUANA HOTEL BV' , 'CHATHAM CHERRY CREEK HP LLC' , 'HP DELAWARE INC' , 'HP BEVERAGE DALLAS DFW AIRPORT LLC' , 'FP 500 & 600 HP WAY LLC' , 'SWK HP HOLDINGS GP LLC' , 'HP FOODS LIMITED' , 'HP TEN TEXAS LLC' )  AND oFinancialMetric.metric_name = 'Revenues' AND oFinancialMetric->forCompany = oCompany GROUP BY oFinancialMetric.metric_year";
			String testKGQuery34="select c.name,c.eid from Company c where c.name in (select cvm.values from TranslationIndex ti, ConceptValueMap cvm where cvm.concept='Company' and ti.keyword='IBM' and cvm->forKeywords=ti and cvm.property ='name')";
			String testKGQuery35="select c.abc as abc from Company c where c.name in (select d.abc from Company d where d.xyz='abc')";
			String testKGQuery36="SELECT oPublicMetricData.metric_value , oPublicMetricData.metric_year FROM PublicMetricData oPublicMetricData , PublicMetric oPublicMetric , PublicCompany oPublicCompany WHERE oPublicMetricData.metric_kind = 'yearly' AND oPublicMetric.metric_name = 'Revenues' AND oPublicCompany.eid IN  (SELECT  oCompany.eid FROM CommonInsiderHistory oCommonInsiderHistory , CommonInsider oCommonInsider , Company oCompany WHERE oCommonInsider.name IN  ( 'John F Cassidy' , 'James M Cassidy' , 'William J Cassidy' , 'Kevin Michael Cassidy' )  AND oCommonInsiderHistory->toCommonInsider = oCommonInsider AND oCommonInsiderHistory->forCompany = oCompany )  AND oPublicMetricData.metric_year >= '2013' AND oPublicMetricData.metric_year <= '2015' AND oPublicMetricData->forMetric = oPublicMetric AND oPublicMetricData->forPublicCompany = oPublicCompany  order by oPublicMetricData.metric_year";
			String testKGQuery37="select  obj.stock_symbol as stock_symbol, obj.eid as eid, obj.name as name FROM Company obj WHERE obj.name IN ('INTERNATIONAL BUSINESS MACHINES CORP')";
			String testKGQuery38="SELECT oPublicMetricData.metric_value , oPublicMetricData.metric_year FROM PublicMetricData oPublicMetricData , PublicMetric oPublicMetric , PublicCompany oPublicCompany WHERE oPublicMetricData->forMetric = oPublicMetric order by oPublicMetricData.metric_year";
			String testKGQuery39="select avg(distinct ocompany.name) from company ocompany ";
			String testKGQuery40="select p.name as person_name, p.eid as person_id, ih.title as title, ih.latest_known_reporting_date as latest_reporting_date, ih.EARLIEST_KNOWN_REPORTING_DATE as earliest_reporting_date from Company c, Person p, InsiderHistory ih, InsiderPerson ip where c.eid = 'CIK51143' and ih.position = 'Director' and ih->forCompany = c and ih->forInsider = ip and ip->Person_isa_InsiderPerson = p and ((ih.LATEST_KNOWN_REPORTING_DATE >= '2011-01-01' and ih.LATEST_KNOWN_REPORTING_DATE <= '2012-12-31') or ((ih.EARLIEST_KNOWN_REPORTING_DATE >= '2011-01-01' and ih.EARLIEST_KNOWN_REPORTING_DATE <= '2012-12-31') or (ih.LATEST_KNOWN_REPORTING_DATE >= '2012-12-31' and ih.EARLIEST_KNOWN_REPORTING_DATE <= '2011-01-01' )))";
			String testKGQuery41="select c.name from Company c where c.name='IBM' UNION select c.name from company c where c.name='INTERNATIONAL BUSINESS MACHINES CORP'";
			String testKGQuery42="select obj.metric_currency as metric_currency, obj.metric_value as metric_value, obj.metric_decimals as metric_decimals, obj.metric_string_value as metric_string_value, obj.metric_year as metric_year, obj.metric_end_quarter as metric_end_quarter, obj.metric_unit as metric_unit, obj.metric_end_year as metric_end_year, obj.metric_start_quarter as metric_start_quarter, obj.is_computed as is_computed, obj.metric_start_year as metric_start_year,obj.metric_period_type as metric_period_type "
					+ "FROM BankMetricData obj, Bank pc , BankMetric pm, Company c WHERE obj.METRIC_PERIOD_TYPE = 'instant'  AND c.eid = 'FDIC3354599'    AND pm.eid in ('RIAD4079_TOTAL_NONINTEREST_INCOME') "
					+ " AND obj->forBank = pc AND obj->forMetric = pm AND pc->Company_isa_Bank = c  "
					+ "order by obj.metric_start_quarter DESC, obj.metric_start_year DESC  fetch first 1 rows only "
					+ "UNION "
					+ " select obj.metric_currency as metric_currency, obj.metric_value as metric_value, obj.metric_decimals as metric_decimals, obj.metric_string_value as metric_string_value, obj.metric_year as metric_year, obj.metric_end_quarter as metric_end_quarter, obj.metric_unit as metric_unit, obj.metric_end_year as metric_end_year, obj.metric_start_quarter as metric_start_quarter, obj.is_computed as is_computed, obj.metric_start_year as metric_start_year,obj.metric_period_type as metric_period_type "
					+ "FROM BankMetricData obj, Bank pc , BankMetric pm, Company c WHERE obj.METRIC_PERIOD_TYPE in ('quarterly','year-to-date')  AND c.eid = 'FDIC3354599'    AND pm.eid in ('RIAD4079_TOTAL_NONINTEREST_INCOME') "
					+ " AND obj->forBank = pc AND obj->forMetric = pm AND pc->Company_isa_Bank = c  "
					+ "order by obj.metric_start_quarter, obj.metric_start_year DESC ";
			String testKGQuery43="select sum(pd.metric_value) as total,p.eid from publicmetricdata pd, publicmetric p where pd->forMetric=p group by p.eid having total>=20000";
			String testKGQuery46=
				"SELECT b.metric_value, b.formetric "
					+ "FROM (SELECT Max(b2.metric_end_quarter) AS m1, b2.metric_end_year AS n1,b2.formetric AS k1 "
						  + "FROM   (SELECT Max(b1.metric_end_year) AS m, b1.formetric AS k "
						  			+ "FROM   bankmetricdata b1, company c1 "
						  			+ "WHERE  c1.eid = 'FDIC1000052' AND b1 ->forbank = c1 AND b1.metric_period_type = 'instant' "
						  			+ "GROUP BY b1.formetric) t1, "
						  + "bankmetricdata b2, company c2 "
						  + "WHERE b2.formetric = t1.k AND b2.metric_end_year = t1.m AND c2.companyid = b2.forbank AND c2.eid = 'FDIC1000052' AND b2.forbank = c2.companyid "
						  + "AND b2.metric_period_type = 'instant'  "
						  + "GROUP BY b2.formetric, b2.metric_end_year) t2, "
					+ "bankmetricdata b,company c "
					+ "WHERE b.metric_end_quarter = t2.m1 AND b.metric_end_year = t2.n1 AND b.formetric = t2.k1 AND c.companyid = b.forbank AND "
					+ " c.eid = 'FDIC1000052' AND b.metric_period_type = 'instant'";
			String testKGQuery44=
					"SELECT b.metric_value, b.formetric "
					+ "FROM (SELECT Max(b2.metric_end_quarter) AS m1, b2.metric_end_year AS n1,b2.formetric AS k1 "
					+ "FROM (SELECT Max(b1.metric_end_year) AS m, b1.formetric AS k "
					+ "FROM bankmetricdata b1, company c1 "
					+ "WHERE c1.eid = 'FDIC1000052' AND b1 ->forbank = c1 AND b1.metric_period_type = 'instant' "
					+ "GROUP BY b1.formetric) t1, "
					+ "bankmetricdata b2, company c2 "
					+ "WHERE b2.formetric = t1.k AND b2.metric_end_year = t1.m AND c2.companyid = b2.forbank AND c2.eid = 'FDIC1000052' AND b2.forbank = c2.companyid AND b2.metric_period_type = 'instant' "
					+ "GROUP BY b2.formetric, b2.metric_end_year) t2, "
					+ "bankmetricdata b,company c "
					+ "WHERE b.metric_end_quarter = t2.m1 AND b.metric_end_year = t2.n1 AND b.formetric = t2.k1 AND c.companyid = b.forbank AND c.eid = 'FDIC1000052' AND b.metric_period_type = 'instant'";
			String testKGQuery45="SELECT a.name from Company a where  a.name='ABC \\PA\\'";
			String testKGQuery47="SELECT oInsiderHistory.position , oInsiderHistory.earliest_known_date , oInsiderHistory.latest_known_date , oInsiderHistory.title , oInsiderPerson.name , oCompany.name FROM InsiderHistory oInsiderHistory , InsiderPerson oInsiderPerson , Company oCompany WHERE oInsiderPerson.name = 'Steven Abramson' AND oCompany.name IN  ('UNIVERSAL DISPLAY CORP \\PA\\' , 'ABC')  AND oInsiderHistory->forInsider = oInsiderPerson AND oInsiderHistory->forCompany = oCompany";
			String testKGQuery48="select distinct obj.metric_name as metric_name, obj.eid as metric_id FROM PublicMetric obj WHERE obj.metric_name IN (select cvm.values from TranslationIndex ti, ConceptValueMap cvm where cvm.concept='PublicMetric' and ti.keyword='revenues' and cvm->forKeywords=ti and cvm.property = 'metric_name') UNION select distinct obj.metric_name as metric_name, obj.eid as bankmetric_id FROM BankMetric obj WHERE obj.eid IN (select cvm.values from TranslationIndex ti, ConceptValueMap cvm where cvm.concept='BankMetric' and ti.keyword='revenues' and cvm->forKeywords=ti and cvm.property = 'eid')";
			String testKGQuery49="select distinct obj.metric_name as metric_name, obj.eid as metric_id FROM PublicMetric obj WHERE obj.metric_name IN (select cvm.values from TranslationIndex ti, ConceptValueMap cvm where cvm.concept='PublicMetric' and ti.keyword='total assets' and cvm->forKeywords=ti and cvm.property = 'metric_name') UNION select distinct obj.metric_name as metric_name, obj.eid as bankmetric_id FROM BankMetric obj WHERE obj.eid IN (select cvm.values from TranslationIndex ti, ConceptValueMap cvm where cvm.concept='BankMetric' and ti.keyword='total assets' and cvm->forKeywords=ti and cvm.property = 'eid')";
			String testKGQuery50="select distinct obj.metric_name as metric_name, obj.eid as bankmetric_id FROM BankMetric obj WHERE obj.eid IN (null)";
			String testKGQuery51="select c1.eid from company c1, commoninsiderhistory cih  where c1.eid = 'CIK29669' and cih->forCompany=c1";
			String testKGQuery52="select  obj.metric_value as metric_value,  obj.metric_string_value as metric_string_value, obj.metric_unit as metric_unit,   obj.metric_start_year as metric_start_year,obj.metric_end_year as metric_end_year,  obj.metric_start_quarter as metric_start_quarter, obj.metric_end_quarter as metric_end_quarter,    obj.is_computed as is_computed, obj.metric_period_type as metric_period_type, c.eid as company_id, c.name as company_name  FROM PublicMetricData obj, PublicCompany pc , PublicMetric pm, Company c, Industry i WHERE pm.eid = 'http://fasb.org/us-gaap_Revenues' AND i.eid = '4512' AND c->toIndustry = i     AND obj.metric_period_type = 'quarterly'    AND obj->forPublicCompany = pc AND obj->forMetric = pm AND pc->Company_isa_PublicCompany = c          AND obj.metric_start_year = '2014'       AND obj.metric_start_quarter = 'Q1'                   order by obj.metric_start_year DESC, obj.metric_start_quarter DESC";
			
			String testKGQuery53="select relation(a.name, b.name) from Company a, Person b where a.eid='abc'";
			String testKGQuery54="select c.name, c.eid from Company c where c.companyId in ('96786868','87678678') union select c.name, c.eid from company c";
		
		
		String Query2="SELECT b.metric_value, b.formetric "
				+ "FROM (SELECT Max(b2.metric_end_quarter) AS m1, b2.metric_end_year AS n1,b2.formetric AS k1 "
				+ "FROM (SELECT Max(b1.metric_end_year) AS m, b1.formetric AS k "
				+ "FROM bankmetricdata b1, company c1 "
				+ "WHERE c1.eid = 'FDIC1000052' AND b1 ->forbank = c1 AND b1.metric_period_type = 'instant' "
				+ "GROUP BY b1.formetric) t1, "
				+ "bankmetricdata b2, company c2 "
				+ "WHERE b2.formetric = t1.k AND b2.metric_end_year = t1.m AND c2.companyid = b2.forbank AND c2.eid = 'FDIC1000052' AND b2.forbank = c2.companyid AND b2.metric_period_type = 'instant' "
				+ "GROUP BY b2.formetric, b2.metric_end_year) t2, "
				+ "bankmetricdata b,company c "
				+ "WHERE b.metric_end_quarter = t2.m1 AND b.metric_end_year = t2.n1 AND b.formetric = t2.k1 AND c.companyid = b.forbank AND c.eid = 'FDIC1000052' AND b.metric_period_type = 'instant'";
		
		String testKGQuery55="SELECT SUM(distinct oPublicMetricData.metric_value) , oPublicMetricData.metric_year FROM PublicMetricData oPublicMetricData , PublicMetric oPublicMetric , PublicCompany oPublicCompany WHERE oPublicMetricData.metric_kind = 'yearly' AND oPublicMetric.metric_name = 'Revenues' AND oPublicCompany.name IN ( 'IBM Retirement Fund' , 'INTERNATIONAL BUSINESS MACHINES CORP' ) AND oPublicMetricData.metric_year >= '2013' AND oPublicMetricData.metric_year <= '2015' AND oPublicMetricData->forMetric = oPublicMetric AND oPublicMetricData->forPublicCompany = oPublicCompany GROUP BY oPublicMetricData.metric_year";
		
	
		String q = "SELECT SUM(distinct oPublicMetricData.metric_value) , "
				+ "oPublicMetricData.metric_year FROM PublicMetricData oPublicMetricData , PublicMetric oPublicMetric , PublicCompany oPublicCompany "
				+ "WHERE oPublicMetricData.metric_kind = 'yearly' "
				+ "AND oPublicMetric.metric_name = 'Revenues' AND "
				+ "oPublicCompany.name IN ( 'IBM Retirements'' Fund' , 'INTERNATIONAL BUSINESS MACHINES CORP' ) AND "
				+ "oPublicMetricData.metric_year >= '2013' AND oPublicMetricData.metric_year <= '2015' AND "
				+ " oPublicMetricData->forMetric = oPublicMetric AND "
				+ " oPublicMetricData->forPublicCompany = oPublicCompany "
				+ "GROUP BY oPublicMetricData.metric_year";
		String testKGQuery56="select c.name from company c, address b where c.name not in ('abc') and b->forCompany= LEFT OUTER JOIN c";
		String testKGQuery57="select obj.industryCodeDesc as industryCodeDesc, obj.industryCode as industryCode, obj.eid as industry_id, \n obj.classificationSystem as classificationSystem \n FROM Industry obj , Company c WHERE c.eid = 'DUNS:001307602' AND obj->toCompanies = c order by obj.eid asc";
		String testKGQuery58="select c.name from Company c where c.name<> 'abc'";
		String testKGQuery59="select b.metric_name as metric_name, b.metric_id as metric_id from (select a.metric_name as metric_name, a.metric_id as metric_id, a.conf as conf from "
				+ "(select distinct obj.metric_name as metric_name, obj.eid as metric_id, 1 as conf FROM PublicMetric obj WHERE  obj.metric_name IN (select cvm.value from TranslationIndex ti, ConceptValueMap cvm where cvm.concept='PublicMetric' and ti.keyword='revenue' and cvm->forKeywords=ti and cvm.property = 'metric_name'  and cvm.confidence = 'high')"
				+ "UNION  select distinct obj.metric_name as metric_name, obj.eid as metric_id, 1 as conf FROM BankMetric obj where obj.eid IN (select cvm.value from TranslationIndex ti, ConceptValueMap cvm where cvm.concept='BankMetric' and ti.keyword='revenue' and cvm->forKeywords=ti and cvm.property = 'eid' and cvm.confidence = 'high')"
				+ "UNION  select distinct obj.metric_name as metric_name, obj.eid as metric_id, 0 as conf FROM PublicMetric obj  WHERE  obj.metric_name IN (select cvm.value from TranslationIndex ti, ConceptValueMap cvm where cvm.concept='PublicMetric' and ti.keyword='revenue' and cvm->forKeywords=ti and cvm.property = 'metric_name'  and cvm.confidence = 'medium')"
				+ "UNION  select distinct obj.metric_name as metric_name, obj.eid as metric_id, 0 as conf FROM BankMetric obj WHERE   obj.eid IN (select cvm.value from TranslationIndex ti, ConceptValueMap cvm where cvm.concept='BankMetric' and ti.keyword='revenue' and cvm->forKeywords=ti and cvm.property = 'eid' and cvm.confidence = 'medium')"
				+ ") a order by a.conf DESC) b";

		String testKGQuery60="select a.p from  c a where a.p = 0";
		String query = testKGQuery60;
		System.out.println(query);
		
		OQLNestedQuery parsed_query = null;
		try {
			parsed_query = tr.parse(query);
		} catch (OQLParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println(parsed_query);
		OQLQuery query1=null;
		if(parsed_query instanceof OQLQuery)
			 query1= (OQLQuery)parsed_query;
		else
			query1=parsed_query.get_query();
		//System.out.println(query1.toString());
		OQLIdentifier oqlid = (OQLIdentifier) query1.getSelect().getSelectColumns().get(0).getValue();
		System.out.println(oqlid.getAliasref().getName());
		System.out.println(oqlid.getProperty().getName());
		
		System.out.println(query1.getFrom().getFromClause().get(0).getConcept().getName());
		System.out.println(query1.getFrom().getFromClause().get(0).getAlias().getName());
		
		CompExpr compexpr = (CompExpr) query1.getWhereClause().getExpr();
		System.out.println(compexpr.getLeftExpr());
		System.out.println(compexpr.getOp().getSymbol());
		System.out.println(compexpr.getRightExpr());
		//String Query=testKGQuery55;
		//System.out.println(Query);
		//System.out.println(tr.parse("select a.p from  c a"));
		//System.out.println(tr.parse("select a.p from  c a where a.p = 0"));
		//System.out.println(tr.parse("select a.p as p from  c a where a.p > +1"));
		//System.out.println(tr.parse(Query));
		
    	//System.out.println(tr.parse("select a.p from c a where a.b> (select max(a.p,b.c) from b1 b, a1 a) path a->d->d = a->d"));
    	

		
	}

}
