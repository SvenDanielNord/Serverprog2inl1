package com.yrgo.dataaccess;

import com.yrgo.domain.Action;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
@Repository
public class ActionDaoJpaImp implements ActionDao {
    private static final String GET_INCOMPLETE_SQL = "SELECT ACTION_ID, DETAILS, COMPLETE, OWNING_USER, REQUIRED_BY FROM ACTION WHERE OWNING_USER=? AND COMPLETE=?";
    @PersistenceContext
    private EntityManager em;
    @Override
    public void create(Action newAction) {
        System.out.println("using jpa");
        em.persist(newAction);

    }

    @Override
    public List<Action> getIncompleteActions(String userId) {
        return em.createQuery("SELECT * FROM ACTION WHERE OWNING_USER="+ userId +" AND COMPLETE=false" , Action.class).getResultList();
    }

    @Override
    public void update(Action actionToUpdate) throws RecordNotFoundException {
//        em.createQuery("UPDATE ACTION SET DETAILS="+ actionToUpdate.getDetails() + ", COMPLETE="+ actionToUpdate.isComplete() +", OWNING_USER="+ actionToUpdate.getOwningUser() + ", REQUIRED_BY="+actionToUpdate.getRequiredBy()+" WHERE ACTION_ID="+ actionToUpdate.getActionId());
        Action exsistingAction = em.find(Action.class, actionToUpdate.getActionId());
        if (exsistingAction != null){
            exsistingAction.setDetails(actionToUpdate.getDetails());
            exsistingAction.setComplete(actionToUpdate.isComplete());
            exsistingAction.setOwningUser(actionToUpdate.getOwningUser());
            exsistingAction.setRequiredBy(actionToUpdate.getRequiredBy());
        }
        em.merge(exsistingAction);
    }

    @Override
    public void delete(Action oldAction) throws RecordNotFoundException {
        em.remove(em.find(Action.class, oldAction.getActionId()));
    }
}
