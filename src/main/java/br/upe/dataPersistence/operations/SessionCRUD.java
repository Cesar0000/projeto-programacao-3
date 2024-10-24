package br.upe.dataPersistence.operations;

import br.upe.dataPersistence.pojos.HelperInterface;
import br.upe.dataPersistence.pojos.Session;
import br.upe.dataPersistence.pojos.Subscription;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

public class SessionCRUD extends BaseCRUD {
    public SessionCRUD(){ super(); }

    public static String path = ".\\state\\sessions.csv";

    public static Session returnSession(UUID sessionUuid){

        try (BufferedReader buffer = new BufferedReader(new FileReader(path))) {
            while(buffer.ready()){
                String line = buffer.readLine();
                if(line.contains(sessionUuid.toString())) {
                    return ParserInterface.parseSession(line);
                }
            }
        } catch (Exception e) {}

        return null;
    }

    public static Collection<Session> returnSession(){
        Collection<Session> sessions = new ArrayList<>();
        try (BufferedReader buffer = new BufferedReader(new FileReader(path))) {
            while(buffer.ready()){
                String line = buffer.readLine();
                if(!line.isEmpty()) {
                    sessions.add(ParserInterface.parseSession(line));
                }
            }
        } catch (Exception e) {}

        return sessions;
    }

    public void updateSession(UUID sessionUuid, Session source) {
        Session session = returnSession(sessionUuid);
        deleteSession(sessionUuid);
        HelperInterface.checkout(source, session);
        createSession(session);
    }

    public void createSession(Session session){
        try (BufferedWriter buffer = new BufferedWriter(new FileWriter(path, true))) {
            buffer.write(ParserInterface.validadeString(session.getUuid()) + ";");
            buffer.write(ParserInterface.validadeString(session.getEventUuid()) + ";");
            buffer.write(ParserInterface.validadeString(session.getDescritor()) + ";");
            buffer.write((session.getStartDate() != null ? ParserInterface.validadeString(session.getStartDate().toInstant()): "") + ";");
            buffer.write((session.getStartDate() != null ? ParserInterface.validadeString(session.getEndDate().toInstant()): "") + ";");
            for (Subscription sub : session.getSubscriptions()){
                buffer.write(ParserInterface.validadeString(sub.getUuid()) + ",");
            }

            buffer.write(";");

            buffer.newLine();
        } catch (Exception e) {}
    }

    public void deleteSession(UUID sessionUuid){
        ArrayList<String> fileCopy = new ArrayList<>();

        try (BufferedReader buffer = new BufferedReader(new FileReader(path))) {
            while(buffer.ready()){
                fileCopy.add(buffer.readLine());
            }
        } catch (Exception e) {}

        try (BufferedWriter buffer = new BufferedWriter(new FileWriter(path))) {
            for(String line: fileCopy){
                if(line.contains(sessionUuid.toString())) continue;
                buffer.write(line);
                buffer.newLine();
            }
        } catch (Exception e) {}
    }
}
