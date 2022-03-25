package zadanie2;

import java.io.IOException;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import zadanie2.connectors.fileConnection.FileConnection;
import zadanie2.connectors.sqlConnection.HibernateFactory;
import zadanie2.exceptions.parserExceptions.ParsingException;
import zadanie2.parsers.fileParsers.FileJsonParser;

public class MainTest {

	public static void main(String[] args) throws Exception {
		printAllCurrencies();
	}

	public static void test1() throws Exception {
		HibernateFactory factory = new HibernateFactory();
		SessionFactory sessionFactory = factory.factory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Query query = session.createQuery("from Rate where date = '2012-01-04'");// here persistent class name is Emp
		List list = query.list();
		System.out.println(list);
	}

	public static void printAllCurrencies() throws ParsingException, IOException {
		FileConnection fileConnection = new FileConnection(new FileJsonParser(), "dane/fileArrayJson.txt");
		fileConnection.printCurrencies();
	}

}
