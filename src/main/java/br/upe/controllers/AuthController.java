package br.upe.controllers;

import br.upe.dataPersistence.operations.HasherInterface;
import br.upe.dataPersistence.operations.QueryState;
import br.upe.dataPersistence.operations.UserCRUD;
import br.upe.dataPersistence.pojos.*;

import java.util.ArrayList;
import java.util.UUID;

public class AuthController {
    private final StateController stateController;
    private final CRUDController crudController;

    public AuthController(StateController stateController, CRUDController crudController) {
        this.stateController = stateController;
        this.crudController = crudController;
    }



    public boolean createNewUser(String email, String password){
        if(QueryState.userFromEmail(email) != null) return false;

        CommomUser user = KeeperInterface.createCommomUser();
        user.setUuid(UUID.randomUUID());
        user.setSubscriptions(new ArrayList<>());
        user.setPassword(HasherInterface.hash(password));
        user.setEmail(email);

        crudController.userCRUD.createUser(user);
        return true;
    }

    public boolean createNewAdmin(String email, String password){
        if(QueryState.userFromEmail(email) != null) return false;

        AdminUser user = KeeperInterface.createAdminUser();
        user.setUuid(UUID.randomUUID());
        user.setSubscriptions(new ArrayList<>());
        user.setEvents(new ArrayList<>());
        user.setPassword(HasherInterface.hash(password));
        user.setEmail(email);

        crudController.userCRUD.createUser(user);
        return true;
    }

    public boolean login(String email, String password){
        User user = UserCRUD.returnUser(QueryState.userFromEmail(email));
        if(user != null && HasherInterface.hash(password).equals(user.getPassword())){
            stateController.setCurrentUser(user);
            return true;
        } return false;
    }

    public boolean logout(){
        if(stateController.getCurrentUser() != null){
            stateController.setCurrentUser(null);
            return true;
        } return false;
    }

}
