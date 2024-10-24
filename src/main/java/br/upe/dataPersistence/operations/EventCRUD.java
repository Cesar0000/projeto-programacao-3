package br.upe.dataPersistence.operations;

import br.upe.dataPersistence.pojos.GreatEvent;
import br.upe.dataPersistence.pojos.HelperInterface;
import br.upe.dataPersistence.pojos.Session;
import br.upe.dataPersistence.pojos.Submission;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

public class EventCRUD extends BaseCRUD {
    public EventCRUD(){ super(); }

    public static String path = ".\\state\\events.csv";

    public static GreatEvent returnEvent(UUID eventUuid){
        try (BufferedReader buffer = new BufferedReader(new FileReader(path))) {
            while(buffer.ready()){
                String line = buffer.readLine();
                if(line.contains(eventUuid.toString())) {
                    return ParserInterface.parseEvent(line);
                }

            }
        } catch (Exception e) {}

        return null;
    }

    public static Collection<GreatEvent> returnEvent(){
        Collection<GreatEvent> events = new ArrayList<>();
        try (BufferedReader buffer = new BufferedReader(new FileReader(path))) {
            while(buffer.ready()){
                String line = buffer.readLine();
                if(!line.isEmpty()) {
                    events.add(ParserInterface.parseEvent(line));

                }
            }
        } catch (Exception e) {}

        return events;
    }

    public void updateEvent(UUID eventUuid, GreatEvent source) {

        GreatEvent event = returnEvent(eventUuid);
        deleteEvent(eventUuid);
        HelperInterface.checkout(source, event);
        createEvent(event);
    }

    public void createEvent(GreatEvent event){

        try (BufferedWriter buffer = new BufferedWriter(new FileWriter(path, true))) {
            buffer.write(ParserInterface.validadeString(event.getUuid()) + ";");
            buffer.write(ParserInterface.validadeString(event.getDescritor()) + ";");
            buffer.write(ParserInterface.validadeString(event.getDirector()) + ";");
            buffer.write((event.getStartDate() != null ? event.getStartDate().toInstant().toString(): "") + ";");
            buffer.write((event.getEndDate() != null ? event.getEndDate().toInstant().toString(): "") + ";");

            for (Session session : event.getSessions()){
                buffer.write(ParserInterface.validadeString(session.getUuid()) + ",");
            }
            buffer.write(";");
            for (Submission submission : event.getSubmissions()){
                buffer.write(ParserInterface.validadeString(submission.getUuid()) + ",");
            }

            buffer.write(";");

            buffer.newLine();
        } catch (Exception e) {}
    }

    public void deleteEvent(UUID eventUuid){
        ArrayList<String> fileCopy = new ArrayList<>();
        try (BufferedReader buffer = new BufferedReader(new FileReader(path))) {
            while(buffer.ready()){
                fileCopy.add(buffer.readLine());
            }
        } catch (Exception e) {}

        try (BufferedWriter buffer = new BufferedWriter(new FileWriter(path))) {
            for(String line: fileCopy){
                if(line.contains(eventUuid.toString())) continue;
                buffer.write(line);
                buffer.newLine();
            }
        } catch (Exception e) {}
    }
}
