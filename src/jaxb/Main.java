package jaxb;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import model.W4FDOC;
import model.W4FDOC.Actor;
import model.W4FDOC.Actor.Movie;

public class Main {
	private static final String XML = "./jaxb.xml";
	
	public static void main(String[] args) throws JAXBException, IOException {
		Actor a1 = new Actor();
		a1.setFirstName("Neil");
		a1.setLastName("Strauss");

		Actor a2 = new Actor();
		a2.setFirstName("Charlotte");
		a2.setLastName("Roche");

		Movie m1 = new Movie();

		m1.setTitle("asdf");
		m1.setYear((short) 1997);

		Movie m11 = new Movie();

		m11.setTitle("asdf1");
		m11.setYear((short) 1995);

		Movie m12 = new Movie();

		m12.setTitle("asdf2");
		m12.setYear((short) 1998);

		Movie m13 = new Movie();

		m13.setTitle("asdf3");
		m13.setYear((short) 1997);

		a1.getMovie().add(m1);
		a1.getMovie().add(m11);
		a1.getMovie().add(m12);
		a2.getMovie().add(m13);
		a2.getMovie().add(m1);

		W4FDOC doc = new W4FDOC();

		doc.getActor().add(a1);
		doc.getActor().add(a2);

		// ===================================== JAXB write
		// =======================================================
		// create JAXB context and instantiate marshaller
		JAXBContext context = JAXBContext.newInstance(W4FDOC.class);

		Marshaller m = context.createMarshaller();
		// formatted as human readable XML
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

		// Write to System.out
		m.marshal(doc, System.out);

		// Write to File
		m.marshal(doc, new File(XML));

		// ===================================== JAXB read
		// =======================================================
		// get variables from our xml file, created before
		System.out.println();
		System.out.println("Output from our XML File: ");

		// Unmarshalling (reading) XML instance documents into Java content
		// trees
		Unmarshaller um = context.createUnmarshaller();
		W4FDOC doc2 = (W4FDOC) um.unmarshal(new FileReader(XML));
		ArrayList<Actor> list = (ArrayList<Actor>) doc2.getActor();

		for (Actor actor : list) {
			System.out.print("Actor: " + actor.getFirstName() + " " + actor.getLastName() + " Films: ");
			for(Movie movie : actor.getMovie()) {
				System.out.print(movie.getTitle() + "/" + movie.getYear() + "; ");
			}
			System.out.print('\n');
		}
	}
}
